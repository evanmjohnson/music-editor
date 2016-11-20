package cs3500.music.view;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.*;

import cs3500.music.model.Note;

/**
 * Represents he panel on the left side of the GUI view that displays
 * the range of notes in the piece.
 */
public class RangePanel extends JPanel {
  List<Note> range;
  List<String> labels;

  public RangePanel() {

  }

  public void setNotes(List<Note> noteRange) {
    range = noteRange;
    Collections.reverse(range);
    labels = new ArrayList<>();
    for (Note n : range) {
      labels.add(n.toString());
    }
  }

  public void clearNotes() {
    this.range = new ArrayList<>();
  }

  @Override
  protected void paintComponent(Graphics g) {
    //never forget to call super.paintComponent!
    super.paintComponent(g);
    for (int i = 0; i < range.size(); i++) {
      g.setFont(new Font("Josephine Sans", Font.PLAIN, 18));
      g.drawString(labels.get(i).toString(), 0, 24 * i);
    }
  }
}
