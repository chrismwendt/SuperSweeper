package com.cs408.supersweeper;

import java.io.IOException;
import java.util.Properties;

import javax.swing.JPanel;

public class GamePanel extends JPanel {

	private GameState _gs;
	private Properties _prop = new Properties();
	
	public GamePanel(String propFileName) throws IOException {
		this._prop.load(getClass().getClassLoader().getResourceAsStream("level_properties/" + propFileName));
		_gs = new GameState()
	}
 	
	/** Listeners */
}
