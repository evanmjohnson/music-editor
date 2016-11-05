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
  private JPanelNoteInstance noteInstance;
  private JPanel panel = new JPanel();
  public JPanelColumn(int beat, List<Integer> start, List<Integer> cont) {
    super();
    this.beat = beat;
    this.start = start;
    this.cont = cont;
    this.setLayout(new GridLayout());
           // BoxLayout(this, BoxLayout.Y_AXIS));
  }


  public void draw(MusicViewModel model) {
   // System.out.println(model.getNoteRange().size());

    int size = model.getNoteRange().size();
   //System.out.println("Size" + size);

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
//      System.out.println("Beat:" + beat);
      noteInstance = new JPanelNoteInstance(left, right, top, bottom, beat, i);
//      System.out.println(noteInstance.toString());

      //this.add(noteInstance);
      //this.add(noteInstance);


//      if (start.contains(i)) {
//        System.out.println("START");
//        noteInstance.setColor("start");
//        this.add(noteInstance);
//      }
//      else if (cont.contains(i)) {
//        System.out.println("CONTINUE");
//        noteInstance.setColor("continue");
//        this.add(noteInstance);
//      }
//      else {
//        noteInstance.setColor("none");
//        this.add(noteInstance);
//      }
    }

    this.setVisible(true);
  }


}
