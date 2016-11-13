package cs3500.music.controller;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.MusicViewModel;
import cs3500.music.model.Note;
import cs3500.music.view.IMusicGUIView;
import cs3500.music.view.IMusicView;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the controller for the music editor. Implements the IMusicController interface.
 */
public class MusicController implements IMusicController {
  private IMusicModel model;
  private IMusicView view;

  @Override
  public void start(IMusicModel model, String[] args) {
    // if we want a gui view, delegate to the gui controller
    if (args[0].equals("visual")) {
      GUIController gui = new GUIController();
      gui.start(model, args);
    }
    this.model = model;
    this.view = MusicCreator.create(args);
    MusicViewModel viewModel = new MusicViewModel(model);
    view.create(viewModel);
    view.makeVisible();
  }
}