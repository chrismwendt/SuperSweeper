package com.cs408.supersweeper;

import java.awt.Point;
import java.util.Random;

import com.cs408.supersweeper.GridUnit.State;

public class GameState
{

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
   
   public void exposeAll()
   {
      for (int x = 0; x < _grid.length; x++)
      {
         for (int y = 0; y < _grid[0].length; y++)
         {
            _grid[x][y].setState(State.CHECKED);
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
            _grid[x][y] = new GridUnit(new Point(x, y));
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
      
      //populate gridunit nearby mines
      for (int i = 0; i < gridWidth; i++)
      {
         for (int j = 0; j < gridHeight; j++)
         {
            _grid[i][j].setNearbyMines(this.countNumberOfMines(_grid[i][j]));
         }
      }
   }
   
   private int countNumberOfMines(GridUnit unit)
   {
      int numMines = 0;
      int x = (int) unit.getCoordinate().getX();
      int y = (int) unit.getCoordinate().getY();
      
      if(unit.hasMine())
         return -1;
      
      for (int X = -1; X < 2; X++)
      {
         for (int Y = -1; Y < 2; Y++)
         {
            if(X==0 && Y==0)
               continue;
            try
            {
               if (_grid[x + X][y + Y].hasMine())
                  numMines++;
               unit.addAdjacenctUnit(_grid[x + X][y + Y]);
            }
            catch (Exception e)
            {
               // ignore dis bish
               // I am ripe for bugs
            }
         }
      }
      return numMines;
   }
   
   public void exposeNumber(GridUnit unit)
   {
      unit.setState(State.CHECKED);
      if(unit.getNearbyMineCount() > 0)
         return;
         

      for(GridUnit u : unit.getAdjacentUnits())
      {
         
         if(u.getState() != GridUnit.State.CHECKED && u.getState() != GridUnit.State.FLAGGED)
            exposeNumber(u);
         
      }
   }


   /** Getters */
   public int getFlagCount() {
	   return this._numOfFlags;
   }
   
   public GridUnit[][] getGrid() {
      return this._grid;
   }
   public GridUnit getGridUnit(int x, int y)
   {
      return this._grid[x][y];
   }
   
   /** Setters */
   public void setFlagCount(int flagCount) {
      this._numOfFlags = flagCount;
   }
}
