package cs3500.music.model;

import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map;
import java.util.ArrayList;

/**
 * Represents a piece of music that can be manipulated.
 * Implements the {@link IMusicModel} interface.
 */
public class MusicModel implements IMusicModel {

  private TreeMap<Integer, TreeSet<Note>> notes;
  private List<Note> noteRange;

  public MusicModel() {
    this.notes = new TreeMap<>();
    this.noteRange = new ArrayList<>();
  }

  /**
   * Adds the given Note to this model. If there is already a Note sustained at the same Pitch and
   * octave at the starting beat of the given Note, override that Note for the duration of the
   * given Note.
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
      }
      else {
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
    for(Map.Entry<Integer,TreeSet<Note>> entry : notes.entrySet()) {
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
  public String getState() {
    if (this.notes.isEmpty()) {
      return "";
    }
    StringBuilder sb = new StringBuilder();
    int totalDigits = new Integer(this.length()).toString().length();
    sb.append(this.printFirstRow() + "\n");
    for (int i = 0; i <= notes.lastKey(); i++) {
      int digits = new Integer(i).toString().length();
      for (int j = digits; j <= totalDigits - digits; j++) {
        sb.append(" ");
      }
      sb.append(i);
      TreeSet<Note> set;
      try {
        set = notes.get(i);
      }
      catch (NullPointerException e) {
        set = new TreeSet<>();
      }
      for (Note n : noteRange) {
        if (this.containsNote(set, n.getPitch(), n.getOctave())) {
          Note note = set.pollFirst();
          if (note.getStartBeat() == i) {
            sb.append("  X  ");
          }
          else {
            sb.append("  |  ");
          }
        }
        else {
          sb.append("     ");
        }
      }
      sb.append("\n");
    }
    return sb.toString();
  }

  /**
   * Gets the first row of the console output.
   * @return String respresentation of all of the notes between the lowest and highest notes
   * in this piece.
   */
  private String printFirstRow() {
    StringBuilder sb = new StringBuilder();
    int totalDigits = new Integer(this.length()).toString().length();
    Note lowest = this.lowestNote();
    Note highest = this.highestNote();
    for (int i = 0; i < totalDigits; i++) {
      sb.append(" ");
    }
    if (lowest.getOctave() == highest.getOctave()) {
      for (PitchType pitch : PitchType.values()) {
        if (pitch.getToneOrder() >= lowest.getPitch().getToneOrder() &&
            pitch.getToneOrder() <= highest.getPitch().getToneOrder()) {
          sb.append(this.center(pitch.getToneValue() + "" + lowest.getOctave()));
          if (this.noteRange.size() < this.getNoteRangeSize()) {
            this.noteRange.add(new Note(pitch, 0, 0, lowest.getOctave()));
          }
        }
      }
    }
    else {
      for (PitchType pitch : PitchType.values()) {
        if (pitch.getToneOrder() >= lowest.getPitch().getToneOrder()) {
          sb.append(this.center(pitch.getToneValue() + "" + lowest.getOctave()));
          if (this.noteRange.size() < this.getNoteRangeSize()) {
            this.noteRange.add(new Note(pitch, 0, 0, lowest.getOctave()));
          }
        }
      }
      for (int i = lowest.getOctave() + 1; i < highest.getOctave(); i++) {
        for (PitchType pitch : PitchType.values()) {
          sb.append(this.center(pitch.getToneValue() + "" + i));
          if (this.noteRange.size() < this.getNoteRangeSize()) {
            this.noteRange.add(new Note(pitch, 0, 0, i));
          }
        }
      }
      for (PitchType pitch : PitchType.values()) {
        if (pitch.getToneOrder() <= highest.getPitch().getToneOrder()) {
          sb.append(this.center(pitch.getToneValue() + "" + highest.getOctave()));
        }
        if (this.noteRange.size() < this.getNoteRangeSize()) {
          this.noteRange.add(new Note(pitch, 0, 0, highest.getOctave()));
        }
      }
    }
    return sb.toString();
  }

  /**
   * Determine whether or not if the given {@code TreeSet<Note>} has a Note with the given Pitch
   * and octave.
   * @param set The {@code TreeSet<Note>} to check
   * @param pitch The Pitch to check against the TreeSet
   * @param octave The octave to check against the TreeSet
   * @return If the given TreeSet has a Note with the given Pitch and octave
   */
  private boolean containsNote(TreeSet<Note> set, PitchType pitch, int octave) {
    if (set == null || set.isEmpty()) {
      return false;
    }
    for (Note n : set) {
      if (n.getPitch() == pitch && n.getOctave() == octave) {
        return true;
      }
    }
    return false;
  }

  /**
   * Gets the lowest Note in this piece.
   * @return The lowest Note in this piece
   */
  private Note lowestNote() {
    TreeSet<Note> resultSet = new TreeSet<>();
    for (Map.Entry<Integer, TreeSet<Note>> entry : notes.entrySet()) {
      if (!entry.getValue().isEmpty()) {
        resultSet.add(entry.getValue().first());
      }
    }
    return resultSet.first();
  }

  /**
   * Gets the highest Note in this piece.
   * @return The highest Note in this piece
   */
  private Note highestNote() {
    TreeSet<Note> resultSet = new TreeSet<>();
    for (Map.Entry<Integer, TreeSet<Note>> entry : notes.entrySet()) {
      if (!entry.getValue().isEmpty()) {
        resultSet.add(entry.getValue().last());
      }
    }
    return resultSet.last();
  }

  /**
   * Centers the given String in the middle of a length 5 String.
   * @param s The String to center
   * @return The centered String
   */
  private String center(String s) {
    StringBuilder sb = new StringBuilder(5);
    for (int i = 0; i < (5 - s.length()) / 2; i++) {
      sb.append(" ");
    }
    sb.append(s);
    while (sb.length() < 5) {
      sb.append(" ");
    }
    return sb.toString();
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
    for(Map.Entry<Integer,TreeSet<Note>> entry : notes.entrySet()) {
      for (Note n : entry.getValue()) {
        sheet1.add(n);
      }
    }
  }

  @Override
  public void combineConsecutively(MusicModel sheet1) {
    ArrayList<Note> notesToAdd = new ArrayList<>();
    for(Map.Entry<Integer,TreeSet<Note>> entry : notes.entrySet()) {
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
    noteRange = this.getNoteRange();
    if (set == null || set.isEmpty()) {
      return new ArrayList<>();
    }
    for (Note n : this.noteRange) {

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
    //problematic?
    noteRange = this.getNoteRange();
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
    for(int i : keys) {
      if (!notes.get(i).isEmpty()) {
        result = i;
      }
    }
    return result;
  }

  /**
   * Gets the amount of notes in the range of this piece.
   * @return The int value of the size of the Note range of this piece
   */
  private int getNoteRangeSize() {
    int result = 0;
    Note lowest = this.lowestNote();
    Note highest = this.highestNote();
    List<Note> lowestToHighest = new ArrayList<>();
    if (lowest.getOctave() == highest.getOctave()) {
      for (PitchType pitch : PitchType.values()) {
        if (pitch.getToneOrder() >= lowest.getPitch().getToneOrder() &&
            pitch.getToneOrder() <= highest.getPitch().getToneOrder()) {
          result++;
        }
      }
    }
    else {
      for (PitchType pitch : PitchType.values()) {
        if (pitch.getToneOrder() >= lowest.getPitch().getToneOrder()) {
          result++;
        }
      }
      for (int i = lowest.getOctave() + 1; i < highest.getOctave(); i++) {
        for (PitchType pitch : PitchType.values()) {
          result++;
        }
      }
      for (PitchType pitch : PitchType.values()) {
        if (pitch.getToneOrder() <= highest.getPitch().getToneOrder()) {
          result++;
        }
      }
    }
    return result;
  }

  @Override
  public List<Note> getNoteRange() {
    if (this.noteRange.size() == this.getNoteRangeSize()) {
      return this.noteRange;
    }
    else {
      String dontuse = this.printFirstRow();
      return this.noteRange;
    }
  }
}