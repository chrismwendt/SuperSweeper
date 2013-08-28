package com.cs408.supersweeper;

import java.awt.Point;
import java.awt.image.BufferedImage;

public class GridUnit {

	public enum State {CHECKED, UNCHECKED, FLAGGED;}

	
	private boolean _isMine = false;
	private int _nearbyMines = 0;
	private State _state = State.UNCHECKED;
	private BufferedImage _bitmap; 
	private Point _coordinate;
	private int _numSides;
	
	/** Constructor */
	public GridUnit(int numSides, Point coordinate) {
		this._numSides = numSides;
		this._coordinate = coordinate;
		//_bitmap = (getbitmap from numSides)
	}
	
	public void setBomb() {
		this._isMine = true;
	}
}
