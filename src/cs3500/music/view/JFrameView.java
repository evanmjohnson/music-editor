package cs3500.music.view;

import com.sun.prism.image.ViewPort;
import cs3500.music.model.MusicViewModel;
import cs3500.music.model.Note;
import cs3500.music.model.PitchType;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
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
    this.setSize(new Dimension(1920, 1200));
    this.setLocation(200, 200);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());
    this.notesPanel = new NotesPanel();
    this.beatPanel = new BeatPanel();
    this.rangePanel = new RangePanel();
    this.add(rangePanel, BorderLayout.WEST);
    this.add(beatPanel, BorderLayout.NORTH);
//    this.setResizable(false);
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
    this.reDrawNotes(model);
    this.rangePanel.clearNotes();
    this.rangePanel.setNotes(model.getNoteRange());
    this.beatPanel.repaint();
    this.rangePanel.revalidate();
    this.rangePanel.repaint();
  }

  @Override
  public void reDrawNotes(MusicViewModel model) {
    //this.notesPanel.removeRects();
    //this.createNotes(model);
    this.notesPanel.repaint();
  }

  @Override
  public Note showAddPrompt() {
    Object[] possibilities = PitchType.values();
    // ask the user about the details of the note to add
    Object pitchObject = JOptionPane.showInputDialog(this,
        "Select the pitch that you want the new note to have.\n",
        "Add a note", JOptionPane.PLAIN_MESSAGE, null, possibilities, PitchType.C);
    Object startBeatObject = JOptionPane.showInputDialog(this,
        "Enter a start beat for the note\n", "Add a note", JOptionPane.QUESTION_MESSAGE,
        null, null, null);
    Object durationObject = JOptionPane.showInputDialog(this,
        "Enter the duration of the note\n", "Add a note", JOptionPane.QUESTION_MESSAGE,
        null, null, null);
    Object octaveObject = JOptionPane.showInputDialog(this,
        "Enter a valid octave for the note\n", "Add a note", JOptionPane.QUESTION_MESSAGE,
        null, null, null);

    // convert the user's inputs to data for the model
//    if (pitchObject == null || startBeatObject == null || durationObject == null
//        || octaveObject == null) {
//      return;
//    }
    PitchType type = (PitchType) pitchObject;
    Integer startBeat = Integer.parseInt((String) startBeatObject);
    Integer duration = Integer.parseInt((String) durationObject);
    Integer octave = Integer.parseInt((String) octaveObject);
    return new Note(type, startBeat, duration, octave);
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
    vp.setViewPosition(new Point(vp.getX() + 250, vp.getY()));
    this.requestFocusInWindow();
  }

  @Override
  public void scrollLeft() {
    // this is wrong
    this.scrollPane.getHorizontalScrollBar().setValue(
        this.scrollPane.getHorizontalScrollBar().getValue() * -1);
  }

  @Override
  public boolean doRemove() {
    int option = JOptionPane.showConfirmDialog(null, "Do you want to remove this note?",
        "Are you sure?", JOptionPane.YES_NO_OPTION);
    if (option == 0) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public void setMouseListener(MouseListener mouse) {
    this.notesPanel.addMouseListener(mouse);
  }

  @Override
  public void moveRedLine() {
    this.notesPanel.moveRedLine();
  }

  @Override
  public void createRedLine() {
    this.notesPanel.createRedLine();
  }

  @Override
  public int getCurrentPosition() {
    return 0;
  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }
}