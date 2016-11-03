package cs3500.music.controller;

import cs3500.music.view.ConsoleView;
import cs3500.music.view.IMusicView;
import cs3500.music.view.JFrameView;

/**
 * Factory class for the music editor.
 */
public class MusicCreator {
  public static IMusicView create(String viewType) {
    if (viewType.equals("console")) {
      return new ConsoleView(System.out);
    }
    else if (viewType.equals("visual")) {
      return new JFrameView();
    }
//    else if (viewType.equals("midi")){
//      //midi
//    }
    else {
      throw new IllegalArgumentException("Must be one of console, visual, or midi.");
    }
  }
}
