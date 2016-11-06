package cs3500.music.view;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.MusicModel;
import cs3500.music.model.MusicViewModel;

import java.io.IOException;

/**
 * Represents the view for the music editor outputted to the console.
 * Implements the {@link IMusicView} interface
 */
public class ConsoleView implements IMusicView {
  private final Appendable ap;

  public ConsoleView(Appendable ap) {
    this.ap = System.out;
  }

  @Override
  public void create(MusicViewModel model) {
    try {
      ap.append(model.getState());
    } catch (IOException e) {
      return;
    }
  }

  @Override
  public void makeVisible() {
    return;
  }
}
