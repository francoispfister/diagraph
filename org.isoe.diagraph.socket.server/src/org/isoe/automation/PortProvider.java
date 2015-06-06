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
package org.isoe.automation;

import java.util.Random;

/**
 *
 * @author pfister
 *
 */
public class PortProvider {

	static PortProvider instance;
	int port;

	private int getPort() {
		return port;
	}

	public void getAdress(){

	}

	public PortProvider() {
		port = new Random().nextInt(5000 - 9000 + 1) + 5000;
		System.out.println("port="+port);
	}


	public PortProvider getInstance(){
		if (instance==null){
			instance=new PortProvider();
		}
		return instance;

	}


}
