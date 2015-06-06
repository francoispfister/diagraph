package org.isoe.fwk.diagen.text;

import java.util.ArrayList;
import java.util.List;

import org.isoe.extensionpoint.diagen.IDiagraphAlphabet;
import org.isoe.extensionpoint.diagen.ITextParser;
import org.isoe.fwk.core.DParams;

public class RoleManagerImpl implements RoleManager {

	private static final boolean LOG = DParams.RoleManager_LOG;
	private ITextParser textParser;
	private List<String> roles = new ArrayList<String>();


	public RoleManagerImpl(ITextParser textParser) {
		this.textParser = textParser;
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	private void addRole(String role) {
		if (!roles .contains(role)) {
			if (LOG)
				clog("parse - addRole: "+role);
			roles.add(role);
		}
	}

    @Override
	public void parseNotationNodeOrGeneric(String view, String element,
			String argument) {
		if (LOG)
			clog("parseNotationNodeOrGeneric " + element + "." + view);
		if (IDiagraphAlphabet.DIAGRAPH_VOCAB_GENERIC.equals(argument))
			createGenericElement(view, element);
		else
			createNode(view, element, IDiagraphAlphabet.DIAGRAPH_VOCAB_POV_.equals(argument));
	}

	private void parseLink(String view, String src, String assoc) {
		String claz = assoc;
		String ref = null;
		if (assoc.contains(".")) {
			String[] v = assoc.split("\\.");
			claz = v[0];
			ref = v[1];
		}
		createLink(view, src, claz, ref);
	}

	private void parseKref(String view, String name, String ref) {
		createKref(view, name, ref);//FP150318t
	}

	private void parseRef(String view, String name, String ref) {
		createRef(view, name, ref);
	}

	private void parseAfx(String view, String name, String ref) {
		createAfx(view, name, ref);
	}

	private void parseNav(String view, String name, String targetView) {
		createNav(view, name, targetView);
	}

	private void parsePov(String view, String name) {
		createPov(view, name);
	}

	private void parseGeneric(String view, String name) {
		createGenericElement(view, name);
	}

	private void parseCref(String view, String name, String ref) {
		createCref(view, name, ref);
	}


	private void parseLabels(String view, String name, String value) {
		if (name.contains("("))
			name = trimArguments(name)[0];
		createLabels(view, name, value);
	}

	private String[] trimArguments(String element) {
		int op = element.indexOf(IDiagraphAlphabet.SEP_OPEN_PARENTH);
		int cl = element.indexOf(IDiagraphAlphabet.SEP_CLOSE_PARENTH);
		String[]result = new String[2];
		try {
			result[0]=element.substring(0, op);
			result[1]=element.substring(op + 1, cl);
		} catch (Exception e) {
			result[0]=element;
		}
		return result;
	}

	private void createGenericElement(String view, String element) {
		if (LOG)
			clog("parse - createGenericElement ("+element+")");
		addRole(IDiagraphAlphabet.DIAGRAPH_CMD_GEN + "(" + view + "," + element + ")");
	}

	private void createNode(String view, String node, boolean isPov) {
		if (LOG)
			clog("parse - createNodes ("+node+(isPov?"":".pov")+")");
		if (isPov)
			addRole(IDiagraphAlphabet.DIAGRAPH_CMD_POV_ + "(" + view + "," + node + ")");
		else
			addRole(IDiagraphAlphabet.DIAGRAPH_CMD_NOD + "(" + view + "," + node + ")");
	}

	private void createPov(String view, String node) {
		if (LOG)
			clog("parse - createPov ("+node+")");
		addRole(IDiagraphAlphabet.DIAGRAPH_CMD_POV_ + "(" + view + "," + node + ")");
	}

	private void createLink(String view, String src, String claz, String ref) {
		if (LOG)
			clog("parse - createLink ("+claz+")");
		addRole(IDiagraphAlphabet.DIAGRAPH_CMD_LINK + "(" + view + "," + src + "," + claz + ","
				+ (ref == null ? "_" : ref) + ")");
	}

	private void createLabels(String view, String name, String values) { //FP150601voir
		String v=values;
		if (LOG)
			clog("parse - createLabels ("+name+" "+v+")");
		if (values.contains(",")){
			v="[ ";
			String[] vals=values.split(",");
			for (int i = 0; i < vals.length; i++) {
				v+= vals[i].trim();
				if (i<vals.length)
					v+=",";
			}
			v=" ]";
		}
		addRole(IDiagraphAlphabet.DIAGRAPH_CMD_LABELS_ + "(" + view + "," + name + "," + v + ")");
	}

	private void createCref(String view, String src, String ref) {
		addRole(IDiagraphAlphabet.DIAGRAPH_CMD_CREF + "(" + view + "," + src + "," + ref + ")");
	}

	private void createKref(String view, String src, String ref) {
		addRole(IDiagraphAlphabet.DIAGRAPH_CMD_KREF + "(" + view + "," + src + "," + ref + ")");
	}

	private void createRef(String view, String src, String ref) {
		addRole(IDiagraphAlphabet.DIAGRAPH_CMD_REF + "(" + view + "," + src + "," + ref + ")");
	}

	private void createAfx(String view, String src, String ref) {
		addRole(IDiagraphAlphabet.DIAGRAPH_CMD_AFX + "(" + view + "," + src + "," + ref + ")");
	}

	private void createNav(String view, String name, String targetView) {
		addRole(IDiagraphAlphabet.DIAGRAPH_CMD_NAV + "(" + view + "," + name + "," + targetView
				+ ")");
	}

	@Override
	public int parseNotationEdge(String view, String element, String edg,
			List<String> cst, int l) {
		if (LOG)
			clog("parseNotationEdge " + element + "." + view);
		if (edg != null && !edg.isEmpty()) {
			edg = edg.trim();
			String[] edgvalues = edg.trim().split("=");
			if (edgvalues.length == 2) {
				String key = edgvalues[0];

				if (!key.isEmpty()) {
					String value = edgvalues[1];
					if (LOG)
						clog(key+"="+value);
					if (key.equals(IDiagraphAlphabet.DIAGRAPH_VOCAB_REF))
						parseRef(view, element, value);
					else if (key.equals(IDiagraphAlphabet.DIAGRAPH_VOCAB_KREF))
						parseKref(view, element, value);
					else if (key.equals(IDiagraphAlphabet.DIAGRAPH_VOCAB_CREF))
						parseCref(view, element, value);
					else if (key.equals(IDiagraphAlphabet.DIAGRAPH_VOCAB_LINK))
						parseLink(view, element, value);
					else if (key.equals(IDiagraphAlphabet.DIAGRAPH_VOCAB_AFX_))
						parseAfx(view, element, value);
					else if (key.equals(IDiagraphAlphabet.DIAGRAPH_VOCAB_NAV_))
						parseNav(view, element, value);
					else if (key.equals(IDiagraphAlphabet.DIAGRAPH_VOCAB_LABELS_)){
						parseLabels(view, element, value);
					}
					else
						error("(1) unknown keyword: "
								+ key);
				} else if (LOG)
					clog("(1) key is empty");

				// System.out.println("k=" + key);
				// System.out.println("v=" + value);
			} else if (edgvalues.length == 1) {
				String key = edgvalues[0];
				if (!key.isEmpty()) {

					if (key.equals(IDiagraphAlphabet.DIAGRAPH_VOCAB_POV_))
						parsePov(view, element);
					else if (key.equals(IDiagraphAlphabet.DIAGRAPH_VOCAB_GENERIC))
						parseGeneric(view, element);
					else
						error("(2) unknown keyword: "
								+ key);
				} else if (LOG)
					clog("(2) key is empty");

			} else
				error("(3) unknown keyword: " + edg.trim());
			// System.out.println("****n=" + edg.trim());
		} else
			error("should not happen in parseNotationEdge");
		return l;
	}

	private void error(String mesg) {
		textParser.error(mesg);
	}

	@Override
	public List<String> getRoles() {
		return roles;
	}

	@Override
	public void sortCommands() {
		List<String> sorted = new ArrayList<String>();
		for (String cmd : roles)
			if (cmd.startsWith(IDiagraphAlphabet.DIAGRAPH_CMD_POV_) && !sorted.contains(cmd))
				sorted.add(cmd);
		for (String cmd : roles)
			if (cmd.startsWith(IDiagraphAlphabet.DIAGRAPH_CMD_GEN) && !sorted.contains(cmd))
				sorted.add(cmd);
		for (String cmd : roles)
			if (cmd.startsWith(IDiagraphAlphabet.DIAGRAPH_CMD_NOD) && !sorted.contains(cmd))
				sorted.add(cmd);
		for (String cmd : roles)
			if (!cmd.startsWith(IDiagraphAlphabet.DIAGRAPH_CMD_NAV) && !sorted.contains(cmd))
				sorted.add(cmd);
		for (String cmd : roles)
			if (cmd.startsWith(IDiagraphAlphabet.DIAGRAPH_CMD_NAV) && !sorted.contains(cmd))
				sorted.add(cmd);
		roles = sorted;

	}



}
