package com.revature.eval.java.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MyEvaluationService {


	public static void main(String[] args) {
		// TODO Auto-generated method stub


//		List<Integer> testList = new ArrayList<Integer>();
//		testList.add(1);
//		testList.add(3);
//		testList.add(5);
//		testList.add(7);
//		testList.add(9);
//		
//		int position = MyEvaluationService.BinarySearch<T>.IndexOf(testList); 
		
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
	

		
		
		
	}	
	
	
	public boolean isArmstrongNumber(int input) {
		// TODO Write an implementation for this method declaration
		return false;
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
		System.out.println("index of first volwel is "+index);
		int lastIndex = string.length();
		if (index == 0) {
			System.out.println("in index 0");
			pigLatin = string+"ay";
		}
		else if (index == 1) {
			System.out.println("in index 1");
			if (string.charAt(0) == 'q') { //check special condition where q follows the u
				pigLatin = string.substring(index+1, lastIndex)+firstChar+"uay";
			}
			else {
				pigLatin = string.substring(index, lastIndex)+firstChar+"ay";
			}
		}
		else if (index > 1) {
			System.out.println("in index "+index);
			pigLatin = string.substring(index, lastIndex)+string.substring(0, index)+"ay";
		}

		return pigLatin;
	}
	
	
	static class BinarySearch<T> {
		private List<T> sortedList;

		public int indexOf(T t) {
			// TODO Write an implementation for this method declaration
			return 0;
		}

		public BinarySearch(List<T> sortedList) {
			super();
			this.sortedList = sortedList;
		}

		public List<T> getSortedList() {
			return sortedList;
		}

		public void setSortedList(List<T> sortedList) {
			this.sortedList = sortedList;
		}

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
		System.out.println("s is "+s);
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
			if (this.getSideOne() == this.getSideTwo() && this.getSideTwo() == this.getSideThree()) {
				return true;
			} else {
				return false;
			}
		}

		public boolean isIsosceles() {
			// TODO Write an implementation for this method declaration
			if (this.getSideOne() == this.getSideTwo() || this.getSideTwo() == this.getSideThree() || this.getSideOne() == this.getSideThree()) {
				return true;
			} else {
				return false;
			}
		}

		public boolean isScalene() {
			// TODO Write an implementation for this method declaration
			if (this.getSideOne() != this.getSideTwo() & this.getSideTwo() != this.getSideThree() & this.getSideOne() != this.getSideThree()) {
				return true;
			} else {
				return false;
			}
		}

	}

}
