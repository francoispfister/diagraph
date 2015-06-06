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

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionEndpointLocator;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;

/**
 * A figure used for Generalization.
 * 
 * @author dumoulin
 * 
 */
public class GeneralizationConnectionFigure extends PolylineConnection implements IFigure {

    /**
     * Constructor.
     * 
     */
    public GeneralizationConnectionFigure() {
        super();
        setLineStyle(Graphics.LINE_SOLID); // line drawing style
        setLineWidth(2);
        // setForegroundColor( ColorConstants.red);

        // Set an arraow at the end of the figure
        PolygonDecoration dec = new PolygonDecoration();
        dec.setScale(20, 10);
        dec.setBackgroundColor(ColorConstants.white);
        dec.setLineWidth(2);
        setTargetDecoration(dec);

        setToolTip(new Label("generalize"));

    }

}
