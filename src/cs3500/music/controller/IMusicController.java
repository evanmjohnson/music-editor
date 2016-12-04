package cs3500.music.controller;

/**
 * This is the interface for the controller of the music editor.
 */
public interface IMusicController {
  /**
   * Start the music editor with the given view.
   *
   * @param args The command line arguments, including which view to use for this editor
   */
  void start(String[] args);
}
