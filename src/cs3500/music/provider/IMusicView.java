package cs3500.music.provider;

/**
 * This is the interface of the Music View; it represents
 * a view, a representation of an inputted model.
 */
public interface IMusicView {

  /**
   * Renders the model with respect to the current view.
   *
   * @param viewmodel the viewmodel to be given to the view so it cannot mutate the data of a model
   */
  void renderModel(ViewModel viewmodel) throws InterruptedException;


}
