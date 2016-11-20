package cs3500.music.view;

//import cs3500.music.controller.KeyboardListener;
import cs3500.music.model.MusicViewModel;
import cs3500.music.model.Note;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.IllegalFormatException;

/**
 * Represents a GUI view for the music editor.
 */
public interface IMusicGUIView extends IMusicView {
  /**
   * Show the prompt to add a Note to the editor. Once a prompt is shown,
   * if every necessary field is filled out correctly return the Note that
   * should be added to the editor.
   *
   * @return The Note to add to the model
   * @throws IllegalFormatException If the input is malformed
   */
  Note showAddPrompt() throws IllegalFormatException;

  /**
   * Reset the focus on the appropriate part of the view that has the keyboard listener
   * attached to it, so that keyboard events will still flow through.
   */
  void resetFocus();

  /**
   * This is to force the view to have a method to set up the keyboard. The name has been chosen
   * deliberately. This is the same method signature to add a key listener in Java Swing.
   * <p>
   * Thus our Swing-based implementation of this interface will already have such a method.
   */
  void addListener(KeyListener kbd);

  /**
   * Scroll the panel of Notes to the right.
   */
  void scrollRight();

  /**
   * Scroll the panel of Notes to the left.
   */
  void scrollLeft();

  /**
   * Redraw the board.
   *
   * @param model A {@link MusicViewModel} of the model to draw
   */
  void reDraw(MusicViewModel model);

  /**
   * Redraw the panel of Notes in the middle of the visual view.
   *
   * @param model A {@link MusicViewModel} of the model to draw
   */
  void reDrawNotes(MusicViewModel model);

  /**
   * Prompt the user if they want to remove their selected Note.
   * @return If the user says they want to remove the Note.
   */
  boolean doRemove();

  /**
   * Set this view's note panel's MouseListener to the given MouseListener.
   * @param mouse The MouseListener to set
   */
  void setMouseListener(MouseListener mouse);

  /**
   * Moves the red line tracking the beat over to the right by one.
   */
  void moveRedLine();

  /**
   * Creates the red line at beat 0.
   */
  void createRedLine();

  /**
   * Gets the current position of the red line in the view, if the view is a combined view.
   * @return The x position (in beats) of the red line
   */
  int getCurrentPosition();
}
