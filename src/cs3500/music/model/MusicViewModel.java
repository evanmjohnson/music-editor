package cs3500.music.model;

import java.util.List;

/**
 * Represents a read-only version of the model for the music editor.
 * Implements the IMusicModel interface.
 */
public final class MusicViewModel implements IMusicModel {
  IMusicModel model;
  IllegalArgumentException cantMutate = new IllegalArgumentException("Cannot mutate in viewmodel");

  public MusicViewModel(IMusicModel model) {
    this.model = model;
  }

  @Override
  public void add(Note n) {
    throw cantMutate;
  }

  @Override
  public void replace(Note from, Note to) {
    throw cantMutate;
  }

  @Override
  public void remove(Note n) throws IllegalArgumentException {
    throw cantMutate;
  }

  @Override
  public String getState() {
    return model.getState();
  }

  @Override
  public void playSimultaneously(IMusicModel model2) {
    throw cantMutate;
  }

  @Override
  public void playConsecutively(IMusicModel model2) {
    throw cantMutate;
  }

  @Override
  public void combineSimultaneously(MusicModel sheet1) {
    throw cantMutate;
  }

  @Override
  public void combineConsecutively(MusicModel sheet1) {
    throw cantMutate;
  }

  @Override
  public int length() {
    return model.length();
  }

  @Override
  public List<Integer> notesStartAtThisBeat(int beat) {
    return model.notesStartAtThisBeat(beat);
  }

  @Override
  public List<Integer> notesContinueAtThisBeat(int beat) {
    return model.notesContinueAtThisBeat(beat);
  }

  @Override
  public int getNumBeats() {
    return model.getNumBeats();
  }

  @Override
  public List<Note> getNoteRange() {
    return model.getNoteRange();
  }
}
