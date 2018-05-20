/**
 * 
 */
package org.bawaweb;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * This class will attempt to identify the smallest substring from an input of 
 *         1 a large string 
 *         2 a token string containing target characters ---- I am assuming the characters here are non repeating
 *         
 *         Mechanism used to identify the substrings:
 *         Set up booleans
 *         Create List from string of tokens - called 'tokens'
 *         Iterate through the large string one character at a time
 *           If an element exists in 'tokens'
 *           		-- if it is first occurrence of a match found in the list then identify startIndex for the substring
 *           Remove the element from the 'tokens'
 *           		-- if the size of the tokens is zero, then identify the lastIndex for the substring
 *         keep a track of the smallest substring as one continues
 *             				
 */
public class IdentifySmallestSubstring {

	private boolean isFirstIndexIdentified = false;

	private int startIndex;

	private int lastIndex;

	private void setStartPosition(int i) {
		if (!isFirstIndexIdentified) {
			startIndex = i;
		}
	}

	private List<Character> getTokenChararcters(String tokenString) {
		final List<Character> tokenList = new ArrayList<Character>();
		char[] characters = tokenString.toCharArray();
		for (char aChar : characters) {
			tokenList.add(aChar);
		}
		return tokenList;
	}


	public String getSmallestSubstring(final String largeString, final String tokenString) {
		System.out.println("L = " + largeString);
		System.out.println("t = " + tokenString);

		String smallestSubstring = null;

		List<Character> tokens = getTokenChararcters(tokenString);

		for (int i = 0; i < largeString.length(); i++) {
			char charVal = largeString.charAt(i);

			if (tokens.contains(charVal)) {

				setStartPosition(i);

				isFirstIndexIdentified = true;
				int charValIndex = tokens.indexOf(charVal);
				tokens.remove(charValIndex);

				if (tokens.isEmpty()) {
					lastIndex = i;
					tokens = getTokenChararcters(tokenString);
					isFirstIndexIdentified = false;

					String subString = (String) largeString.subSequence(
							startIndex, lastIndex + 1);
					System.out.println("Found substring >>" + subString
							+ "<< at index = " + i);

					if (smallestSubstring == null) {
						smallestSubstring = subString;
					} else {
						smallestSubstring = (smallestSubstring.length() <= subString .length()) ? 
								smallestSubstring : subString;
					}
				}

			}
		}

		return smallestSubstring;
	}
	
	public static void main(String[] args) {
		IdentifySmallestSubstring iss = new IdentifySmallestSubstring();
		String L1 = "one very long characters of letters here ad infinitum";
		String T2 = "ner";
		System.out.println("Smallest substring is " + iss.getSmallestSubstring(L1, T2));

	}

}
