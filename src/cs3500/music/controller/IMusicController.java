package cs3500.music.controller;

import cs3500.music.model.IMusicModel;
import cs3500.music.view.IMusicView;

/**
 * This is the interface for the controller of the music editor.
 */
public interface IMusicController {
  /**
   * Start the music editor with the given view.
   * @param viewType The type of view to start the editor with
   */
  void start(IMusicModel model, String viewType);
}
