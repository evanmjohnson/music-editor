package cs3500.music.controller;

import cs3500.music.provider.IMusicView;
import cs3500.music.provider.ViewFactory;
import cs3500.music.provider.ViewModel;

/**
 * Created by evan on 11/30/16.
 */
public class ViewAdapter implements IMusicView {
  private ViewModel viewModel;
  private String type;

  public ViewAdapter(IMusicEditorModel model, String type) {
    this.viewModel = new ViewModel(model);
    this.type = type;
  }

  @Override
  public void renderModel(ViewModel viewmodel) throws InterruptedException {
    ViewFactory.build(type).renderModel(viewModel);
  }
}
