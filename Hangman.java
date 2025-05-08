/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;

public class Hangman extends ConsoleProgram {


    private HangmanCanvas canvas;

    // This variable is used to track which letters the player has guessed.
    private String guessedLetters = "";
    private int lives = 8;

    // This variable is used to count how many guessed letters are there in the word.
    private int count = 0;

    public void init() {
        canvas = new HangmanCanvas();
        add(canvas);
    }

    public void run() {
        canvas.reset();
        HangmanLexicon lexicon = new HangmanLexicon();
        String word = lexicon.getWord().toUpperCase();
        printDashes(word);
        while (true) {
            String guess = readGuess();
            checkIfCorrect(guess, word);
            if (guessedLetters.length() == word.length() || lives == 0) {
                break;
            }
        }
        if (lives == 0) {
            println("Game Over, you are completely hung.");
            println("The word was: " + word);
            canvas.displayWord(word);
        } else {
            println("Congratulations, you won!");
        }
    }

    private void printDashes(String word) {
        String dashes = "";
        print("The word looks like this: ");
        for (int i = 0; i < word.length(); i++) {
            dashes = dashes + '-';
        }
        println(dashes);
        canvas.displayWord(dashes);
    }

    // This method checks if user has entered only one letter.
    private boolean valid(String guess) {
        return guess.length() == 1 && isLetter(guess);
    }

    // This method checks if user has entered a letter.
    private boolean isLetter(String guess) {
        return (guess.charAt(0) >= 'a' && guess.charAt(0) <= 'z') || (guess.charAt(0) >= 'A' && guess.charAt(0) <= 'Z');
    }

    // This method reads the user input and checks if it is a valid input (a single letter).
    private String readGuess() {
        String guess;
        while (true) {
            guess = readLine("Enter your guess: ");
            if (valid(guess)) {
                break;
            } else {
                println("Enter a valid character.");
            }
        }
        return guess;
    }

    // This method checks if the letter is in the word and displays messages and graphics accordingly.
    // Also, it adjusts the lives and adds correct letters to guessedLetters string.
    private void checkIfCorrect(String guess, String word) {
        guess = guess.toUpperCase();
        String displayedWord = guessedWord(guess, word);
        if (word.contains(guess) && !guessed(guess.charAt(0))) {
            println("Your guess is correct.");
            guessedLetters = guessMultiplier(guess) + guessedLetters;
            count = 0;
            canvas.displayWord(displayedWord);
        } else if (guessed(guess.charAt(0))) {
            println("You already guessed that letter.");
        } else {
            println("There are no " + guess + "'s in the word.");
            lives--;
            canvas.noteIncorrectGuess(guess.charAt(0));
        }
        println("The word now looks like this: " + displayedWord);
        if (lives > 1) {
            println("You have " + lives + " guesses left.");
        } else if (lives == 1) {
            println("You have 1 guess left.");
        }
    }

    // This method returns the current state of the word.
    private String guessedWord(String guess, String word) {
        guess = guess.toUpperCase();
        char guessedLetter = guess.charAt(0);
        String newWord = "";
        for (int i = 0; i < word.length(); i++) {
            if (!(word.charAt(i) == guessedLetter) && !guessed(word.charAt(i))) {
                newWord = newWord + '-';
            } else if (guessed(word.charAt(i))) {
                newWord = newWord + word.charAt(i);
            } else {
                newWord = newWord + guessedLetter;
                count++;
            }
        }
        return newWord;
    }

    // This method checks if a player has already guessed the letter.
    private boolean guessed(char letter) {
        for (int i = 0; i < guessedLetters.length(); i++) {
            if (guessedLetters.charAt(i) == letter) {
                return true;
            }
        }
        return false;
    }

    // This method adds the same guessed letter in one string depending on count variable.
    // We need this to properly end the game when all the letters are guessed.
    private String guessMultiplier(String guess) {
        String finalGuess = "";
        for (int i = 0; i < count; i++) {
            finalGuess += guess.charAt(0);
        }
        return finalGuess;
    }


}
