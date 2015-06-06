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
package org.isoe.diagraph.checker;

import org.eclipse.emf.ecore.EClass;
import org.isoe.diagraph.internal.api.IAnnotatedEClass;
import org.isoe.diagraph.internal.api.IAnnotationPattern;
import org.isoe.fwk.log.LogConfig;


public class AnnotationPattern implements IAnnotationPattern {

	private static final boolean LOG = false;
	private IAnnotatedEClass sourceAnnotatedClass;
	private IAnnotatedEClass targetAnnotatedClass;
	private EClass sourceClass;
	private EClass targetClass;
	private String key;


	@Override
	public String toString(){
		String result = super.toString();
		if (sourceAnnotatedClass!=null||targetAnnotatedClass!=null || sourceClass!=null || targetClass!=null)
			result="";
		if (key!=null)
			result+="key: "+key;
		if (sourceClass!=null)
				result+="\n"+sourceClass.getName();
		if (targetClass!=null)
				result+="\n"+targetClass.getName();

		if (sourceAnnotatedClass!=null)
			result+="\n"+sourceAnnotatedClass.toString();
		if (targetAnnotatedClass!=null)
			result+="\n"+targetAnnotatedClass.toString();
		return result;
	}

	public IAnnotatedEClass getSourceAnnotatedEClass() {
		return sourceAnnotatedClass;
	}



	public IAnnotatedEClass getTargetAnnotatedEClass() {
		return targetAnnotatedClass;
	}

	public AnnotationPattern(IAnnotatedEClass sourceAnnotatedEClass, IAnnotatedEClass targetAnnotatedEClass, EClass eSourceClass, EClass eTargetClass, String key) {
		this.sourceAnnotatedClass = sourceAnnotatedEClass;
		this.targetAnnotatedClass = targetAnnotatedEClass;
		this.sourceClass = eSourceClass;
		this.targetClass = eTargetClass;
		this.key = key;
	}

	@Override
	public String getKey() {
		return key;
	}


	@Override
	public EClass getSourceEClass() {
		return sourceClass;
	}

	@Override
	public EClass getTargetEClass(){
		return targetClass;
	}

/*
	@Override
	public void applyChangeLitteralPattern() {
		if (LOG_ && LogConfig.LOG_GLOBAL)
			System.out.println("refactoring LitteralPattern " + sourceClass.getName());
		DAnnotation.changeLitteral(sourceClass);
	}
	*/

	@Override
	public void applyChangeAnnotationLinkPattern1() {
		if (LOG )
		  System.out.println("ChangeAnnotationLinkPattern1: nothing to do for "+this.toString());//+sourceClass.getName()+"->"+targetClass.getName()+"("+key+")");
	}



	@Override
	public void applyChangeAnnotationLinkPattern2b() {
		if (LOG )
		  System.out.println("ChangeAnnotationLinkPattern2: TODO for "+this.toString());//+sourceClass.getName()+"->"+targetClass.getName()+"("+key+")  \n"+this.toString());
	}

	@Override
	public void applyChangeAnnotationPattern() {
		if (LOG )
		  System.out.println("ChangeAnnotationLinkPattern: TODO for " +this.toString());
	}





}
