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

import java.util.Iterator;

import org.eclipse.draw2d.FigureListener;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Rectangle;

public class PolylineConnectionBase extends PolylineConnection implements IFigure {

	private Rectangle oldBounds;
	/**
	 * Notifies to all {@link FigureListener}s that this figure has moved. Moved means
	 * that the bounds have changed in some way, location and/or size. 
	 * @since 3.1
	 */
	protected void fireFigureMoved() {
		
		Rectangle newBounds = getBounds();
		if( newBounds.equals(oldBounds))
		{
			return;
		}
		oldBounds = newBounds;
		
		Iterator figureListeners = getListeners(FigureListener.class);
		while (figureListeners.hasNext())
			((FigureListener)figureListeners.next()).
				figureMoved(this);
	}

}
