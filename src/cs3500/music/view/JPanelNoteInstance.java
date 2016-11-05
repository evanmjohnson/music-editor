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
  private Color color = Color.white;
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

//  void drawLine(int x, int y, Graphics g) {
//    Line2D line = new Line2D.Double(x, y, x + SIDE_LENGTH, y + SIDE_LENGTH);
//
//  }

  @Override
  protected void paintComponent(Graphics g) {
    //never forget to call super.paintComponent!
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;

    //g2d.setColor(Color.BLACK);

    /*
    the origin of the panel is top left. In order
    to make the origin bottom left, we must "flip" the
    y coordinates so that y = height - y

    We do that by using an affine transform. The flip
    can be specified as scaling y by -1 and then
    translating by height.
     */

   // AffineTransform originalTransform = g2d.getTransform();

    //the order of transforms is bottom-to-top
    //so as a result of the two lines below,
    //each y will first be scaled, and then translated
    //g2d.translate(0, this.getPreferredSize().getHeight());
   // g2d.scale(1, -1);

//    for (Line l : lines) {
//      Position2D start = l.start;
//      Position2D end = l.end;
//      g2d.drawLine((int) start.getX(), (int) start.getY(),
//              (int) end.getX(), (int) end.getY());
//    }

    //draw the turtle
    //an easy way to draw the turtle would be
    //to draw it in its default position, and then
    //rotate it by heading and translating it to
    //its actual position

    g2d.setColor(color);
    g2d.fillRect(0, 0, 50, 50);
    int width = 30;
    BasicStroke stroke = new BasicStroke(width);
    g2d.setStroke(stroke);
    if (this.left) {
      g2d.drawLine(0, 0, 0, 50);
    }


    //reset the transform to what it was!
   // g2d.setTransform(originalTransform);

  }

}
