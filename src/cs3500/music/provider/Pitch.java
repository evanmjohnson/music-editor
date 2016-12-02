package cs3500.music.provider;

/**
 * This is an enumeration for the twelve possible <i>pitches</i> a note can have
 * in Western music. This class also includes static methods for comparing and mutating noteVals.
 */
public enum Pitch {
  C, CSHARP, D, DSHARP, E, F, FSHARP, G, GSHARP, A, ASHARP, B;

  /**
   * Converts a given {@code Pitch} to a string.
   *
   * @return pitch as a String
   */

  @Override
  public String toString() {
    String pitchName = this.name();

    return pitchName.replace("SHARP", "#");
  }

  /**
   * Takes in a {@code Pitch} and an octave and produces the note value as an int.
   *
   * @param pitch  the pitch of the note
   * @param octave the octave of the note
   * @return note value as an int
   */
  public static int toNoteVal(Pitch pitch, int octave) {
    return (12 * octave) + pitch.ordinal();
  }


  /**
   * Takes in a {@code Integer} and an octave and produces the note value as an int.
   *
   * @param noteVal int noteval of the Pitch to be returned
   * @return Pitch of the noteval
   */
  public static Pitch noteValToPitch(int noteVal) {
    Pitch result = null;

    for (Pitch p : Pitch.values()) {
      if ((noteVal % 12) == p.ordinal()) {
        result = p;
      }
    }
    return result;
  }

  public static Integer noteValToOctave(int noteVal) {
    return (noteVal / 12);
  }

  /**
   * Takes in an int and produces the note's pitch and octave as a string.
   *
   * @param noteVal the value of the note as an int
   * @return the pitch and octave of the note as a string
   */
  public static String noteValToString(int noteVal) throws IllegalArgumentException {
    if (noteVal < 0) {
      throw new IllegalArgumentException("Invalid note value; must be at least 0");
    } else {
      return noteValToPitch(noteVal).toString() + noteValToOctave(noteVal).toString();
    }
  }
}
