package com.cs408.supersweeper;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GridUnit {

	public enum State {CHECKED, UNCHECKED, FLAGGED;}

	
	private boolean _isMine = false;
	private int _nearbyMines = 0;
	private State _state = State.UNCHECKED;
	public BufferedImage bitmap; 
	private Point _coordinate;
	private int _numSides;
	
	/** Constructor */
	public GridUnit(int numSides, Point coordinate) {
		this._numSides = numSides;
		this._coordinate = coordinate;
		this.bitmap = null;
		try {
		    this.bitmap = ImageIO.read(new File("images/grid_unit.png"));
		} catch (IOException e) {}
		//TODO _bitmap = (getbitmap from numSides)
	}
	
	public void draw(Graphics g) {
		g.drawImage(bitmap, 0, 0, null);
	}
	
	/** Getters */
	public boolean hasMine() {
		return this._isMine;
	}
	public int getNearbyMineCount() {
		return this._nearbyMines;
	}
	public State getState() {
		return this._state;
	}
	public Point getCoordinate() {
		return this._coordinate;
	}
	
	/** Setters */
	public void setHasMine(boolean hasMine) {
		this._isMine = hasMine;
	}
	public void setState (State newState) {
		this._state = newState;
		switch(newState) {
		default:
		case UNCHECKED:
			//TODO: Change bitmap
			break;
		case CHECKED:
			//TODO: change bitmap
			break;
		case FLAGGED:
			//TODO: change bitmap
			break;
		}
	}
	public void setCoordinate (Point newLocation) {
		this._coordinate = newLocation;
	}
}
