package cs3500.music.tests;

import cs3500.music.controller.KeyboardHandler;
import org.junit.Test;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
/**
 * Tests that the KeyboardHandler class in the music editor runs the appropriate Runnables.
 */
public class TestKeyboardHandler {
  KeyboardHandler kbd = new KeyboardHandler();
  String keyString;
  Map<Integer, Runnable> keyReleased = new HashMap<>();
  StringBuilder sb;

  void init() {
    this.sb = new StringBuilder();
    keyReleased.put(KeyEvent.VK_A, () -> sb.append("A"));
    keyReleased.put(KeyEvent.VK_SPACE, () -> sb.append("space"));
    keyReleased.put(KeyEvent.VK_LEFT, () -> sb.append("left"));
    keyReleased.put(KeyEvent.VK_RIGHT, () -> sb.append("right"));
    kbd.setKeyReleasedMap(keyReleased);
  }

  @Test
  public void testKeyA() {
    this.init();
    this.keyString = "";
    keyReleased.get(KeyEvent.VK_A).run();
    assertEquals(this.sb.toString(), "A");
  }

  @Test
  public void testKeySpace() {
    this.init();
    this.keyString = "";
    keyReleased.get(KeyEvent.VK_SPACE).run();
    assertEquals(this.sb.toString(), "space");
  }

  @Test
  public void testKeyLeft() {
    this.init();
    this.keyString = "";
    keyReleased.get(KeyEvent.VK_LEFT).run();
    assertEquals(this.sb.toString(), "left");
  }

  @Test
  public void testKeyRight() {
    this.init();
    this.keyString = "";
    keyReleased.get(KeyEvent.VK_RIGHT).run();
    assertEquals(this.sb.toString(), "right");
  }
}
