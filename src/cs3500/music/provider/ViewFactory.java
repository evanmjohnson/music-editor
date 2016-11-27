package cs3500.music.provider;

/**
 * This factory class creates new IMusicViews based on the given string.
 */
public class ViewFactory {
  /**
   * Given a string this method creates and returns an instance of the desired view.
   */
  public static IMusicView build(String str) {
    switch (str) {
      case "console":
        return new ConsoleView();
      case "visual":
        return new GuiView();
      case "midi":
        return new MidiView();
      case "composite":
        return new CompositeView(new GuiView(), new MidiView());
      default:
        throw new IllegalArgumentException("Not a valid view.");
    }
  }
}
