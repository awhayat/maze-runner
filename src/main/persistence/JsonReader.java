package persistence;

import model.Maze;
import model.MazeCollection;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

// Represents a reader that reads a MazeCollection from JSON data stored in a file
// code modeled on application found at https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads MazeCollection from file and returns it
    //          throws IOException if an error occurs reading data from file
    public MazeCollection read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonCollection = new JSONObject(jsonData);
        return parseMazeCollection(jsonCollection);
    }

    // EFFECTS: reads source file as a string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses MazeCollection from JSON object and returns it
    private MazeCollection parseMazeCollection(JSONObject jsonCollection) {
        MazeCollection collection = new MazeCollection();
        addMazes(collection, jsonCollection);
        return collection;
    }

    // MODIFIES: mc
    // EFFECTS: parses mazes from JSON object and adds them to MazeCollection
    private void addMazes(MazeCollection collection, JSONObject jsonCollection) {
        JSONArray mazeArray = jsonCollection.getJSONArray("mazes");
        for (Object json : mazeArray) {
            JSONObject jsonMaze = (JSONObject) json;
            addMaze(collection, jsonMaze);
        }
    }

    // MODIFIES: mc
    // EFFECTS: parses Maze from JSON object and adds it to MazeCollection
    private void addMaze(MazeCollection collection, JSONObject jsonMaze) {
        String name = jsonMaze.getString("name");

        JSONArray slotsArray = jsonMaze.getJSONArray("slots");
        int[][] slots = new int[Maze.ROWS][Maze.COLS];

        for (int i = 0; i < Maze.ROWS; i++) {
            JSONArray slotsRow = slotsArray.getJSONArray(i);

            for (int j = 0; j < Maze.COLS; j++) {
                slots[i][j] = slotsRow.getInt(j);
            }
        }

        String charString = jsonMaze.getString("characters");
        ArrayList<Character> characters = new ArrayList<>();

        char[] chars = charString.toCharArray();
        for (char c : chars) {
            characters.add(c);
        }

        Maze maze = new Maze(name, slots, characters);
        collection.addMaze(maze);
    }
}
