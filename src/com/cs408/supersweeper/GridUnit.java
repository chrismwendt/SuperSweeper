package com.cs408.supersweeper;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class GridUnit {
    public boolean mine = false;
    public boolean checked = false;
    public boolean flagged = false;
    public boolean pressed = false;
    public boolean hovered = false;
    public boolean exposed = false;
    public ArrayList<GridUnit> _adjacentUnits = new ArrayList<GridUnit>();
    // TODO width and height should not be hardcoded, maybe set to the size of one of the images
    public int width = 16, height = 16;
    private static HashMap<String, BufferedImage> images = new HashMap<String, BufferedImage>();

    static {
        GridUnit.images.put("normal", Utility.imageFromFilename("images/grid_unit.png"));
        GridUnit.images.put("empty", Utility.imageFromFilename("images/grid_unit_empty.png"));
        GridUnit.images.put("hover", Utility.imageFromFilename("images/grid_unit_hover.png"));
        GridUnit.images.put("press", Utility.imageFromFilename("images/grid_unit_click.png"));
        GridUnit.images.put("mine", Utility.imageFromFilename("images/grid_unit_mine.png"));
        GridUnit.images.put("flag", Utility.imageFromFilename("images/grid_unit_flag.png"));
        for (int i = 1; i <= 8; i++) {
            GridUnit.images.put(String.valueOf(i), Utility.imageFromFilename("images/grid_unit_" + i + ".png"));
        }
    }

    /** Constructor */
    public GridUnit() {
    }

    public void draw(Graphics g) {
        BufferedImage image = null;

        if (checked) {
            // TODO replace grid_unit_empty with grid_unit_0
            if (mine) {
                image = GridUnit.images.get("mine");
            } else if (adjacentMineCount() > 0) {
                image = GridUnit.images.get(String.valueOf(adjacentMineCount()));
            } else {
                image = GridUnit.images.get("empty");
            }
        } else {
            if (flagged) {
                image = GridUnit.images.get("flag");
            } else {
                if (pressed) {
                    image = GridUnit.images.get("press");
                } else {
                    if (hovered) {
                        image = GridUnit.images.get("hover");
                    } else {
                        image = GridUnit.images.get("normal");
                    }
                }
            }
        }

        g.drawImage(image, 0, 0, null);
    }

    public int adjacentMineCount() {
        int count = 0;
        for (GridUnit unit : _adjacentUnits) {
            if (unit.mine) {
                count++;
            }
        }
        return count;
    }

    private void exposeMines() {
        if (exposed) {
            return;
        }

        exposed = true;
        if (mine) {
            checked = true;
        }

        for (GridUnit unit : _adjacentUnits) {
            if (!unit.exposed) {
                unit.exposeMines();
            }
        }
    }

    private void check() {
        checked = true;

        if (adjacentMineCount() > 0) {
            return;
        }

        for (GridUnit unit : _adjacentUnits) {
            // System.out.println(u + " | " + getNearbyMineCount());
            // System.out.println(unit + " | " + adjacentMineCount());

            if (!unit.checked) {
                unit.check();
            }
        }
    }

    public void checkPress() {
        if (!checked && !flagged) {
            pressed = true;
        }
    }

    public void checkRelease() {
        pressed = false;
        if (!flagged) {
            if (mine) {
                exposeMines();
            } else {
                check();
            }
        }
    }

    public void flagPress() {
        // ignore
    }

    public void flagRelease() {
        if (!checked) {
            flagged = !flagged;
        }
    }
}
