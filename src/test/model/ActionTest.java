package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ActionTest {
    private Action action1;
    private Action action2;
    private Action action3;
    private Action action4;
    private Action action5;
    private Action action6;

    @BeforeEach
    public void setup() {
        action1 = new Action("left");
        action2 = new Action("right");
        action3 = new Action("up");
        action4 = new Action("down");
        action5 = new Action("break");
        action6 = new Action("fail");
    }

    @Test
    public void testConstructor() {
        assertEquals("left", action1.getDescription());
        assertEquals("right", action2.getDescription());
        assertEquals("up", action3.getDescription());
        assertEquals("down", action4.getDescription());
        assertEquals("break", action5.getDescription());
        assertEquals("fail", action6.getDescription());

        int[] charPositions1 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        int[] charPositions2 = new int[]{12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
        int[] charPositions3 = new int[]{24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35};
        int[] charPositions4 = new int[]{36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47};
        int[] charPositions5 = new int[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59};
        int[] charPositions6 = new int[]{60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71};

        for (int i = 0; i < action1.getCharPositions().length; i++) {
            assertEquals(charPositions1[i], action1.getCharPositions()[i]);
        }
        for (int i = 0; i < action2.getCharPositions().length; i++) {
            assertEquals(charPositions2[i], action2.getCharPositions()[i]);
        }
        for (int i = 0; i < action3.getCharPositions().length; i++) {
            assertEquals(charPositions3[i], action3.getCharPositions()[i]);
        }
        for (int i = 0; i < action4.getCharPositions().length; i++) {
            assertEquals(charPositions4[i], action4.getCharPositions()[i]);
        }
        for (int i = 0; i < action5.getCharPositions().length; i++) {
            assertEquals(charPositions5[i], action5.getCharPositions()[i]);
        }
        for (int i = 0; i < action6.getCharPositions().length; i++) {
            assertEquals(charPositions6[i], action6.getCharPositions()[i]);
        }

        assertEquals(0, action1.getCurrentPosition());
    }

    @Test
    public void testNextCharacter() {
        Maze maze = new Maze();
        ArrayList<Character> characters = maze.getCharacters();

        assertEquals(0, action3.getCurrentPosition());
        assertEquals(characters.get(24), action3.nextCharacter(characters));
        assertEquals(characters.get(25), action3.nextCharacter(characters));
        assertEquals(characters.get(26), action3.nextCharacter(characters));
        assertEquals(characters.get(27), action3.nextCharacter(characters));
        assertEquals(characters.get(28), action3.nextCharacter(characters));

        assertEquals(5, action3.getCurrentPosition());
        assertEquals(characters.get(29), action3.nextCharacter(characters));
        assertEquals(characters.get(30), action3.nextCharacter(characters));
        assertEquals(characters.get(31), action3.nextCharacter(characters));
        assertEquals(characters.get(32), action3.nextCharacter(characters));
        assertEquals(characters.get(33), action3.nextCharacter(characters));
        assertEquals(characters.get(34), action3.nextCharacter(characters));

        assertEquals(11, action3.getCurrentPosition());
        assertEquals(characters.get(35), action3.nextCharacter(characters));

        assertEquals(0, action3.getCurrentPosition());
        assertEquals(characters.get(24), action3.nextCharacter(characters));
    }
}
