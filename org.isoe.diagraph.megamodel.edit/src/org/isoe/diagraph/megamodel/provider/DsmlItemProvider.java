/**
 */
package org.isoe.diagraph.megamodel.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import org.isoe.diagraph.megamodel.Dsml;
import org.isoe.diagraph.megamodel.MegamodelFactory;
import org.isoe.diagraph.megamodel.MegamodelPackage;

/**
 * This is the item provider adapter for a {@link org.isoe.diagraph.megamodel.Dsml} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class DsmlItemProvider
	extends MegamodelElementItemProvider
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
	public DsmlItemProvider(AdapterFactory adapterFactory) {
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

			addIdPropertyDescriptor(object);
			addNotationBridgePropertyDescriptor(object);
			addKnownAsPropertyDescriptor(object);
			addDocumentationPropertyDescriptor(object);
			addOriginPropertyDescriptor(object);
			addProblemPropertyDescriptor(object);
			addContextPropertyDescriptor(object);
			addKeywordsPropertyDescriptor(object);
			addBuildInPropertyDescriptor(object);
			addLeftParentDetailsPropertyDescriptor(object);
			addRightParentDetailsPropertyDescriptor(object);
			addRequireDetailsPropertyDescriptor(object);
			addRelatedDetailsPropertyDescriptor(object);
			addVariantDetailsPropertyDescriptor(object);
			addRootNotationPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Id feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addIdPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Dsml_id_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Dsml_id_feature", "_UI_Dsml_type"),
				 MegamodelPackage.Literals.DSML__ID,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Notation Bridge feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addNotationBridgePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Dsml_notationBridge_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Dsml_notationBridge_feature", "_UI_Dsml_type"),
				 MegamodelPackage.Literals.DSML__NOTATION_BRIDGE,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Known As feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addKnownAsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Dsml_knownAs_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Dsml_knownAs_feature", "_UI_Dsml_type"),
				 MegamodelPackage.Literals.DSML__KNOWN_AS,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Documentation feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDocumentationPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Dsml_documentation_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Dsml_documentation_feature", "_UI_Dsml_type"),
				 MegamodelPackage.Literals.DSML__DOCUMENTATION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Origin feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addOriginPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Dsml_origin_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Dsml_origin_feature", "_UI_Dsml_type"),
				 MegamodelPackage.Literals.DSML__ORIGIN,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Problem feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addProblemPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Dsml_problem_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Dsml_problem_feature", "_UI_Dsml_type"),
				 MegamodelPackage.Literals.DSML__PROBLEM,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Context feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addContextPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Dsml_context_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Dsml_context_feature", "_UI_Dsml_type"),
				 MegamodelPackage.Literals.DSML__CONTEXT,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Keywords feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addKeywordsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Dsml_keywords_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Dsml_keywords_feature", "_UI_Dsml_type"),
				 MegamodelPackage.Literals.DSML__KEYWORDS,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Build In feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addBuildInPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Dsml_buildIn_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Dsml_buildIn_feature", "_UI_Dsml_type"),
				 MegamodelPackage.Literals.DSML__BUILD_IN,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Left Parent Details feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addLeftParentDetailsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Dsml_leftParentDetails_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Dsml_leftParentDetails_feature", "_UI_Dsml_type"),
				 MegamodelPackage.Literals.DSML__LEFT_PARENT_DETAILS,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Right Parent Details feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addRightParentDetailsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Dsml_rightParentDetails_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Dsml_rightParentDetails_feature", "_UI_Dsml_type"),
				 MegamodelPackage.Literals.DSML__RIGHT_PARENT_DETAILS,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Require Details feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addRequireDetailsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Dsml_requireDetails_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Dsml_requireDetails_feature", "_UI_Dsml_type"),
				 MegamodelPackage.Literals.DSML__REQUIRE_DETAILS,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Related Details feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addRelatedDetailsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Dsml_relatedDetails_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Dsml_relatedDetails_feature", "_UI_Dsml_type"),
				 MegamodelPackage.Literals.DSML__RELATED_DETAILS,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Variant Details feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addVariantDetailsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Dsml_variantDetails_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Dsml_variantDetails_feature", "_UI_Dsml_type"),
				 MegamodelPackage.Literals.DSML__VARIANT_DETAILS,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Root Notation feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addRootNotationPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Dsml_rootNotation_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Dsml_rootNotation_feature", "_UI_Dsml_type"),
				 MegamodelPackage.Literals.DSML__ROOT_NOTATION,
				 true,
				 false,
				 true,
				 null,
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
			childrenFeatures.add(MegamodelPackage.Literals.DSML__RELATIONS);
			childrenFeatures.add(MegamodelPackage.Literals.DSML__MODELS);
			childrenFeatures.add(MegamodelPackage.Literals.DSML__NOTATIONS);
			childrenFeatures.add(MegamodelPackage.Literals.DSML__ECORE_DIAGRAMS);
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
	 * This returns Dsml.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/Dsml"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((Dsml)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_Dsml_type") :
			getString("_UI_Dsml_type") + " " + label;
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

		switch (notification.getFeatureID(Dsml.class)) {
			case MegamodelPackage.DSML__ID:
			case MegamodelPackage.DSML__KNOWN_AS:
			case MegamodelPackage.DSML__DOCUMENTATION:
			case MegamodelPackage.DSML__ORIGIN:
			case MegamodelPackage.DSML__PROBLEM:
			case MegamodelPackage.DSML__CONTEXT:
			case MegamodelPackage.DSML__KEYWORDS:
			case MegamodelPackage.DSML__BUILD_IN:
			case MegamodelPackage.DSML__LEFT_PARENT_DETAILS:
			case MegamodelPackage.DSML__RIGHT_PARENT_DETAILS:
			case MegamodelPackage.DSML__REQUIRE_DETAILS:
			case MegamodelPackage.DSML__RELATED_DETAILS:
			case MegamodelPackage.DSML__VARIANT_DETAILS:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case MegamodelPackage.DSML__RELATIONS:
			case MegamodelPackage.DSML__MODELS:
			case MegamodelPackage.DSML__NOTATIONS:
			case MegamodelPackage.DSML__ECORE_DIAGRAMS:
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
				(MegamodelPackage.Literals.DSML__RELATIONS,
				 MegamodelFactory.eINSTANCE.createRelatedTo()));

		newChildDescriptors.add
			(createChildParameter
				(MegamodelPackage.Literals.DSML__MODELS,
				 MegamodelFactory.eINSTANCE.createModel()));

		newChildDescriptors.add
			(createChildParameter
				(MegamodelPackage.Literals.DSML__NOTATIONS,
				 MegamodelFactory.eINSTANCE.createNotation()));

		newChildDescriptors.add
			(createChildParameter
				(MegamodelPackage.Literals.DSML__ECORE_DIAGRAMS,
				 MegamodelFactory.eINSTANCE.createEcoreDiagram()));
	}

}
