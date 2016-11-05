package cs3500.music.view;

import cs3500.music.model.MusicViewModel;
import cs3500.music.model.Note;

import java.util.List;

/**
 * Represents the view interface for the music editor.
 */
public interface IMusicView {
  /**
   * Draws the state of the given read-only model.
   * @param model The model to draw
   */
  void draw(MusicViewModel model);

  /**
   * Make the view visible. This is usually called
   * after the view is constructed
   */
  void makeVisible();

}
