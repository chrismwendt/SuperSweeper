package com.cs408.supersweeper;

import java.util.ArrayList;

import javax.swing.JPanel;

public class GameState {


	/** Global Variables */
	private ArrayList<GridUnit> _grid = new ArrayList<GridUnit>();
	private double _time;
	private int _minesFound;
	private int _minesTotal;
	private int _numOfFlags;
	private double _score;
	private boolean _isTimed;
	
	/** Constructors */
	public GameState(JPanel gamePanel) {
		
	}
}
