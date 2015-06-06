package org.isoe.diagraph.operations.views;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.fwk.core.DParams;

public class DiagraphState {

	private static final boolean LOG = DParams.DiagraphState_LOG;
	private Diagram currentDiagram;
	private DGraph currentDiagraph;
	private EClass currentRoot;
	private EModelElement currentElement;
	private EPackage currentPackage;
	private String currentPov;
	private int currentPovId;

	private String focusedlanguage;
	private Diagram lastFocusedDiagram;
	private String langageName;
	private EClass selectedClass;
	private String settedView;
	private String[] layers;
	private static int logcount;
	private String newPov;
	private int newPovId;
	private Map<String, String> viewReg;

	private boolean layoutDone;
	private List<String> deployedLanguages;
	private List<IProject> deployedProjects;
	private int focusCounter;
	private IGraphicalEditPart currentGraphicalEditPart;
	private EObject prevObj;
	private String currentModel;
	private EObject currentEObject;
	private boolean is_headless;
	private EModelElement[] elementStack;
	private boolean redraw = true;
	private List<DGraph> currentDiagraphs;
	private int lwbPport = -1;
	private Diagram changedDiagram;
	private String langageNsUri;
	private String langageNsPrefix;

	private String megamodeluri;
	private String megamodelPath;

	private SandboxView owner;
	private String language_view;
	private String currentLanguage;

	public DiagraphState(SandboxView owner) {
		this.owner = owner;
	}

	private void copy() {
		currentLanguage = owner.getCurrentLanguage();
		currentDiagram = owner.getCurrentDiagram();
		//currentDiagram = owner.currentDiagram;
		lastFocusedDiagram = owner.lastFocusedDiagram;
		currentDiagraph = owner.getCurrentDiagraph();
		currentElement = owner.currentElement;
		currentPackage = owner.currentPackage;
		currentPov = owner.currentPov;
		currentPovId = owner.currentPovId;

		focusedlanguage = owner.focusedlanguage;
		currentRoot = owner.getCurrentRoot();
		langageName = owner.langageName;
		selectedClass = owner.selectedClass;
		settedView = owner.getCurrentView();
		layers = owner.layers_;

		newPov = owner.newPov;
		newPovId = owner.newPovId;
		viewReg = owner.getLayerMap();

		layoutDone = owner.layoutDone;
		deployedLanguages = owner.deployedLanguages_;
		deployedProjects = owner.deployedProjects;

		currentGraphicalEditPart = owner.currentGraphicalEditPart;
		prevObj = owner.prevObj;
		currentModel = owner.currentModel;
		currentEObject = owner.currentEObject;
		is_headless = owner.is_headless;
		elementStack = owner.elementStack;
		redraw = owner.redraw;
		currentDiagraphs = owner.getCurrentDiagraphs();
		// lwbPport = owner.lwbPport;
		changedDiagram = owner.changedDiagram;

		megamodeluri = owner.megamodeluri;
		megamodelPath = owner.megamodelPath;

	}

	public String logInitial(String language_view) {
		String log = "";
		copy();
		logcount++;
		try {
			log += "\nlanguage_view="
					+ (language_view == null ? "" : language_view);
			log += "\nlangageName.view="
					+ (langageName == null ? "" : langageName + ""
							+ owner.getRegisterdView(langageName));
			// String languag=getLanguag();
			// log+="languag.view="+(languag==null?"":languag+""+getRegisterdView(languag));
			log += "\nselectedClass="
					+ (selectedClass == null ? "" : selectedClass.getName());
			log += "\ncurrentDiagraph.name="
					+ (currentDiagraph == null ? "" : ((EClass) currentDiagraph
							.getPointOfView().getSemanticRole()).getEPackage()
							.getName());
			log += "\ncurrentDiagraph.view="
					+ (currentDiagraph == null ? "" : currentDiagraph
							.getViewName());
			log += "\ncurrentRoot="
					+ (currentRoot == null ? "" : currentRoot.getName());
			log += "\ncurrentDiagram="
					+ (currentDiagram == null ? "" : ((EPackage) currentDiagram
							.getElement()).getName());
			if (currentElement instanceof ENamedElement) {
				ENamedElement ne = (ENamedElement) currentElement;
				log += "\ncurrentElement=" + ne.getName();
			} else
				log += "\ncurrentElement=";
			log += "\nlangageName=" + (langageName == null ? "" : langageName);
			log += "\ncurrentPackage="
					+ (currentPackage == null ? "" : currentPackage.getName());
			log += "\nfocusedlanguage="
					+ (focusedlanguage == null ? "" : focusedlanguage);

			log += "\nlastFocusedDiagram="
					+ (lastFocusedDiagram == null ? ""
							: ((EPackage) lastFocusedDiagram.getElement())
									.getName());
			String logk = "";
			Set<String> ks = viewReg.keySet();
			for (String k : ks)
				logk += k + "=" + viewReg.get(k) + " ; ";
			log += "\nviewReg=" + logk;
			String logl = "";
			if (layers != null)
				for (String layer : layers)
					logl += layer + " ;";
			log += "\nviews=" + logl;
			log += "\nsettedView=" + (settedView == null ? "" : settedView);
			log += "\ncurrentPov=" + (currentPov == null ? "" : currentPov);
			log += "\ncurrentPovId=" + currentPovId;
			log += "\nnewPov=" + (newPov == null ? "" : newPov);
			log += "\nnewPovId=" + newPovId;

		} catch (Exception e) {
			e.printStackTrace();
		}

		clog(logcount + ">>-------logInitial-------");
		clog(log.trim());
		clog(logcount + "-------logInitial-------<<");
		return log;
	}

	private String getDGraphName(DGraph dgraph) {
		return ((EClass) dgraph.getPointOfView().getSemanticRole())
				.getEPackage().getName();
	}

	public static int getLogcount() {
		return logcount;
	}

	public String logDelta(String lv, DiagraphState other, String previouslog) {
		int l = 1;
		this.language_view = lv;

		String log = "";
		copy();

		logcount++;
		try {
			log += !language_view.equals(other.language_view) ? ("\n language_view=" + (language_view == null ?

			"null"
					: language_view))
					: "";
			l++;
			log += selectedClass != other.selectedClass ? ("\nselectedClass=" + (selectedClass == null ? "null"
					: selectedClass.getName()))
					: "";

			String dcs = "\ncurrentDiagraphs=";
			if (currentDiagraphs != null)
				for (DGraph dGraph : currentDiagraphs)
					dcs += getDGraphName(dGraph) + "." + dGraph.getViewName()
							+ " ;";
			else
				dcs += "null";

			String odcs = "\ncurrentDiagraphs=";
			if (other.currentDiagraphs != null)
				for (DGraph dGraph : other.currentDiagraphs)
					odcs += getDGraphName(dGraph) + "." + dGraph.getViewName()
							+ " ;";
			else
				odcs += "null";

			l++; // 4
			log += (!dcs.equals(odcs)) ? dcs : "";

			String cvn = ("\ncurrentDiagraph=" + (currentDiagraph == null ? "null"
					: getDGraphName(currentDiagraph) + "."
							+ currentDiagraph.getViewName()));

			String ocvn = ("\ncurrentDiagraph=" + (other.currentDiagraph == null ? "null"
					: getDGraphName(other.currentDiagraph) + "."
							+ other.currentDiagraph.getViewName()));

			l++;
			log += (!cvn.equals(ocvn)) ? cvn : "";

			l++;
			log += currentRoot != null && currentRoot != other.currentRoot ? ("\ndefaultRoot=" + (currentRoot ==

			null ? "null" : currentRoot.getName()))
					: "";

			String cdn = ("\ncurrentDiagram=" + (currentDiagram == null ? "null"
					: ((EPackage) currentDiagram.getElement()).getName()));
			String ocdn = ("\ncurrentDiagram=" + (other.currentDiagram == null ? "null"
					: ((EPackage) other.currentDiagram.getElement()).getName()));

			l++;
			log += (!cdn.equals(ocdn)) ? cdn : "";

			String cel = "";
			if (currentElement != null) {
				if (currentElement instanceof ENamedElement) {
					ENamedElement ne = (ENamedElement) currentElement;
					cel = "\ncurrentElement=" + ne.getName();
				} else
					cel = "\ncurrentElement=" + currentElement.toString();
			}

			String ocel = "";
			if (other.currentElement != null) {
				if (other.currentElement instanceof ENamedElement) {
					ENamedElement ne = (ENamedElement) other.currentElement;
					ocel = "\ncurrentElement=" + ne.getName();
				} else
					ocel = "\ncurrentElement="
							+ other.currentElement.toString();
			}

			l++;
			log += (!cel.equals(ocel)) ? cel : "";

			l++; // 8
			log += (langageName != null && !langageName
					.equals(other.langageName)) ? ("\nlangageName=" + (langageName == null ? "null"
					: langageName))
					: "";

			l++;
			log += currentPackage != other.currentPackage ? ("\ncurrentPackage=" + (currentPackage == null ?

			"null"
					: currentPackage.getName()))
					: "";

			l++;
			log += focusedlanguage != null
					&& !focusedlanguage.equals(other.focusedlanguage) ? ("\nfocusedlanguage=" +

			(focusedlanguage == null ? "null" : focusedlanguage))
					: "";

			String lfd = "\nlastFocusedDiagram="
					+ (lastFocusedDiagram == null ? "null"
							: ((EPackage) lastFocusedDiagram.getElement())
									.getName());
			String olfd = "\nlastFocusedDiagram="
					+ (other.lastFocusedDiagram == null ? "null"
							: ((EPackage) other.lastFocusedDiagram.getElement())
									.getName());

			l++; // 12
			log += (!lfd.equals(olfd)) ? lfd : "";

			String logk = "\nviewReg=";
			if (viewReg != null) {
				Set<String> ks = viewReg.keySet();
				for (String k : ks)
					logk += k + "=" + viewReg.get(k) + " ; ";
			} else
				logk += "null";

			String ologk = "\nviewReg=";
			if (other.viewReg != null) {
				Set<String> ks = other.viewReg.keySet();
				for (String k : ks)
					ologk += k + "=" + other.viewReg.get(k) + " ; ";
			} else
				ologk += "null";

			l++; // 13
			log += (!logk.equals(ologk)) ? logk : "";

			String logl = "\n" + currentLanguage + " views=";
			if (layers != null)
				for (String layer : layers) {
					if (!layer.equals("all layers"))
						logl += layer + " ; ";
				}
			else
				logl += "null";

			String ologl = "\n" + other.currentLanguage + " views=";
			if (other.layers != null) {
				for (String layer : other.layers)
					if (!layer.equals("all layers"))
						ologl += layer + " ; ";
			} else
				ologl += "null";

			l++;
			log += !logl.equals(ologl) ? (logl) : "";

			l++;
			log += settedView != null && settedView != other.settedView ? ("\nsettedView=" + (settedView == null

			? "null" : settedView))
					: "";

			l++;
			log += currentPov != null && currentPov != other.currentPov ? ("\ncurrentPov=" + (currentPov == null

			? "null" : currentPov))
					: "";

			l++;
			log += currentPovId != other.currentPovId ? ("\ncurrentPovId=" + currentPovId)
					: "";

			l++;
			log += newPov != null && newPov != other.newPov ? ("\nnewPov=" + (newPov == null ? "null"
					: newPov))
					: "";
			l++;
			log += newPovId != other.newPovId ? ("\nnewPovId=" + newPovId) : "";

		} catch (Exception e) {
			owner.cerror("logDelta error:(" + l + ")");
		}
		if (previouslog == null || !previouslog.equals(log)
				&& (!log.trim().isEmpty())) {
			clog(logcount + ">>-------logCurrents-------");
			clog(log.trim());
			clog(logcount + "-------logCurrents-------<<");

		}
		return log;

	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

}
