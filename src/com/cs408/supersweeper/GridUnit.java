package com.cs408.supersweeper;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class GridUnit
{

   public enum State
   {
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
	   images.put("normal", Utility.imageFromFilename("images/grid_unit.png"));
	   images.put("empty", Utility.imageFromFilename("images/grid_unit_empty.png"));
	   images.put("hover", Utility.imageFromFilename("images/grid_unit_hover.png"));
	   images.put("press", Utility.imageFromFilename("images/grid_unit_click.png"));
	   images.put("mine", Utility.imageFromFilename("images/grid_unit_mine.png"));
	   images.put("flag", Utility.imageFromFilename("images/grid_unit_flag.png"));
	   for (int i = 1; i <= 8; i++) {
		   images.put(String.valueOf(i), Utility.imageFromFilename("images/grid_unit_"+i+".png"));
	   }
   }

   /** Constructor */
   public GridUnit(int numSides, Point coordinate)
   {
      this._numSides = numSides;
      this._coordinate = coordinate;
      // TODO _bitmap = (getbitmap from numSides)
   }

   public void draw(Graphics g)
   {
	   BufferedImage image = null;
	   
	   switch (_state) {
	   case UNCHECKED:
		   image = images.get("normal");
	       break;
	   case CHECKED:
		   // TODO replace grid_unit_empty with grid_unit_0
		   if (_isMine) {
			   image = images.get("mine");
		   } else if (adjacentMineCount() > 0) {
			   image = images.get(String.valueOf(adjacentMineCount()));
		   } else {
			   image = images.get("empty");
		   }
		   break;
	   case FLAGGED:
		   image = images.get("flag");
		   break;
	   case PRESSED:
		   image = images.get("press");
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
   public boolean hasMine()
   {
      return this._isMine;
   }

   public int getNearbyMineCount()
   {
      return this._nearbyMines;
   }

   public State getState()
   {
      return this._state;
   }

   public Point getCoordinate()
   {
      return this._coordinate;
   }
   
   public ArrayList<GridUnit> getAdjacentUnits()
   {
      return this._adjacentUnits;
   }

   /** Setters */
   public void setState(State newState)
   {
      this._state = newState;
//      switch (newState)
//      {
//      default:
//      case UNCHECKED:
//         // TODO: Change bitmap
//         break;
//      case CHECKED:
//         // TODO: change bitmap
//         break;
//      case FLAGGED:
//         // TODO: change bitmap
//         break;
//      }
   }
   
   public void setNearbyMines(int numMines)
   {
      this._nearbyMines = numMines;
   }

   public void setCoordinate(Point newLocation)
   {
      this._coordinate = newLocation;
   }

   public void setHasMine(boolean hasMine)
   {
      this._isMine = hasMine;
   }
   
   public void toggleFlagged()
   {
      if (_state == State.UNCHECKED || _state == State.PRESSED) {
    	  _state = State.FLAGGED;
      } else if (_state == State.FLAGGED || _state == State.PRESSED){
    	  _state = State.UNCHECKED;
      }
   }


   /** Helpers **/
   public void addAdjacenctUnit(GridUnit unit)
   {
      this._adjacentUnits.add(unit);
   }
}
