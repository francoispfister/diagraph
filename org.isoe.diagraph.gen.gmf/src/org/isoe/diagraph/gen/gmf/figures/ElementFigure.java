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


import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;

/**
 * Draw2D figure for a label.
 */
public class ElementFigure extends Label {
	
	public ElementFigure()
	{
//		System.out.println("MethodFigure()");
		Font font = new Font(null, "Arial", 10, SWT.NORMAL);
		setFont(font);
		setLabelAlignment(PositionConstants.LEFT );
	}
	
}
