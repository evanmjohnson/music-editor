package cs3500.music.controller;

/**
 * An interface for the MIDI view to communicate timestamps back to the GUI controller.
 * For use with the combined view.
 */
public interface ITimeCallback {
  /**
   * Sends the given timestamp back to the controller.
   * @param timestamp the timestamp to send
   */
  void send(int timestamp);
}
