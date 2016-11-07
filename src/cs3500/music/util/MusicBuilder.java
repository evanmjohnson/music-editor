package cs3500.music.util;

import cs3500.music.model.*;

/**
 * Represents a MusicBuilder. Implements the CompositionBuilder interface paramaterized over
 * the {@link MusicViewModel} class.
 */
public class MusicBuilder implements CompositionBuilder<IMusicModel> {
  private MusicModel model = new MusicModel();

  @Override
  public MusicViewModel build() {
    return new MusicViewModel(model);
  }

  @Override
  public CompositionBuilder<IMusicModel> setTempo(int tempo) {
    this.model.setTempo(tempo);
    return this;
  }

  @Override
  public CompositionBuilder<IMusicModel> addNote(int start, int end, int instrument,
                                          int pitch, int volume) {
    model = new MusicModel();
    PitchType pitchType = PitchType.A;
    for (PitchType type : PitchType.values()) {
      if (type.getToneOrder() == pitch % 12) {
        pitchType = type;
      }
    }
    Note toAdd = new Note(pitchType, start, end - start, (pitch / 12) + 1, instrument, volume);
    model.add(toAdd);
    return this;
  }
}
