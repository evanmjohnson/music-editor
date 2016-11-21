package cs3500.music.controller;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.MusicViewModel;
import cs3500.music.view.IMusicView;

/**
 * Represents the controller for the music editor. Implements the IMusicController interface.
 */
public class MusicController implements IMusicController {
  private IMusicModel model;
  private IMusicView view;
  private String type;

  MusicController() {

  }

  public MusicController(IMusicModel model, String[] args) {
    this.model = model;
    this.type = args[0];
  }

  @Override
  public void start(IMusicModel model, String[] args) {
    // if we want a gui view, delegate to the gui controller
    if (args[0].equals("visual") || args[0].equals("combined")) {
      GUIController gui = new GUIController(model, args);
      gui.start(model, args);
    } else {
      this.view = MusicCreator.create(args);
      this.model = model;
      MusicViewModel viewModel = new MusicViewModel(model);
      view.create(viewModel);
      view.makeVisible();
    }
  }
}