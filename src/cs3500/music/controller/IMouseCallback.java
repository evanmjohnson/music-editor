package cs3500.music.controller;

/**
 * Interface used for giving the coordinates of the mouse event back to the controller.
 */
public interface IMouseCallback {
  /**
   * Checks the X and Y value of the most recent click against the model.
   * If there is a Note there, select it.
   */
  void check(int x, int y);
}