package cs3500.music.provider;

import java.util.List;

import cs3500.music.model.Note;
import cs3500.music.model.Pitch;

/**
 * This class represents the model as a string printed to the console.
 */
public final class ConsoleView implements IMusicView {
  private String result = "";

  @Override
  public void renderModel(ViewModel vm) {
    int modelLength = vm.getFinalBeat() + 1;

    for (int index = 0; index < Integer.toString(vm.getFinalBeat()).length() - 2; index++) {
      result += " ";
    }

    if (modelLength > 0) {
      for (int i = vm.getLowerNote().getNoteVal(); i <= vm.getUpperNote().getNoteVal(); i = i + 1) {
        result = result + this.center(String.format("%" + 5 + "s", Pitch.noteValToString(i)));
      }
      for (int i = 0; i <= vm.getFinalBeat(); i = i + 1) {
        result = result + (String.format("\n%" + Integer.toString(vm.getFinalBeat()).length()
                + "d", i)) + printNotes(vm, i);
      }
    }
    System.out.print(result);
  }

  public String getResult() {
    return this.result;
  }

  /**
   * Centers the column indicators by adding spaces.
   *
   * @param str the string to center
   * @return the appended string
   */
  private String center(String str) {
    int buffer = 5 - str.length();
    for (int i = 0; i < (buffer / 2); i++) {
      str += " ";
    }
    while (str.length() < 5) {
      str = " " + str;
    }
    return str;
  }

  /**
   * Represents all of the {@node Note}s at a given beat as a String.
   *
   * @param beat the current beat
   * @return all of the notes at the current beat as a String
   */
  private StringBuilder printNotes(ViewModel vm, int beat) {
    List<Note> notesToPrint = vm.getAllNotesAtBeat(beat);
    int b = (vm.getUpperNote().getNoteVal() - vm.getLowerNote().getNoteVal()) * 5 + 2;
    StringBuilder result = new StringBuilder(String.format("%" + b + "s", ""));

    for (Note note : notesToPrint) {
      int i = (5 * (note.getNoteVal() - vm.getLowerNote().getNoteVal())) + 1;
      if (note.getStartTime() == beat) {
        result.setCharAt(i, 'X');
      }
      if (beat > note.getStartTime() && beat <= (note.getStartTime() + note.getDuration()) - 1) {
        result.setCharAt(i, '|');
      }
    }
    return result;
  }

}
