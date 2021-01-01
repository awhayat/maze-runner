package model;

import java.util.ArrayList;

/*
Represents a character in a particular Maze.
The actions of the Runner are logged, and a password can be generated based on their order.
 */

public class Runner {
    private Maze maze;
    private int posX;
    private int posY;
    private ArrayList<Action> actions;

    // MODIFIES: this
    // EFFECTS: constructs a new Runner in the given Maze
    //          sets posX and posY to the centre of the Maze
    //          instantiates actions as an empty list
    public Runner(Maze maze) {
        this.maze = maze;
        posX = (Maze.COLS / 2) - 1;
        posY = (Maze.ROWS / 2) - 1;
        actions = new ArrayList<>();
    }

    // MODIFIES: this, this.maze
    // EFFECTS: if there is no wall 1 slot to the left of the runner, moves the position of the runner to that slot
    //          if there is a wall 1 slot to the left of the runner, does not change the position of the runner
    //          updates the maze to place the runner at its new position
    //          adds a new Action ("left" or "fail") to actions depending on whether the move was successful
    public void moveLeft() {
        String actionDescription;
        if ((posX == 0) || (maze.getSlot(posX - 1, posY) == 1)) {
            actionDescription = "fail";
        } else {
            maze.editSlot(posX, posY, 0);
            posX -= 1;
            maze.editSlot(posX, posY, 2);
            actionDescription = "left";
        }

        updateActions(actionDescription);
    }

    // MODIFIES: this, this.maze
    // EFFECTS: if there is no wall 1 slot to the right of the runner, moves the position of the runner to that slot
    //          if there is a wall 1 slot to the right of the runner, does not change the position of the runner
    //          updates the maze to place the runner at its new position
    //          adds a new Action ("right" or "fail") to actions depending on whether the move was successful
    public void moveRight() {
        String actionDescription;
        if ((posX == Maze.COLS - 1) || (maze.getSlot(posX + 1, posY) == 1)) {
            actionDescription = "fail";
        } else {
            maze.editSlot(posX, posY, 0);
            posX += 1;
            maze.editSlot(posX, posY, 2);
            actionDescription = "right";
        }

        updateActions(actionDescription);
    }

    // MODIFIES: this, this.maze
    // EFFECTS: if there is no wall 1 slot above the runner, moves the position of the runner to that slot
    //          if there is a wall 1 slot above the runner, does not change the position of the runner
    //          updates the maze to place the runner at its new position
    //          adds a new Action ("up" or "fail") to actions depending on whether the move was successful
    public void moveUp() {
        String actionDescription;
        if ((posY == 0) || (maze.getSlot(posX, posY - 1) == 1)) {
            actionDescription = "fail";
        } else {
            maze.editSlot(posX, posY, 0);
            posY -= 1;
            maze.editSlot(posX, posY, 2);
            actionDescription = "up";
        }

        updateActions(actionDescription);
    }

    // MODIFIES: this, this.maze
    // EFFECTS: if there is no wall 1 slot below the runner, moves the position of the runner to that slot
    //          if there is a wall 1 slot below the runner, does not change the position of the runner
    //          updates the maze to place the runner at its new position
    //          adds a new Action ("down" or "fail") to actions depending on whether the move was successful
    public void moveDown() {
        String actionDescription;
        if ((posY == Maze.ROWS - 1) || (maze.getSlot(posX, posY + 1) == 1)) {
            actionDescription = "fail";
        } else {
            maze.editSlot(posX, posY, 0);
            posY += 1;
            maze.editSlot(posX, posY, 2);
            actionDescription = "down";
        }

        updateActions(actionDescription);
    }

    // MODIFIES: this.maze
    // EFFECTS: removes the wall directly to the left ("L"), right ("R"), above ("U"), or below ("D") the runner
    //          adds a new Action ("break") to actions
    //          if there is no wall at the position in question, adds a new Action ("fail") to actions
    public void breakWall(String position) {
        int targetX;
        int targetY;
        if (position.equals("L")) {
            targetX = posX - 1;
            targetY = posY;
        } else if (position.equals("R")) {
            targetX = posX + 1;
            targetY = posY;
        } else if (position.equals("U")) {
            targetX = posX;
            targetY = posY - 1;
        } else {
            targetX = posX;
            targetY = posY + 1;
        }

        try {
            maze.getSlot(targetX, targetY);
        } catch (ArrayIndexOutOfBoundsException e) {
            updateActions("fail");
            return;
        }

        breakWallInMaze(targetX, targetY);
    }

    // MODIFIES: this.maze
    // EFFECTS: removes the wall at (targetX, targetY)
    private void breakWallInMaze(int targetX, int targetY) {
        if (maze.getSlot(targetX, targetY) == 1) {
            maze.editSlot(targetX, targetY, 0);
            updateActions("break");
        } else {
            updateActions("fail");
        }
    }

    // MODIFIES: this
    // EFFECTS: if there is already an Action with the given description, adds another reference to
    //          that Action to the list.
    //          if there is not already an Action in actions with the given description, adds a new Action.
    private void updateActions(String actionDescription) {
        boolean duplicate = false;
        for (Action action : actions) {
            if (action.getDescription().equals(actionDescription)) {
                actions.add(action);
                duplicate = true;
                break;
            }
        }
        if (!duplicate) {
            actions.add(new Action(actionDescription));
        }
    }

    // MODIFIES: this, this.maze
    // EFFECTS: resets this.maze to its original state.
    //          updates the position of the Runner to the centre of the Maze.
    //          clears all logged actions
    public void reset() {
        maze.reset();
        posX = (Maze.ROWS / 2) - 1;
        posY = (Maze.COLS / 2) - 1;
        actions.clear();
    }

    // EFFECTS: returns a password based on the actions taken so far and the character order in this.maze
    public String generatePassword() {
        String password = "";
        for (Action action : actions) {
            password += Character.toString(action.nextCharacter(maze.getCharacters()));
        }

        return password;
    }

    // FOR TESTING/GUI:

    public Maze getMaze() {
        return maze;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public ArrayList<Action> getActions() {
        return actions;
    }
}
