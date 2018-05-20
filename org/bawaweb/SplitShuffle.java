/**
 * 
 */
package org.bawaweb;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * @author NMedhora
 * Given-a-deck-of-cards
 * how-many-times-to-split=interleave=shuffle-a-deck-of-cards
 * to-get-same-order
 * 
 * 
 * via-iteration	(n-is-always-a-multiple-of-2)
 * row	n		num-iterations-to-restore
 * 1	2	==>	1
 * 2	4	==>	2
 * 3	6	==>	4
 * 4	8	==>	3
 * 5	10	==>	6
 * 6	12	==>	10
 * 
 * a-b-c-d							a-b-c-d-e-f-g-h
 * 1								1
 * a-b==c-d							a-b-c-d==e-f-g-h				split
 * a-c-b-d							a-e-b-f-c-g-d-h					shuffle
 * 2								2
 *  * a-c==b-d						a-e-b-f==c-g-d-h
 * a-b-c-d		4->2				a-c-e-g-b-d-f-h
 * ============================		3
 * 									a-c-e-g==b-d-f-h
 * 									a-b-c-d-e-f-g-h		8->3		
 * 									===================================
 *
 */



public class SplitShuffle {


	public static void main(String[] args) {
		SplitShuffle ss = new SplitShuffle();
		
		// using LinkedHashMap to preserve key insertion order
		LinkedHashMap<Integer,Integer> dmap = new LinkedHashMap<Integer,Integer>();
		
		final int size = 52;		
		int dsize = 2;	// making generic 
		
		 while(dsize <= size) {
			int[] deck = new int[dsize];
			for (int i = 0; i < dsize; i++) {
				deck[i] = i;
			}
			int count = 1;
			int[][] splits = ss.split(deck);
			int[] shuffle = ss.shuffle(splits[0], splits[1]);
			while (!ss.isIdentical(deck, shuffle)) {//System.out.println("count is " + count);
				splits = ss.split(shuffle);
				shuffle = ss.shuffle(splits[0], splits[1]);
				count++;
			}
//			System.out.println("For a deck of size " + dsize + " ==> ["+ ss.printArray(deck) + "]");
//			System.out.println("There must be " + count + " split-interleave-shuffles to restore back to order\n_______________________________________");
			
			dmap.put(dsize, count);
			dsize += 2;
		};
		
		System.out.println("\n==========================================================\n");
		System.out.println(ss.printMap(dmap));
		
	}

	private int[][] split(int[] anArray) {
		if (anArray.length==0||anArray.length%2!=0)
		return null;
		else {
			int[] a1 = new int[anArray.length/2];
			System.arraycopy(anArray, 0, a1, 0, anArray.length/2);
			
			int[] a2 = new int[anArray.length/2];
			System.arraycopy(anArray, anArray.length/2, a2, 0, anArray.length/2);
			
			int[][] splits = new int[2][anArray.length/2];
			splits[0] = a1;
			splits[1] = a2;
//			System.out.println(printArray(splits));
			return splits;			
		}
	}
	
	/**
	 * not-using-4-now	joins two arrays
	 * @param arr1
	 * @param arr2
	 * @return
	 */
	private int[] join (int[] arr1, int[] arr2) {
		if (arr1.length==0||arr1.length%2!=0||arr2.length==0||arr2.length%2!=0||arr1.length!=arr2.length)
			return null;
		else {
			int[] joined = new int[arr1.length*2];
			
			System.arraycopy(arr1, 0, joined, 0, arr1.length);
			System.arraycopy(arr2, 0, joined, arr2.length, arr2.length);
			
			return joined;
		}
		
	}
	
	/**
	 * interleave shuffle of two arrays
	 * @param arr1	- array 1 1st
	 * @param arr2	- array 2 2nd
	 * @return	shuffled - the interleaved shuffled array of arr1 & arr2
	 * note precedence selection of arr1 is always above arr2
	 */
	private int[] shuffle(int[] arr1, int[] arr2) {
		if (arr1.length==0||arr2.length==0||(arr1.length+arr2.length)%2!=0||arr1.length!=arr2.length)
			return null;
		else {
			int[] shuffled = new int[arr1.length*2];
			int shIndex = 0;
			for ( int i = 0; i < arr1.length; i++) {
				shuffled[shIndex] = arr1[i];
				shIndex += 1;
				shuffled[shIndex] = arr2[i];
				shIndex += 1;
			}
			printArray(shuffled);
			return shuffled;
		}
		
	}

	/**
	 * returns a string representation of a map
	 * @param amap
	 * @return
	 */
	private String printMap(LinkedHashMap<Integer, Integer> amap) {
		StringBuilder b = new StringBuilder("DeckSize\t ==> \tInterleave-ShuffleCount\t ==> \tDiff\t ==> \tRatio\n");
		Set<Integer> k = amap.keySet();
		Iterator<Integer> iter = k.iterator();
		while(iter.hasNext()) {
			int size = iter.next();
			int count = amap.get(size);
			b.append(size + "\t\t ==> \t" + count + "\t\t\t ==> \t"+(size-count)+"\t ==> \t"+new Double(size)/count+"\n");
		}
		return b.toString();
		
	}
	
	/**
	 * returns a string representation of an array
	 * @param anArray
	 * @return a string representation of an array
	 */
	private String printArray(int[] anArray) {
		StringBuilder b = new StringBuilder();
		
		for(int i = 0; i < anArray.length; i++)
			if(i == anArray.length-1) {
				b.append(anArray[i]);
			} else {
				b.append(anArray[i] + ", ");
			}
		return b.toString();
	}

	/**
	 * returns a string representation of an array with dimension [2][n]
	 * @param anArray - two arrays representing the split of the deck
	 * @return string representation of an array with dimension [2][n]
	 */
	private String printArray(int[][] anArray) {//System.out.println("0 is " + anArray[0].length);System.out.println("1 is " + anArray[1].length);
		StringBuilder b = new StringBuilder();
		
		for(int i = 0; i < 2; i++) {//b.append("\n["+i+"]");
			for(int j = 0; j < anArray[i].length; j++) {
//				System.out.println("i is " + i + " and j is " + j);
				printArray(anArray[i]);
//				if(i == anArray[i].length-1) {
//					b.append(anArray[i][j] + "\n");
//				} else {
//					b.append(anArray[i][j] + ", ");
//				}
			}
		}
		
		
		return b.toString();
	}
	
	/**
	 * boolean check to see if two arrays are equals
	 * @param arr1
	 * @param arr2
	 * @return boolean true if all the elements in the arrays are identical - and with order
	 */
	private boolean isIdentical ( int[] arr1, int[] arr2) {
		boolean allClear = false;
		if (arr1.length==0||arr2.length==0||(arr1.length+arr2.length)%2!=0||arr1.length!=arr2.length)
			return false;
		
		else {
			/* *************************************
			for ( int i = 0; i < arr1.length; i++ ) {
				boolean isEqual = arr1[i] == arr2[i];
				if(i==0 && isEqual) allClear = true;
				allClear = allClear && isEqual;
			}
			
			return allClear;
			***************************************** */
			
			// replaces above commented code----but note that
			// the code below also uses a reference check - arr1==arr2
			return Arrays.equals(arr1, arr2);
		}
	}

	
	

}
