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
package org.isoe.diagraph.gen.gmf.temp;

import java.util.Calendar;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;

/**
 * TODO:
 * ADD: 
 *  - link label feature
 *  - more nodes
 *  - child nodes
 */
public class DomainModelSetup  {

	

	public DomainModelSetup() {
	}

	/**
	 * @return <code>this</code> for convenience
	 */
	public EPackage init() {
		EPackage pak = EcoreFactory.eINSTANCE.createEPackage();
		pak.setName("samplemodel");
		pak.setNsPrefix("gmftest");
		Calendar c = Calendar.getInstance();
		pak.setNsURI("uri://eclipse/gmf/tests/sample/" + c.get(Calendar.HOUR_OF_DAY) + '/' + c.get(Calendar.MINUTE) + '/');

		EClass superNode = EcoreFactory.eINSTANCE.createEClass();
		superNode.setName("CommonBaseClass");
		superNode.setAbstract(true);

		EClass containmentNode = EcoreFactory.eINSTANCE.createEClass();
		containmentNode.setName("UltimateContainer");
		EReference r0 = EcoreFactory.eINSTANCE.createEReference();
		r0.setContainment(true);
		r0.setName("all");
		r0.setEType(superNode);
		r0.setUpperBound(-1);
		containmentNode.getEStructuralFeatures().add(r0);
		final EAttribute da1 = EcoreFactory.eINSTANCE.createEAttribute();
		da1.setName("diagramAttribute");
		da1.setEType(EcorePackage.eINSTANCE.getEString());
		containmentNode.getEStructuralFeatures().add(da1);

		EClass nodeA = EcoreFactory.eINSTANCE.createEClass();
		nodeA.setName("NodeSrcA");
		nodeA.getESuperTypes().add(superNode);
		EClass nodeB = EcoreFactory.eINSTANCE.createEClass();
		nodeB.setName("NodeTargetB");
		nodeB.getESuperTypes().add(superNode);
		EClass nodeC = EcoreFactory.eINSTANCE.createEClass();
		nodeC.setName("NodeTargetC");
		EClass nodeD = EcoreFactory.eINSTANCE.createEClass();
		nodeD.setName("NodeTargetD");
		EClass nodeLinkA2C = EcoreFactory.eINSTANCE.createEClass();
		nodeLinkA2C.setName("LinkAtoC");
		EClass nodeLinkA2C2 = EcoreFactory.eINSTANCE.createEClass();
		nodeLinkA2C2.setName("LinkAtoC_Cardinality2");
		EClass nodeLinkA2C3 = EcoreFactory.eINSTANCE.createEClass();
		nodeLinkA2C3.setName("LinkAtoC_Cardinality1");
		EClass nodeLinkA2A = EcoreFactory.eINSTANCE.createEClass();
		nodeLinkA2A.setName("LinkAtoA");
		EClass childNode = EcoreFactory.eINSTANCE.createEClass();
		childNode.setName("Child");
		EClass childNode2 = EcoreFactory.eINSTANCE.createEClass();
		childNode2.setName("Child2");
		EClass link2Link = EcoreFactory.eINSTANCE.createEClass();
		link2Link.setName("Link2Link");
		EClass linkFromLink = EcoreFactory.eINSTANCE.createEClass();
		linkFromLink.setName("LinkFromLink");
		EClass linkCrossLink = EcoreFactory.eINSTANCE.createEClass();
		linkCrossLink.setName("LinkCrossLink");


		final EAttribute a1 = EcoreFactory.eINSTANCE.createEAttribute();
		a1.setName("label");
		a1.setEType(EcorePackage.eINSTANCE.getEString());
		nodeA.getEStructuralFeatures().add(a1);

		final EAttribute a2 = EcoreFactory.eINSTANCE.createEAttribute();
		a2.setName("title");
		a2.setEType(EcorePackage.eINSTANCE.getEString());
		nodeB.getEStructuralFeatures().add(a2);
		nodeC.getESuperTypes().add(nodeB);
		nodeD.getESuperTypes().add(nodeB);
		
		final EAttribute childLabel = EcoreFactory.eINSTANCE.createEAttribute();
		childLabel.setName("childLabel");
		childLabel.setEType(EcorePackage.eINSTANCE.getEString());
		childNode.getEStructuralFeatures().add(childLabel);
		
		final EAttribute childLabel2 = EcoreFactory.eINSTANCE.createEAttribute();
		childLabel2.setName("childLabel");
		childLabel2.setEType(EcorePackage.eINSTANCE.getEString());
		childNode2.getEStructuralFeatures().add(childLabel2);

		EReference linkToB = EcoreFactory.eINSTANCE.createEReference();
		linkToB.setName("refLinkToB");
		linkToB.setEType(nodeB);
		linkToB.setUpperBound(-1);
		nodeA.getEStructuralFeatures().add(linkToB);
		
		EReference linkToB2 = EcoreFactory.eINSTANCE.createEReference();
		linkToB2.setName("refLinkToB_Cardinality2");
		linkToB2.setEType(nodeB);
		linkToB2.setUpperBound(2);
		nodeA.getEStructuralFeatures().add(linkToB2);
		
		EReference linkToB3 = EcoreFactory.eINSTANCE.createEReference();
		linkToB3.setName("refLinkToB_Cardinality1");
		linkToB3.setEType(nodeB);
		linkToB3.setUpperBound(1);
		nodeA.getEStructuralFeatures().add(linkToB3);
		
		EReference link2LinkRef = EcoreFactory.eINSTANCE.createEReference();
		link2LinkRef.setName("refLinkToLink");
		link2LinkRef.setEType(nodeLinkA2C);
		link2LinkRef.setUpperBound(3);
		nodeD.getEStructuralFeatures().add(link2LinkRef);
		
		EReference linkFromLinkRef = EcoreFactory.eINSTANCE.createEReference();
		linkFromLinkRef.setName("refLinkFromLink");
		linkFromLinkRef.setEType(nodeD);
		linkFromLinkRef.setUpperBound(4);
		nodeLinkA2C.getEStructuralFeatures().add(linkFromLinkRef);

		EReference linkCrossLinkRef = EcoreFactory.eINSTANCE.createEReference();
		linkCrossLinkRef.setName("refLinkCrossLink");
		linkCrossLinkRef.setEType(nodeLinkA2C);
		linkCrossLinkRef.setUpperBound(5);
		nodeLinkA2C.getEStructuralFeatures().add(linkCrossLinkRef);
		
		EReference linkToARef = EcoreFactory.eINSTANCE.createEReference();
		linkToARef.setName("refLinkToA");
		linkToARef.setEType(nodeA);
		linkToARef.setUpperBound(-1);
		nodeA.getEStructuralFeatures().add(linkToARef);

		EReference linkToC = EcoreFactory.eINSTANCE.createEReference();
		linkToC.setName("classLinkToC");
		linkToC.setEType(nodeLinkA2C);
		linkToC.setUpperBound(-1);
		linkToC.setContainment(true);
		nodeA.getEStructuralFeatures().add(linkToC);

		EReference refCfromLink = EcoreFactory.eINSTANCE.createEReference();
		refCfromLink.setName("trg");
		refCfromLink.setEType(nodeC);
		refCfromLink.setUpperBound(1);
		refCfromLink.setUnique(false);
		nodeLinkA2C.getEStructuralFeatures().add(refCfromLink);
		
		EReference linkToC2 = EcoreFactory.eINSTANCE.createEReference();
		linkToC2.setName("classLinkToC_Cardinality2");
		linkToC2.setEType(nodeLinkA2C2);
		linkToC2.setUpperBound(2);
		linkToC2.setContainment(true);
		nodeA.getEStructuralFeatures().add(linkToC2);

		EReference refCfromLink2 = EcoreFactory.eINSTANCE.createEReference();
		refCfromLink2.setName("trg");
		refCfromLink2.setEType(nodeC);
		refCfromLink2.setUpperBound(-1);
		refCfromLink2.setUnique(true);
		nodeLinkA2C2.getEStructuralFeatures().add(refCfromLink2);
		
		EReference linkToC3 = EcoreFactory.eINSTANCE.createEReference();
		linkToC3.setName("classLinkToC_Cardinality1");
		linkToC3.setEType(nodeLinkA2C3);
		linkToC3.setUpperBound(1);
		linkToC3.setContainment(true);
		nodeA.getEStructuralFeatures().add(linkToC3);

		EReference refCfromLink3 = EcoreFactory.eINSTANCE.createEReference();
		refCfromLink3.setName("trg");
		refCfromLink3.setEType(nodeC);
		refCfromLink3.setUpperBound(1);
		refCfromLink3.setUnique(false);
		nodeLinkA2C3.getEStructuralFeatures().add(refCfromLink3);
		
		EReference linkToAClass = EcoreFactory.eINSTANCE.createEReference();
		linkToAClass.setName("classLinkToA");
		linkToAClass.setEType(nodeLinkA2A);
		linkToAClass.setUpperBound(-1);
		linkToAClass.setContainment(true);
		nodeA.getEStructuralFeatures().add(linkToAClass);

		EReference refAfromLink = EcoreFactory.eINSTANCE.createEReference();
		refAfromLink.setName("trg");
		refAfromLink.setEType(nodeA);
		refAfromLink.setUpperBound(1);
		refAfromLink.setUnique(false);
		nodeLinkA2A.getEStructuralFeatures().add(refAfromLink);

		EReference containment1ForA = EcoreFactory.eINSTANCE.createEReference();
		containment1ForA.setContainment(true);
		containment1ForA.setName("children1OfA");
		containment1ForA.setEType(childNode);
		containment1ForA.setUpperBound(ETypedElement.UNBOUNDED_MULTIPLICITY);
		nodeA.getEStructuralFeatures().add(containment1ForA);
		
		EReference containment2ForA = EcoreFactory.eINSTANCE.createEReference();
		containment2ForA.setContainment(true);
		containment2ForA.setName("children2OfA");
		containment2ForA.setEType(childNode2);
		containment2ForA.setUpperBound(ETypedElement.UNBOUNDED_MULTIPLICITY);
		nodeA.getEStructuralFeatures().add(containment2ForA);
		
		EReference containmentForB = EcoreFactory.eINSTANCE.createEReference();
		containmentForB.setContainment(true);
		containmentForB.setName("childrenOfB");
		containmentForB.setEType(childNode);
		containmentForB.setUpperBound(ETypedElement.UNBOUNDED_MULTIPLICITY);
		nodeB.getEStructuralFeatures().add(containmentForB);
		
		EReference selfContainment = EcoreFactory.eINSTANCE.createEReference();
		selfContainment.setContainment(true);
		selfContainment.setName("innerChildrenOfBChild");
		selfContainment.setEType(childNode);
		selfContainment.setUpperBound(ETypedElement.UNBOUNDED_MULTIPLICITY);
		childNode.getEStructuralFeatures().add(selfContainment);
		
		EReference linkToLink2Link = EcoreFactory.eINSTANCE.createEReference();
		linkToLink2Link.setName("classLinkToLink");
		linkToLink2Link.setEType(link2Link);
		linkToLink2Link.setUpperBound(3);
		linkToLink2Link.setContainment(true);
		nodeD.getEStructuralFeatures().add(linkToLink2Link);

		EReference refLinkFromLink2Link = EcoreFactory.eINSTANCE.createEReference();
		refLinkFromLink2Link.setName("trg");
		refLinkFromLink2Link.setEType(nodeLinkA2C);
		refLinkFromLink2Link.setUpperBound(1);
		refLinkFromLink2Link.setUnique(false);
		link2Link.getEStructuralFeatures().add(refLinkFromLink2Link);
		
		EReference linkToLinkFromLink = EcoreFactory.eINSTANCE.createEReference();
		linkToLinkFromLink.setName("classLinkFromLink");
		linkToLinkFromLink.setEType(linkFromLink);
		linkToLinkFromLink.setUpperBound(4);
		linkToLinkFromLink.setContainment(true);
		nodeLinkA2C.getEStructuralFeatures().add(linkToLinkFromLink);

		EReference refLinkFromLinkFromLink = EcoreFactory.eINSTANCE.createEReference();
		refLinkFromLinkFromLink.setName("trg");
		refLinkFromLinkFromLink.setEType(nodeD);
		refLinkFromLinkFromLink.setUpperBound(1);
		refLinkFromLinkFromLink.setUnique(false);
		linkFromLink.getEStructuralFeatures().add(refLinkFromLinkFromLink);
		
		EReference linkToLinkCrossLink = EcoreFactory.eINSTANCE.createEReference();
		linkToLinkCrossLink.setName("classLinkCrossLink");
		linkToLinkCrossLink.setEType(linkCrossLink);
		linkToLinkCrossLink.setUpperBound(5);
		linkToLinkCrossLink.setContainment(true);
		nodeLinkA2C.getEStructuralFeatures().add(linkToLinkCrossLink);

		EReference refLinkFromLinkCorssLink = EcoreFactory.eINSTANCE.createEReference();
		refLinkFromLinkCorssLink.setName("trg");
		refLinkFromLinkCorssLink.setEType(nodeLinkA2C);
		refLinkFromLinkCorssLink.setUpperBound(1);
		refLinkFromLinkCorssLink.setUnique(false);
		linkCrossLink.getEStructuralFeatures().add(refLinkFromLinkCorssLink);

		pak.getEClassifiers().add(superNode);
		pak.getEClassifiers().add(containmentNode);
		pak.getEClassifiers().add(nodeA);
		pak.getEClassifiers().add(nodeB);
		pak.getEClassifiers().add(nodeC);
		pak.getEClassifiers().add(nodeD);
		pak.getEClassifiers().add(nodeLinkA2C);
		pak.getEClassifiers().add(nodeLinkA2C2);
		pak.getEClassifiers().add(nodeLinkA2C3);
		pak.getEClassifiers().add(nodeLinkA2A);
		pak.getEClassifiers().add(childNode);
		pak.getEClassifiers().add(childNode2);
		pak.getEClassifiers().add(link2Link);
		pak.getEClassifiers().add(linkFromLink);
		pak.getEClassifiers().add(linkCrossLink);

		new ResourceImpl(URI.createURI("uri://org.eclipse.gmf/tests/DomainModelSetup")).getContents().add(pak);
		return pak;
	}



	
}
