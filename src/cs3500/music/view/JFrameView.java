package cs3500.music.view;

import cs3500.music.model.MusicViewModel;
import cs3500.music.model.Note;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;

import java.awt.*;

/**
 * Displays a java Swing BoxLayout view for the music editor.
 */
public class JFrameView extends JFrame implements IMusicView {

  private int measureLength;
  private int numBeats;
  private JPanel[][] panels;


  public JFrameView() {
    super();
    //setPreferredSize(new Dimension(1500, 300));
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void create(MusicViewModel model) {
    int cols = model.getNumBeats() + 1;
    int rows = model.getNoteRange().size() + 1;
    this.panels = new JPanel[rows][cols];
    for (JPanel[] row : panels) {
      Arrays.fill(row, new JPanel());
    }
    JPanel notePanel = new JPanel();
    JPanel beatPanel = new JPanel();
    notePanel.setLayout(new BoxLayout(notePanel, BoxLayout.Y_AXIS));
    notePanel.setBackground(new Color(238, 238, 238));
    this.setLayout(new BorderLayout());
    //this.setPreferredSize(new Dimension(cols * 50, rows * 25));

    // write the range of notes on the left side
    List<Note> range = model.getNoteRange();
    for (Note n : range) {
      JLabel label = new JLabel(n.toString());
      label.setFont(new Font("Josephine Sans", Font.PLAIN, 18));
      notePanel.add(label);
    }
    this.add(notePanel, BorderLayout.WEST);

    // create each of the beats on the top
    beatPanel.setBackground(new Color(238, 238, 238));
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
    this.add(beatPanel, BorderLayout.NORTH);

    // create all of the notes in the middle
    JPanel noteGrid = new JPanel();
    noteGrid.setLayout(new GridLayout());
    for (int i = 0; i <= numBeats; i++) {
      List<Integer> cont = model.notesContinueAtThisBeat(i);
      List<Integer> start = model.notesStartAtThisBeat(i);
      List<Integer> not = new ArrayList<>();
      for (int k = 0; k < range.size(); k++) {
        not.add(k);
      }
      for (Integer s : start) {
        JPanelNoteInstance noteInstance = new JPanelNoteInstance();
        noteInstance.setColor("start");
        this.panels[s][i].add(noteInstance);
        not.remove(s);
      }
      for (Integer c : cont) {
        JPanelNoteInstance noteInstance = new JPanelNoteInstance();
        noteInstance.setColor("continue");
        this.panels[c][i].add(noteInstance);
        not.remove(c);
      }
      for (Integer n : not) {
        JPanelNoteInstance noteInstance = new JPanelNoteInstance();
        noteInstance.setColor("none");
        this.panels[n][i].add(noteInstance);
      }
    }

    for (JPanel[] row : panels) {
      for (JPanel cell : row) {
        noteGrid.add(cell);
      }
    }
    this.add(noteGrid, BorderLayout.CENTER);

    pack();
  }

}
