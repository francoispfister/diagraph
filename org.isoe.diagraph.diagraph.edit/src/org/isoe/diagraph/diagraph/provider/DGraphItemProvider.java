/**
 */
package org.isoe.diagraph.diagraph.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EStructuralFeature;

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

import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.diagraph.DiagraphFactory;
import org.isoe.diagraph.diagraph.DiagraphPackage;

/**
 * This is the item provider adapter for a {@link org.isoe.diagraph.diagraph.DGraph} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class DGraphItemProvider
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
	public DGraphItemProvider(AdapterFactory adapterFactory) {
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

			addPointOfViewPropertyDescriptor(object);
			addViewNamePropertyDescriptor(object);
			addFacade1PropertyDescriptor(object);
			addFacade2PropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Point Of View feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addPointOfViewPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_DGraph_pointOfView_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_DGraph_pointOfView_feature", "_UI_DGraph_type"),
				 DiagraphPackage.Literals.DGRAPH__POINT_OF_VIEW,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the View Name feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addViewNamePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_DGraph_viewName_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_DGraph_viewName_feature", "_UI_DGraph_type"),
				 DiagraphPackage.Literals.DGRAPH__VIEW_NAME,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Facade1 feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addFacade1PropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_DGraph_facade1_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_DGraph_facade1_feature", "_UI_DGraph_type"),
				 DiagraphPackage.Literals.DGRAPH__FACADE1,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Facade2 feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addFacade2PropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_DGraph_facade2_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_DGraph_facade2_feature", "_UI_DGraph_type"),
				 DiagraphPackage.Literals.DGRAPH__FACADE2,
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
			childrenFeatures.add(DiagraphPackage.Literals.DGRAPH__ELEMENTS);
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
	 * This returns DGraph.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/DGraph"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((DGraph)object).getViewName();
		return label == null || label.length() == 0 ?
			getString("_UI_DGraph_type") :
			getString("_UI_DGraph_type") + " " + label;
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

		switch (notification.getFeatureID(DGraph.class)) {
			case DiagraphPackage.DGRAPH__VIEW_NAME:
			case DiagraphPackage.DGRAPH__FACADE1:
			case DiagraphPackage.DGRAPH__FACADE2:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case DiagraphPackage.DGRAPH__ELEMENTS:
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
				(DiagraphPackage.Literals.DGRAPH__ELEMENTS,
				 DiagraphFactory.eINSTANCE.createDNode()));

		newChildDescriptors.add
			(createChildParameter
				(DiagraphPackage.Literals.DGRAPH__ELEMENTS,
				 DiagraphFactory.eINSTANCE.createDLabeledEdge()));

		newChildDescriptors.add
			(createChildParameter
				(DiagraphPackage.Literals.DGRAPH__ELEMENTS,
				 DiagraphFactory.eINSTANCE.createDReference()));

		newChildDescriptors.add
			(createChildParameter
				(DiagraphPackage.Literals.DGRAPH__ELEMENTS,
				 DiagraphFactory.eINSTANCE.createDNestedEdge()));

		newChildDescriptors.add
			(createChildParameter
				(DiagraphPackage.Literals.DGRAPH__ELEMENTS,
				 DiagraphFactory.eINSTANCE.createDCompartmentEdge()));

		newChildDescriptors.add
			(createChildParameter
				(DiagraphPackage.Literals.DGRAPH__ELEMENTS,
				 DiagraphFactory.eINSTANCE.createDPointOfView()));

		newChildDescriptors.add
			(createChildParameter
				(DiagraphPackage.Literals.DGRAPH__ELEMENTS,
				 DiagraphFactory.eINSTANCE.createDAffixedEdge()));

		newChildDescriptors.add
			(createChildParameter
				(DiagraphPackage.Literals.DGRAPH__ELEMENTS,
				 DiagraphFactory.eINSTANCE.createDNavigationEdge()));

		newChildDescriptors.add
			(createChildParameter
				(DiagraphPackage.Literals.DGRAPH__ELEMENTS,
				 DiagraphFactory.eINSTANCE.createDGeneric()));
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return DiagraphEditPlugin.INSTANCE;
	}

}
