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
package org.isoe.diagraph.diastyle.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

import org.isoe.diagraph.diastyle.DNodeEdgeStyle;
import org.isoe.diagraph.diastyle.DiastyleFactory;
import org.isoe.diagraph.diastyle.DiastylePackage;

/**
 * This is the item provider adapter for a {@link org.isoe.diagraph.diastyle.DNodeEdgeStyle} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class DNodeEdgeStyleItemProvider
	extends ItemProviderAdapter
	implements
		IEditingDomainItemProvider,
		IStructuredItemContentProvider,
		ITreeItemContentProvider,
		IItemLabelProvider,
		IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DNodeEdgeStyleItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addNamePropertyDescriptor(object);
			addColorPropertyDescriptor(object);
			addParentPropertyDescriptor(object);
			addParentNamePropertyDescriptor(object);
			addLinePropertyDescriptor(object);
			addLineWidthPropertyDescriptor(object);
			addFontNamePropertyDescriptor(object);
			addFontStylePropertyDescriptor(object);
			addFontSizePropertyDescriptor(object);
			addFontColorPropertyDescriptor(object);
			addTextAlignmentPropertyDescriptor(object);
			addIconPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Name feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addNamePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_DBaseStyle_name_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_DBaseStyle_name_feature", "_UI_DBaseStyle_type"),
				 DiastylePackage.Literals.DBASE_STYLE__NAME,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Color feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addColorPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_DBaseStyle_color_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_DBaseStyle_color_feature", "_UI_DBaseStyle_type"),
				 DiastylePackage.Literals.DBASE_STYLE__COLOR,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Parent feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addParentPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_DBaseStyle_parent_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_DBaseStyle_parent_feature", "_UI_DBaseStyle_type"),
				 DiastylePackage.Literals.DBASE_STYLE__PARENT,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Parent Name feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addParentNamePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_DBaseStyle_parentName_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_DBaseStyle_parentName_feature", "_UI_DBaseStyle_type"),
				 DiastylePackage.Literals.DBASE_STYLE__PARENT_NAME,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Line feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addLinePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_DNodeEdgeStyle_line_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_DNodeEdgeStyle_line_feature", "_UI_DNodeEdgeStyle_type"),
				 DiastylePackage.Literals.DNODE_EDGE_STYLE__LINE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Line Width feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addLineWidthPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_DNodeEdgeStyle_lineWidth_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_DNodeEdgeStyle_lineWidth_feature", "_UI_DNodeEdgeStyle_type"),
				 DiastylePackage.Literals.DNODE_EDGE_STYLE__LINE_WIDTH,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Font Name feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addFontNamePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_DNodeEdgeStyle_fontName_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_DNodeEdgeStyle_fontName_feature", "_UI_DNodeEdgeStyle_type"),
				 DiastylePackage.Literals.DNODE_EDGE_STYLE__FONT_NAME,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Font Style feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addFontStylePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_DNodeEdgeStyle_fontStyle_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_DNodeEdgeStyle_fontStyle_feature", "_UI_DNodeEdgeStyle_type"),
				 DiastylePackage.Literals.DNODE_EDGE_STYLE__FONT_STYLE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Font Size feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addFontSizePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_DNodeEdgeStyle_fontSize_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_DNodeEdgeStyle_fontSize_feature", "_UI_DNodeEdgeStyle_type"),
				 DiastylePackage.Literals.DNODE_EDGE_STYLE__FONT_SIZE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Font Color feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addFontColorPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_DNodeEdgeStyle_fontColor_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_DNodeEdgeStyle_fontColor_feature", "_UI_DNodeEdgeStyle_type"),
				 DiastylePackage.Literals.DNODE_EDGE_STYLE__FONT_COLOR,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Text Alignment feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addTextAlignmentPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_DNodeEdgeStyle_textAlignment_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_DNodeEdgeStyle_textAlignment_feature", "_UI_DNodeEdgeStyle_type"),
				 DiastylePackage.Literals.DNODE_EDGE_STYLE__TEXT_ALIGNMENT,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Icon feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addIconPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_DNodeEdgeStyle_icon_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_DNodeEdgeStyle_icon_feature", "_UI_DNodeEdgeStyle_type"),
				 DiastylePackage.Literals.DNODE_EDGE_STYLE__ICON,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(EcorePackage.Literals.EMODEL_ELEMENT__EANNOTATIONS);
			childrenFeatures.add(DiastylePackage.Literals.DBASE_STYLE__STYLE_BRIDGES);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((DNodeEdgeStyle)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_DNodeEdgeStyle_type") :
			getString("_UI_DNodeEdgeStyle_type") + " " + label;
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(DNodeEdgeStyle.class)) {
			case DiastylePackage.DNODE_EDGE_STYLE__NAME:
			case DiastylePackage.DNODE_EDGE_STYLE__COLOR:
			case DiastylePackage.DNODE_EDGE_STYLE__PARENT_NAME:
			case DiastylePackage.DNODE_EDGE_STYLE__LINE:
			case DiastylePackage.DNODE_EDGE_STYLE__LINE_WIDTH:
			case DiastylePackage.DNODE_EDGE_STYLE__FONT_NAME:
			case DiastylePackage.DNODE_EDGE_STYLE__FONT_STYLE:
			case DiastylePackage.DNODE_EDGE_STYLE__FONT_SIZE:
			case DiastylePackage.DNODE_EDGE_STYLE__FONT_COLOR:
			case DiastylePackage.DNODE_EDGE_STYLE__TEXT_ALIGNMENT:
			case DiastylePackage.DNODE_EDGE_STYLE__ICON:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case DiastylePackage.DNODE_EDGE_STYLE__EANNOTATIONS:
			case DiastylePackage.DNODE_EDGE_STYLE__STYLE_BRIDGES:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
				return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add
			(createChildParameter
				(DiastylePackage.Literals.DBASE_STYLE__STYLE_BRIDGES,
				 DiastyleFactory.eINSTANCE.createDStyleBridge()));
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return Diastyle5EditPlugin.INSTANCE;
	}

}
