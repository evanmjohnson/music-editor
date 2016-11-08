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
    super();
    this.beat = beat;
    this.start = start;
    this.cont = cont;
  }


  public void draw(MusicViewModel model) {
    int size = model.getNoteRange().size();
    this.setLayout(new GridLayout(size, 1));

    for (int i = 0; i < size; i++) {
      JPanelNoteInstance noteInstance;
      noteInstance = new JPanelNoteInstance();
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