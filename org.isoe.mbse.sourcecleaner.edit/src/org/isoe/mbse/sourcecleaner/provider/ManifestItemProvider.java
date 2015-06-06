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

import org.isoe.mbse.sourcecleaner.Manifest;
import org.isoe.mbse.sourcecleaner.SourcecleanerFactory;
import org.isoe.mbse.sourcecleaner.SourcecleanerPackage;

/**
 * This is the item provider adapter for a {@link org.isoe.mbse.sourcecleaner.Manifest} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ManifestItemProvider
	extends SourceItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ManifestItemProvider(AdapterFactory adapterFactory) {
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

			addSymbolicNamePropertyDescriptor(object);
			addSingletonPropertyDescriptor(object);
			addVendorPropertyDescriptor(object);
			addVersionPropertyDescriptor(object);
			addVersionIdPropertyDescriptor(object);
			addVersionQualifierPropertyDescriptor(object);
			addLazyPropertyDescriptor(object);
			addExecutionEnvironmentPropertyDescriptor(object);
			addDiagraphPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Symbolic Name feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addSymbolicNamePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Manifest_symbolicName_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Manifest_symbolicName_feature", "_UI_Manifest_type"),
				 SourcecleanerPackage.Literals.MANIFEST__SYMBOLIC_NAME,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Singleton feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addSingletonPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Manifest_singleton_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Manifest_singleton_feature", "_UI_Manifest_type"),
				 SourcecleanerPackage.Literals.MANIFEST__SINGLETON,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Vendor feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addVendorPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Manifest_vendor_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Manifest_vendor_feature", "_UI_Manifest_type"),
				 SourcecleanerPackage.Literals.MANIFEST__VENDOR,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Version feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addVersionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Manifest_version_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Manifest_version_feature", "_UI_Manifest_type"),
				 SourcecleanerPackage.Literals.MANIFEST__VERSION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Version Id feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addVersionIdPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Manifest_versionId_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Manifest_versionId_feature", "_UI_Manifest_type"),
				 SourcecleanerPackage.Literals.MANIFEST__VERSION_ID,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Version Qualifier feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addVersionQualifierPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Manifest_versionQualifier_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Manifest_versionQualifier_feature", "_UI_Manifest_type"),
				 SourcecleanerPackage.Literals.MANIFEST__VERSION_QUALIFIER,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Lazy feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addLazyPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Manifest_lazy_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Manifest_lazy_feature", "_UI_Manifest_type"),
				 SourcecleanerPackage.Literals.MANIFEST__LAZY,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Execution Environment feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addExecutionEnvironmentPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Manifest_executionEnvironment_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Manifest_executionEnvironment_feature", "_UI_Manifest_type"),
				 SourcecleanerPackage.Literals.MANIFEST__EXECUTION_ENVIRONMENT,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Diagraph feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDiagraphPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Manifest_diagraph_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Manifest_diagraph_feature", "_UI_Manifest_type"),
				 SourcecleanerPackage.Literals.MANIFEST__DIAGRAPH,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
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
			childrenFeatures.add(SourcecleanerPackage.Literals.MANIFEST__DEPENDENCIES);
			childrenFeatures.add(SourcecleanerPackage.Literals.MANIFEST__CLASSPATHES);
			childrenFeatures.add(SourcecleanerPackage.Literals.MANIFEST__EXPORTS);
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
	 * This returns Manifest.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/Manifest"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((Manifest)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_Manifest_type") :
			getString("_UI_Manifest_type") + " " + label;
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

		switch (notification.getFeatureID(Manifest.class)) {
			case SourcecleanerPackage.MANIFEST__SYMBOLIC_NAME:
			case SourcecleanerPackage.MANIFEST__SINGLETON:
			case SourcecleanerPackage.MANIFEST__VENDOR:
			case SourcecleanerPackage.MANIFEST__VERSION:
			case SourcecleanerPackage.MANIFEST__VERSION_ID:
			case SourcecleanerPackage.MANIFEST__VERSION_QUALIFIER:
			case SourcecleanerPackage.MANIFEST__LAZY:
			case SourcecleanerPackage.MANIFEST__EXECUTION_ENVIRONMENT:
			case SourcecleanerPackage.MANIFEST__DIAGRAPH:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case SourcecleanerPackage.MANIFEST__DEPENDENCIES:
			case SourcecleanerPackage.MANIFEST__CLASSPATHES:
			case SourcecleanerPackage.MANIFEST__EXPORTS:
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
				(SourcecleanerPackage.Literals.MANIFEST__DEPENDENCIES,
				 SourcecleanerFactory.eINSTANCE.createDependency()));

		newChildDescriptors.add
			(createChildParameter
				(SourcecleanerPackage.Literals.MANIFEST__CLASSPATHES,
				 SourcecleanerFactory.eINSTANCE.createClassPath()));

		newChildDescriptors.add
			(createChildParameter
				(SourcecleanerPackage.Literals.MANIFEST__EXPORTS,
				 SourcecleanerFactory.eINSTANCE.createExport()));
	}

}
