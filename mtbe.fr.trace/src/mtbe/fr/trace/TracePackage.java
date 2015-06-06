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
package mtbe.fr.trace;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see mtbe.fr.trace.TraceFactory
 * @model kind="package"
 * @generated
 */
public interface TracePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "trace";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.lirmm.fr/mtbe/1.0.1/trace.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "trace";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TracePackage eINSTANCE = mtbe.fr.trace.impl.TracePackageImpl.init();

	/**
	 * The meta object id for the '{@link mtbe.fr.trace.impl.TraceImpl <em>Trace</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see mtbe.fr.trace.impl.TraceImpl
	 * @see mtbe.fr.trace.impl.TracePackageImpl#getTrace()
	 * @generated
	 */
	int TRACE = 0;

	/**
	 * The feature id for the '<em><b>Traces</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACE__TRACES = 0;

	/**
	 * The feature id for the '<em><b>Source Model</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACE__SOURCE_MODEL = 1;

	/**
	 * The feature id for the '<em><b>Target Model</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACE__TARGET_MODEL = 2;

	/**
	 * The number of structural features of the '<em>Trace</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link mtbe.fr.trace.impl.TraceLinkImpl <em>Link</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see mtbe.fr.trace.impl.TraceLinkImpl
	 * @see mtbe.fr.trace.impl.TracePackageImpl#getTraceLink()
	 * @generated
	 */
	int TRACE_LINK = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACE_LINK__NAME = 0;

	/**
	 * The feature id for the '<em><b>Similarity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACE_LINK__SIMILARITY = 1;

	/**
	 * The feature id for the '<em><b>Required Similarity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACE_LINK__REQUIRED_SIMILARITY = 2;

	/**
	 * The feature id for the '<em><b>Rationale</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACE_LINK__RATIONALE = 3;

	/**
	 * The feature id for the '<em><b>Similarity Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACE_LINK__SIMILARITY_METHOD = 4;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACE_LINK__SOURCE = 5;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACE_LINK__TARGET = 6;

	/**
	 * The feature id for the '<em><b>Target Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACE_LINK__TARGET_VALUE = 7;

	/**
	 * The feature id for the '<em><b>Source Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACE_LINK__SOURCE_VALUE = 8;

	/**
	 * The number of structural features of the '<em>Link</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACE_LINK_FEATURE_COUNT = 9;


	/**
	 * Returns the meta object for class '{@link mtbe.fr.trace.Trace <em>Trace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Trace</em>'.
	 * @see mtbe.fr.trace.Trace
	 * @generated
	 */
	EClass getTrace();

	/**
	 * Returns the meta object for the containment reference list '{@link mtbe.fr.trace.Trace#getTraces <em>Traces</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Traces</em>'.
	 * @see mtbe.fr.trace.Trace#getTraces()
	 * @see #getTrace()
	 * @generated
	 */
	EReference getTrace_Traces();

	/**
	 * Returns the meta object for the reference '{@link mtbe.fr.trace.Trace#getSourceModel <em>Source Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source Model</em>'.
	 * @see mtbe.fr.trace.Trace#getSourceModel()
	 * @see #getTrace()
	 * @generated
	 */
	EReference getTrace_SourceModel();

	/**
	 * Returns the meta object for the reference '{@link mtbe.fr.trace.Trace#getTargetModel <em>Target Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target Model</em>'.
	 * @see mtbe.fr.trace.Trace#getTargetModel()
	 * @see #getTrace()
	 * @generated
	 */
	EReference getTrace_TargetModel();

	/**
	 * Returns the meta object for class '{@link mtbe.fr.trace.TraceLink <em>Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Link</em>'.
	 * @see mtbe.fr.trace.TraceLink
	 * @generated
	 */
	EClass getTraceLink();

	/**
	 * Returns the meta object for the attribute '{@link mtbe.fr.trace.TraceLink#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see mtbe.fr.trace.TraceLink#getName()
	 * @see #getTraceLink()
	 * @generated
	 */
	EAttribute getTraceLink_Name();

	/**
	 * Returns the meta object for the attribute '{@link mtbe.fr.trace.TraceLink#getSimilarity <em>Similarity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Similarity</em>'.
	 * @see mtbe.fr.trace.TraceLink#getSimilarity()
	 * @see #getTraceLink()
	 * @generated
	 */
	EAttribute getTraceLink_Similarity();

	/**
	 * Returns the meta object for the attribute '{@link mtbe.fr.trace.TraceLink#getRequiredSimilarity <em>Required Similarity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Required Similarity</em>'.
	 * @see mtbe.fr.trace.TraceLink#getRequiredSimilarity()
	 * @see #getTraceLink()
	 * @generated
	 */
	EAttribute getTraceLink_RequiredSimilarity();

	/**
	 * Returns the meta object for the attribute '{@link mtbe.fr.trace.TraceLink#getRationale <em>Rationale</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Rationale</em>'.
	 * @see mtbe.fr.trace.TraceLink#getRationale()
	 * @see #getTraceLink()
	 * @generated
	 */
	EAttribute getTraceLink_Rationale();

	/**
	 * Returns the meta object for the attribute '{@link mtbe.fr.trace.TraceLink#getSimilarityMethod <em>Similarity Method</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Similarity Method</em>'.
	 * @see mtbe.fr.trace.TraceLink#getSimilarityMethod()
	 * @see #getTraceLink()
	 * @generated
	 */
	EAttribute getTraceLink_SimilarityMethod();

	/**
	 * Returns the meta object for the reference '{@link mtbe.fr.trace.TraceLink#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see mtbe.fr.trace.TraceLink#getSource()
	 * @see #getTraceLink()
	 * @generated
	 */
	EReference getTraceLink_Source();

	/**
	 * Returns the meta object for the reference '{@link mtbe.fr.trace.TraceLink#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see mtbe.fr.trace.TraceLink#getTarget()
	 * @see #getTraceLink()
	 * @generated
	 */
	EReference getTraceLink_Target();

	/**
	 * Returns the meta object for the attribute '{@link mtbe.fr.trace.TraceLink#getTargetValue <em>Target Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Target Value</em>'.
	 * @see mtbe.fr.trace.TraceLink#getTargetValue()
	 * @see #getTraceLink()
	 * @generated
	 */
	EAttribute getTraceLink_TargetValue();

	/**
	 * Returns the meta object for the attribute '{@link mtbe.fr.trace.TraceLink#getSourceValue <em>Source Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Source Value</em>'.
	 * @see mtbe.fr.trace.TraceLink#getSourceValue()
	 * @see #getTraceLink()
	 * @generated
	 */
	EAttribute getTraceLink_SourceValue();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TraceFactory getTraceFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link mtbe.fr.trace.impl.TraceImpl <em>Trace</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see mtbe.fr.trace.impl.TraceImpl
		 * @see mtbe.fr.trace.impl.TracePackageImpl#getTrace()
		 * @generated
		 */
		EClass TRACE = eINSTANCE.getTrace();

		/**
		 * The meta object literal for the '<em><b>Traces</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRACE__TRACES = eINSTANCE.getTrace_Traces();

		/**
		 * The meta object literal for the '<em><b>Source Model</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRACE__SOURCE_MODEL = eINSTANCE.getTrace_SourceModel();

		/**
		 * The meta object literal for the '<em><b>Target Model</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRACE__TARGET_MODEL = eINSTANCE.getTrace_TargetModel();

		/**
		 * The meta object literal for the '{@link mtbe.fr.trace.impl.TraceLinkImpl <em>Link</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see mtbe.fr.trace.impl.TraceLinkImpl
		 * @see mtbe.fr.trace.impl.TracePackageImpl#getTraceLink()
		 * @generated
		 */
		EClass TRACE_LINK = eINSTANCE.getTraceLink();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRACE_LINK__NAME = eINSTANCE.getTraceLink_Name();

		/**
		 * The meta object literal for the '<em><b>Similarity</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRACE_LINK__SIMILARITY = eINSTANCE.getTraceLink_Similarity();

		/**
		 * The meta object literal for the '<em><b>Required Similarity</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRACE_LINK__REQUIRED_SIMILARITY = eINSTANCE.getTraceLink_RequiredSimilarity();

		/**
		 * The meta object literal for the '<em><b>Rationale</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRACE_LINK__RATIONALE = eINSTANCE.getTraceLink_Rationale();

		/**
		 * The meta object literal for the '<em><b>Similarity Method</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRACE_LINK__SIMILARITY_METHOD = eINSTANCE.getTraceLink_SimilarityMethod();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRACE_LINK__SOURCE = eINSTANCE.getTraceLink_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRACE_LINK__TARGET = eINSTANCE.getTraceLink_Target();

		/**
		 * The meta object literal for the '<em><b>Target Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRACE_LINK__TARGET_VALUE = eINSTANCE.getTraceLink_TargetValue();

		/**
		 * The meta object literal for the '<em><b>Source Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRACE_LINK__SOURCE_VALUE = eINSTANCE.getTraceLink_SourceValue();

	}

} //TracePackage
