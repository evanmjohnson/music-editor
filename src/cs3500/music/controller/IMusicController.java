package cs3500.music.controller;

import cs3500.music.model.IMusicModel;

/**
 * This is the interface for the controller of the music editor.
 */
public interface IMusicController {
  /**
   * Start the recording of the given model.
   * @param model The model to start the recording of
   */
  void startRecording(IMusicModel model);
}
