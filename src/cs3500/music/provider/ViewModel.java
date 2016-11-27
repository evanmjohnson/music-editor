package cs3500.music.provider;

import java.util.List;
import java.util.Objects;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;

/**
 * This class represents a MusicEditorModel accessible by the view, such that
 * certain information is not accessible.
 */
public class ViewModel {
  private IMusicModel model;

  /**
   * Constructs a ViewModel of a particular MusicEditorModel.
   *
   * @param m the Music Editor model
   * @throws IllegalArgumentException if any argument is not valid
   */
  public ViewModel(IMusicModel m) {
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
    return this.model.lowestNote();
  }

  /**
   * Returns the current upperNote of the model.
   *
   * @return the uppermost note
   */
  public Note getUpperNote() {
    return this.model.highestNote();
  }

  /**
   * Returns the current finalBeat of the model.
   *
   * @return the final beat as an int
   */
  public int getFinalBeat() {
    return this.model.getNumBeats();
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
    List<Note> start = model.noteListStartAt(beat);
    List<Note> cont = model.noteListContinueAt(beat);
    start.addAll(cont);
    return start;
  }

  /**
   * Returns the current set of notes in this model that start at the given beat.
   *
   * @return the notes as an ArrayList
   */
  public List<Note> getStartingNotesAtBeat(int beat) {
    return this.model.noteListStartAt(beat);
  }

  /**
   * Returns the actual IMusicEditorModel associated with this ViewModel.
   *
   * @return the model
   */
  public IMusicModel getModel() {
    return this.model;
  }
}
