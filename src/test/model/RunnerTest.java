package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RunnerTest {
    private Runner runner;

    @BeforeEach
    public void setup() {
        runner = new Runner(new Maze());
    }

    @Test
    public void testConstructor() {
        assertEquals((Maze.COLS / 2) - 1, runner.getPosX());
        assertEquals((Maze.ROWS / 2) - 1, runner.getPosY());
        assertEquals(0, runner.getActions().size());
    }

    @Test
    public void testMoveLeft() {
        int ogPosX = runner.getPosX();
        int ogPosY = runner.getPosY();
        runner.setPosX(0);

        runner.moveLeft();

        assertEquals(0, runner.getPosX());
        assertEquals(ogPosY, runner.getPosY());
        assertEquals(2, runner.getMaze().getSlot(ogPosX, ogPosY));

        runner.setPosX(ogPosX);
        runner.getMaze().editSlot(ogPosX - 1, ogPosY, 1);

        runner.moveLeft();

        assertEquals(ogPosX, runner.getPosX());
        assertEquals(ogPosY, runner.getPosY());
        assertEquals(2, runner.getMaze().getSlot(ogPosX, ogPosY));

        runner.getMaze().editSlot(ogPosX - 1, ogPosY, 0);

        runner.moveLeft();

        assertEquals(ogPosX - 1, runner.getPosX());
        assertEquals(ogPosY, runner.getPosY());
        assertEquals(0, runner.getMaze().getSlot(ogPosX, ogPosY));
        assertEquals(2, runner.getMaze().getSlot(ogPosX - 1, ogPosY));

        assertEquals("fail", runner.getActions().get(0).getDescription());
        assertEquals("fail", runner.getActions().get(1).getDescription());
        assertEquals("left", runner.getActions().get(2).getDescription());
    }

    @Test
    public void testMoveRight() {
        int ogPosX = runner.getPosX();
        int ogPosY = runner.getPosY();
        runner.setPosX(Maze.COLS - 1);

        runner.moveRight();

        assertEquals(Maze.COLS - 1, runner.getPosX());
        assertEquals(ogPosY, runner.getPosY());
        assertEquals(2, runner.getMaze().getSlot(ogPosX, ogPosY));

        runner.setPosX(ogPosX);
        runner.getMaze().editSlot(ogPosX + 1, ogPosY, 1);

        runner.moveRight();

        assertEquals(ogPosX, runner.getPosX());
        assertEquals(ogPosY, runner.getPosY());
        assertEquals(2, runner.getMaze().getSlot(ogPosX, ogPosY));

        runner.getMaze().editSlot(ogPosX + 1, ogPosY, 0);

        runner.moveRight();

        assertEquals(ogPosX + 1, runner.getPosX());
        assertEquals(ogPosY, runner.getPosY());
        assertEquals(0, runner.getMaze().getSlot(ogPosX, ogPosY));
        assertEquals(2, runner.getMaze().getSlot(ogPosX + 1, ogPosY));

        assertEquals("fail", runner.getActions().get(0).getDescription());
        assertEquals("fail", runner.getActions().get(1).getDescription());
        assertEquals("right", runner.getActions().get(2).getDescription());
    }

    @Test
    public void testMoveUp() {
        int ogPosX = runner.getPosX();
        int ogPosY = runner.getPosY();
        runner.setPosY(0);

        runner.moveUp();

        assertEquals(ogPosX, runner.getPosX());
        assertEquals(0, runner.getPosY());
        assertEquals(2, runner.getMaze().getSlot(ogPosX, ogPosY));

        runner.setPosY(ogPosY);
        runner.getMaze().editSlot(ogPosX, ogPosY - 1, 1);

        runner.moveUp();

        assertEquals(ogPosX, runner.getPosX());
        assertEquals(ogPosY, runner.getPosY());
        assertEquals(2, runner.getMaze().getSlot(ogPosX, ogPosY));

        runner.getMaze().editSlot(ogPosX, ogPosY - 1, 0);

        runner.moveUp();

        assertEquals(ogPosX, runner.getPosX());
        assertEquals(ogPosY - 1, runner.getPosY());
        assertEquals(0, runner.getMaze().getSlot(ogPosX, ogPosY));
        assertEquals(2, runner.getMaze().getSlot(ogPosX, ogPosY - 1));

        assertEquals("fail", runner.getActions().get(0).getDescription());
        assertEquals("fail", runner.getActions().get(1).getDescription());
        assertEquals("up", runner.getActions().get(2).getDescription());
    }

    @Test
    public void testMoveDown() {
        int ogPosX = runner.getPosX();
        int ogPosY = runner.getPosY();
        runner.setPosY(Maze.ROWS - 1);

        runner.moveDown();

        assertEquals(ogPosX, runner.getPosX());
        assertEquals(Maze.ROWS - 1, runner.getPosY());
        assertEquals(2, runner.getMaze().getSlot(ogPosX, ogPosY));

        runner.setPosY(ogPosY);
        runner.getMaze().editSlot(ogPosX, ogPosY + 1, 1);

        runner.moveDown();

        assertEquals(ogPosX, runner.getPosX());
        assertEquals(ogPosY, runner.getPosY());
        assertEquals(2, runner.getMaze().getSlot(ogPosX, ogPosY));

        runner.getMaze().editSlot(ogPosX, ogPosY + 1, 0);

        runner.moveDown();

        assertEquals(ogPosX, runner.getPosX());
        assertEquals(ogPosY + 1, runner.getPosY());
        assertEquals(0, runner.getMaze().getSlot(ogPosX, ogPosY));
        assertEquals(2, runner.getMaze().getSlot(ogPosX, ogPosY + 1));

        assertEquals("fail", runner.getActions().get(0).getDescription());
        assertEquals("fail", runner.getActions().get(1).getDescription());
        assertEquals("down", runner.getActions().get(2).getDescription());
    }

    @Test
    public void testBreakWall() {
        int posX = runner.getPosX();
        int posY = runner.getPosY();

        runner.getMaze().editSlot(posX - 1, posY, 1);
        runner.getMaze().editSlot(posX + 1, posY, 1);
        runner.getMaze().editSlot(posX, posY - 1, 1);
        runner.getMaze().editSlot(posX, posY + 1, 1);

        runner.breakWall("L");
        runner.breakWall("R");
        runner.breakWall("U");
        runner.breakWall("D");
        runner.breakWall("D");

        assertEquals(0, runner.getMaze().getSlot(posX - 1, posY));
        assertEquals(0, runner.getMaze().getSlot(posX + 1, posY));
        assertEquals(0, runner.getMaze().getSlot(posX, posY - 1));
        assertEquals(0, runner.getMaze().getSlot(posX, posY + 1));

        runner.setPosX(0);
        runner.setPosY(0);

        runner.breakWall("L");

        assertEquals("break", runner.getActions().get(0).getDescription());
        assertEquals("break", runner.getActions().get(1).getDescription());
        assertEquals("break", runner.getActions().get(2).getDescription());
        assertEquals("break", runner.getActions().get(3).getDescription());
        assertEquals("fail", runner.getActions().get(4).getDescription());
        assertEquals("fail", runner.getActions().get(5).getDescription());
    }

    @Test
    public void testReset() {
        int ogSlot = runner.getMaze().getSlot(runner.getPosX(), runner.getPosY() - 1);

        if (ogSlot == 1) {
            runner.breakWall("U");
        }

        runner.moveUp();

        runner.reset();

        assertEquals((Maze.COLS / 2) - 1, runner.getPosX());
        assertEquals((Maze.ROWS / 2) - 1, runner.getPosY());
        assertEquals(ogSlot, runner.getMaze().getSlot(runner.getPosX(), runner.getPosY() - 1));
        assertEquals(0, runner.getActions().size());
    }

    @Test
    public void testGeneratePassword() {
        assertEquals(0, runner.generatePassword().length());

        runner.moveLeft();
        String password1 = runner.generatePassword();

        assertEquals(1, password1.length());

        runner.moveDown();
        String password2 = runner.generatePassword();

        assertEquals(2, password2.length());
        assertNotEquals(password1.substring(0, 1), password2.substring(0, 1));

        runner.breakWall("L");
        String password3 = runner.generatePassword();

        assertEquals(3, runner.generatePassword().length());
        assertNotEquals(password2.substring(0, 1), password3.substring(0, 1));

        runner.reset();
        assertEquals(0, runner.generatePassword().length());
    }
}
