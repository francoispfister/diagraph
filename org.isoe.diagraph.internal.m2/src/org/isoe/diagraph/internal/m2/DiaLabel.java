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
package org.isoe.diagraph.internal.m2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.isoe.diagraph.internal.api.IDiaLabel;
import org.isoe.diagraph.internal.api.IDiaNamedElement;
import org.isoe.diagraph.internal.m2.api.IDiaContainedElement;
import org.isoe.fwk.core.DParams;


/**
 *
 * @author pfister
 *
 */
public class DiaLabel implements IDiaLabel, IDiaNamedElement{

	private String defaultName;
	private String attributeName_;
	private List<EAttribute> eAttributes = new ArrayList<EAttribute>();
	private IDiaContainedElement owner;
	protected boolean inSuperType_;
	private boolean inferred;
	private boolean multiple;
	private String[] attributeNames;
	private String[] attributeSeparators_;


	public String multipleAsString(){
		//	lb.getAttributeSeparators_();
		String lg="";
		String[] seps =  getAttributeSeparators_();

		if (seps!=null)
		  for (String sep : seps)
			lg+=sep+" ";

		String[] ats =  getAttributeNames();
		if (ats!=null)
		  for (String at : ats)
			lg+=at+" ";

		return lg;
	}


	public String[] getAttributeSeparators_() {
		return attributeSeparators_;
	}

	public String[] getAttributeNames() {
		return attributeNames;
	}

	public void setAttributeNames(String[] attributeNames) {
		this.attributeNames = attributeNames;
	}

	public List<EAttribute> getEAttributes_() {
		return eAttributes;
	}


	public void setEAttributes(List<EAttribute> eAttributes) {
		this.eAttributes = eAttributes;
	}


	public void setEAttribute(EAttribute eAttribute) {
		if (eAttributes.isEmpty())
			eAttributes.add(eAttribute);
		else
			eAttributes.add(0, eAttribute);
	}


	public EAttribute getEAttribute() {
		if (eAttributes.isEmpty())
			return null;
		else
			return eAttributes.get(0);
	}



	public IDiaContainedElement getOwner() {
		return owner;
	}

	public boolean isInSuperType() {
		return inSuperType_;
	}


    @Override
	public void setInSuperType(boolean inSuperType_) {
		this.inSuperType_ = inSuperType_;
	}


	public boolean isInferred() {
		return inferred;
	}


	public DiaLabel(DiaContainedElement del, String attributeNames, boolean multiple,boolean dummy) { //FP140204
		this.owner = del;
		this.attributeName_ = attributeNames;
		if (DParams.COMPOSITE_LABELS_LOG_)
			cloglabels("create DiaLabel "+(multiple?" multiple ":" ")+attributeName_);
		this.multiple=multiple;
	}

	private void cloglabels(String mesg) {
		if (DParams.COMPOSITE_LABELS_LOG_)
			System.out.println(mesg);
	}


	public String toString() {
		return "[" + (multiple?"mult ":"uniq ")+ this.getClass().getSimpleName() + "] " + "attributeName: "
				+ attributeName_ + " name:"+getName() +" defaultName:" + defaultName;//+ " Name:" + this.getName() +" defaultName:" + defaultName;
	}

	public String getAttributeName_() {
		return attributeName_;
	}

	@Override
	public String getName() {
		try {
			if (defaultName != null)
				return defaultName;
			else{
				EAttribute attr = getEAttribute();
				if (attr==null)
					return "DLnull?";//FP140205x
				else{
					return  //FP140205
							((ENamedElement) (owner.getEModelElement())).getName()
							+ attr.getName().substring(0, 1).toUpperCase()
							+ attr.getName().substring(1);

				}
			}


		} catch (Exception e) {
			return "DLerror?";
		}

	}


	private EAttribute findAttributeInAllClasses(EClass ownclaz, String aname){
		EList<EStructuralFeature> fs=ownclaz.getEAllStructuralFeatures();
		for (EStructuralFeature eStructuralFeature : fs) {
			if (eStructuralFeature instanceof EAttribute && eStructuralFeature.getName().equals(aname))
				return (EAttribute) eStructuralFeature;
		}
		return null;
	}

	private EAttribute findAttributeInOwnClass(EClass ownclaz, String aname){
		EList<EStructuralFeature> fs=ownclaz.getEAllStructuralFeatures();
		for (EStructuralFeature eStructuralFeature : fs)
			if (eStructuralFeature instanceof EAttribute && eStructuralFeature.getName().equals(aname))
				return (EAttribute) eStructuralFeature;
		return null;
	}


	private EAttribute findAttribute(String ename) { //FP140206
		  EAttribute attr = findAttributeInOwnClass((EClass) owner.getEModelElement(),ename);//(EAttribute) ownclaz.getEStructuralFeature(attributeName);
		  if (attr==null)
			attr = findAttributeInAllClasses((EClass) owner.getEModelElement(),ename);
		  if (attr==null)
			  throw new RuntimeException("no attribute found for "+ ename+" in "+this.getOwner().getName());
        return attr;
	}



	@Override
	public void createWithNames() { //FP140206xxxaaa  //FP2311

		attributeNames = null;
		attributeSeparators_= null;

		if (attributeName_ == null || attributeName_.isEmpty())
			throw new RuntimeException("error in resolveWithName");
		if (!isMultiple()){
			setEAttribute(findAttribute(attributeName_));
		}
		 else{
			splitAttributeNames(attributeName_);//attributeName.split(findSeparator_());
			for (String ename : attributeNames)
	                  if (!ename.isEmpty()) eAttributes.add(findAttribute(ename));
		 }
	}


	private static char[] toArray(String s){
		char[] result = new char[s.length()];
		for (int i=0;i<s.length();i++)
			result[i] = s.charAt(i);
		return result;

	}

	private static char[]SEPS = toArray(DParams.LABEL_SEPARATORS);

	private void splitAttributeNames(String names) {
		if (DParams.COMPOSITE_LABELS_LOG_){
			cloglabels("parsing composite attribute name ");
			StringBuffer seps=  new StringBuffer();
			for (char l : SEPS) {
				seps.append(l).append(" ");
			}
			cloglabels("separators are "+seps.toString());
		}
	    StringBuffer buf = new StringBuffer();
		List<String> atrs = new ArrayList<String>();
		List<String> seps = new ArrayList<String>();
		char[]cs = toArray(names);
		for (char c:cs) {
			for (char l : SEPS) {
				if (l==c){
					atrs.add(buf.toString());
					seps.add(new StringBuffer().append(c).toString());
					buf = new StringBuffer();
					break;
				}
			}
			if (DParams.LABEL_SEPARATORS.indexOf(c)==-1)
			   buf.append(c);
		}
		atrs.add(buf.toString());
		int j=0;
		attributeNames = new String[atrs.size()];
		attributeSeparators_ = new String[seps.size()];

		for (String split : atrs) {
			attributeNames[j]=atrs.get(j);
			j++;
		}
		j=0;
		for (String sepr : seps) {
			attributeSeparators_[j]=seps.get(j);
			j++;
		}

		for (String attributeName : attributeNames) {  //PF140630
			cloglabels("attributeName="+attributeName);
		}


	}

/*
	private String findSeparator_() {
		return DParams.LABEL_SEPARATORS[0];
	}*/

	@Override
	public void setInferred_(boolean inferred) {
		this.inferred = inferred;
	}

	public void setDefaultName(String defaultName) {
		this.defaultName = defaultName;
	}

	public String getDefaultName() {
		return defaultName;
	}

	public boolean isMultiple() {
		return multiple;
	}


	public void setAttributeSeparators(String[] attributeSeparators) { //FP140220
		this.attributeSeparators_ = attributeSeparators;
	}



}
