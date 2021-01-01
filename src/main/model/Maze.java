package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;

/*
Represents a maze of height ROWS and width COLS.
The slots in the maze are represented by a 2D array of integers:
- a 0 represents an empty space
- a 1 represents a blocked space (wall segment)
- a 2 represents the current position of the Runner

Each maze also has a random list of characters (alphanumeric and some special symbols).
 */

public class Maze implements Writable {
    public static final int ROWS = 20;
    public static final int COLS = 20;

    private String name;
    private int[][] slots;
    private final int[][] originalSlots;
    private final ArrayList<Character> characters;

    // MODIFIES: this
    // EFFECTS: constructs a new unnamed Maze of height ROWS and width COLS
    //          each slot is randomly filled with an empty space (70%) or a wall (30%) (centre slot is Runner position)
    //          saves a copy of maze slots to originalSlots
    //          instantiates characters in random order
    public Maze() {
        name = "none";

        slots = new int[ROWS][COLS];
        originalSlots = new int[ROWS][COLS];

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if ((i == (ROWS / 2) - 1) && (j == (COLS / 2) - 1)) {
                    slots[i][j] = 2;
                    originalSlots[i][j] = 2;
                } else {
                    // 0-9; a 0, 1, or 2 means a wall segment, anything else means an empty space
                    int content = (int) (Math.random() * 10);

                    if (content == 0 || content == 1 || content == 2) {
                        content = 1;
                    } else {
                        content = 0;
                    }

                    slots[i][j] = content;
                    originalSlots[i][j] = content;
                }
            }
        }

        characters = new ArrayList<>();
        char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.,:;?!@#$%".toCharArray();
        for (char c : chars) {
            characters.add(c);
        }
        Collections.shuffle(characters);
    }

    // MODIFIES: this
    // EFFECTS: constructs a new Maze of height ROWS and width COLS, with the given name, slots and characters
    //          saves a copy of maze slots to originalSlots
    public Maze(String name, int[][] slots, ArrayList<Character> characters) {
        this.name = name;

        this.slots = slots;
        this.originalSlots = new int[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            System.arraycopy(this.slots[i], 0, this.originalSlots[i], 0, COLS);
        }

        this.characters = characters;
    }

    // MODIFIES: this
    // EFFECTS: sets the name of this maze to the given string
    public void setName(String name) {
        this.name = name;
    }

    // EFFECTS: returns the name of this maze
    public String getName() {
        return name;
    }

    // REQUIRES: posX is between 0 and Maze.COLS - 1
    //           posY is between 0 and Maze.ROWS - 1
    // EFFECTS: returns the content of the maze at position posX, posY
    public int getSlot(int posX, int posY) {
        return slots[posY][posX];
    }

    // REQUIRES: posX is between 0 and Maze.COLS - 1
    //           posY is between 0 and Maze.ROWS - 1
    //           to is one of:
    //           - 0 (empty slot)
    //           - 1 (wall segment)
    //           - 2 (runner position)
    // MODIFIES: this
    // EFFECTS: updates the content of the maze at position posX, posY to the given integer
    public void editSlot(int posX, int posY, int to) {
        slots[posY][posX] = to;
    }

    // MODIFIES: this
    // EFFECTS: resets the maze to its original state
    public void reset() {
        for (int i = 0; i < ROWS; i++) {
            System.arraycopy(originalSlots[i], 0, slots[i], 0, COLS);
        }
    }

    // EFFECTS: returns the random characters associated with this maze
    public ArrayList<Character> getCharacters() {
        return characters;
    }

    // EFFECTS: returns this maze as a JSON Object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        JSONArray slotsArray = new JSONArray();
        for (int i = 0; i < ROWS; i++) {
            JSONArray slotsRow = new JSONArray();

            for (int j = 0; j < COLS; j++) {
                slotsRow.put(slots[i][j]);
            }

            slotsArray.put(slotsRow);
        }

        String charString = "";
        for (int i = 0; i < characters.size(); i++) {
            charString += Character.toString(characters.get(i));
        }

        json.put("name", name);
        json.put("characters", charString);
        json.put("slots", slotsArray);

        return json;
    }

    // FOR TESTING:

    public int[][] getSlots() {
        return slots;
    }
}
