package cs3500.music.util;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * A helper to read music data and construct a music composition from it.
 */
public class MusicReader {
  /**
   * A factory for producing new music compositions, given a source of music and a
   * builder for constructing compositions.
   *
   * @param readable The source of data for the music composition
   * @param piece    A builder for helping to construct a new composition
   * @param <T>      The main model interface type describing music compositions
   * @return Returns the main model interface type with the information from the text file.
   */
  public static <T> T parseFile(Readable readable, CompositionBuilder<T> piece) {
    Scanner scanner = new Scanner(readable);
    while (scanner.hasNext()) {
      String lineType = scanner.next();
      switch (lineType) {
        case "tempo":
          try {
            piece.setTempo(scanner.nextInt());
          } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Malformed tempo line: " + scanner.nextLine());
          }
          break;
        case "note":
          try {
            int startBeat = scanner.nextInt();
            int endBeat = scanner.nextInt();
            int instrument = scanner.nextInt();
            int pitch = scanner.nextInt();
            int volume = scanner.nextInt();
            piece.addNote(startBeat, endBeat, instrument, pitch, volume);
          } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Malformed note line: " + scanner.nextLine());
          }
          break;
        case "repeat" :
          try {
            int startBeat = scanner.nextInt();
            int endBeat = scanner.nextInt();
            piece.addRepeat(startBeat, endBeat);
          }
          catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Malformed repeat line: " + scanner.nextLine());
          }
          break;
        case "ending" :
          try {
            int startBeat = scanner.nextInt();
            int endBeat = scanner.nextInt();
            piece.addEnding(startBeat, endBeat);
          }
          catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Malformed ending line: " + scanner.nextLine());
          }
          break;
        default:
          throw new IllegalArgumentException("Bad line type: " + lineType);
      }
    }
    return piece.build();
  }
}
