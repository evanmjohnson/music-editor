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

  public RangePanel() {

  }

  public void setNotes(List<Note> noteRange) {
    range = noteRange;
    Collections.reverse(range);
  }

  public void clearNotes() {
    this.range = new ArrayList<>();
  }

  @Override
  protected void paintComponent(Graphics g) {
    //never forget to call super.paintComponent!
    super.paintComponent(g);
    //this.setLayout(new GridLayout(range.size(), 1, 1, 1));
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    for (Note n : range) {
      JLabel label = new JLabel(n.toString());
      label.setFont(new Font("Josephine Sans", Font.PLAIN, 18));
      this.add(label);
    }
    range = new ArrayList<>();
  }
}
