package cs3500.music.view;


/**
 * Represents one line to be drawn by the visual view of the music editor.
 */
public final class Line {
  public final int x0;
  public final int y0;
  public final int x1;
  public final int y1;

  /**
   * Constructs a line with the given points.
   * @param x0 The initial x position
   * @param y0 The initial y position
   * @param x1 The final x position
   * @param y1 The final y position
   */
  public Line(int x0, int y0, int x1, int y1) {
    this.x0 = x0;
    this.y0 = y0;
    this.x1 = x1;
    this.y1 = y1;
  }
}