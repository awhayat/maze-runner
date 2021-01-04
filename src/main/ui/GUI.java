package ui;

import model.Maze;
import model.MazeCollection;
import model.Runner;
import model.exceptions.MazeNotFoundException;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/*
A graphical user interface for the application
 */
public class GUI {
    private Runner runner;
    private static final ImageIcon RUNNER_IMAGE = new ImageIcon("./data/runner.png");
    // image from https://www.netclipart.com/pp/m/12-122800_person-running-clipart.png (non-commercial use)

    private MazeCollection collection;
    private static final int MAX_MAZES = 60;
    private ArrayList<String> mazeNames;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/MazeCollection.json";

    private JFrame window;
    private static final int W_WIDTH = 750;
    private static final int W_HEIGHT = 600;
    private static final Color W_COLOUR = new Color(13, 27, 56);

    private static final int B_WIDTH = 100;
    private static final int B_HEIGHT = 50;
    private static final Color B_COLOUR = new Color(236, 232, 231);
    private static final Font B_FONT = new Font("Dialog", Font.BOLD, 14);

    private static final int SLOT_SIZE = 20;
    private static final Color WALL_COLOUR = new Color(2, 182, 158);
    private static final Color TARGET_COLOUR = new Color(194, 146, 14);

    private JTextField passwordDisplay;
    private static final int P_WIDTH = Maze.ROWS * SLOT_SIZE;
    private static final int P_HEIGHT = 25;

    private final KeyHandler keyHandler = new KeyHandler();

    // MODIFIES: this
    // EFFECTS: runs the Maze Runner application
    public GUI() {
        init();
        displayMenu();
    }

    // MODIFIES: this
    // EFFECTS: adds a new button with the given text, bounds, and colour to window
    //          returns a reference to the button
    private JButton newButton(String text, int x, int y, int width, int height, Color colour, boolean border) {
        JButton newButton = new JButton(text);
        newButton.setBounds(x, y, width, height);
        newButton.setBackground(colour);
        newButton.setFont(B_FONT);

        if (!border) {
            newButton.setBorderPainted(false);
        }

        window.add(newButton);
        newButton.repaint();

        return newButton;
    }

    // MODIFIES: this
    // EFFECTS: initializes maze collection, mazeNames, JSON writer/reader, and window
    private void init() {
        collection = new MazeCollection();

        mazeNames = new ArrayList<>();
        Collections.addAll(mazeNames, "Alpha", "Beta", "Gamma", "Delta", "Epsilon", "Zeta",
                "Eta", "Theta", "Iota", "Kappa", "Lambda", "Mu",
                "Nu", "Xi", "Omicron", "Pi", "Rho", "Sigma",
                "Tau", "Upsilon", "Phi", "Chi", "Psi", "Omega");

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        window = new JFrame("Maze Runner");
        window.setSize(W_WIDTH, W_HEIGHT);
        window.getContentPane().setBackground(W_COLOUR);
        window.setVisible(true);
        window.setFocusable(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // MODIFIES: this
    // EFFECTS: fills window with main menu contents
    private void displayMenu() {
        window.getContentPane().removeAll();
        window.repaint();
        window.revalidate();

        JButton add = newButton("Add", W_WIDTH - B_WIDTH - 20, 5, B_WIDTH, B_HEIGHT, B_COLOUR, true);
        add.addActionListener(e -> addMaze());

        JButton save = newButton("Save", W_WIDTH - B_WIDTH - 20, 10 + B_HEIGHT, B_WIDTH, B_HEIGHT,
                B_COLOUR, true);
        save.addActionListener(e -> saveMazeCollection());

        JButton load = newButton("Load", W_WIDTH - B_WIDTH - 20, 15 + (2 * B_HEIGHT), B_WIDTH, B_HEIGHT,
                B_COLOUR, true);
        load.addActionListener(e -> loadMazeCollection());

        int x = 5;
        int y = 5;
        for (Maze maze : collection) {
            JButton mazeButton = newButton(maze.getName(), x, y, B_WIDTH, B_HEIGHT, B_COLOUR, true);
            mazeButton.addActionListener(e -> enterMaze(maze));

            y += 5 + B_HEIGHT;
            if ((W_HEIGHT - y) < B_HEIGHT) {
                x += 5 + B_WIDTH;
                y = 5;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a new maze to the collection (if there are less than MAX_MAZES) and updates the main menu
    //          assigns a random name from mazeNames
    private void addMaze() {
        if (collection.size() == MAX_MAZES) {
            JOptionPane.showMessageDialog(window, "Cannot have more than " + MAX_MAZES + " mazes.");
        } else {
            Maze maze = new Maze();
            int nameIndex = (int) (Math.random() * mazeNames.size());
            maze.setName(mazeNames.get(nameIndex));

            collection.add(maze);
            displayMenu();
        }
    }

    // EFFECTS: saves the collection to file
    private void saveMazeCollection() {
        try {
            jsonWriter.open();
            jsonWriter.write(collection);
            jsonWriter.close();
            JOptionPane.showMessageDialog(window, "Saved your mazes to: " + JSON_STORE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(window, "Unable to write to file: " + JSON_STORE
                    + "\nPlease ensure your data folder is in the same directory as Maze Runner.jar");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads the collection from file and updates the main menu
    private void loadMazeCollection() {
        try {
            collection = jsonReader.read();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(window, "Unable to read from file: " + JSON_STORE
                    + "\nPlease ensure your data folder is in the same directory as Maze Runner.jar");
        }

        displayMenu();
    }

    // MODIFIES: this
    // EFFECTS: instantiates a new runner in the given maze from the collection
    //          opens maze for use in window
    private void enterMaze(Maze maze) {
        runner = new Runner(maze);

        window.addKeyListener(keyHandler);
        displayMaze();
    }

    // MODIFIES: this
    // EFFECTS: fills window with current maze contents
    private void displayMaze() {
        window.getContentPane().removeAll();
        window.repaint();
        window.revalidate();

        JButton reset = newButton("Reset", W_WIDTH - B_WIDTH - 20, 5, B_WIDTH, B_HEIGHT, B_COLOUR, true);
        reset.addActionListener(e -> reset());

        JButton exit = newButton("Exit", W_WIDTH - B_WIDTH - 20, 10 + B_HEIGHT, B_WIDTH, B_HEIGHT, B_COLOUR, true);
        exit.addActionListener(e -> exitMaze());

        JButton delete = newButton("Delete", W_WIDTH - B_WIDTH - 20, 15 + (2 * B_HEIGHT), B_WIDTH, B_HEIGHT, B_COLOUR, true);
        delete.setForeground(Color.RED);
        delete.addActionListener(e -> displayDeleteWarning());

        passwordDisplay = new JTextField("Arrow keys to move, WASD to break a wall, Enter to generate password");
        passwordDisplay.setBounds(0, (Maze.ROWS * SLOT_SIZE) + 5, P_WIDTH, P_HEIGHT);
        window.add(passwordDisplay);

        Maze maze = runner.getMaze();

        for (int i = 0; i < Maze.ROWS; i++) {
            for (int j = 0; j < Maze.COLS; j++) {
                int content = maze.getSlot(j, i);
                if (content == 1) {
                    newButton("", j * SLOT_SIZE, i * SLOT_SIZE, SLOT_SIZE, SLOT_SIZE, WALL_COLOUR, false);
                } else if (content == 2) {
                    JLabel runnerImage = new JLabel(RUNNER_IMAGE);
                    runnerImage.setBounds(j * SLOT_SIZE, i * SLOT_SIZE, SLOT_SIZE, SLOT_SIZE);
                    window.add(runnerImage);
                } else if (content == 3) {
                    newButton("", j * SLOT_SIZE, i * SLOT_SIZE, SLOT_SIZE, SLOT_SIZE, TARGET_COLOUR, false);
                }
                // empty space if content == 0
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: resets the Runner and updates the window
    private void reset() {
        runner.reset();
        displayMaze();
    }

    // MODIFIES: this
    // EFFECTS: resets the current maze and returns to the main menu
    private void exitMaze() {
        runner.getMaze().reset();

        window.removeKeyListener(keyHandler);
        displayMenu();
    }

    // MODIFIES: this
    // EFFECTS: fills window with a confirmation warning for deleting the current maze
    private void displayDeleteWarning() {
        window.getContentPane().removeAll();
        window.repaint();
        window.revalidate();

        JOptionPane.showMessageDialog(window,
                "Are you sure you want to delete " + runner.getMaze().getName() + "?"
                + "\nWarning: this action cannot be undone. If you have used passwords generated from this maze for any"
                + "\naccounts, those passwords will not be recoverable.");

        JButton yes = newButton("Yes", 5, 5, B_WIDTH, B_HEIGHT, B_COLOUR, true);
        yes.setForeground(Color.RED);
        yes.addActionListener(e -> deleteCurrentMaze());

        JButton no = newButton("No", 10 + B_WIDTH, 5, B_WIDTH, B_HEIGHT, B_COLOUR, true);
        no.addActionListener(e -> displayMaze());
    }

    // MODIFIES: this
    // EFFECTS: deletes the current maze from the collection and returns to the main menu
    private void deleteCurrentMaze() {
        try {
            collection.remove(runner.getMaze());
        } catch (MazeNotFoundException e) {
            JOptionPane.showMessageDialog(window, "Error: Maze does not exist. Please restart the app.");
            window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
        }

        window.removeKeyListener(keyHandler);
        displayMenu();
    }

    /*
    A key handler to respond to key events
     */
    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            respondToKey(e.getKeyCode());
        }
    }

    // MODIFIES: this
    // EFFECTS: moves the runner in response to the pressed key and updates the window
    //          prints a generated password if the enter key is pressed
    private void respondToKey(int keyCode) {
        if (keyCode == 37) {
            runner.moveLeft();
        } else if (keyCode == 39) {
            runner.moveRight();
        } else if (keyCode == 38) {
            runner.moveUp();
        } else if (keyCode == 40) {
            runner.moveDown();
        } else if (keyCode == 65) {
            runner.breakWall("L");
        } else if (keyCode == 68) {
            runner.breakWall("R");
        } else if (keyCode == 87) {
            runner.breakWall("U");
        } else if (keyCode == 83) {
            runner.breakWall("D");
        } else if (keyCode == 10) {
            passwordDisplay.setText(runner.generatePassword());
        }

        if (keyCode != 10) {
            displayMaze();
        }
    }
}
