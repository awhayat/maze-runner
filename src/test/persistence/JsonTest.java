package persistence;

import model.Maze;
import model.MazeCollection;
import model.exceptions.MazeNotFoundException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// code modeled on application found at https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonTest {
    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            MazeCollection mc = reader.read();
            fail("IOException should have been thrown");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    public void testWriterInvalidFile() {
        try {
            MazeCollection mc = new MazeCollection();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException should have been thrown");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    public void testJsonEmptyCollection() {
        try {
            MazeCollection mc = new MazeCollection();

            JsonWriter writer = new JsonWriter("./data/testJsonEmptyCollection.json");
            writer.open();
            writer.write(mc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testJsonEmptyCollection.json");
            mc = reader.read();

            assertEquals(0, mc.size());
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    public void testJsonGeneralCollection() {
        try {
            MazeCollection mc = new MazeCollection();

            Maze maze1 = new Maze();
            maze1.setName("Alpha");
            mc.add(maze1);

            Maze maze2 = new Maze();
            maze2.setName("Beta");
            mc.add(maze2);

            String correctName1 = maze1.getName();
            int[][] correctSlots1 = maze1.getSlots();
            ArrayList<Character> correctCharacters1 = maze1.getCharacters();

            String correctName2 = maze2.getName();
            int[][] correctSlots2 = maze2.getSlots();
            ArrayList<Character> correctCharacters2 = maze2.getCharacters();

            JsonWriter writer = new JsonWriter("./data/testJsonGeneralCollection.json");
            writer.open();
            writer.write(mc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testJsonGeneralCollection.json");
            mc = reader.read();

            assertEquals(2, mc.size());

            try {
                checkMaze(correctName1, correctSlots1, correctCharacters1, mc.getMaze(1));
                checkMaze(correctName2, correctSlots2, correctCharacters2, mc.getMaze(2));
            } catch (MazeNotFoundException e) {
                System.out.println("Error: Maze does not exist.");
            }
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    public static void checkMaze(String name, int[][] slots, ArrayList<Character> characters, Maze maze) {
        assertEquals(name, maze.getName());

        int[][] mazeSlots = maze.getSlots();
        for (int i = 0; i < Maze.ROWS; i++) {
            for (int j = 0; j < Maze.COLS; j++) {
                assertEquals(slots[i][j], mazeSlots[i][j]);
            }
        }

        ArrayList<Character> mazeCharacters = maze.getCharacters();
        for (int i = 0; i < characters.size(); i++) {
            assertEquals(characters.get(i), mazeCharacters.get(i));
        }
    }
}
