package cs3500.music.view;

import javax.swing.*;
import java.awt.*;

/**
 * Represents one note at one beat inside a JPanelColumn in the music editor GUI.
 */
public class JPanelNoteInstance extends JPanel {
  Color color = new Color(238, 238, 238);

  /**
   * Creates a new JPanelNoteInstance object. Sets the size to 50x50 and opaque.
   */
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
      case "start" :
        color = Color.orange;
        break;
      case "continue" :
        color = Color.blue;
        break;
      case "none" :
      default :
        break;
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(color);
    g2d.fillRect(0, 0, 100, 100);
    g2d.setColor(Color.black);
    g2d.setStroke(new BasicStroke(2));
    g2d.drawLine(0, 0, 0, 50);
    g2d.drawLine(0, 0, 50, 0);
    g2d.drawLine(50, 50, 0, 50);
    g2d.drawLine(50, 50, 50, 0);
  }
}
