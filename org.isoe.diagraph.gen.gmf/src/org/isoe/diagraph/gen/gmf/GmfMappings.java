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
package org.isoe.diagraph.gen.gmf;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gmf.mappings.CompartmentMapping;
import org.eclipse.gmf.mappings.FeatureLabelMapping;
import org.eclipse.gmf.mappings.NodeMapping;
import org.isoe.diagraph.internal.api.IDiaNamedElement;
import org.isoe.diagraph.internal.api.IDiaPlatformDelegate;
import org.isoe.diagraph.internal.api.IDiaSyntaxElement;
//import org.isoe.fwk.log.LogConfig;


/**
 *
 * @author pfister
 * not used
 */
public class GmfMappings implements IDiaPlatformDelegate {

	private static final boolean LOG = false;
	private List<NodeMapping> nodeMappings;
	private boolean nodeMappingsPresent;
	private List<CompartmentMapping> compartmentMappings;
	private boolean compartmentMappingsPresent;
	private List<FeatureLabelMapping> labelMappings;
	private boolean labelMappingsPresent;
	private IDiaSyntaxElement dSyntaxElement;

	@Override
	public List<NodeMapping> removePreparedNodes() {
		List<NodeMapping> result = removeNodeMappings();
		if (LOG ) {
			if (result != null)
				for (NodeMapping nmapping : result)
					System.out.println("////NodeMap-remove from: " + dSyntaxElement.getName() + " : " + nmapping.getDiagramNode().getName());
			if (result == null || result.isEmpty())
				System.out.println("NodeMap-empty for: " + dSyntaxElement.getName());
		}
		return result;
	}

	@Override
	public List<FeatureLabelMapping> removePreparedLabels() {
		List<FeatureLabelMapping> result = removeLabelMappings();
		if (LOG ) {
			if (result != null)
				for (FeatureLabelMapping lmapping : result)
					System.out.println("////LabelMap-remove from: " + dSyntaxElement.getName() + " : " + lmapping.getDiagramLabel().getName());
			if (result == null || result.isEmpty())
				System.out.println("LabelMap-empty for: " + dSyntaxElement.getName());
		}
		return result;
	}

	@Override
	public List<CompartmentMapping> removePreparedCompartments() {
		List<CompartmentMapping> result = removeCompartmentMappings();
		if (LOG ) {
			if (result != null)
				for (CompartmentMapping cmapping : result)
					System.out.println("////CompartmentMap-remove from: " + dSyntaxElement.getName() + " : " + cmapping.getCompartment().getName());
			if (result == null || result.isEmpty())
				System.out.println("CompartmentMap-empty for: " + dSyntaxElement.getName());
		}
		return result;
	}


	public GmfMappings(IDiaNamedElement dSyntaxElement) {
		super();
		this.dSyntaxElement = (IDiaSyntaxElement) dSyntaxElement;
	}


	private List<NodeMapping> removeNodeMappings() {
		if (nodeMappingsPresent) {
			nodeMappingsPresent = false;
			return nodeMappings;
		} else
			return null;
	}

	private List<FeatureLabelMapping> removeLabelMappings() {
		if (labelMappingsPresent) {
			labelMappingsPresent = false;
			return labelMappings;
		} else
			return null;
	}

	private List<CompartmentMapping> removeCompartmentMappings() {
		if (compartmentMappingsPresent) {
			compartmentMappingsPresent = false;
			return compartmentMappings;
		} else
			return null;
	}


	@Override
	public void addPreparedNode(Object nodeMapping) {
		if (LOG )
			System.out.println("////NodeMap-add to: " + dSyntaxElement.getName() + " : " + ((NodeMapping)nodeMapping).getDiagramNode().getName());
		if (!nodeMappingsPresent) {
			nodeMappingsPresent = true;
			nodeMappings = new ArrayList<NodeMapping>();
		}
		nodeMappings.add((NodeMapping) nodeMapping);
	}

	@Override
	public void addPreparedCompartment(Object compartmentMapping) {
		if (LOG )
			System.out.println("////CompartmentMap-add to: " + dSyntaxElement.getName() + " : " + ((CompartmentMapping)compartmentMapping).getCompartment().getName());
		if (!compartmentMappingsPresent) {
			compartmentMappingsPresent = true;
			compartmentMappings = new ArrayList<CompartmentMapping>();
		}
		compartmentMappings.add((CompartmentMapping) compartmentMapping);
	}

	@Override
	public void addPreparedLabel(Object labelMapping) {
		if (LOG )
			System.out.println("////LabelMap-add to: " + dSyntaxElement.getName() + " : " + ((FeatureLabelMapping)labelMapping).getDiagramLabel().getName());
		if (!labelMappingsPresent) {
			labelMappingsPresent = true;
			labelMappings = new ArrayList<FeatureLabelMapping>();
		}
		labelMappings.add((FeatureLabelMapping) labelMapping);
	}

	@Override
	public boolean checkHandled() {
		return !(nodeMappingsPresent || compartmentMappingsPresent || labelMappingsPresent);
	}

	@Override
	public List getPreparedCompartments() {
		if (LOG )
			System.out.println("////CompartmentMap-get from: " + dSyntaxElement.getName() + " : " +(compartmentMappingsPresent?compartmentMappings.size()+" preparedCompartments for ":"no preparedCompartments for ")+ dSyntaxElement.getName());
		return compartmentMappingsPresent ? compartmentMappings : null;
	}

	@Override
	public List getPreparedLabels() {
		if (LOG )
			System.out.println("////LabelMap-get from: " + dSyntaxElement.getName() + " : " +(labelMappingsPresent?labelMappings.size()+" preparedLabels for ":"no preparedLabels for ")+ dSyntaxElement.getName());
		return labelMappingsPresent ? labelMappings : null;
	}

	@Override
	public List<NodeMapping> getPreparedNodes() {
		if (LOG )
			System.out.println("////NodeMap-get from: " + dSyntaxElement.getName() + " : " +(nodeMappingsPresent?nodeMappings.size()+" preparedNodes for ":"no preparedNodes for ")+ dSyntaxElement.getName());
		return nodeMappingsPresent ? nodeMappings : null;
	}


}
