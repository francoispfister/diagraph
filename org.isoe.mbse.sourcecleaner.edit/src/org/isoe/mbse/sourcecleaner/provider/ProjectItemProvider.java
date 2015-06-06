/**
 */
package org.isoe.mbse.sourcecleaner.provider;


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

import org.isoe.mbse.sourcecleaner.Project;
import org.isoe.mbse.sourcecleaner.SourcecleanerFactory;
import org.isoe.mbse.sourcecleaner.SourcecleanerPackage;

/**
 * This is the item provider adapter for a {@link org.isoe.mbse.sourcecleaner.Project} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ProjectItemProvider
	extends LocatedElementItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProjectItemProvider(AdapterFactory adapterFactory) {
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
			addWorkspacePropertyDescriptor(object);
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
				 getString("_UI_Project_id_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Project_id_feature", "_UI_Project_type"),
				 SourcecleanerPackage.Literals.PROJECT__ID,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Workspace feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addWorkspacePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Project_workspace_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Project_workspace_feature", "_UI_Project_type"),
				 SourcecleanerPackage.Literals.PROJECT__WORKSPACE,
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
			childrenFeatures.add(SourcecleanerPackage.Literals.PROJECT__SOURCES);
			childrenFeatures.add(SourcecleanerPackage.Literals.PROJECT__MANIFEST);
			childrenFeatures.add(SourcecleanerPackage.Literals.PROJECT__BUILD);
			childrenFeatures.add(SourcecleanerPackage.Literals.PROJECT__PLUGIN);
			childrenFeatures.add(SourcecleanerPackage.Literals.PROJECT__SCHEMA);
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
	 * This returns Project.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/Project"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((Project)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_Project_type") :
			getString("_UI_Project_type") + " " + label;
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

		switch (notification.getFeatureID(Project.class)) {
			case SourcecleanerPackage.PROJECT__ID:
			case SourcecleanerPackage.PROJECT__WORKSPACE:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case SourcecleanerPackage.PROJECT__SOURCES:
			case SourcecleanerPackage.PROJECT__MANIFEST:
			case SourcecleanerPackage.PROJECT__BUILD:
			case SourcecleanerPackage.PROJECT__PLUGIN:
			case SourcecleanerPackage.PROJECT__SCHEMA:
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
				(SourcecleanerPackage.Literals.PROJECT__SOURCES,
				 SourcecleanerFactory.eINSTANCE.createJava()));

		newChildDescriptors.add
			(createChildParameter
				(SourcecleanerPackage.Literals.PROJECT__MANIFEST,
				 SourcecleanerFactory.eINSTANCE.createManifest()));

		newChildDescriptors.add
			(createChildParameter
				(SourcecleanerPackage.Literals.PROJECT__BUILD,
				 SourcecleanerFactory.eINSTANCE.createBuild()));

		newChildDescriptors.add
			(createChildParameter
				(SourcecleanerPackage.Literals.PROJECT__PLUGIN,
				 SourcecleanerFactory.eINSTANCE.createPlugin()));

		newChildDescriptors.add
			(createChildParameter
				(SourcecleanerPackage.Literals.PROJECT__SCHEMA,
				 SourcecleanerFactory.eINSTANCE.createSchema()));
	}

}
