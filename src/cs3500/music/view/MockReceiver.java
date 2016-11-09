package cs3500.music.view;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;

/**
 * Mock Receiver used for testing the MIDI view.
 */
public final class MockReceiver implements Receiver {
  public StringBuilder messageString = new StringBuilder();

  @Override
  public void send(MidiMessage message, long timeStamp) {
    messageString.append(message.toString() + "\n");
  }

  @Override
  public void close() {
    return;
  }

}
