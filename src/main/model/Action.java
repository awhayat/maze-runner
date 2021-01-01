package model;

import java.util.ArrayList;

/*
Represents an action taken in a Maze by the Runner. Can be one of:
- left
- right
- up
- down
- break (removing a wall segment)
- fail (an attempt to move into a wall, or to break a wall that doesn't exist)

Each action corresponds to a subset of characters from any Maze.
 */

public class Action {
    private final String description;
    private final int[] charPositions;
    private int currentPosition;

    // REQUIRES: description is one of:
    //           - "left"
    //           - "right"
    //           - "up"
    //           - "down"
    //           - "fail"
    //           - "break"
    // MODIFIES: this
    // EFFECTS: constructs a new Action with the given description.
    //          charPositions is instantiated depending on the description.
    //          currentPosition is instantiated at 0.
    public Action(String description) {
        this.description = description;

        switch (description) {
            case "left":
                charPositions = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
                break;
            case "right":
                charPositions = new int[]{12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
                break;
            case "up":
                charPositions = new int[]{24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35};
                break;
            case "down":
                charPositions = new int[]{36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47};
                break;
            case "break":
                charPositions = new int[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59};
                break;
            default:
                charPositions = new int[]{60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71};
                break;
        }

        currentPosition = 0;
    }

    // REQUIRES: characters is a valid list of characters from a Maze
    // MODIFIES: this
    // EFFECTS: returns a character from the given list depending on which set of indices this Action refers to
    //          increments currentPosition, but does not go over this.charPositions.length - 1
    public char nextCharacter(ArrayList<Character> characters) {
        char nextChar = characters.get(charPositions[currentPosition]);
        if (currentPosition == charPositions.length - 1) {
            currentPosition = 0;
        } else {
            currentPosition++;
        }
        return nextChar;
    }

    public String getDescription() {
        return description;
    }

    // FOR TESTING:

    public int[] getCharPositions() {
        return charPositions;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }
}
