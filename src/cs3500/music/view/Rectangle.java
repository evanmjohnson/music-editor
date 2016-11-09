package cs3500.music.view;

import java.awt.Color;

/**
 * Represents a rectangle to draw a Note in the visual view of the music editor. Supports
 * custom Colors.
 */
public class Rectangle {
  public final Color color;
  public final int x;
  public final int y;
  public final int width;
  public final int height;

  public Rectangle(int x, int y, int width, int height, Color color) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.color = color;
  }
}
