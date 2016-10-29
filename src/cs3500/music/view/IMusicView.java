package cs3500.music.view;

/**
 * Represents the view interface for the music editor.
 */
public interface IMusicView {
  /**
   * Display this view.
   */
  void show();

  /**
   * Sets the measure length of this piece.
   * @param length The new measure length of this piece
   */
  void setMeasureLength(int length);
}
