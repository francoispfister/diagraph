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

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.ToolbarLayout;

/**
 * A shape accepting childs.
 * @author dumoulin
 *
 */
public class ShapeFigure extends Figure {

	private Label figureName;
	private Figure contentPane;
	private Color borderColor=new Color(null,157,124,47);
	private Color backgroundColor=new Color(null,248,249,214);

	public ShapeFigure()
	{
		ToolbarLayout layout = new ToolbarLayout();

		setLayoutManager(layout);
        layout.setVertical(true);
        layout.setStretchMinorAxis(true);
		layout.setSpacing(2);

		setOpaque(true); // non-transparent figure
		//setBorder( new LineBorder(ColorConstants.black));
		setBorder( new LineBorder(borderColor));
		//setBackgroundColor(ColorConstants.yellow);
		setBackgroundColor(backgroundColor);//yellow

		Font font = new Font(null, "Arial", 10, SWT.BOLD);
		figureName = new Label();
		figureName.setFont(font);
		figureName.setOpaque(false);
		add(figureName);
		// The area accepting inner figures.
		createContentPane();	
	}

	/**
	 * Create an area for childs which are list of labels
	 */
	private void createContentPane() {
        contentPane = new Figure();
        ToolbarLayout layout = new ToolbarLayout();
        layout.setVertical(true);
        contentPane.setLayoutManager(layout);
        add(contentPane);       
	}

	/**
	 * Get the contentPane of the figure.
	 * The contentPane is used to draw childs of the figure.
	 * @return
	 */
	public Figure getContentPane() {
		return contentPane;
	}

	public void setClassName(String className) {
		figureName.setText(className);
	}
}
