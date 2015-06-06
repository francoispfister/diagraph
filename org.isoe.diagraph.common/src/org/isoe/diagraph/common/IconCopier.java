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
package org.isoe.diagraph.common;

/**
 * 
 * @author pfister
 *
 */
public 	class IconCopier{
	private String srcPlugin,srcPath,targetPlugin,targetPath;
	
	boolean isRootElement;

	private String backupPath;

	private String iconPath;
	
	public boolean isRootElement() {
		return isRootElement;
	}

	public String getSrcPlugin() {
		return srcPlugin;
	}

	public String getSrcPath() {
		return srcPath;
	}

	public String getTargetPlugin() {
		return targetPlugin;
	}

	public String getTargetPath() {
		return targetPath;
	}

	public String getBackupPath() {
		return backupPath;
	}	
	
	public String getIconPath() {
		return iconPath;
	}

	
	public IconCopier(String srcPlugin, String srcPath, String iconPath, String backupPath, String targetPlugin,
			String targetPath, boolean root) {
		super();
		this.isRootElement = root;
		this.srcPlugin = srcPlugin;
		this.srcPath = srcPath;
		this.targetPlugin = targetPlugin;
		this.targetPath = targetPath;
		this.backupPath = backupPath;
		this.iconPath = iconPath;
	}





}
