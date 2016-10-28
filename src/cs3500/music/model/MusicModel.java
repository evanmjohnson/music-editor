package cs3500.music.model;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Represents a piece of music that can be manipulated.
 * Implements the {@link IMusicModel} interface.
 */
public class MusicModel implements IMusicModel {

  private TreeMap<Integer, TreeSet<Note>> notes;

  public MusicModel() {
    this.notes = new TreeMap<>();
  }

  @Override
  public void add(Note n) {
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
    for(Map.Entry<Integer,TreeSet<Note>> entry : notes.entrySet()) {
      TreeSet<Note> set = entry.getValue();
      if (set.contains(n)) {
        set.remove(n);
      }
    }
  }

  @Override
  public String getState() {
    StringBuilder sb = new StringBuilder();
    sb.append(this.printFirstRow());
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
        }
      }
    }
    else {
      for (PitchType pitch : PitchType.values()) {
        if (pitch.getToneOrder() >= lowest.getPitch().getToneOrder()) {
          sb.append(this.center(pitch.getToneValue() + "" + lowest.getOctave()));
        }
      }
      for (int i = lowest.getOctave() + 1; i < highest.getOctave(); i++) {
        for (PitchType pitch : PitchType.values()) {
          sb.append(this.center(pitch.getToneValue()) + "" + i);
        }
      }
      for (PitchType pitch : PitchType.values()) {
        if (pitch.getToneOrder() <= highest.getPitch().getToneOrder()) {
          sb.append(this.center(pitch.getToneValue() + "" + highest.getOctave()));
        }
      }
    }
    return sb.toString();
  }

  /**
   * Gets the lowest Note in this piece.
   * @return The lowest Note in this piece
   */
  private Note lowestNote() {
    TreeSet<Note> resultSet = new TreeSet<>();
    for (Map.Entry<Integer, TreeSet<Note>> entry : notes.entrySet()) {
      resultSet.add(entry.getValue().first());
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
      resultSet.add(entry.getValue().last());
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
    return notes.lastKey() + 1;
  }
}