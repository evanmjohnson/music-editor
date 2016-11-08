package cs3500.music.view;

import cs3500.music.model.MusicViewModel;
import cs3500.music.model.Note;

import javax.swing.*;

import java.awt.*;
import java.util.*;

/**
 * Displays a java Swing BoxLayout view for the music editor.
 */
public class JFrameView extends JFrame implements IMusicView {

  private int numBeats;
  private NotesPanel notesPanel;


  public JFrameView() {
    super();
    setSize(500, 300);
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());
    notesPanel = new NotesPanel();
    //change dimension to reflect notes and beats
    notesPanel.setPreferredSize(new Dimension(500, 500));
    this.add(notesPanel, BorderLayout.CENTER);
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
    this.add(beatPanel, BorderLayout.NORTH);
    notesPanel.setLines(model.getNumBeats(), model.getNoteRange().size());
    pack();
  }



}