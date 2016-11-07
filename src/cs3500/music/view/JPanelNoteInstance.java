package cs3500.music.view;

import javax.swing.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

/**
 * Represents one note at one beat inside a JPanelColumn in the music editor GUI.
 */
public class JPanelNoteInstance extends JPanel {
  private final int SIDE_LENGTH = 50;
  private Color color = Color.LIGHT_GRAY;
  private boolean left;
  private boolean right;
  private boolean top;
  private boolean bottom;
  private int x;
  private int y;

  public JPanelNoteInstance(boolean left, boolean right, boolean top, boolean bottom, int x, int y) {
    super();
    this.left = left;
    this.right = right;
    this.top = top;
    this.bottom = bottom;
    this.x = x;
    this.y = y;
    setOpaque(true);
    setSize(50, 50);
  }

  public JPanelNoteInstance() {
    super();
    setOpaque(true);
    setSize(50, 50);
  }

  /**
   * Set the color of this box dependent on the note it represents
   *
   * @param status Whether or not this note starts at, continues at, or does not exist at the
   *               current beat.
   */
  void setColor(String status) {
    switch (status) {
      case "start":
        color = Color.orange;
        break;
      case "continue":
        color = Color.blue;
        break;
      case "none" :
      default:
        break;
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;

    g2d.setColor(color);
    g2d.fillRect(0, 0, 50, 50);
    int width = 30;
    BasicStroke stroke = new BasicStroke(width);
    g2d.setStroke(stroke);
    if (this.left) {
      g2d.drawLine(0, 0, 0, 50);
    }

  }

}
