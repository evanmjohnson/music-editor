package cs3500.music.provider;

import javax.swing.JScrollPane;
import javax.swing.JButton;

import java.awt.Point;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

/**
 * This interface extends the IMusicView interface; it represents
 * a view that implements a GUI, whether standalone or in tandem
 * with MIDI playback.
 */
public interface IGuiView extends IMusicView {

  /**
   * Moves the horizontal scrollbar to the position of the red line once the red line reaches
   * the end of the current window.
   */
  void scrollToRedLine();

  /**
   * Moves the horizontal scrollbar to the beginning of the composition.
   */
  void scrollBeginning();

  /**
   * Moves the horizontal scrollbar to the end of the composition.
   */
  void scrollEnd();

  /**
   * Moves the vertical scrollbar up.
   */
  void scrollUp();

  /**
   * Moves the vertical scrollbar down.
   */
  void scrollDown();

  /**
   * Moves the horizontal scrollbar to the left.
   */
  void scrollLeft();

  /**
   * Moves the horizontal scrollbar to the right.
   */
  void scrollRight();

  /**
   * Adds a {@link KeyListener} to the composition such that keyboard commands can be given.
   *
   * @param e the KeyListener to be added to the composition
   */
  void addListener(KeyListener e);

  /**
   * Adds a {@link MouseListener} to the composition such that mouse commands can be given.
   *
   * @param e the MouseListener to be added to the composition
   */
  void addMouse(MouseListener e);

  /**
   * Attempts to retrieve what note has been clicked on at a certain point.
   *
   * @param p the point clicked on by the mouse
   * @return the note at the point
   */
  Note getNoteAtPoint(Point p);

  /**
   * Retrieves {@code thread1}.
   *
   * @return thread1
   */
  Thread getThread1();

  /**
   * Retrieves {@code thread2}.
   *
   * @return thread2
   */
  Thread getThread2();

  /**
   * Retrieves the JButton associated with this view.
   *
   * @return the JButton
   */
  JButton getButton();

  /**
   * Gets the scroll pane for the current view.
   *
   * @return JScrollPane the views scroll pane
   */
  public JScrollPane getScrollPane();

  /**
   * Takes in all the inputs from the JComboBoxes and JTextFields, creates a new note based
   * on the inputted parameters, and adds the note to the respective view(s).
   */
  void action();

  /**
   * Gets the last Note to be added to the view.
   *
   * @return Note that was added
   */
  public Note getAddedNote();

  /**
   * Gets the display panel for the current view.
   *
   * @return ViewPanel the views display panel
   */
  public ViewPanel getDisplayPanel();


}
