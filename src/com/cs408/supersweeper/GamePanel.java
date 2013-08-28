package com.cs408.supersweeper;

import java.io.IOException;
import java.util.Properties;

import javax.swing.JPanel;

public class GamePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private GameState _gs;
	private Properties _prop = new Properties();
	
	/** Constructor */
	public GamePanel(String propFileName) throws IOException {
		//Fetch the specified properties file (throws ioexception if its not there)
		this._prop.load(getClass().getClassLoader().getResourceAsStream("level_properties/" + propFileName));
		validate(_prop);
		
		//Make a new GameState from the specified properties file
		_gs = new GameState(
				Double.parseDouble(_prop.getProperty("time")),
				Integer.parseInt(_prop.getProperty("numMines")),
				Integer.parseInt(_prop.getProperty("gridHeight")),
				Integer.parseInt(_prop.getProperty("gridWidth")),
				Integer.parseInt(_prop.getProperty("gridNumSides"))
				);
		
		//Draw the initial board
		_gs.drawState();
	}
 	
	
	private void validate(Properties _prop) {
		//this function makes sure the properties file specified is valid
		double time = Double.parseDouble(_prop.getProperty("time"));
		int numMines = Integer.parseInt(_prop.getProperty("numMines"));
		int gridHeight = Integer.parseInt(_prop.getProperty("gridHeight"));
		int gridWidth = Integer.parseInt(_prop.getProperty("gridWidth"));
		int gridNumSides = Integer.parseInt(_prop.getProperty("gridNumSides"));
		
		//Throw error if either gridHeight or gridWidth <= 0
		if(gridWidth <=0) {
			System.err.println("Invalid level properties file. GridWidth must be greater than zero.");
			System.exit(-1);
		}
		else if (gridHeight <= 0) {
			System.err.println("Invalid level properties file. GridHeight must be greater than zero.");
			System.exit(-1);
		}
		
		//Throw error if there's more bombs than grid units
		if(numMines > gridHeight*gridWidth) {
			System.err.println("Invalid level properties file. Number of mines exceeds size of grid.");
			System.exit(-1);
		}
		
		//Throw error if gridNumSides is not 3,4, or 6 (for now...)
		if(gridNumSides != 3 && gridNumSides != 4 && gridNumSides != 6) {
			System.err.println("Invalid level properties file. Invalid gridNumSides defined, must be 3, 4, or 6.");
			System.exit(-1);
		}
	}
	
	/** Listeners */
}
