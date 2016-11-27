package cs3500.music.provider;

import javax.sound.midi.ShortMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.MidiMessage;

/**
 * This class represents a mock for a Midi Receiver.
 */
public class MockMidiReceiver implements Receiver {

  @Override
  public void send(MidiMessage message, long timeStamp) {
    ShortMessage sm = (ShortMessage) message;

    MidiView.RESULT
            .append("Note: ")
            .append(timeStamp)
            .append(" ")
            .append(sm.getData1())
            .append(" ")
            .append(sm.getData2())
            .append("\n");
  }

  @Override
  public void close() {
    MidiView.RESULT.append("Receiver Closed");
  }
}
