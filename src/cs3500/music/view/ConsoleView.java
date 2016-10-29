package cs3500.music.view;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.MusicModel;

/**
 * Represents the view for the music editor outputted to the console.
 * Implements the {@link IMusicView}
 */
public class ConsoleView implements IMusicView {

  public ConsoleView(Appendable ap) {
    this.ap = ap;

  }


  @Override
  public void show() {

  }

  @Override
  public void setMeasureLength(int length) {

  }
}
