package cs3500.music.tests;

import cs3500.music.controller.GUIController;
import cs3500.music.model.IMusicModel;
import cs3500.music.model.MusicModel;
import cs3500.music.model.Note;
import cs3500.music.model.PitchType;
import org.junit.Test;

import java.awt.Button;
import java.awt.event.MouseEvent;

import static org.junit.Assert.assertEquals;

/**
 * Tests the composite view of the music editor.
 */
public class TestComposite {
  IMusicModel model = new MusicModel();
  Note c4 = new Note(PitchType.C, 0, 2, 4);

  @Test
  public void testRemove() {
    model.add(c4);
    String[] args = new String[1];
    args[0] = "visual";
    GUIController controller = new GUIController(model, args);
    MouseEvent e = new MouseEvent(new Button(), 0, 0, 0, 5, 10, 0, false);
    controller.check(e.getX(), e.getY(), false);
    assertEquals(0, model.getNumBeats());
  }

  @Test
  public void testAdd() {
    String[] args = new String[1];
    args[0] = "visual";
    GUIController controller = new GUIController(model, args);
    controller.addNote("C", 0, 2, 4, false);
    assertEquals(1, model.getNumBeats());
  }
}
