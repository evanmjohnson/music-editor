package cs3500.music.view;

import java.util.Objects;


/**
 * Created by blerner on 10/10/16.
 */
public final class Line {
  public final int x0;
  public final int y0;
  public final int x1;
  public final int y1;

  public Line(int x0, int y0, int x1, int y1) {
    this.x0 = x0;
    this.y0 = y0;
    this.x1 = x1;
    this.y1 = y1;
  }
}

//  @Override
//  public String toString() {
//    return String.format("%s--%s", this.start.toString(), this.end.toString());
//  }
//
//  @Override
//  public boolean equals(Object o) {
//    if (this == o) {
//      return true;
//    }
//    if (!(o instanceof Line)) {
//      return false;
//    }
//
//    Line line = (Line) o;
//
//    return (this.start.equals(line.start) && this.end.equals(line.end))
//            || (this.end.equals(line.start) && this.start.equals(line.end));
//  }
//
//  @Override
//  public int hashCode() {
//    return Objects.hash(this.start, this.end);
//  }
//}
