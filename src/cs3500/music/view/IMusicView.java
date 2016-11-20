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
   * Pauses the playback of the piece if this view is a combined view.
   */
  void pause();

  /**
   * Resumes the plackback of the piece if this view is a combined view.
   */
  void resume();
}
