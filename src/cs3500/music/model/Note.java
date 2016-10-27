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

  /**
   *
   * @param pitch
   * @param startBeat
   * @param duration
   * @param octave
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
  }

  @Override
  public String toString() {
    return this.pitch.toString() + Integer.toString(this.octave);
  }

  /**
   * Compares this Note with the given Note.
   * @param n The Note to compare to
   * @return A negative integer, zero, or a positive integer as this Note is less than,
   * equal to, or greater than the given Note.
   */
  public int compareTo(Note n) {
    if (this.octave == n.octave) {
      return this.pitch.getToneOrder() - n.pitch.getToneOrder();
    }
    return this.octave - n.octave;
  }

  /**
   * Get the value of this Note's pitch.
   * @return This Note's PitchType
   */
  public PitchType getPitch() {
    return this.pitch;
  }

  /**
   * Get the value of this Note's octave.
   * @return This Note's octave
   */
  public int getOctave() {
    return this.octave;
  }

  /**
   * Get the value of this Note's starting beat.
   * @return This Note's starting beat
   */
  public int getStartBeat() {
    return this.startBeat;
  }

  /**
   * Get this Note's duration.
   * @return This Note's duration
   */
  public int getDuration() {
    return this.duration;
  }

  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Note)) {
      return false;
    }
    Note that = (Note) o;
    return that.pitch == this.pitch && this.octave == that.octave &&
        this.startBeat == that.startBeat && this.duration == that.duration;
  }
}
