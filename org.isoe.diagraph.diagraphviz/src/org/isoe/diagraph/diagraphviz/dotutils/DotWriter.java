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
package org.isoe.diagraph.diagraphviz.dotutils;



import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.ETypedElement;


public abstract class DotWriter implements DotConstants {

	protected int tableDepth; // TODO indentation on option only
	private static int arrowid_;
	protected static int clusterid;
	protected static int nodeId;
	
	public static final String GRAPH_END = "}/*end graph*/";


	protected void initializeIds() {
		arrowid_ = 0;
		clusterid = 0;
		nodeId = 0;
	}

	public static String getObjectId(EObject eObject) {
		return "id" + Integer.toString(eObject.hashCode()); // graphviz is not
															// very constant...
															// should not begin
															// with a number, in
															// some cases there
															// is a 587 error :
															// ambiguous
															// splits into two
															// names
	}
	/*
	protected String startArrowNameHtml_old(EClassifier source, EClassifier target) {
		return source.getName()+":"+PORT_PREFIX+source.getName() + " -> "
				+ target.getName()+":"+PORT_PREFIX+target.getName();
	}

	protected String startArrowNameHtml_old(String srcid, String trgid) {
		return srcid+":"+PORT_PREFIX+srcid + " -> "
				+ trgid+":"+PORT_PREFIX+trgid;
	}
	*/

	protected String startArrow(String srcid) {
		return srcid+":"+PORT_PREFIX+srcid;
	}
	
	protected String endArrow(String trgid) {
		return trgid+":"+PORT_PREFIX+trgid;
	}
	
	protected String startTopNode(String topId, String portId) {
		tableDepth++;
		String spaces = SPACES.substring(0, tableDepth * 3);
		String start = spaces
				+ "/*start top node*/\n"
				+ spaces
				+ topId
				+ "[label=<<TABLE  cellspacing=\"-1\" cellpadding=\"0\" border=\"0\" > <TR><TD port=\""
				+ portId + "\">";
		return start;
	}
	
	protected String startTable_(String color) {
		tableDepth++;
		String spaces = SPACES.substring(0, tableDepth * 3);
		return spaces + "\n<TABLE bgcolor=\"" + color + "\" cellspacing=\"-1\" border=\"1\">\n";
	}
	
	protected String startColoredTable(String name, String color) {
		return "<TABLE bgcolor=\"" + color
				+ "\" cellspacing=\"-1\" border=\"1\" ><TR><TD>"
				+ name + "</TD></TR>\n";
	}

	protected String endTopNode() {
		String spaces = SPACES.substring(0, tableDepth * 3);
		String end = spaces + "</TD></TR></TABLE>>];\n" + spaces
				+ "/*end top node*/\n";
		tableDepth--;
		return end;
	}

	protected String startChildNode(String id, String portId) {
		String spaces = SPACES.substring(0, tableDepth * 3);
		String start = spaces + "<!-- start child node -->\n" + spaces
				+ "<TR><TD port=\"" + portId + "\">";
		return start;
	}

	protected String endChildNode() {
		String spaces = SPACES.substring(0, tableDepth * 3);
		return spaces + "</TD></TR>\n" + spaces + "<!-- end child node -->\n";
	}
	
	protected String startGraph(String shape) {
		return "digraph G { rankdir=LR    node [fontname=\"" + FONT
				+ "\",fontsize=" + FONT_SIZE + ",shape=" + shape
				+ "] edge [fontname=\"" + FONT + "\",fontsize=" + FONT_SIZE
				+ "];";
	}

	protected String dotStartRecordShape(String color) {
		if (color.contains("."))
			color = "\"" + color + "\"";
		return " [shape=record,style=filled,fillcolor=" + color + ",";
	}

	protected String dotLabel(EObject eobj, String nameAttribute) {
		return "label=\""
				+ (String) eobj.eGet(eobj.eClass().getEStructuralFeature(
						nameAttribute)) + "\"";
	}
	
	protected String writeAttribute(EAttribute eAttribute,EDataType eDataType){
		EClass contnr = (EClass) eAttribute.eContainer();
		String attrid = contnr.getName() + "_" + eAttribute.getName();
		String line = "<TR><!-- attribute --><TD port=\"" + PORT_PREFIX + attrid
				+ "\" align=\"left\">" + eAttribute.getName() + ": "
				+ eDataType.getName() + "</TD></TR>";
		return line;
	}
	
	protected String writeEmptyLine(){
		return "<TR><TD></TD></TR>";
	}

	protected String startArrowHascode(EObject source, EObject target) {
		return Integer.toString(source.hashCode()) + " -> "
				+ Integer.toString(target.hashCode());
	}

	protected String dotEnd() {
		return "];";
	}

	protected String dotLabel(EObject eobj, EAttribute attribute) {
		return "label=\"" + (String) eobj.eGet(attribute) + "\"";
	}

	protected String dotLabel(String name) { // graph.getLayerName()
		return "label=\"" + name + "\"";
	}

	protected String m2StartLabel(ENamedElement n) {
		return (COMMENT ? "/*start label*/" : "") + "label=\"{" + n.getName();
	}

	protected String m2Label(ENamedElement p) {
		return "label=\"" + p.getName() + "\"";
	}
	
	protected String m2Label(String label) {
		return "label=\"" + label + "\"";
	}

	protected String m2Cardinality(ETypedElement te) {
		String c = Integer.toString(te.getLowerBound());
		c += "..";
		if (te.getUpperBound() == -1)
			c += "*";
		else
			c += Integer.toString(te.getUpperBound());
		return c;
	}

	protected String m2CardinalityLabel(ETypedElement eref) {
		return "headlabel=\"" + m2Cardinality(eref) + "\",label=\""
				+ eref.getName() + "\"";
	}

	protected String m2StartInverseLink(String color) {
		return " [color=\"" + color + "\", arrowhead=empty ";
	}

	
	protected String dotStartArrow(boolean agregation, String color) {
		if (color.contains("."))
			color = "\"" + color + "\"";
		return " [color=" + color + ","
				+ (agregation ? "dir=both,arrowtail=diamond," : "") // arrowtail
																	// implies
																	// dir=both
				+ "arrowhead=vee,";
	}

	protected String diamondArrow(String srclabel, String trglabel, String startoid,
			String endOid) {
		String result = (COMMENT ? "/*" + srclabel + " => " + trglabel
				+ "*/   " : "")
				+ startoid
				+ " -> "
				+ endOid
				+ " [dir=both arrowtail=diamond];"
				+ (COMMENT ? " /*" + arrowid_++ + "*/" : "");											
		return result;
	}

	protected String refArrow(String srclabel, String trglabel, String startoid,
			String endOid) {
		String result = (COMMENT ? "/*" + srclabel + " => " + trglabel
				+ "*/   " : "")
				+ startoid + " -> " + endOid
				// + " [dir=both arrowtail=odot];"
				+ " [color=blue];" + (COMMENT ? " /*" + arrowid_++ + "*/" : "");
		return result;
	}

	protected String linkArrow(String srclabel, String trglabel, String startoid,
			String endOid, String edgelab) {
		String result = (COMMENT ? "/*" + srclabel + " => " + trglabel
				+ "*/   " : "")
				+ startoid
				+ " -> "
				+ endOid
				+ " [label= \""
				+ edgelab
				+ "\", color=red];"
				// + " [dir=both arrowtail=dot];"
				+ (COMMENT ? " /*" + arrowid_++ + "*/" : "");
		return result;
	}

	protected String startSubGraph(String oid, String label, boolean cluster) {// EObject
																		// eObj
																		// oidmap.get(eObj)
		return "subgraph " + (cluster ? "cluster_" : "")
				+ oid // eObj.hashCode()
				+ " { " + " label=\"" + label
				+ "\"  fontname=\"Verdana\",fontsize=10,  \n"; // "_" + name
	}

	protected String dotNode(String text, String color, String oid) {
		if (color == null)
			color = COLOR_BLUE;
		return oid
				+ "[label=\""
				+ text
				+ "\" fontname=\"Verdana\",fontsize=10, shape = box, style = \"rounded,filled\", fillcolor=\""
				+ color + "\"];";
	}

	
	
	
	protected String endGraph() {
		return GRAPH_END;
	}

	protected String m2EndLabel() {
		return "}\"/*end label*/";
	}

	protected String endSubGraph() {
		return "}/*end subgraph*/";
	}


	protected String writeRow_(String rowId, String label) {
		String spaces = SPACES.substring(0, tableDepth * 3);
		return spaces + "<TR><!-- row --><TD port=\"" + rowId + "\" align=\"left\">"
				+ label + "</TD></TR>\n";
	}

	protected String writeRawNode(String oid, String label, String color) {
		return oid + " [label=\"" + label
				+ "\", shape = box, style = \"rounded,filled\", fillcolor=\""
				+ color + "\"];\n";
	}

	protected String newLine() {
		String spaces = SPACES.substring(0, tableDepth * 3);
		return spaces + "\n";
	}

	protected String addHeader(EClass eclass, String color){
		String header="<TABLE bgcolor=\""+color+"\" cellspacing=\"-1\" border=\"1\" ><TR><TD>"+eclass.getName()+"</TD></TR>\n";
        return header;
	}

	protected String startTable(String color) {
		tableDepth++;
		String spaces = SPACES.substring(0, tableDepth * 3);
		return "\n" + spaces + "<TABLE bgcolor=\"" + color
				+ "\" cellspacing=\"-1\" border=\"1\">\n";
	}

	protected String endTable() {
		String spaces = SPACES.substring(0, tableDepth * 3);
		String result = spaces + "</TABLE>\n";
		tableDepth--;
		return result;
	}

	protected String endTableInRow() {
		String spaces = SPACES.substring(0, tableDepth * 3);
		String result = spaces + "</TABLE></TD></TR>\n";
		tableDepth--;
		return result;
	}

	protected String startGraph() {
		return "digraph G { rankdir=BT  overlap = \"scale\" labelfloat = \"true\" node [fontname=\"" + FONT
				+ "\", fontsize=" + FONT_SIZE + ", shape=none] edge [fontname=\""
				+ FONT + "\", fontsize=" + FONT_SIZE + "];";
	}

	protected String dotBracketedLabel(EObject eobj, String nameAttribute) {
		return "label=\"{"
				+ (String) eobj.eGet(eobj.eClass().getEStructuralFeature(
						nameAttribute)) + "}\"";
	}

	protected String dotBracketedLabel(EObject eobj, EAttribute lattr) {
		if (lattr != null)
			return "label=\"{" + (String) eobj.eGet(lattr) + "}\"";
		else
			return "label=\"{" + "default" + "}\"";
	}

	protected String dotBracketedLabel(String name) {
		return "label=\"{" + name + "}\"";
	}

	protected String startSubGraph(EObject eObj, boolean cluster) {
		return "subgraph " + (cluster ? "cluster_" : "") + eObj.hashCode()
				+ " /*start subgraph 1*/{ ";
	}
	
	protected String startSubGraph(int id,boolean cluster) {
		return "subgraph " + (cluster ? "cluster_" : "") + id
				+ " /*start subgraph 2*/{ ";
	}
	
	protected String startSubGraph(int id, String shape, boolean cluster) {
		return "subgraph " + (cluster ? "cluster_" : "") + id
				+ " /*start subgraph 3*/{ node [fontname=\"Verdana\",fontsize=10, shape=\""+shape+"\" ] ";
	}
	
	protected String endTable2() {
		return "</TABLE>\n";
	}

	protected String endNodeTable() {
		return "</TD></TR></TABLE>>];\n";
	}

	protected String startAttributesTable() {
		return "<TR><TD><TABLE border=\"0\">\n";
	}
	
	protected String endAttributesTable() {
		return "</TABLE></TD></TR>\n";
	}

	protected void addAttributeNodeInRecordShape_(EAttribute eattr, EClass cls,
			EDataType dtype, StringBuffer classBuffer) {
			classBuffer
					.append(eattr.getName() + "[" + m2Cardinality(eattr) + "]");
			classBuffer.append(": " + dtype.getName());
			classBuffer.append("\\n");
	}
	/*
	protected String startTopNode_old(EClass eclass){
		String start=eclass.getName()+"[label=<<TABLE  cellspacing=\"-1\" cellpadding=\"0\" border=\"0\" > <TR><TD port=\""+PORT_PREFIX+eclass.getName()+"\">";
        return start;
	}
	*/
	protected String startTopNode(EClass eclass, String objectId){
		String start=objectId+"[label=<<TABLE  cellspacing=\"-1\" cellpadding=\"0\" border=\"0\" > <!-- top  --> <TR><TD port=\""+PORT_PREFIX+objectId+"\">";
        return start;
	}
	

	
	protected void addAttributeNodeInHtml_(EAttribute eattr, EClass cls,
			EDataType dtype, StringBuffer classBuffer) {
			EClass contnr=(EClass) eattr.eContainer();
			String attrid=contnr.getName()+"_"+eattr.getName();
			String line="<!-- attr 2  --><TR><TD port=\""+PORT_PREFIX+attrid+"\" align=\"left\">"+eattr.getName()+": "+dtype.getName()+"</TD></TR>";
			classBuffer.append(line);
	}	
	
	protected String addAttributeNodeInHtml_(EAttribute eattr, EClass cls,EDataType dtype) {
			EClass contnr=(EClass) eattr.eContainer();
			String attrid=contnr.getName()+"_"+eattr.getName();
			String line="<!-- attr 3  --><TR><TD port=\""+PORT_PREFIX+attrid+"\" align=\"left\">"+eattr.getName()+": "+dtype.getName()+"</TD></TR>";
			return line;
	}	
	
	protected String addAttributeNodeInHtml(EAttribute eattr, String nameToDisplay, EClass cls,EDataType dtype) {
		EClass contnr=(EClass) eattr.eContainer();
		String attrid=contnr.getName()+"_"+eattr.getName();
		String line="<!-- attr 4  --><TR><TD port=\""+PORT_PREFIX+attrid+"\" align=\"left\">"+nameToDisplay+": "+dtype.getName()+"</TD></TR>";
		return line;
}	
	
	protected void addEmptyRow(StringBuffer classBuffer) {
		classBuffer.append("<TR><TD></TD></TR>");
	}
	
	protected void addOperationsLabels(EClass cls, StringBuffer classBuffer) {
		classBuffer.append("|");
		for (EOperation operation : cls.getEOperations())
			addOperationLabel(operation, classBuffer);
	}

	protected void addOperationLabel(EOperation operation,
			StringBuffer classBuffer) {
		classBuffer.append(operation.getName() + "(");
		String params = "";
		for (EParameter param : operation.getEParameters())
			params += (param.getName() + ": " + param.getEType().getName() + ",");
		if (operation.getEParameters().size() > 0)
			classBuffer.append(params.substring(0, params.length() - 1));
		classBuffer.append(")");
		if (operation.getEType() != null)
			classBuffer.append(": " + operation.getEType().getName());
		classBuffer.append("\\n");
	}
	
}
