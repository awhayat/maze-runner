package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonTest;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class MazeTest {
    private Maze testMaze1;
    private Maze testMaze2;

    @BeforeEach
    public void setup() {
        testMaze1 = new Maze();
        testMaze2 = new Maze();
    }

    @Test
    public void testConstructorSlots() {
        int[][] slots1 = testMaze1.getSlots();
        int[][] slots2 = testMaze2.getSlots();

        assertEquals(Maze.ROWS, slots1.length);
        assertEquals(Maze.COLS, slots1[0].length);
        assertEquals(2, slots1[(Maze.ROWS / 2) - 1][(Maze.COLS / 2) - 1]);
        for (int i = 0; i < slots1.length; i++) {
            for (int j = 0; j < slots1[0].length; j++) {
                int content = slots1[i][j];
                assertTrue((content == 0) || (content == 1) || (content == 2));
            }
        }

        assertEquals(Maze.ROWS, slots2.length);
        assertEquals(Maze.COLS, slots2[0].length);
        assertEquals(2, slots2[(Maze.ROWS / 2) - 1][(Maze.COLS / 2) - 1]);
        for (int i = 0; i < slots2.length; i++) {
            for (int j = 0; j < slots2[0].length; j++) {
                int content = slots2[i][j];
                assertTrue((content == 0) || (content == 1) || (content == 2));
            }
        }
    }

    @Test
    public void testConstructorCharacters() {
        assertEquals(72, testMaze1.getCharacters().size());
        assertEquals(72, testMaze2.getCharacters().size());

        String characters1 = "";
        for (char c : testMaze1.getCharacters()) {
            characters1 += Character.toString(c);
        }

        String characters2 = "";
        for (char c : testMaze2.getCharacters()) {
            characters2 += Character.toString(c);
        }

        assertNotEquals(characters2, characters1);
    }

    @Test
    public void testPreciseConstructor() {
        int[][] slots = new int[Maze.ROWS][Maze.COLS];
        for (int i = 0; i < Maze.ROWS; i++) {
            for (int j = 0; j < Maze.COLS; j++) {
                if ((i == (Maze.ROWS / 2) - 1) && (j == (Maze.COLS / 2) - 1)) {
                    slots[i][j] = 2;
                } else {
                    int content = (int) (Math.random() * 2);
                    slots[i][j] = content;
                }
            }
        }

        ArrayList<Character> characters = new ArrayList<>();
        char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.,:;?!@#$%".toCharArray();
        for (char c : chars) {
            characters.add(c);
        }
        Collections.shuffle(characters);

        Maze preciseMaze = new Maze(slots, characters);

        JsonTest.checkMaze(slots, characters, preciseMaze);
    }

    @Test
    public void testGetSlot() {
        int[][] slots = testMaze1.getSlots();

        int content1 = testMaze1.getSlot(0, 0);
        int content2 = testMaze1.getSlot(0, Maze.ROWS - 1);
        int content3 = testMaze1.getSlot(Maze.COLS - 1, 0);
        int content4 = testMaze1.getSlot(Maze.COLS - 1, Maze.ROWS - 1);
        int content5 = testMaze1.getSlot(12, 11);
        int content6 = testMaze1.getSlot(7, 9);

        assertEquals(slots[0][0], content1);
        assertEquals(slots[Maze.ROWS - 1][0], content2);
        assertEquals(slots[0][Maze.COLS - 1], content3);
        assertEquals(slots[Maze.ROWS - 1][Maze.COLS - 1], content4);
        assertEquals(slots[11][12], content5);
        assertEquals(slots[9][7], content6);
    }

    @Test
    public void testEditSlot() {
        testMaze1.editSlot(13, 5, 2);
        testMaze1.editSlot(17, 2, 2);

        assertEquals(2, testMaze1.getSlot(13, 5));
        assertEquals(2, testMaze1.getSlot(17, 2));
    }

    @Test
    public void testReset() {
        int og1 = testMaze1.getSlot(16, 6);
        int og2 = testMaze1.getSlot(3, 11);

        testMaze1.editSlot(16, 6, 2);
        testMaze1.editSlot(3, 11, 2);

        testMaze1.reset();

        assertEquals(og1, testMaze1.getSlot(16, 6));
        assertEquals(og2, testMaze1.getSlot(3, 11));
    }
}