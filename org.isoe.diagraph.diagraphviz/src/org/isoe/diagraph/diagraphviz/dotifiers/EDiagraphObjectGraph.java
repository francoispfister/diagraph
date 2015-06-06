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
package org.isoe.diagraph.diagraphviz.dotifiers;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.isoe.extensionpoint.graphviz.IDotifier;

public class EDiagraphObjectGraph extends EObjectGraph{


	final static String[] REFER_FILTER = { "eContainingClass" };

	final static String[] ECLASS_FILTER = {
			"DGraph",// "EReference", "EClass", "EAttribute",
			"EAnnotation", "EGenericType", "EStringToStringMapEntry",
			"EPackage", "EFactory" };

	final static String[][] EDGE_FILTER_ = { { "DNode", "DReference" },
			{ "DNode", "DPartitioningNestingEdge" },
			{ "DNestingEdge", "DPointOfView" }, { "EReference", "EReference" },
			{ "EReference", "EClass" }, { "EClass", "EReference" } };

	final static String[][] NODE_STYLE = {
			{ "EClass",
					" shape=\"box\"  style=\"filled\", fillcolor=\"lightyellow1\" " },
			{ "EReference",
					" shape=\"pentagon\" style=\"filled\", fillcolor=\"lightyellow1\"  " },
			{ "EAttribute",
					" shape=\"octagon\"  style=\"filled\", fillcolor=\"lightyellow1\" " },
			{
					"DNode",
					" shape=\"circle\" style=\"filled\", fillcolor=\"lightsalmon1\" fixedsize=\"true\" width=\"2.0\"  height=\"2.0\"" },
			{
					"DPointOfView",
					" shape=\"circle\"  style=\"filled\", fillcolor=\"lightsalmon1\"  fixedsize=\"true\" width=\"3.0\"  height=\"3.0\"" },
			{ "DNestingEdge",
					" shape=\"hexagon\"  style=\"filled\", fillcolor=\"lightseagreen\" " },
			{ "DPartitioningNestingEdge",
					" shape=\"septagon\"  style=\"filled\", fillcolor=\"lightseagreen\" " },
			{ "DReference",
					" shape=\"parallelogram\"  style=\"filled\", fillcolor=\"lightseagreen\" " },
			{ "DLink",
					" shape=\"house\"  style=\"filled\", fillcolor=\"lightslateblue\" " },
			{
					"DGraph",
					" shape=\"oval\"  style=\"filled\", fillcolor=\"lawngreen\" fixedsize=\"true\" width=\"2.0\"  height=\"2.0\" " }, };

	final static String[][] NODE_LABEL_REPLACE = { { ";default_root", "" },
			{ "Foo", "Bar" } };

	final static String[][] EDGE_LABEL_REPLACE = { { "roleInCsGraph", "role" },
			{ "bar", "baz" } };
	
	
	public EDiagraphObjectGraph(ResourceSet rs, String inputFile,
			String outputFile, String dotOutputPath, IDotifier handler) {
		super(rs, inputFile, outputFile, dotOutputPath, handler);
		setEdgeLabelReplacements(EDGE_LABEL_REPLACE);
		setNodeLabelReplacements(NODE_LABEL_REPLACE);
		setNodeStyles(NODE_STYLE);
		setEdgefilters(EDGE_FILTER_);
		setEcoreClassfilter(ECLASS_FILTER);
		setReferencefilter(REFER_FILTER);
		
	}

}
