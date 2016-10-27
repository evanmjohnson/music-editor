package cs3500.music.model;

import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Represents a piece of music that can be manipulated.
 * Implements the {@link IMusicModel} interface.
 */
public class MusicModel implements IMusicModel {

  private TreeMap<Integer, TreeSet<Note>> notes;

  @Override
  public void add(Note n) {
    for (int i = n.getStartBeat(); i < n.getStartBeat() + n.getDuration(); i++) {
      notes.get(i).add(n);
    }
  }

  @Override
  public void replace(Note from, Note to) {
    this.remove(from);
    this.add(to);
  }

  @Override
  public void remove(Note n) throws IllegalArgumentException {
    for(Map.Entry<Integer,TreeSet<Note>> entry : notes.entrySet()) {
      int i = entry.getKey();
      TreeSet<Note> set = entry.getValue();
      if (set.contains(n)) {
        set.remove(n);
      }
    }
  }

  @Override
  public String getState() {
    return null;
  }

  @Override
  public void playSimultaneously(IMusicModel model2) {

  }

  @Override
  public void playConsecutively(IMusicModel model2) {

  }

  @Override
  public void combineSimultaneously(MusicModel sheet1) {

  }

  @Override
  public void combineConsecutively(MusicModel sheet1) {

  }
}