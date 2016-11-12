package cs3500.music.view;

import cs3500.music.model.Note;

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

}
