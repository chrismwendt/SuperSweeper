package com.cs408.supersweeper;

import java.awt.Point;
import java.awt.image.BufferedImage;

public class GridUnit {

	public enum State {CHECKED, UNCHECKED, FLAGGED;}
	
	private boolean _isMine;
	private int _nearbyMines;
	private State _state;
	private BufferedImage _bitmap; 
	private Point _coordinate;
	
	
	
}
