/*********************************************************************************
 * Copyright (c) 2007, 2008 Jean-Rémy Falleri <jr.falleri@laposte.net>
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Jean-Rémy Falleri <jr.falleri@laposte.net> - initial API and implementation
 *********************************************************************************/

package org.isoe.diagraph.operations.views;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;


import mtbe.fr.trace.TraceLink;



/**
 *
 * @author "Fran�ois.Pfister francois.pfister@gmail.com"
 * borrowed from http://code.google.com/p/gumm-project/
 * thanks to JR Falleri
 *
 */
public class AlignmentTable extends Composite  {



	private Table table;
	private TraceLink selectedLink;
	private Map<TableItem, TraceLink> mappingMap;

	private void createMappinMap() {
		mappingMap = new HashMap<TableItem, TraceLink>();
	}


	public TableItem [] getItems() {
		return table.getItems();
	}


	public Set<TraceLink> getToRemove() {
		Set<TraceLink> toRemove = new HashSet<TraceLink>();
		for (TableItem t : getItems())
			if (t.getChecked())
				toRemove.add(mappingMap.get(t));
		return toRemove;
	}



	public void buildTable(List traces) {
		clearMappingMap();
		table.removeAll();
		List<TraceLink> ltr = traces;
		for (TraceLink tl : ltr) {
			EObject leftob = (EObject) tl.getSource();
			EObject rightob = (EObject) tl.getTarget();
			String left = leftob.eClass().getName() + " : "
					+ tl.getSourceValue();
			String right = rightob.eClass().getName() + " : "
					+ tl.getTargetValue();
			TableItem t = new TableItem(table, SWT.NONE);
			link(tl, t);
			t.setText(1, left);
			t.setText(2, right);
			t.setText(3, "" + tl.getRequiredSimilarity());
			t.setText(4, "" + tl.getSimilarity());
			t.setText(5, "" + tl.getSimilarityMethod());
			t.setText(6, "" + tl.getRationale());
		}
	}

	private void link(TraceLink m, TableItem t) {
		mappingMap.put(t, m);
		// tableItemMap_.put(m,t);
	}

	private void clearMappingMap() {
		mappingMap.clear();

	}


	public AlignmentTable(Composite parent, int style) {
		super(parent, style);

		createMappinMap();

		GridLayout l1 = new GridLayout(1, true);
		this.setLayout(l1);


		table = new Table(this, SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER
				| SWT.MULTI | SWT.CHECK);
		table.setLayoutData(new GridData(GridData.FILL_BOTH));



		TableColumn tc1 = new TableColumn(table, SWT.CENTER);
		TableColumn tc2 = new TableColumn(table, SWT.LEFT);
		TableColumn tc3 = new TableColumn(table, SWT.LEFT);
		TableColumn tc4 = new TableColumn(table, SWT.LEFT);
		TableColumn tc5 = new TableColumn(table, SWT.LEFT);
		TableColumn tc6 = new TableColumn(table, SWT.LEFT);
		TableColumn tc7 = new TableColumn(table, SWT.LEFT);

		tc1.setWidth(25);
		tc2.setWidth(200);
		tc3.setWidth(200);
		tc4.setWidth(50);
		tc5.setWidth(50);
		tc6.setWidth(50);
		tc7.setWidth(200);
		tc2.setText("Source Element");
		tc3.setText("Target Element");
		tc4.setText("Requ similarity");
		tc5.setText("Similarity");
		tc6.setText("Method");
		tc7.setText("Rationale");

		table.setHeaderVisible(true);
		table.setLinesVisible(true);

	}


	public void searchPattern(String pattern) {
		selectedLink = null;
		// buildTable();
		for (TableItem t : table.getItems())
			searchTableItem(t, pattern);
		colorizeAll();
	}

	private void searchTableItem(TableItem t, String pattern) {
		TraceLink current = mappingMap.get(t);
		pattern = pattern.toLowerCase();
		String srcname = current.getSourceValue().toLowerCase();
		String tgtname = current.getTargetValue().toLowerCase();
		if (srcname != null && !srcname.isEmpty() && srcname.contains(pattern))
			t.setChecked(true);
		if (tgtname != null && !tgtname.isEmpty() && tgtname.contains(pattern))
			t.setChecked(true);
	}

	private void colorizeAll() {

	}


}
