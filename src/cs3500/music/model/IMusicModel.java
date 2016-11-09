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
   * Replace the first Note with the second Note.
   *
   * @param from The Note to delete from the model
   * @param to   The Note to add to the model
   */
  void replace(Note from, Note to);

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
   * range of Notes in this piece.
   *
   * @param beat The beat to analyze
   * @return The List of indicies of Notes that start at the given beat
   */
  List<Integer> notesStartAtThisBeat(int beat);

  /**
   * Return a {@code List<Integer>} of the indicies of the Notes that continue at this beat in the
   * range of Notes in this piece.
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
   * but are not longer playing.
   *
   * @param beat The beat at which Notes stop
   * @return The List of indicies of Notes that stop before the given beat
   */
  List<Integer> notesStopAtThisBeat(int beat);

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
}
