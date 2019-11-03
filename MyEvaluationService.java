package com.revature.eval.java.core;

import java.lang.*;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;


public class MyEvaluationService {


	public static void main(String[] args) {
		// TODO Auto-generated method stub


		List<Integer> testList = new ArrayList<Integer>();
		testList.add(1);
		testList.add(3);
		testList.add(5);
		testList.add(7);
		testList.add(9);

		MyEvaluationService m = new MyEvaluationService();
		//	MyEvaluationService.BinarySearch<Integer> bs = new MyEvaluationService.BinarySearch<>(testList);
		//		BinarySearch<Integer> bs = new BinarySearch<>(testList);
		//		Integer k = 0;
		//		int getpos = bs.indexOf(9);
		//		System.out.println(" get pos is "+getpos);

		//	int getpos = bs.indexOf();
		//int position = MyEvaluationService.BinarySearch<List>().indexOf(testList);


		String expression = "What is 33 divided by -3?";
//		if(expression.matches(".*\\d.*")) {
//			System.out.println("the number is "+expression);
//		}
		int answer = m.solveWordProblem(expression);
		System.out.println("the answer is "+answer);
		//		if (expression.matches("\\d+")) {  //regex only to check if the string is a number
		//			System.out.println("yes tehre is a number");
		//		}
		// to find more than one numbers in the expression
		//		if (expression.matches(".*\\d.*")) {
		//			System.out.println("yes tehre is a number");
		//		}



	}

	public int solveWordProblem(String string) {
		// TODO Write an implementation for this method declaration
		String cleanExpression = string.replace("?",""); //remove question mark
		String [] exp = cleanExpression.split(" ");
		//String [] operators = {"plus","minus","multiplied","divided"};
		int answer = 0;

		int factor1 = Integer.parseInt(exp[exp.length -1]); //last element is always a number
		int factor2 = 0;

		for (int i = 0; i< exp.length; ++i) { //find the second number
			String tt = exp[i];
			if (exp[i].matches(".*\\d.*")) { //check if the array string element is a number
				factor2 = Integer.parseInt(exp[i]);
				String op = exp[i+1];  //next is the operator always
				switch (op) {
				case "plus":
					answer = factor2 + factor1;
					break;
				case "minus": 
					answer = factor2 - factor1;
					break;
				case "multiplied":
					answer = factor2 * factor1;
					break;
				case "divided":
					answer = factor2 / factor1;
					break;
				}
				break;
			}
		}	

		//		System.out.println(Arrays.toString(splitExpression));
		return answer;
	}
	
	/**
	 * 19. Given a number determine whether or not it is valid per the Luhn formula.
	 * 
	 * The Luhn algorithm is a simple checksum formula used to validate a variety of
	 * identification numbers, such as credit card numbers and Canadian Social
	 * Insurance Numbers.
	 * 
	 * The task is to check if a given string is valid.
	 * 
	 * Validating a Number Strings of length 1 or less are not valid. Spaces are
	 * allowed in the input, but they should be stripped before checking. All other
	 * non-digit characters are disallowed.
	 * 
	 * Example 1: valid credit card number 1 4539 1488 0343 6467 The first step of
	 * the Luhn algorithm is to double every second digit, starting from the right.
	 * We will be doubling
	 * 
	 * 4_3_ 1_8_ 0_4_ 6_6_ If doubling the number results in a number greater than 9
	 * then subtract 9 from the product. The results of our doubling:
	 * 
	 * 8569 2478 0383 3437 Then sum all of the digits:
	 * 
	 * 8+5+6+9+2+4+7+8+0+3+8+3+3+4+3+7 = 80 If the sum is evenly divisible by 10,
	 * then the number is valid. This number is valid!
	 * 
	 * Example 2: invalid credit card number 1 8273 1232 7352 0569 Double the second
	 * digits, starting from the right
	 * 
	 * 7253 2262 5312 0539 Sum the digits
	 * 
	 * 7+2+5+3+2+2+6+2+5+3+1+2+0+5+3+9 = 57 57 is not evenly divisible by 10, so
	 * this number is not valid.
	 * 
	 * @param string
	 * @return
	 */
	
	public boolean isLuhnValid(String string) {
		// TODO Write an implementation for this method declaration
		List<Integer> intArray = new ArrayList<Integer>();
		
		if (string.matches(".*[a-z-].*")) { //check is valid string before continuing
		    //System.out.println("this string contains a alphabet or - ");
		    return false;
		}
		
		for (int i = 0; i < string.length(); ++i) { //build the digits pool
			char p = string.charAt(i);
			if (Character.isDigit(p)) {
				intArray.add((Character.getNumericValue(p)));
			}
		}
		System.out.println(intArray);
		int sumTotal = 0;
		for (int k = 0; k < intArray.size(); ++k) {
			if ((k+1) % 2 == 0) { // index of 0 is 1 for modulo calculations
				int testNum = intArray.get(k)*2;
				if (testNum > 9) {
					sumTotal = sumTotal + testNum - 9;
				}
				else {
					sumTotal = sumTotal + testNum;
				}
			}
			else {
				sumTotal = sumTotal + intArray.get(k);
			}
		}
		
		if (sumTotal % 10 == 0) {
			return true;
		}
		else {
			return false;
		}
		
	}

	/**
	 * 18. Given a number, find the sum of all the unique multiples of particular
	 * numbers up to but not including that number.
	 * 
	 * If we list all the natural numbers below 20 that are multiples of 3 or 5, we
	 * get 3, 5, 6, 9, 10, 12, 15, and 18.
	 * 
	 * The sum of these multiples is 78.
	 * 
	 * @param i
	 * @param set
	 * @return
	 */
	
	public int getSumOfMultiples(int i, int[] set) {
		// TODO Write an implementation for this method declaration
		int ceiling = i;
		Set<Integer> setMultiples = new HashSet<>();  // will store all multiples - unique
		for (int s : set) {
		    for (int k = 1; k <10000; ++k) { // make k limit arbitrarily big
		    	if (s*k < ceiling) {
		    		setMultiples.add(s*k);  // only unique members added
		    	}
		    }
		}
		int memberSetSum = 0;  // start with member
		int totalSum = 0;
		
		// loop through new Set of multiples and add
		for (Integer getMult: setMultiples) {
			totalSum = totalSum + getMult;
		}
		return totalSum;
	}
	
	public Temporal getGigasecondDate(Temporal given) {
		// TODO Write an implementation for this method declaration
		return null;
	}
	/**
	 * 16. Determine if a sentence is a pangram. A pangram (Greek: , pan
	 * gramma, "every letter") is a sentence using every letter of the alphabet at
	 * least once. The best known English pangram is:
	 * 
	 * The quick brown fox jumps over the lazy dog.
	 * 
	 * The alphabet used consists of ASCII letters a to z, inclusive, and is case
	 * insensitive. Input will not contain non-ASCII symbols.
	 * 
	 * @param string
	 * @return 
	 */
	
	
	public boolean isPangram(String string) {
		// TODO Write an implementation for this method declaration
		String alphabet = "abcdefghijklmnopqurstuvwxyz";
		boolean pangram = true;
		for (int i = 0; i < alphabet.length(); i++) { //loop through characters of the word
			char c = alphabet.charAt(i);  //go through the alphabet
			String cString = string.valueOf(c);
			if (!string.contains(cString)){
				pangram = false;			
			}
		}
		return pangram;
	}
	
	/**
	 * 15. The ISBN-10 verification process is used to validate book identification
	 * numbers. These normally contain dashes and look like: 3-598-21508-8
	 * 
	 * ISBN The ISBN-10 format is 9 digits (0 to 9) plus one check character (either
	 * a digit or an X only). In the case the check character is an X, this
	 * represents the value '10'. These may be communicated with or without hyphens,
	 * and can be checked for their validity by the following formula:
	 * 
	 * (x1 * 10 + x2 * 9 + x3 * 8 + x4 * 7 + x5 * 6 + x6 * 5 + x7 * 4 + x8 * 3 + x9
	 * * 2 + x10 * 1) mod 11 == 0 If the result is 0, then it is a valid ISBN-10,
	 * otherwise it is invalid.
	 * 
	 * Example Let's take the ISBN-10 3-598-21508-8. We plug it in to the formula,
	 * and get:
	 * 
	 * (3 * 10 + 5 * 9 + 9 * 8 + 8 * 7 + 2 * 6 + 1 * 5 + 5 * 4 + 0 * 3 + 8 * 2 + 8 *
	 * 1) mod 11 == 0 Since the result is 0, this proves that our ISBN is valid.
	 * 
	 * @param string
	 * @return
	 */
	
	public boolean isValidIsbn(String string) {
		// TODO Write an implementation for this method declaration
		List<Integer> isDigit = new ArrayList<Integer>();
		
		for (int i = 0; i < string.length(); ++i) { //build the digits pool
			char p = string.charAt(i);
			if (Character.isDigit(p)) {
				isDigit.add((Character.getNumericValue(p)));
			}			
		}
//		 isDigit.forEach(n ->{
//        System.out.println(isDigit);
//    });
//System.out.println(isDigit);
		
		if (string.contains("X")) { //assuming X always in last spot 
			isDigit.add(10);
		}
		
		int sum = 0;
		int factor = 1;
		ListIterator<Integer> listIterator = isDigit.listIterator(isDigit.size());  //ListIterator to traverse backwards
		while (listIterator.hasPrevious()) {
			sum = sum+listIterator.previous()*factor++;
		}
		if (sum % 11 == 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * 13 & 14. Create an implementation of the atbash cipher, an ancient encryption
	 * system created in the Middle East.
	 * 
	 * The Atbash cipher is a simple substitution cipher that relies on transposing
	 * all the letters in the alphabet such that the resulting alphabet is
	 * backwards. The first letter is replaced with the last letter, the second with
	 * the second-last, and so on.
	 * 
	 * An Atbash cipher for the Latin alphabet would be as follows:
	 * 
	 * Plain: abcdefghijklmnopqrstuvwxyz Cipher: zyxwvutsrqponmlkjihgfedcba It is a
	 * very weak cipher because it only has one possible key, and it is a simple
	 * monoalphabetic substitution cipher. However, this may not have been an issue
	 * in the cipher's time.
	 * 
	 * Ciphertext is written out in groups of fixed length, the traditional group
	 * size being 5 letters, and punctuation is excluded. This is to make it harder
	 * to guess things based on word boundaries.
	 * 
	 * Examples Encoding test gives gvhg Decoding gvhg gives test Decoding gsvjf
	 * rxpyi ldmul cqfnk hlevi gsvoz abwlt gives thequickbrownfoxjumpsoverthelazydog
	 *
	 */
	
	static class AtbashCipher {

		/**
		 * Question 13
		 * 
		 * @param string
		 * @return
		 */
		public static String encode(String string) {
			// TODO Write an implementation for this method declaration
			String encodeStr = " ";
			String lowerCaseStr = string.replaceAll("[^a-zA-Z0-9]","").toLowerCase(); //turn string to lower case and get rid of special characters
			System.out.println("the clean string is "+lowerCaseStr);
			String alphabetKey = "abcdefghijklmnopqrstuvwxyz1234567890";
			String alphabetValue = "zyxwvutsrqponmlkjihgfedcba1234567890";
			HashMap<String,String> encodeMap = new HashMap<String,String>();
			// build the hashmap key value set to match each character to their code
			for (int i = 0; i < alphabetKey.length();++i) {
				encodeMap.put(alphabetKey.substring(i, i+1), alphabetValue.substring(i, i+1));
			}

			for (int j = 0; j<lowerCaseStr.length(); ++j) {
				String keyString = lowerCaseStr.substring(j, j+1);
				System.out.println(" keyString is "+keyString);
				String mapChar = encodeMap.get(keyString);
				encodeStr = encodeStr+mapChar;
			}
			String noSpaces = encodeStr.trim();
			String strEncode = " ";
			if (noSpaces.length() <= 4) {
				return noSpaces;
			}
			else {
				int charBlocks = 5;
				
				if (noSpaces.length() <= charBlocks) {  // word is less than block of 5 char
				}
				else {
					int remainder = noSpaces.length() % charBlocks;  // remainder block of characters
					int loops = 0;
					if (remainder == 0) {
						loops = noSpaces.length()/charBlocks;  // loop number of multiple of 5 block characters
					}
					else {
						loops = noSpaces.length()/charBlocks+1; // need the last loop to capture last few characters not in a 5 block of characters
					}
					int n = 1;
					int p = 0;
					String blank = " ";
					while (n <= loops) {

						if ( (loops == n) && (remainder != 0)) {
							// last loop check remainder
							strEncode = strEncode +blank+ noSpaces.substring(p,p+remainder);
						}
						else {
							strEncode = strEncode +blank+ noSpaces.substring(p,p+charBlocks);
							p = p+charBlocks;
						}
						++n;

					}
				}
			}

			return strEncode.trim();
		}

		/**
		 * Question 14
		 * 
		 * @param string
		 * @return
		 */
		public static String decode(String string) {
			// TODO Write an implementation for this method declaration
			String encodeStr = " ";
			String lowerCaseStr = string.replaceAll("[^a-zA-Z0-9]","").toLowerCase(); //turn string to lower case and get rid of special characters
			System.out.println("the clean string is "+lowerCaseStr);
			String alphabetKey = "abcdefghijklmnopqrstuvwxyz1234567890";
			String alphabetValue = "zyxwvutsrqponmlkjihgfedcba1234567890";
			HashMap<String,String> encodeMap = new HashMap<String,String>();
			// build the hashmap key value set to match each character to their code
			for (int i = 0; i < alphabetKey.length();++i) {
				encodeMap.put(alphabetKey.substring(i, i+1), alphabetValue.substring(i, i+1));
			}

			for (int j = 0; j<lowerCaseStr.length(); ++j) {
				String keyString = lowerCaseStr.substring(j, j+1);
				System.out.println(" keyString is "+keyString);
				String mapChar = encodeMap.get(keyString);
				encodeStr = encodeStr+mapChar;
			}
			return encodeStr.trim();
		}
	}
	
	/**
	 * 12. Given a number n, determine what the nth prime is.
	 * 
	 * By listing the first six prime numbers: 2, 3, 5, 7, 11, and 13, we can see
	 * that the 6th prime is 13.
	 * 
	 * If your language provides methods in the standard library to deal with prime
	 * numbers, pretend they don't exist and implement them yourself.
	 * 
	 * @param i
	 * @return
	 */
	
	
	public int calculateNthPrime(int i) {
		// TODO Write an implementation for this method declaration
		if (i == 0) { // no prime at position 0 - for test case
			throw new IllegalArgumentException();
		}
		int nthPrime = 0;
		int g = 1;
		while (nthPrime < i) {
			int flag = 1;
			if ((g > 2) && (g % 2 == 0)) {
				flag = 0; // not a prime
			}
			else {
				for (int k = 3; k < (g/2); k=k+2) {
					if (g % k == 0 ) {
						flag = 0; //not a prime
						break;
					}

				}
			}
			if ((flag == 1) & (g >= 2)) {
				nthPrime = nthPrime+1;
				//System.out.println("prime found is "+i);
			}
			++g;
		}
		return g-1; //because g was incremented at the end in order to check while loop so answer is g-1
	}
	/**
	 * 11. Create an implementation of the rotational cipher, also sometimes called
	 * the Caesar cipher.
	 * 
	 * The Caesar cipher is a simple shift cipher that relies on transposing all the
	 * letters in the alphabet using an integer key between 0 and 26. Using a key of
	 * 0 or 26 will always yield the same output due to modular arithmetic. The
	 * letter is shifted for as many values as the value of the key.
	 * 
	 * The general notation for rotational ciphers is ROT + <key>. The most commonly
	 * used rotational cipher is ROT13.
	 * 
	 * A ROT13 on the Latin alphabet would be as follows:
	 * 
	 * Plain: abcdefghijklmnopqrstuvwxyz Cipher: nopqrstuvwxyzabcdefghijklm It is
	 * stronger than the Atbash cipher because it has 27 possible keys, and 25
	 * usable keys.
	 * 
	 * Ciphertext is written our in the same formatting as the input including
	 * spaces and punctuation.
	 * 
	 * Examples: ROT5 omg gives trl ROT0 c gives c ROT26 Cool gives Cool ROT13 The
	 * quick brown fox jumps over the lazy dog. gives Gur dhvpx oebja sbk whzcf bire
	 * gur ynml qbt. ROT13 Gur dhvpx oebja sbk whzcf bire gur ynml qbt. gives The
	 * quick brown fox jumps over the lazy dog.
	 */

	static class RotationalCipher {
		private int key;

		public RotationalCipher(int key) {
			super();
			this.key = key;
		}

		public String rotate(String string) {
			// TODO Write an implementation for this method declaration
			String alphaLet = "abcdefghijklmnopqrstuvwxyz";
			String upperLet = alphaLet.toUpperCase();
	//		System.out.println("upper case alphabet is "+upperLet);
//			char[] lowerAlpha = new String(alphaLet).toCharArray();
//			char[] upperAlpha = new String(alphaLet.toUpperCase()).toCharArray();
			String cipher = " ";
			System.out.println("key is "+key+" length of alphabet "+alphaLet.length());

			for (char c : string.toCharArray()) {
				if (Character.isUpperCase(c)) {
					cipher = cipher+cipheredLetter(upperLet,key,c);
				}
				else if (Character.isLowerCase(c)) {
					cipher = cipher+cipheredLetter(alphaLet,key,c);
				}
				else {
					cipher = cipher+c;
				}
			}

			return cipher.trim();
		}


	}
	
	public static String cipheredLetter(String alphabet, int k, char ch) {
		int index = alphabet.indexOf(ch)+1;
		int shiftedTo = index+k;
		if (shiftedTo > 26) {
			shiftedTo = (shiftedTo % 26)-1;
			System.out.println("shifted key is "+shiftedTo);
		}
		else {
			shiftedTo = shiftedTo-1;
		}
		String extractedString = alphabet.substring(shiftedTo,shiftedTo+1);
		return extractedString;
	}
		

//	static class BinarySearch<T> {
//		interface Comparable<T>{
//			public int compareTo(T o);
//		}
//		private List<T> sortedList;
//
//		public int indexOf(T t) {
//			// TODO Write an implementation for this method declaration
////			int located = sortedList.indexOf(t);
////			System.out.println("located index is "+located);
//			
//			 int l = 0, r = sortedList.size() - 1; 
//		        while (l <= r) { 
//		            int m = l + (r - l) / 2; 
//		  
//		            // Check if x is present at mid 
//		            if (sortedList.get(m) == t) {
//		            	return m;
//		            }	  
//		            // If x greater, ignore left half 
//		            if ((sortedList.get(m)).compareTo(t)> 0) {
//		            	l = m + 1; 
//		            }
//
//		            else
//		                r = m - 1; 
//		        } 
//		 
//		        return -1; 
//			
//		}
//
//		public BinarySearch(List<T> sortedList) {
//			super();
//			this.sortedList = sortedList;
//		}
//
//		public List<T> getSortedList() {
//			return sortedList;
//		}
//
//		public void setSortedList(List<T> sortedList) {
//			this.sortedList = sortedList;
//		}
//
//	}

	/**
	 * 10. Compute the prime factors of a given natural number.
	 * 
	 * A prime number is only evenly divisible by itself and 1.
	 * 
	 * Note that 1 is not a prime number.
	 * 
	 * @param l
	 * @return
	 */
		
		
	
	public List<Long> calculatePrimeFactorsOf(long l) {
		// TODO Write an implementation for this method declaration
		long n = l;  // easier to see n instead l in statements
		List<Long> pList = new ArrayList<Long>();
		List<Long> factorList = new ArrayList<Long>();
		MyEvaluationService getListPrimes = new MyEvaluationService();
		pList = getListPrimes.getPrimes();
		
		boolean primeFlag = false;
		
		while ((n % 2 == 0) && (n/2 > 0)){ // for even numbers start this first in order to get an odd factor
			factorList.add(2l);// keeping pushing 2s into the prime factor list until an odd number emerges
			n = n/2;
			if (n == 1l) { //case where last "2" factor was found
				primeFlag = true; 
			}
//			System.out.println("n is "+n);
//			System.out.println("pushed 2 in");
		} // outside the loop then an odd number emerges - check it
		
		
		if (pList.contains(n)) { // check if odd factor to prime pool
				factorList.add(n);  //add it to the prime factor list
				primeFlag = true;  // if prime then set true
		}
		
		
		if (!primeFlag) { // still odd number but prime not found - find the divisors and check those
			boolean foundOne = false;
			boolean foundTwo = false;
			while (!foundTwo) { // stop when the last two remaining prime factors are found
				Long midPoint = n/2; 
				Long maybePrime1 = 0L;
				Long maybePrime2 = 0L;
				for (Long i = 3L; i < midPoint ;i=i+2) { // divide only by odd numbers since odd number to begin with
					if (n % i == 0) { // two potential prime factors found
						maybePrime1 = i;  // check if odd numbers are prime
						maybePrime2 = n/i;
						break; // stop loop to check the odd numbers
					}
				}
				if (pList.contains(maybePrime1) & pList.contains(maybePrime2)) { // found last 2 odd factor primes
					factorList.add(maybePrime1);
					factorList.add(maybePrime2);
					foundOne = true;
					foundTwo = true;
				}
				else if (pList.contains(maybePrime1)) { // found only one, the higher odd number is not a prime
					factorList.add(maybePrime1);
					n = maybePrime2;  // set n to the higher odd number that isn't a prime
					foundOne = true;
				}
			}
		}
		
//		for (int j = 0; j < factorList.size(); ++j) {
//			System.out.println("factors are: "+factorList.get(j));
//		}
		return factorList;
	}
	
//	public boolean checkIfPrime(Long factor, List<Integer> primeList) {
//		return primeList.contains(factor) ? true : false;	// test ternary operator
//	}
	
	public List<Long> getPrimes(){
		List<Long> primeList = new ArrayList<Long>();
		for (Long i = 2L; i<1000; ++i) {
			int flag = 1;
			if (i % 2 == 0) {
				flag = 0; // not a prime
			}
			else {
				for (Long k = 3L; k < (i/2); k=k+2) {
					if (i % k == 0 ) {
						flag = 0; //not a prime
					}
					
				}
			}
			if (flag == 1) {
				primeList.add(i);
				//System.out.println("prime found is "+i);
			}
		}
		return primeList;
	}
	   
	
	
	/**
	 * 9. An Armstrong number is a number that is the sum of its own digits each
	 * raised to the power of the number of digits.
	 * 
	 * For example:
	 * 
	 * 9 is an Armstrong number, because 9 = 9^1 = 9 10 is not an Armstrong number,
	 * because 10 != 1^2 + 0^2 = 2 153 is an Armstrong number, because: 153 = 1^3 +
	 * 5^3 + 3^3 = 1 + 125 + 27 = 153 154 is not an Armstrong number, because: 154
	 * != 1^3 + 5^3 + 4^3 = 1 + 125 + 64 = 190 Write some code to determine whether
	 * a number is an Armstrong number.
	 * 
	 * @param input
	 * @return
	 */
	
	public boolean isArmstrongNumber(int input) {
		// TODO Write an implementation for this method declaration
		int countDigits = 0;
		int copyInput = input;
		while (copyInput != 0) {
			copyInput = copyInput/10;
			++countDigits;
		}
		//System.out.println("count digits is "+countDigits);
		
		int sum = 0;
		int extractDigits = 0;
		int copyInputAgain = input;
		for (int i = 1; i <= countDigits; ++i) {
			extractDigits = input % 10;
		//System.out.println("the extracted digits are "+extractDigits);
			sum = sum + (int)(Math.pow(extractDigits,countDigits));
			//System.out.println("the sum is "+sum);
			input = input/10;
		}
		if (sum == copyInputAgain ) {
			return true;
		}
		else {
			return false;
		}
				
	}
	/**
	 * 8. Implement a program that translates from English to Pig Latin.
	 * 
	 * Pig Latin is a made-up children's language that's intended to be confusing.
	 * It obeys a few simple rules (below), but when it's spoken quickly it's really
	 * difficult for non-children (and non-native speakers) to understand.
	 * 
	 * Rule 1: If a word begins with a vowel sound, add an "ay" sound to the end of
	 * the word. Rule 2: If a word begins with a consonant sound, move it to the end
	 * of the word, and then add an "ay" sound to the end of the word. There are a
	 * few more rules for edge cases, and there are regional variants too.
	 * 
	 * See http://en.wikipedia.org/wiki/Pig_latin for more details.
	 * 
	 * @param string
	 * @return
	 */
	
	public String toPigLatin(String string) {
		String[] eachWord = string.split(" ");
		String result = " ";
		String buildString = " ";
		for (String s: eachWord) {
			MyEvaluationService callPigLatin = new MyEvaluationService();
			result = callPigLatin.pigLatinOneWordAtTime(s)+" ";
			buildString = buildString+result;
		}
		return buildString.trim(); //trim off leading blanks
	}

	public String pigLatinOneWordAtTime(String string ) {
		// TODO Write an implementation for this method declaration
		char[] vowelarray = new String("aeiou").toCharArray();
		char firstChar = string.charAt(0);
		String pigLatin = " ";
		int firstVolwel = 100; // make this arbitrarily large
		for (int i = 0; i < string.length(); i++) { //loop through characters of the word
			char c = string.charAt(i);
			for (char v : vowelarray) {  
				if (c == v) {
					if (i < firstVolwel ) {
						firstVolwel = i;  // only store the lowest index of volwel found
					}
				}
			}
		}


		int index = firstVolwel;
		//System.out.println("index of first volwel is "+index);
		int lastIndex = string.length();
		if (index == 0) {
		//	System.out.println("in index 0");
			pigLatin = string+"ay";
		}
		else if (index == 1) {
		//	System.out.println("in index 1");
			if (string.charAt(0) == 'q') { //check special condition where q follows the u
				pigLatin = string.substring(index+1, lastIndex)+firstChar+"uay";
			}
			else {
				pigLatin = string.substring(index, lastIndex)+firstChar+"ay";
			}
		}
		else if (index > 1) {
		//	System.out.println("in index "+index);
			pigLatin = string.substring(index, lastIndex)+string.substring(0, index)+"ay";
		}

		return pigLatin;
	}
	
	

	
	public Map<String, Integer> wordCount(String string) {
		// TODO Write an implementation for this method declaration
		// declare the output hashmap to eventually set key value pairs
		Map<String,Integer> tallyWords = new HashMap<String,Integer>();

		//split the string into separate words
		//use custom regex delimiters to delimit on non-alphacharacters
		final String delimit = "[^a-zA-Z]";
		String[] parseWords = string.split(delimit);		

//		int count = 0;
//		for (int k = 0; k < parseWords.length;++k) {
//			if( !parseWords[k].isEmpty()) {
//				++count;
//			};
//		}
//		if (count >0) // make sure elements to remove before declaring the new string array
//		{
//			String[] validWords = new String[count]; // need to initialize it 
//			int validWordIndex = 0;
//			for (int j = 0; j < parseWords.length;++j) {
//				if( !parseWords[j].isEmpty()) {
//					System.out.println("parse words is "+parseWords[j]);
//					validWords[validWordIndex++] = parseWords[j];
//					//System.out.println(" valid word is "+validWords[validWordIndex]);
//				};
//
//			}
//		}
		// find unique words by putting the string array into a hashset
		Set<String> uniqueWords = new HashSet<String>();

		Collections.addAll(uniqueWords, parseWords);
		for (String s : uniqueWords) {
			//System.out.println("unique word is "+s);
			int countWord = 0;
			for (int i = 0; i < parseWords.length;++i) {
				if (s.equals(parseWords[i])) {
					++countWord;
				}

			}
			// create the hashmap of unique words and their count using key value pairs
			tallyWords.put(s, countWord);
		}
		return tallyWords;


	}

	/**
	 * 5. Clean up user-entered phone numbers so that they can be sent SMS messages.
	 * 
	 * The North American Numbering Plan (NANP) is a telephone numbering system used
	 * by many countries in North America like the United States, Canada or Bermuda.
	 * All NANP-countries share the same international country code: 1.
	 * 
	 * NANP numbers are ten-digit numbers consisting of a three-digit Numbering Plan
	 * Area code, commonly known as area code, followed by a seven-digit local
	 * number. The first three digits of the local number represent the exchange
	 * code, followed by the unique four-digit number which is the subscriber
	 * number.
	 * 
	 * The format is usually represented as
	 * 
	 * 1 (NXX)-NXX-XXXX where N is any digit from 2 through 9 and X is any digit
	 * from 0 through 9.
	 * 
	 * Your task is to clean up differently formatted telephone numbers by removing
	 * punctuation and the country code (1) if present.
	 * 
	 * For example, the inputs
	 * 
	 * +1 (613)-995-0253 613-995-0253 1 613 995 0253 613.995.0253 should all produce
	 * the output
	 * 
	 * 6139950253
	 * 
	 * Note: As this exercise only deals with telephone numbers used in
	 * NANP-countries, only 1 is considered a valid country code.
	 */
	public  String cleanPhoneNumber(String string)  {
		// TODO Write an implementation for this method declaration
		String s = string.replaceAll("[^a-zA-Z0-9@!]","");
		//System.out.println("s is "+s);
		if (s.length() > 10 || s.length() < 7) {
			throw new IllegalArgumentException();
		}
		else {
			for (int i = 0; i < s.length() -1 ; ++i) {
				if (!Character.isDigit(s.charAt(i))) {
					throw new IllegalArgumentException(); 
				};
			}
		}
		return s;
	}


	/**
	 * 4. Given a word, compute the scrabble score for that word.
	 * 
	 * --Letter Values-- Letter Value A, E, I, O, U, L, N, R, S, T = 1; D, G = 2; B,
	 * C, M, P = 3; F, H, V, W, Y = 4; K = 5; J, X = 8; Q, Z = 10; Examples
	 * "cabbage" should be scored as worth 14 points:
	 * 
	 * 3 points for C, 1 point for A, twice 3 points for B, twice 2 points for G, 1
	 * point for E And to total:
	 * 
	 * 3 + 2*1 + 2*3 + 2 + 1 = 3 + 2 + 6 + 3 = 5 + 9 = 14
	 * 
	 * @param string
	 * @return
	 */

	public int getScrabbleScore(String string) {
		// TODO Write an implementation for this method declaration
		string = string.toUpperCase();
		int points = 0;


		for (int i = 0; i < string.length(); i++) {
			char c = string.charAt(i);
			switch (c) {
			case 'A':
			case 'E':
			case 'I':
			case 'O':
			case 'U':
			case 'L':
			case 'N':
			case 'R':
			case 'S':
			case 'T':
				points = points + 1;
				break;
			case 'D':
			case 'G':
				points = points + 2;
				break;
			case 'B':
			case 'C':
			case 'M':
			case 'P':
				points = points + 3;
				break;
			case 'F':
			case 'H':
			case 'V':
			case 'W':
			case 'Y':
				points = points + 4;
				break;
			case 'K':
				points = points + 5;
				break;
			case 'J':
			case 'X':
				points = points + 8;
				break;
			case 'Q':
			case 'Z':
				points = points + 10;
				break;

			}
		}
		return points;
	}




	/**
	 * 1. Without using the StringBuilder or StringBuffer class, write a method that
	 * reverses a String. Example: reverse("example"); -> "elpmaxe"
	 * 
	 * @param string
	 * @return
	 */

	public String reverse(String string) {
		char[] reversed = new char[string.length()];
		for (int i = reversed.length - 1, j = 0; i >= 0; i--, j++) {
			reversed[j] = string.charAt(i);
		}
		return new String(reversed);
	}

	/**
	 * 2. Convert a phrase to its acronym. Techies love their TLA (Three Letter
	 * Acronyms)! Help generate some jargon by writing a program that converts a
	 * long name like Portable Network Graphics to its acronym (PNG).
	 * 
	 * @param phrase
	 * @return
	 */
	public String acronym(String phrase) {
		// TODO Write an implementation for this method declaration
		String s;
		String acrn;
		s = phrase;
		// search and replace special characters but leave comma alone
		s = s.replaceAll("[^a-zA-Z0-9,]"," ");
		//System.out.println("s removed special is "+s);
		acrn = s.substring(0, 1);
		int space;
		boolean flag = true;
		while (flag) 
		{
			space = s.indexOf(" ");
			if (space > 0) {
				acrn = acrn + s.substring(space+1, space+2);
				s = s.substring(space+1);			
			}
			else {
				flag = false;
			}			
		}

		return acrn.toUpperCase();
	}

	/**
	 * 3. Determine if a triangle is equilateral, isosceles, or scalene. An
	 * equilateral triangle has all three sides the same length. An isosceles
	 * triangle has at least two sides the same length. (It is sometimes specified
	 * as having exactly two sides the same length, but for the purposes of this
	 * exercise we'll say at least two.) A scalene triangle has all sides of
	 * different lengths.
	 *
	 */

	static class Triangle {
		private double sideOne;
		private double sideTwo;
		private double sideThree;

		public Triangle() {
			super();
		}

		public Triangle(double sideOne, double sideTwo, double sideThree) {
			this();
			this.sideOne = sideOne;
			this.sideTwo = sideTwo;
			this.sideThree = sideThree;
		}

		public double getSideOne() {
			return sideOne;
		}

		public void setSideOne(double sideOne) {
			this.sideOne = sideOne;
		}

		public double getSideTwo() {
			return sideTwo;
		}

		public void setSideTwo(double sideTwo) {
			this.sideTwo = sideTwo;
		}

		public double getSideThree() {
			return sideThree;
		}

		public void setSideThree(double sideThree) {
			this.sideThree = sideThree;
		}

		public boolean isEquilateral() {
			// TODO Write an implementation for this method declaration
			System.out.println("side 1 is "+this.getSideOne()+" side 2 is "+this.getSideTwo()+" side 3 is "+this.getSideThree());
			if ((Math.abs(this.getSideOne() - this.getSideTwo()) <= 0.0001) && (Math.abs(this.getSideTwo() - this.getSideThree()) <= 0.0001)) {
				return true;
			} else {
				return false;
			}
		}

		public boolean isIsosceles() {
			// TODO Write an implementation for this method declaration
			if ((Math.abs(this.getSideOne() - this.getSideTwo()) <= 0.0001) || (Math.abs(this.getSideTwo() - this.getSideThree()) <= 0.0001) || (Math.abs(this.getSideOne() - this.getSideThree()) <= 0.0001)) {
				return true;
			} else {
				return false;
			}
		}

		public boolean isScalene() {
			// TODO Write an implementation for this method declaration
			if (!(Math.abs(this.getSideOne() - this.getSideTwo()) <= 0.0001) & !(Math.abs(this.getSideTwo() - this.getSideThree()) <= 0.0001) & !(Math.abs(this.getSideOne() - this.getSideThree()) <= 0.0001)) {
				return true;
			} else {
				return false;
			}
		}

	}

}
