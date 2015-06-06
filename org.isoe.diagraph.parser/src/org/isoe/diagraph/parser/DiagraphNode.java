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
import org.isoe.diagraph.parser.api.IDiagraphNode;
import org.isoe.diagraph.parser.api.IDiagraphParser;
import org.isoe.diagraph.parser.api.IDiagraphReferenceAssociation;
import org.isoe.fwk.core.DParams;

/**
 *
 * @author fpfister
 *
 */
public class DiagraphNode extends DiagraphElement implements IDiagraphNode {

	private static final boolean LOG = DParams.DiagraphNode_LOG;
	private EClass povIfSuperPov;
	private int depth;
	private String navigation;
	private EReference xcontref;
	private EReference altContainmentReference;
	private List logNodeMappings = new ArrayList();

	private List<EReference> autoCompartments;
	private List<EReference> abstractAutoCompartments_;
	private List<EReference> abstractAutoCompartmentBySupers;
	private List<EReference> autoCompartmentBySupers;
	private List<EReference> krefAltContainmentReferences;
	private List<EReference> directContainmentReferences;



	private List<EClass> superGenericsOrNodes;
	private List<EClass> subGenericsOrNodes;
	private boolean pov;
	private boolean directTopNode;
	private boolean inheritedTopNode;
	private boolean selfContained;
	private boolean selfContainedThroughInheritance;


	private List<IDiagraphNode> subNodes;
	private List<IDiagraphReferenceAssociation> allContainments;
	private List<IDiagraphReferenceAssociation> containments;
	private List<IDiagraphReferenceAssociation> allSimpleReferences;
	private List<IDiagraphReferenceAssociation> simpleReferences;
	private List<IDiagraphReferenceAssociation> allSiblingContainments;
	private IDiagraphReferenceAssociation createdContainment;
	private IDiagraphReferenceAssociation baseContainment;
	private IDiagraphReferenceAssociation tempRef;





	@Override
	public List<EReference> getAbstractAutoCompartments() {
		return abstractAutoCompartments_;
	}

	@Override
	public List<EReference> getAbstractAutoCompartmentBySupers() {
		return abstractAutoCompartmentBySupers;
	}

	@Override
	public List<EReference> getAutoCompartmentBySupers() {
		return autoCompartmentBySupers;
	}

	@Override
	public List<EReference> getAllContainmentReferences() {
		return allContainmentReferences_;
	}

	@Override
	public List<EClass> getSuperGenericsOrNodes() {
		return superGenericsOrNodes;
	}

	@Override
	public List<EClass> getSubGenericsOrNodes() {
		return subGenericsOrNodes;
	}

	@Override
	public EClass getPovIfSuperPov() {
		return povIfSuperPov;
	}

	@Override
	public List<EReference> getKrefAltContainmentReferences() {
		return krefAltContainmentReferences;
	}

	@Override
	public List<IDiagraphReferenceAssociation> getAllContainments() {
		return allContainments;
	}

	void setBaseContainment_(IDiagraphReferenceAssociation baseContainment) {
		this.baseContainment = baseContainment;
	}

	void setTempRef(IDiagraphReferenceAssociation temp) {
		this.tempRef = temp;
	}

	@Override
	public List<IDiagraphReferenceAssociation> getContainments() {
		return containments;
	}




	@Override
	public boolean isPov() {
		return pov;
	}

	@Override
	public List<IDiagraphReferenceAssociation> getAllSiblingContainments() {
		return allSiblingContainments;
	}

	@Override
	public List<EReference> getAutoCompartments() {
		return autoCompartments;
	}



	@Override
	public List<EReference> getDirectContainmentReferences() {
		return directContainmentReferences;
	}

	@Override
	public List<IDiagraphReferenceAssociation> getAllSimpleReferences() {
		return allSimpleReferences;
	}

	@Override
	public List<IDiagraphReferenceAssociation> getSimpleReferences() {
		return simpleReferences;
	}

	@Override
	public boolean isDirectTopNode() {
		return directTopNode;
	}

	@Override
	public boolean isInheritedTopNode() {
		return inheritedTopNode;
	}

	@Override
	public boolean isSelfContained() {
		return selfContained;
	}

	@Override
	public boolean isSelfContainedThroughInheritance() {
		return selfContainedThroughInheritance;
	}

	public DiagraphNode(IDiagraphParser parser, String view, EClass eClass, boolean derived) {
		super(parser, view,  eClass, derived);
	}

	void setKrefAltContainments(List<EReference> altContainments) {
		this.krefAltContainmentReferences = new ArrayList<EReference>();
		setAllRefs(krefAltContainmentReferences, altContainments, "KrefAltContainments");
	}

	void setDirectContainmentReferences_(
			List<EReference> directContainmentReferences) {
		this.directContainmentReferences = new ArrayList<EReference>();
		setAllRefs(directContainmentReferences, directContainmentReferences, "directContainmentReferences");
	}

	public void setSuperGenericsOrNode(List<EClass> superGenericsOrNodes) {
		this.superGenericsOrNodes = new ArrayList<EClass>();
		setAllClass(this.superGenericsOrNodes, superGenericsOrNodes, "superGenericsOrNodes");
	}



	public void setPovIfSuperPov(EClass povIfSuperPov) {
		this.povIfSuperPov = povIfSuperPov;
	}

	@Override
	public IDiagraphReferenceAssociation getRecursiveCompartment(
			EReference eReference) {
		return getParser().getRecursiveCompartment(getEClass(),
				eReference.getName());// FP1404qqqq
	}


	@Override
	public void setContainments(List<IDiagraphReferenceAssociation> containments) {
		this.containments = new ArrayList<IDiagraphReferenceAssociation>();
		setAll(this.containments,containments,"Containements");
	}

	public void setAllContainements(List<IDiagraphReferenceAssociation> containments) {

		allContainments = new ArrayList<IDiagraphReferenceAssociation>();
		setAll(this.allContainments,containments,"allContainements");
		if (allContainments.size() == 1)
			this.baseContainment = allContainments.get(0); // FP140417
		else
			setContainment(guessContainment());

		if (LOG)
			clog("baseContainement="
					+ (baseContainment == null ? "null" : baseContainment
							.toLog()));


	}


	private void setAllClass(List<EClass> claz, List<EClass> all, String label) {
		for (EClass clas : all) {
			if (!claz.contains(clas))
				claz.add(clas);
		}
		if (LOG) {
			clog(label +" for "+getName());
			for (EClass clas : claz)
				clog(clas.getName());
		}
	}


	private void setAll(List<IDiagraphReferenceAssociation> cts,List<IDiagraphReferenceAssociation> containments, String label) {
	    for (IDiagraphReferenceAssociation containment : containments) { //FP150518
			if (!cts.contains(containment))
				cts.add(containment);
		}

		if (LOG) {
			clog(label +" for "+getName());
			String log = associationsToStringLong_(cts, label);
			clog(log);
		}
	}



	public void setContainment(EReference ref) {
		if (ref != null){
			boolean simple_no = false;
			this.baseContainment = getParser().createReferenceAssociation(ref,simple_no);// getExternContainment();
		}else
			setPov();
	}


	@Override
	public void setContainmentAlt(EReference containement) {
		this.altContainmentReference = containement;
	}


	public void setAllSiblingContainements(
			List<IDiagraphReferenceAssociation> siblingContainements) {
		this.allSiblingContainments = new ArrayList<IDiagraphReferenceAssociation>();
		setAll(this.allSiblingContainments, siblingContainements, "allSiblingContainments");
	}

	void setContainements(List<IDiagraphReferenceAssociation> containements) {
		this.containments = new ArrayList<IDiagraphReferenceAssociation>();
		setAll(this.containments, containements, "containements");
	}

	void setAbstractAutoCompartments(List<EReference> refs) {
		this.abstractAutoCompartments_ = new ArrayList<EReference>();
		setAllRefs(this.abstractAutoCompartments_, refs, "abstractAutoCompartments");
	}

	public void setAbstractAutoCompartmentBySupers(List<EReference> refs) {
		this.abstractAutoCompartmentBySupers = new ArrayList<EReference>();
		setAllRefs(this.abstractAutoCompartmentBySupers, refs, "abstractAutoCompartmentBySupers");
	}

	public void setAutoCompartmentBySupers(List<EReference> refs) {
		this.autoCompartmentBySupers = new ArrayList<EReference>();
		setAllRefs(this.autoCompartmentBySupers, refs, "autoCompartmentBySupers");
	}

	void setAutoCompartments(List<EReference> refs) {
		this.autoCompartments = new ArrayList<EReference>();
		setAllRefs(this.autoCompartments, refs, "autoCompartments");
	}

	void setDirectTopNode(boolean directTopNode) {
		this.directTopNode = directTopNode;
	}

	void setInheritedTopNode(boolean inheritedTopNode) {
		this.inheritedTopNode = inheritedTopNode;
	}

	void setSelfContained(boolean selfContained) {
		this.selfContained = selfContained;
	}

	void setSelfContainedThroughInheritance(
			boolean selfContainedThroughInheritance) {
		this.selfContainedThroughInheritance = selfContainedThroughInheritance;
	}

	private String referenceToString(EReference ref, String label) {
		String result = ref == null ? "" : (label + "=" + ref == null ? "null"
				: ref.getName());
		if (!result.isEmpty())
			result += "\n";
		return result;
	}

	private String eClassToString(EClass cl, String label) {
		String result = cl == null ? "" : (label + "=" + cl.getName());
		if (!result.isEmpty())
			result += "\n";
		return result;
	}



	private String referencesToString(List<EReference> refs, String label) {
		String result = "";
		if (!refs.isEmpty()) {
			result += (label + " for " + getName() + ": ");
			for (EReference eReference : refs) {
				result += " " + eReference.getName() + " -";
			}
		} // else
			// result += ("no " + label + " for " + getName());
		if (!result.isEmpty())
			result += "\n";
		return result;
	}

	private String associationsToStringLong_(
			List<IDiagraphReferenceAssociation> assocs, String label) {

		String result = "";
		if (!assocs.isEmpty()) {
			result += ("assocs "+label + " for " + getName() + ": ");
			for (IDiagraphReferenceAssociation assoc : assocs) {
				result += "[" + assoc.toId() + "] -";
			}
		} // else
			// result += ("no " + label + " for " + getName());
		if (!result.isEmpty())
			result += ";";
		return result;
	}

	private String referencesToStringShort(List<EReference> refs, String shortLabel,String label_) {
		String result = "";
		if (!refs.isEmpty()) {
			result += shortLabel+"(";
			for (EReference eReference : refs)
				result += "<" + eReference.getName() + ">-";
			result = result.substring(0, result.length()-1);
			result += ");";
		}
		return result;
	}

	private String associationsToStringShort(
			List<IDiagraphReferenceAssociation> assocs, String shortLabel,String label_) {
		String result = "";
		if (!assocs.isEmpty()) {

			result += shortLabel+"(";
			for (IDiagraphReferenceAssociation assoc : assocs)
				result += "[" + assoc.toId() + "]-";
			result = result.substring(0, result.length()-1);
			result += ");";
		}
		return result;
	}

	private String associationToString_(IDiagraphReferenceAssociation assoc,
			String label) {
		return label + " for " + getName() + ": "
				+ (assoc == null ? "null" : assoc.toLog());
	}

	private String eclassesToString(List<EClass> eclasses, String label) {
		String result = "";
		if (!eclasses.isEmpty()) {
			result += (label + " for " + getName() + ": ");
			for (EClass eclaz : eclasses) {
				result += " " + eclaz.getName() + " -";
			}
		}// else
			// result += ("no " + label + " for " + getName());
		if (!result.isEmpty())
			result += "\n";
		return result;
	}

	@Override
	public void setPov() {
		this.pov = true;
	}


	@Override
	public String handleSelfCompartmentContainementPattern() {
		if (isSelfContained() || isSelfContainedThroughInheritance()) {
			IDiagraphReferenceAssociation cont = getParser()
					.getBaseContainment(getEClass());
			if (cont == null)
				return ("containment is null for " + toString() + "\n");
			else {
				if (LOG)
					clog("handleSelfCompartmentContainement: "
							+ getEClass().getName() + ".baseContainment="
							+ cont.toLog());
				setBaseContainment_(cont);
			}
		}
		return "";
	}

	@Override
	public EReference getDeclaredContainment() {
		return super.getDeclaredContainment();
	}

	@Override
	public EReference getContainmentBase_() {// FP140416
		return (baseContainment == null) ? null : baseContainment
				.getTargetReference();
	}

	@Override
	public EReference getContainmentAlt() { // FP140416
		EReference base = getContainmentBase_();
		List<EReference> allconts = getAllContainmentReferences();
		if (allconts.size() == 2)
			for (EReference eReference : allconts)
				if (eReference != base)
					return eReference;
		return null;
	}

	private EReference guessContainment() { // FP140417

		List<EReference> allconts = getAllContainmentReferences();
		if (allconts.size() == 1)
			return allconts.get(0);
		EClass eclaz = getEClass();
		if (allconts.size() == 2)
			for (EReference eReference : allconts) {
				EClass rt = (EClass) eReference.eContainer();
				if (eReference.eContainer() != eclaz)
					return eReference;
			}
		return null;
	}

	public String handleSimpleCompartmentContainementPattern_nu() {
		if (isSelfContained() || isSelfContainedThroughInheritance()) {
			IDiagraphReferenceAssociation cont = getParser()
					.getBaseContainment(getEClass());
			if (cont == null)
				return ("containment is null for " + toString() + "\n");
			else {
				if (LOG)
					clog("handleSimpleCompartmentContainement: "
							+ getEClass().getName() + ".baseContainment="
							+ cont.toLog());
				setBaseContainment_(cont);
			}
		}
		return "";
	}

	/*
	 * @Override public void setDepth_(int depth) { this.depth = depth; }
	 */



	private void checkCanvasInheritance() {
		if (!isAbstract() && !isCanvas())
			for (IDiagraphNode sub : getSubNodes())
				if (sub.isCanvas())
					;//throw new RuntimeException(
						//	"the canvas should not inheritate from a concrete node !!!");

	}


	@Override
	public boolean isOrSubNodeCanvas() {
		if (isCanvas())
			return true;
		checkCanvasInheritance();
		for (IDiagraphNode sub : getSubNodes())
			if (sub.isCanvas())
				return true;
		return false;
	}



	@Override
	public boolean isCanvas() {
		return depth == -1;
	}


	@Override
	public IDiagraphNode getCanvasContainment() {//TODO simplify
		IDiagraphParser p = getParser();
		List<EReference>  refsToMe = p.getDirectReferencesTo(getEClass());
		for (EReference refToMe : refsToMe) {
			boolean containment = (((EClass) refToMe.getEType())
					.isSuperTypeOf(getEClass()) && refToMe
					.isContainment());
			if (containment) {
				IDiagraphNode element = p.findNode_((EClass) refToMe
						.eContainer());
				if (element != null
						&& element.isCanvas())
					return element;
			}
		}
		return null;
	}

	@Override
	public int getDepth() {
		return depth;
	}

	@Override
	public String getChildInfo() {
		String result = "??? TODO";
		try {
			EReference base = baseContainment.getTargetReference();
			EClass modelElement = baseContainment.getTarget();
			result = (depth == 0 ? "TOP " : "CHILD ")
					+ ((EClass) base.eContainer()).getName() + "."
					+ base.getName() + ":" + ((EClass) modelElement).getName();
		} catch (Exception e) {
			getParser().cerror("error in getChildInfo");
		}
		return result;
	}

	@Override
	public EReference getActualContainment() {
		EReference result = getContainmentBase_();
		EReference containmentAlt = getContainmentAlt();
		if (depth > 0 && containmentAlt != null)
			result = containmentAlt;
		return result;
	}




	public IDiagraphReferenceAssociation getContainment(IDiagraphAssociation.AssociationType containmentType_,
			IDiagraphNode trgnode, EReference er, String name, boolean isPropagated,
			boolean derived) {// boolean
		// isPort,

		IDiagraphReferenceAssociation nodecont = findContainmentByTargetName(trgnode
				.getName());
		EClass srcclass = (EClass) getEClass();
		if (nodecont == null) {
			nodecont = getParser().createContainment(containmentType_, srcclass,
					trgnode.getName(), name, isPropagated, derived);// isPort,
			// nodecont = parser.createNodeContainment_(srcclass,
			// trgnode.getName(), name, isPort, isPropagated);
			nodecont.setTargetReference(er);
			nodecont.setTargetNode(trgnode);
		}
		return nodecont;
	}


	@Override
	public void hook_(String caze, String mesg) {
		boolean check;
		if (caze.contains("createNodeMapping")
				&& mesg.equals("CHILD Root.topDs:Dddd"))
			check = true; // FP140415
	}

	@Override
	public void createMapping() {

	}


	@Override
	public void addNodeMapping(Object nodeMapping, Object childReference) {

		if (LOG){
			this.logNodeMappings.add(nodeMapping);
			clog(nodeMapping.toString());
		}

	}

	protected void clog(String mesg) {
		if (LOG){

			System.out.println(mesg);
		}
	}

	@Override
	public String toLogCompl() {
		return toString();
	}

	@Override
	public String toLog() {
		String result = "";
		result += ("name=" + getName() + "; ");
		result += "depth=" + depth + "; ";
		result += "view=" + getView() + "; ";

		//result += " depth=" + depth + "; ";
		result += directTopNode ? " directTopNode;" : "; ";
		result += inheritedTopNode ? " inheritedTopNode;" : "; ";
		result += selfContainedThroughInheritance ? " selfContainedThroughInheritance "
				: "; ";
		result += selfContained ? " selfContained; " : "; ";
		result += pov ? " pov; " : "; ";
		result += isGeneric() ? " generic; " : "; ";
		result += isAbstract() ? " abstract; " : "; ";


		result += referencesToStringShort(allContainmentReferences_,"acr","allContainmentReferences");
		result += associationsToStringShort(containments, "con","containments");
		result += associationsToStringShort(allSimpleReferences,"asr","allSimpleReferences");
		result += associationsToStringShort(allSiblingContainments,"asc","allSiblingContainments"); //FP150324add

		return result;
	}

	@Override
	public String toString() {
		String result = "";
		result += ("----" + getName() + "----\n");
		result += "depth=" + depth + "\n";
		result += "view=" + getView() + "\n";

		if (!getEClass().isAbstract() && directContainmentReferences.size() == 0 && getAllContainmentReferences().size() == 0)
			result += (getName() + " is a pov candidate for the view "+getView()+" \n");

		result += eClassToString(getEClass(), "eClass");
		result += eClassToString(povIfSuperPov, "povIfSuperPov");

		result += referenceToString(getDeclaredContainment(),
				"declaredContainment");
		result += referencesToString(autoCompartments, "autoCompartments");
		result += referencesToString(abstractAutoCompartments_,
				"abstractAutoCompartment");
		result += referencesToString(abstractAutoCompartmentBySupers,
				"abstractAutoCompartmentBySuper");
		result += referencesToString(autoCompartmentBySupers,
				"autoCompartmentBySuper");
		result += referencesToString(directContainmentReferences,
				"directContainmentReferences");
		result += referencesToString(allContainmentReferences_,
				"allContainmentReferences");
		result += referencesToString(krefAltContainmentReferences,
				"_krefAltContainmentReferences");
		result += associationsToStringLong_(allContainments, "allContainments")+"\n";
		result += associationsToStringLong_(containments, "containments")+"\n";
		result += associationsToStringLong_(allSimpleReferences,
				"allSimpleReferences")+"\n";
		result += associationsToStringLong_(simpleReferences, "simpleReferences")+"\n";
		result += associationsToStringLong_(allSiblingContainments,
				"allSiblingContainments")+"\n";
		result += associationToString_(baseContainment, "baseContainment")+"\n";
		result += associationToString_(tempRef, "tempRef")+"\n";
		result += eclassesToString(superGenericsOrNodes, "superGenericsOrNodes");
		result += eclassesToString(subGenericsOrNodes, "subGenericsOrNodes");

		String status = "\n";
		status += " depth=" + depth + " ";
		status += directTopNode ? " directTopNode " : "";
		status += inheritedTopNode ? " inheritedTopNode " : "";
		status += selfContainedThroughInheritance ? " selfContainedThroughInheritance "
				: "";
		status += selfContained ? " selfContained " : "";
		status += pov ? " pov " : "";
		status += isGeneric() ? " generic " : "";
		status += isAbstract() ? " abstract " : "";

		result += status.trim();

		return result;
	}

	/*
	 * @Override public IDiagraphParser getParser() { return super.getParser();
	 * }
	 */

	public int computeDepth() {//FP140417

		if (LOG){



			clog("computeDepth "+getName());
		//	if (getName().equals("Base"))
		//		 tb = false;
			EClass pov= getParser().getPointOfViewClass(getEClass().getEPackage());
			clog("pov= "+pov.getName());
		}
		//EClass pov= getParser().getPointOfViewClass(getEClass().getEPackage());
		/*
		if (pov==this.getEClass()){ //FP150428
			depth = 0;
			return depth;
		} else
		*/
		{
		    depth = computeDepth(getEClass(), getParser()
				.getPointOfViewClass(getEClass().getEPackage()), 0);
		    return depth;
		}
	}

	private int computeDepth_old_nu(EClass claz, EClass pov, int depth) { //FP140417
		if (depth > 200)
			throw new RuntimeException("loop error in computeDepth");
		if (isPov())
			 return 0; //FP150428
		for (EReference containementReference : getParser().parseAllContainementReferences(claz))
			if (containementReference.isContainment()) {
				EClass contClass = (EClass) containementReference.eContainer();
				if (contClass != null && getParser().isNode(contClass))
					if (contClass == pov)
						return depth;
					else
						return computeDepth(contClass,  pov, ++depth);
			}
		return -1;
	}




	private int computeDepth(EClass claz, EClass pov, int depth) { //FP150428
	    if (LOG){

	    	String l=claz.getName()+"-"+pov.getName()+"-"+depth;

	    	clog("computeDepth "+l);
	    }

		if (depth > 200)
			throw new RuntimeException("loop error in computeDepth");
		if (isPov())
			 return 0; //FP150428
		for (EReference containementReference : getParser().parseAllContainementReferences(claz)){
			if (containementReference.isContainment()) {
				EClass contClass = (EClass) containementReference.eContainer();
				if (contClass != null && getParser().isNode(contClass))
					if (contClass == pov)
						return depth;

			}
		}
		for (EReference containementReference : getParser().parseAllContainementReferences(claz))
			if (containementReference.isContainment()) {
				EClass contClass = (EClass) containementReference.eContainer();
				if (contClass != null && !contClass.isSuperTypeOf(claz) && getParser().isNode(contClass) )//FP150516azerazer && !contClass.isSuperTypeOf(claz)
						return computeDepth(contClass,  pov, ++depth);
			}
		return -1;
	}

/****************************************************************/


	@Override
	public IDiagraphReferenceAssociation findContainmentByTargetName(String label) {
		//FP140421aaa
		return null;
	}

	@Override
	public void addContainment(int i, IDiagraphAssociation containment, int j,
			boolean doAdd) {
		//FP140421aaa

	}
/*
	@Override
	public List<EReference> getContainments1() {
		// TODO Auto-generated method stub
		return null;
	}*/

	@Override
	public List<IDiagraphNode> getSubNodes() {
       return subNodes;
	}



	public void setSubGenericsOrNode(List<EClass> subGenericsOrNodes) {
		if (LOG)
			clog("setSubGenericsOrNode");
		this.subGenericsOrNodes = new ArrayList<EClass>();
		setAllClass(this.subGenericsOrNodes, subGenericsOrNodes, "subGenericsOrNodes");


		this.subNodes = new ArrayList<IDiagraphNode>();

		if (LOG)
			clog("subNodes "+this.getName());
		for (EClass subGenericsOrNode : subGenericsOrNodes){
			IDiagraphNode nod= getParser().getDiagraphNode(subGenericsOrNode,0);
			if(!subNodes.contains(nod)){
			   subNodes.add(nod);
			   if (LOG)
					clog(nod.getName());
			}
		}
	}

	/*
	public List<EClass> getSubClasses() {
		return getSubGenericsOrNodes();
	}

	*/


	@Override
	public IDiagraphNode getContainer() {
		throw new RuntimeException("TODO inplement getContainer");
	}

	@Override
	public IDiagraphNode getSourceNode() {
		throw new RuntimeException("TODO inplement getSourceNode");
	}

	@Override
	public void addDerivedSubNodes(List<IDiagraphNode> subnodes) {
		throw new RuntimeException("TODO inplement addDerivedSubNodes");
	}

	@Override
	public boolean isCompartment() {
		throw new RuntimeException("TODO inplement isCompartment");
	}

	@Override
	public boolean isPort() {
		throw new RuntimeException("TODO inplement isPort");
	}

	@Override
	public List<IDiagraphNode> getSuperElements() {
		throw new RuntimeException("TODO inplement getSuperElements");
	}

	@Override
	public EReference getContainerReference() {
		throw new RuntimeException("TODO inplement getContainerReference");
	}


	@Override
	public void setCreatedContainment(IDiagraphReferenceAssociation createdContainment) {
		this.createdContainment = createdContainment;
	}

	@Override
	public IDiagraphReferenceAssociation getCreatedContainment() {
		return createdContainment;
	}

	@Override
	public void setNavigation(String navigation) {
		this.navigation = navigation;
	}

	@Override
	public String getNavigation() {
		return navigation;
	}

	@Override
	public void setXtraDeclaredContainmentReference_nu(EReference xcontref) {
		this.xcontref = xcontref;
	}

	@Override
	public void setContainmentName(String containementName) {
		if (DParams.TODO_LOG_)
			getParser().cerror("TODO implement setContainmentName");
	}




	void setAllSimpleReferences(List<IDiagraphReferenceAssociation> rafs, EClass eclaz) {

		this.allSimpleReferences = new ArrayList<IDiagraphReferenceAssociation>();
		this.allSimpleReferences.addAll(rafs);
	    for (IDiagraphReferenceAssociation raf : rafs)
	    	 raf.addSimple(eclaz);
		if (LOG) {
			clog("AllSimpleReferences");
			for (IDiagraphReferenceAssociation containment : allSimpleReferences)
				clog(containment.toLog());
		}
	}

	void setSimpleReferences(List<IDiagraphReferenceAssociation> rafs, EClass eclaz) {
		this.simpleReferences = new ArrayList<IDiagraphReferenceAssociation>();
		this.simpleReferences.addAll(rafs);
	    for (IDiagraphReferenceAssociation raf : rafs)
	    	raf.addSimple2(eclaz);
		if (LOG) {
			clog("SimpleReferences");
			for (IDiagraphReferenceAssociation containment : simpleReferences)
				clog(containment.toLog());
		}
	}





	/*
	 * @Override public int getRecursiveDepth() { return _mappings.size(); }
	 */

}
