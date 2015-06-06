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
package org.isoe.diagraph.diastyle.helpers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.isoe.diagraph.diagraph.DOwnedEdge;
import org.isoe.diagraph.diagraph.DEdge;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.diagraph.DGraphElement;
import org.isoe.diagraph.diagraph.DNavigationEdge;
import org.isoe.diagraph.diagraph.DNestedEdge;
import org.isoe.diagraph.diagraph.DLabeledEdge;
import org.isoe.diagraph.diagraph.DNode;
import org.isoe.diagraph.diagraph.DReference;
import org.isoe.diagraph.diagraph.DSimpleEdge;
import org.isoe.diagraph.diastyle.DBaseStyle;
import org.isoe.diagraph.diastyle.DEdgeStyle;
import org.isoe.diagraph.diastyle.DNestingEdgeStyle;
import org.isoe.diagraph.diastyle.DNodeStyle;
import org.isoe.diagraph.diastyle.DStyle;
import org.isoe.diagraph.diastyle.DiastyleFactory;
import org.isoe.diagraph.lang.DiagraphKeywords;



/**
 *
 * @author pfister
 *
 */
public class StyleUtils {

    private static final boolean LOG = false;
    // private EPackage metaModel;
    private IStyleHandler handler;
    private DGraph dgraph;
	private IErrorReporter errorReporter;

    DBaseStyle getGraphStyle(DStyle dStyle, String name,
            StyleHandler.Style style) {
        DBaseStyle result = findGraphStyle(dStyle, name, style);
        if (result == null)
            result = createGraphStyle(dStyle, name, style);
        return result;
    }

    private DBaseStyle findGraphStyle(DStyle dStyle, String name,
            StyleHandler.Style styl) {
        DBaseStyle result = null;
        for (DBaseStyle dGraphStyle : dStyle.getStyles()) {
            if (dGraphStyle.getName().equals(name)) {
                if (styl == StyleHandler.Style.node
                        && dGraphStyle instanceof DNodeStyle)
                    result = (DNodeStyle) dGraphStyle;
                else if (styl == StyleHandler.Style.edge
                        && dGraphStyle instanceof DEdgeStyle)
                    result = (DEdgeStyle) dGraphStyle;
                else if (styl == StyleHandler.Style.nesting
                        && dGraphStyle instanceof DNestingEdgeStyle)
                    result = (DNestingEdgeStyle) dGraphStyle;
                if (result != null)
                    break;
            }
        }
        return result;
    }

    private DBaseStyle createGraphStyle(DStyle dStyle, String name,
            StyleHandler.Style styl) {
        DBaseStyle result = null;
        if (LOG)
            switch (styl) {
            case node:
                clog("STUTCGS" + " creating NodeStyle " + name);
                break;
            case edge:
                clog("STUTCGS" + " creating EdgeStyle " + name);
                break;
            case nesting:
                clog("STUTCGS" + " creating NestingEdgeStyle " + name);
                break;
            }
        switch (styl) {
        case node:
            result = DiastyleFactory.eINSTANCE.createDNodeStyle();
            break;
        case edge:
            result = DiastyleFactory.eINSTANCE.createDEdgeStyle();
            break;
        case nesting:
            result = DiastyleFactory.eINSTANCE.createDNestingEdgeStyle();
            break;
        }
        result.setName(name);
        handler.setGenericDefaultValues(result);
        switch (styl) {
        case node:
            handler.setSpecificDefaultValues((DNodeStyle) result);
            break;
        case edge:
            handler.setSpecificDefaultValues((DEdgeStyle) result); // FP120929
            break;
        case nesting:
            handler.setSpecificDefaultValues((DNestingEdgeStyle) result); // FP120929
            break;
        }
        if (LOG)
            clog("created: " + result.toString());
        dStyle.getStyles().add(result);
        return result;
    }

    public String toString(DBaseStyle dGraphStyle) {
        String result = "";
        if (dGraphStyle instanceof DNodeStyle)
            result += " type=NodeStyle";
        else if (dGraphStyle instanceof DEdgeStyle)
            result += " type=EdgeStyle";
        else if (dGraphStyle instanceof DNestingEdgeStyle)
            result += " type=NestingEdgeStyle";
        result += " name=" + dGraphStyle.getName();
        result += " color=" + dGraphStyle.getColor().getLiteral();
        // FP120929 result += " font=" + dGraphStyle.getFontName();
        if (dGraphStyle instanceof DNestingEdgeStyle) {
            DNestingEdgeStyle es = (DNestingEdgeStyle) dGraphStyle;
            result += " nesting...";
        }
        if (dGraphStyle instanceof DNodeStyle) {
            DNodeStyle ns = (DNodeStyle) dGraphStyle;
            result += " layout=" + ns.getLayout().getLiteral();
            result += " shape=" + ns.getShape().getLiteral();
            result += " ...";
        }
        if (dGraphStyle instanceof DEdgeStyle) {
            DEdgeStyle es = (DEdgeStyle) dGraphStyle;
            result += " arrowSize=" + es.getArrowSize();
            result += " arrowDirection=" + es.getArrowDirection();
            result += " ...";
        }
        return result;
    }

    public DBaseStyle getNodeStyle(DStyle dStyle, String name) {
        for (DBaseStyle dGraphStyle : dStyle.getStyles())
            if ((dGraphStyle instanceof DNodeStyle)
                    && (((DNodeStyle) dGraphStyle).getName().equals(name)))
                return dGraphStyle;
        return null;
    }

    public DBaseStyle getEdgeStyle(DStyle dStyle, String name) {
        for (DBaseStyle dGraphStyle : dStyle.getStyles())
            if ((dGraphStyle instanceof DEdgeStyle)
                    && (((DEdgeStyle) dGraphStyle).getName().equals(name)))
                return dGraphStyle;
        return null;
    }

    public DBaseStyle getPartitionStyle(DStyle dStyle, String name) {
        for (DBaseStyle dGraphStyle : dStyle.getStyles())
            if ((dGraphStyle instanceof DNestingEdgeStyle)
                    && (((DNestingEdgeStyle) dGraphStyle).getName()
                            .equals(name)))
                return dGraphStyle;
        return null;
    }

    public List<DGraphElement> getAllElements(DGraph dGraph) {
        List<DGraphElement> result = new ArrayList<DGraphElement>();
        result.addAll(getNodes(dGraph));
        for (DNode nod : getNodes(dGraph)) {
            for (DEdge edg : getEdges(nod))
                if (!result.contains(edg))
                    result.add(edg);
        }
        return result;
    }

    private List<DNode> getNodes(DGraph graph) { // FP140518
        List<DNode> nodes = new ArrayList<DNode>();
        List<DGraphElement> elements = graph.getElements();
        for (DGraphElement element : elements) {
            if (element instanceof DNode)
                nodes.add((DNode) element);
        }
        return nodes;
    }

    private List<DEdge> getEdges(DGraph graph) { // FP140518
        List<DEdge> result = new ArrayList<DEdge>();
        for (DGraphElement element : graph.getElements())
            if (element instanceof DEdge)
                result.add((DEdge) element);
        return result;
    }
/*
    private List<DEdge> getEdges_old(DNode nod) {
        List<DEdge> result = new ArrayList<DEdge>();
        List<DEdge> all = getEdges(nod.getGraph());
        for (DEdge dEdge : all)
            if (dEdge.getSource().getNode() == nod)
                result.add(dEdge);
        return result;
    }*/

	public List<DEdge> getEdges(DNode node) {
		List<DEdge> result = new ArrayList<DEdge>();
		for (DGraphElement element : node.getGraph().getElements()){
			if (element instanceof DNestedEdge){
				if (((DNestedEdge) element).getSource().getNode() == node)
					result.add((DEdge) element);
			} else if(element instanceof DSimpleEdge){
				 if (((DSimpleEdge) element).getSource() == node)
					result.add((DEdge) element); //FP150423b
			}
		}
		return result;
	}


    private DNestedEdge findContainmentElement(DGraph dgraph, DNode dNode,
            String name) {
        for (DGraphElement dGraphElement : getAllElements(dgraph))
            if ((dGraphElement instanceof DNestedEdge)
                    && (((DNestedEdge) dGraphElement).getSource().getNode() == dNode && ((DNestedEdge) dGraphElement)
                            .getName().equals(name)))
                return (DNestedEdge) dGraphElement;
        return null;
    }

    private DReference findReferenceElement(DGraph dgraph, DNode dNode,
            String name) {
        for (DGraphElement dGraphElement : getAllElements(dgraph))
            if ((dGraphElement instanceof DReference) //FP150423b
                    && (((DReference) dGraphElement).getSource() == dNode && ((DReference) dGraphElement)
                            .getName().equals(name)))
                return (DReference) dGraphElement;
        return null;
    }

    private DLabeledEdge finDLabeledEdgeElement__(DGraph dgraph, DNode dNode,
            String name) {
        for (DGraphElement dGraphElement : getAllElements(dgraph))
            if (dGraphElement instanceof DLabeledEdge)
                if (((DLabeledEdge) dGraphElement).getSource() == dNode)//FP150413c
                    for (EReference eReference : dNode.getEClaz()
                            .getEReferences())
                        if (eReference.getName().equals(name)
                                && eReference.getEType() == ((DLabeledEdge) dGraphElement)
                                        .getEClaz())
                            return (DLabeledEdge) dGraphElement;
        return null;
    }

    private DLabeledEdge finDLabeledEdgeElement(DGraph dgraph, DNode dNode,
            String name) {
        for (DGraphElement dGraphElement : getAllElements(dgraph)) {
            if (dGraphElement instanceof DLabeledEdge) {
                DLabeledEdge link = (DLabeledEdge) dGraphElement;
                   // if (link.getContainmentd().getSource() == dNode) {
                    if (link.getSource() == dNode) { //FP150423c
                    // EReference srcref = link.getSourceReference();
                    // EClass eclaz = link.getEClass();
                    for (EReference eReference : dNode.getEClaz()
                            .getEReferences()) {
                        if (LOG)
                            clog(eReference.getName());
                        if (eReference.getName().equals(name)) {
                            EClassifier rt = eReference.getEType();
                            if (rt == link.getEClaz())
                                return link;
                        }
                    }
                }
            }
        }
        return null;
    }

    private void clog(String mesg) {
        if (LOG)
            System.out.println(mesg);
    }

    public DLabeledEdge finDLabeledEdge(DGraph dgraph, DGraphElement dGraphElement,
            String key) {
    	DLabeledEdge result = null;
        if (!(dGraphElement instanceof DNode))
            throw new RuntimeException("graphElement should be a DNode");
        result = finDLabeledEdgeElement(dgraph, (DNode) dGraphElement,
                key.substring(DiagraphKeywords.STYLE_LNK.length() + 1));
        if (result == null)
            throw new RuntimeException("no link found for style "
                    + key.substring(DiagraphKeywords.STYLE_LNK.length() + 1));
        return result;
    }

    public DNestedEdge findCompartment(DGraph dgraph,
            DGraphElement dGraphElement, String key) {
        DNestedEdge result = null;
        if (!(dGraphElement instanceof DNode))
            throw new RuntimeException("graphElement should be a DNode");
        result = findContainmentElement(dgraph, (DNode) dGraphElement,
                key.substring(DiagraphKeywords.STYLE_KREF.length() + 1));
        if (result == null)
            throw new RuntimeException("no containmnent found for style "
                    + key.substring(DiagraphKeywords.STYLE_KREF.length() + 1));
        return result;
    }

    public DReference findReference(DGraph dgraph, DGraphElement dGraphElement,
            String key) {
        DReference result = null;
        if (!(dGraphElement instanceof DNode))
            throw new RuntimeException("graphElement should be a DNode");
        result = findReferenceElement(dgraph, (DNode) dGraphElement,
                key.substring(DiagraphKeywords.STYLE_REF.length() + 1));
        if (result == null)
            throw new RuntimeException("no reference found for style "
                    + key.substring(DiagraphKeywords.STYLE_REF.length() + 1));
        return result;
    }

    public DNestedEdge findContainment(DGraph dgraph,
            DGraphElement dGraphElement, String key) {
        DNestedEdge result = null;
        if (!(dGraphElement instanceof DNode))
            throw new RuntimeException("graphElement should be a DNode");
        result = findContainmentElement(dgraph, (DNode) dGraphElement,
                key.substring(DiagraphKeywords.STYLE_CREF.length() + 1));
        if (result == null)
            throw new RuntimeException("no containmnent found for style "
                    + key.substring(DiagraphKeywords.STYLE_CREF.length() + 1));
        return result;
    }

    public EClass findDiagraphedClass(EPackage pak, String eClassName) {
        for (TreeIterator<EObject> it1 = pak.eAllContents(); it1.hasNext();) {
            EObject obj = it1.next();
            if ((obj instanceof EClass)
                    && ((EClass) obj).getName().equals(eClassName)
                    && ((EClass) obj)
                            .getEAnnotation("diagraph") != null)
                return (EClass) obj;
        }
        return null;
    }

    public EReference findDiagraphedReference(EPackage pak, String eRefName) {
        for (TreeIterator<EObject> it1 = pak.eAllContents(); it1.hasNext();) {
            EObject obj = it1.next();
            if ((obj instanceof EReference)
                    && ((EReference) obj).getName().equals(eRefName)
                    && ((EClass) (obj.eContainer()))
                            .getEAnnotation("diagraph") != null)
                return (EReference) obj;
        }
        return null;
    }

    public StyleUtils(IErrorReporter errorReporter,DGraph dgraph, IStyleHandler handler) {
        // this.metaModel = mmodel;
        this.dgraph = dgraph;
        this.handler = handler;
        this.errorReporter = errorReporter;
    }

}
