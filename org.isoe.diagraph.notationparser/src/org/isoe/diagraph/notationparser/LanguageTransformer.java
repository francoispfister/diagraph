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
package org.isoe.diagraph.notationparser;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.ui.PlatformUI;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.generic.GenericConstants;
import org.isoe.diagraph.notationparser.visitor.ClassifierVisitor;
import org.isoe.diagraph.notationparser.visitor.InheritanceVisitor;
import org.isoe.diagraph.notationparser.visitor.ReferenceVisitor;
import org.isoe.extensionpoint.ecoreutils.IEcoreUtils;
import org.isoe.extensionpoint.languagehandler.ILanguageHandler;
import org.isoe.extensionpoint.parsers.DiagraphParserConnector;
import org.isoe.extensionpoint.parsers.IRuntimeDiagraphParser;
import org.isoe.fwk.core.DParams;
import org.isoe.diagraph.diagrammable.IDiagrammable;

/**
 *
 * @author fpfister
 * derived from graphviz generator, re-unify
 *
 */
import org.isoe.diagraph.controler.IDiagraphControler;  //FP140707refactored
public class LanguageTransformer implements IDiagrammable{
   private static final boolean LOG = DParams.LanguageTransformer_LOG;
   private static final boolean TRACE = true;
   private IRuntimeDiagraphParser runtimeDiagraphParser;
   private ILanguageHandler handler;
   private List<String> visitedClassifiers;
   private EPackage copyPack_;
   private EPackage originalPack;
   private List<DGraph> concreteSyntax;
   private boolean cloning;
   private boolean parsing;
   private IEcoreUtils ecoreService;
   private List<AbstractVisitor> classifierVisitors; // single classifiers
   private List<AbstractVisitor> inheritanceVisitors;
   private List<AbstractVisitor> referenceVisitors; // couples of
                                                    // edge+targetNode
   private List<AbstractVisitor> allElementVisitors;


   private ConcreteSyntaxParser concreteSyntaxParser = new ConcreteSyntaxParser(this);
   private boolean generateConcreteSyntaxDone;
   private IEcoreUtils ecoreUtils;
 // private IDiagraphConsole console_;


   private IDiagraphControler controler;



   @Override
   public IDiagraphControler getControler() {
	return controler;
   }


   @Override
   public IEcoreUtils getEcoreService() {
      return ecoreService;
   }

   /*
   @Override
   public DGraph getConcreteSyntax() {
      return diagraph;
   }

   */
   @Override
   public List<DGraph> getConcreteSyntax() {
      return concreteSyntax;
   }



public LanguageTransformer(ILanguageHandler languageHandler,// EPackage targetPack,
         EClass srcClass_, EClass mergeClass,boolean dummy) {
      // this.rootToMerge = rootClassToMerge;
	  this.handler = languageHandler;
      this.originalPack = srcClass_.getEPackage();
      this.controler = languageHandler.getControler();

      this.copyPack_ = mergeClass.getEPackage();//targetPack;

      clog("srcuri="+originalPack.eResource().getURI().toString());
      clog("trguri="+copyPack_.eResource().getURI().toString());
      //this.diagraph__ = diagraph_;
   }




   public List<AbstractVisitor> doit(boolean cloning, boolean parsing) {
      if (!originalPack.getESubpackages().isEmpty())
         throw new RuntimeException(
               "operations on sub packages are not supported");// addEPackage(sp,toMerge);
      this.generateConcreteSyntaxDone = false;
      this.ecoreService = ecoreUtils;
      this.cloning = cloning;
      this.parsing = parsing;
      this.visitedClassifiers = new ArrayList<String>();
      this.inheritanceVisitors = new ArrayList<AbstractVisitor>();
      this.referenceVisitors = new ArrayList<AbstractVisitor>();
      this.classifierVisitors = new ArrayList<AbstractVisitor>();
      if (LOG && cloning) clog("___a");
      if (cloning) {
    	 if (LOG) clog("language merging "+ originalPack.getName()+" -> "+copyPack_.getName());
         ecoreUtils.init(copyPack_, originalPack);
         //ecoreUtils.cloneAnnotations(copyPack, originalPack,false);
         ecoreUtils.clonePackageAnnotations(copyPack_, originalPack);
      }
      if (LOG && cloning) clog("___b");
      transformStyles();
      if (LOG && cloning) clog("___c");
      transformPackage();
      if (LOG && cloning) clog("___d");

      allElementVisitors = new ArrayList<AbstractVisitor>();
      allElementVisitors.addAll(classifierVisitors);
      allElementVisitors.addAll(referenceVisitors);
      allElementVisitors.addAll(inheritanceVisitors);
      //defaultSyntax = null;
      // if (!VISIT_ON_ADD)
      for (AbstractVisitor visitor : allElementVisitors){
		//if (cloning)
        //	 tb = true;
         visitor.visit();
         if (visitor instanceof ClassifierVisitor){
            ClassifierVisitor classifierVisitor=(ClassifierVisitor) visitor;
            if (classifierVisitor.getConcreteSyntax_()!=null){
               concreteSyntax = new ArrayList<DGraph>();
               concreteSyntax.addAll(classifierVisitor.getConcreteSyntax_());
            }

         }
      }

      if (LOG && cloning) clog("___e");
      if (cloning)
         this.ecoreService.consolide(); // copies the temporary cloned package
                                        // back
      // to the given package
      if (LOG && cloning) clog("___f");

      // if (LOG)
      // for (AbstractVisitor eObjectEdge : allElements)
      // clog(eObjectEdge.toString());

      if (LOG) {
         clog("classifiers");
         for (AbstractVisitor eObjectEdge : classifierVisitors)
            clog(eObjectEdge.toString());
         clog("references");
         for (AbstractVisitor eObjectEdge : referenceVisitors)
            clog(eObjectEdge.toString());
         clog("inheritances");
         for (AbstractVisitor eObjectEdge : inheritanceVisitors)
            clog(eObjectEdge.toString());
      }

      if (LOG && cloning) clog("___g");
      return allElementVisitors;
   }

   boolean findClassifierInReferences(EObject eObject) {
      for (AbstractVisitor ed : referenceVisitors)
         if (ed.getTarget().equals(eObject))
            return true;
      return false;
   }

   private void clog(String mesg) {
      if (LOG)
         System.out.println(mesg);
   }

   private String getObjectId(Object o) {
      return Integer.toString(o.hashCode());
   }

   private void visitReference(EClass cls, EReference eref) {
      EClass eContainer = (EClass) eref.eContainer(); // FP130613xx
      if (!visitedClassifiers.contains(eContainer.getName())) {
         registerClassifier(eContainer);
         if (LOG)
            clog("CC->" + eContainer.getName());
         iterateThroughReferencesAndInheritances(eContainer);
      }

      EClass type = eref.getEReferenceType();
      if (!visitedClassifiers.contains(type.getName())) {
         registerClassifier(type);
         iterateThroughReferencesAndInheritances(type);
      }
      addReferenceVisitor(cls, type, eref);
      // AbstractVisitor pv = addReferenceEdge_(cls, type, eref);
      // if (VISIT_ON_ADD)
      // pv.visit();
   }

   private AbstractVisitor addClassifierVisitor(EClassifier classifier, //FP130806
         String targetAnchor, String trace) {
      boolean visible = true;
      AbstractVisitor cv = null;
      if (classifier instanceof EClass){
        cv = new ClassifierVisitor(this, classifier,
            targetAnchor, TRACE ? trace : "", visible);
        classifierVisitors.add(cv);
      } else
            if (classifier instanceof EEnum){ //FP130806
               clog("todo addClassifierVisitor for EEnum");
            } else
               if (classifier instanceof EDataType){
                  clog("todo addClassifierVisitor for EDataType");
               }
      return cv;
   }

   private AbstractVisitor addReferenceVisitor(EClass srcClass,
         EClass trgClass, EReference eref) {
      boolean visible = true;
      // String trace = TRACE ? eref.getName() : null;
      AbstractVisitor pv = new ReferenceVisitor(this, srcClass, trgClass,
            getObjectId(srcClass), getObjectId(trgClass),
            TRACE ? eref.getName() : "", visible, eref);// , target, source
      referenceVisitors.add(pv);
      return pv;
      // pv.visit();
   }

   private AbstractVisitor addInheritanceVisitor(EClass eclass,
         EClass eSuperclass) {
      boolean visible = true;
      String trace = null;
      AbstractVisitor iv = new InheritanceVisitor(this, eclass, eSuperclass,
            getObjectId(eclass), getObjectId(eSuperclass), TRACE ? trace : "",
            visible);// , target, source
      inheritanceVisitors.add(iv);
      return iv;
      // pv.visit();
   }

   private void addEEnum(EEnum enm) {
      String trace = null;
      if (TRACE) {
         trace = enm.getName() + ";";
         for (EEnumLiteral lit : enm.getELiterals())
            trace += lit.getLiteral() + "\\n";
         trace += "";
      }
      addClassifierVisitor(enm, getObjectId(enm), trace);

      // AbstractVisitor pv = addClassifier(enm, getObjectId(enm), trace);
      // if (VISIT_ON_ADD)
      // pv.visit();
   }

   private String m2CardinalityLabel(ETypedElement eref) {
      return "headlabel=\"" + m2Cardinality(eref) + "\",label=\""
            + eref.getName() + "\"";
   }

   private String m2Cardinality(ETypedElement te) {
      String c = Integer.toString(te.getLowerBound());
      c += "..";
      if (te.getUpperBound() == -1)
         c += "*";
      else
         c += Integer.toString(te.getUpperBound());
      return c;
   }

   private void addAttributeEnumEdge(EAttribute eattr, EClass cls,
         EDataType dtype) {
      String st = "sa-" + getObjectId(cls);
      String tg = "ea-" + getObjectId(dtype);
      String edg = st + " -> " + tg;
      String relDef = edg + "sa" + m2CardinalityLabel(eattr) + ".";
   }

   private String addAttributeNode(EAttribute eattr, String nameToDisplay,
         EClass cls, EDataType dtype) {
      EClass contnr = (EClass) eattr.eContainer();
      String attrid = contnr.getName() + "_" + eattr.getName();
      String line = "pp" + attrid + " " + nameToDisplay + ":" + dtype.getName();
      return line;
   }

   private String addAttributeNodeOrEdge(EClass cls, EAttribute eattr) {
      EDataType dtype = eattr.getEAttributeType();
      if (!(dtype instanceof EEnum)) {
         return addAttributeNode(eattr, eattr.getName(), cls, dtype);
      } else if (true)
         addAttributeEnumEdge(eattr, cls, dtype);
      return "";
   }

   private String addFeatureElement_(EStructuralFeature feature, EClass cls) {
      // Checks if the feature is displayable.
      if (!feature.isDerived()) {
         if (feature instanceof EReference)
            visitReference(cls, (EReference) feature);
         else if (feature instanceof EAttribute) {
            if (TRACE)
               return addAttributeNodeOrEdge(cls, (EAttribute) feature);
         }
      }
      return "";
   }

   private String addFeatures(EClass cls) {
      String result = "";
      for (EStructuralFeature feature : cls.getEStructuralFeatures())
         // FP130613x
         result += addFeatureElement_(feature, cls);
      return result;
   }

   private void goThroughFeatures(EClass eclass, StringBuffer classBuffer) {

      String attributesAdded = addFeatures(eclass);
      boolean atLeastOneAdded = attributesAdded != null
            && !attributesAdded.isEmpty();
      if (TRACE) {
         if (atLeastOneAdded) {
            classBuffer.append("attributes");
            classBuffer.append(attributesAdded);
         } else
            classBuffer.append("(featuresadded)\n");
      }
   }

   private void addOperation(EOperation operation, StringBuffer classBuffer) {
      for (EParameter param : operation.getEParameters())
         ; // dosomething
      if (operation.getEType() != null)
         ; // dosomething
      if (TRACE) {
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

   private void addOperations(EClass cls, StringBuffer classBuffer_) {
      if (TRACE)
         classBuffer_.append("|");
      for (EOperation operation : cls.getEOperations())
         addOperation(operation, classBuffer_);
   }

   private void iterateThroughReferencesAndInheritances(EClass eclass) {
	  if (LOG)
		  clog("ITRI "+eclass.getName());
      StringBuffer classBody = null;
      if (TRACE)
         classBody = new StringBuffer();
      if (TRACE)
         classBody.append(eclass.getName());
      goThroughFeatures(eclass, classBody);
      if (TRACE)
         classBody.append("________\n");
      addOperations(eclass, classBody);
      if (TRACE)
         classBody.append("________\n");
      AbstractVisitor pv_ = addClassifierVisitor(eclass, getObjectId(eclass),
            TRACE ? classBody.toString() : null);
      // if (VISIT_ON_ADD)
      // pv.visit();
      for (EClass supCls : eclass.getESuperTypes()) {
         if (!visitedClassifiers.contains(supCls.getName())) {
            registerClassifier(supCls);
            iterateThroughReferencesAndInheritances(supCls);
         }
         AbstractVisitor pvi = addInheritanceVisitor(eclass, supCls);
         // if (VISIT_ON_ADD)
         // pvi.visit();
      }
   }

   private void registerClassifier(EClassifier claz) {
      visitedClassifiers.add(claz.getName());
      if (LOG) {
         clog("rcl->" + claz.getName());
         if (claz instanceof EClass) {
            for (EAnnotation eAnnotation : ((EClass) claz).getEAnnotations()) {
               if (eAnnotation.getSource().equals("diagraph")) {
                  clog(claz.getName() + " is diagraphed ");
                  return;
               }
            }
         }
      }
   }

   private void transformStyles() { //TODO
      for (EAnnotation eAnnotation : originalPack.getEAnnotations()) {
         if (eAnnotation.getSource().equals("diastyle")) {
            clog(originalPack.getName() + " is diastyled ");
            break;
         }
      }
   }

   private void transformPackage() {
      for (EClassifier eClassifier : originalPack.getEClassifiers())
         if (!visitedClassifiers.contains(eClassifier.getName())) {
            if (eClassifier instanceof EClass) {
               clog("pck "+eClassifier.getName());//should occur one time with the root of the metamodel
               registerClassifier(eClassifier);
               iterateThroughReferencesAndInheritances((EClass) eClassifier); //other classifiers are recursively visited
            } else if (eClassifier instanceof EEnum) {
               registerClassifier(eClassifier);
               addEEnum((EEnum) eClassifier);
            }
         }
   }

   @Override
   public boolean isCloning() {
      return cloning;
   }

   @Override
   public boolean isParsing() {
      return parsing;
   }


   public IRuntimeDiagraphParser getDiagraphParser_nu() {
      if (runtimeDiagraphParser == null) {
         runtimeDiagraphParser = new DiagraphParserConnector().getRuntimeDiagraphParser();
         if (runtimeDiagraphParser == null)
            throw new RuntimeException(
                  "IRuntimeDiagraphParser should not be null !!!"); // FP130723
        // runtimeDiagraphParser.init(this,null);//(IDiagraphProvider) diagraphGenerator
      }
      return runtimeDiagraphParser;
   }




   @Override
   public List<DGraph> parseClassWithAttributesAndAnnotations(EClass source, Object sender) {
     if (!generateConcreteSyntaxDone){
    	 generateConcreteSyntaxDone=true;
    	 if (DParams.LanguageTransformer_4_LOG)
			 clog4("--------- AKW pcwa&a "+source.getName());
        return concreteSyntaxParser.generateConcreteSyntax_(source.getEPackage());
     }
     return null;
   }

   private void clog4__(String mesg) {
	   if (DParams.LanguageTransformer_4_LOG)
	      System.out.println(mesg);
   }


/*

	public IDiagraphConsole getConsole() {
    	if (console==null){
    	    console =(IDiagraphConsole) PlatformUI.getWorkbench().getActiveWorkbenchWindow()
    				.getActivePage().findView(GenericConstants.DIAGRAPH_BASIC_CONSOLE_ID);
    	}
		return console;
	}
*/

	private void clog4(String mesg) {
		if (DParams.LanguageTransformer_4_LOG){ // && !silent
			if (mesg == null)
				mesg="null";
			if (DParams.LOG_ON_CONSOLE)
				controler.cerror(mesg);
				//getConsole().clog(mesg);//FP140630aaa
			else
			   System.out.println( mesg);
		}
	}






@Override
   public void parseInheritance(EClass source, EClass target) {
      clog("parseInheritance "+source.getName()+" - "+target.getName());
   }

   @Override
   public void parseEReference(EReference eReference) {
      clog("parseEReference "+eReference.getName());
   }

   public void setEcoreUtils(IEcoreUtils ecoreUtils) {
	   this.ecoreUtils = ecoreUtils;
  }



}
