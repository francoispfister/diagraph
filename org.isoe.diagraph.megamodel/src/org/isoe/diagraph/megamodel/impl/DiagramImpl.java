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
package org.isoe.diagraph.megamodel.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.isoe.diagraph.megamodel.Diagram;
import org.isoe.diagraph.megamodel.MegamodelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Diagram</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.isoe.diagraph.megamodel.impl.DiagramImpl#getDiagramPictureURI <em>Diagram Picture URI</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class DiagramImpl extends MegamodelElementImpl implements Diagram {
   /**
	 * The default value of the '{@link #getDiagramPictureURI() <em>Diagram Picture URI</em>}' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see #getDiagramPictureURI()
	 * @generated
	 * @ordered
	 */
   protected static final String DIAGRAM_PICTURE_URI_EDEFAULT = null;

   /**
	 * The cached value of the '{@link #getDiagramPictureURI() <em>Diagram Picture URI</em>}' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see #getDiagramPictureURI()
	 * @generated
	 * @ordered
	 */
   protected String diagramPictureURI = DIAGRAM_PICTURE_URI_EDEFAULT;

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   protected DiagramImpl() {
		super();
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   @Override
   protected EClass eStaticClass() {
		return MegamodelPackage.Literals.DIAGRAM;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public String getDiagramPictureURI() {
		return diagramPictureURI;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public void setDiagramPictureURI(String newDiagramPictureURI) {
		String oldDiagramPictureURI = diagramPictureURI;
		diagramPictureURI = newDiagramPictureURI;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MegamodelPackage.DIAGRAM__DIAGRAM_PICTURE_URI, oldDiagramPictureURI, diagramPictureURI));
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   @Override
   public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MegamodelPackage.DIAGRAM__DIAGRAM_PICTURE_URI:
				return getDiagramPictureURI();
		}
		return super.eGet(featureID, resolve, coreType);
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   @Override
   public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MegamodelPackage.DIAGRAM__DIAGRAM_PICTURE_URI:
				setDiagramPictureURI((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   @Override
   public void eUnset(int featureID) {
		switch (featureID) {
			case MegamodelPackage.DIAGRAM__DIAGRAM_PICTURE_URI:
				setDiagramPictureURI(DIAGRAM_PICTURE_URI_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   @Override
   public boolean eIsSet(int featureID) {
		switch (featureID) {
			case MegamodelPackage.DIAGRAM__DIAGRAM_PICTURE_URI:
				return DIAGRAM_PICTURE_URI_EDEFAULT == null ? diagramPictureURI != null : !DIAGRAM_PICTURE_URI_EDEFAULT.equals(diagramPictureURI);
		}
		return super.eIsSet(featureID);
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   @Override
   public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (diagramPictureURI: ");
		result.append(diagramPictureURI);
		result.append(')');
		return result.toString();
	}

} //DiagramImpl
