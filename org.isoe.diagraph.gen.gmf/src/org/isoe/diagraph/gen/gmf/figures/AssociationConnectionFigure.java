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
import org.eclipse.draw2d.PolylineDecoration;
import org.eclipse.draw2d.RelativeLocator;
import org.eclipse.draw2d.RotatableDecoration;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.PointList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;

/**
 * A figure used for Generalization.
 * @author dumoulin
 *
 */
public class AssociationConnectionFigure extends PolylineConnection implements IFigure {

    public static Font font = new Font(null, "Arial", 10, SWT.NORMAL);
    
    public static final int NONE = 0;
    public static final int COMPOSITION = 1;
    public static final int AGGREGATION = 2;
    public static final int NAVIGABLE = 3;
	
    private boolean isSourceNavigable;
    private boolean isTargetNavigable;
    private int srcDecoration;
    private int targetDecoration;

    /** labels */
    protected Label name;   
    protected Label srcRole;
    protected Label srcCardinality;
    protected Label targetRole;
    protected Label targetCardinality;

    /** A diamond template */
    public static final PointList DIAMOND_DESC = new PointList();

    static {
        DIAMOND_DESC.addPoint(0,0);
        DIAMOND_DESC.addPoint(-2,2);
        DIAMOND_DESC.addPoint(-4,0);
        DIAMOND_DESC.addPoint(-2,-2);
        DIAMOND_DESC.addPoint(0,0);
    }


    /**
	 * Constructor.
	 *
	 */
	public AssociationConnectionFigure() {
		super();
		setLineStyle(Graphics.LINE_SOLID);  // line drawing style
		setLineWidth(2);
		//setForegroundColor( ColorConstants.red);
		
		// Set an arraow at the end of the figure
        setTargetDecoration(new CompositionDecoration()); 
        setSourceDecoration(new NavigableDecoration()); 

		setToolTip(new Label("association"));

		// labels
	        
	        // Create labels
	        srcRole = createAndAddLabel(font, false, 10, 10, "src role");
	        srcCardinality = createAndAddLabel(font, false, -10, 10, "cardinality");
	        
	        targetRole = createAndAddLabel(font, true, 10, 10, "target role");
	        targetCardinality = createAndAddLabel(font, true, -10, 10, "cardinality");
	        
	        // connection name
	        name = createAndAddNameLabel(font, "association");

	}
	
	/**
     * Refresh connections decorations.
     */
    public void refreshDecorations() {
        setSourceDecoration(createDecoration(srcDecoration));
        setTargetDecoration(createDecoration(targetDecoration));
    }
    
    protected RotatableDecoration createDecoration(int type)
    {
        switch(type){
        case COMPOSITION : 
            return new CompositionDecoration(); 
        case AGGREGATION : 
            return new AggregationDecoration(); 
        case NAVIGABLE : 
            return new NavigableDecoration(); 
        default : 
            return null; 
        }
        
    }
    /**
     * 
     * @param navigable
     */
public void setSourceIsNavigable(boolean navigable) {
    isSourceNavigable = navigable;
    
}

public void setTargetIsNavigable(boolean navigable) {
    isTargetNavigable = navigable;
    
}

/**
 * @param srcDecoration the srcDecoration to set
 */
public void setSrcDecoration(int srcDecoration) {
    this.srcDecoration = srcDecoration;
}

/**
 * @param targetDecoration the targetDecoration to set
 */
public void setTargetDecoration(int targetDecoration) {
    this.targetDecoration = targetDecoration;
}
    
/**
 * Create a central label.
 * @param font
 * @param tooltip
 * @return
 */
private Label createAndAddNameLabel( Font font, String tooltip) {
    Label label = createLabel(font);
    // Create a central label
    RelativeLocator tel = new RelativeLocator(this, 0.5, 0.5);
    add(label, tel);
    label.setToolTip(new Label(tooltip));
    
    return label;
}

/**
 * 
 * @param font
 * @param isEnd true if location is relative to end point
 * @param vDistance Distance in pixel from the Connection
 * @param uDistance Distance in pixel from the Connection owner
 * @return
 */
private Label createAndAddLabel( Font font, Boolean isEnd, int vDistance, int uDistance, String tooltip) {
    Label label = createAndAddLabel(font, isEnd, vDistance, uDistance);
    label.setToolTip(new Label(tooltip));
    
    return label;
}

/**
 * 
 * @param font
 * @param isEnd true if location is relative to end point
 * @param vDistance Distance in pixel from the Connection
 * @param uDistance Distance in pixel from the Connection owner
 * @return
 */
private Label createAndAddLabel( Font font, Boolean isEnd, int vDistance, int uDistance) {
    Label label = createLabel(font);
    ConnectionEndpointLocator tel = createLabelEnPointLocator(isEnd, vDistance, uDistance);
    add(label, tel);
    
    return label;
}

/**
 * Create label constraint.
 * @param isEnd true if location is relative to end point
 * @param vDistance Distance in pixel from the Connection
 * @param uDistance Distance in pixel from the Connection owner
 * @return The constraint
 */
private ConnectionEndpointLocator createLabelEnPointLocator(Boolean isEnd, int vDistance, int uDistance) {
    ConnectionEndpointLocator tel = new ConnectionEndpointLocator(this, isEnd);
    tel.setVDistance(vDistance);
    tel.setUDistance(uDistance);
    return tel;
}

/**
 * @param font
 */
private Label createLabel(Font font) {
    Label label = new Label();
    label.setForegroundColor(ColorConstants.cyan);
    label.setMinimumSize(new Dimension(10, 8));
    label.setFont(font);
    label.setText("_");
    return label;
}

/**
 * @return the srcCardinality
 */
public Label getSrcCardinality() {
    return srcCardinality;
}



/**
 * @param srcCardinality the srcCardinality to set
 */
public void setSrcCardinality(Label srcCardinality) {
    this.srcCardinality = srcCardinality;
}

/**
 * @param srcCardinality the srcCardinality to set
 */
public void setSrcCardinality(String text) {
    if(text == null || text.length()==0)
        text="_";
    srcCardinality.setText(text);
}

/**
 * @return the srcRole
 */
public Label getSrcRole() {
    return srcRole;
}

/**
 * @param srcRole the srcRole to set
 */
public void setSrcRole(Label srcRole) {
    this.srcRole = srcRole;
}

/**
 * @param srcRole the srcRole to set
 */
public void setSrcRole(String text) {
    if(text == null || text.length()==0)
        text="_";
    srcRole.setText(text);
}

/**
 * @return the targetCardinality
 */
public Label getTargetCardinality() {
    return targetCardinality;
}

/**
 * @param targetCardinality the targetCardinality to set
 */
public void setTargetCardinality(Label targetCardinality) {
    this.targetCardinality = targetCardinality;
}

/**
 * @param targetCardinality the targetCardinality to set
 */
public void setTargetCardinality(String text) {
    if(text == null || text.length()==0)
        text="_";
    targetCardinality.setText(text);
}

/**
 * @return the targetRole
 */
public Label getTargetRole() {
    return targetRole;
}

/**
 * @param targetRole the targetRole to set
 */
public void setTargetRole(Label targetRole) {
    this.targetRole = targetRole;
}

/**
 * @param targetRole the targetRole to set
 */
public void setTargetRole(String text) {
    if(text == null || text.length()==0)
        text="_";
    targetRole.setText(text);
}

/**
 * An arraow end
 * @author dumoulin
 *
 */
public class NavigableDecoration extends PolylineDecoration {
    
    public NavigableDecoration()
    {
        setScale( 15, 5);
        setLineWidth(1);
    }
}

/**
 * An aggregation end
 * @author dumoulin
 *
 */
public class AggregationDecoration extends PolygonDecoration {

    public AggregationDecoration()
    {
        setTemplate(DIAMOND_DESC);
        setBackgroundColor(ColorConstants.white);
        setLineWidth(2);
        setScale(3, 3);
    }
}

/**
 * An composition end
 * @author dumoulin
 *
 */
public class CompositionDecoration extends PolygonDecoration {
    
    public CompositionDecoration()
    {
        setTemplate(DIAMOND_DESC);
        setBackgroundColor(ColorConstants.black);
        setLineWidth(2);
        setScale(3, 3);
    }
}

}
