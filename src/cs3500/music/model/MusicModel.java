package cs3500.music.model;

import java.util.*;

/**
 * Represents a piece of music that can be manipulated.
 * Implements the {@link IMusicModel} interface.
 */
public class MusicModel implements IMusicModel {

  private TreeMap<Integer, TreeSet<Note>> notes;
  private int tempo;

  public MusicModel() {
    this.notes = new TreeMap<>();
  }

  /**
   * Adds the given Note to this model. If there is already a Note sustained at the same Pitch and
   * octave at the starting beat of the given Note, override that Note for the duration of the
   * given Note.
   *
   * @param n The Note to add
   */
  @Override
  public void add(Note n) {
    if (n.getDuration() == 0) {
      throw new IllegalArgumentException("Cannot add a note with a duration of 0");
    }
    for (int i = n.getStartBeat(); i < n.getStartBeat() + n.getDuration(); i++) {
      if (!notes.containsKey(i)) {
        TreeSet<Note> toAdd = new TreeSet<>();
        toAdd.add(n);
        notes.put(i, toAdd);
      } else {
        notes.get(i).add(n);
      }
    }
  }

  @Override
  public void replace(Note from, Note to) {
    this.remove(from);
    this.add(to);
  }

  @Override
  public void remove(Note n) throws IllegalArgumentException {
    boolean removed = false;
    for (Map.Entry<Integer, TreeSet<Note>> entry : notes.entrySet()) {
      TreeSet<Note> set = entry.getValue();
      if (set.contains(n)) {
        set.remove(n);
        removed = true;
      }
    }
    if (!removed) {
      throw new IllegalArgumentException("That Note isn't in this model");
    }
  }

  @Override
  public Note lowestNote() {
    TreeSet<Note> resultSet = new TreeSet<>();
    for (Map.Entry<Integer, TreeSet<Note>> entry : notes.entrySet()) {
      if (!entry.getValue().isEmpty()) {
        resultSet.add(entry.getValue().first());
      }
    }
    return resultSet.first();
  }

  @Override
  public Note highestNote() {
    TreeSet<Note> resultSet = new TreeSet<>();
    for (Map.Entry<Integer, TreeSet<Note>> entry : notes.entrySet()) {
      if (!entry.getValue().isEmpty()) {
        resultSet.add(entry.getValue().last());
      }
    }
    return resultSet.last();
  }

  @Override
  public List<Note> noteListStartAt(int beat) {
    List<Note> result = new ArrayList<>();
    if (this.notes.get(beat) == null || this.notes.get(beat).isEmpty()) {
      return result;
    }
    for (Note n : this.notes.get(beat)) {
      if (n.getStartBeat() == beat) {
        result.add(n);
      }
    }
    return result;
  }

  @Override
  public List<Note> noteListContinueAt(int beat) {
    List<Note> result = new ArrayList<>();
    if (this.notes.get(beat) == null || this.notes.get(beat).isEmpty()) {
      return result;
    }
    for (Note n : this.notes.get(beat)) {
      if (n.getStartBeat() != beat) {
        result.add(n);
      }
    }
    return result;
  }

  @Override
  public void playSimultaneously(IMusicModel model2) {
    model2.combineSimultaneously(this);
  }

  @Override
  public void playConsecutively(IMusicModel model2) {
    model2.combineConsecutively(this);
  }

  @Override
  public void combineSimultaneously(MusicModel sheet1) {
    for (Map.Entry<Integer, TreeSet<Note>> entry : notes.entrySet()) {
      for (Note n : entry.getValue()) {
        sheet1.add(n);
      }
    }
  }

  @Override
  public void combineConsecutively(MusicModel sheet1) {
    ArrayList<Note> notesToAdd = new ArrayList<>();
    for (Map.Entry<Integer, TreeSet<Note>> entry : notes.entrySet()) {
      int i = entry.getKey();
      TreeSet<Note> set = entry.getValue();
      for (Note n : set) {
        if (n.getStartBeat() == i) {
          notesToAdd.add(n);
        }
      }
    }
    for (Note n : notesToAdd) {
      Note newNote = new Note(n.getPitch(), n.getStartBeat() + sheet1.length(), n.getDuration(),
          n.getOctave());
      sheet1.add(newNote);
    }
  }

  @Override
  public int length() {
    if (notes.isEmpty()) {
      return 0;
    }
    return notes.lastKey() + 1;
  }

  @Override
  public List<Integer> notesStartAtThisBeat(int beat) {
    List<Integer> result = new ArrayList<>();
    TreeSet<Note> set = this.notes.get(beat);
    List<Note> noteRange = this.getNoteRange();
    if (set == null || set.isEmpty()) {
      return new ArrayList<>();
    }
    for (Note n : this.getNoteRange()) {

      for (Note s : set) {
        if (n.getOctave() == s.getOctave() && n.getPitch() == s.getPitch() &&
            s.getStartBeat() == beat) {
          result.add(noteRange.indexOf(n));
        }
      }
    }
    return result;
  }

  @Override
  public List<Integer> notesContinueAtThisBeat(int beat) {
    List<Integer> result = new ArrayList<>();
    TreeSet<Note> set = this.notes.get(beat);
    List<Note> noteRange = this.getNoteRange();
    if (set == null || set.isEmpty()) {
      return new ArrayList<>();
    }
    for (Note n : noteRange) {
      for (Note s : set) {
        if (n.getOctave() == s.getOctave() && n.getPitch() == s.getPitch() &&
            s.getStartBeat() != beat) {
          result.add(noteRange.indexOf(n));
        }
      }
    }
    return result;
  }

  @Override
  public int getNumBeats() {
    int result = 0;
    ArrayList<Integer> keys = new ArrayList<>(notes.keySet());
    for (int i : keys) {
      if (!notes.get(i).isEmpty()) {
        result = i;
      }
    }
    return result;
  }

  @Override
  public List<Note> getNoteRange() {
    List<Note> noteRange = new ArrayList<>();
    if (this.notes.isEmpty()) {
      return noteRange;
    }
    Note lowest = this.lowestNote();
    Note highest = this.highestNote();
    if (lowest.getOctave() == highest.getOctave()) {
      for (PitchType pitch : PitchType.values()) {
        if (pitch.getToneOrder() >= lowest.getPitch().getToneOrder() &&
            pitch.getToneOrder() <= highest.getPitch().getToneOrder()) {
          noteRange.add(new Note(pitch, 0, 0, lowest.getOctave()));
        }
      }
    } else {
      for (PitchType pitch : PitchType.values()) {
        if (pitch.getToneOrder() >= lowest.getPitch().getToneOrder()) {
          noteRange.add(new Note(pitch, 0, 0, lowest.getOctave()));
        }
      }
      for (int i = lowest.getOctave() + 1; i < highest.getOctave(); i++) {
        for (PitchType pitch : PitchType.values()) {
          noteRange.add(new Note(pitch, 0, 0, i));
        }
      }
      for (PitchType pitch : PitchType.values()) {
        if (pitch.getToneOrder() <= highest.getPitch().getToneOrder()) {
          noteRange.add(new Note(pitch, 0, 0, highest.getOctave()));
        }
      }
    }
    return noteRange;
  }

  @Override
  public List<Integer> getInstruments() {
    List<Integer> result = new ArrayList<>();
    for (Map.Entry<Integer, TreeSet<Note>> entry : notes.entrySet()) {
      for (Note n : entry.getValue()) {
        if (!result.contains(n.getInstrument())) {
          result.add(n.getInstrument());
        }
      }
    }
    return result;
  }

  @Override
  public List<Integer> notesStopAtThisBeat(int beat) {
    List<Integer> result = new ArrayList<>();
    if (beat == this.getNumBeats()) {
      result.addAll(this.notesContinueAtThisBeat(beat));
      result.addAll(this.notesStartAtThisBeat(beat));
    }
    List<Integer> contNow = this.notesContinueAtThisBeat(beat);
    List<Integer> startNow = this.notesStartAtThisBeat(beat);
    List<Integer> contNext = this.notesContinueAtThisBeat(beat + 1);
    for (Integer i : contNow) {
      if (!contNext.contains(i) && !result.contains(i) &&
          !startNow.contains(i)) {
        result.add(i);
      }
    }
    for (Integer i : startNow) {
      if (!contNext.contains(i) && !result.contains(i)) {
        result.add(i);
      }
    }
    return result;
  }

  @Override
  public Note getNote(int index, int beat) throws IllegalArgumentException {
    List<Note> range = this.getNoteRange();
    Note result;
    if (this.notes.get(beat) == null) {
      throw new IllegalArgumentException("Cannot find note");
    }
    for (Note n : this.notes.get(beat)) {
      if (n.getPitch().getToneOrder() == range.get(index).getPitch().getToneOrder() &&
          n.getOctave() == range.get(index).getOctave()) {
        return n;
      }
    }
    throw new IllegalArgumentException("Cannot find note");
  }

  @Override
  public void setTempo(int tempo) {
    this.tempo = tempo;
  }

  @Override
  public int getTempo() throws IllegalStateException {
    if (tempo != 0) {
      return this.tempo;
    } else {
      throw new IllegalStateException("Tempo has not been set yet.");
    }
  }

}