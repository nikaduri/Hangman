/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {

    /**
     * Resets the display so that only the scaffold appears
     */
    public void reset() {
        removeAll();
        drawScaffold();
        drawBeam();
        drawRope();
    }


    /**
     * Updates the word on the screen to correspond to the current
     * state of the game.  The argument string shows what letters have
     * been guessed so far; unguessed letters are indicated by hyphens.
     */
    public void displayWord(String word) {
        if (this.word != null) {
            remove(this.word);
        }
        double x = (double) getWidth() / 2 - BEAM_LENGTH;
        double y = Y_OFFSET + SCAFFOLD_HEIGHT + WORD_Y_OFFSET;
        this.word = new GLabel(word);
        this.word.setFont("Calibri-45");
        add(this.word, x, y);
    }


    // This method updates the display and calls needed methods if user has guessed incorrectly.
    // It also keeps count of how many wrong guesses user has had.
    public void noteIncorrectGuess(char letter) {
        wrongAnswers++;
        drawNextObject();
        if (alreadyGuessed(letter)) {
            return;
        }
        incorrectLetters = letter + incorrectLetters;
        changeLabel();
    }


    // This method is called if incorrectly guessed letters label needs to be changed.
    // It updates the label and adds the new letter.
    private void changeLabel() {
        if (incorrectGuess != null) {
            remove(incorrectGuess);
        }
        double x = (double) getWidth() / 2 - BEAM_LENGTH;
        double y = Y_OFFSET + SCAFFOLD_HEIGHT + WORD_Y_OFFSET + word.getAscent() + word.getDescent();
        incorrectGuess = new GLabel(incorrectLetters);
        incorrectGuess.setFont("Calibri-25");
        add(incorrectGuess, x, y);
    }

    // This method checks if user has already guessed the letter.
    private boolean alreadyGuessed(char letter) {
        return incorrectLetters.contains(String.valueOf(letter));
    }


    // This method adds new objects for incorrect answers.
    private void drawNextObject() {
        switch (wrongAnswers) {
            case 1:
                drawHead();
                break;
            case 2:
                drawBody();
                break;
            case 3:
                drawLeftHand();
                break;
            case 4:
                drawRightHand();
                break;
            case 5:
                drawLeftLeg();
                break;
            case 6:
                drawRightLeg();
                break;
            case 7:
                drawLeftFoot();
                break;
            case 8:
                drawRightFoot();
                break;
        }
    }

    // Eight methods below just draw the objects on canvas.
    private void drawRope() {
        GLine rope = new GLine((double) getWidth() / 2, Y_OFFSET, (double) getWidth() / 2, Y_OFFSET + ROPE_LENGTH);
        add(rope);
    }

    private void drawRightFoot() {
        double x = (double) getWidth() / 2 + HIP_WIDTH;
        double y = Y_OFFSET + 2 * HEAD_RADIUS + ROPE_LENGTH + BODY_LENGTH + LEG_LENGTH;
        GLine foot = new GLine(x,y,x+FOOT_LENGTH,y);
        add(foot);
    }

    private void drawLeftFoot() {
        double x = (double) getWidth() / 2 - HIP_WIDTH;
        double y = Y_OFFSET + 2 * HEAD_RADIUS + ROPE_LENGTH + BODY_LENGTH + LEG_LENGTH;
        GLine foot = new GLine(x,y,x-FOOT_LENGTH,y);
        add(foot);
    }

    private void drawRightLeg() {
        double x = (double) getWidth() / 2;
        double y = Y_OFFSET + 2 * HEAD_RADIUS + ROPE_LENGTH + BODY_LENGTH;
        GLine hip = new GLine(x, y, x + HIP_WIDTH, y);
        add(hip);
        GLine leg = new GLine(x + HIP_WIDTH, y, x + HIP_WIDTH, y + LEG_LENGTH);
        add(leg);
    }

    private void drawLeftLeg() {
        double x = (double) getWidth() / 2;
        double y = Y_OFFSET + 2 * HEAD_RADIUS + ROPE_LENGTH + BODY_LENGTH;
        GLine hip = new GLine(x, y, x - HIP_WIDTH, y);
        add(hip);
        GLine leg = new GLine(x - HIP_WIDTH, y, x - HIP_WIDTH, y + LEG_LENGTH);
        add(leg);
    }

    private void drawRightHand() {
        double x = (double) getWidth() / 2;
        double y = Y_OFFSET + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD;
        GLine upperArm = new GLine(x, y, x + UPPER_ARM_LENGTH, y);
        add(upperArm);
        GLine lowerArm = new GLine(x + UPPER_ARM_LENGTH, y, x + UPPER_ARM_LENGTH, y + LOWER_ARM_LENGTH);
        add(lowerArm);
    }

    private void drawLeftHand() {
        double x = (double) getWidth() / 2;
        double y = Y_OFFSET + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD;
        GLine upperArm = new GLine(x, y, x - UPPER_ARM_LENGTH, y);
        add(upperArm);
        GLine lowerArm = new GLine(x - UPPER_ARM_LENGTH, y, x - UPPER_ARM_LENGTH, y + LOWER_ARM_LENGTH);
        add(lowerArm);
    }

    private void drawBody() {
        double x = (double) getWidth() / 2;
        double y = Y_OFFSET + 2 * HEAD_RADIUS + ROPE_LENGTH;
        GLine body = new GLine(x, y, (double) getWidth() / 2, y + BODY_LENGTH);
        add(body);
    }

    private void drawHead() {
        double x = (double) getWidth() / 2 - HEAD_RADIUS;
        double y = Y_OFFSET + ROPE_LENGTH;
        GOval head = new GOval(2 * HEAD_RADIUS, 2 * HEAD_RADIUS);
        add(head, x, y);
    }


    private void drawBeam() {
        double beamX1 = (double) getWidth() / 2;
        double beamX2 = (double) getWidth() / 2 - BEAM_LENGTH;
        GLine beam = new GLine(beamX1, Y_OFFSET, beamX2, Y_OFFSET);
        add(beam);
    }

    private void drawScaffold() {
        double x = (double) getWidth() / 2 - BEAM_LENGTH;
        GLine scaffold = new GLine(x, Y_OFFSET, x, Y_OFFSET + SCAFFOLD_HEIGHT);
        add(scaffold);
    }

    // Constants for the simple version of the picture (in pixels)
    private static final int SCAFFOLD_HEIGHT = 360;
    private static final int BEAM_LENGTH = 144;
    private static final int ROPE_LENGTH = 18;
    private static final int HEAD_RADIUS = 36;
    private static final int BODY_LENGTH = 144;
    private static final int ARM_OFFSET_FROM_HEAD = 28;
    private static final int UPPER_ARM_LENGTH = 72;
    private static final int LOWER_ARM_LENGTH = 44;
    private static final int HIP_WIDTH = 36;
    private static final int LEG_LENGTH = 108;
    private static final int FOOT_LENGTH = 28;
    private static final int Y_OFFSET = 10;
    private static final int WORD_Y_OFFSET = 40;

    // Instance Variables

    // "word" stores the current word status in the game.
    private GLabel word;
    private GLabel incorrectGuess;
    private String incorrectLetters = "";
    private int wrongAnswers = 0;
}
