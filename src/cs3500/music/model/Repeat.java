package cs3500.music.model;

/**
 * Represents a repeat in the music.
 */
public class Repeat {
  private int startBeat;
  private int endBeat;

  public Repeat(int endBeat) {
    this.startBeat = 0;
    this.endBeat = endBeat;
  }

  public Repeat(int startBeat, int endBeat) {
    this.startBeat = startBeat;
    this.endBeat = endBeat;
  }

  /**
   * Gets the beat where this repeat starts.
   * @return the beat where this repeat starts
   */
  public int getStartBeat() {
    return startBeat;
  }

  /**
   * Gets the beat where this repeat ends.
   * @return the beat where this repeat ends
   */
  public int getEndBeat() {
    return this.endBeat;
  }
}
