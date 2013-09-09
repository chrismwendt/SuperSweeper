package com.cs408.supersweeper;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class GridUnit
{

   public enum State
   {
      CHECKED, UNCHECKED, FLAGGED, PRESSED;
   }

   private boolean _isMine = false;
   private int _nearbyMines = 0;
   private State _state = State.UNCHECKED;
   private BufferedImage bitmap;
   private Point _coordinate;
   private ArrayList<GridUnit> _adjacentUnits = new ArrayList<GridUnit>();

   /** Constructor */
   public GridUnit(Point coordinate)
   {
      this._coordinate = coordinate;
      this.setState(State.UNCHECKED);
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
   
   public BufferedImage getBitmap()
   {
      return this.bitmap;
   }
   
   public ArrayList<GridUnit> getAdjacentUnits()
   {
      return this._adjacentUnits;
   }

   /** Setters */
   public void setState(State newState)
   {
      /*
       * Sets the bitmap according to the given state
       */
      this._state = newState;
      switch (newState)
      {
      default:
      case UNCHECKED:
         this.setImageBitmap("images/grid_unit.png");
         break;
      case CHECKED:
         if(this._nearbyMines == 0)
            this.setImageBitmap("images/grid_unit_empty.png");
         else if(this._isMine)
            this.setImageBitmap("images/grid_unit_mine.png");
         else
            this.setImageBitmap("images/grid_unit_" + this._nearbyMines + ".png");
         break;
      case PRESSED:
         this.setImageBitmap("images/grid_unit_click.png");
         break;
      case FLAGGED:
         this.setImageBitmap("images/grid_unit_flag.png");
         break;
      }
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
   
   public void addAdjacenctUnit(GridUnit unit)
   {
      this._adjacentUnits.add(unit);
      
   }


   /** Helpers **/
   private void setImageBitmap(String fileName)
   {
      try
      {
         this.bitmap = ImageIO.read(new File(fileName));
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
   }

}
