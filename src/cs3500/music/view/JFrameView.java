package cs3500.music.view;

import cs3500.music.model.MusicViewModel;
import cs3500.music.model.Note;
import java.util.List;

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
    JPanel beatPanel = new JPanel();
    this.setLayout(new BorderLayout());
    notePanel.setLayout(new BoxLayout(notePanel, BoxLayout.Y_AXIS));
    for (Note n : model.getNoteRange()) {
      JLabel label = new JLabel(n.toString());
      label.setFont(new Font("Josephine Sans", Font.PLAIN, 18));
      notePanel.add(label);
      this.add(notePanel, BorderLayout.WEST);
    }

    beatPanel.setLayout(new BoxLayout(beatPanel, BoxLayout.X_AXIS));
    this.numBeats = model.getNumBeats();
    for (int i = 0; i <= numBeats; i++) {
      if (i % 16 == 0) {
        if(i == 0) {
          beatPanel.add(Box.createRigidArea(new Dimension(50, 0)));

        }
        JLabel label = new JLabel(Integer.toString(i));
        label.setFont(new Font("Josephine Sans", Font.PLAIN, 18));
        beatPanel.add(label);
      } else {
        beatPanel.add(Box.createRigidArea(new Dimension(50, 0)));
      }
      List<Integer> cont = model.notesContinueAtThisBeat(i);
      List<Integer> start = model.notesStartAtThisBeat(i);
      this.add(new JPanelColumn(i, start, cont), BorderLayout.CENTER);
    }
    this.add(beatPanel, BorderLayout.NORTH);
    this.add(notePanel);
    pack();
    this.showMusic();
  }

}
