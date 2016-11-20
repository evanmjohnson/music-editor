package cs3500.music.controller;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.MusicViewModel;
import cs3500.music.model.Note;
import cs3500.music.view.IMusicGUIView;
import cs3500.music.view.IMusicView;
import cs3500.music.view.JFrameView;
import cs3500.music.view.MidiView;

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
    if (args[0].equals("visual") || args[0].equals("combined")) {
      GUIController gui = new GUIController();
      gui.start(model, args);
    }
    else {
      this.view = MusicCreator.create(args);
      this.model = model;
      MusicViewModel viewModel = new MusicViewModel(model);
      view.create(viewModel);
      view.makeVisible();
    }
  }
}