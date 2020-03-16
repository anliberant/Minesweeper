package sweeper;

import java.awt.*;

public class Flag {
    private Matrix flagMap;
    private int totalFlagged;
    private int totalClosed;

    void start() {
        flagMap = new Matrix(Box.CLOSED);
        totalFlagged = 0;
        totalClosed = Ranges.getSquare();
    }

    Box get(Coord coord) {
        return flagMap.get(coord);
    }

    void setOpenedToBox(Coord coord) {
        flagMap.set(coord, Box.OPENED);
        totalClosed--;
    }

    void setFlaggedToBox(Coord coord) {
        flagMap.set(coord, Box.FLAGED);
        totalFlagged++;
    }

    void toogleFlaggedToBox(Coord coord) {
        switch (flagMap.get(coord)) {
            case FLAGED:
                setClosedToBox(coord);
                break;
            case CLOSED:
                setFlaggedToBox(coord);
                break;
        }
    }

    private void setClosedToBox(Coord coord) {
        flagMap.set(coord, Box.CLOSED);
        totalFlagged--;
    }

    int getTotalFlagged() {
        return totalFlagged;
    }

    int getTotalClosed() {
        return totalClosed;
    }

    public void setFlaggedToLastClosedBoxes() {
        for (Coord coord : Ranges.getAllCoords()) {
            if (Box.CLOSED == flagMap.get(coord))
                setFlaggedToBox(coord);
        }
    }

    void setBombedToBox(Coord coord) {
        flagMap.set(coord, Box.BOMBED);
    }

    public void setOpenedToClosedBox(Coord coord) {
        if (Box.CLOSED == flagMap.get(coord))
            flagMap.set(coord, Box.OPENED);
    }

    public void setNoBombToFlaggedBox(Coord coord) {
        if (Box.FLAGED == flagMap.get(coord)) {
            flagMap.set(coord, Box.NOBOMB);
        }
    }

    int getCountOfFlaggedBoxesAround(Coord coord) {
        int count = 0;
        for (Coord around : Ranges.getCoordsAround(coord))
            if (Box.FLAGED == flagMap.get(around))
                count++;

        return count;
    }
}
