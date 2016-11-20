package cs3500.music.view;

import cs3500.music.controller.GUIController;
import cs3500.music.model.IMusicModel;
import cs3500.music.model.MusicViewModel;
import cs3500.music.model.Note;

import javax.sound.midi.Synthesizer;
import javax.sound.midi.Receiver;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.InvalidMidiDataException;

import java.util.List;
import java.util.Arrays;
import java.util.TreeMap;


/**
 * A skeleton for MIDI playback.
 */
public class MidiView implements IMusicView {
  private Synthesizer synth;
  private Receiver receiver;
  public StringBuilder messageString = new StringBuilder();
  private MusicViewModel model;
  private TreeMap<Integer, Integer> instrumentToChannel;


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
    instrumentToChannel = new TreeMap<>();
  }

  /**
   * Constructor for testing. Takes a message String that is used to get the state of this view.
   *
   * @param messageString The starter message String for this view
   */
  public MidiView(String messageString) {
    try {
      this.synth = new MockSynthesizer();
      this.receiver = this.synth.getReceiver();
      this.synth.open();
      this.messageString.append(messageString);
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void create(MusicViewModel model) {
    this.model = model;
    if (model.length() == 0) {
      throw new IllegalArgumentException("Empty model.");
    }

    // set up channels
    MidiChannel[] channels = synth.getChannels();
    List<Integer> instruments = model.getInstruments();
    for (int i = 0; i < model.getInstruments().size(); i++) {
      if (channels[i] != null) {
        if (!Arrays.asList(channels).contains(instruments.get(i))) {
          channels[i].programChange(instruments.get(i));
          instrumentToChannel.put(instruments.get(i), i);
        }
      }
    }

    this.createFrom(0);
  }

  private void createFrom(int beat) {
    for (int i = beat; i <= model.getNumBeats(); i++) {
      // play each note
      List<Integer> startList = model.notesStartAtThisBeat(i);
      if (startList != null) {
        for (Integer start : startList) {
          Note toAdd = model.getNote(start, i);
          int channelOf;
          if (instrumentToChannel.get(toAdd.getInstrument()) == null) {
            channelOf = 0;
          } else {
            channelOf = instrumentToChannel.get(toAdd.getInstrument());
          }
          int pitch = toAdd.getPitch().getToneOrder() + (toAdd.getOctave() * 12) + 12;
          try {
            MidiMessage message = new ShortMessage(ShortMessage.NOTE_ON, channelOf,
                pitch, toAdd.getVolume());
            MidiMessage stopMessage = new ShortMessage(ShortMessage.NOTE_OFF, channelOf,
                pitch, toAdd.getVolume());
            this.receiver.send(message, i * model.getTempo());
            this.receiver.send(stopMessage, (i + toAdd.getDuration()) * model.getTempo());
            this.messageString.append("start " + channelOf + " " + pitch + " " +
                toAdd.getVolume() + "\n");
            this.messageString.append("stop " + channelOf + " " + pitch + " " +
                toAdd.getVolume() + "\n");
          } catch (InvalidMidiDataException e) {
            e.printStackTrace();
          }
        }
      }
    }
    this.receiver.close(); // Only call this once you're done playing *all* notes
  }

  @Override
  public void makeVisible() {
    return;
  }

  @Override
  public void pause() {
    this.receiver.close();
  }

  @Override
  public void resume() {

  }
}