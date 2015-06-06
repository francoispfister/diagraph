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

import org.eclipse.draw2d.Border;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.ToolbarLayout;

/**
 * List Container figure.
 * This figure is designed to receive element in a stack fashion.
 * @author dumoulin
 *
 */
public class ListContainerFigure extends Figure {

  public ListContainerFigure()
  {
	 System.out.println("create ListContainerFigure()");
	// layout:
	ToolbarLayout layout = new ToolbarLayout(ToolbarLayout.VERTICAL);
	layout.setStretchMinorAxis(true);
	layout.setMinorAlignment(ToolbarLayout.ALIGN_TOPLEFT);
	int spacing = 1;
	layout.setSpacing(spacing);

	setLayoutManager(layout);
	Border border = new LineBorder(ColorConstants.black, 3); 
	setBorder(border);

  }
}
