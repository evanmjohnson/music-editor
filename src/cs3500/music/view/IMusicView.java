package cs3500.music.view;

import cs3500.music.model.Note;

import java.util.List;

/**
 * Represents the view interface for the music editor.
 */
public interface IMusicView {
  /**
   * Draws the model represented by the given state
   * @param state The String state of a model to draw
   */
  void draw(String state);
}
