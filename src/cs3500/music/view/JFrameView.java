package cs3500.music.view;

import cs3500.music.model.MusicViewModel;
import cs3500.music.model.Note;

import javax.swing.*;

import java.awt.*;

/**
 * Displays a java Swing BoxLayout view for the music editor.
 */
public class JFrameView extends JFrame implements IMusicView {

  private int measureLength;
  private int numBeats;
  private JLabel display;

  public void showMusic() {
    setVisible(true);
  }

  @Override
  public void draw(MusicViewModel model) {
    setSize(500, 300);
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    JPanel newPanel = new JPanelColumn(model.notesStartAtThisBeat(),
//        model.notesContinueAtThisBeat());
    JPanel notePanel = new JPanel();

    this.setLayout(new BorderLayout());
    notePanel.setLayout(new BoxLayout(notePanel, BoxLayout.Y_AXIS));
    for (Note n : model.getNoteRange()) {
      notePanel.add(new JLabel(n.toString()));
      this.add(notePanel, BorderLayout.WEST);
    }

    this.numBeats = model.getNumBeats();
    for (int i = 0; i <= numBeats; i++) {
      if (numBeats % 16 == 0) {
        this.add(new JLabel(Integer.toString(i)), BorderLayout.NORTH);
      } else {
        this.add(new JLabel(" "), BorderLayout.NORTH);
      }
      this.add(new JPanelColumn(i), BorderLayout.CENTER);
    }
    this.add(notePanel);
    pack();
    this.showMusic();
  }

}
