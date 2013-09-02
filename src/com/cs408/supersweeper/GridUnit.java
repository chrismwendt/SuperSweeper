package com.cs408.supersweeper;

import java.awt.Graphics;
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
      CHECKED, UNCHECKED, FLAGGED;
   }

   private boolean _isMine = false;
   private int _nearbyMines = 0;
   private State _state = State.UNCHECKED;
   private BufferedImage bitmap;
   private Point _coordinate;
   private int _numSides;
   private ArrayList<Point> _adjacentUnits = new ArrayList<Point>();

   /** Constructor */
   public GridUnit(int numSides, Point coordinate)
   {
      this._numSides = numSides;
      this._coordinate = coordinate;
      this.bitmap = null;
      this.setUnchecked();
      // TODO _bitmap = (getbitmap from numSides)
   }

   public void draw(Graphics g)
   {
      g.drawImage(bitmap, 0, 0, null);
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
   
   public ArrayList<Point> getAdjacentUnits()
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
   
   public void setMine()
   {
      this.setImageBitmap("images/grid_unit_mine.png");
   }
   
   public void setChecked()
   {
      this.setState(State.CHECKED);
      this.setImageBitmap("images/grid_unit_click.png");
   }
   
   public void setCheckedNoState()
   {
      this.setImageBitmap("images/grid_unit_click.png");
   }
   
   public void setEmpty()
   {
      this.setImageBitmap("images/grid_unit_empty.png");
      this.setState(State.CHECKED);
   }
   
   public void setFlagged()
   {
      this.setState(State.FLAGGED);
      this.setImageBitmap("images/grid_unit_flag.png");
   }
   
   public void setUnchecked()
   {
      this.setState(State.UNCHECKED);
      this.setImageBitmap("images/grid_unit.png");
   }
   
   public void setUncheckedNoState()
   {
      this.setImageBitmap("images/grid_unit.png");
   }
   
   public void setHovered()
   {
      this.setImageBitmap("images/grid_unit_hover.png");
   }
   
   public void setNumber(int num)
   {
      this.setImageBitmap("images/grid_unit_" + num + ".png");
      this.setState(State.CHECKED);
   }


   /** Helpers **/
   public void setImageBitmap(String fileName)
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

   public void addAdjacenctUnit(Point point)
   {
      this._adjacentUnits.add(point);
      
   }
}
