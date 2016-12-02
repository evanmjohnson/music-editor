package cs3500.music.provider;

import java.util.List;
import java.util.Objects;

/**
 * This class represents a MusicEditorModel accessible by the view, such that
 * certain information is not accessible.
 */
public class ViewModel {
  private IMusicEditorModel model;

  /**
   * Constructs a ViewModel of a particular MusicEditorModel.
   *
   * @param m the Music Editor model
   * @throws IllegalArgumentException if any argument is not valid
   */
  public ViewModel(IMusicEditorModel m) {
    try {
      Objects.requireNonNull(m);
    } catch (NullPointerException x) {
      throw new IllegalArgumentException("Model cannot be given as null.");
    }

    this.model = m;
  }

  /**
   * Returns the current lowerNote of the model.
   *
   * @return the lowermost note
   */
  public Note getLowerNote() {
    return this.model.getLowerNote();
  }

  /**
   * Returns the current upperNote of the model.
   *
   * @return the uppermost note
   */
  public Note getUpperNote() {
    return this.model.getUpperNote();
  }

  /**
   * Returns the current finalBeat of the model.
   *
   * @return the final beat as an int
   */
  public int getFinalBeat() {
    return this.model.getFinalBeat();
  }

  /**
   * Returns the current tempo of the model.
   *
   * @return the tempo as a int
   */
  public int getTempo() {
    return this.model.getTempo();
  }

  public void setTempo(int x) {
    this.model.setTempo(x);
  }

  /**
   * Returns the current set of notes in this model at the given beat.
   *
   * @return the notes as an ArrayList
   */
  public List<Note> getAllNotesAtBeat(int beat) {
    return this.model.getAllNotesAtBeat(beat);
  }

  /**
   * Returns the current set of notes in this model that start at the given beat.
   *
   * @return the notes as an ArrayList
   */
  public List<Note> getStartingNotesAtBeat(int beat) {
    return this.model.getStartingNotesAtBeat(beat);
  }

  /**
   * Returns the actual IMusicEditorModel associated with this ViewModel.
   *
   * @return the model
   */
  public IMusicEditorModel getModel() {
    return this.model;
  }
}
