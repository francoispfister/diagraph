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

import org.isoe.diagraph.internal.m2.DiaContainedElement;
import org.isoe.diagraph.internal.m2.DiaLink;
import org.isoe.diagraph.internal.m2.DiaNode;
import org.isoe.diagraph.internal.m2.api.IDiaContainment;

/**
 *
 * @author pfister
 *
 */
public class DStatement {

	private String name_;
	private DCommand_ command;
	private DiaContainedElement element;
	private int id;
	private IDiaContainment containment;
	private static final boolean LOG = false;

	public int getId() {
		return id;
	}

	public enum DCommand_ {
		GRAPH_CREATE, GRAPH_NODE_, GRAPH_LINK, GRAPH_CONTAIN, MAP_CREATE_, MAP_NODE_, MAP_LINK_, MAP_CONTAIN, TOOL_CREATE, TOOL_NODE, TOOL_LINK, NO_TOOL, UNKNOWN, GRAPH_CONTAIN_SRC_ABSTRACT, GRAPH_CONTAIN_TRG_ABSTRACT, // NOP_,
	}

	public String toCsv() {
		String result = "";
		result += command + ";";
		result += element.getName() + ";";
		result += (containment != null && containment.getTargetNode() != null) ? containment
				.getTargetNode().getName() : " nocont ";
		result += (name_ != null ? name_ : "");
		// result += (inheritance ? " yes inh " : " no inh ");
		return result;
	}

	public void setCommand(DCommand_ command) {
		this.command = command;
	}

	public DStatement(DiaContainedElement diagramElement,
			IDiaContainment containment,
			DCommand_ command, int i) {

		this.element = diagramElement;
		this.containment = containment;
		this.name_ = containment == null ? diagramElement.getName()
				: containment.getName();
		        //: containment.getContainmentName_();//FP150526voir

		this.command = command;
		this.id = i;
	}

	public Object getElement() {
		return element;
	}

	public Object getContainment() {// FP150407a
		return containment;
	}

	public String getTargetElementName() { // FP150407a
		return containment.getTargetNode().getName();
	}

	public String toString() {
		String result = "";
		if (command != null)
			result += command;
		result += " - " + element.getName();
		result += (containment != null && containment.getTargetNode() != null) ? ("->" + containment
				.getTargetNode().getName()) : "";
		if (name_ != null)
			result += " -name " + name_;
		return "[" + id + "]" + result;
	}

	public DCommand_ getCommand() {
		return command;
	}


	public String getDiagramElementName() {
		return element.getName();
	}

	public String getIdent() {
		if (element instanceof DiaNode) {
			DiaNode d = (DiaNode) element;
			return d.getDepth() + "." + d.getName() + "." + name_;
		}else if (element instanceof DiaLink) {
			DiaLink d = (DiaLink) element;
			return d.getName() + "." + name_;
		} else
			return "";
	}

	public String getName() {
		return name_;
	}


}
