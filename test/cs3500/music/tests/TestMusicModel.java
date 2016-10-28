package cs3500.music.tests;

import org.junit.Test;

import cs3500.music.model.*;

import static org.junit.Assert.assertEquals;

/**
 * Tests the public-facing behavoir of the music editor model.
 */
public class TestMusicModel {

  IMusicModel model = new MusicModel();
  Note middleC = new Note(PitchType.C, 0, 2, 4);
  Note a440 = new Note(PitchType.A, 3, 2, 4);
  Note e5 = new Note(PitchType.E,  3, 1, 5);

  @Test
  public void testFirstRow() {
    model.add(middleC);
    model.add(e5);
    assertEquals("", model.getState());
  }

}
