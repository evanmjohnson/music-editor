package cs3500.music.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a repeat in the music.
 */
public class Repeat {
  private int startBeat;
  private int endBeat;
  private List<Repeat> endings;
  private Repeat parent;

  /**
   * Constructs a new Repeat object with only an ending beat. Defaults the starting beat to 0.
   * @param endBeat the ending beat of this Repeat
   */
  public Repeat(int endBeat) {
    this.startBeat = 0;
    this.endBeat = endBeat;
  }

  /**
   * Constructs a new Repeat object with an starting and an ending beat.
   * @param startBeat the starting beat of this Repeat
   * @param endBeat the ending beat of this Repeat
   */
  public Repeat(int startBeat, int endBeat) {
    this.startBeat = startBeat;
    this.endBeat = endBeat;
  }

  /**
   * Constructs a new Repeat object with an starting and an ending beat.
   * @param startBeat the starting beat of this Repeat
   * @param endBeat the ending beat of this Repeat
   * @param parent the parent of this Repeat. In other words, this Repeat is an ending of the
   *               given Repeat parent
   */
  public Repeat(int startBeat, int endBeat, Repeat parent) {
    this.startBeat = startBeat;
    this.endBeat = endBeat;
    this.parent = parent;
  }

  /**
   * Constructs a new Repeat object with a starting beat, ending beat, and a list of other Repeats
   * that represent the varied endings of this repeat.
   * @param startBeat the starting beat of this Repeat
   * @param endBeat the ending beat of this Repeat
   * @param endings a {@code List<Repeat>} that represents the various endings that are played
   *                every time this Repeat is played
   * @param parent the parent of this Repeat. In other words, this Repeat is an ending of the
   *               given Repeat parent
   */
  public Repeat(int startBeat, int endBeat, List<Repeat> endings, Repeat parent) {
    this.startBeat = startBeat;
    this.endBeat = endBeat;
    this.endings = endings;
    this.parent = parent;
  }

  /**
   * Gets the beat where this repeat starts.
   *
   * @return the beat where this repeat starts
   */
  public int getStartBeat() {
    return startBeat;
  }

  /**
   * Gets the beat where this repeat ends.
   *
   * @return the beat where this repeat ends
   */
  public int getEndBeat() {
    return this.endBeat;
  }

  /**
   * Add an ending to this repeat.
   * @param ending the ending to add to this repeat
   */
  public void addEnding(Repeat ending) {
    Repeat endingWithParent = new Repeat(ending.getStartBeat(), ending.getEndBeat(), this);
    if (this.endings == null) {
      this.endings = new ArrayList<>();
    }
    endings.add(endingWithParent);
  }

  /**
   * Returns the list of endings in this Repeat.
   * @return the list of repeats that represent the endings of this repeat
   */
  public List<Repeat> getEndings() {
    return this.endings;
  }

  /**
   * Checks if this repeat has multiple endings or not.
   * @return if this repeat has more than one ending
   */
  public boolean hasEndings() {
    return this.endings != null && this.endings.size() > 0;
  }

  /**
   * Checks if this repeat has a parent or not.
   * @return if this repeat has a parent
   */
  public boolean hasParent() {
    return this.parent != null;
  }

  /**
   * Gets the parent of this repeat.
   * @return the parent of this repeat
   */
  public Repeat getParent() {
    return this.parent;
  }

  /**
   * Deletes the first ending in this repeat.
   */
  public void deleteEnding() {
    if (this.endings != null && this.endings.size() > 0) {
      this.endings.remove(0);
    }
  }
}