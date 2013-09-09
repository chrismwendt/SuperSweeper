package com.cs408.supersweeper;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class GridUnit {
    public enum State {
        CHECKED, UNCHECKED, FLAGGED, PRESSED;
    }

    private boolean _isMine = false;
    private int _nearbyMines = 0;
    public State _state = State.UNCHECKED;
    private Point _coordinate;
    private int _numSides;
    private ArrayList<GridUnit> _adjacentUnits = new ArrayList<GridUnit>();
    // TODO width and height should not be hardcoded, maybe set to the size of one of the images
    public int width = 16, height = 16;
    private static HashMap<String, BufferedImage> images = new HashMap<String, BufferedImage>();

    static {
        GridUnit.images.put("normal", Utility.imageFromFilename("images/grid_unit.png"));
        GridUnit.images.put("empty", Utility.imageFromFilename("images/grid_unit_empty.png"));
        GridUnit.images.put("hover", Utility.imageFromFilename("images/grid_unit_hover.png"));
        GridUnit.images.put("press", Utility.imageFromFilename("images/grid_unit_click.png"));
        GridUnit.images.put("mine", Utility.imageFromFilename("images/grid_unit_mine.png"));
        GridUnit.images.put("flag", Utility.imageFromFilename("images/grid_unit_flag.png"));
        for (int i = 1; i <= 8; i++) {
            GridUnit.images.put(String.valueOf(i), Utility.imageFromFilename("images/grid_unit_" + i + ".png"));
        }
    }

    /** Constructor */
    public GridUnit(int numSides, Point coordinate) {
        _numSides = numSides;
        _coordinate = coordinate;
        // TODO _bitmap = (getbitmap from numSides)
    }

    public void draw(Graphics g) {
        BufferedImage image = null;

        switch (_state) {
        case UNCHECKED:
            image = GridUnit.images.get("normal");
            break;
        case CHECKED:
            // TODO replace grid_unit_empty with grid_unit_0
            if (_isMine) {
                image = GridUnit.images.get("mine");
            } else if (adjacentMineCount() > 0) {
                image = GridUnit.images.get(String.valueOf(adjacentMineCount()));
            } else {
                image = GridUnit.images.get("empty");
            }
            break;
        case FLAGGED:
            image = GridUnit.images.get("flag");
            break;
        case PRESSED:
            image = GridUnit.images.get("press");
            break;
        }

        g.drawImage(image, 0, 0, null);
    }

    public int adjacentMineCount() {
        int count = 0;
        for (GridUnit unit : _adjacentUnits) {
            if (unit.hasMine()) {
                count++;
            }
        }
        return count;
    }

    /** Getters */
    public boolean hasMine() {
        return _isMine;
    }

    public int getNearbyMineCount() {
        return _nearbyMines;
    }

    public State getState() {
        return _state;
    }

    public Point getCoordinate() {
        return _coordinate;
    }

    public ArrayList<GridUnit> getAdjacentUnits() {
        return _adjacentUnits;
    }

    /** Setters */
    public void setState(State newState) {
        _state = newState;
    }

    public void setNearbyMines(int numMines) {
        _nearbyMines = numMines;
    }

    public void setCoordinate(Point newLocation) {
        _coordinate = newLocation;
    }

    public void setHasMine(boolean hasMine) {
        _isMine = hasMine;
    }

    public void toggleFlagged() {
        if (_state == State.UNCHECKED || _state == State.PRESSED) {
            _state = State.FLAGGED;
        } else if (_state == State.FLAGGED || _state == State.PRESSED) {
            _state = State.UNCHECKED;
        }
    }

    /** Helpers **/
    public void addAdjacenctUnit(GridUnit unit) {
        _adjacentUnits.add(unit);
    }
}
