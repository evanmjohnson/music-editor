package cs3500.music.provider;

import cs3500.music.model.Note;

import javax.sound.midi.Synthesizer;
import javax.sound.midi.Receiver;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Instrument;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * This class represents the model played as midi music.
 */
public class MidiView implements IMusicView {
  private Synthesizer synth = null;
  private Receiver receiver = null;
  private int currentBeat = 0;
  public static StringBuilder RESULT;
  private HashMap<Integer, ArrayList<Note>> notes;

  /**
   * Constructs a MidiView.
   */
  public MidiView() {
    this.notes = new HashMap<>();
    try {
      synth = MidiSystem.getSynthesizer();
      receiver = synth.getReceiver();
      synth.open();
    } catch (MidiUnavailableException e) {
      System.out.print(e.toString());
    }

  }


  /**
   * Constructs a MidiView.
   *
   * @param sb StringBuilder for testing mock midis
   */
  public MidiView(StringBuilder sb) {
    RESULT = sb;
    try {
      synth = new MockMidi();
      receiver = synth.getReceiver();

      synth.open();
    } catch (MidiUnavailableException e) {
      System.out.print(e.toString());
    }
  }

  /**
   * Adds the given note to this class's set of notes.
   *
   * @param n Note to be added
   */
  protected void addNote(Note n) {
    for (int i = n.getStartBeat(); i <= (n.getDuration() + n.getStartBeat()); i++) {
      if (!notes.containsKey(i)) {
        ArrayList<Note> addNewBeat = new ArrayList<>();
        addNewBeat.add(n);
        notes.put(i, addNewBeat);
        notes.get(i).add(n);
      } else {
        notes.get(i).add(n);
      }
    }
  }

  /**
   * Returns the current beat of midi.
   *
   * @return int the current beat
   */
  protected int getCurrentBeat() {
    return this.currentBeat;
  }

  /**
   * Returns the result as a StringBuilder.
   *
   * @return StringBuilder result for testing
   */
  protected StringBuilder getResult() {
    return RESULT;
  }


  /**
   * Gets all the notes from the map of notes that are at the given beat.
   *
   * @param beat int beat to look at
   * @return List of Notes that are at the beat given
   */
  private List<Note> getAllNotesAtBeat(int beat) throws IllegalArgumentException {
    if (beat < 0) {
      throw new IllegalArgumentException("Invalid beat number");
    }
    if (notes.containsKey(beat)) {
      return notes.get(beat);
    } else {
      return new ArrayList<Note>();
    }
  }

  /**
   * Gets all the notes from the map of notes that start at the given beat.
   *
   * @param beat int beat to look at
   * @return List of Notes that start at the beat given
   */
  private List<Note> getStartingNotesAtBeat(int beat) throws IllegalArgumentException {
    if (beat < 0) {
      throw new IllegalArgumentException("Invalid beat number");
    }
    List<Note> startingNotes = new ArrayList<Note>();
    if (this.notes.containsKey(beat)) {
      for (int i = 0; i < notes.get(beat).size(); i++) {
        if (this.notes.get(beat).get(i).getStartBeat() == beat) {
          startingNotes.add(notes.get(beat).get(i));
        }
      }
      return startingNotes;

    } else {
      return new ArrayList<Note>();
    }
  }


  /**
   * Plays through the {@link Note}s in the model.
   *
   * @param viewmodel the model being played through
   */
  @Override
  public void renderModel(ViewModel viewmodel) throws InterruptedException {
    int finalB;

    finalB = viewmodel.getFinalBeat();
    for (int index = 0; index <= viewmodel.getFinalBeat(); index++) {
      ArrayList<Note> noteArrayList = new ArrayList<>();
      for (Note note : viewmodel.getAllNotesAtBeat(index)) {
        noteArrayList.add(note);
      }
      this.notes.put(index, noteArrayList);
    }

    for (; currentBeat <= finalB; currentBeat++) {
      for (Note note : this.getAllNotesAtBeat(currentBeat)) {
        try {
          if (this.getStartingNotesAtBeat(currentBeat).contains(note)) {
            playNote(note);
          }
          if (currentBeat == note.getDuration() + note.getStartBeat()) {
            stopNote(note);
          }
        } catch (InvalidMidiDataException e) {
          System.out.print(e.toString());
        }
      }
      Thread.sleep(viewmodel.getTempo() / 1000);

      if (currentBeat >= viewmodel.getFinalBeat()) {
        this.receiver.close();
      }
    }
  }


  /**
   * Plays the given {@link Note}.
   *
   * @param note the note to be played through
   */
  private void playNote(Note note) throws InvalidMidiDataException {
    Instrument[] allInstruments = synth.getDefaultSoundbank().getInstruments();
    synth.getChannels()[0].programChange(allInstruments[note.getInstrument()].getPatch()
            .getProgram());

    int value = note.getPitch().getToneOrder() + (note.getOctave() * 12) + 12;
    MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, 0, value,
            note.getVolume());
    receiver.send(start, note.getStartBeat());
  }

  /**
   * Stops the given {@link Note}.
   *
   * @param note the note to be stopped
   */
  private void stopNote(Note note) throws InvalidMidiDataException {
    int value = note.getPitch().getToneOrder() + (note.getOctave() * 12) + 12;
    MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, 0, value,
            note.getVolume());

    receiver.send(stop, note.getStartBeat() + note.getDuration() - 1);
  }

}
