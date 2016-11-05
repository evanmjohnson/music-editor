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
  private JPanel panel = new JPanel();

  public JPanelColumn(int beat, List<Integer> start, List<Integer> cont) {
    super();
    this.beat = beat;
    this.start = start;
    this.cont = cont;
  }


  public void draw(MusicViewModel model) {
    int size = model.getNoteRange().size();
    this.setLayout(new GridLayout(size, 1));

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
      JPanelNoteInstance noteInstance;
      //noteInstance = new JPanelNoteInstance(left, right, top, bottom, beat, i);
      noteInstance = new JPanelNoteInstance();
      //System.out.println(noteInstance.toString());


      if (start.contains(i)) {
        noteInstance.setColor("start");
        this.add(noteInstance);
      }
      else if (cont.contains(i)) {
        noteInstance.setColor("continue");
        this.add(noteInstance);
      }
      else {
        noteInstance.setColor("none");
        this.add(noteInstance);
      }
    }

    this.setVisible(true);
  }


}
