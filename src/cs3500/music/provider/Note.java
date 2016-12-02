package cs3500.music.provider;

import java.util.Objects;

/**
 * This class represents any one Note.
 */
public final class Note {
  private int noteVal;
  private Pitch pitch;
  private int octave;
  private int instrument;
  private int volume;
  private int duration;
  private int startTime;

  /**
   * Constructs a note of a particular pitch, octave, instrument, volume, duration, and start time.
   *
   * @param pitch      the pitch of the note
   * @param octave     the octave of the note
   * @param instrument the instrument playing the note
   * @param volume     the volume of the note
   * @param duration   the duration of the note in beats
   * @param startTime  the start time of the note
   * @throws IllegalArgumentException if any argument is not valid
   */
  public Note(Pitch pitch, int octave, int instrument, int volume, int duration, int startTime)
          throws IllegalArgumentException {
    if (!(pitch instanceof Pitch)) {
      throw new IllegalArgumentException("Invalid pitch");
    }
    if (octave > 10 || octave < 0) {
      throw new IllegalArgumentException("Invalid octave");
    }
    if (instrument > 127 || instrument < 0) {
      throw new IllegalArgumentException("Invalid instrument");
    }
    if (volume < 0) {
      throw new IllegalArgumentException("Invalid volume");
    }
    if (duration < 1) {
      throw new IllegalArgumentException("Invalid duration");
    }
    if (startTime < 0) {
      throw new IllegalArgumentException("Invalid start time");
    } else {
      this.noteVal = Pitch.toNoteVal(pitch, octave);
      this.pitch = pitch;
      this.octave = octave;
      this.instrument = instrument;
      this.volume = volume;
      this.duration = duration;
      this.startTime = startTime;
    }
  }

  /**
   * Updates the {@code noteVal} of this note.
   */
  public void updateNoteVal(Pitch pitch, int octave) {
    if (!(pitch instanceof Pitch)) {
      throw new IllegalArgumentException("Invalid pitch");
    }
    if (octave > 10 || octave < 0) {
      throw new IllegalArgumentException("Invalid octave");
    }
    this.noteVal = Pitch.toNoteVal(pitch, octave);
    this.pitch = pitch;
    this.octave = octave;
  }

  /**
   * Updates the {@code instrument} of this note.
   */
  public void updateInstrument(int instrument) {
    if (instrument > 127 || instrument < 0) {
      throw new IllegalArgumentException("Invalid instrument");
    }
    this.instrument = instrument;
  }

  /**
   * Updates the {@code volume} of this note.
   */
  public void updateVolume(int volume) {
    if (volume < 0) {
      throw new IllegalArgumentException("Invalid volume");
    }
    this.volume = volume;
  }

  /**
   * Updates the {@code duration} of this note.
   */
  public void updateDuration(int duration) {
    if (duration < 1) {
      throw new IllegalArgumentException("Invalid duration");
    }
    this.duration = duration;
  }

  /**
   * Updates the {@code startTime} of this note.
   */
  public void updateStartTime(int startTime) {
    if (startTime < 0) {
      throw new IllegalArgumentException("Invalid start time");
    }
    this.startTime = startTime;
  }

  /**
   * Returns the {@code noteVal} of this note.
   *
   * @return the value of the note as an int
   */
  public int getNoteVal() {
    return this.noteVal;
  }

  /**
   * Returns the {@code pitch} of this note.
   *
   * @return the pitch of the note as a {@code Pitch}
   */
  public Pitch getPitch() {
    return this.pitch;
  }

  /**
   * Returns the {@code octave} of this note.
   *
   * @return the octave of the note as an int
   */
  public int getOctave() {
    return this.octave;
  }

  /**
   * Returns the {@code instrument} playing this note.
   *
   * @return the instrument playing the note as an int
   */
  public int getInstrument() {
    return this.instrument;
  }

  /**
   * Returns the {@code volume} of this note.
   *
   * @return the volume of the note as an int
   */
  public int getVolume() {
    return this.volume;
  }

  /**
   * Returns the {@code duration} of this note.
   *
   * @return the duration (in beats) as an int
   */
  public int getDuration() {
    return this.duration;
  }

  /**
   * Returns the {@code startTime} of this note.
   *
   * @return the starting time (beat) as an int
   */
  public int getStartTime() {
    return this.startTime;
  }

  /**
   * Determines if the inputted object is the same {@code Note} as this.
   *
   * @param that the object that is being compared to the note
   * @return a boolean telling whether or not the two are equal
   */
  @Override
  public boolean equals(Object that) {
    boolean result;

    if (this == that) {
      result = true;
    }
    if (that == null) {
      result = false;
    } else {
      Note thatNote = (Note) that;

      result = (this.getNoteVal() == thatNote.getNoteVal())
              && (this.getDuration() == thatNote.getDuration())
              && (this.getStartTime() == thatNote.getStartTime());
    }
    return result;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getNoteVal(), this.getDuration(), this.getStartTime());
  }

  /**
   * Calculates the distance between one {@code Note} and another.
   *
   * @param note1 the first note
   * @param note2 the second note
   * @return distance between pitches as an int
   */
  public static int distanceBetweenNotes(Note note1, Note note2) {
    Pitch p1 = note1.getPitch();
    Pitch p2 = note2.getPitch();
    int o1 = note1.getOctave();
    int o2 = note2.getOctave();

    return Math.abs(Pitch.toNoteVal(p1, o1) - Pitch.toNoteVal(p2, o2));
  }
}
