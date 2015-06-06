 /**
 * Copyright (c) 2014 Laboratoire de Genie Informatique et Ingenierie de Production - Ecole des Mines d'Ales
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Francois Pfister (ISOE-LGI2P) - initial API and implementation
 */
package org.isoe.diagraph.gen.gmf.figures;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * A figure that has a bent corner in the top right hand. Typically used for
 * sticky notes.
 * 
 * @see org.eclipse.gef.examples.ediagram.figures.BentCornerFigure
 */
public class BentCornerFigure extends FigureBase {

    /**
     * The default amount of pixels subtracted from the figure's height and
     * width to determine the size of the corner.
     */
    protected static int DEFAULT_CORNER_SIZE = 10;

    /**
     * 
     */
    private int cornerSize;

    /**
     * Constructs an empty BentCornerFigure with default background color of
     * ColorConstants.tooltipBackground and default corner size.
     */
    public BentCornerFigure() {
        setCornerSize(DEFAULT_CORNER_SIZE);
        setOpaque(false);
    }

    /**
     * Returns the size, in pixels, that the figure should use to draw its bent
     * corner.
     * 
     * @return size of the corner
     */
    public int getCornerSize() {
        return this.cornerSize;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.draw2d.Figure#paintFigure(org.eclipse.draw2d.Graphics)
     */
    /**
     * 
     * 
     * @param graphics
     */
    @Override
    public void paintFigure(Graphics graphics) {
        super.paintFigure(graphics);
        Rectangle rect = getBounds().getCopy();

        graphics.translate(getLocation());

        // fill the note
        PointList outline = new PointList();

        outline.addPoint(0, 0);
        outline.addPoint(rect.width - this.cornerSize, 0);
        outline.addPoint(rect.width - 1, this.cornerSize);
        outline.addPoint(rect.width - 1, rect.height - 1);
        outline.addPoint(0, rect.height - 1);

        graphics.fillPolygon(outline);

        // draw the inner outline
        PointList innerLine = new PointList();

        innerLine.addPoint(rect.width - this.cornerSize - 1, 0);
        innerLine.addPoint(rect.width - this.cornerSize - 1, this.cornerSize);
        innerLine.addPoint(rect.width - 1, this.cornerSize);
        innerLine.addPoint(rect.width - this.cornerSize - 1, 0);
        innerLine.addPoint(0, 0);
        innerLine.addPoint(0, rect.height - 1);
        innerLine.addPoint(rect.width - 1, rect.height - 1);
        innerLine.addPoint(rect.width - 1, this.cornerSize);

        graphics.drawPolygon(innerLine);

        graphics.translate(getLocation().getNegated());
    }

    /**
     * Sets the size of the figure's corner to the given offset.
     * 
     * @param newSize
     *            the new size to use.
     */
    public void setCornerSize(int newSize) {
        this.cornerSize = newSize;
    }

    public Dimension getMinimumDimension() {
        return new Dimension(DEFAULT_CORNER_SIZE + 10, DEFAULT_CORNER_SIZE + 10);
    }
}
