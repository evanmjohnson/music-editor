package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

/**
 * This class represents a keyboard listener. It is configurable by the controller that instantiates
 * it.
 * <p>
 * This listener keeps three maps, one each for key typed, key pressed and key released Each map
 * stores a key mapping. A key mapping is a pair (keystroke,code to be executed with that keystroke)
 * The latter part of that pair is actually a function object, i.e. an object of a class that
 * implements the Runnable interface
 * <p>
 * This class implements the KeyListener interface, so that its object can be used as a valid
 * keylistener for Java Swing.
 */
public class KeyboardHandler implements KeyListener {
  private Map<Integer, Runnable> keyTypedMap;
  private Map<Integer, Runnable> keyPressedMap;
  private Map<Integer, Runnable> keyReleasedMap;

  /**
   * Empty default constructor
   */
  public KeyboardHandler() {
  }

  /**
   * Set the map for key typed events. Ke y typed events in Java Swing are characters
   *
   * @param map The key typed events map
   */
  public void setKeyTypedMap(Map<Integer, Runnable> map) {
    this.keyTypedMap = map;
  }

  /**
   * Set the map for key pressed events. Key pressed events in Java Swing are integer codes.
   *
   * @param map The key pressed events map
   */
  public void setKeyPressedMap(Map<Integer, Runnable> map) {
    keyPressedMap = map;
  }

  /**
   * Set the map for key released events. Key released events in Java Swing are integer codes.
   *
   * @param map The key released events map
   */
  public void setKeyReleasedMap(Map<Integer, Runnable> map) {
    keyReleasedMap = map;
  }


  @Override
  public void keyTyped(KeyEvent e) {
    if (keyTypedMap.containsKey(e.getKeyCode())) {
      keyTypedMap.get(e.getKeyCode()).run();
    }
  }

  /**
   * This is called when the view detects that a key has been pressed. Find if anything has been
   * mapped to this key code and if so, execute it.
   *
   * @param e The key event that has been detected
   */
  @Override
  public void keyPressed(KeyEvent e) {
    if (keyPressedMap.containsKey(e.getKeyCode())) {
      keyPressedMap.get(e.getKeyCode()).run();
    }
  }

  /**
   * This is called when the view detects that a key has been released. Find if anything has been
   * mapped to this key code and if so, execute it.
   *
   * @param e The key event that has been detected
   */
  @Override
  public void keyReleased(KeyEvent e) {
    if (keyReleasedMap.containsKey(e.getKeyCode())) {
      keyReleasedMap.get(e.getKeyCode()).run();
    }
  }
}