package sweeper;

public class Game {
    Bomb bomb;
    Flag flag;
    GameState state;

    public Game(int cols, int rows, int bombs) {
        Ranges.setSize(new Coord(cols, rows));
        bomb = new Bomb(bombs);
        flag = new Flag();
    }

    public void start() {
        bomb.start();
        flag.start();
        state = GameState.PLAYED;
    }

    public Box getBox(Coord coord) {
        if (Box.OPENED == flag.get(coord))
            return bomb.get(coord);
        else
            return flag.get(coord);
    }

    public void pressLeftButton(Coord coord) {
        if (isGameOver()) return;

        openBox(coord);
        checkWinner();
    }
    public void pressRightButton(Coord coord) {
        if (isGameOver()) return;
        flag.toogleFlaggedToBox(coord);
    }

    private void openBox(Coord coord) {
        switch (flag.get(coord)) {
            case OPENED: setOpenedToClosedBoxesAroundNumber(coord);
                break;
            case FLAGED:
                break;
            case CLOSED:
                switch (bomb.get(coord)) {
                    case ZERO:
                        openBoxesAroundZero(coord);
                        break;
                    case BOMB:
                        openBombs(coord);
                        break;
                    default:
                        flag.setOpenedToBox(coord);
                        break;
                }
        }
    }

    private void setOpenedToClosedBoxesAroundNumber(Coord coord) {
        if (Box.BOMB != bomb.get(coord))
            if (bomb.get(coord).getNumber() == flag.getCountOfFlaggedBoxesAround(coord))
                for (Coord around: Ranges.getCoordsAround(coord))
                    if (Box.CLOSED == flag.get(coord))
                        openBox(around);
    }

    private void openBoxesAroundZero(Coord coord) {
        flag.setOpenedToBox(coord);
        for (Coord around : Ranges.getCoordsAround(coord)) {
            openBox(around);
        }
    }

    private void checkWinner() {
        if (GameState.PLAYED == state)
            if (flag.getTotalClosed() == getTotalBombs()) {
                state = GameState.WINNER;
                flag.setFlaggedToLastClosedBoxes();
            }
    }

    private void openBombs(Coord bombedCoord) {
        flag.setBombedToBox(bombedCoord);
        for (Coord coord: Ranges.getAllCoords())
            if (Box.BOMB == bomb.get(coord))
                flag.setOpenedToClosedBox(coord);
            else
                flag.setNoBombToFlaggedBox(coord);
        state = GameState.BOMBED;
    }

    private boolean isGameOver() {
        if (GameState.PLAYED != state) {
            start();
            return true;
        }
        return false;
    }

    public GameState getState() {
        return state;
    }

    public int getTotalBombs() {
        return bomb.getTotalBombs();
    }

    public int getTotalFlagged() {
        return flag.getTotalFlagged();
    }
}
