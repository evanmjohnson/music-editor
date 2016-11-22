package cs3500.music.view;

import cs3500.music.model.MusicViewModel;
import cs3500.music.model.Note;
import cs3500.music.model.PitchType;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

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
    this.setPreferredSize(new Dimension(1000, 1000));
    this.setLocation(200, 200);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void create(MusicViewModel model) {
    this.setLayout(new BorderLayout());
    this.beatPanel = new BeatPanel();
    this.rangePanel = new RangePanel();
    this.notesPanel = new NotesPanel();
    beatPanel.setBeats(model.getNumBeats());
    rangePanel.setNotes(model.getNoteRange());
    this.add(rangePanel, BorderLayout.WEST);
    this.add(beatPanel, BorderLayout.NORTH);
    notesPanel = this.createNotes(model);
    notesPanel.setPreferredSize(new Dimension(model.getNumBeats() * 30, model.getNoteRange().size() * 24));
    scrollPane = new JScrollPane(notesPanel,
        ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setPreferredSize(new Dimension(model.getNumBeats(), model.getNoteRange().size()));
    scrollPane.getHorizontalScrollBar().setUnitIncrement(15);
    scrollPane.getVerticalScrollBar().setUnitIncrement(15);
    this.add(scrollPane, BorderLayout.CENTER);
    this.pack();
  }

  /**
   * Creates the center panel of the notes.
   * @param model A {@link MusicViewModel} of the model to draw
   * @return the notes panel
   */
  private NotesPanel createNotes(MusicViewModel model) {
    notesPanel.setLines(model.getNumBeats(), model.getNoteRange().size());
    for (int i = 0; i < model.getNumBeats(); i++) {
      notesPanel.setNotes(model.notesStartAtThisBeat(i),
          model.notesContinueAtThisBeat(i), i);
    }
    return notesPanel;
  }

  /**
   * Re-draw the frame.
   * @param model A {@link MusicViewModel} of the model to draw
   */
  public void reDraw(MusicViewModel model) {
    this.notesPanel.removeRects();
    this.createNotes(model);
    this.notesPanel.repaint();
    this.rangePanel = new RangePanel();
    this.add(rangePanel, BorderLayout.WEST);
    this.rangePanel.setNotes(model.getNoteRange());
    this.beatPanel.repaint();
    this.rangePanel.revalidate();
    this.rangePanel.repaint();
  }

  @Override
  public void reDrawNotes(MusicViewModel model) {
    this.notesPanel.repaint();
  }

  @Override
  public void reDrawRemove(MusicViewModel model) {
    this.notesPanel.removeRects();
    this.createNotes(model);
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

    if (pitchObject == null || startBeatObject == null || durationObject == null
        || octaveObject == null) {
      return null;
    }
    PitchType type = (PitchType) pitchObject;
    Integer startBeat = Integer.parseInt((String) startBeatObject);
    Integer duration = Integer.parseInt((String) durationObject);
    Integer octave = Integer.parseInt((String) octaveObject);
    return new Note(type, startBeat, duration, octave);
  }

  @Override
  public void addListener(KeyListener kbd) {
    this.setFocusable(true);
    this.requestFocus();
    this.addKeyListener(kbd);
  }

  @Override
  public void scrollRight() {
    this.scrollPane.getHorizontalScrollBar().setValue(
        this.scrollPane.getHorizontalScrollBar().getValue() +
        this.scrollPane.getHorizontalScrollBar().getUnitIncrement());
  }

  @Override
  public void scrollLeft() {
    this.scrollPane.getHorizontalScrollBar().setValue(
        this.scrollPane.getHorizontalScrollBar().getValue() +
        this.scrollPane.getHorizontalScrollBar().getUnitIncrement() * -1);
  }

  @Override
  public void scrollUp() {
    this.scrollPane.getVerticalScrollBar().setValue(
        this.scrollPane.getVerticalScrollBar().getValue() +
        this.scrollPane.getVerticalScrollBar().getUnitIncrement());
  }

  @Override
  public void scrollDown() {
    this.scrollPane.getVerticalScrollBar().setValue(
        this.scrollPane.getVerticalScrollBar().getValue() +
        this.scrollPane.getVerticalScrollBar().getUnitIncrement() * -1);
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
    int currentMaxPosition = this.scrollPane.getHorizontalScrollBar().getValue();
    if (currentMaxPosition <= this.notesPanel.getPosition() - 500) {
      this.scrollRight();
    }
    this.notesPanel.moveRedLine();
  }

  @Override
  public void createRedLine() {
    this.notesPanel.createRedLine();
  }

  @Override
  public int getCurrentPosition() {
    return this.notesPanel.getPosition();
  }

  @Override
  public void sendNotes(int counter) {
    // this method is not used in this view
  }
}