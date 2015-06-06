 /*******************************************************************************
 * Copyright (c) 2008, Jean-Rémy Falleri.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Jean-Rémy Falleri - initial API and implementation
 *******************************************************************************/

package org.isoe.diagraph.diagraphviz.ecore.export;

public class FullModelExport extends AbstractExportDot {

	@Override
	protected boolean[] ecoreToDotOptions() {
		return new boolean[] {true,true,true,true};
	}

}
