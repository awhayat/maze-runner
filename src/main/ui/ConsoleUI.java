//package ui;
//
//import model.Maze;
//import model.MazeCollection;
//import model.Runner;
//import persistence.JsonReader;
//import persistence.JsonWriter;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.Scanner;
//
///*
//A console-based interface for the application (currently commented out due to disuse)
// */
//public class ConsoleUI {
//    private MazeCollection collection;
//    private Scanner input;
//    private JsonWriter jsonWriter;
//    private JsonReader jsonReader;
//    private static final String JSON_STORE = "./data/MazeCollection.json";
//
//
//    // EFFECTS: runs the Maze Runner application
//    public ConsoleUI() {
//        System.out.println("Welcome to the Maze Runner.");
//        runApp();
//    }
//
//    // MODIFIES: this
//    // EFFECTS: processes user input
//    private void runApp() {
//        init();
//        boolean running = true;
//
//        do {
//            printCollection(collection);
//
//            int response = mainMenu();
//
//            if (response == 0) {
//                chooseMaze();
//            } else if (response == 1) {
//                collection.add(new Maze());
//            } else if (response == 2) {
//                saveMazeCollection();
//            } else if (response == 3) {
//                loadMazeCollection();
//            } else {
//                running = false;
//            }
//        } while (running);
//    }
//
//    // MODIFIES: this
//    // EFFECTS: initializes MazeCollection, user input handler, and writer/reader
//    private void init() {
//        collection = new MazeCollection();
//        collection.add(new Maze());
//
//        input = new Scanner(System.in);
//        jsonWriter = new JsonWriter(JSON_STORE);
//        jsonReader = new JsonReader(JSON_STORE);
//    }
//
//    // EFFECTS: processes user input
//    private int mainMenu() {
//        int response;
//        do {
//            System.out.println("Enter 0 if you would like to use one of the above mazes, or 1 to add a new maze.");
//            System.out.println("Enter 2 to save your mazes to file, or 3 to load your mazes from file.");
//            System.out.println("Enter 4 to exit the program: ");
//            response = input.nextInt();
//            if ((response != 0) && (response != 1) && (response != 2) && (response != 3) && (response != 4)) {
//                System.out.println("Invalid response.");
//            }
//        } while ((response != 0) && (response != 1) && (response != 2) && (response != 3) && (response != 4));
//
//        return response;
//    }
//
//    // EFFECTS: processes user input
//    private void chooseMaze() {
//        int response;
//        do {
//            System.out.println("Enter the number of the maze you would like to use: ");
//            response = input.nextInt();
//            if ((response < 1) || (response > collection.size())) {
//                System.out.println("Invalid response.");
//            }
//        } while ((response < 1) || (response > collection.size()));
//
//        useMaze(response);
//        collection.getMaze(response).reset();
//    }
//
//    // EFFECTS: processes user input
//    private void useMaze(int mazeNumber) {
//        Runner runner = new Runner(collection.getMaze(mazeNumber));
//        boolean inMaze = true;
//
//        do {
//            printMaze(runner.getMaze());
//            int response = chooseAction();
//
//            if (response == 0) {
//                runner.moveLeft();
//            } else if (response == 1) {
//                runner.moveRight();
//            } else if (response == 2) {
//                runner.moveUp();
//            } else if (response == 3) {
//                runner.moveDown();
//            } else if (response == 4) {
//                breakWall(runner);
//            } else if (response == 5) {
//                System.out.println("Your password is: " + runner.generatePassword());
//            } else if (response == 6) {
//                runner.reset();
//            } else if (response == 7) {
//                inMaze = false;
//            }
//        } while (inMaze);
//    }
//
//    // EFFECTS: processes user input
//    private int chooseAction() {
//        System.out.println("Enter 0 to move left, 1 to move right, 2 to move up, or 3 to move down.");
//        System.out.println("Enter 4 to break a wall, 5 to generate a password, 6 to reset the maze, or "
//                + "7 to leave the maze: ");
//
//        return input.nextInt();
//    }
//
//    // EFFECTS: processes uer input
//    private void breakWall(Runner runner) {
//        System.out.println("Enter 0 to break the wall to your left, 1 to break the wall to your right, "
//                + "2 to break the wall above you, or 3 to break the wall below you: ");
//        int response = input.nextInt();
//        if (response == 0) {
//            runner.breakWall("L");
//        } else if (response == 1) {
//            runner.breakWall("R");
//        } else if (response == 2) {
//            runner.breakWall("U");
//        } else if (response == 3) {
//            runner.breakWall("D");
//        }
//    }
//
//    // EFFECTS: prints String representations of all mazes in the given collection
//    private void printCollection(MazeCollection mc) {
//        for (int i = 1; i <= mc.size(); i++) {
//            System.out.println("MAZE " + i + ":");
//            printMaze(mc.getMaze(i));
//        }
//    }
//
//    // EFFECTS: prints a String representation of the given maze
//    private void printMaze(Maze maze) {
//        int[][] slots = maze.getSlots();
//        for (int i = 0; i < slots.length; i++) {
//            for (int j = 0; j < slots[0].length; j++) {
//                if (slots[i][j] == 1) {
//                    System.out.print("|-|");
//                } else if (slots[i][j] == 2) {
//                    System.out.print(" O ");
//                } else {
//                    System.out.print("   ");
//                }
//                if (j == slots[0].length - 1) {
//                    System.out.println();
//                }
//            }
//        }
//    }
//
//    // EFFECTS: saves the MazeCollection to file
//    private void saveMazeCollection() {
//        try {
//            jsonWriter.open();
//            jsonWriter.write(collection);
//            jsonWriter.close();
//            System.out.println("Saved your mazes to " + JSON_STORE);
//        } catch (FileNotFoundException e) {
//            System.out.println("Unable to write to file: " + JSON_STORE);
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: loads the MazeCollection from file
//    private void loadMazeCollection() {
//        try {
//            collection = jsonReader.read();
//            System.out.println("Loaded your mazes from " + JSON_STORE);
//        } catch (IOException e) {
//            System.out.println("Unable to read from file: " + JSON_STORE);
//        }
//    }
//}
