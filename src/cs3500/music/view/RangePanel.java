package cs3500.music.view;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.*;

import cs3500.music.model.Note;

/**
 * Created by Shravali on 11/17/2016.
 */
public class RangePanel extends JPanel {
  List<Note> range;

//  public RangePanel() {
//    range = new ArrayList<>();
//  }

  public void setNotes(List<Note> noteRange) {
    System.out.println("SET Notes");
    range = noteRange;
    System.out.println(range.size());
  }

  @Override
  protected void paintComponent(Graphics g) {
    System.out.println("PAINT Notes");
    //never forget to call super.paintComponent!
    super.paintComponent(g);
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    Collections.reverse(range);
    for (Note n : range) {
      JLabel label = new JLabel(n.toString());
      label.setFont(new Font("Josephine Sans", Font.PLAIN, 18));
      this.add(label);
    }
    range = new ArrayList<>();
  }
}
