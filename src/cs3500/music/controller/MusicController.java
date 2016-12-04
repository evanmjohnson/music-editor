package cs3500.music.controller;

import cs3500.music.model.IMusicModel;
import cs3500.music.provider.IMusicView;
import cs3500.music.provider.ViewModel;

/**
 * Represents the controller for the music editor. Implements the IMusicController interface.
 */
public class MusicController implements IMusicController {
  private IMusicModel model;
  private IMusicView view;
  private String type;

  MusicController() {
    // default controller
  }

  public MusicController(IMusicModel model, String[] args) {
    this.model = model;
    this.type = args[0];
  }

  @Override
  public void start(String[] args) {
    // if we want a gui view, delegate to the gui controller
    if (args[0].equals("visual") || args[0].equals("composite")) {
      GUIController gui = new GUIController(model, args);
      gui.start(args);
    } else {
      this.view = MusicCreator.create(args);
      ModelAdapter adapter = new ModelAdapter(model);
      ViewModel viewModel = new ViewModel(adapter);
      try {
        view.renderModel(viewModel);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}