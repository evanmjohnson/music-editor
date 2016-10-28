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
  Note a1 = new Note(PitchType.A, 2, 0, 1);
  Note f4 = new Note(PitchType.F, 4, 0, 4);
  Note b4 = new Note(PitchType.B, 3, 0, 4);
  Note b3 = new Note(PitchType.B, 0, 0, 3);
  Note g4 = new Note(PitchType.G, 6, 0, 4);
  Note a4 = new Note(PitchType.A, 0, 4, 4);

  @Test
  public void testAdd() {
    model.add(middleC);
    String expected = "  C4  \n" +
        "0  X  \n" +
        "1  |  \n";
    assertEquals(expected, model.getState());
  }

  @Test
  public void testAddDuplicate() {
    model.add(middleC);
    model.add(middleC);
    String expected = "  C4  \n" +
        "0  X  \n" +
        "1  |  \n";
    assertEquals(expected, model.getState());
  }

  @Test
  public void testAddOverlap() {
    model.add(a440);
    model.add(a4);
    String expected = "  A4  \n" +
        "0  X  \n" +
        "1  |  \n" +
        "2  |  \n" +
        "3  X  \n" +
        "4  |  \n";
    assertEquals(expected, model.getState());
  }
}
