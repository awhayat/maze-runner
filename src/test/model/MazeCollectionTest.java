package model;

import model.exceptions.MazeNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MazeCollectionTest {
    private MazeCollection mc;

    @BeforeEach
    public void setup() {
        mc = new MazeCollection();
    }

    @Test
    public void testConstructor() {
        assertEquals(0, mc.getMazes().size());
    }

    @Test
    public void testGetMaze() {
        Maze maze1 = new Maze();
        Maze maze2 = new Maze();
        mc.addMaze(maze1);
        mc.addMaze(maze2);

        try {
            assertEquals(maze1, mc.getMaze(1));
            assertEquals(maze2, mc.getMaze(2));
        } catch (MazeNotFoundException e) {
            fail("MazeNotFoundException should not have been thrown.");
        }

        try {
            mc.getMaze(3);
            fail("MazeNotFoundException should have been thrown.");
        } catch (MazeNotFoundException e) {
            // expected
        }
    }

    @Test
    public void testAdd() {
        assertEquals(0, mc.getMazes().size());

        Maze maze1 = new Maze();
        Maze maze2 = new Maze();
        mc.addMaze(maze1);
        mc.addMaze(maze2);

        assertTrue(mc.getMazes().contains(maze1));
        assertTrue(mc.getMazes().contains(maze2));
        assertEquals(2, mc.getMazes().size());
    }

    @Test
    public void testRemoveMaze() {
        Maze maze1 = new Maze();
        Maze maze2 = new Maze();

        mc.addMaze(maze1);

        try {
            mc.removeMaze(1);
        } catch (MazeNotFoundException e) {
            fail("MazeNotFoundException should not have been thrown.");
        }

        assertFalse(mc.getMazes().contains(maze1));
        assertEquals(0, mc.size());

        mc.addMaze(maze1);
        mc.addMaze(maze2);

        try {
            mc.removeMaze(2);
        } catch (MazeNotFoundException e) {
            fail("MazeNotFoundException should not have been thrown.");
        }

        assertTrue(mc.getMazes().contains(maze1));
        assertFalse(mc.getMazes().contains(maze2));
        assertEquals(1, mc.size());

        try {
            mc.removeMaze(3);
            fail("MazeNotFoundException should have been thrown.");
        } catch (MazeNotFoundException e) {
            // expected
        }

        try {
            mc.removeMaze(maze1);
        } catch (MazeNotFoundException e) {
            fail("MazeNotFoundException should not have been thrown.");
        }

        assertFalse(mc.getMazes().contains(maze1));
        assertEquals(0, mc.size());

        try {
            mc.removeMaze(new Maze());
            fail("MazeNotFoundException should have been thrown.");
        } catch (MazeNotFoundException e) {
            // expected
        }
    }

    @Test
    public void testSize() {
        assertEquals(mc.getMazes().size(), mc.size());

        mc.addMaze(new Maze());
        assertEquals(mc.getMazes().size(), mc.size());

        mc.addMaze(new Maze());
        mc.addMaze(new Maze());
        assertEquals(mc.getMazes().size(), mc.size());
    }
}
