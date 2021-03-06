package cs3500.music.model;

import java.util.List;

/**
 * Represents the model interface of a music editor.
 */
public interface IMusicModel {

  /**
   * Adds the given Note to the model.
   *
   * @param n The Note to add
   */
  void add(Note n);

  /**
   * Adds the given repeat to the model.
   * @param repeat the repeat to add
   */
  void addRepeat(Repeat repeat);

  /**
   * Replace the first Note with the second Note.
   *
   * @param from The Note to delete from the model
   * @param to   The Note to add to the model
   */
  void replace(Note from, Note to);

  /**
   * Gets the list of repeats in this piece.
   * @return a list of repeats in this piece
   */
  List<Repeat> getRepeats();

  /**
   * Gets the beginning beat of a repeat, given that the given beat is at the end of a repeat
   * in the piece.
   * @param beat the beat at which a repeat ends
   * @return the beat at which the given repeat starts at
   */
  int getBeginningofRepeat(int beat);

  /**
   * Checks if a repeat ends at the given beat.
   * @param beat the beat at which to check
   * @return if there is a repeat at the given beat
   */
  boolean repeatEndsHere(int beat);

  /**
   * Remove the given Note from the model, if it exists.
   *
   * @param n The Note to remove
   * @throws IllegalArgumentException If the given Note is not in the model
   */
  void remove(Note n) throws IllegalArgumentException;

  /**
   * Combines a MusicEditor with another MusicEditor such that they play simultaneously.
   *
   * @param model2 the other editor to combine with.
   */
  void playSimultaneously(IMusicModel model2);

  /**
   * Combines a MusicEditor with another MusicEditor such that they play consecutively.
   *
   * @param model2 the other editor to combine with.
   */
  void playConsecutively(IMusicModel model2);

  /**
   * Combines two pieces of music such that they play simultaneously.
   *
   * @param sheet1 the other piece of music to combine with.
   */
  void combineSimultaneously(MusicModel sheet1);

  /**
   * Combines two pieces of music such that they play consecutively.
   * Sheet1 will play after this.
   *
   * @param sheet1 the other piece of music to combine with.
   */
  void combineConsecutively(MusicModel sheet1);

  /**
   * Gets the length of this piece of music.
   *
   * @return The length of this model
   */
  int length();

  /**
   * Return a {@code List<Integer>} of the indicies of the Notes that start at this beat in the
   * range of Notes in this piece, sorted from lowest to highest.
   *
   * @param beat The beat to analyze
   * @return The List of indicies of Notes that start at the given beat
   */
  List<Integer> notesStartAtThisBeat(int beat);

  /**
   * Return a {@code List<Integer>} of the indicies of the Notes that continue at this beat in the
   * range of Notes in this piece, sorted from lowest to highest.
   *
   * @param beat The beat to analyze
   * @return The List of indicies of Notes that continue at the given beat
   */
  List<Integer> notesContinueAtThisBeat(int beat);

  /**
   * Get the number of beats in this piece.
   *
   * @return The number of beats in this piece
   */
  int getNumBeats();

  /**
   * Returns all the notes within the range for this music composition.
   *
   * @return Ranges of notes.
   */
  List<Note> getNoteRange();

  /**
   * Gets all of the instruments in this piece.
   *
   * @return The {@code List<Integer>} of instruments in this piece
   */
  List<Integer> getInstruments();

  /**
   * Gets all of the Notes in this piece that were playing the beat before the given beat,
   * but are not longer playing, sorted from lowest to highest.
   *
   * @param beat The beat at which Notes stop
   * @return The List of indicies of Notes that stop before the given beat
   */
  List<Integer> notesStopAtThisBeatLowestToHighest(int beat);

  /**
   * Gets the Note at the given beat and the given index in the range of Notes.
   *
   * @param index The index of the Note in the range of this piece
   * @param beat  The beat at which this Note plays
   * @return The Note that fits the criteria
   * @throws IllegalArgumentException If the Note cannot be found
   */
  Note getNote(int index, int beat) throws IllegalArgumentException;

  /**
   * Set the tempo of this piece to the given tempo.
   *
   * @param tempo The tempo to set
   */
  void setTempo(int tempo);

  /**
   * Get the tempo of this piece.
   *
   * @return The tempo of this piece
   * @throws IllegalStateException If the tempo of this piece has not been set
   */
  int getTempo() throws IllegalStateException;

  /**
   * Gets the lowest Note in this piece.
   *
   * @return The lowest Note in this piece
   */
  Note lowestNote();

  /**
   * Gets the highest Note in this piece.
   *
   * @return The highest Note in this piece
   */
  Note highestNote();

  /**
   * Gets the List of Notes that start at the given beat.
   *
   * @param beat The beat at which to check
   * @return A {@code List<Note>} of the Notes that start at the given beat
   */
  List<Note> noteListStartAt(int beat);

  /**
   * Gets the List of Notes that continue at the given beat.
   *
   * @param beat The beat at which to check
   * @return A {@code List<Note>} of the Notes that continue at the given beat
   */
  List<Note> noteListContinueAt(int beat);

  /**
   * Adds the given Repeat as an ending to the last Repeat in the piece
   * @param ending the ending to add as a {@link Repeat}
   */
  void addEnding(Repeat ending);

  /**
   * Gets the repeat at the given beat, if there is one.
   * @param beat the beat to get the repeat at
   * @return the repeat at the given beat
   */
  Repeat getRepeat(int beat);

  /**
   * Gets the first beat of the next ending for the repeat that ends at the given beat.
   * Then, delete that ending so it will not be played again.
   * @param beat the beat at which to get the next ending of
   * @return the starting beat of the next ending for the repeat that ends at the given beat
   */
  int getNextEnding(int beat);

  /**
   * Gets the starting beat of the parent of the repeat at the given beat.
   * @param beat the beat at which an ending of the parent repeat stops at
   * @return the start beat of the parent repeat that has an ending that ends at the given beat
   */
  int getParent(int beat);

  /**
   * Gets every single start and end beat of every repeat and ending in this piece. Returns one list
   * of the repeat beats and one list of the ending beats.
   * @return A {@code List<List<Integer>>} representing every start and end beat of repeats
   *         in this piece in the first list, and every beat of every ending in the second list.
   */
  List<List<Integer>> getEveryRepeatBeat();
}
