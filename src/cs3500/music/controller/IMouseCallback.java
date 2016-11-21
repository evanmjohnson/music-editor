package cs3500.music.controller;

/**
 * Interface used for giving the coordinates of the mouse event back to the controller.
 */
public interface IMouseCallback {
  /**
   * Checks the X and Y value of the most recent click against the model.
   * If there is a Note there, select it. If the third parameter is {@code true}, show the
   * user an option pane asking if they want to remove the note. If it is {@code false},
   * remove the note on click.
   *
   * @param x          the x-value of the mouse event
   * @param y          the y-value of the mouse event
   * @param showOption if the controller should show a JOptionPane asking for remove confirmation
   */
  void check(int x, int y, boolean showOption);
}