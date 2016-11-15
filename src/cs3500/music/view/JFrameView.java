package cs3500.music.view;

import cs3500.music.model.MusicViewModel;
import cs3500.music.model.Note;
import cs3500.music.model.PitchType;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.Collections;
import java.util.List;

/**
 * Displays a java Swing BoxLayout view for the music editor.
 */
public class JFrameView extends JFrame implements IMusicGUIView {

  /**
   * Constructs a JthisView with a border layout which has a NotesPanel inside of it.
   */
  public JFrameView() {
    super();
    this.setSize(new Dimension(500, 500));
    this.setLocation(200, 200);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());
    //this.setResizable(false);
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void create(MusicViewModel model) {
    this.add(this.createRange(model), BorderLayout.WEST);
    this.add(this.createBeats(model), BorderLayout.NORTH);
    NotesPanel notes = this.createNotes(model);
    JScrollPane scrollPane = new JScrollPane(notes,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    this.add(scrollPane);
  }

  private JPanel createRange(MusicViewModel model) {
    JPanel rangePanel = new JPanel();
    rangePanel.setLayout(new BoxLayout(rangePanel, BoxLayout.Y_AXIS));
    List<Note> range = model.getNoteRange();
    Collections.reverse(range);
    for (Note n : range) {
      JLabel label = new JLabel(n.toString());
      label.setFont(new Font("Josephine Sans", Font.PLAIN, 18));
      rangePanel.add(label);
      //this.add(rangePanel, BorderLayout.WEST);
    }
    return rangePanel;
  }

  private JPanel createBeats(MusicViewModel model) {
    JPanel beatPanel = new JPanel();
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
    return beatPanel;
  }

  private NotesPanel createNotes(MusicViewModel model) {
    NotesPanel notesPanel = new NotesPanel();
    notesPanel.setLines(model.getNumBeats(), model.getNoteRange().size());
    for (int i = 0; i < model.getNumBeats(); i++) {
      notesPanel.setNotes(model.notesStartAtThisBeat(i),
          model.notesContinueAtThisBeat(i), i);
    }
    return notesPanel;
  }


  @Override
  public Note showAddPrompt() {
    Object[] possibilities = PitchType.values();
    Object pitchObject = JOptionPane.showInputDialog(this,
        "Select the pitch that you want the new note to have.\n",
        "Add a note", JOptionPane.PLAIN_MESSAGE, null, possibilities, PitchType.C);
    PitchType type = (PitchType)pitchObject;
    Object startBeatObject = JOptionPane.showInputDialog(this,
        "Enter a start beat for the note\n", "Add a note", JOptionPane.QUESTION_MESSAGE,
        null, null, null);
    Integer startBeat = Integer.parseInt((String)startBeatObject);
    Object durationObject = JOptionPane.showInputDialog(this,
        "Enter the duration of the note\n", "Add a note", JOptionPane.QUESTION_MESSAGE,
        null, null, null);
    Integer duration = Integer.parseInt((String)durationObject);
    Object octaveObject = JOptionPane.showInputDialog(this,
        "Enter a valid octave for the note\n", "Add a note", JOptionPane.QUESTION_MESSAGE,
        null, null, null);
    Integer octave = Integer.parseInt((String)octaveObject);
    Note n = new Note(type, startBeat, duration, octave);
    return n;
  }

  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  @Override
  public void addListener(KeyListener kbd) {
    this.setFocusable(true);
    this.requestFocus();
    this.addKeyListener(kbd);
  }
}