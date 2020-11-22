package model;


import model.exceptions.MazeNotFoundException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

/*
Represents a collection of mazes.
 */

public class MazeCollection implements Writable {
    private ArrayList<Maze> mazes;

    // MODIFIES: this
    // EFFECTS: constructs a new empty MazeCollection
    public MazeCollection() {
        mazes = new ArrayList<>();
    }

    // EFFECTS: returns the ith maze
    //          if there is no ith maze, throws a MazeNotFoundException
    public Maze getMaze(int i) throws MazeNotFoundException {
        if (mazes.size() < i) {
            throw new MazeNotFoundException();
        }

        return mazes.get(i - 1);
    }

    // MODIFIES: this
    // EFFECTS: adds the given Maze to the collection
    public void add(Maze maze) {
        mazes.add(maze);
    }

    // MODIFIES: this
    // EFFECTS: removes the ith maze
    //          if there is no ith maze, throws a MazeNotFoundException
    public void removeMaze(int i) throws MazeNotFoundException {
        if (mazes.size() < i) {
            throw new MazeNotFoundException();
        }

        mazes.remove(i - 1);
    }

    // EFFECTS: returns the number of Mazes in the collection
    public int size() {
        return mazes.size();
    }

    // EFFECTS: returns this MazeCollection as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("mazes", mazesToJson());

        return json;
    }

    // EFFECTS: returns Mazes in this MazeCollection as a JSON Array
    private JSONArray mazesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Maze maze : mazes) {
            jsonArray.put(maze.toJson());
        }

        return jsonArray;
    }

    // FOR TESTING:

    public ArrayList<Maze> getMazes() {
        return mazes;
    }
}
