package cs3500.music.provider;

import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.ArrayList;
import java.util.Vector;


/**
 * This class represents the model as printed out and displayed as a GUI.
 */
public class GuiView extends javax.swing.JFrame implements IGuiView {
  private final ViewPanel displayPanel;
  private JScrollPane scrollPane;
  private JComboBox<Pitch> note;
  private JComboBox<Integer> octave;
  private JComboBox<Integer> instrument;
  private JTextField field;
  private JTextField field2;
  private JTextField field3;
  private JButton button;
  private Note added;

  /**
   * Creates a new GuiView.
   */
  public GuiView() {
    super();
    this.displayPanel = new ViewPanel();
    this.scrollPane = new JScrollPane();
  }


  @Override
  public Dimension getPreferredSize() {
    return this.displayPanel.getPreferredSize();
  }

  @Override
  public void renderModel(ViewModel viewmodel) {

    ArrayList<Note> nList = new ArrayList<>();
    for (int index = 0; index < viewmodel.getFinalBeat(); index++) {
      nList.addAll(viewmodel.getStartingNotesAtBeat(index));
    }
    this.displayPanel.setNotes(nList);
    this.displayPanel.setLines();
    this.displayPanel.setPreferredSize(getPreferredSize());

    Pitch[] notes = Pitch.values();
    Integer[] octaves = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    Vector<Integer> vector = new Vector<>();
    for (int index = 0; index < 128; index++) {
      vector.add(index);
    }

    note = new JComboBox<>(notes);
    octave = new JComboBox<>(octaves);
    instrument = new JComboBox<>(vector);
    field = new JTextField();
    field2 = new JTextField();
    field3 = new JTextField();
    button = new JButton("Add Note");

    field.setLocation(675, 45);
    field2.setLocation(875, 45);
    field3.setLocation(1075, 45);
    note.setLocation(50, 30);
    octave.setLocation(240, 30);
    instrument.setLocation(450, 30);
    button.setLocation(1000, 75);
    field.setSize(100, 20);
    field2.setSize(100, 20);
    field3.setSize(100, 20);
    note.setSize(100, 50);
    octave.setSize(100, 50);
    instrument.setSize(100, 50);
    button.setSize(100, 30);
    note.setVisible(true);
    octave.setVisible(true);
    instrument.setVisible(true);
    field.setVisible(true);
    field2.setVisible(true);
    field3.setVisible(true);
    button.setVisible(true);

    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    scrollPane = new JScrollPane(displayPanel);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    this.setFocusable(true);
    this.getContentPane().add(button);
    this.getContentPane().add(field2);
    this.getContentPane().add(field);
    this.getContentPane().add(field3);
    this.getContentPane().add(note);
    this.getContentPane().add(octave);
    this.getContentPane().add(instrument);
    this.getContentPane().add(scrollPane);
    this.pack();

    this.setVisible(true);
  }

  @Override
  public ViewPanel getDisplayPanel() {
    return this.displayPanel;
  }

  @Override
  public Thread getThread1() {
    return null;
  }

  @Override
  public Thread getThread2() {
    return null;
  }

  @Override
  public void scrollToRedLine() {
    if (this.getDisplayPanel().getrLinePos() % 1200 == 0) {
      this.scrollPane.getHorizontalScrollBar().setValue(this.getDisplayPanel().getrLinePos());
    }
  }

  @Override
  public void scrollBeginning() {
    this.scrollPane.getHorizontalScrollBar().setValue(0);
  }

  @Override
  public void scrollEnd() {
    this.scrollPane.getHorizontalScrollBar().setValue(
            (int) ((Math.ceil(this.displayPanel.tempSum() / 4) + 1) * 100) + 1200);
  }

  @Override
  public void scrollUp() {
    this.scrollPane.getVerticalScrollBar()
            .setValue(this.scrollPane.getVerticalScrollBar().getValue() - 20);
  }

  @Override
  public void scrollDown() {
    this.scrollPane.getVerticalScrollBar()
            .setValue(this.scrollPane.getVerticalScrollBar().getValue() + 20);
  }

  @Override
  public void scrollLeft() {
    this.scrollPane.getHorizontalScrollBar()
            .setValue(this.scrollPane.getHorizontalScrollBar().getValue() - 20);
  }

  @Override
  public void scrollRight() {
    this.scrollPane.getHorizontalScrollBar()
            .setValue(this.scrollPane.getHorizontalScrollBar().getValue() + 20);
  }


  @Override
  public void addListener(KeyListener e) {
    this.addKeyListener(e);
  }

  @Override
  public void addMouse(MouseListener e) {
    this.addMouseListener(e);
  }

  @Override
  public Note getNoteAtPoint(Point p) {
    return this.displayPanel.getNoteAtPoint(p);
  }

  @Override
  public JButton getButton() {
    return this.button;
  }

  @Override
  public JScrollPane getScrollPane() {
    return this.scrollPane;
  }

  @Override
  public Note getAddedNote() {
    return this.added;
  }

  @Override
  public void action() {
    added = new Note((Pitch) note.getSelectedItem(), (int) octave.getSelectedItem(),
            (int) instrument.getSelectedItem(), Integer.parseInt(field3.getText()),
            Integer.parseInt(field2.getText()), Integer.parseInt(field.getText()));
    List<Note> toAdd = new ArrayList<>();
    toAdd.add(added);
    this.displayPanel.setNotes(toAdd);
    this.displayPanel.setLines();
    this.displayPanel.repaint();

  }
}
