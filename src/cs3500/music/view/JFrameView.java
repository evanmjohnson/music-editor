package cs3500.music.view;

import com.sun.prism.image.ViewPort;
import cs3500.music.model.MusicViewModel;
import cs3500.music.model.Note;
import cs3500.music.model.PitchType;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.Collections;
import java.util.List;

/**
 * Displays a java Swing BoxLayout view for the music editor.
 */
public class JFrameView extends JFrame implements IMusicGUIView {
  private JScrollPane scrollPane;
  private NotesPanel notesPanel;
  private RangePanel rangePanel;
  private BeatPanel beatPanel;

  /**
   * Constructs a JFrameView with a border layout which has a NotesPanel inside of it.
   */
  public JFrameView() {
    super();
    this.setSize(new Dimension(500, 500));
    this.setLocation(200, 200);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());
    this.notesPanel = new NotesPanel();
    this.beatPanel = new BeatPanel();
    this.rangePanel = new RangePanel();
    //this.setResizable(false);
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void create(MusicViewModel model) {
    //this.rangePanel = this.createRange(model);
    beatPanel.setBeats(model.getNumBeats());
    rangePanel.setNotes(model.getNoteRange());
    this.add(rangePanel, BorderLayout.WEST);
    this.add(beatPanel, BorderLayout.NORTH);
    NotesPanel notes = this.createNotes(model);
    scrollPane = new JScrollPane(notes,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    this.add(scrollPane, BorderLayout.CENTER);
  }




  private NotesPanel createNotes(MusicViewModel model) {
    notesPanel.setLines(model.getNumBeats(), model.getNoteRange().size());
    for (int i = 0; i < model.getNumBeats(); i++) {
      notesPanel.setNotes(model.notesStartAtThisBeat(i),
          model.notesContinueAtThisBeat(i), i);
    }
    return notesPanel;
  }

  public void reDraw(MusicViewModel model) {
    this.notesPanel.removeRects();
    this.createNotes(model);
    this.rangePanel.setNotes(model.getNoteRange());
    this.notesPanel.repaint();
    this.beatPanel.repaint();
    this.rangePanel.revalidate();
    this.rangePanel.repaint();
  }

  @Override
  public void showSelected(MusicViewModel model) {

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

  @Override
  public void scrollRight() {
    JViewport vp = this.scrollPane.getViewport();
    System.out.println(vp.getX() + " " + vp.getY());
    vp.setViewPosition(new Point((int)(vp.getX() + 200), vp.getY()));
    System.out.println(vp.getX() + " " + vp.getY());
    this.notesPanel.repaint();
    this.requestFocusInWindow();
  }

  @Override
  public void scrollLeft() {
    // this is wrong
    this.scrollPane.getHorizontalScrollBar().setValue(
        this.scrollPane.getHorizontalScrollBar().getValue() * -1);
  }
}