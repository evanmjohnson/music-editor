package cs3500.music.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;

/**
 * Handles all mouse events for the music editor.
 */
public class MouseHandler implements MouseListener {
  private Map<Integer, Runnable> mousePressedMap;
  private Map<Integer, Runnable> mouseReleasedMap;
  private Map<Integer, Runnable> mouseClickedMap;

  public void setMouseClickedMap(Map<Integer, Runnable> map) {
    this.mouseClickedMap = map;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if (this.mouseClickedMap.containsKey(e)) {
      this.mouseClickedMap.get(e).run();
    }
  }

  public void setMousePressedMap(Map<Integer, Runnable> map) {
    this.mousePressedMap = map;
  }

  @Override
  public void mousePressed(MouseEvent e) {
    if (this.mousePressedMap.containsKey(e)) {
      this.mousePressedMap.get(e).run();
    }
  }

  public void setMouseReleasedMap(Map<Integer, Runnable> map) {
    this.mouseReleasedMap = map;
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    if (this.mouseReleasedMap.containsKey(e)) {
      this.mouseReleasedMap.get(e).run();
    }
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    // we did not implement any events using mouseEntered
  }

  @Override
  public void mouseExited(MouseEvent e) {
    // we did not implement any events using mouseExited
  }
}
