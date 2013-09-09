package com.cs408.supersweeper;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.Properties;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GamePanel extends JPanel implements MouseListener, MouseMotionListener {
    private static final long serialVersionUID = 1L;
    private GameState _gs;
    private Properties _prop = new Properties();
    private int gridHeight;
    private int gridWidth;
    private GridUnit tempUnit = new GridUnit();
    private boolean _firstClick = true;
    private JLabel statusLabel;
    private GridUnit previouslyPressedGridUnit = null;

    /** Constructor */
    public GamePanel(String propFileName, JLabel label) {
        // Fetch the specified properties file
        try {
            _prop.load(this.getClass().getResourceAsStream("/" + propFileName));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        validate(_prop);

        // Make a new GameState from the specified properties file
        _gs = new GameState(Double.parseDouble(_prop.getProperty("time")), Integer.parseInt(_prop.getProperty("numMines")), Integer.parseInt(_prop.getProperty("gridHeight")), Integer.parseInt(_prop.getProperty("gridWidth")), Integer.parseInt(_prop.getProperty("gridNumSides")));

        addMouseListener(this);
        addMouseMotionListener(this);
        gridWidth = Integer.parseInt(_prop.getProperty("gridWidth"));
        gridHeight = Integer.parseInt(_prop.getProperty("gridHeight"));

        int w = _gs.getGridUnit(0, 0).width;
        int h = _gs.getGridUnit(0, 0).height;
        setPreferredSize(new Dimension(_gs.gridWidth * w, _gs.gridHeight * h));

        statusLabel = label;
        updateStatusLabel();
    }

    private void validate(Properties _prop) {
        // this function makes sure the properties file specified is valid
        double time = Double.parseDouble(_prop.getProperty("time"));
        int numMines = Integer.parseInt(_prop.getProperty("numMines"));
        int gridHeight = Integer.parseInt(_prop.getProperty("gridHeight"));
        int gridWidth = Integer.parseInt(_prop.getProperty("gridWidth"));
        int gridNumSides = Integer.parseInt(_prop.getProperty("gridNumSides"));

        // Throw error if either gridHeight or gridWidth <= 0
        if (gridWidth <= 0) {
            System.err.println("Invalid level properties file. GridWidth must be greater than zero.");
            System.exit(-1);
        } else if (gridHeight <= 0) {
            System.err.println("Invalid level properties file. GridHeight must be greater than zero.");
            System.exit(-1);
        }

        // Throw error if there's more bombs than grid units
        if (numMines >= gridHeight * gridWidth) {
            System.err.println("Invalid level properties file. Number of mines exceeds size of grid.");
            System.exit(-1);
        }

        // Throw error if gridNumSides is not 3,4, or 6 (for now...)
        if (gridNumSides != 3 && gridNumSides != 4 && gridNumSides != 6) {
            System.err.println("Invalid level properties file. Invalid gridNumSides defined, must be 3, 4, or 6.");
            System.exit(-1);
        }
    }

    private void updateStatusLabel() {
        statusLabel.setText("Flags: " + _gs.getFlagCount());
    }

    public void paint(Graphics g) {
        _gs.drawState(g);
    }

    public void stateUpdated() {
        repaint();
        updateStatusLabel();
    }

    private GridUnit getGridUnit(MouseEvent e) {
        int x = e.getX() / tempUnit.width;
        int y = e.getY() / tempUnit.height;

        if (x >= gridWidth || y >= gridHeight) {
            return null;
        }

        return _gs.getGridUnit(x, y);
    }

    /** Listeners */
    public void mousePressed(MouseEvent e) {
        GridUnit gridUnit = getGridUnit(e);
        if (gridUnit == null) {
            return;
        }

        if (SwingUtilities.isLeftMouseButton(e)) {
            gridUnit.checkPressed();
            previouslyPressedGridUnit = gridUnit;
        } else if (SwingUtilities.isRightMouseButton(e)) {
            gridUnit.flagPressed();
        }

        stateUpdated();
    }

    public void mouseReleased(MouseEvent e) {
        GridUnit gridUnit = getGridUnit(e);
        if (gridUnit == null) {
            return;
        }

        if (_firstClick && !SwingUtilities.isRightMouseButton(e)) {
            do {
                _gs.resetGrid();
                _gs.populateMines();
            } while (gridUnit.isMined);
            _firstClick = false;
        }

        if (SwingUtilities.isLeftMouseButton(e)) {
            gridUnit.checkReleased();
            previouslyPressedGridUnit = null;
        } else if (SwingUtilities.isRightMouseButton(e)) {
            gridUnit.flagReleased();
        }

        stateUpdated();
    }

    public void mouseDragged(MouseEvent e) {
        if (previouslyPressedGridUnit == null) {
            return;
        }

        GridUnit gridUnit = getGridUnit(e);
        if (gridUnit == null) {
            return;
        }

        if (SwingUtilities.isLeftMouseButton(e)) {
            previouslyPressedGridUnit.checkCancelled();
            gridUnit.checkPressed();
            previouslyPressedGridUnit = gridUnit;
        }

        stateUpdated();
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent arg0) {
    }
}
