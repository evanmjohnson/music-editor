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
  public StringBuilder messageString = new StringBuilder();

  /**
   * Constructor for using the MIDI view to produce actual sound.
   */
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
   * Constructor for testing. Takes a message String that is used to get the state of this view.
   * @param messageString The starter message String for this view
   */
  public MidiView(String messageString) {
    try {
      this.synth = new MockSynthesizer();
      this.receiver = this.synth.getReceiver();
      this.synth.open();
      this.messageString.append(messageString);
    }
    catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
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
          int channelOf;
          if (instrumentToChannel.get(toAdd.getInstrument()) == null) {
            channelOf = 0;
          }
          else {
            channelOf = instrumentToChannel.get(toAdd.getInstrument());
          }
          int pitch = toAdd.getPitch().getToneOrder() + (toAdd.getOctave() * 12);
          try {
            MidiMessage message = new ShortMessage(ShortMessage.NOTE_ON, channelOf,
                pitch, toAdd.getVolume());
            this.receiver.send(message, i * model.getTempo());
            this.messageString.append("start " + channelOf + " " + pitch + " " +
                toAdd.getVolume() + "\n");
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
          int channelOf;
          if (instrumentToChannel.get(toAdd.getInstrument()) == null) {
            channelOf = 0;
          }
          else {
            channelOf = instrumentToChannel.get(toAdd.getInstrument());
          }
          int pitch = toAdd.getPitch().getToneOrder() + (toAdd.getOctave() * 12);
          try {
            MidiMessage message = new ShortMessage(ShortMessage.NOTE_OFF, channelOf,
                pitch, toAdd.getVolume());
            this.receiver.send(message, i * model.getTempo());
            this.messageString.append("stop " + channelOf + " " + pitch +  " " +
                toAdd.getVolume() + "\n");
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