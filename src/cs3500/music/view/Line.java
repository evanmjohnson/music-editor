package cs3500.music.view;


import java.awt.Color;

/**
 * Represents one line to be drawn by the visual view of the music editor.
 */
public final class Line {
  public final int x0;
  public final int y0;
  public final int x1;
  public final int y1;
  public final boolean thick;
  public Color color;

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
    this.thick = false;
    this.color = Color.black;
  }

  /**
   * Constructs a line with the given points and thickness boolean.
   * @param x0 The initial x position
   * @param y0 The initial y position
   * @param x1 The final x position
   * @param y1 The final y position
   * @param thick if this line is thicker than the rest or not
   */
  public Line(int x0, int y0, int x1, int y1, boolean thick) {
    this.x0 = x0;
    this.y0 = y0;
    this.x1 = x1;
    this.y1 = y1;
    this.thick = thick;
    this.color = Color.black;
  }

  /**
   * Constructs a line with the given points, thickness boolean, and color.
   * @param x0 The initial x position
   * @param y0 The initial y position
   * @param x1 The final x position
   * @param y1 The final y position
   * @param thick if this line is thicker than the rest or not
   * @param color the color of the line
   */
  public Line(int x0, int y0, int x1, int y1, boolean thick, Color color) {
    this.x0 = x0;
    this.y0 = y0;
    this.x1 = x1;
    this.y1 = y1;
    this.thick = thick;
    this.color = color;
  }
}