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
import org.isoe.diagraph.parser.api.IDiagraphNode;
import org.isoe.diagraph.parser.api.IDiagraphParser;
import org.isoe.diagraph.parser.api.IDiagraphReferenceAssociation;

//import org.isoe.diagraph.parser.api.IDiagraphReferenceAssociation.ContainmentType;

/**
 *
 * @author fpfister
 *
 */
public class DiagraphReferenceAssociation extends DiagraphAssociation implements
		IDiagraphReferenceAssociation {

	private EReference declaredContainment;
	private int declaredCase;
	private String targetName;
	private boolean compartment;
	private boolean port;
	private boolean recursive;
	private boolean simple;
	private List<EClass> siblings = new ArrayList<EClass>();
	private List<EClass> targetAbstracts_ = new ArrayList<EClass>();
	private List<EClass> simples = new ArrayList<EClass>();
	private List<EClass> simple2s = new ArrayList<EClass>();
	private List<EClass> krefBySupers = new ArrayList<EClass>();
	private List<EClass> krefBySubs = new ArrayList<EClass>();
	private List<EClass> krefContainmentAlts = new ArrayList<EClass>();
	private List<EClass> subtargets = new ArrayList<EClass>();
	private EClass superTarget;
	private String argument;


	public List<EClass> getSubTargets() {
		return subtargets;
	}

	public EClass getSuperTarget() {
		return superTarget;
	}


	@Override
	public void resolveTargetReference() {
		throw new RuntimeException("TODO implement resolveTargetReference");
	}


	@Override
	public void resolveTargets() {
		if (targetNode == null){
			if (LOG)
				clog(" not implemented: ");
			  return;
		}
		EReference targref = getTargetReference();
		if (targref != null) {
			EClass reftyp = (EClass) targref.getEType();
			if (reftyp == targetNode.getEClass()) {
				if (LOG)
					clog(" abstract, subtargets are: ");
				for (EClass subtarget : parser.getConcreteSubNodes_(reftyp)) {
					subtargets.add(subtarget);
					if (LOG)
						clog(subtarget.getName());
				}
			} else if (reftyp.isSuperTypeOf(targetNode.getEClass())) {
				superTarget = reftyp;
				if (LOG)
					clog(" abstract, is a subtarget of: " + reftyp.getName());
			}
		}
	}

	public DiagraphReferenceAssociation(IDiagraphParser parser, String view,
			EClass refsource, EClass refinstancesource, EClass target,
			List<EClass> targetSubHierarchy, EReference eReference,
			AssociationType assoType, boolean derived, boolean simple,String info) {
		super(parser, view, refsource, refinstancesource, target, eReference,
				targetSubHierarchy, assoType, derived, info);
		this.simple=simple;
		String s = getSource().getName() + "->" + eReference.getName();
	}



	public boolean isSimple() {
		return simple;
	}

	@Override
	public boolean isRefContainment() {
		return targetReference.isContainment();
	}

	@Override
	public boolean isCompartment() {
		return associationType == AssociationType.TYPED_COMPARTMENT;
	}

	@Override
	public boolean isPort() {
		return associationType == AssociationType.AT_PORT;
	}

	@Override
	public boolean isContainment() {
		return associationType == AssociationType.AT_CONTAINMENT;
	}

	@Override
	public boolean isSharedCompartment() {
		return associationType == AssociationType.SHARED_COMPARTMENT_;
	}

	@Override
	public boolean isExternal() {
		return associationType == AssociationType.EXTERNAL;
	}

	public String toLog_old() {
		int WBASE = 40;
		try {
			// [ 142185096] PartofR (PartofR) -> Function.init3

			String id = String.format("[%11s]", F12.format(this.hashCode()));
			String srcid = String.format("%11s",
					F12.format(getSource().hashCode()));
			String trgid = String.format("%11s",
					F12.format(targetReference.hashCode()));

			String result1 = id + "(" + srcid + "." + trgid + ") "
					+ getSource().getName() + "." + targetReference.getName()
					+ " (" + getInstanceSource().getName() + ")->"
					+ targetReference.getEType().getName() + " ";

			// result = justifyRight(result,38,' ')+" ";
			result1 = (result1 + "                     ").substring(0,
					WBASE + 10);

			String result2 = (isTargetAbstract() ? "targetabstract-"
					: "targetconcrete-");
			// result2 += (isTargetAbstract() ? "ycont " : "ncont ");
			result2 += "subhierarchy=[ ";
			for (EClass subTarget : getTargetSubHierarchy()) {
				result2 += subTarget.getName()
						+ (subTarget.isAbstract() ? "(_abstr) " : "(_concr) ");
			}
			result2 += "]";
			result2 = justifyRight(result2, WBASE + 10, ' ');

			return (result1 + result2).trim();
		} catch (Exception e) {
			return "error";
		}
	}

	/**
	 * dca_ta - Region-subElements-> Region dca_ta - Region-subElements-> State
	 * dca_ta - Region-subElements-> Foo
	 *
	 *
	 * direct contaiment assoc:erenceAssociation targ_abstract - Region -
	 * ref=subElements - typ=AbstractState - -> Region (~) subhierarchy=[ Region
	 * State Foo ] [19289587]( 3291836.23254930) Region.subElements (Region) ->
	 * AbstractState direct contaiment assoc:ssociation targ_abstract - Region -
	 * ref=subElements - typ=AbstractState - -> AbstractState (~) subhierarchy=[
	 * Region State Foo ] [11787458]( 3291836.23254930) Region.subElements
	 * (Region) -> AbstractState direct contaiment assoc:ferenceAssociation
	 * targ_abstract - Region - ref=subElements - typ=AbstractState - -> State
	 * (~) subhierarchy=[ Region State Foo ] [28162694]( 3291836.23254930)
	 * Region.subElements (Region) -> AbstractState direct contaiment
	 * assoc:referenceAssociation targ_abstract - Region - ref=subElements -
	 * typ=AbstractState - -> Foo (~) subhierarchy=[ Region State Foo ]
	 * [13005130]( 3291836.23254930) Region.subElements (Region) ->
	 * AbstractState
	 */

	public void valid_nu() {


	}

	@Override
	public String toString() {
		return toLog();
	}

	@Override
	public String toId() { // FP150322
		String result = getSource().getName()
				+ "-"
				+ getName()
				+ (getTarget() == null ? "~" : ("->" + getTarget().getName()));
		return result;
	}

	@Override
	public String toLog() { // FP150322
		try {
			/*
			 * String id = String.format("[%8s]", F12.format(this.hashCode()));
			 * String srcid = String.format("%8s",
			 * F12.format(getSource().hashCode())); String trgid =
			 * String.format("%8s", F12.format(targetReference.hashCode()));
			 */

			String cazs = "";
			for (AssociationCandidateType caze : cases)
				cazs += CASE_NAMES[caze.ordinal()] + "-";

			if (associationType == null)
				associationType = AssociationType.VOID_;

			String result = view + "_" + cazs + "_";
			result += TYPE_NAMES[associationType.ordinal()] + "_";
			// result += (isSourcePov() ? "spov " : "");
			result += (isSourceAbstract() ? "sa_" : "sc_");
			result += (isTargetAbstract() ? "ta " : "tc ") + "                ";
			result = result.substring(0, 30);
			result = "("+getInstanceSource().getName()+") "+ toId()+" "+result;
			//result += " " + toId();
			return result;
		} catch (Exception e) {
			return "error";
		}
	}

	private String eclazes(List<EClass> eclazes, String label){
		String result = label+"[";
		if (!eclazes.isEmpty()) {
			for (EClass cl : eclazes)
				result += cl.getName() + "-";
			result = result.substring(0, result.length() - 1);
		}
		result += "];";
		return result;
	}


	@Override
	public String toCsvHeader() {
		String result ="simple;type;source;instanceSource;targetReference;target";
		result += ";sourcePov";
		result += ";compartment";
		result += ";recursive";
		result += ";port";
		result += ";argument";
		return result;
	}

	@Override
	public String toCsv() {
		String result = "error";

		//IDiagraphNode sourceNode;
		//IDiagraphNode targetNode;
		String typ= TYPE_NAMES[this.associationType.ordinal()];

		String typ_nu="VOID";
		switch (this.associationType) {
		case SIMPLE:
			typ="SIMPLE";
			break;
		case CLASSOC_:
			typ="CLASSOC";
			break;
		case TYPED_COMPARTMENT:
			typ="TYPED_COMPARTMENT";
			break;
		case SHARED_COMPARTMENT_:
			typ="SHARED_COMPARTMENT";
			break;
		case AT_CONTAINMENT:
			typ="CONTAINMENT";
			break;
		case AT_PORT:
			typ="PORT";
			break;
		case EXTERNAL:
			typ="EXTERNAL";
			break;

		case VOID_:
			typ="VOID";
			break;
		default:
			typ="UNKNOWN";
			break;
		}


		if ((sourceNode!=null && sourceNode.getName()!=source.getName())
				||(targetNode!=null && targetNode.getName()!=target.getName())
				)
			throw new RuntimeException("should not happen in toCsv");
		try {
			result = (simple?"s":"")+";"+typ+";"+source.getName()+";"+instanceSource.getName()+";"+targetReference.getName()+";"+target.getName();

			result += sourcePov ? "pov;" : ";";
			result += compartment ? "kref;" : ";";
			result += recursive ? "recurs;" : ";";
			result += port ? "port;" : ";";
			result += argument!=null ? argument : ";";

		} catch (Exception e) {
			result = "error;"+e.toString();
		}
			/*
		protected boolean sourcePov;
		protected IDiagraphNode sourceNode;
		protected IDiagraphNode targetNode;
		protected EClass source; // may be abstract
		protected EClass instanceSource; // if source is abstract
		protected EClass target;
		protected List<EClass> targetSubHierarchy;
		protected EReference targetReference;*/
		return result;
	}


	@Override
	public String toLogCompl() {
		try {

			String result = toLog() + " (type=" + getType().getName() + ");";
			result += "(" + getInstanceSource().getName() + ")->"
					+ targetReference.getEType().getName() + ";";
			result += "cazes[";
			result += sourcePov ? "_pov;" : ";";
			result += compartment ? "_k;" : ";";
			result += recursive ? "_r;" : ";";
			result += port ? "_port;" : ";";
			result += argument!=null ? ("_arg="+argument) : ";";
			result += "];";
	        result+= eclazes(getSiblings(), "siblings");
	        result+= eclazes(getTargetAbstracts(), "targ_abstracts");
	        result+= eclazes(getSimples(), "simples");
	        result+= eclazes(getSimple2s(), "simple2s");
	        result+= eclazes(getKrefBySupers(), "_krefBySupers");
	        result+= eclazes(getKrefBySubs(), "_krefBySubs");
	        result+= eclazes(getKrefContainmentAlts(), "_krefContainmentAlts");



			result += "subhierarchy=[ ";
			if (!getTargetSubHierarchy().isEmpty()) {
				for (EClass subTarget : getTargetSubHierarchy())
					result += subTarget.getName()
							+ (subTarget.isAbstract() ? "(_abstr)-" : "-");
				result = result.substring(0, result.length() - 1);
			}
			result += "];";

			return result;
			// return (result + " " + result1).trim();
		} catch (Exception e) {
			return "error";
		}
	}

	public String toLogComplold() {

		// if (getType().isAbstract() && getTarget()!=null &&
		// getTarget().isAbstract() && getType() == getTarget())
		// tb = true;

		try {
			// [ 142185096] PartofR (PartofR) -> Function.init3

			String id = String.format("[%11s]", F12.format(this.hashCode()));
			String srcid = String.format("%11s",
					F12.format(getSource().hashCode()));
			String trgid = String.format("%11s",
					F12.format(targetReference.hashCode()));

			String result = "referenceAssociation ";

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

			return (result + '\n' + result1);
			// return (result + " " + result1).trim();
		} catch (Exception e) {
			return "error";
		}
	}

	public String toLogOld() {

		// if (getType().isAbstract() && getTarget()!=null &&
		// getTarget().isAbstract() && getType() == getTarget())
		// tb = true;

		try {
			// [ 142185096] PartofR (PartofR) -> Function.init3

			String id = String.format("[%11s]", F12.format(this.hashCode()));
			String srcid = String.format("%11s",
					F12.format(getSource().hashCode()));
			String trgid = String.format("%11s",
					F12.format(targetReference.hashCode()));

			String result = "referenceAssociation ";

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

			return (result + '\n' + result1);
			// return (result + " " + result1).trim();
		} catch (Exception e) {
			return "error";
		}
	}

	public String toString_nu() {
		String result = "";
		try {
			result += (isTargetAbstract() ? "sub " : "dir ")
					+ " - "
					+ (isRefContainment() ? "cont " : "ncont ")
					+ " - src="
					+ getSource().getName()
					+ " - ref="
					+ getName()
					+ " - typ="
					+ getType().getName()
					+ " - "
					+ (getTarget() == null ? "~" : " -> "
							+ getTarget().getName()) + " ("
					+ (getInfo() == null ? "~" : getInfo()) + ")";
			result += "\nsubhier=[ ";
			for (EClass subTarget : getTargetSubHierarchy()) {
				result += subTarget.getName()
						+ (subTarget.isAbstract() ? "(_a) " : " ");
			}
			result += "]";
		} catch (Exception e) {
			result += " ...error...";
		}
		return result.trim();
	}

	/*
	 * @Override public EReference getEReference() { return eReference; }
	 */
	@Override
	public EClass getType() {
		return (EClass) targetReference.getEType();
	}

	@Override
	public String getName() {
		return targetReference.getName();
	}

	@Override
	public void setDeclaredContainment(EReference declaredContainment) {
		this.declaredContainment = declaredContainment;
	}

	@Override
	public void setDeclaredCase(int declaredCase) {
		this.declaredCase = declaredCase;
	}

	@Override
	public String getTargetName() {
		return targetReference.getName();
	}

	@Override
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	@Override
	public void setCompartment(boolean compartment) {
		this.compartment = compartment;
	}

	@Override
	public void setPort(boolean port) {
		this.port = port;
	}

	/*
	 * @Override public void setTargetReference(EReference reference) {
	 * this.eReference = reference; }
	 */

	@Override
	public int getDepth() {
		return 0;
	}

	@Override
	public void setRecursive() {
		recursive = true;

	}

	@Override
	public boolean isRecursive() {
		return recursive;
	}

	@Override
	public void addSibling(EClass sibling) {
		if (!siblings.contains(sibling))
			siblings.add(sibling);
	}

	@Override
	public List<EClass> getSiblings() {
		return siblings;
	}

	@Override
	public void addTargetAbstract_(EClass targetAbstract) {
		if (!targetAbstracts_.contains(targetAbstract))
		    targetAbstracts_.add(targetAbstract);
	}

	@Override
	public List<EClass> getTargetAbstracts() {
		return targetAbstracts_;
	}

	@Override
	public void addSimple(EClass simple) {
		if (!simples.contains(simple))
		   this.simples.add(simple);
	}

	@Override
	public List<EClass> getSimples() {
		return simples;
	}

	@Override
	public void addSimple2(EClass simple2) {
		if (!simple2s.contains(simple2))
		  this.simple2s.add(simple2);
	}

	@Override
	public List<EClass> getSimple2s() {
		return simple2s;
	}

	@Override
	public void addKrefBySuper(EClass krefBySuper) {
		if (!krefBySupers.contains(krefBySuper))
		  krefBySupers.add(krefBySuper);
	}

	@Override
	public List<EClass> getKrefBySupers() {
		return krefBySupers;
	}

	@Override
	public void addKrefBySub(EClass krefBySub) {
		if (!krefBySubs.contains(krefBySub))
		  krefBySubs.add(krefBySub);
	}

	@Override
	public List<EClass> getKrefBySubs() {
		return krefBySubs;
	}

	@Override
	public void addKrefContainmentAlt(EClass krefContainmentAlt) {
		if (!krefContainmentAlts.contains(krefContainmentAlt))
			krefContainmentAlts.add(krefContainmentAlt);
	}

	@Override
	public List<EClass> getKrefContainmentAlts() {
		return krefContainmentAlts;
	}

	@Override
	public boolean isPropagated() {
		return false;
	}

	@Override
	public void setArgument(String arg) {
		this.argument = arg;
	}

	@Override
	public String getArgument() {
		return argument;
	}





}
