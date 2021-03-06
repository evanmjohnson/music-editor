package cs3500.music.util;

import cs3500.music.model.MusicModel;
import cs3500.music.model.IMusicModel;
import cs3500.music.model.PitchType;
import cs3500.music.model.Note;
import cs3500.music.model.Repeat;
import cs3500.music.view.IMusicGUIView;

/**
 * Represents a MusicBuilder. Implements the CompositionBuilder interface paramaterized over
 * the {@link IMusicModel} class.
 */
public class MusicBuilder implements CompositionBuilder<IMusicModel> {
  private MusicModel model = new MusicModel();

  @Override
  public IMusicModel build() {
    return this.model;
  }

  @Override
  public CompositionBuilder<IMusicModel> setTempo(int tempo) {
    this.model.setTempo(tempo);
    return this;
  }

  @Override
  public CompositionBuilder<IMusicModel> addNote(int start, int end, int instrument,
                                                 int pitch, int volume) {
    PitchType pitchType = PitchType.A;
    for (PitchType type : PitchType.values()) {
      if (type.getToneOrder() == pitch % 12) {
        pitchType = type;
      }
    }
    if (start == end) {
      throw new IllegalArgumentException("Duration cannot be zero.");
    }
    if (pitch > 127 || pitch < 0) {
      throw new IllegalArgumentException("Pitch must be between 0 and 127.");
    }
    if (pitch < 12) {
      Note toAdd = new Note(pitchType, start, end - start, (pitch / 12), instrument, volume);
      model.add(toAdd);
    } else {
      Note toAdd = new Note(pitchType, start, end - start, (pitch / 12) - 1, instrument, volume);
      model.add(toAdd);
    }
    return this;
  }

  @Override
  public CompositionBuilder<IMusicModel> addRepeat(int start, int end) {
    Repeat repeat = new Repeat(start, end);
    model.addRepeat(repeat);
    return this;
  }

  @Override
  public CompositionBuilder<IMusicModel> addEnding(int start, int end) {
    Repeat ending = new Repeat(start, end);
    model.addEnding(ending);
    return this;
  }
}
