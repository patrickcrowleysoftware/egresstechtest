package com.egress.bullsandcows;

import java.util.ArrayList;
import java.util.Random;

public class TestRnd {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// 4-digit secret number
		ArrayList<Integer> secretNumber = new ArrayList<Integer>(4);
	    
		System.out.println("Start...");	
			
	      // Generate the secret number
        genSecretNumber(secretNumber);
        
        // Print the secret number
        printSecretNumber(secretNumber);
  
	}

	static void printSecretNumber(ArrayList<Integer> secretNumber) {
    	
    	System.out.println("SECRET NUMBER: "+secretNumber.toString());
    }
    
    static private void genSecretNumber(ArrayList<Integer> secretNumber) {
    	
    	int a=0,b=0,c=0,d=0;

    	a = getRandomDigitOneToNine();
      	System.out.println("a="+String.valueOf(a));
          	
    	do {
        	b = getRandomDigitOneToNine();
        	System.out.println("b="+String.valueOf(b));
    	} while (a == b);
    	
    	do {
        	c = getRandomDigitOneToNine();
        	System.out.println("c="+String.valueOf(c));
    	} while ((a == c) || (b == c)); 
    	
      	do {
        	d = getRandomDigitOneToNine();
        	System.out.println("d="+String.valueOf(d));
      	} while ((a == d) || (b == d) || (c == d));
    	
    	System.out.println("VALUE: "+
    	String.valueOf(a)+
    	String.valueOf(b)+
    	String.valueOf(c)+
    	String.valueOf(d));
    	
    	secretNumber.add(new Integer(a));
    	secretNumber.add(new Integer(b));
    	secretNumber.add(new Integer(c));
    	secretNumber.add(new Integer(d));
    	
    }
    
    static private int getRandomDigitOneToNine() {
    	int num=0;
    	
    	Random rand = new Random();

    	// Generate a random number between 1 and 9 inclusive
    	do {
    		
    		num = rand.nextInt(10);
    		
       } while (num == 0);
    	
    	return num;
    }
    

}
