package com.cs408.supersweeper;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class GameState {


	/** Global Variables */
	private GridUnit[][] _grid;
	private double _time;
	private int _minesFound = 0;
	private int _numMines;
	private int _numOfFlags = 0;
	private double _score = 0;
	private boolean _isTimed = true;
	
	/** Constructors */
	public GameState(double time, int numMines, int gridWidth, int gridHeight, int gridNumSides) {
		this._time = time;
		this._numMines = numMines;
		if(time == 0) { _isTimed = false; }
		this._grid = new GridUnit[gridWidth][gridHeight];
		
		
		//populate grid with blank tiles
		for(int x = 0; x < gridWidth; x ++) {
			for(int y = 0; y < gridHeight; y++) {
				_grid[x][y] = new GridUnit(gridNumSides, new Point(x,y));
			}
		}
		
		
		//populate grid with bombs!
		Random r = new Random();
		for(int i = 0; i < numMines; i ++) {
			GridUnit tmp = _grid[r.nextInt(gridWidth)][r.nextInt(gridHeight)];
			if(!tmp.hasMine())
				tmp.setHasMine(true);
			else
				i--;
		}
		
		
	}
	
	public void drawState() {
		for(int x = 0; x < _grid.length; x++) {
			for(int y = 0; y < _grid[0].length; y++) {
				_grid[x][y].draw();
			}
		}
	}
}
