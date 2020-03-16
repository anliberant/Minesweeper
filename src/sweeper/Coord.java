package sweeper;

import java.util.Objects;

public class Coord {
    public int x;
    public int y;

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Coord)) {
            return super.equals(o);
        }
        Coord to = (Coord) o;
        return to.x == x && to.y == y;
    }
}
