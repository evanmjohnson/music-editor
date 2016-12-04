package cs3500.music.controller;

import cs3500.music.provider.CompositeView;
import cs3500.music.provider.ConsoleView;
import cs3500.music.provider.GuiView;
import cs3500.music.provider.IMusicView;
import cs3500.music.provider.MidiView;

/**
 * Factory class for the music editor.
 */
public class MusicCreator {
  /**
   * Creates an IMusicView based on the specified args.
   *
   * @param args The type of view that is to be created.
   * @return An IMusicView of the specified type.
   */
  public static IMusicView create(String[] args) {
    String viewType = args[0];
    if (viewType.equals("console")) {
      return new ConsoleView();
    } else if (viewType.equals("visual")) {
      return new GuiView();
    } else if (viewType.equals("midi")) {
      return new MidiView();
    } else if (viewType.equals("composite")) {
      return new CompositeView(new GuiView(), new MidiView());
    } else {
      throw new IllegalArgumentException("Must be one of console, visual, midi, " +
          "or composite.");
    }
  }
}
