package cs3500.music.view;

//import cs3500.music.controller.KeyboardListener;
import cs3500.music.model.MusicViewModel;
import cs3500.music.model.Note;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.IllegalFormatException;

/**
 * Represents a GUI view for the music editor.
 */
public interface IMusicGUIView extends IMusicView {
  /**
   * Show the prompt to add a Note to the editor. Once a prompt is shown,
   * if every necessary field is filled out correctly return the Note that
   * should be added to the editor.
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
   *
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
   */
  void reDraw(MusicViewModel model);

  void showSelected(MusicViewModel model);
}
