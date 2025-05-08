/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */

import acm.util.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class HangmanLexicon {

	private RandomGenerator random = RandomGenerator.getInstance();
	private ArrayList <String> words;

	/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		return words.size();
	}

	// Reads the file and adds the words to the ArrayList.
	public HangmanLexicon() {
		try {
			// If it's not reading the file, put in the whole path of the .txt file.
			// I found Hangman words on the internet and put them in Words.txt from this website: https://www.hangmanwords.com/words
			BufferedReader rd = new BufferedReader(new FileReader("src/Words.txt"));
			words = new ArrayList <String> ();
			while (true) {
				String word = rd.readLine();
				if(word == null) {
					break;
				}
				words.add(word);
			}
		} catch (IOException e){
			throw new RuntimeException(e);
		}
	}


	// Returns the random word from the list.
	public String getWord() {
		int wordIndex = random.nextInt(words.size() - 1);
		return words.get(wordIndex).toUpperCase();
	}


}
