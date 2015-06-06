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
package org.isoe.diagraph.internal.m2.parser;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.isoe.diagraph.internal.api.DPhase;
import org.isoe.diagraph.internal.m2.DiaContainedElement;
import org.isoe.diagraph.internal.m2.DiaGraph;
import org.isoe.diagraph.internal.m2.DiaLink;
import org.isoe.diagraph.internal.m2.DiaNode;
import org.isoe.diagraph.internal.m2.api.IDiaContainment;
import org.isoe.diagraph.internal.m2.parser.DStatement.DCommand_;
import org.isoe.fwk.core.DParams;

/**
 *
 * @author pfister
 *
 */
public class DTokenizer {

	private static final boolean LOG = DParams.DTokenizer_LOG;




	private static DCommand_ getCommand_(String command, DPhase phase) {

		if (phase == DPhase.CANVAS) {
			if (DAnnotation.POINT_OF_VIEW.equals(command)   ) //FP121022kkk
				return DCommand_.GRAPH_CREATE;
			else if (DAnnotation.NODE.equals(command))
				return DCommand_.GRAPH_NODE_;
			else if (DAnnotation.CONTAINMENT_CMD_.equals(command))
				return DCommand_.GRAPH_CONTAIN; //FP150407
			//else if (DAnnotation.CONTAINMENT_CMD_.equals(command))
		//		return DCommand_.GRAPH_CONTAIN_SRC_ABSTRACT_; //FP150407
			else if (DAnnotation.LINK.equals(command))
				return DCommand_.GRAPH_LINK;
			else
				return DCommand_.UNKNOWN;
		} else if (phase == DPhase.TOOL) {
			if (DAnnotation.POINT_OF_VIEW.equals(command)  )
				return DCommand_.TOOL_CREATE;
			else if (DAnnotation.NODE.equals(command))
				return DCommand_.TOOL_NODE;
			else if (DAnnotation.LINK.equals(command))
				return DCommand_.TOOL_LINK;
			else if (DAnnotation.CONTAINMENT_CMD_.equals(command))
				return DCommand_.NO_TOOL;
			else
				return DCommand_.UNKNOWN;
		} else if (phase == DPhase.MAP) {
			if (DAnnotation.POINT_OF_VIEW.equals(command)  )
				return DCommand_.MAP_CREATE_;
			else if (DAnnotation.NODE.equals(command))
				return DCommand_.MAP_NODE_;
			else if (DAnnotation.CONTAINMENT_CMD_.equals(command))
				return DCommand_.MAP_CONTAIN;
			else if (DAnnotation.LINK.equals(command))
				return DCommand_.MAP_LINK_;
			else
				return DCommand_.UNKNOWN;
		} else
			return DCommand_.UNKNOWN;
	}






	private static DStatement getStatement2(List<DStatement> statements,DiaContainedElement diagramElement,  IDiaContainment containment,DCommand_ dCommand,int i) {

		//String argument = containment==null?diagramElement.getName():containment.getName();

		DStatement toAdd = new DStatement(diagramElement, containment, dCommand,i);

		DCommand_ cmd = toAdd.getCommand();

		if (diagramElement.isAbstract())
			toAdd.setCommand(DCommand_.GRAPH_CONTAIN_SRC_ABSTRACT); //FP150409
		if (LOG){
			clog_("<statement> " + toAdd.toString());

		}
		if (toAdd.getCommand() == DCommand_.UNKNOWN){
			if (LOG)
				clog_("!!!!Unknown command!!!!!");
		}else
			//if (toAdd.getCommand() != DCommand.NOP_)
			{
			  statements.add(toAdd); //FP140221xx
			  return toAdd;
		    }
		return null;
	}



	private static void addStatement(List<DStatement> statements, DiaContainedElement diagramElement, IDiaContainment containment, DCommand_ dCommand,int i) {
		//DStatement statement =
		//List<DStatement> statements,DiaContainedElement diagramElement,  DiaContainment containment,DCommand dCommand,int i
		getStatement2(statements,diagramElement, containment,dCommand, i);


		//FP121007 diagramElement.getDefaultName(), command, phase);

	}

/*
	private static void addContainmentStatement__nu(List<DStatement> statements, DiaContainedElement diagramElement, DiaContainment containment, String command, DPhase phase,int i) {
		DStatement statement = getStatement2_(diagramElement, containment==null?diagramElement.getName():containment.getName(),containment,dCommand, i);
		if (statement != null)
			statements.add(statement);
	}

	private static DStatement getStatement1_nu(DiaContainedElement diagramElement, Object data,String command, DPhase phase,int i) {
		return getStatement2_(diagramElement, diagramElement.getName(), null,command, phase,i);//FP121007 diagramElement.getDefaultName(), command, phase);
	}
*/

	private static void prepareStatements(List<DiaContainedElement> dels, List<DStatement> statements, DPhase phase) {
		;
		if (LOG)
		  clog_("prepareStatements "+phase);
		int i=2000;

		DiaNode componentRole = null;

		for (DiaContainedElement cel : dels)
			if (cel instanceof DiaNode){
				if (((DiaNode) cel).isComponent())
					componentRole = (DiaNode) cel;
			}




		for (DiaContainedElement del : dels) {
			DiaNode nod = null;
			DiaLink link = null;
			if (del instanceof DiaNode){
				nod = (DiaNode) del;
			}
			if (del instanceof DiaLink)
				link = (DiaLink) del;
			if (nod != null) {

					if (nod.isCanvas()) {
						addStatement(statements, nod,null,DTokenizer.getCommand_(DAnnotation.POINT_OF_VIEW, phase),i++);
						if (nod.isDiagramRecursive() )//FP150531voiraz //|| componentRole!=null
							addStatement(statements, nod, null,DTokenizer.getCommand_(DAnnotation.NODE, phase),i++);
					} else{
						addStatement(statements, del, null,DTokenizer.getCommand_(DAnnotation.NODE, phase),i++);//FP140221z
					}

					for (IDiaContainment dContainment : nod.getContainments()){
						i++;

						if (LOG ){ //FP150407a && del.getName().equals("Foo")
							clog_(del.getName()+"->"+dContainment.getTargetNode().getName());
						}
						//addStatement(statements, del,dContainment, DTokenizer.getCommand(DAnnotation.CONTAINMENT_CMD, phase),i);
						addContaimentStatement_(statements, del,dContainment, phase,i);//FP140328xx
					}

				} else if (link != null)
					addStatement(statements, del, null,DTokenizer.getCommand_(DAnnotation.LINK, phase),i++);
		}
	}



	@SuppressWarnings({ "restriction", "unused" })
	private static void addContaimentStatement_(List<DStatement> statements, DiaContainedElement diagramElement, IDiaContainment containment, DPhase phase,int i) {
		if (LOG && phase==DPhase.CANVAS){
			clog_("["+i+"]***  addContaiment {ContainedElement="
				 +diagramElement.getName()+" containment="+containment.getContainmentName_()
					+ (diagramElement.isAbstract()?" ABSTR ":" KONCR ")+
					" target="+containment.getTargetNode().getName()+
					"}");
		}
		DCommand_ dCommand = DTokenizer.getCommand_(DAnnotation.CONTAINMENT_CMD_, phase);
		DStatement  st = getStatement2(statements,diagramElement, containment,dCommand, i);
		if (LOG){
		  if (st.getCommand()==DCommand_.GRAPH_CONTAIN_SRC_ABSTRACT) //FP150409
			clog_(st.toCsv());
		}
	} //FP140328


	public static List<DStatement> prepareGeneration(DiaGraph diagraph) {
		List<DiaContainedElement> diaContainedElement = diagraph.getAllDiaNodesAndLinks();
		List<DStatement> dstatmts = new ArrayList<DStatement>();
		prepareStatements(diaContainedElement, dstatmts, DPhase.CANVAS);
		prepareStatements(diaContainedElement, dstatmts, DPhase.TOOL);
		prepareStatements(diaContainedElement, dstatmts, DPhase.MAP); //FP2811 not reverse
		return dstatmts;
	}

	private static void log(List<DiaContainedElement> dels, DPhase phase) {
		boolean check;
		if (LOG)
		   clog_("log parsed diagraph "+phase);
		for (DiaContainedElement del : dels) {//FP150318t
			DiaNode nod = null;
			DiaLink link = null;
			if (del instanceof DiaNode)
				nod = (DiaNode) del;
			if (del instanceof DiaLink)
				link = (DiaLink) del;
			if (nod != null) {
					if (nod.isCanvas()) {
						if (nod.isDiagramRecursive())
							check=true;
						if (LOG)
							for (IDiaContainment diaContainment :nod.getPropagatedContainments_()){
								clog_("propag "+nod.getName()+"  ->   "+diaContainment.getContainmentName_());
						}

					}
					for (IDiaContainment dContainment : nod.getContainments()){
						EClass eclaz=(EClass) nod.getEModelElement();
						if (!eclaz.isAbstract() && !eclaz.isInterface()){ //FP130619ya
						if (LOG)
						    clog_("TKN "+dContainment.getSourceNode().getName()+"->"+dContainment.getTargetNode().getName()+" c="+dContainment.getContainmentName_()
						 		   +" k="+dContainment.isCompartment()
		                           +" d="+dContainment.isDerived()
								   +" p="+dContainment.isPort()
								   +" i="+dContainment.isPropagated()
								   );
						}
					}
				} else if (link != null)
					;

		}
	}

	private static void clog_(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	public static void simulateGeneration(DiaGraph diagraph) {
		List<DiaContainedElement> diaContainedElement = diagraph.getAllDiaNodesAndLinks();
		//log(diaContainedElement, DPhase.CANVAS);
		//log(diaContainedElement, DPhase.TOOL);
		log(diaContainedElement, DPhase.MAP);
	}

}
