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
package org.isoe.diagraph.parser;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.isoe.diagraph.parser.api.IDiagraphAssociation;
import org.isoe.diagraph.parser.api.IDiagraphClassAssociation;
import org.isoe.diagraph.parser.api.IDiagraphElement;
import org.isoe.diagraph.parser.api.IDiagraphNode;
import org.isoe.diagraph.parser.api.IDiagraphParser;
import org.isoe.diagraph.parser.api.IDiagraphAssociation.AssociationCandidateType;
import org.isoe.diagraph.parser.api.IDiagraphAssociation.AssociationType;

/**
 *
 * @author fpfister
 *
 */
public class DiagraphClassAssociation extends DiagraphAssociation implements IDiagraphClassAssociation {

	private EClass link;
	private boolean propagated;
	private boolean derived;
	protected EReference sourceReference;


	public DiagraphClassAssociation(IDiagraphParser parser,String view, EClass source, EClass instanceSource,EClass target,
			List<EClass> targetSubHierarchy, EClass link, AssociationType associationType,boolean derived,String info) {
		super(parser,view, source, instanceSource, target, null,targetSubHierarchy, associationType,derived,info);
		this.link = link;
	}


    public DiagraphClassAssociation(IDiagraphParser parser, String view,
    		IDiagraphNode contNode, IDiagraphElement srcNode,
    		IDiagraphElement trgNode, EClass link,EClass refinstancesource,EReference targetref,boolean derived, String info) {
		super(parser,view,
				contNode, srcNode,
				 trgNode, refinstancesource,targetref,derived,  info);
		this.link = link;
	}

	@Override
	public EReference getSourceReference() {
		return sourceReference;
	}

	public List<EReference> getTargetNodeReferences() { //FP140503b
		List<EReference> result = new ArrayList<EReference>();
		for (EReference eReference : getType().getEAllReferences())
			if (eReference.getUpperBound()==1 && isNode((EClass) eReference.getEType()) && eReference != sourceReference)
				     result.add(eReference);
		return result;
	}

	private boolean isNode(EClass claz) {
		IDiagraphNode result =  getParser().findNode_(claz);
		return result!=null;
	}

	@Override
	public void resolveTargetReference() { //FP140503b
		String errorMessage="";
		if (LOG)
			clog("RTG " + getType().getName());
		List<EReference> lrefs = getTargetNodeReferences();
		if (lrefs.size()==1)
			targetReference = lrefs.get(0);
		else if (lrefs.size()>1){
			errorMessage="(Layer " + getView() + ") ClassAssociation " + this.getName()
					+ " Fix Target Reference Error !!";
			throw new RuntimeException(errorMessage);
		} else
			if (getType().isAbstract())
				clog("RTG " + " no reference, is abstract, ok");
			else {
				errorMessage="(Layer " + getView() + ") ClassAssociation "
						+ this.getName()
						+ " Fix Target Reference Error : no target for Link !!";
				throw new RuntimeException(errorMessage);
			}
	}

	@Override
	public boolean isTargetAbstract() {
		return link.isAbstract();
	}



	@Override
	public EClass getLink() {
		return link;
	}

	@Override
	public EClass getType() {
		return getTarget().eClass();
	}

	@Override
	public String getName() {
		return link.getName();
	}

	@Override
	public void setPropagated(boolean propagated) {
		this.propagated = propagated;

	}

	@Override
	public int getDepth() {
		return 0;
	}

	@Override
	public AssociationType getAssociationType() {
		return associationType;
	}


	@Override
	public String toLog() { //FP150322
		try {
			/*String id = String.format("[%8s]", F12.format(this.hashCode()));
			String srcid = String.format("%8s",
					F12.format(getSource().hashCode()));
			String trgid = String.format("%8s",
					F12.format(targetReference.hashCode()));*/

			String cazs="";
			for (AssociationCandidateType caze : cases)
				cazs+=CASE_NAMES[caze.ordinal()]+";";

			if (associationType == null)
				associationType = AssociationType.VOID_;

			String result =view+ "_"+cazs+"_";
			result+= TYPE_NAMES[associationType.ordinal()]+"_";
			//result += (isSourcePov() ? "spov " : "");
			result += (isSourceAbstract() ? "sa_" : "sc_");
			result += (isTargetAbstract() ? "ta " : "tc ")+"                ";
			result = result.substring(0, 30);
		    result += " - "
					+ getSource().getName()
					+ "-"
					+ getName()
					+ (getTarget() == null ? "~" : " -> "
							+ getTarget().getName());

					/*
		            + " tref("+targetReference.getName()
					+ " insts(" + getInstanceSource().getName() + ") -> "
					+ targetReference.getEType().getName() + ") ";	*/

		    return result;
		} catch (Exception e) {
			return "error";
		}
	}


	@Override
	public String toLogCompl() {


	//	 if (getType().isAbstract() && getTarget()!=null && getTarget().isAbstract() && getType() == getTarget())
	//		 tb = true;

		try {

			String result = toLog()+" (type="+getType().getName()+");";
			result +=  "(" + getInstanceSource().getName() + ") -> "
					+ targetReference.getEType().getName() + ";";
			result+="[";
			result+= sourcePov?"_pov":"";
			//result+= compartment_?"_k":"";
			//result+= recursive?"_r":"";
			//result+= port_?"_port":"";
			result+="]";

			if (!getTargetSubHierarchy().isEmpty()) {
				result += "subhierarchy=[ ";
				for (EClass subTarget : getTargetSubHierarchy()) {
					result += subTarget.getName()
							+ (subTarget.isAbstract() ? "(_abstr) ;" : " ;");
				}
				result += "]";
			}

			return result;
			//return (result + " " + result1).trim();
		} catch (Exception e) {
			return "error";
		}
	}


	public String toLog___() {

		try {
			// [ 142185096] PartofR (PartofR) -> Function.init3

			String id = String.format("[%11s]", F12.format(this.hashCode()));
			String srcid = String.format("%11s",
					F12.format(getSource().hashCode()));
			String trgid = String.format("%11s",
					F12.format(targetReference.hashCode()));

			String result = "classAssociation ";

			result += (isTargetAbstract() ? "targ_abstract " : "targ_concrete ")
					+ " - "
					+ getSource().getName()
					+ " - ref="
					+ getName()
					+ " - typ="
					+ getType().getName()
					+ " - "
					+ (getTarget() == null ? "~" : " -> "
							+ getTarget().getName())
					+ " ("
					+ (getInfo() == null ? "~" : getInfo()) + ")";

			if (!getTargetSubHierarchy().isEmpty()) {
				result += "\nsubhierarchy=[ ";
				for (EClass subTarget : getTargetSubHierarchy()) {
					result += subTarget.getName()
							+ (subTarget.isAbstract() ? "(_abstr) " : " ");
				}
				result += "]";
			}
			result = justifyRight(result, WBASE + 10, ' ');
			String result1 = id + "(" + srcid + "." + trgid + ") "
					+ getSource().getName() + "." + targetReference.getName()
					+ " (" + getInstanceSource().getName() + ") -> "
					+ targetReference.getEType().getName() + " ";

			// result = justifyRight(result,38,' ')+" ";
			result1 = (result1 + SPACES).substring(0, WBASE + 10);


/*
		String result2_ = (isTargetAbstract() ? "targetabstract-" : "targetconcrete-");
		result2_ += "subhierarchy=[ ";
		for (EClass subTarget : getTargetSubHierarchy()) {
			result2_ += subTarget.getName()
					+ (subTarget.isAbstract() ? "(_abstr) " : "(_concr) ");
		}
		result2_ += "]";
		result2_ = justifyRight(result2_,WBASE+10,' ');
*/

		return (result+'\n'+result1);
		} catch (Exception e) {
			return "error";
		}
	}



	//@Override
	public String toLog_old() {
		String result = "";
		try {
			result += (isTargetAbstract()?"targ_abstract ":"targ_concrete ")+
					" - "
					+getSource().getName() +
					" - ref="
					+ getName()
					 +
					" - typ=" + getType().getName()
					 +
					" - "
					+ (getTarget() == null ? "~" : " -> " + getTarget().getName()) + " ("
					+ (getInfo() == null ? "~" : getInfo()) + ")";
			result += "\nsubhierarchy=[ ";
			for (EClass subTarget : getTargetSubHierarchy()) {
				result += subTarget.getName()
						+ (subTarget.isAbstract() ? "(_abstr) " : " ");
			}
			result += "]";
		} catch (Exception e) {
			result += " ...error...";
		}
		return result.trim();
	}

	@Override
	public List<AssociationCandidateType> getCases() {
		return cases;

	}
	@Override
	public String toCsv() {
		//FP150520TODO Auto-generated method stub
		return "FP150520TODO";
	}
	@Override
	public String toCsvHeader() {
		//FP150520TODO Auto-generated method stub
		return "FP150520TODO";
	}





}
