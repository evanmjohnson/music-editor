package cs3500.music.view;

import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.Box;

/**
 * Represents the panel of beats at the top of the GUI view.
 */
public class BeatPanel extends JPanel {
  int beats;


  @Override
  protected void paintComponent(Graphics g) {
    //never forget to call super.paintComponent!
    super.paintComponent(g);
    this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    for (int i = 0; i <= beats; i++) {
      if (i % 16 == 0) {
        if (i == 0) {
          this.add(Box.createRigidArea(new Dimension(30, 0)));
        } else {
          this.add(Box.createRigidArea(new Dimension(12 - (i / 16), 0)));
        }
        JLabel label = new JLabel(Integer.toString(i));
        label.setFont(new Font("Josephine Sans", Font.PLAIN, 18));
        this.add(label);
      } else {
        this.add(Box.createRigidArea(new Dimension(30, 0)));
      }
    }
  }

  public void setBeats(int numBeats) {
    beats = numBeats;


  }
}
