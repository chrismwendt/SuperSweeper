package com.cs408.supersweeper;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.SwingUtilities;

public class GridPanel extends Panel implements MouseListener, MouseMotionListener {

    private static final long serialVersionUID = 7300495993169526438L;
    private GameState _gs;
    private GamePanel _gp;
    private boolean _firstClick = true;
    private boolean _powerup = false;
    private GridUnit _previouslyPressedGridUnit = null;
    
    /** Constructor */
    public GridPanel(GameState gs, GamePanel gp) {
        this._gs = gs;
        this._gp = gp;
        
        //Add mouse listeners
        addMouseListener(this);
        addMouseMotionListener(this);
    }
    
    
    /** Internal Methods */
    private GridUnit getGridUnit(MouseEvent e) {
        int x = e.getX() / GridUnit.sample.getWidth();
        int y = e.getY() / GridUnit.sample.getHeight();

        if (x >= _gs.getGridWidth() || y >= _gs.getGridWidth() || x < 0 || y < 0) {
            return null;
        }

        return _gs.getGridUnit(x, y);
    }
    
    public void stateRedraw() {
        repaint();
        ((GamePanel) this.getParent()).updateStatusLabel();
    }
    
    
    /** Setters */
    public void resetFirstClick() {
        this._firstClick = true;
        this._previouslyPressedGridUnit = null;
        stateRedraw();
    }
    
    public void setPowerup(boolean b){
        _powerup = b;
    }
    
    
    @Override
    public void paint(Graphics g) {
        GridUnit[][] grid = _gs.getGrid();
        BufferedImage unit = new BufferedImage(GridUnit.sample.getWidth(), GridUnit.sample.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics unit_graphics = unit.getGraphics();
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                unit_graphics.setColor(Color.WHITE);
                unit_graphics.fillRect(0, 0, unit.getWidth(), unit.getHeight());
                unit_graphics.drawImage(grid[x][y].getImage(), 0, 0, null);
                g.drawImage(unit, x * unit.getWidth(), y * unit.getHeight(), null);
            }
        }
    }
    
    /** Listeners */
    @Override
    public void mousePressed(MouseEvent e) {
        GridUnit gridUnit = getGridUnit(e);
        if (gridUnit == null || _gs.isGameOver()) {
            return;
        }

        if (SwingUtilities.isLeftMouseButton(e)) {
            _gs.checkPressed(gridUnit);
        } else if (SwingUtilities.isRightMouseButton(e)) {
            _gs.flagPressed(gridUnit);
        }

        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(_gs.isGameOver())
            return;
        GridUnit gridUnit = getGridUnit(e);
        if (gridUnit == null) {
            if (_previouslyPressedGridUnit != null) {
                _gs.checkCancelled(_previouslyPressedGridUnit);
            }
            stateRedraw();
            return;
        }

        if (_firstClick && SwingUtilities.isLeftMouseButton(e)) {
            _gs.removeMines();
            _gs.populateMines();
            _gp.startTimer();
            _firstClick = false;
        }

        if (SwingUtilities.isLeftMouseButton(e)) {
            if(_powerup) {
                _powerup = false;
                _gs.powerup(gridUnit);
                
                if(_gs.isMetalDetector()) {           
                    _gs.powerUpRelease(gridUnit);
                }
            } else {
                if(!gridUnit.isChecked)
                    _gs.checkReleased(gridUnit);
                else
                    gridUnit.setImage(GridUnit.images.get("normal"));
            }
            _previouslyPressedGridUnit = null;
        } else if (SwingUtilities.isRightMouseButton(e)) {
            _gs.flagReleased(gridUnit);
        }

       stateRedraw();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (_previouslyPressedGridUnit == null || _gs.isGameOver()) {
            return;
        }

        GridUnit gridUnit = getGridUnit(e);
        if (gridUnit == null) {
            return;
        }

        if (SwingUtilities.isLeftMouseButton(e)) {
            _gs.checkCancelled(_previouslyPressedGridUnit);
            _gs.checkPressed(gridUnit);
            _previouslyPressedGridUnit = gridUnit;
        }

        stateRedraw();
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
    public void mouseMoved(MouseEvent e) {
        if (_powerup){
            if (_gs.isGameOver()) {
                return;
            }
    
            GridUnit gridUnit = getGridUnit(e);
            if (gridUnit == null) {
                return;
            }
            if(_previouslyPressedGridUnit != null){
                for (GridUnit unit : _previouslyPressedGridUnit.adjacentGridUnits) {
                    _gs.checkCancelled(unit);
                }
            }
            for (GridUnit unit : gridUnit.adjacentGridUnits) {
                _gs.checkPressed(unit);
            }
            _gs.checkPressed(gridUnit);
            _previouslyPressedGridUnit = gridUnit;
    
            stateRedraw();
        }
    }

}
