package ru.tilacyn.ox;

import org.junit.Test;

import static org.junit.Assert.*;

public class LogicTest {
    private int fieldSize = 3;

    @Test
    public void resetField() throws Exception {
        Logic logic = new Logic(new TestController());

        logic.resetField();

        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                assertEquals(logic.getCell(i, j), 0);
            }
        }

    }

    @Test
    public void simplePress() throws Exception {
        Logic logic = new Logic(new TestController());

        logic.press(0, 0);
        logic.press(1, 0);
        logic.press(0, 1);

        assertEquals(logic.getCell(0, 0), 1);
        assertEquals(logic.getCell(1, 0), 2);
        assertEquals(logic.getCell(0, 1), 1);
        assertEquals(logic.getCell(2, 1), 0);

        logic.press(2, 1);

        assertEquals(logic.getCell(2, 1), 2);
    }

    @Test
    public void invalidPress() throws Exception {
        Logic logic = new Logic(new TestController());

        logic.press(0, 0);
        logic.press(0, 0);
        logic.press(0, 0);

        assertEquals(logic.getCell(0, 0), 1);

        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                if (i > 0 || j > 0) {
                    assertEquals(logic.getCell(i, j), 0);
                }
            }
        }
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void pressException() throws Exception {
        Logic logic = new Logic(new TestController());

        logic.press(0, 0);
        logic.press(0, 3);
    }

    @Test
    public void pressWithBot() throws Exception {
        Logic logic = new Logic(new TestController());
        logic.resetField();

        logic.pressWithBot(1, 1);

        int botX = -1;

        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                if (i != 1 || j != 1) {
                    if (logic.getCell(i, j) > 0) {
                        assertEquals(botX, -1);
                        botX = i;
                    }
                }
            }
        }

        assertNotEquals(botX, -1);

        logic.pressWithBot(1, 1);
    }

    @Test
    public void isEnd() throws Exception {
        Logic logic = new Logic(new TestController());
        logic.resetField();
        logic.press(1, 1);
        logic.press(1, 0);
        logic.press(0, 0);
        logic.press(2, 0);
        logic.press(2, 2);

        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                assertEquals(logic.getCell(i, j), 0);
            }
        }
    }

    private class TestController extends Controller {

        @Override
        public void backToMain() {
        }

        @Override
        public void draw(int i, int j, int currentPlayer) {
        }

        @Override
        public void addStatistics(int winner) {
            if (winner == 1) {
                XWins++;
            } else if (winner == 2) {
                OWins++;
            } else {
                draws++;
            }
        }

        @Override
        public void resetField() {
        }
    }

}