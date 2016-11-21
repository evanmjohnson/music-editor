package cs3500.music.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Handles all mouse events for the music editor.
 */
public class MouseHandler implements MouseListener {
  IMouseCallback callback;

  public MouseHandler(IMouseCallback callback) {
    this.callback = callback;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    this.callback.check(e.getX(), e.getY());
  }

  @Override
  public void mousePressed(MouseEvent e) {
    this.callback.check(e.getX(), e.getY());
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    this.callback.check(e.getX(), e.getY());
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
