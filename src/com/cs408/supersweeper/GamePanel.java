package com.cs408.supersweeper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.cs408.supersweeper.GridUnit.State;

public class GamePanel extends JPanel implements MouseListener
{

   private static final long serialVersionUID = 1L;
   private GameState _gs;
   private Properties _prop = new Properties();
   private int gridHeight;
   private int gridWidth;
   private GridUnit tempUnit = new GridUnit(new Point(0, 0));
   private GridUnit _pressedUnit;
   private State _pressedUnitState;
   private boolean _firstClick = true;
   
   //internal panels
   private JPanel gridPanel = new JPanel();
   private JPanel labelPanel = new JPanel();
   private JPanel powerUpPanel = new JPanel();
   private JLabel statusLabel = new JLabel();


   /** Constructor */
   public GamePanel(String propFileName) throws IOException
   {
      //set up the gamePanel's internal panel structure
      this.setLayout(new BorderLayout());
      this.add(labelPanel, BorderLayout.NORTH);
      labelPanel.add(statusLabel);
 
      this.add(gridPanel, BorderLayout.CENTER);
      this.add(powerUpPanel, BorderLayout.SOUTH);
      
      // Fetch the specified properties file (throws ioexception if its not
      // there)
      this._prop.load(this.getClass().getResourceAsStream("/" + propFileName));
      validate(_prop);

      // Make a new GameState from the specified properties file
      _gs = new GameState(Double.parseDouble(_prop.getProperty("time")),
            Integer.parseInt(_prop.getProperty("numMines")),
            Integer.parseInt(_prop.getProperty("gridHeight")),
            Integer.parseInt(_prop.getProperty("gridWidth")),
            Integer.parseInt(_prop.getProperty("gridNumSides")));

      //add mouse listeners for the grid
      this.addMouseListener(this);
      this.gridWidth = Integer.parseInt(_prop.getProperty("gridWidth"));
      this.gridHeight = Integer.parseInt(_prop.getProperty("gridHeight"));
      
      int w = _gs.getGridUnit(0, 0).getBitmap().getWidth();
      int h = _gs.getGridUnit(0, 0).getBitmap().getHeight();
      System.out.println(_gs.gridWidth * w + "   " + _gs.gridHeight * h);
      statusLabel.setText("Flags: 0");

      // TODO: Get rid of hard coded height
      setPreferredSize(new Dimension(_gs.gridWidth * w, _gs.gridHeight * h + 40 + labelPanel.getHeight()  + powerUpPanel.getHeight()));
           
      updateStatusLabel();
   }

private void validate(Properties _prop)
   {
      // this function makes sure the properties file specified is valid
      double time = Double.parseDouble(_prop.getProperty("time"));
      int numMines = Integer.parseInt(_prop.getProperty("numMines"));
      int gridHeight = Integer.parseInt(_prop.getProperty("gridHeight"));
      int gridWidth = Integer.parseInt(_prop.getProperty("gridWidth"));
      int gridNumSides = Integer.parseInt(_prop.getProperty("gridNumSides"));

      // Throw error if either gridHeight or gridWidth <= 0
      if (gridWidth <= 0)
      {
         System.err
               .println("Invalid level properties file. GridWidth must be greater than zero.");
         System.exit(-1);
      }
      else if (gridHeight <= 0)
      {
         System.err
               .println("Invalid level properties file. GridHeight must be greater than zero.");
         System.exit(-1);
      }

      // Throw error if there's more bombs than grid units
      if (numMines >= gridHeight * gridWidth)
      {
         System.err
               .println("Invalid level properties file. Number of mines exceeds size of grid.");
         System.exit(-1);
      }

      // Throw error if gridNumSides is not 3,4, or 6 (for now...)
      if (gridNumSides != 3 && gridNumSides != 4 && gridNumSides != 6)
      {
         System.err
               .println("Invalid level properties file. Invalid gridNumSides defined, must be 3, 4, or 6.");
         System.exit(-1);
      }
   }

	private void updateStatusLabel() {
		statusLabel.setText("Flags: "+_gs.getFlagCount());
	}

	@Override
   public void paint(Graphics g)
   {
	   super.paint(g);
      GridUnit[][] grid = _gs.getGrid();
      BufferedImage unit = new BufferedImage(
            tempUnit.getBitmap().getWidth(), tempUnit.getBitmap()
                  .getHeight(), BufferedImage.TYPE_INT_ARGB);
      Graphics unit_graphics = unit.getGraphics();
      for (int x = 0; x < grid.length; x++)
      {
         for (int y = 0; y < grid[0].length; y++)
         {
            unit_graphics.setColor(Color.WHITE);
            unit_graphics.fillRect(0, 0, unit.getWidth(), unit.getHeight());
            //grid[x][y].draw(unit_graphics);
            unit_graphics.drawImage(grid[x][y].getBitmap(), 0, 0, null);
            g.drawImage(unit, x * unit.getWidth(), y * unit.getHeight() + labelPanel.getHeight(), null);
         }
      }
   }

   /** Listeners */
	@Override
   public void mouseClicked(MouseEvent e)
   {

   }

   @Override
   public void mouseEntered(MouseEvent e)
   {
      
   }

   @Override
   public void mouseExited(MouseEvent e)
   {

   }
   
   @Override
   public void mousePressed(MouseEvent e)
   {

      int x = (e.getX()) / (tempUnit.getBitmap().getWidth());
      int y = (e.getY() - this.labelPanel.getHeight()) / (tempUnit.getBitmap().getHeight());

      if (x >= this.gridWidth || y >= this.gridHeight || x < 0 || y < 0)
         return;
      GridUnit gridUnit = _gs.getGridUnit(x, y);
      
      this._pressedUnitState = gridUnit.getState();
           
      if(gridUnit.getState() != State.CHECKED)
      {
         this._pressedUnit = gridUnit;
         gridUnit.setState(State.PRESSED);
         this.paint(this.getGraphics());
      }

   }
   
   @Override
   public void mouseReleased(MouseEvent e)
   {
      int x = (e.getX()) / (tempUnit.getBitmap().getWidth());
      int y = (e.getY() - this.labelPanel.getHeight()) / (tempUnit.getBitmap().getHeight());

      if (x >= this.gridWidth || y >= this.gridHeight || x < 0 || y < 0)
      {
         this._pressedUnit.setState(this._pressedUnitState);
         this.paint(this.getGraphics());
         return;
      }
      
      GridUnit gridUnit = _gs.getGridUnit(x, y);
      boolean isMine = gridUnit.hasMine();
      
      if(!this._pressedUnit.getCoordinate().equals(gridUnit.getCoordinate()))
      {
         System.out.println("HERE");
         this._pressedUnit.setState(_pressedUnitState);
         this.paint(this.getGraphics());
         return;
      }


      if(_firstClick && !SwingUtilities.isRightMouseButton(e))
      {
         _gs.populateMines(x, y);
         _firstClick = false;
      }
      
      if (SwingUtilities.isRightMouseButton(e))
      {
         System.out.println("RIGHT CLICK");
         if(_pressedUnitState == GridUnit.State.UNCHECKED)
         {
            // flag
            System.out.println(gridUnit.getState());
            gridUnit.setState(State.FLAGGED);
            _gs.setFlagCount(_gs.getFlagCount() + 1);
         }
         else if(_pressedUnitState == GridUnit.State.FLAGGED) {
            gridUnit.setState(State.UNCHECKED);
            _gs.setFlagCount(_gs.getFlagCount() - 1);
         }
      }
      else if(_pressedUnitState == GridUnit.State.FLAGGED)
         gridUnit.setState(State.FLAGGED);
      else if (isMine == true)
      {
         // mine go boom
         _gs.exposeAll();
      }
      else if (isMine == false)
      {
            _gs.exposeNumber(gridUnit);
      }

      this.paint(this.getGraphics());
      updateStatusLabel();

   }
}
