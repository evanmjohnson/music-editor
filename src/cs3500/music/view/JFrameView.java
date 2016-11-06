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
  private JPanelColumn col;


  public JFrameView() {
    super();
    setSize(500, 300);
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setLayout(new BorderLayout());
  }

  @Override
  public void makeVisible() {

    this.setVisible(true);
  }

  @Override
  public void create(MusicViewModel model) {
    JPanel notePanel = new JPanel();
    JPanel beatPanel = new JPanel();
    notePanel.setLayout(new BoxLayout(notePanel, BoxLayout.Y_AXIS));

    // write the range of notes on the left side
    for (Note n : model.getNoteRange()) {
      JLabel label = new JLabel(n.toString());
      label.setFont(new Font("Josephine Sans", Font.PLAIN, 18));
      notePanel.add(label);
      this.add(notePanel, BorderLayout.WEST);
    }

    // create each of the beats on the top
    beatPanel.setLayout(new BoxLayout(beatPanel, BoxLayout.X_AXIS));
    this.numBeats = model.getNumBeats();
    for (int i = 0; i <= numBeats; i++) {
      if (i % 16 == 0) {
        if (i == 0) {
          beatPanel.add(Box.createRigidArea(new Dimension(50, 0)));
        }
        JLabel label = new JLabel(Integer.toString(i));
        label.setFont(new Font("Josephine Sans", Font.PLAIN, 18));
        beatPanel.add(label);
      } else {
        beatPanel.add(Box.createRigidArea(new Dimension(50, 0)));
      }
    }

    // create all of the notes in the middle
    JPanel noteGrid = new JPanel();
    noteGrid.setLayout(new GridLayout(1, this.numBeats));
    for (int i = 0; i <= numBeats; i++) {
      List<Integer> cont = model.notesContinueAtThisBeat(i);
      List<Integer> start = model.notesStartAtThisBeat(i);
      col = new JPanelColumn(i, start, cont);
      col.draw(model);
      //this.add(col, BorderLayout.CENTER);
      noteGrid.add(col);
    }

    this.add(beatPanel, BorderLayout.NORTH);
    this.add(noteGrid, BorderLayout.CENTER);
    pack();
  }

}
