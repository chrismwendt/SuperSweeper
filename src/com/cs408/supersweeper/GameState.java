package com.cs408.supersweeper;

import java.util.Arrays;
import java.util.Collections;

public class GameState {

    /** Global Variables */
    private GridUnit[][] _grid;
    private double _time;
    private int _numMines;
    private int _numOfFlags = 0;
    private double _score = 0;
    private boolean _isTimed = true;
    public int gridWidth;
    public int gridHeight;
    private int _gridNumSides;

    /** Constructors */
    public GameState(double time, int numMines, int gridWidth, int gridHeight, int gridNumSides) {
        _time = time;
        _numMines = numMines;
        this.gridHeight = gridHeight;
        this.gridWidth = gridWidth;
        _gridNumSides = gridNumSides;
        if (time == 0) {
            _isTimed = false;
        }

        _grid = new GridUnit[gridWidth][gridHeight];

        // initialize the grid
        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                _grid[x][y] = new GridUnit();
            }
        }

        // set adjacent grid units
        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                for (int i = x - 1; i <= x + 1; i++) {
                    for (int j = y - 1; j <= y + 1; j++) {
                        if (isValidGridUnit(i, j) && !(i - x == 0 && j - y == 0)) {
                            _grid[x][y].adjacentGridUnits.add(_grid[i][j]);
                        }
                    }
                }
            }
        }
    }

    public GridUnit[][] getGrid() {
        return _grid;
    }

    public GridUnit getGridUnit(int x, int y) {
        return _grid[x][y];
    }

    public void resetGrid() {
        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                _grid[x][y].isMined = false;
            }
        }
    }

    public boolean isValidGridUnit(int x, int y) {
        return x >= 0 && x < gridWidth && y >= 0 && y < gridHeight;
    }

    public void populateMines() {
        GridUnit[] units = Utility.flatten(_grid);
        Collections.shuffle(Arrays.asList(units));
        for (int i = 0; i < _numMines; i++) {
            units[i].isMined = true;
        }
    }

    public int getFlagCount() {
        int count = 0;
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                if (_grid[i][j].isFlagged) {
                    count++;
                }
            }
        }
        return count;
    }
}
