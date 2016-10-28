package cs3500.music.tests;

import org.junit.Test;

import cs3500.music.model.*;

import static org.junit.Assert.assertEquals;

/**
 * Tests the public-facing behavoir of the music editor model.
 */
public class TestMusicModel {

  IMusicModel model = new MusicModel();
  IMusicModel model2 = new MusicModel();
  Note middleC = new Note(PitchType.C, 0, 2, 4);
  Note a440 = new Note(PitchType.A, 3, 2, 4);
  Note e5 = new Note(PitchType.E,  3, 1, 5);
  Note a1 = new Note(PitchType.A, 2, 1, 1);
  Note f4 = new Note(PitchType.F, 4, 2, 4);
  Note b4 = new Note(PitchType.B, 3, 1, 4);
  Note b3 = new Note(PitchType.B, 0, 4, 3);
  Note g4 = new Note(PitchType.G, 6, 1, 4);
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

  @Test
  public void testAddNotZero() {
    model.add(b4);
    String expected = "  B4  \n" +
        "0     \n" +
        "1     \n" +
        "2     \n" +
        "3  X  \n";
    assertEquals(expected, model.getState());
  }

  @Test
  public void testFirstRow() {
    model.add(a1);
    model.add(f4);
    String[] lines = model.getState().split(System.getProperty("line.separator"));
    String expected = "  A1   A#1  B1   C2   C#2  D2   D#2  E2   F2   F#2  G2   G#2  A2   " +
        "A#2  B2   C3   C#3  D3   D#3  E3   F3   F#3  G3   G#3  A3   A#3  B3   C4   C#4  D4   " +
        "D#4  E4   F4  ";
    assertEquals(expected, lines[0]);
  }

  @Test
  public void testRemove() {
    model.add(middleC);
    model.add(a4);
    model.remove(a4);
    String expected = "  C4  \n" +
        "0  X  \n" +
        "1  |  \n" +
        "2     \n" +
        "3     \n";
    assertEquals(expected, model.getState());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveNotThere() {
    model.add(middleC);
    model.remove(a4);
  }

  @Test
  public void testPlayConsecutively() {
    model.add(middleC);
    model.add(b3);
    model2.add(middleC);
    model2.add(a4);
    model.playConsecutively(model2);
    String expected = "   B3   C4   C#4  D4   D#4  E4   F4   F#4  G4   G#4  A4  \n" +
        " 0  X    X                                               \n" +
        " 1  |    |                                               \n" +
        " 2  |                                                    \n" +
        " 3  |                                                    \n" +
        " 4       X                                               \n" +
        " 5       |                                               \n" +
        " 6                                                    X  \n" +
        " 7                                                    |  \n" +
        " 8                                                    |  \n" +
        " 9                                                    |  \n";
    assertEquals(expected, model.getState());
  }

  @Test
  public void testPlayConsecutivelyFirstEmpty() {
    model2.add(middleC);
    model.playConsecutively(model2);
    String expected = "  C4  \n" +
        "0  X  \n" +
        "1  |  ";
    assertEquals(expected, model.getState());
  }

  @Test
  public void testPlayConsecutivelySecondEmpty() {
    model.add(middleC);
    model.playConsecutively(model2);
    String expected = "  C4  \n" +
        "0  X  \n" +
        "1  |  ";
    assertEquals(expected, model.getState());
  }

  @Test
  public void testPlayConsecutivelyBothEmpty() {

  }
}