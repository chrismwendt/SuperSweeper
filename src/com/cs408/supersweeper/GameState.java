package com.cs408.supersweeper;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Random;

public class GameState
{

   /** Global Variables */
   private GridUnit[][] _grid;
   private double _time;
   private int _minesFound = 0;
   private int _numMines;
   private int _numOfFlags = 0;
   private double _score = 0;
   private boolean _isTimed = true;
   public int gridWidth;
   public int gridHeight;
   private int _gridNumSides;

   /** Constructors */
   public GameState(double time, int numMines, int gridWidth, int gridHeight,
         int gridNumSides)
   {
      this._time = time;
      this._numMines = numMines;
      this.gridHeight = gridHeight;
      this.gridWidth = gridWidth;
      this._gridNumSides = gridNumSides;
      if (time == 0)
      {
         _isTimed = false;
      }
      this.resetGrid();

   }

   // TODO: so far only says if has mine or not. needs to return state.
   public boolean getState(int x, int y)
   {
      return _grid[x][y].hasMine();
   }
   
   public GridUnit getGridUnit(int x, int y)
   {
      return this._grid[x][y];
   }
   
   public void exposeAllMines()
   {
      for (int x = 0; x < _grid.length; x++)
      {
         for (int y = 0; y < _grid[0].length; y++)
         {
            if(_grid[x][y].hasMine())
               _grid[x][y].setMine();
         }
      }      
   }

   public void resetGrid()
   {
      this._grid = new GridUnit[gridWidth][gridHeight];

      // populate grid with blank tiles
      for (int x = 0; x < gridWidth; x++)
      {
         for (int y = 0; y < gridHeight; y++)
         {
            _grid[x][y] = new GridUnit(_gridNumSides, new Point(x, y));
         }
      }
   }
   
   public void populateMines(int x, int y)
   {
      // populate grid with bombs!
      // TODO populate bombs after user first clicks
      Random r = new Random();
      for (int i = 0; i < _numMines; i++)
      {
         int randX = r.nextInt(gridWidth);
         int randY = r.nextInt(gridHeight);
         
         if(randX == x && randY == y)
         {
            i--;
            continue;
         }
         
         GridUnit tmp = _grid[randX][randY];
         if (!tmp.hasMine())
            tmp.setHasMine(true);
         else
            i--;
      }
   }

   public void drawState(Graphics g)
   {
      BufferedImage unit = new BufferedImage(
            _grid[0][0].getBitmap().getWidth(), _grid[0][0].getBitmap()
                  .getHeight(), BufferedImage.TYPE_INT_ARGB);
      Graphics unit_graphics = unit.getGraphics();
      for (int x = 0; x < _grid.length; x++)
      {
         for (int y = 0; y < _grid[0].length; y++)
         {
            unit_graphics.setColor(Color.WHITE);
            unit_graphics.fillRect(0, 0, unit.getWidth(), unit.getHeight());
            _grid[x][y].draw(unit_graphics);
            g.drawImage(unit, x * unit.getWidth(), y * unit.getHeight(), null);
         }
      }
   }
}
