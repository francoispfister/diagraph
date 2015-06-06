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
import org.eclipse.swt.graphics.Font;

import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.ParagraphTextLayout;
import org.eclipse.draw2d.text.TextFlow;

/**
 * A Figure with a bent corner and an embedded TextFlow within a FlowPage that
 * contains text.
 * 
 */
public class StickyNoteFigure extends BentCornerFigure {

    /**
     * The inner TextFlow *.
     */
    protected TextFlow textFlow;
    protected static Font elementNameFont = new Font(null, "Arial", 10, SWT.NORMAL);

    /**
     * Creates a new StickyNoteFigure with a default MarginBorder size of
     * DEFAULT_CORNER_SIZE - 3 and a FlowPage containing a TextFlow with the
     * style WORD_WRAP_SOFT.
     */
    public StickyNoteFigure() {
        this(BentCornerFigure.DEFAULT_CORNER_SIZE - 3);
    }

    /**
     * Creates a new StickyNoteFigure with a MarginBorder that is the given size
     * and a FlowPage containing a TextFlow with the style WORD_WRAP_SOFT.
     * 
     * @param borderSize
     *            the size of the MarginBorder
     */
    public StickyNoteFigure(int borderSize) {
        setLayoutManager(new org.eclipse.draw2d.ToolbarLayout());
        setBorder(new MarginBorder(borderSize));
        FlowPage flowPage = new FlowPage();
        this.textFlow = new TextFlow();
        textFlow.setFont(elementNameFont);
        textFlow.setForegroundColor(COLOR_BLACK);

        this.textFlow.setLayoutManager(new ParagraphTextLayout(this.textFlow, ParagraphTextLayout.WORD_WRAP_TRUNCATE));

        flowPage.add(this.textFlow);
        add(flowPage);
    }

    /**
     * Returns the text inside the TextFlow.
     * 
     * @return the text inside the text flow.
     */
    public String getText() {
        return this.textFlow.getText();
    }

    /**
     * Sets the text of the TextFlow to the given value.
     * 
     * @param newText
     *            the new text value.
     */
    public void setText(String newText) {
        this.textFlow.setText(newText);
    }

    /**
     * Returns the text flow where text is displayed.
     * 
     * @return the text flow where text is displayed
     */
    public TextFlow getTextFigure() {
        return textFlow;
    }

    public Dimension getMinimumDimension() {
        return new Dimension(200, 100);
    }
}
