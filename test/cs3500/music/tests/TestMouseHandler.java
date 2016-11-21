package cs3500.music.tests;

import cs3500.music.controller.IMouseCallback;
import cs3500.music.controller.KeyboardHandler;
import cs3500.music.controller.MouseHandler;
import org.junit.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests the MouseHandler class from the music editor.
 */
public class TestMouseHandler {
  int testX;
  int testY;

  private class ConcreteMouseCallback implements IMouseCallback {

    @Override
    public void check(int x, int y) {
      testX = x;
      testY = y;
    }
  }

  MouseHandler mouse = new MouseHandler(new ConcreteMouseCallback());

  @Test
  public void testMouseClicked() {
    mouse.mouseClicked(new MouseEvent(new Button(), 0, 0, 0, 5, 10, 0, false));
    assertEquals(5, this.testX);
    assertEquals(10, this.testY);
  }

  @Test
  public void testMousePressed() {
    MouseEvent e = new MouseEvent(new Button(), 0, 0, 0, 15, 20, 0, false);
    mouse.mousePressed(e);
    assertEquals(15, this.testX);
    assertEquals(20, this.testY);
  }

  @Test
  public void testMouseReleased() {
    mouse.mouseReleased(new MouseEvent(new Button(), 0, 0, 0, 25, 30, 0, false));
    assertEquals(25, this.testX);
    assertEquals(30, this.testY);
  }
}
