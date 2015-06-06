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
package mtbe.model.matcher.impl;


import mtbe.model.matcher.DoubleLabel;
import mtbe.model.matcher.ILabel;

public class DoubleLabelImpl implements ILabel, DoubleLabel{

	private double value;

	private int love;

	public DoubleLabelImpl(double value, int love) {
		this.value = value;
		this.love = love;
	}
	
	
	@Override
	public String toString() {
		return getStringValue() + " ("+love+")";
	}


	@Override
	public int getLove() {
		return love;
	}


	public double getDoubleValue(){
		return value;
	}
	

	@Override
	public Object getValue(){
		return new Double(value);
	}

	@Override
	public String getStringValue() {
		return Double.toString(value);
	}


	@Override
	public String[] getValues() {
		String[] result = new String[1];
	    result[0]=getStringValue();
	    return result;
	}
	
}
