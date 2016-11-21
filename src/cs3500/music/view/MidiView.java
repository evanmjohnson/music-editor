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

//     set up channels
//    MidiChannel[] channels = synth.getChannels();
//    List<Integer> instruments = model.getInstruments();
//    for (int i = 0; i < model.getInstruments().size(); i++) {
//      if (channels[i] != null) {
//        if (!Arrays.asList(channels).contains(instruments.get(i))) {
//          channels[i].programChange(instruments.get(i));
//          instrumentToChannel.put(instruments.get(i), i);
//        }
//      }
//    }
  }

  @Override
  public void makeVisible() {
    return;
  }

  @Override
  public void pause() {
//    int beat = 0;
//    List<Note> start = model.noteListStartAt(beat);
//    List<Note> cont = model.noteListContinueAt(beat);
//    for (Note s : start) {
//      try {
//        int channelOf = instrumentToChannel.get(s.getInstrument());
//        MidiMessage message = new ShortMessage(ShortMessage.NOTE_OFF, channelOf,
//            s.getPitch().getToneOrder() + (s.getOctave() * 12), s.getVolume());
//        this.receiver.send(message, beat * model.getTempo());
//      }
//      catch (InvalidMidiDataException e) {
//        e.printStackTrace();
//      }
//    }
  }

  @Override
  public void resume() {

  }

  @Override
  public void sendNotes(int beat) {
    MidiChannel[] channels = synth.getChannels();
    List<Integer> instruments = model.getInstruments();
    System.out.println(instruments);
    for (int i = 0; i < model.getInstruments().size(); i++) {
      if (channels[i] != null) {
        if (!Arrays.asList(channels).contains(instruments.get(i))) {
          channels[i].programChange(instruments.get(i));
          instrumentToChannel.put(instruments.get(i), i);
        }
      }
    }
    List<Note> start = model.noteListStartAt(beat);
    for (int i = 0; i <= start.size(); i++) {
      // play each note
      List<Integer> startList = model.notesStartAtThisBeat(beat);
      if (startList != null) {
        for (Integer s : startList) {
          Note toAdd = model.getNote(s, beat);
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
            this.receiver.send(message, beat * model.getTempo());
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
  }
}