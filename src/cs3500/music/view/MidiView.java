package cs3500.music.view;

import cs3500.music.model.MusicViewModel;
import cs3500.music.model.Note;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.Sequence;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Receiver;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Track;

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
    if (model.length() == 0) {
      throw new IllegalArgumentException("Empty model.");
    }

    // set up the MIDI Sequence
    Sequence sequence;
    try {
      sequence = new Sequence(Sequence.PPQ, model.getTempo(), 1);
      sequence.getTracks()[0] = this.startNotes(model, sequence.getTracks()[0]);

    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
    }

    /*
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
          } else {
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
      try {
        Thread.sleep(model.getTempo() / 1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      // stop each note
      List<Integer> stopList = model.notesStopAtThisBeatLowestToHighest(i);
      if (stopList != null) {
        for (Integer stop : stopList) {
          Note toAdd = model.getNote(stop, i);
          int channelOf;
          if (instrumentToChannel.get(toAdd.getInstrument()) == null) {
            channelOf = 0;
          } else {
            channelOf = instrumentToChannel.get(toAdd.getInstrument());
          }
          int pitch = toAdd.getPitch().getToneOrder() + (toAdd.getOctave() * 12);
          try {
            MidiMessage message = new ShortMessage(ShortMessage.NOTE_OFF, channelOf,
                    pitch, toAdd.getVolume());
            this.receiver.send(message, i * model.getTempo());
            this.messageString.append("stop " + channelOf + " " + pitch + " " +
                    toAdd.getVolume() + "\n");
          } catch (InvalidMidiDataException e) {
            e.printStackTrace();
          }
        }
      }
    }
    */
    this.receiver.close(); // Only call this once you're done playing *all* notes
  }

  private Track startNotes(MusicViewModel model, Track track) {
    for (int i = 0; i <= model.getNumBeats(); i++) {
      // start each note
      List<Integer> startList = model.notesStartAtThisBeat(i);
      if (startList != null) {
        for (Integer start : startList) {
          Note toAdd = model.getNote(start, i);
          int pitch = toAdd.getPitch().getToneOrder() + (toAdd.getOctave() * 12);
          try {
            MidiMessage message = new ShortMessage(ShortMessage.NOTE_ON, toAdd.getInstrument(),
                pitch, toAdd.getVolume());
            track.add(new MidiEvent(message, toAdd.getStartBeat() * model.getTempo()));
            this.messageString.append("start " + toAdd.getInstrument() + " " + pitch + " " +
                toAdd.getVolume() + "\n");
          } catch (InvalidMidiDataException e) {
            e.printStackTrace();
          }
        }
      }
      // stop each note
      List<Integer> stopList = model.notesStopAtThisBeatLowestToHighest(i);
      if (stopList != null) {
        for (Integer stop : stopList) {
          Note toAdd = model.getNote(stop, i);
          int pitch = toAdd.getPitch().getToneOrder() + (toAdd.getOctave() * 12);
          try {
            MidiMessage message = new ShortMessage(ShortMessage.NOTE_OFF, toAdd.getInstrument(),
                pitch, toAdd.getVolume());
            track.add(new MidiEvent(message, toAdd.getStartBeat() * model.getTempo()));
            this.messageString.append("stop " + toAdd.getInstrument() + " " + pitch + " " +
                toAdd.getVolume() + "\n");
          } catch (InvalidMidiDataException e) {
            e.printStackTrace();
          }
        }
      }
    }
    return track;
  }

  @Override
  public void makeVisible() {

  }
}