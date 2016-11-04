package cs3500.music.view;

import java.awt.*;
import java.util.List;

import javax.swing.*;

import cs3500.music.model.MusicViewModel;

/**
 * Represents column panels of every beat in the visual music editor GUI.
 */
public class JPanelColumn extends JPanel {
  int beat;
  List<Integer> start;
  List<Integer> cont;

  public JPanelColumn(int beat, List<Integer> start, List<Integer> cont) {
    this.beat = beat;
    this.start = start;
    this.cont = cont;
  }


  public void draw(MusicViewModel model) {
    System.out.println(model.getNoteRange().size());
    int size = model.getNoteRange().size();
    System.out.println(size);
    for (int i = 0; i < size; i++) {
      boolean left = false;
      boolean right = false;
      boolean top = false;
      boolean bottom = true;
      if (beat == 0) {
        left = true;
      }
      if (beat == model.getNumBeats()) {
        right = true;
      }
      //change to customize measure later
      if (beat != 0 && beat % 4 == 0) {
        right = true;
      }
      if (i == 0) {
        top = true;
      }
      JPanelNoteInstance note = new JPanelNoteInstance(left, right, top, bottom, beat, i);
      if (start.contains(i)) {
        note.setColor("start");
      }
      else if (cont.contains(i)) {
        note.setColor("continue");
      }
      else {
        note.setColor("none");
      }
    }
    this.setVisible(true);
  }


}
