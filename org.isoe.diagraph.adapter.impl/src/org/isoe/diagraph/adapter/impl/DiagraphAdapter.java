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
package org.isoe.diagraph.adapter.impl;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecoretools.legacy.diagram.edit.parts.EAnnotationDetailsEditPart;
import org.eclipse.emf.ecoretools.legacy.diagram.edit.parts.EAnnotationEditPart;
import org.eclipse.emf.ecoretools.legacy.diagram.edit.parts.EAttributeEditPart;
import org.eclipse.emf.ecoretools.legacy.diagram.edit.parts.EClassESuperTypesEditPart;
import org.eclipse.emf.ecoretools.legacy.diagram.edit.parts.EClassEditPart;
import org.eclipse.emf.ecoretools.legacy.diagram.edit.parts.EPackageEditPart;
import org.eclipse.emf.ecoretools.legacy.diagram.edit.parts.EReferenceEditPart;
import org.eclipse.emf.ecoretools.legacy.diagram.edit.parts.EReferenceNameEditPart;
import org.eclipse.emf.ecoretools.legacy.diagram.edit.parts.EStringToStringMapEntryEditPart;
import org.eclipse.emf.ecoretools.legacy.diagram.part.EcoreDiagramEditor;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.jdt.internal.core.JavaProject;
import org.eclipse.jdt.internal.ui.packageview.PackageExplorerPart;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.internal.console.ConsoleView;
import org.isoe.diagraph.adapter.IDiagraphAdapter;
/**
 *
 * @author fpfister
 *
 */
import org.isoe.diagraph.controler.IDiagraphControler;  //FP140707refactored
import org.isoe.diagraph.megamodel.Dsml;
import org.isoe.diagraph.megamodel.EcoreDiagram;
import org.isoe.diagraph.megamodel.Megamodel;
import org.isoe.diagraph.preferences.DiagraphPreferences;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.utils.ResourceUtils;
public class DiagraphAdapter implements IDiagraphAdapter {

	private static final boolean LOG = DParams.DiagraphAdapter_LOG;
	private IDiagraphControler controler;
	private int firstOpened = 0;

	private EPackage changedPackage;





	private EStructuralFeature getDiagraphFeature(IStructuredSelection selection) {
		EStructuralFeature result = getDiagraphAttribute(selection);
		if (result == null)
			result = getDiagraphReference(selection);
		return result;
	}

	public DiagraphAdapter(IDiagraphControler owner) {
		super();
		this.controler = owner;
	}

	private EAttribute getDiagraphAttribute(IStructuredSelection selection) {
		if (selection.getFirstElement() instanceof EAttributeEditPart) {
			if (((EAttributeEditPart) selection.getFirstElement()).getModel() instanceof Node) {
				Node eatnode = (Node) (((EAttributeEditPart) selection
						.getFirstElement()).getModel());
				if (eatnode.getElement() instanceof EAttribute) {
					EAttribute attr = (EAttribute) eatnode.getElement();
					EClass cclaz = attr.getEContainingClass();
					EPackage pak = cclaz.getEPackage();
					if (pak.equals(controler.getCurrentPackage()))
						return attr;
				}
			}
		}
		return null;
	}

	private EReference getDiagraphReference(IStructuredSelection selection) {
		if (selection.getFirstElement() instanceof EReferenceEditPart) {
			if (((EReferenceEditPart) selection.getFirstElement()).getModel() instanceof Edge) {
				Edge erfedge = (Edge) (((EReferenceEditPart) selection
						.getFirstElement()).getModel());
				if (erfedge.getElement() instanceof EReference) {
					EReference ref = (EReference) erfedge.getElement();
					EClass cclaz = (EClass) ref.eContainer();
					EPackage pak = cclaz.getEPackage();
					if (pak.equals(controler.getCurrentPackage()))
						return ref;
				}
			}
		}
		return null;
	}

	private EPackage getDiagraphPackage(IStructuredSelection selection) {
		if (selection.getFirstElement() instanceof EPackageEditPart) {
			if (((EPackageEditPart) selection.getFirstElement()).getModel() instanceof org.eclipse.gmf.runtime.notation.Diagram) {
				Diagram diag = (Diagram) (((EPackageEditPart) selection
						.getFirstElement()).getModel());
				if (diag.getElement() instanceof EPackage) {
					if (diag.getElement() == controler.getCurrentPackage())
						return (EPackage) diag.getElement();
				}
			}
		}
		return null;
	}

	private EClass getDiagraphClass(IStructuredSelection selection) {
		if (selection.getFirstElement() instanceof EClassEditPart) {
			if (((EClassEditPart) selection.getFirstElement()).getModel() instanceof Node) {
				Node eclnode = (Node) (((EClassEditPart) selection
						.getFirstElement()).getModel());
				if (eclnode.getElement() instanceof EClass) {
					if (((EClass) eclnode.getElement()).getEPackage() == controler
							.getCurrentPackage())
						return (EClass) eclnode.getElement();
				}
			}
		}
		return null;
	}

	private EAnnotation doGetAnnotation(EAnnotation eanot) {
		if (eanot != null) {
			EModelElement me = eanot.getEModelElement();
			if (me instanceof EClass) {
				EPackage pak = ((EClass) me).getEPackage();
				if (pak.equals(controler.getCurrentPackage()))
					return eanot;
			}
		}
		return null;
	}

	private EAnnotation getDiagraphAnnotation(IStructuredSelection selection) {
		if (selection.getFirstElement() instanceof EStringToStringMapEntryEditPart) {
			EStringToStringMapEntryEditPart ep = (EStringToStringMapEntryEditPart) selection
					.getFirstElement();
			EAnnotationDetailsEditPart pe = (EAnnotationDetailsEditPart) ep
					.getParent();
			Node eanode = (Node) pe.getModel();
			return doGetAnnotation((EAnnotation) eanode.getElement());
		} else if (selection.getFirstElement() instanceof EAnnotationEditPart) {
			if (((EAnnotationEditPart) selection.getFirstElement()).getModel() instanceof Node) {
				Node eanode = (Node) (((EAnnotationEditPart) selection
						.getFirstElement()).getModel());
				return doGetAnnotation((EAnnotation) eanode.getElement());
			}
		}
		return null;
	}

	public EObject getDiagraphObject(ISelection selection) {
		EAnnotation eanot = getDiagraphAnnotation((IStructuredSelection) selection);
		if (eanot != null)
			return eanot;
		else {
			EClass eclaz = getDiagraphClass((IStructuredSelection) selection);
			if (eclaz != null)
				return eclaz;
			else {
				EStructuralFeature sf = getDiagraphFeature((IStructuredSelection) selection);
				if (sf != null)
					return sf;
				else {
					EPackage p = getDiagraphPackage((IStructuredSelection) selection);
					if (p != null)
						return p;
				}
			}
		}
		return null;
	}

	private EModelElement setCurrentModelElement(IStructuredSelection selection) {
		EObject diagraphObject = getDiagraphObject(selection);// diagraphAdapter.
		if (diagraphObject != null) {
			EModelElement me = null;
			IGraphicalEditPart currentpart = (IGraphicalEditPart) selection
					.getFirstElement();
			if (diagraphObject instanceof EClass) {
				me = (EModelElement) diagraphObject;
			} else if (diagraphObject instanceof EAnnotation) {
				EAnnotation ean = (EAnnotation) diagraphObject;
				me = ean.getEModelElement();
			}
		}
		return null;
	}

	private void handlePackageExplorerPart(TreeSelection ts) {
		if (ts != null && LOG)
			clog(ts.toString());
		Diagram d = controler.getCurrentDiagram();
		if (d == null)
			controler.setCurrentEcoreDiagram(null, null);
		else if (controler.isLanguageConfiguration()) {
			EcoreDiagramEditor ecoreDiagramEditor = controler
					.getEcoreDiagramEditor();
			if (ecoreDiagramEditor != null) {

				if ((EPackage) ecoreDiagramEditor.getDiagram().getElement() != null
						&& !controler.isUnderProject(ecoreDiagramEditor)) // FP130618
					controler.setCurrentEcoreDiagram(null, null);
			}
		}
	}

	private void handleFile(TreeSelection treeSelection) {
		Object element = treeSelection.getFirstElement();
		if (element instanceof IFile) {
			IFile ifil = (IFile) element;
			controler.refreshPathes(new File((ifil).getRawLocation().toFile()
					.getAbsolutePath()).getParentFile().getAbsolutePath());
		}
	}

	private void handleJavaproject_old(TreeSelection selection) {
		Object element = selection.getFirstElement();
		if (element instanceof JavaProject) {
			String lang =  ResourceUtils.getDiagraphLanguageName_((JavaProject) element);
			if (lang!=null){
				if (DParams.LanguageTransformer_4_LOG)
					clog4("***  AKW lang=" + lang);
				focusDiagraph(lang);
			} else
				controler.focusOther(((JavaProject) element).getProject());//cinfo("not a diagraphed project: " +((JavaProject) element).getProject().getName());
		}
	}






	private EPackage  handleJavaproject(TreeSelection selection) {
		EPackage p = null;
		Object element = selection.getFirstElement();
		if (element instanceof JavaProject) {
			controler.cinfo("on "+((JavaProject) element).getProject().getName());
			p = ResourceUtils.getPackage((JavaProject) element);
			if (p!=null && ResourceUtils.isProjectWellNamed(p)){
				String lname = p.getName();
				if (ResourceUtils.isDiagraphedM2(p)) {
					if (DParams.LanguageTransformer_4_LOG)
						clog4("***  AKW lang=" + lname);
					changedPackage = p;
					controler.setCurrentResource(changedPackage.eResource()); //FP150514
					focusDiagraph(lname);
				} else{
					if (lname.isEmpty()){
						String prjn= ((JavaProject) element).getProject().getName();
						prjn =  prjn.substring(DiagraphPreferences.getTeamNamespace().length()+1);
						lname = prjn;
					}
					focusNotDiagraphed(((JavaProject) element).getProject(),lname,p);
				}
			} else
				focusOther(((JavaProject) element).getProject());//cinfo("not a diagraphed project: " +((JavaProject) element).getProject().getName());

		}
		return p;
	}



	private void clog4____(String mesg) {
		if (DParams.LanguageTransformer_4_LOG)
			System.out.println(mesg);
	}

	private void clog4(String mesg) {
		if (DParams.LanguageTransformer_4_LOG) { // && !silent
			if (mesg == null)
				mesg = "null";
			if (DParams.LOG_ON_CONSOLE)
				controler.logConsole(mesg);// FP140630aaa
			else
				System.out.println(mesg);
		}
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	private boolean handleMegamodel(TreeSelection selection) {
		TreeSelection treeSelection = (TreeSelection) selection;
		if (treeSelection.getFirstElement() != null) {
			Object element = treeSelection.getFirstElement();
			if (element instanceof org.isoe.diagraph.megamodel.Dsml) {
				Dsml dsml = (Dsml) element;
				String uri = dsml.getURI();
				EObject nb = dsml.getNotationBridge();
				Megamodel mm = (Megamodel) dsml.eContainer();
				controler.selectDsml(dsml);
				return true;
			}
		}
		return false;
	}

	private void focusNotDiagraphed(IProject project,String lang, EPackage p) { //FP140706
		controler.cinfo("focus not diagraphed:"+lang+" package name="+(p.getName().isEmpty()?"empty":p.getName()));
		boolean diagramOpened = false;
		EcoreDiagramEditor ecoreDiagramEditor = controler.isDiagramOpen(lang);
		if (ecoreDiagramEditor == null) {
			if (firstOpened > -1)
				firstOpened++;
			if (DParams.LanguageTransformer_4_LOG)
				clog4("AKW focusNotDiagraphed");
			controler.openDiagram(ecoreDiagramEditor, lang);
			ecoreDiagramEditor = controler.isDiagramOpen(lang);
			if (ecoreDiagramEditor != null)
				diagramOpened = true;
		} else
			diagramOpened = true;
		// IWorkbenchPage page = owner.getActivePage();
		if ( diagramOpened)
				controler.focusNotDiagraphed(ecoreDiagramEditor, project,lang,
						diagramOpened);
	}


	private void focusOther(IProject project) {
		controler.cinfo("focus other:"+project.getName());
		controler.focusOther(project);//cinfo("not a diagraphed project: " +((JavaProject) element).getProject().getName());
	}



	private boolean focusDiagraph(String lang) {
		controler.cinfo("focus diagraphed:"+lang);
		boolean languageChanged = false;
		boolean diagramOpened = false;
		if (!lang.equals(controler.getFocusedLanguage())) {
			controler.setFocusedLanguage(lang);
			languageChanged = true;
		}
		EcoreDiagramEditor ecoreDiagramEditor = controler.isDiagramOpen(lang);
		if (ecoreDiagramEditor == null) {
			if (firstOpened > -1)
				firstOpened++;
			if (DParams.LanguageTransformer_4_LOG)
				clog4("AKW OpenDiagram");
			controler.openDiagram(ecoreDiagramEditor, lang);
			ecoreDiagramEditor = controler.isDiagramOpen(lang);
			if (ecoreDiagramEditor != null)
				diagramOpened = true;
		} else
			diagramOpened = true;
		// IWorkbenchPage page = owner.getActivePage();
		if (languageChanged
				&& diagramOpened
				&& controler.focusDiagraph(ecoreDiagramEditor, lang,
						diagramOpened))
			return true;// FP140227

		return false;
	}

	@Override
	public void parsed(EPackage epak) {
		if (firstOpened > 0) {
			firstOpened = -1;
			controler.firstLayout();
		}
	}

	@Override
	public void change(final IWorkbenchPart part, final ISelection selection) { // FP130606
		// FP130606
		boolean check;
		IFile fil = null;
		if (part instanceof ConsoleView)
			return;
		String data = part + "-" + selection;

		if (data.toString().contains("PackageExplorerPart"))
			check = true;
		if (part instanceof EcoreDiagramEditor)
			check = true;
		if (part instanceof PackageExplorerPart)
			check = true;
		if (selection instanceof EditPart)
			check = true;
		if (selection instanceof TreeSelection) {
			TreeSelection ts = (TreeSelection) selection;
			Object fe = ts.getFirstElement();
			if (ts.getFirstElement() instanceof IFile)
				fil = (IFile) ts.getFirstElement();
		}

		if (selection instanceof IStructuredSelection) {
			StructuredSelection ssel = (StructuredSelection) selection;
			EModelElement me = setCurrentModelElement(ssel);
			if (part instanceof EcoreDiagramEditor) {// FP131116
				Object fel = ssel.getFirstElement();
				if (fel instanceof IGraphicalEditPart) {
					updateCurrentPart((IGraphicalEditPart) fel);
					controler.checkLanguage();
				}
			}
		}
		if (selection instanceof TreeSelection) {
			TreeSelection treeSelection = (TreeSelection) selection;
			if (handleMegamodel((TreeSelection) selection))
				return;
			boolean langconfig = controler.isLanguageConfiguration();
			if (DParams.LanguageTransformer_4_LOG && !langconfig)
				clog4("AKW not a LanguageConfiguration"); // FP140521ccc
			if (langconfig) {
				if (treeSelection.getFirstElement() != null) {
					handleJavaproject(treeSelection);
					handleFile(treeSelection);
				}
			}
			if (DParams.SandboxView_LOG_RESOURCECHANGED)
				clog("(1) resourceChanged: " + data);
			if (selection instanceof EditPart) {
				EditPart editPart = (org.eclipse.gef.EditPart) selection;
				if (LOG)
					clog(editPart.toString());
				if (editPart instanceof EcoreDiagramEditor)
					controler
							.setEcoreDiagramEditor((EcoreDiagramEditor) editPart);
				if (part instanceof PackageExplorerPart)
					handlePackageExplorerPart(treeSelection);
			}// TreeSelection)

			controler.update(part, selection);

		}
	}

	@Override
	public EClass updateCurrentPart(IGraphicalEditPart editPart) {
		EClass result = null;
		if (editPart.getModel() instanceof Node) {
			Node node = (Node) editPart.getModel();
			if (node.getElement() instanceof EClass) {
				result = (EClass) node.getElement();
				controler.setCurrent(1, result, result, editPart,
						result.getName());
			} else if (node.getElement() instanceof EAttribute) {
				EAttribute eAttribute = (EAttribute) node.getElement();
				result = eAttribute.getEContainingClass();
				controler.setCurrent(2, eAttribute, result, editPart,
						eAttribute.getName());
			} else if (editPart instanceof EAnnotationEditPart) {
				EAnnotation eAnnotation = (EAnnotation) node.getElement();
				if ("diagraph".equals(eAnnotation.getSource())) {
					result = (EClass) eAnnotation.getEModelElement();
					if (result != null)
						controler.setCurrent(3, eAnnotation, result, editPart,
								result.getName() + ".diagraph.annotation");
					else
						controler.cerror("should not happen in UPDCPT");
				} else if (LOG)
					clog("unknown annotation "
							+ (eAnnotation.getSource() != null ? eAnnotation
									.getSource() : "??"));
			} else if (editPart instanceof EReferenceNameEditPart) {
				EReference erf = (EReference) node.getElement();
				result = (EClass) (erf.eContainer());
				controler.setCurrent(4, erf, result, editPart, erf.getName());
			} else if (editPart instanceof EStringToStringMapEntryEditPart) {
				editPart.getParent();
				EAnnotation eanot = (EAnnotation) ((EStringToStringMapEntryImpl) node
						.getElement()).eContainer();
				EModelElement eModelElement = eanot.getEModelElement();
				if (eModelElement instanceof EClass) {
					result = (EClass) eModelElement;
					controler.setCurrent(5, eanot, result, editPart,
							((EClass) eanot.eContainer()).getName()
									+ ".diagraph.annotation");
				}
			} else
				clog("UCPT1 unknown Node");
		} else if (editPart.getModel() instanceof Edge) {
			Edge edg = (Edge) editPart.getModel();
			if (edg.getElement() instanceof EReference) {
				EReference erf = (EReference) edg.getElement();
				result = (EClass) erf.eContainer();
				controler.setCurrent(6, erf, result, editPart, erf.getName());
			} else if (editPart instanceof EClassESuperTypesEditPart) {
				EClassESuperTypesEditPart steprt = (EClassESuperTypesEditPart) editPart;
				Node src = (Node) steprt.getSource().getModel();
				Node trg = (Node) steprt.getTarget().getModel();
				result = (EClass) src.getElement();
				EClass trgclaz = (EClass) trg.getElement();
				controler.setCurrent(7, null, result, null, "Inheritance "
						+ result.getName() + "->" + trgclaz.getName());
			} else {
				clog("UCPT2 unknown Edge");
				controler.unknownEdge(edg);
			}
		} else {
			if (editPart.getModel() instanceof Diagram) {
				Diagram d = (Diagram) editPart.getModel();
				if (d instanceof EcoreDiagram) {
					EPackage p = (EPackage) d.getElement();
					controler.unknownModel(editPart.getModel());
					clog("UCPT5 package " + p.getName());
				}
			} else {
				clog("UCPT5 unknown Model");
				controler.unknownModel(editPart.getModel());
			}
		}
		return result;
	}

}
