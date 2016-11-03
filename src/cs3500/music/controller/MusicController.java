package cs3500.music.controller;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.MusicViewModel;
import cs3500.music.view.IMusicView;

/**
 * Represents the controller for the music editor. Implements the IMusicController interface.
 */
public class MusicController implements IMusicController {
  private IMusicView view;
  private IMusicModel model;

  @Override
  public void start(IMusicModel model, String viewType) {
    this.view = MusicCreator.create(viewType);
    this.model = model;
    MusicViewModel viewModel = new MusicViewModel(model);
    view.draw(viewModel);
  }

}
