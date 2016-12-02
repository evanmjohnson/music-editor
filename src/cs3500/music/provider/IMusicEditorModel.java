package cs3500.music.provider;

import java.util.List;

/**
 * This is the interface of the Music Editor model; it represents
 * one piece of music.
 */
public interface IMusicEditorModel {

  /**
   * Adds a {@code Note} to the model.
   */
  void addNote(Note note);

  /**
   * Removes a {@code Note} at the given beat to the model.
   */
  void removeNote(Note note);

  /**
   * Edits a {@code Note} in the model.
   */
  void editNote(Note note1, Note note2);

  /**
   * Sets the tempo of the model.
   */
  void setTempo(int tempo);

  /**
   * Returns the current {@code tempo} of this model.
   *
   * @return the tempo as a int
   */
  int getTempo();

  /**
   * Returns the current {@code lowerNote} of this model.
   *
   * @return the lowermost note
   */
  Note getLowerNote();

  /**
   * Returns the current {@code upperNote} of this model.
   *
   * @return the uppermost note
   */
  Note getUpperNote();

  /**
   * Returns the current {@code finalBeat} of this model.
   *
   * @return the final beat as an int
   */
  int getFinalBeat();

  /**
   * Returns the current set of notes in this model at the given beat.
   *
   * @return the notes as an ArrayList
   */
  List<Note> getAllNotesAtBeat(int beat);

  /**
   * Returns the current set of notes in this model that start at the given beat.
   *
   * @return the notes as an ArrayList
   */
  List<Note> getStartingNotesAtBeat(int beat);

  /**
   * Returns the number of beats in the music.
   *
   * @return number of beats
   */
  public int getTotalDuration();

  /**
   * Merges two sets of music.
   *
   * @param model        the model to be merged with this one
   * @param beatsShifted where to begin the given music with respect to this one
   * @throws IllegalArgumentException if the given music is invalid
   */
  public void merge(IMusicEditorModel model, int beatsShifted) throws IllegalArgumentException;

  /**
   * Appends a set of music to this one.
   *
   * @param model the set of music to be added
   * @throws IllegalArgumentException if the given music is invalid
   */
  public void append(IMusicEditorModel model) throws IllegalArgumentException;
}
