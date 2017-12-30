package com.egress.bullsandcows;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BullsAndCowsServlet
 */
@WebServlet("/BullsAndCowsServlet")
public class BullsAndCowsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Random rand = new Random();

	// 4-digit secret number
	private ArrayList<Integer> secretNumber = new ArrayList<Integer>(4);
	private int secretNumberAttempts=0;

	// 4-digit possible guesses by computer
	private ArrayList<Integer> computerGuessNumbers = new ArrayList<Integer>(9*8*7*6);
	
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BullsAndCowsServlet() {
        super();
        // Generate a secret number for Part 1 
        genNumberToArray(secretNumber, genRandomNumber());
        secretNumberAttempts=0;
        
        // Initialise possible compueter guesses for part 2 of the game
        initialiseComputerGuessNumbers(computerGuessNumbers);
    }
    
    private void initialiseComputerGuessNumbers(ArrayList<Integer> guessNumList) {
    	
    	guessNumList.clear();
    	
    	for (int i=1111; i<10000; i++) {
    		String val = String.valueOf(i);
    		
    		if (!(val.contains("0")) && !hasDupes(val) ) {
	    		Integer num = new Integer(i);
	            guessNumList.add(num);
    		}
    	}
    	
    	System.out.println("Computer guess list size: "+guessNumList.size());
    }	
    
    private boolean hasDupes(String inputDigits) {
    	boolean hasDupes=false;
    	int a=0,b=0,c=0,d=0;
    	
    	try {
        	a = Integer.parseInt(inputDigits.substring(0, 1));
        	b = Integer.parseInt(inputDigits.substring(1, 2));
        	c = Integer.parseInt(inputDigits.substring(2, 3));
        	d = Integer.parseInt(inputDigits.substring(3));
    	} catch (Exception e) {
    		// Should not happen
    	}
    	
    	if (a != b && a !=c && a !=d && b != c && b !=d && c != d) {
    		// All unique
    	} else {
    		hasDupes = true;
    	}
    	
    	return hasDupes;
    }
    
    private void genNumberToArray(ArrayList<Integer> arrayNumber, String genNumber) {
    	
      	// Set up the secret number in the internal array 
    	arrayNumber.clear();
    	
    	arrayNumber.add(new Integer(genNumber.substring(0,1)));
    	arrayNumber.add(new Integer(genNumber.substring(1,2)));
    	arrayNumber.add(new Integer(genNumber.substring(2,3)));
    	arrayNumber.add(new Integer(genNumber.substring(3)));

    	// Print the secret number to console just for info
    	System.out.println("ARRAY NUMBER: "+arrayNumber.toString());
  	
    }

    private boolean validateInputDigits(String inputDigits) {
    	boolean isUniqueDigits=false;

    	int a=0, b=0, c=0, d=0;
    	
    	// Check for numeric
    	try {
        	a = Integer.parseInt(inputDigits.substring(0, 1));
        	b = Integer.parseInt(inputDigits.substring(1, 2));
        	c = Integer.parseInt(inputDigits.substring(2, 3));
        	d = Integer.parseInt(inputDigits.substring(3));
    	} catch (Exception e) {
    		// Non-numeric digit
    		return isUniqueDigits;
    	}
    	
    	// Check all digits are not zero
    	if (a == 0 || b == 0 || c == 0 || d == 0) {
    		
    		// Some zero digits
    		return isUniqueDigits;
    	}
    	
    	if (a != b && a !=c && a !=d && b != c && b !=d && c != d) {
    		isUniqueDigits=true;
    	}
 		  	
    	return isUniqueDigits;
    }
    
    private String genRandomNumber() {
    	
    	// Clear array in preparation for generating a new secret number
    	
     	int a=0,b=0,c=0,d=0;

    	a = getRandomDigitOneToNine();
      	//System.out.println("a="+String.valueOf(a));
          	
    	do {
        	b = getRandomDigitOneToNine();
        	//System.out.println("b="+String.valueOf(b));
    	} while (a == b);
    	
    	do {
        	c = getRandomDigitOneToNine();
        	//System.out.println("c="+String.valueOf(c));
    	} while ((a == c) || (b == c)); 
    	
      	do {
        	d = getRandomDigitOneToNine();
        	//System.out.println("d="+String.valueOf(d));
      	} while ((a == d) || (b == d) || (c == d));
    	
      	// Return the new generated number
    	return (String.valueOf(a)+String.valueOf(b)+String.valueOf(c)+String.valueOf(d));
    }
    
    private int getRandomDigitOneToNine() {
    	int num=0;
    	
    	// Generate a random number between 1 and 9 inclusive
    	do {
    		
    		num = rand.nextInt(10);
    		
       } while (num == 0);
    	
    	return num;
    }
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String outcome="";
		
		String guessedNumber = request.getParameter("number_guess");
		if (guessedNumber == null || guessedNumber.equals("")) {

			String clientSecretNumber = request.getParameter("number_secret");
			if (clientSecretNumber == null || clientSecretNumber.equals("")) {
				System.out.println("NOTHING TO DO");

				String resultHTML = formErrorResultHTML("Please click on \"Try Again\" and select an option, Part 1 or Part 2");
				
				// Build the response page
				generatePageOutput(resultHTML, request, response);

				
			} else {
				// Execute Part 2
				
				if (validateInputDigits(clientSecretNumber)) {

					System.out.println("CLIENT SECRET NUMBER: "+clientSecretNumber);
					
					outcome = doPartTwo(clientSecretNumber);
					System.out.println("OUTCOME: "+outcome);

					String resultHTML = formPart2ResultHTML(request, outcome);
					
					// Build the response page
					generatePageOutput(resultHTML, request, response);

			        // Generate another secret number in case Part 1 is done again
					genNumberToArray(secretNumber, genRandomNumber());
					secretNumberAttempts=0;
					
				    // Initialise possible computer guesses in case part 2 of the game is done again
			        initialiseComputerGuessNumbers(computerGuessNumbers);
			 
					
				} else {
					
					System.out.println("INPUT VALIDATION ERROR");

					String resultHTML = formErrorResultHTML("Specified number must only contain 4 unique different digits 1-9");
					
					// Build the response page
					generatePageOutput(resultHTML, request, response);

				}
				
			}
			
		} else {
			// Execute Part 1
			if (validateInputDigits(guessedNumber)) {

				++secretNumberAttempts;
				
				System.out.println("GUESSED NUMBER: "+guessedNumber);
				
				outcome = doPartOne(guessedNumber);
				System.out.println("OUTCOME: "+outcome);
	
				String resultHTML = formPart1ResultHTML(request, outcome);
				
				// Build the response page
				generatePageOutput(resultHTML, request, response);
			} else {
				System.out.println("INPUT VALIDATION ERROR");

				String resultHTML = formErrorResultHTML("Specified number must only contain 4 unique different digits 1-9");
				
				// Build the response page
				generatePageOutput(resultHTML, request, response);

				
			}
		}
	}

	private String doPartOne(String guessedNumber) {
		
		StringBuffer outcome = new StringBuffer();
		
		int countCows=0, countBulls=0, countNone=0;
		
		for (int i=0; i<guessedNumber.length(); i++) {
			int guessDigit = Integer.parseInt(guessedNumber.substring(i, i+1));
		
			String outcomeType = checkOutcome(i, guessDigit, secretNumber);
			
			if (outcomeType.equals("Bull")) {
				
				++countBulls;
			} else if (outcomeType.equals("Cow")) {
				
				++countCows;
			} else {
				
				++countNone;
			}
		}
		
		if (countBulls == 4) {
			outcome.append("Congratulations, you have guessed the secret number after " + String.valueOf(secretNumberAttempts) + " attempts!" );
			
		} else {
			
			outcome.append("Bulls: "+String.valueOf(countBulls) + "; " );
			outcome.append("Cows: "+String.valueOf(countCows) + "; " );
			outcome.append("None: "+String.valueOf(countNone) );
		}
		
		
		
		return outcome.toString();
	}
	
	private String doPartTwo(String clientSecretNumber) {
		
		StringBuffer outcome = new StringBuffer();
		
		int countCows=0, countBulls=0, countNone=0;
		
		// First set the secret number from the client
		genNumberToArray(secretNumber, clientSecretNumber);
		
	    int numAttempts = 0;
	    String computerGuessNumber = "";

	    do {

	    	if (numAttempts == 0) {
	    		// On first attempt use a random 4 digit number
	    		computerGuessNumber = genRandomNumber();
	    		
	    	} else {
	    		// TODO: On subsequent attempts get a random 4 digit number from the 
	    		// pruned array "computerGuessNumbers"
	    		// computerGuessNumber = 
	    		computerGuessNumber = genRandomNumber(); // comment out once TODO is done
	    	}
	 	    
		    System.out.println("Part 2 Guess ("+String.valueOf(numAttempts+1)+"): "+computerGuessNumber);
		    
			for (int i=0; i<computerGuessNumber.length(); i++) {
				int guessDigit = Integer.parseInt(computerGuessNumber.substring(i, i+1));
			
				String outcomeType = checkOutcome(i, guessDigit, secretNumber);
				
				if (outcomeType.equals("Bull")) {
					
					++countBulls;
				} else if (outcomeType.equals("Cow")) {
					
					++countCows;
				} else {
					
					++countNone;
				}
			}
			
			pruneGuessArray(computerGuessNumber, countBulls, countCows, countNone, computerGuessNumbers);
			
			++numAttempts;
			
			outcome.append("Computer attempt "+String.valueOf(numAttempts)+" guess is "+computerGuessNumber+" - ");
			outcome.append("Bulls: "+String.valueOf(countBulls) + "; " );
			outcome.append("Cows: "+String.valueOf(countCows) + "; " );
			outcome.append("None: "+String.valueOf(countNone) );
			outcome.append("<br/>");
			
			countBulls=0;
			countCows=0;
			countNone=0;
			
	    } while (numAttempts < 15); // Comment this out once the other code is completed
    // } while (countBulls < 4 ); // Uncomment this once the other code is completed
	    
	    	    
		if (countBulls == 4) {
			outcome.append("Congratulate the computer, it has guessed the secret number in " );
			outcome.append(String.valueOf(numAttempts));
			outcome.append(" attempts!");
		} else {
			outcome.append("<br />Need to complete code on this Part 2 algorithm");
		}
		
		return outcome.toString();
	}

	private void pruneGuessArray(String computerGuessNumber, int countBulls, int countCows, int countNone, ArrayList<Integer> guessArray) {
		// Need to remove candidates from the computer guess array that dont fulfil the bulls and cows criteria
		// before the computer next guess
		
		// If Cow count is zero, can remove all permutations of "computerGuessNumber" from the guess array 
		
		if (countNone == 4) {
			guessArray.remove(Integer.valueOf(computerGuessNumber));
			
			// TODO: Also remove all permutations of "computerGuessNumber" from "guessArray"
		} else { 
			
			if (countCows > 0) {
				// TODO: Complete code - some digits are valid - decide on strategy
			}
		
			if (countBulls > 0) {
				// TODO: Complete code - some digits are valid - decide on strategy
			}
		
			
		}
		
		
		System.out.println("After pruning, guess array size is: "+String.valueOf(guessArray.size()));
	}

	
	private String formErrorResultHTML(String errorText) {
		String outputText="";
		
		outputText = "<h2><b><font color=\"red\">" + errorText + "</font></b></h2>";
				
		return outputText;
	}

	private String formPart1ResultHTML(HttpServletRequest request, String result) {
		String outputText="";
		
		outputText = "<h2>" + "You guessed: " + request.getParameter("number_guess") + "</h2>\n" +
                 "<br />" +
              	  "<h2><b>" + result + "</b></h2>" +  
                 "\n" + "<br />" +
                 "  <b><font color=\\\"red\\\">( SECRET NUMBER: "+secretNumber.toString() + " )</font></b>";
		
		return outputText;
	}

	private String formPart2ResultHTML(HttpServletRequest request, String result) {
		String outputText="";
		
		outputText = "  <b><font color=\"red\">( SECRET NUMBER: "+secretNumber.toString() + " )</font></b>"+
		"<h2><b>"+result+"</b></h2>";
		
		return outputText;
	}

	private void generatePageOutput(String resultHTML, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Set response content type
	      response.setContentType("text/html");

	      PrintWriter out = response.getWriter();

		  out.append("Pat Crowleys servlet, served at: ").append(request.getContextPath());

	      String title = "Bulls and Cows Game";
	      String docType =
	         "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
	         
	      out.println(docType +
	 	         "<html>\n" +
	 	            "<head><title>" + title + "</title></head>\n" +
	 	            "<body bgcolor = \"#f0f0f0\">\n" +
	 	               "<h1 align = \"center\">" + title + "</h1>\n" +
	               	  "<div align=\"center\">" + 
	         	 	   
					resultHTML +
	 		 	          
	 		 	          "<h3><a href=\"http://localhost:8080/BullsAndCows/BullsAndCowsForm.html\">Try Again</a></h3>" +
	 	                  "</div>" + 
	 	          "</body></html>"
	 	      );
	 		
		
	}
	
	private String checkOutcome(int guessPos, int guessNum, ArrayList<Integer> secretNumber) {
		String outcome = "";
		
		if (isCow(guessNum, secretNumber)) {
			
			if (isBull(guessPos, guessNum, secretNumber)) {
				outcome = "Bull";
			} else {
				outcome = "Cow";
			}
		} else {
			outcome = "Nah";
		}
		
		return outcome;
	}

	private boolean isCow(int guessNum, ArrayList<Integer> secretNumber) {
		boolean isCow = false;
		
		isCow = secretNumber.contains(new Integer(guessNum));
		
		return isCow;
	}
	
	private boolean isBull(int guessPos, int guessNum, ArrayList<Integer> secretNumber) {
		boolean isBull = false;
		
		if (guessPos == secretNumber.indexOf(new Integer(guessNum))) {
			isBull = true;
		}
		
		return isBull;
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
