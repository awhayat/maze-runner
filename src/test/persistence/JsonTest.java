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
    private int[][] correctSlots1;
    private int[][] correctSlots2;
    private ArrayList<Character> correctCharacters1;
    private ArrayList<Character> correctCharacters2;

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
            mc.add(new Maze());

            try {
                correctSlots1 = mc.getMaze(1).getSlots();
                correctCharacters1 = mc.getMaze(1).getCharacters();
                mc.add(new Maze());
                correctSlots2 = mc.getMaze(2).getSlots();
                correctCharacters2 = mc.getMaze(2).getCharacters();
            } catch (MazeNotFoundException e) {
                System.out.println("Error: Maze does not exist.");
            }

            JsonWriter writer = new JsonWriter("./data/testJsonGeneralCollection.json");
            writer.open();
            writer.write(mc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testJsonGeneralCollection.json");
            mc = reader.read();

            assertEquals(2, mc.size());

            try {
                checkMaze(correctSlots1, correctCharacters1, mc.getMaze(1));
                checkMaze(correctSlots2, correctCharacters2, mc.getMaze(2));
            } catch (MazeNotFoundException e) {
                System.out.println("Error: Maze does not exist.");
            }
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    public static void checkMaze(int[][] slots, ArrayList<Character> characters, Maze maze) {
        int[][] mazeSlots = maze.getSlots();
        ArrayList<Character> mazeCharacters = maze.getCharacters();

        for (int i = 0; i < Maze.ROWS; i++) {
            for (int j = 0; j < Maze.COLS; j++) {
                assertEquals(slots[i][j], mazeSlots[i][j]);
            }
        }

        for (int i = 0; i < characters.size(); i++) {
            assertEquals(characters.get(i), mazeCharacters.get(i));
        }
    }
}
