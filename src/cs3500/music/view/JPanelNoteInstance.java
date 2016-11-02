package cs3500.music.view;

import javax.swing.*;
import java.awt.*;

/**
 * Represents one note at one beat inside a JPanelColumn in the music editor GUI.
 */
public class JPanelNoteInstance extends JPanel {

  public JPanelNoteInstance() {
    setOpaque(true);
    setSize(50, 50);
  }

  /**
   * Set the color of this box dependent on the note it represents
   * @param status Whether or not this note starts at, continues at, or does not exist
   *               at the current beat.
   */
  void setColor(String status) {
    switch (status) {
      case "start" :
        setBackground(Color.BLACK);
        break;
      case "continue" :
        setBackground(Color.YELLOW);
        break;
      default :
        setOpaque(false);
    }
  }

  void drawLine() {

  }
}
