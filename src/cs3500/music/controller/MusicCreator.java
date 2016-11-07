package cs3500.music.controller;

import cs3500.music.view.ConsoleView;
import cs3500.music.view.IMusicView;
import cs3500.music.view.JFrameView;
import cs3500.music.view.MidiView;

import java.io.FileWriter;
import java.io.IOException;
//import cs3500.music.view.MidiView;

/**
 * Factory class for the music editor.
 */
public class MusicCreator {
  public static IMusicView create(String[] args) {
    String viewType = args[0];
    if (viewType.equals("console")) {
      Appendable ap;
      // if no file is specified, use System.out as the Appendable
      if (args.length == 1) {
        ap = System.out;
      }
      // if a file is specified, use it as the Appendable
      else {
        String filePath = args[1];
        try {
          ap = new FileWriter(filePath);
        }
        catch (IOException e) {
          ap = System.out;
          e.printStackTrace();
        }
      }
      return new ConsoleView(ap);
    }
    else if(viewType.equals("visual")) {
      return new JFrameView();
    }
    else if (viewType.equals("midi")){
      return new MidiView();
    }
    else {
      throw new IllegalArgumentException("Must be one of console, visual, or midi.");
    }
  }
}
