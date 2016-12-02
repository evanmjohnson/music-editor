package cs3500.music.controller;

import cs3500.music.model.IMusicModel;
import cs3500.music.provider.IMusicView;
import cs3500.music.provider.ViewFactory;
import cs3500.music.provider.ViewModel;

/**
 * Controller for the provider views.
 */
public class ProviderController implements IMusicController {

  @Override
  public void start(IMusicModel model, String[] args) {
    ModelAdapter adapter = new ModelAdapter(model);
    ViewModel viewModel = new ViewModel(adapter);
    IMusicView view = ViewFactory.build(args[0]);
    if (args[0].equals("visual") || args[0].equals("composite")) {

    }
    try {
      ViewFactory.build(args[0]).renderModel(viewModel);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}