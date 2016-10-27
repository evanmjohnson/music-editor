package cs3500.music.model;

/**
 * Enumeration for the 12 distinct types of pitches.
 */
public enum PitchType {
  C("C", 0), CSharp("C#", 1), D("D", 2), DSharp("D#", 3), E("E", 4), F("F", 5), FSharp("F#", 6),
  G("G", 7), GSharp("G#", 8), A("A", 9), ASharp("A#", 10), B("B", 11);

  private final String value;
  private final int order;

  /**
   * Constructs a {@code PitchType} object.
   *
   * @param value the String value of the PitchType
   * @param order the integer value of the PitchType
   */
  PitchType(String value, int order) {
    this.value = value;
    this.order = order;
  }

  /**
   * Gets the String value of PitchType.
   */
  public String getToneValue() {
    return this.value;
  }

  /**
   * Gets the integer order of PitchType.
   */
  public int getToneOrder() {
    return this.order;
  }

  @Override
  public String toString() {
    return this.getToneValue();
  }
}
