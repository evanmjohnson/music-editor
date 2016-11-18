package cs3500.music.view;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

/**
 * Created by Shravali on 11/16/2016.
 */
public class BeatPanel extends JPanel {
  List<Integer> beats;

  public BeatPanel() {
    this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    beats = new ArrayList<Integer>();
  }

  @Override
  protected void paintComponent(Graphics g) {
    //never forget to call super.paintComponent!
    super.paintComponent(g);
   // System.out.println("paint");
    for (Integer i : beats) {
      JLabel label = new JLabel(Integer.toString(i));
      label.setFont(new Font("Josephine Sans", Font.PLAIN, 18));
      this.add(label);
    }
  }

  public void setBeats(int numBeats) {
    //System.out.println("SET");

    for (int i = 0; i <= numBeats; i++) {

      if (i % 16 == 0) {
        if (i == 0) {
          this.add(Box.createRigidArea(new Dimension(30, 0)));
        } else {
          this.add(Box.createRigidArea(new Dimension(12 - (i / 16), 0)));
        }
        beats.add(i);
        JLabel label = new JLabel(Integer.toString(i));
        label.setFont(new Font("Josephine Sans", Font.PLAIN, 18));
        this.add(label);
      } else {
        this.add(Box.createRigidArea(new Dimension(30, 0)));
      }
    }
  }
}
