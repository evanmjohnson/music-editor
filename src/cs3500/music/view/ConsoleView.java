package cs3500.music.view;

import cs3500.music.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the view for the music editor outputted to the console.
 * Implements the {@link IMusicView} interface
 */
public class ConsoleView implements IMusicView {
  private final Appendable ap = System.out;
  private MusicViewModel model;

  public ConsoleView(Appendable ap) {
    //this.ap = System.out;
  }

  @Override
  public void create(MusicViewModel model) {
    this.model = model;
    try {
      ap.append(this.getState());
    } catch (IOException e) {
      return;
    }
  }

  /**
   * Gets the formatted text state of the model.
   * @return The String state of the view model
   */
  private String getState() {
    StringBuilder sb = new StringBuilder();
    int totalDigits = new Integer(model.length()).toString().length();
    sb.append(this.printFirstRow() + "\n");
    for (int i = 0; i <= model.getNumBeats(); i++) {
      int digits = new Integer(i).toString().length();
      for (int j = digits; j <= totalDigits - digits; j++) {
        sb.append(" ");
      }
      sb.append(i);
      ArrayList<Integer> notesAtThisBeat = new ArrayList<>();
      try {
        notesAtThisBeat.addAll(model.notesStartAtThisBeat(i));
        notesAtThisBeat.addAll(model.notesContinueAtThisBeat(i));
      }
      catch (NullPointerException e) {
        notesAtThisBeat = new ArrayList<>();
      }
      for (Note n : model.getNoteRange()) {
        if (this.containsNote(notesAtThisBeat, n.getPitch(), n.getOctave())) {
          if (n.getStartBeat() == i) {
            sb.append("  X  ");
          }
          else {
            sb.append("  |  ");
          }
        }
        else {
          sb.append("     ");
        }
        notesAtThisBeat.remove(0);
      }
      sb.append("\n");
    }
    return sb.toString();
  }

  /**
   * Determine whether or not if the given {@code ArrayList<Integer>} representing indicies of
   * Notes in the range of Notes in this piece has a Note with the given Pitch and octave.
   * @param notes The {@code ArrayList<Integer>} to check
   * @param pitch The Pitch to check against the ArrayList
   * @param octave The octave to check against the ArrayList
   * @return If the given ArrayList has an index of a Note with the given Pitch and octave
   */
  private boolean containsNote(ArrayList<Integer> notes, PitchType pitch, int octave) {
    if (notes == null || notes.isEmpty()) {
      return false;
    }
    for (Integer i : notes) {
      if (model.getNoteRange().get(i).getPitch() == pitch &&
          model.getNoteRange().get(i).getOctave() == octave) {
        return true;
      }
    }
    return false;
  }

  /**
   * Gets the first row of the console output.
   * @return String respresentation of all of the notes between the lowest and highest notes
   * in this piece.
   */
  private String printFirstRow() {
    StringBuilder sb = new StringBuilder();
    int totalDigits = new Integer(model.length()).toString().length();
    Note lowest = model.lowestNote();
    Note highest = model.highestNote();
    List<Note> range = model.getNoteRange();
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
          sb.append(this.center(pitch.getToneValue() + "" + i));
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
  public void makeVisible() {
    return;
  }
}
