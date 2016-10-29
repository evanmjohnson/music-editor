package cs3500.music.model;

import java.util.List;

/**
 * Represents the model interface of a music editor.
 */
public interface IMusicModel {

  /**
   * Adds the given Note to the model.
   * @param n The Note to add
   */
  void add(Note n);

  /**
   * Replace the first Note with the second Note.
   * @param from The Note to delete from the model
   * @param to The Note to add to the model
   */
  void replace(Note from, Note to);

  /**
   * Remove the given Note from the model, if it exists.
   * @param n The Note to remove
   * @throws IllegalArgumentException If the given Note is not in the model
   */
  void remove(Note n) throws IllegalArgumentException;

  /**
   * Returns the console version of the view for this model.
   * @return The String of the console view for the model
   */
  String getState();

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
   * @return The length of this model
   */
  int length();

  /**
   * Return a {@code List<Integer>} of the indicies of the Notes that start at this beat in the
   * range of Notes in this piece.
   * @param beat The beat to analyze
   * @return The List of indicies of Notes that start at the given beat
   */
  List<Integer> notesStartAtThisBeat(int beat);

  /**
   * Return a {@code List<Integer>} of the indicies of the Notes that continue at this beat in the
   * range of Notes in this piece.
   * @param beat The beat to analyze
   * @return The List of indicies of Notes that continue at the given beat
   */
  List<Integer> notesContinueAtThisBeat(int beat);
}
