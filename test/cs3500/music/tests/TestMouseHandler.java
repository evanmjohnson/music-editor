package cs3500.music.tests;

import cs3500.music.controller.IMouseCallback;
import cs3500.music.controller.MouseHandler;
import org.junit.Test;

import java.awt.event.MouseEvent;
import java.awt.Button;

import static org.junit.Assert.assertEquals;

/**
 * Tests the MouseHandler class from the music editor.
 */
public class TestMouseHandler {
  int testX;
  int testY;

  private class ConcreteMouseCallback implements IMouseCallback {

    @Override
    public void check(int x, int y, boolean showOption) {
      testX = x;
      testY = y;
    }
  }

  MouseHandler mouse = new MouseHandler(new ConcreteMouseCallback());

  @Test
  public void testMouseClicked() {
    MouseEvent e = new MouseEvent(new Button(), 0, 0, 0, 5, 10, 0, false);
    mouse.mouseClicked(e);
    assertEquals(e.getX(), this.testX);
    assertEquals(e.getY(), this.testY);
  }

  @Test
  public void testMousePressed() {
    MouseEvent e = new MouseEvent(new Button(), 0, 0, 0, 15, 20, 0, false);
    mouse.mousePressed(e);
    assertEquals(e.getX(), this.testX);
    assertEquals(e.getY(), this.testY);
  }

  @Test
  public void testMouseReleased() {
    MouseEvent e = new MouseEvent(new Button(), 0, 0, 0, 25, 30, 0, false);
    mouse.mouseReleased(e);
    assertEquals(e.getX(), this.testX);
    assertEquals(e.getY(), this.testY);
  }
}
