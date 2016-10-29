package cs3500.music.controller;

import cs3500.music.view.IMusicView;

/**
 * Factory class for the music editor.
 */
public class MusicCreator {
  public static IMusicView create(String viewType) {
    if (viewType.equals("console")) {
      //console
    }
    else if (viewType.equals("visual")) {
      //visual
    }
    else if (viewType.equals("midi")){
      //midi
    }
    else {
      throw new IllegalArgumentException("Must be one of console, visual, or midi.");
    }
  }
}
