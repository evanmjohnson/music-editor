package cs3500.music.view;

import cs3500.music.model.MusicViewModel;

/**
 * Represents the view interface for the music editor.
 */
public interface IMusicView {
  /**
   * Draws the state of the given read-only model.
   *
   * @param model The model to create
   */
  void create(MusicViewModel model);

  /**
   * Make the view visible. This is usually called
   * after the view is constructed
   */
  void makeVisible();

  /**
   * Create the view at the given beat.
   *
   * @param counter the beat at which to create the view
   */
  void sendNotes(int counter);
}
