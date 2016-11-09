package cs3500.music.controller;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.MusicViewModel;
import cs3500.music.view.IMusicView;

/**
 * Represents the controller for the music editor. Implements the IMusicController interface.
 */
public class MusicController implements IMusicController {

  @Override
  public void start(IMusicModel model, String[] args) {
    IMusicView view = MusicCreator.create(args);
    MusicViewModel viewModel = new MusicViewModel(model);
    view.create(viewModel);
    view.makeVisible();
  }
}
