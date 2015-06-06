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
package org.isoe.extensionpoint.automation;

import java.util.Random;

/**
 *
 * @author "François.Pfister francois.pfister@gmail.com"
 *
 */
public interface IAutomationServer {
	static final String END_SEPARATOR = ";";
	static final String END_CLIENT = "BYE";
	static final String OK = "OK";
	static final String STOP_SERVER = "STOP-SERVER";
	static final String END_SERVER = STOP_SERVER+"-"+new Random().nextInt();
	void runServer();
	void setSocketControler(IAutomationControler socketControler);
	boolean isStub();
}
