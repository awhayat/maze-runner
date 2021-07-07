package model;


import model.exceptions.MazeNotFoundException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Iterator;

/*
Represents a collection of mazes.
 */

public class MazeCollection implements Writable, Iterable<Maze> {
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
    // EFFECTS: removes the given maze
    //          if there is no such maze, throws a MazeNotFoundException
    public void remove(Maze maze) throws MazeNotFoundException {
        if (!mazes.contains(maze)) {
            throw new MazeNotFoundException();
        }

        mazes.remove(maze);
    }

    // MODIFIES: this
    // EFFECTS: removes the ith maze
    //          if there is no ith maze, throws a MazeNotFoundException
    public void remove(int i) throws MazeNotFoundException {
        if (mazes.size() < i) {
            throw new MazeNotFoundException();
        }

        mazes.remove(i - 1);
    }

    // EFFECTS: returns the number of mazes in this collection
    public int size() {
        return mazes.size();
    }

    // EFFECTS: returns an iterator over the mazes in this collection
    public Iterator<Maze> iterator() {
        return mazes.iterator();
    }

    // EFFECTS: returns this MazeCollection as a JSON object
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        JSONArray mazeArray = new JSONArray();
        for (Maze maze : mazes) {
            mazeArray.put(maze.toJSON());
        }

        json.put("mazes", mazeArray);

        return json;
    }

    // FOR TESTING:

    public ArrayList<Maze> getMazes() {
        return mazes;
    }
}
