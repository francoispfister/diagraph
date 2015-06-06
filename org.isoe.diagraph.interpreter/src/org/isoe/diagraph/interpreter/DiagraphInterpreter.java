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
package org.isoe.diagraph.interpreter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.RTFTransfer;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.isoe.diagraph.diagraph.DGraph;
//import org.isoe.diagraph.generic.GenericConstants;
import org.isoe.extensionpoint.diagen.IDiagraphAlphabet;
import org.isoe.extensionpoint.interpreter.IDiagraphInterpreter;
import org.isoe.fwk.core.DParams;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.parts.DiagramDocumentEditor;
import org.eclipse.gmf.runtime.notation.Diagram;

/**
 *
 * @author fpfister handmade first approach of an interpretable textual syntax;
 *         TODO: use dedicated tools
 */
import org.isoe.diagraph.controler.IDiagraphControler; //FP140707refactored

public class DiagraphInterpreter implements IDiagraphInterpreter {

	private static final boolean LOG = DParams.DiagraphInterpreter_LOG;
	private static final boolean JOB_LOG_ = DParams.MegaModelBuilderJOB_LOG;
	private IDiagraphControler owner;
	private Map<String, Long> timestamps = new HashMap<String, Long>();

	public DiagraphInterpreter(IDiagraphControler owner) {
		this.owner = owner;
		initStyler();
	}

	private String invokeMegamodelJob(String language, String[] args, boolean headless, boolean regenerate) { // FP140528
		String result = "ok";
		if (LOG)
			clog("invokeMegamodelJob " + args[0] + " " + args[1] + (regenerate ? " regenerate" : ""));
		boolean refreshOnlyYes = true;
		result = owner.invokeMegamodelJob(language, headless, args, regenerate, refreshOnlyYes);
		return result;
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	private void jobclog(String mesg) {
		if (JOB_LOG_)
			System.out.println(mesg);
	}

	private String execDiagraphEditor(String[] args, boolean headless) { // FP140528
		String result = "ok";
		if (LOG)
			clog("execDiagraphEditor " + args[0] + " " + args[1]);
		boolean refreshOnlyNo = false;
		boolean regenerateYes = false;
		result = owner.invokeMegamodelJob(null, headless, args, regenerateYes, refreshOnlyNo);
		return result;
	}

	private void iclog(String mesg) {
		if (DParams.Interpreter_LOG)
			System.out.println(mesg);
	}

	private String executeGrammarOrRolesCommand(String[] loc, String[] args, boolean headless) {
		if (LOG)
			clog("executeGrammarOrEditorCommand " + args[0] + " " + loc[1]);
		String result = "ok";
		if (args[0].equals(IDiagraphAlphabet.MEGAMODEL_GEN_DSML)) {
			result = genGrammar(loc, args, "textual concrete syntax grammar", headless);
		} else if (args[0].startsWith(IDiagraphAlphabet.MEGAMODEL_GEN_ROLES)) {
			result = genEditor(loc, args, "textual concrete syntax roles", headless);
		}
		if (!result.equals("ok")) {
			String lang = loc[1].substring(loc[1].lastIndexOf(".") + 1);
			reset(loc[0], result, lang);
		}
		return result;
	}

	private void reset(String location, String mesg, String language) {
		// language =owner.getCurrentPackage().getName();
		initGrammar(location, language);
		initEditor(location, language);
		owner.getDiagraphScriptText().setText("");
		owner.getDiagraphResponseText().setText("");
		owner.setCmdResponse(mesg);
		if (LOG)
			clog("execution error " + mesg);
	}

	@Override
	public void clear() {
		if (LOG)
			clog("clear console");
		owner.getDiagraphScriptText().setText("");
		owner.getDiagraphResponseText().setText("");
	}

	private String[] parseStatement(String statement) {
		String[] args = new String[2];
		if (statement.contains(" ")) {
			args = statement.split(("\\s+"));
		} else if (statement.contains("(")) {
			args = statement.split("\\(");
			args[1] = args[1].substring(0, args[1].length() - 1);
		} else {
			args[0] = statement;
			args[1] = "";
		}
		if (args.length > 2) {
			String[] nargs = new String[2];
			nargs[0] = args[0];
			nargs[1] = args[1];
			for (int i = 2; i < args.length; i++) {
				if (i < args.length)
					nargs[1] += ",";
				nargs[1] += args[i];
			}
			return nargs;
		} else
			return args;
	}

	private String setUnknownStatement(String statement) {
		String result = "(3)unknown statement " + statement;
		owner.getDiagraphScriptText().setText("");
		owner.getDiagraphResponseText().setText("");
		owner.setCmdResponse(result);
		cerror(result);
		return result;
	}

	private void cerror(String mesg) {
		owner.cerror(mesg);
	}

	private void cinfo(String mesg) {
		owner.cinfo(mesg);
	}

	private String setError(String mesg) {
		String result = (mesg == null || mesg.isEmpty()) ? "error in a console command" : mesg;
		owner.getDiagraphScriptText().setText("");
		owner.getDiagraphResponseText().setText("");
		owner.setCmdResponse(result);
		cerror(result);
		return result;
	}

	private String cmdError(String commandLine) {
		String cmds = "";
		for (String knownCommand : IDiagraphAlphabet.EXISTING_COMMANDS)
			cmds += knownCommand + "\n";
		reset("", "not understood: " + commandLine + "\nallowed commands are:\n" + cmds.trim(), "");
		return "unknown command";
	}

	@Override
	public void executeCommandLine(String content) { //FP141227aa
		String cmd = "unknown_command";
		if (content.contains(IDiagraphAlphabet.MEGAMODEL_GEN_DSML))
			cmd = IDiagraphAlphabet.MEGAMODEL_GEN_DSML; // FP140623
		else if (content.contains(IDiagraphAlphabet.MEGAMODEL_ACTION_CLONE)) {
			cmd = IDiagraphAlphabet.MEGAMODEL_ACTION_CLONE; // FP140623
			owner.setClonedPackage(null);
		} else if (content.contains(IDiagraphAlphabet.MEGAMODEL_ACTION_MERGE)) {
			cmd = IDiagraphAlphabet.MEGAMODEL_ACTION_MERGE; // FP140623
			owner.setClonedPackage(null);
		} else if (content.contains(IDiagraphAlphabet.MEGAMODEL_FIND)) {
			cmd = IDiagraphAlphabet.MEGAMODEL_FIND; // FP140625
		} else if (content.contains(IDiagraphAlphabet.MEGAMODEL_DIAGRAPH)) {
			cmd = IDiagraphAlphabet.MEGAMODEL_DIAGRAPH; // FP140625
		} else if (content.contains(IDiagraphAlphabet.MEGAMODEL_ALL)) {
			cmd = IDiagraphAlphabet.MEGAMODEL_ALL; // FP140625
		} else if (content.contains(IDiagraphAlphabet.MEGAMODEL_GEN_ROLES)) {
			cmd = IDiagraphAlphabet.MEGAMODEL_GEN_ROLES; // FP140623
		}

		String result = executeCommandLine(false, cmd, content);
		if (!result.equals("ok"))
			cerror("error " + result + " " + cmd);
		else
			cinfo(">ok");
	}


	private String executeCommand(String[] location, boolean headless,String[]parsedStatements){
		    String result="error";
			String[] args = setCurrentLanguageIfNot(parsedStatements);
			if (parsedStatements[0].equals(IDiagraphAlphabet.MEGAMODEL_GEN_DSML)
					|| parsedStatements[0].startsWith(IDiagraphAlphabet.MEGAMODEL_GEN_ROLES)) {

				result = executeGrammarOrRolesCommand(location, parsedStatements, headless);
			} else if (parsedStatements[0].equals(IDiagraphAlphabet.MEGAMODEL_ACTION_CLONE)) {
				result = executeCloneCommand(location, parsedStatements, headless);
			}/*
			 * else if (parsedStatements[0]
			 * .equals(IDiagraphAlphabet.MEGAMODEL_ACTION_GENEDITOR)) { if
			 * (reenter) tb = true;
			 *
			 * result = executeGenEditorCommand(location, parsedStatements,
			 * headless); }
			 */else if (parsedStatements[0].equals(IDiagraphAlphabet.MEGAMODEL_ACTION_MERGE)) {

				result = executeMergeCommand(location, parsedStatements, headless);
			} else if (parsedStatements[0].startsWith(IDiagraphAlphabet.MEGAMODEL_FIND)) {

				result = executeFindCommand(location, args, headless);
			}

			else if (parsedStatements[0].startsWith(IDiagraphAlphabet.MEGAMODEL_ALL)) {
				result = executeAllCommand(location, args, headless);
			} else if (parsedStatements[0].startsWith(IDiagraphAlphabet.MEGAMODEL_DIAGRAPH)) {
				result = executeDiagraphCommand(location, args, headless);
			} else
				result = setUnknownStatement(parsedStatements[0]);
		return result;
	}


	// @Override
	private String executeCommandLine(boolean reenter, String cmd, String commandLine) {//FP150305

		boolean headless = false;
		String result = "ok";
		if (cmd.equals("unknown_command"))
			return cmdError(commandLine);
		try {
			String statement = owner.getDiagraphStatements().trim();
			if (statement.isEmpty()) {
				owner.setDiagraphStatements(cmd);
				executeCommandLine(true, cmd, commandLine);
				return "empty command";
			}
			String[] parsedStatements = parseStatement(statement);// [clone,
																	// helloworld,urit]
			String locs = parsedStatements[1];
			if ("*".equals(locs)){
				 List<String[]> allLocations= owner.getAllDiagraphedProjectLocations(); //FP150113
				 for (String[] aLocation : allLocations) {
					  String[] reParsedStatements = new String[2];
					  reParsedStatements[0] = parsedStatements[0];
					  String lang = aLocation[1];
					  lang = lang.substring(lang.lastIndexOf(".")+1);
					  reParsedStatements[1] = lang;
					  String r = executeCommand(aLocation, headless, reParsedStatements);
				}
			} else{

				   if (locs.contains(",")) {
						String[] ps = locs.split(",");
						locs = ps[0];
					}
					String[] location = owner.getDiagraphedProjectLocation(locs);
					if (location != null)
						executeCommand(location, headless, parsedStatements);
					else
						result = setError("project " + parsedStatements[1] + " not found");


			}
		} catch (Exception e) {
			result = "error while " + cmd + " (" + e.toString() + ")";
			cerror(result);
		}

		return result;
	}

	/*
	 * public String executeCommandLine_old(boolean reenter, String cmd) {
	 * boolean headless = false; //
	 * cmd=IDiagraphAlphabet.MEGAMODEL_GEN_GRAMMAR_; String result = "ok"; try {
	 * String statement = owner.getDiagraphStatements().trim(); if
	 * (statement.isEmpty()) { owner.setDiagraphStatements(cmd);
	 * executeCommandLine(true, cmd); return "empty command"; } String[]
	 * parsedStatements = parseStatement(statement); String locs =
	 * parsedStatements[1]; if (locs.contains(",")) { String[] ps =
	 * locs.split(","); locs = ps[0]; } String[] location =
	 * owner.getDiagraphedProjectLocation(locs);
	 *
	 * if (location != null) { String[] langs =
	 * setCurrentLanguageIfNot(parsedStatements); if (parsedStatements[0]
	 * .equals(IDiagraphAlphabet.MEGAMODEL_GEN_GRAMMAR_) || parsedStatements[0]
	 * .startsWith(IDiagraphAlphabet.MEGAMODEL_GENSCRIPT)) {
	 *
	 * result = executeGrammarOrEditorCommand(location, parsedStatements,
	 * headless); } else if (parsedStatements[0]
	 * .equals(IDiagraphAlphabet.MEGAMODEL_ACTION_CLONE_)) { if (reenter) tb =
	 * true;
	 *
	 * result = executeCloneCommand(location, parsedStatements, headless); }
	 * else if (parsedStatements[0]
	 * .startsWith(IDiagraphAlphabet.MEGAMODEL_FIND)) { if (reenter) tb = true;
	 *
	 * result = executeFindCommand(location, langs, headless); } else result =
	 * setUnknownStatement(parsedStatements[0]); } else result =
	 * setError("project " + parsedStatements[1] + " not found"); } catch
	 * (Exception e) { result = "error while " + cmd + " (" + e.toString() +
	 * ")"; cerror(result); }
	 *
	 * return result; }
	 */
	private String[] setCurrentLanguageIfNot(String[] args) { // FP140625
		String currentLanguage = owner.getLanguageName();
		String tlang;
		String[] langs = new String[args.length];
		langs[0] = args[0];
		if (args.length > 1) {
			if (args[1] != null && args[1].contains(","))
				langs[1] = args[1].split(",")[0];
			else
				langs[1] = args[1];
		}
		if (langs.length == 2 && (langs[1] != null && !langs[1].isEmpty())) {
			tlang = langs[1];
			if (!currentLanguage.equals(tlang)) {
				if (DParams.merge_LOG)
					clogmerge("setCurrentLanguageIfNot :" + currentLanguage + "->" + tlang);
				owner.get(tlang);
			}
		} else
			tlang = currentLanguage;
		String[] result = new String[2];
		result[0] = currentLanguage;
		result[1] = tlang;
		return result;
	}

	//FP141227int

	private void initGrammar(String path, String lang) { // FP140528
		// String currentPakage_ = owner.getCurrentPackage().getName();
		String rtfFileName1 = path + lang + IDiagraphAlphabet.DIAGEN_CST_RTF_EXT__;
		File fil = new File(rtfFileName1);
		if (fil.exists())
			fil.delete();
		String rtfFileName2 = path + lang + IDiagraphAlphabet.DIAGEN_CMD_RTF_EXT;
		fil = new File(rtfFileName2);
		if (fil.exists())
			fil.delete();
		String cstFilename = path + lang + "." + IDiagraphAlphabet.DIAGEN_CST_EXT;
		fil = new File(cstFilename);
		if (fil.exists())
			fil.delete();
		String cmdFileName_ = path + lang + "." + IDiagraphAlphabet.DIAGEN_CMD_EXT;
		fil = new File(cmdFileName_);
		if (fil.exists())
			fil.delete();
	}

	private void initEditor(String path, String lang) { // FP140528
		// currentPakage_=owner.getCurrentPackage().getName();
		String cmdFileName_ = path + lang + "." + IDiagraphAlphabet.DIAGEN_CMD_EXT;
		File fil = new File(cmdFileName_);
		if (fil.exists())
			fil.delete();
		String rtfFileName = path + lang + IDiagraphAlphabet.DIAGEN_CMD_RTF_EXT;
		fil = new File(rtfFileName);
		if (fil.exists())
			fil.delete();
	}

	private void syntaxHighlight(String textFileName, String rtfFileName, StyledText st) {
		st.setText(readFile(textFileName, "\n"));
		// lineStyler_.parseBlockComments(cst);
		Point oldSelection = st.getSelection();
		st.selectAll();
		st.copy();
		st.setSelection(oldSelection);
		Display displ = owner.getDisplay();
		writeFile(rtfFileName, (String) new Clipboard(displ).getContents(RTFTransfer.getInstance()));
	}

	private boolean isDirty(String[] location) {
		boolean dirty = false;
		String langagename = location[1].substring(location[1].lastIndexOf(".") + 1);
		File file = new File(location[0] + langagename + "." + "ecore");
		if (!file.exists())
			throw new RuntimeException("should not happen in isDirty");
		Long last = timestamps.get(langagename);
		if (last == null)
			dirty = true;
		owner.saveAllEditors();
		long lastModified = file.lastModified();
		timestamps.put(langagename, new Long(lastModified));
		if (last != null && !last.equals(lastModified))
			dirty = true;
		return dirty;
	}

	private String readFile(String path, String lineSeparator) {
		StringBuffer buffer = new StringBuffer();
		try {
			InputStream in = new FileInputStream(new File(path));
			try {
				InputStreamReader inR = new InputStreamReader(in);
				BufferedReader buf = new BufferedReader(inR);
				String line;
				while ((line = buf.readLine()) != null)
					buffer.append(line).append(lineSeparator);
			} finally {
				in.close();
			}
		} catch (IOException e) {
			cerror("DGTX_RF  " + "no file " + path);
			return "";
		}
		return buffer.toString();
	}

	private void writeFile(String path, String content) {
		if (path != null && !path.isEmpty() && content != null && !content.isEmpty()) {
			FileWriter fw;
			try {
				fw = new FileWriter(new File(path), false);
				fw.write(content);
				fw.close();
			} catch (IOException e) {
				cerror("DGTX_WF  " + e.toString());
			}
		} else
			cerror("nothing to save (" + path + ")");
	}

	/*----------------------------------------*/

	private void clogmerge(String mesg) {
		if (DParams.merge_LOG)
			System.out.println(mesg);
	}

	private DGraph doFind(String mergeWith, boolean headless) { // FP140627cms
		// return owner.getMergeDGraph();
		String[] args = new String[2];
		args[0] = "find";
		args[1] = mergeWith;
		boolean genLanguage = false;
		boolean refreshOnly = true;
		String result = owner.invokeMegamodelJob(null, headless, args, genLanguage, refreshOnly);// null,false,[find,
																									// matoto],false,false
		if (result.equals("ok"))
			return owner.getCurrentDGraph();
		else
			return null;
	}

	private EPackage doMerge_(String cloneLanguage, String root, String lang, String mergeWith, DGraph src,
			DGraph merg, boolean headless, boolean byscript) { // FP140627cmd

		if (DParams.merge_LOG || DParams.MegaModelBuilderJOB_LOG) {
			String c = src.getFacade1().getLanguageName() + "." + src.getViewName();
			String m = merg.getFacade1().getLanguageName() + "." + merg.getViewName();
			String l = "merge language";
			l += " src: currentDGraph=";
			l += c;
			l += " mrg: mergeDGraph=";
			l += m;
			l += " trg=" + cloneLanguage;
			l += (headless ? " headless" : " not headless");
			l += " root=" + root;// owner.getCurrentRootName();
			if (DParams.merge_LOG)
				clogmerge(l);
			else if (DParams.MegaModelBuilderJOB_LOG)
				jobclog(l); // src: currentDGraph=statechart.default mrg:
							// mergeDGraph=helloworld.default trg= not headless
							// root=State
		}
		EPackage srp = owner.getSourcePackage();// owner.getCurrentPackage();
		EPackage clop = owner.getClonedPackage();
		if ((clop != null || clop == srp) && !byscript)
			throw new RuntimeException("should not happen in doMerge");
		return owner.merge(cloneLanguage, src, merg, headless, byscript);
	}

	private EPackage doClone(String cloneLanguage, boolean headless) {

		DGraph curnt = owner.getCurrentDGraph();

		if (DParams.merge_LOG || DParams.MegaModelBuilderJOB_LOG) {
			String c = curnt.getFacade1().getLanguageName() + "." + curnt.getViewName();
			String l = "clone language";
			l += " src: currentDGraph=";
			l += c;
			l += " trg=" + (cloneLanguage != null ? cloneLanguage : "(no name)");
			l += (headless ? " headless" : " not headless");
			l += " root=" + owner.getCurrentRootName();
			if (DParams.merge_LOG)
				clogmerge(l);
			else if (DParams.MegaModelBuilderJOB_LOG)
				jobclog(l);
		}
		EPackage curp = owner.getCurrentPackage();
		EPackage clop = owner.getClonedPackage();
		if (clop != null || clop == curp)
			throw new RuntimeException("should not happen in doClone");
		return owner.clone(cloneLanguage, headless);
	}

	@Override
	public void mergeLanguagesAndGenGrammar(String lang, String with, String cloneLanguage, boolean headless) {
		boolean ingui = (lang == null || lang.isEmpty() || with == null || with.isEmpty());
		boolean byscript = !ingui;
		EClass currentRoot = owner.getCurrentRoot();// World
		if (lang == null || lang.isEmpty())
			lang = currentRoot.getEPackage().getName();
		DGraph src = owner.getCurrentDGraph();
		EPackage epak = null;// owner.getCurrentPackage();
		DGraph merg = null;
		owner.setSourcePackage(owner.getCurrentPackage());
		owner.setSourceDiagram(owner.getCurrentDiagram());
		owner.setSourceDGraph(owner.getCurrentDGraph());
		if (byscript) {
			merg = doFind(with, headless);
			epak = owner.getCurrentPackage();
			owner.setMergePackage(owner.getCurrentPackage());
			owner.setMergeDiagram(owner.getCurrentDiagram());
			owner.setMergeDGraph(merg);

		} else {
			merg = owner.getMergeDGraph();
			epak = owner.getCurrentPackage();
			owner.setMergePackage(epak);
			owner.setMergeDiagram(owner.getCurrentDiagram());
		}
		boolean prep = owner.prepareMerge(byscript, currentRoot, epak);
		if (byscript || prep) {
			String rootn = currentRoot.getName();// owner.getCurrentRootName();
			EPackage p = doMerge_(cloneLanguage, rootn, lang, with, src, merg, headless, byscript);
			if (p != null) {
				owner.finalizeMerge(p);
				genGrammar(p.getName(), "merged", IDiagraphAlphabet.MEGAMODEL_GEN_DSML, headless);
				owner.bringToTop(p);
			} else
				cerror("merge stopped");
		}
	}

	private void generateGmfEditorAndGenGrammar_TODO(String lang, boolean headless) {

		// if (ge)
		genGrammar(lang, "editor generated", IDiagraphAlphabet.MEGAMODEL_GEN_DSML, headless);

		owner.bringToTop(owner.getCurrentPackage());

	}

	@Override
	public void cloneLanguageAndGenGrammar(String cloneLanguage, boolean headless) {
		owner.setClonedPackage(null);
		if (owner.prepareClone()) {

			owner.setSourcePackage(owner.getCurrentPackage());
			owner.setSourceDiagram(owner.getCurrentDiagram());

			EPackage p = doClone(cloneLanguage, headless);
			if (p != null) { // FP140623
				owner.finalizeClone(p);
				// owner.clearClone_();
				genGrammar(p.getName(), "cloned", IDiagraphAlphabet.MEGAMODEL_GEN_DSML, headless);
				owner.bringToTop(p);
			} else
				owner.cerror("clone stopped");
		}
	} // FP140623aa

	private String findLanguageAndGenGrammar(String currentLang, String toFind, boolean headless) { // FP140625
		String[] args = new String[1];
		args[0] = IDiagraphAlphabet.MEGAMODEL_FIND;
		String result = owner.invokeMegamodelJob(toFind, headless, args, true, true);
		if (result.equals("ok"))
			genGrammar(toFind, "found", IDiagraphAlphabet.MEGAMODEL_GEN_DSML, headless);
		// owner.bringToTop(toFind);
		return result;
	}

	private String findAndGenerateDiagraphTODO(String currentLang, boolean headless) {
		String[] args = new String[1];
		args[0] = IDiagraphAlphabet.MEGAMODEL_FIND;
		String result = owner.invokeMegamodelJob("", headless, args, true, true);
		// if (result.equals("ok"))
		// genGrammar(toFind, "found",
		// IDiagraphAlphabet.MEGAMODEL_GEN_DSML, headless);
		// owner.bringToTop(currentLang);
		return result;
	}

	private String findAndGenerateAllTODO(String currentLang, boolean headless) {
		String[] args = new String[1];
		args[0] = IDiagraphAlphabet.MEGAMODEL_FIND;
		String result = owner.invokeMegamodelJob("", headless, args, true, true);
		// if (result.equals("ok"))
		// genGrammar(toFind, "found",
		// IDiagraphAlphabet.MEGAMODEL_GEN_DSML, headless);
		// owner.bringToTop(currentLang);
		return result;

	}

	@Override
	public void buildAll(boolean generateEditors, boolean headless) {
		if (owner.prepareBuild(headless)) {
			owner.clearMegamodel();
			boolean genLanguageYes = true; // FP140602see
			boolean refreshOnlyNo = !generateEditors;
			List<String> prjs = owner.getDiagraphProjectNames();
			for (String prj : prjs) {
				if (JOB_LOG_)
					jobclog("Building " + prj);

				if (prj.equals("obeotraining"))
					clog("DIP  build " + prj);

				try {
					String[] args = new String[2];
					args[0] = IDiagraphAlphabet.MEGAMODEL_GEN_DSML;
					args[1] = prj; // FP140605
					// megamodelMan.clearMegamodel_(); //FP140622
					String result = owner.invokeMegamodelJob(null, headless, args, genLanguageYes, refreshOnlyNo);
					if (LOG)
						clog(result);
					// sleep(20);
					// generateLanguage(headless,this, prj);
				} catch (Exception e) {
					cerror("error while testing " + prj);
				}
			}
			owner.endBuild(false);
		}
	}



	@Override
	public void copyAll(boolean generateEditors, boolean headless) {

			List<String> prjs = owner.getDiagraphProjectNames();
			for (String prj_ : prjs) {
				if (JOB_LOG_)
					jobclog("Copying " + prj_);
				try {
					String[] args = new String[2];
					args[0] = IDiagraphAlphabet.MEGAMODEL_GEN_DSML;
					args[1] = prj_; // FP140605
					// megamodelMan.clearMegamodel_(); //FP140622
					String result = owner.invokeCopyJob(headless, args,"m2");
					if (LOG)
						clog(result);
				} catch (Exception e) {
					cerror("error while copying " + prj_);
				}
			}
			owner.endBuild(false);

	}



	private void genGrammar(String langName, String op, String cmd, boolean headless) {
		// cmd=IDiagraphAlphabet.MEGAMODEL_GEN_GRAMMAR_;
		String[] location = owner.getDiagraphedProjectLocation(langName);
		String[] ss = new String[2];
		ss[0] = cmd;// IDiagraphAlphabet.MEGAMODEL_GEN_GRAMMAR;
		ss[1] = langName;

		String result = genGrammar(location, ss, "textual concrete syntax grammar", headless);
		if (!result.equals("ok"))
			reset(location[0], result, langName);

		// if (!headless)
		// owner.showMessage("Language " + op + ": "
		// + langName);
	}

	/*--------------------------------*/

	private String genEditor(String[] loc, String[] args, String label, boolean headless) { // FP140528
		String cmd = args[0];
		if (LOG)
			clog(cmd + " " + loc[0]);
		owner.starts(cmd);
		owner.getDiagraphScriptText().setText("");
		boolean dirty = isDirty(loc);
		if (dirty) {
			if (LOG)
				clog("language is dirty, regenerate !");
			return "language is dirty, regenerate !";
		}
		String lang = loc[1].substring(loc[1].lastIndexOf(".") + 1);
		// lang=owner.getCurrentPackage().getName();
		initEditor(loc[0], lang);
		String result = execDiagraphEditor(args, headless);
		if (result.equals("ok"))
			result = scriptOutput(lang, loc, cmd, label);
		owner.ends(cmd);
		owner.refreshProject(loc[1]);
		return result;
	}

	private String scriptOutput(String lang, String[] loc, String cmd, String label) {
		String cmdFileName = loc[0] + owner.getCurrentPackage().getName() + "." + IDiagraphAlphabet.DIAGEN_CMD_EXT;
		String rtfFileName = loc[0] + owner.getCurrentPackage().getName() + IDiagraphAlphabet.DIAGEN_CMD_RTF_EXT;
		File fil = new File(cmdFileName);
		if (fil.exists()) {
			syntaxHighlight(cmdFileName, rtfFileName, owner.getDiagraphScriptText());
			return "ok";
		} else {
			String error = InterpreterPlugin.getInstance().getErrors();
			InterpreterPlugin.getInstance().setErrors("");
			return ("no " + label + " for " + loc[1] + "\nfile " + cmdFileName + " is missing " + "\ncause is:" + error);
		}
	}

	private String grammarOutput(String lang, String[] loc, String cmd, String label) {
		String cstFilename = loc[0] + lang + "." + IDiagraphAlphabet.DIAGEN_CST_EXT;
		String rtfFileName = loc[0] + lang + IDiagraphAlphabet.DIAGEN_CST_RTF_EXT__;
		File fil = new File(cstFilename);
		if (fil.exists()) {
			syntaxHighlight(cstFilename, rtfFileName, owner.getDiagraphResponseText());
			return "ok";
		} else {
			String error = InterpreterPlugin.getInstance().getErrors();
			InterpreterPlugin.getInstance().setErrors("");
			String sresult = "no " + label + " for " + loc[1] + "\nfile " + cstFilename + " is missing "
					+ "\ncause is:" + error;
			if (JOB_LOG_)
				jobclog(cmd + " problem:" + sresult);
			return (sresult);
		}
	}

	private String genGrammar(String[] loc, String[] args, String label, boolean headless) {
		String cmd = args[0];
		if (LOG)
			clog("genGrammar cmd=" + cmd + " loc=" + loc[0]);
		else if (JOB_LOG_)
			jobclog("genGrammar cmd=" + cmd + " loc=" + loc[0]);
		owner.starts(cmd);
		boolean dirty = isDirty(loc);
		owner.getDiagraphScriptText().setText("");
		owner.getDiagraphResponseText().setText("");
		String lang = loc[1].substring(loc[1].lastIndexOf(".") + 1);
		initGrammar(loc[0], lang);
		// if (!headless)
		owner.get(lang);
		String result = invokeMegamodelJob(lang, args, headless, dirty);//build
		if (result.equals("ok"))
			result = grammarOutput(lang, loc, cmd, label);
		owner.ends(cmd);
		owner.refreshProject(loc[1]);
		return result;
	}

	/*----------------------------------*/

	private String executeCloneCommand(String[] loc, String[] args, boolean headless) {
		String[] subargs = args[1].split(",");
		String srcLang = subargs[0];
		String trgLang = subargs.length > 1 ? subargs[1] : "no name";
		if (DParams.Interpreter_LOG)
			iclog(">    clone(" + srcLang + ", " + trgLang + ")");

		if (DParams.MegaModelBuilderJOB_LOG)
			jobclog("executeCloneCommand " + srcLang + " => " + trgLang + " " + loc[1]); // executeCloneCommand
																							// clone
																							// lang.m2.helloworld
																							// sender=0
		if (LOG)
			clog("executeCloneCommand " + srcLang + " => " + trgLang + " " + loc[1]);
		String result = "error while cloning";
		if (args[0].equals(IDiagraphAlphabet.MEGAMODEL_ACTION_CLONE)) {
			cloneLanguageAndGenGrammar(subargs.length > 1 ? trgLang : null, headless);
			result = "ok";
		}
		if (!result.equals("ok")) {
			String lang = loc[1].substring(loc[1].lastIndexOf(".") + 1);
			reset(loc[0], result, lang);
		}
		return result;
	}

	private String executeGenGmfEditorCommand(String[] loc, String[] args, boolean headless) {
		if (DParams.Interpreter_LOG)
			iclog(">    geneditor(" + args[1] + ")");
		if (DParams.MegaModelBuilderJOB_LOG)
			jobclog("executeGenEditorCommand " + args[1] + " " + loc[1]);
		if (LOG)
			clog("executeGenEditorCommand " + args[1] + " " + loc[1]);
		String result = "error while GenGmf";
		if (args[0].equals(IDiagraphAlphabet.MEGAMODEL_GEN_GMF_EDITOR)) {
			generateGmfEditorAndGenGrammar_TODO(args[1], headless);
			result = "ok";
		}
		if (!result.equals("ok")) {
			String lang = loc[1].substring(loc[1].lastIndexOf(".") + 1);
			reset(loc[0], result, lang);
		}
		return result;
	}

	private String executeDiagraphCommand(String[] loc, String[] args, boolean headless) {
		if (DParams.Interpreter_LOG)
			iclog(">    diagraph(" + args[1] + ")");
		if (DParams.MegaModelBuilderJOB_LOG)
			jobclog("executeDiagraphCommand " + args[1] + " " + loc[1]);
		if (LOG)
			clog("executeDiagraphCommand " + args[1] + " " + loc[1]);
		String result = "error while executeDiagraphCommand - not yet implemented  ";
		args[0] = IDiagraphAlphabet.MEGAMODEL_DIAGRAPH;

		// if (args[0].equals(IDiagraphAlphabet.MEGAMODEL_DIAGRAPH)) {
		// findAndGenerateDiagraphTODO(args[1], headless);
		// result = "ok";
		// }
		if (!result.equals("ok")) {
			String lang = loc[1].substring(loc[1].lastIndexOf(".") + 1);
			reset(loc[0], result, lang);
		}
		return result;
	}

	private String executeAllCommand(String[] loc, String[] args, boolean headless) {
		if (DParams.Interpreter_LOG)
			iclog(">    all(" + args[1] + ")");
		if (DParams.MegaModelBuilderJOB_LOG)
			jobclog("executeAllCommand " + args[1] + " " + loc[1]);
		if (LOG)
			clog("executeAllCommand " + args[1] + " " + loc[1]);
		String result = "error while executeAllCommand - not yet implemented ";
		// args[0] = IDiagraphAlphabet.MEGAMODEL_ALL;
		// if (args[0].equals(IDiagraphAlphabet.MEGAMODEL_ALL)) {
		// findAndGenerateAllTODO(args[1], headless);
		// result = "ok";
		// }
		if (!result.equals("ok")) {
			String lang = loc[1].substring(loc[1].lastIndexOf(".") + 1);
			reset(loc[0], result, lang);
		}
		return result;
	}

	private String executeMergeCommand(String[] loc, String[] args, boolean headless) {
		String[] subargs = args[1].split(",");
		String srcLang = subargs[0];
		String withLang = subargs[1];
		String trgLang = subargs[2];
		if (DParams.Interpreter_LOG)
			iclog(">    merge(" + srcLang + ", " + withLang + ", " + trgLang + ")");

		if (DParams.MegaModelBuilderJOB_LOG)
			jobclog("executeMergeCommand " + srcLang + " U " + withLang + " => " + trgLang + " " + loc[1]); // executeCloneCommand
																											// clone
																											// lang.m2.helloworld
																											// sender=0
		if (LOG)
			clog("executeMergeCommand " + srcLang + " U " + withLang + " => " + trgLang + " " + loc[1]);
		String result = "error while cloning";
		if (args[0].equals(IDiagraphAlphabet.MEGAMODEL_ACTION_MERGE)) {

			mergeLanguagesAndGenGrammar(srcLang, withLang, trgLang, headless);
			// cloneLanguageAndGenGrammar(trgLang, headless);
			result = "ok";
		}
		if (!result.equals("ok")) {
			String lang = loc[1].substring(loc[1].lastIndexOf(".") + 1);
			reset(loc[0], result, lang);
		}
		return result;
	}

	private String executeFindCommand(String[] loc, String[] langs, boolean headless) {
		String result = "error while finding";
		String currentLang = langs[0];
		String givenLang = langs[1];

		if (DParams.Interpreter_LOG)
			iclog(">    find(" + givenLang + ")");

		if (DParams.MegaModelBuilderJOB_LOG)
			jobclog("executeFindCommand " + currentLang + " => " + givenLang + " " + loc[1]); // executeCloneCommand
																								// clone
																								// lang.m2.helloworld
																								// sender=0
		result = findLanguageAndGenGrammar(currentLang, givenLang, headless);

		if (!result.equals("ok")) {
			String lang = loc[1].substring(loc[1].lastIndexOf(".") + 1);
			reset(loc[0], result, lang);
		}
		return result;
	}

	@Override
	public void initStyler() {
		owner.getDiagraphResponseText().addLineStyleListener(
				new DiagraphLineStyler("GRAMMAR_BY_VIEWs", IDiagraphAlphabet.DIAGRAPH_KEYWORDS_));
		owner.getDiagraphScriptText().addLineStyleListener(
				new DiagraphLineStyler("GRAMMAR_BY_ROLES", IDiagraphAlphabet.DIAGRAPH_ROLE_KW_));
	}

}
