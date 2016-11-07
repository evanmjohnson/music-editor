package cs3500.music.view;

import cs3500.music.model.MusicViewModel;
import cs3500.music.model.Note;

import javax.sound.midi.*;
import java.util.*;

/**
 * A skeleton for MIDI playback
 */

public class MidiView implements IMusicView {
  private Synthesizer synth;
  private Receiver receiver;

  public MidiView() {
    try {
      this.synth = MidiSystem.getSynthesizer();
      this.receiver = synth.getReceiver();
      this.synth.open();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
  }
  /**
   * Relevant classes and methods from the javax.sound.midi library:
   * <ul>
   *  <li>{@link MidiSystem#getSynthesizer()}</li>
   *  <li>{@link Synthesizer}
   *    <ul>
   *      <li>{@link Synthesizer#open()}</li>
   *      <li>{@link Synthesizer#getReceiver()}</li>
   *      <li>{@link Synthesizer#getChannels()}</li>
   *    </ul>
   *  </li>
   *  <li>{@link Receiver}
   *    <ul>
   *      <li>{@link Receiver#send(MidiMessage, long)}</li>
   *      <li>{@link Receiver#close()}</li>
   *    </ul>
   *  </li>
   *  <li>{@link MidiMessage}</li>
   *  <li>{@link ShortMessage}</li>
   *  <li>{@link MidiChannel}
   *    <ul>
   *      <li>{@link MidiChannel#getProgram()}</li>
   *      <li>{@link MidiChannel#programChange(int)}</li>
   *    </ul>
   *  </li>
   * </ul>
   * @see <a href="https://en.wikipedia.org/wiki/General_MIDI">
   *   https://en.wikipedia.org/wiki/General_MIDI
   *   </a>
   */

  public void playNote() throws InvalidMidiDataException {
    MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, 0, 60, 64);
    MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, 0, 60, 64);
    this.receiver.send(start, -1);
    try {
      Thread.sleep(2000);
    }
    catch (InterruptedException e) {
      e.printStackTrace();
    }
    this.receiver.send(stop, this.synth.getMicrosecondPosition() + 200000);
    this.receiver.close();
  }

  @Override
  public void create(MusicViewModel model) {
    // set up channels
    MidiChannel[] channels = synth.getChannels();
    TreeMap<Integer, Integer> instrumentToChannel = new TreeMap<>();
    List<Integer> instruments = model.getInstruments();
    for (int i = 0; i < model.getInstruments().size(); i++) {
      if (channels[i] != null) {
        if (!Arrays.asList(channels).contains(instruments.get(i))) {
          channels[i].programChange(instruments.get(i));
          instrumentToChannel.put(instruments.get(i), i);
        }
      }
    }

    for (int i = 0; i <= model.getNumBeats(); i++) {
      // start each note
      List<Integer> startList = model.notesStartAtThisBeat(i);
      if (startList != null) {
        for (Integer start : startList) {
          Note toAdd = model.getNote(start, i);
          int channelOf = instrumentToChannel.get(toAdd.getInstrument());
          int pitch = toAdd.getPitch().getToneOrder() + (toAdd.getOctave() * 12);
          try {
            MidiMessage message = new ShortMessage(ShortMessage.NOTE_ON, channelOf,
                pitch, toAdd.getVolume());
            this.receiver.send(message, i * model.getTempo());
          } catch (InvalidMidiDataException e) {
            e.printStackTrace();
          }
        }
      }
//      try {
//        Thread.sleep(model.getTempo() / 1000);
//      }
//      catch (InterruptedException e) {
//        e.printStackTrace();
//      }
      // stop each note
      List<Integer> stopList = model.notesStopAtThisBeat(i);
      if (stopList != null) {
        for (Integer stop : stopList) {
          Note toAdd = model.getNote(stop, i);
          int channelOf = instrumentToChannel.get(toAdd.getInstrument());
          int pitch = toAdd.getPitch().getToneOrder() + (toAdd.getOctave() * 12);
          try {
            MidiMessage message = new ShortMessage(ShortMessage.NOTE_OFF, channelOf,
                pitch, toAdd.getVolume());

            this.receiver.send(message, i * model.getTempo());
          } catch (InvalidMidiDataException e) {
            e.printStackTrace();
          }
        }
      }
    }

    try {
      Thread.sleep(model.getTempo() * model.getNumBeats());
    }
    catch (InterruptedException e) {
      e.printStackTrace();
    }
    this.receiver.close(); // Only call this once you're done playing *all* notes
  }

  @Override
  public void makeVisible() {

  }
}