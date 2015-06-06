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

import org.eclipse.swt.graphics.Color;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;

/**
 * This class contains common utilities.
 * @author dumoulin
 *
 */
public class FigureBase extends Figure {

    static protected Color borderColor = new Color(null,157,124,47);
    static protected Color backgroundColor = new Color(null,248,249,214);
    static protected Color COLOR_BLACK = new Color(null,0,0,0);

    public FigureBase() {
        super();
        setBackgroundColor(backgroundColor);
        setForegroundColor(borderColor);

    }

}
