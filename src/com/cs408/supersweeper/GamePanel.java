package com.cs408.supersweeper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class GamePanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener {

    private static final long serialVersionUID = 1L;
    private GameState _gs;
    private Properties _prop = new Properties();
    private int gridHeight;
    private int gridWidth;
    private boolean _firstClick = true;
    private GridUnit previouslyPressedGridUnit = null;
    private int time = 0;


    private JPanel gridPanel = new JPanel();
    private JPanel labelPanel = new JPanel();
    private JPanel powerUpPanel = new JPanel();
    private JLabel statusLabel = new JLabel();
    private JLabel timeLabel = new JLabel();
    private Timer timeDelay;
    private Powerup missile, extralife, metalDetector;
    private JButton help;
    private String helpMessage = "Powerups are a powerful tool for winning SuperSweeper.  Their point value will deduct from your points.\n\n" +
    "Extra Lives will save you if you click on a mine.\nThe Metal Detector will allow you to see a small part of the board for a second.\n"
    + "The Missile will destroy a part of the board, safely detonating all mines.";

    /** Constructor */
    public GamePanel(String propFileName) {
        // set up the gamePanel's internal panel structure
        setLayout(new BorderLayout());
        this.add(labelPanel, BorderLayout.NORTH);
        labelPanel.add(statusLabel);
        labelPanel.add(timeLabel);

        this.add(gridPanel, BorderLayout.CENTER);
        powerUpPanel.setLayout(new FlowLayout());
        
        extralife = new Powerup("Extra Life", 100);
        powerUpPanel.add(extralife);
        extralife.addActionListener(this);
        
        metalDetector = new Powerup("Metal Detector", 150);
        powerUpPanel.add(metalDetector);
        metalDetector.addActionListener(this);
        
        missile = new Powerup("Missile", 200);
        powerUpPanel.add(missile);
        missile.addActionListener(this);
        
        help = new JButton("?");
        powerUpPanel.add(help);
        help.addActionListener(this);
        
        this.add(powerUpPanel, BorderLayout.SOUTH);

        // Fetch the specified properties file (throws ioexception if its not
        // there)
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

        int w = GridUnit.sample.getWidth();
        int h = GridUnit.sample.getHeight();
        statusLabel.setText("Flags: 0");
        time = (int)_gs.getTime();
        timeLabel.setText(time/60 + ":" + String.format("%02d", time%60));
        timeDelay = new Timer(1000, this);

        // TODO: Get rid of hard coded height
        setPreferredSize(new Dimension(_gs.gridWidth * w, _gs.gridHeight * h + 40 + labelPanel.getHeight() + powerUpPanel.getHeight()));

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

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        GridUnit[][] grid = _gs.getGrid();
        BufferedImage unit = new BufferedImage(GridUnit.sample.getWidth(), GridUnit.sample.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics unit_graphics = unit.getGraphics();
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                unit_graphics.setColor(Color.WHITE);
                unit_graphics.fillRect(0, 0, unit.getWidth(), unit.getHeight());
                unit_graphics.drawImage(grid[x][y].getImage(), 0, 0, null);
                g.drawImage(unit, x * unit.getWidth(), y * unit.getHeight() + labelPanel.getHeight(), null);
            }
        }
    }

    private GridUnit getGridUnit(MouseEvent e) {
        int x = e.getX() / GridUnit.sample.getWidth();
        int y = (e.getY() - labelPanel.getHeight()) / GridUnit.sample.getHeight();

        if (x >= gridWidth || y >= gridHeight || x < 0 || y < 0) {
            return null;
        }

        return _gs.getGridUnit(x, y);
    }

    public void stateUpdated() {
        repaint();
        updateStatusLabel();
    }

    /** Listeners */
    @Override
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

    @Override
    public void mouseReleased(MouseEvent e) {
        GridUnit gridUnit = getGridUnit(e);
        if (gridUnit == null) {
            if (previouslyPressedGridUnit != null) {
                previouslyPressedGridUnit.checkCancelled();
            }
            stateUpdated();
            return;
        }

        if (_firstClick && SwingUtilities.isLeftMouseButton(e)) {
            do {
                _gs.resetGrid();
                _gs.populateMines();
                timeDelay.start();
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

    @Override
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

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent arg0) {
    }


    public void actionPerformed(ActionEvent e) {
        Object action = e.getSource();
        
        if(action == help) {
            JOptionPane.showMessageDialog(null, helpMessage, "Help", JOptionPane.PLAIN_MESSAGE);
        } else if(action == missile){
            // GamePanel.points = GamePanel.points - missile.getPrice();
            
        } else if(action == metalDetector){
            // GamePanel.points = GamePanel.points - missile.getPrice();
            
        } else if(action == extralife){
            // GamePanel.points = GamePanel.points - missile.getPrice();
            
        } else if(action == timeDelay){
            if(--time == 0){
                timeDelay.stop();
                timeLabel.setText("0:00");
                //Game Over
            } else {
                timeLabel.setText(time/60 + ":" + String.format("%02d", time%60));
            }
        }
        
    }
}
