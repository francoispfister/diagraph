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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.isoe.extensionpoint.automation.AutomationConnector;
//import org.isoe.extensionpoint.automation.AutomationServerStub;
import org.isoe.extensionpoint.automation.IAutomationControler;
import org.isoe.extensionpoint.automation.IAutomationServer;
import org.isoe.extensionpoint.automation.IAutomationService;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.pathes.PathPreferences;

/**
 *
 * @author fpfister
 *
 */
import org.isoe.diagraph.controler.IDiagraphControler;  //FP140707refactored
import org.isoe.extensionpoint.diagraph.action.DiagraphService;  //FP140707_c_refactored
public class AutomationHandler implements IAutomationService{

	private static final boolean LOG = DParams.Automation_LOG;

	private IAutomationControler controler;
	private Socket clientSocket;
	private BufferedReader localClientIn;
	private PrintWriter localClientOut_;
	private Socket lwbSocket;
	private BufferedReader lwbClientIn;
	private PrintWriter lwbClientOut;
	private boolean serverRuns;
	private IAutomationServer server;
	private boolean LWBserverNotResponding;
	private boolean localServerNotResponding;
	private boolean silent;

	private boolean running ;



	@Override
	public void setControler(Object controler) {
		this.controler = (IAutomationControler) controler;
		((IDiagraphControler)controler).registerService(this);
	}

	private void initClient() throws InterruptedException,
			UnknownHostException, IOException {
		int port = controler.getPort();//((IAutomationControler) controler).getPort();
		clientSocket = new Socket(DParams.HOST_NAME,  port);
		if (LOG)
			clog("initClient: Connected to the local server:"+DParams.HOST_NAME+" port="+port);
		localClientIn = new BufferedReader(new InputStreamReader(
				clientSocket.getInputStream()));
		localClientOut_ = new PrintWriter(new BufferedWriter(
				new OutputStreamWriter(clientSocket.getOutputStream())), true);
	}

	private void clog(String mesg) {
		if (LOG)// && !silent)
		   System.out.println(mesg);
	}

	private void initLwbClient() throws InterruptedException,
			UnknownHostException, IOException {
		//int port = controler.getPort();


		String sport = System.getProperty("port"); //FP140526
		int port = -1;
		try {
			port = Integer.parseInt(sport);
			if (LOG)
				clog("model server ="+port);
		} catch (Exception e) {
           System.err.println("port error");
		}

		if (port!=-1){

		  lwbSocket = new Socket(DParams.HOST_NAME, port);//DParams.LANGAGE_SERVER_PORT);
		  if (LOG)
			clog("Connected to the LWB server , port="+port);
		  lwbClientIn = new BufferedReader(new InputStreamReader(
				lwbSocket.getInputStream()));
		  lwbClientOut = new PrintWriter(new BufferedWriter(
				new OutputStreamWriter(lwbSocket.getOutputStream())), true);
		} else
			System.err.println("client socket creation failed !");
	}

	public Socket getClientSocket() {
		if (clientSocket == null)
			try {
				initClient();
			} catch (Exception e) {
				System.err.println("local server not responding");
				return null;
			}
		return clientSocket;
	}

	private Socket getLwbSocket() {
		if (lwbSocket == null)
			try {
				initLwbClient();
			} catch (Exception e) {
				System.err.println("LWB server not responding");
				return null;
			}
		return lwbSocket;
	}

	public void saveAllEditors() {
		IWorkbenchPage workbenchPage = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		if (workbenchPage != null)
			workbenchPage.saveAllEditors(false);
	}

	public void requestStop() {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				try {
					sendLocalQuery(IAutomationServer.END_SERVER);
					if (LOG)
						clog("automation server should stop");
					sleep(1000);
				} catch (Exception e) {
					System.err.println("error in requestServerStop "+e.toString());
				}
			}
		});
	}

	private void sleep(int timeout) {
		if (Display.getCurrent() == null)
			return;
		final long start = System.currentTimeMillis();
		final long deltaMillis = timeout;
		do {
			while (Display.getCurrent().readAndDispatch()) {
				;
			}
		} while ((System.currentTimeMillis() - start) < deltaMillis);
	}




	@Override
	public void consolidate() { //FP150510
		if (controler.isModelConfiguration()) {
			saveAllEditors();
			sleep(100);
			consolidateModelRepository(); //FP130516zz
			sleep(1000); // FP130514zz
		}
	}


	public void consolidateModelRepository() { // FP130514
		if (controler.isModelConfiguration()) {
			//saveAllEditors();
			if (LOG) clog("consolidating the model repository");
			String languageToConsolidate = controler.getLanguageUri(); //txtPutToLwb.getText()
			if (LOG) clog("consolidating language "+languageToConsolidate);
			controler.consolidate(PathPreferences.getPreferences().getUniverseProjectName(),
				DParams.MEGAMODEL_FOLDER_LOCAL, languageToConsolidate);
			sleep(100);
			sendLWBQuery(IDiagraphControler.CMD_REFRESH_LWB);
			sleep(100);
			if (LOG) clog("end consolidating the model repository");
		} else
		   if (LOG) clog("consolidateModelRepository:  should not happen");
	}


	private boolean isServerActive() {
		if (!serverRuns) {
		    logResponse("server is down");
			return false;
		} else if (server == null) {
			logResponse("server is null");
			return false;
		}
		return true;
	}

	private void logResponse(String mesg) {
		try {
			controler.logResponse_(mesg);
		} catch (Exception e) {
			System.err.println("error while logResponse "+mesg);
		}

	}

	public void sendLWBQuery(String query) {
		if (!isServerActive())
			return;
		if (LWBserverNotResponding){
			logResponse("LWB server is down");
			return;
		}

		try {
			Socket s = getLwbSocket();
			if (s == null || s.isClosed()) {
				logResponse("LWB server is down");
				((IDiagraphControler) controler).log("","LWB automation server is not responding");//showMessage("LWB automation server is not responding");
				LWBserverNotResponding=true;
				return;
			}

			if (LOG)
				clog(s.toString());// //model config 8262

			if (query != null)
				lwbClientOut.println(query + IAutomationServer.END_SEPARATOR);

			String serverResponse = "";
			String rl = null;
			boolean end = false;
			do {
				rl = lwbClientIn.readLine();
				if (rl != null && !rl.isEmpty())
					serverResponse += rl;
				end = rl == null
						|| rl.endsWith(IAutomationServer.END_SEPARATOR);
			} while (!end);

			if (serverResponse.endsWith(IAutomationServer.END_SEPARATOR))
				serverResponse = serverResponse.substring(0,
						serverResponse.length() - 1);
			if (serverResponse == null || serverResponse.isEmpty()) {
				logResponse("server is down");
				return;
			}
			logResponse(serverResponse);
			if (LOG)
				clog(serverResponse);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void sendLocalQuery(String query) {
		if (!isServerActive())
			return;
		if (localServerNotResponding){
			logResponse("local server is down");
			return;
		}
		try {
			Socket s = getClientSocket();
			if (s == null || s.isClosed()) {
				logResponse("local server is down");
				((IDiagraphControler) controler).log("","Local automation server is not responding");//showMessage("Local automation server is not responding");
				localServerNotResponding=true;
				return;
			}

			if (LOG)
				clog(s.toString());// //model config 8262

			if (query != null){
				if (LOG) clog("SLC query="+query);
				localClientOut_.println(query + IAutomationServer.END_SEPARATOR);
			}
			String serverResponse = "";
			String rl_ = null;
			boolean end = false;
			do {
				rl_ = localClientIn.readLine();
				if (rl_ != null && !rl_.isEmpty())
					serverResponse += rl_;
				end = rl_ == null
						|| rl_.endsWith(IAutomationServer.END_SEPARATOR);
			} while (!end);

			if (serverResponse.endsWith(IAutomationServer.END_SEPARATOR))
				serverResponse = serverResponse.substring(0,
						serverResponse.length() - 1);
			if (serverResponse == null || serverResponse.isEmpty()) {
				logResponse("server is down");
				if (LOG)
					clog("SLC server is down");
				return;
			}
			logResponse(serverResponse);
			if (LOG)
				clog("SLC resp="+serverResponse);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stopJob() {
		serverRuns = false;
	}


	@Override
	public void stopService() {
		if (LOG)
			clog("AutomationHandler: stop service");
		sleep(100);
		beforeStopping();
		sleep(100);
		requestStop();
		sleep(100);
	}

	private void beforeStopping() {
		if (controler.isModelConfiguration()) //FP130518
			   consolidate();
	}

	@Override
	public boolean isStub() {
		return false;
	}



    @Override
	public void run() { // TODO: autostarts, but unable to restart if stopped
		serverRuns = true;
		server = new AutomationConnector().getAutomationService();
		if (server != null){
		  server.setSocketControler(controler);
		  server.runServer();
		  running = true;
		  if (LOG)
				clog("server runs");
		} else
			System.err.println("no server");
	}


	@Override
	public void initServer() {
		if (LOG)
			clog("initServer");
		run();
	}

	@Override
	public boolean isQualified() {
		return true;
	}

	@Override
	public void setSilent(boolean value) {
		silent=value;

	}

	@Override
	public boolean isRunning() {
		return running;
	}






}
