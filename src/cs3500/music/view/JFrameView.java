package cs3500.music.view;

import cs3500.music.model.MusicViewModel;
import cs3500.music.model.Note;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.ScrollPaneConstants;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Font;

/**
 * Displays a java Swing BoxLayout view for the music editor.
 */
public class JFrameView extends JFrame implements IMusicView {
  /**
   * Constructs a JFrameView with a border layout which has a NotesPanel inside of it.
   */
  private NotesPanel notesPanel;


  public JFrameView() {
    super();
    setSize(new Dimension(500, 500));
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());
    //this.setResizable(false);
    this.notesPanel = new NotesPanel();
    //change dimension to reflect notes and beats
    ;
    //scrollPane.setPreferredSize(new Dimension(200, 200));

    //this.add(notesPanel, BorderLayout.CENTER);
  }

  @Override
  public void makeVisible() {

    this.setVisible(true);
  }

  @Override
  public void create(MusicViewModel model) {
    System.out.println(model.getNumBeats());
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
    int numBeats = model.getNumBeats();
    for (int i = 0; i <= numBeats; i++) {
      if (i % 16 == 0) {
        if (i == 0) {
          beatPanel.add(Box.createRigidArea(new Dimension(30, 0)));
        } else {
          beatPanel.add(Box.createRigidArea(new Dimension(12 - (i / 16), 0)));
        }
        JLabel label = new JLabel(Integer.toString(i));
        label.setFont(new Font("Josephine Sans", Font.PLAIN, 18));
        beatPanel.add(label);
      } else {
        beatPanel.add(Box.createRigidArea(new Dimension(30, 0)));
      }
    }
    this.add(beatPanel, BorderLayout.NORTH);
    notesPanel.setLines(model.getNumBeats(), model.getNoteRange().size());
    for (int i = 0; i < numBeats; i++) {
      notesPanel.setNotes(model.notesStartAtThisBeat(i), model.notesContinueAtThisBeat(i), i);
    }
    JScrollPane scrollPane = new JScrollPane(notesPanel,
        ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    this.add(scrollPane, BorderLayout.CENTER);
//    pack();
  }


}