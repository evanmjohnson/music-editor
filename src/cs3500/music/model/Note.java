package cs3500.music.model;

import java.util.Objects;

/**
 * Represents a Note in the music editor.
 */
public class Note implements Comparable<Note> {
  private PitchType pitch;
  private int startBeat;
  private int duration;
  private int octave;
  private int instrument;
  private int volume;
  private boolean selected;

  /**
   * Creates a new Note with default values for instrument and volume.
   *
   * @param pitch     The PitchType of this Note
   * @param startBeat The beat at which this Note starts
   * @param duration  How many beats this Note is sustained, including the starting beat
   * @param octave    The octave of this Note
   */
  public Note(PitchType pitch, int startBeat, int duration, int octave) {
    if (duration < 0) {
      throw new IllegalArgumentException("Duration cannot be negative");
    }
    if (startBeat < 0) {
      throw new IllegalArgumentException("Start beat must be positive");
    }
    if (octave < 0) {
      throw new IllegalArgumentException("Octave cannot be negative");
    }
    Objects.requireNonNull(pitch);
    this.pitch = pitch;
    this.startBeat = startBeat;
    this.duration = duration;
    this.octave = octave;
    this.instrument = 0;
    this.volume = 127;
    this.selected = false;
  }

  /**
   * Creates a new Note with all given paramaters.
   *
   * @param pitch      The PitchType
   * @param startBeat  The beat at which this Note starts
   * @param duration   How many beats this Note is sustained, including the starting beat
   * @param octave     The ocatve of this Note
   * @param instrument The instrument that plays this Note
   * @param volume     The volume of this Note
   */
  public Note(PitchType pitch, int startBeat, int duration, int octave,
              int instrument, int volume) {
    if (duration < 0) {
      throw new IllegalArgumentException("Duration cannot be negative");
    }
    if (startBeat < 0) {
      throw new IllegalArgumentException("Start beat must be positive");
    }
    if (octave < 0) {
      throw new IllegalArgumentException("Octave cannot be negative");
    }
    Objects.requireNonNull(pitch);
    this.pitch = pitch;
    this.startBeat = startBeat;
    this.duration = duration;
    this.octave = octave;
    if (instrument > 127 || instrument < 0) {
      throw new IllegalArgumentException("Instrument must be between 0 and 127.");
    }
    this.instrument = instrument;
    if (volume > 127 || volume < 0) {
      throw new IllegalArgumentException("Volume must be between 0 and 127.");
    }
    this.volume = volume;
    this.selected = false;
  }

  @Override
  public String toString() {
    return this.pitch.toString() + Integer.toString(this.octave);
  }

  /**
   * Compares this Note with the given Note.
   *
   * @param n The Note to compare to
   * @return A negative integer, zero, or a positive integer as this Note is less than,
   *         equal to, or greater than the given Note.
   */
  public int compareTo(Note n) {
    if (this.octave == n.octave) {
      return this.pitch.getToneOrder() - n.pitch.getToneOrder();
    }
    return this.octave - n.octave;
  }

  /**
   * Get the value of this Note's pitch.
   *
   * @return This Note's PitchType
   */
  public PitchType getPitch() {
    return this.pitch;
  }

  /**
   * Get the value of this Note's octave.
   *
   * @return This Note's octave
   */
  public int getOctave() {
    return this.octave;
  }

  /**
   * Get the value of this Note's starting beat.
   *
   * @return This Note's starting beat
   */
  public int getStartBeat() {
    return this.startBeat;
  }

  /**
   * Get this Note's duration.
   *
   * @return This Note's duration
   */
  public int getDuration() {
    return this.duration;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Note)) {
      return false;
    }
    Note that = (Note) o;
    return that.pitch == this.pitch && this.octave == that.octave &&
        this.startBeat == that.startBeat && this.duration == that.duration &&
        this.instrument == that.instrument && this.volume == that.volume;
  }

  /**
   * Gets the instrument of this Note.
   *
   * @return The instrument type of this Note
   */
  public int getInstrument() {
    return this.instrument;
  }

  /**
   * Gets the volume of this Note.
   *
   * @return The volume of this Note
   */
  public int getVolume() {
    return this.volume;
  }

  /**
   * Sets this Note's selected field to the given boolean.
   * @param selected What to set this Note's selected field as
   */
  public void makeSelected(boolean selected) {
    this.selected = selected;
  }
}
