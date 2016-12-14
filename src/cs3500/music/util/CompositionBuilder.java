package cs3500.music.util;

/**
 * A builder of compositions.  Since we do not know in advance what
 * the name of the main type is for a model, we parameterize this builder interface
 * by an unknown type.
 *
 * @param <T> The type of the constructed composition
 */
public interface CompositionBuilder<T> {
  /**
   * Constructs an actual composition, given the notes that have been added.
   *
   * @return The new composition
   */
  T build();

  /**
   * Sets the tempo of the piece.
   *
   * @param tempo The speed, in microseconds per beat
   * @return This builder
   */
  CompositionBuilder<T> setTempo(int tempo);

  /**
   * Adds a new note to the piece.
   *
   * @param start      The start time of the note, in beats
   * @param end        The end time of the note, in beats
   * @param instrument The instrument number (to be interpreted by MIDI)
   * @param pitch      The pitch (in the range [0, 127], where 60 represents C4, the middle-C on
   *                   a piano)
   * @param volume     The volume (in the range [0, 127])
   * @return A CompositionBuilder with the note to be added.
   */
  CompositionBuilder<T> addNote(int start, int end, int instrument, int pitch, int volume);

  /**
   * Adds a new repeat to the piece.
   * @param start the start beat of the repeat
   * @param end the end beat of the repeat
   * @return A CompositionBuilder with the repeat to be added.
   */
  CompositionBuilder<T> addRepeat(int start, int end);

  /**
   * Adds a new ending to the most recently add repeat in the piece.
   * @param start the starting beat of the ending
   * @param end the end beat of the ending
   * @return A CompositionBuilder with the ending added to the most recent repeat.
   */
  CompositionBuilder<T> addEnding(int start, int end);
}
