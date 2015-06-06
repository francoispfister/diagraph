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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.isoe.extensionpoint.automation.IAutomationControler;
import org.isoe.extensionpoint.automation.IAutomationServer;
import org.isoe.fwk.core.DParams;

/**
 *
 * @author fpfister
 *
 */
public class AutomationServer implements IAutomationServer{
    private static final boolean LOG = DParams.Automation_LOG;
//org.isoe.automation.AutomationServer
	private boolean stopServer;
	private IAutomationControler socketControler;
	private Job job;



	private void clog(String mesg) {
		if (socketControler != null)
			socketControler.logServer(mesg);
	    if (LOG)
		   System.out.println(mesg);
	}

	public AutomationServer() {
		   if (LOG)
			   clog("creating AutomationServer - end server word is "+END_SERVER);
	}


	@Override
	public void setSocketControler(IAutomationControler socketControler) {
		this.socketControler = socketControler;
	}

	public void stopServerJob() {
		if (job!=null){
			if (LOG)
				  clog("stopServer !");
			Job.getJobManager().cancel(job);
		}
		  //job.cancel();
	}

	public void runServer() {
		if (LOG)
			  clog("run Server");
       job = new Job("Eclipse Automation Server") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				work(socketControler,monitor);	 //blocking while the server is active
				socketControler.endServerJob();
				if (LOG)
				  clog("server job stopped");
				else
				  socketControler.logServer("server job stopped");
				monitor.done();
				return Status.OK_STATUS;
			}
		};
		job.setUser(false);
		//job.setRule...
		job.setPriority(Job.LONG);
		job.schedule(0);
	}


	public void work(IAutomationControler controler, IProgressMonitor progressMonitor) {
		this.socketControler = controler;
		try {
			new SocketServer().runAndAcceptConnections(progressMonitor);
		} catch (Exception e) {
			System.err.println("server ended");
		}

	}

	private void test() {
		new SocketServer().runAndAcceptConnections(new NullProgressMonitor());
	}

	class SocketServer {
		public void runAndAcceptConnections(IProgressMonitor progressMonitor) {

			int port = -1;
			try {
				port = socketControler.getPort();
				/*
				int port = 0;
				if (configuration)
					port = DParams.SERVER_PORT_1;
				else
					port = DParams.SERVER_PORT_2;*/
				ServerSocket serverSocket = new ServerSocket(port);
				if (LOG) clog("Server started on port :" + port);
				while (!stopServer) {
					Socket remoteClient = null;
					if (LOG) clog("Waiting a new Connection");
					try {
						remoteClient = serverSocket.accept();
					} catch (java.net.SocketException e) {
						if (LOG) clog("server down");
						return;
					}
					if (stopServer) {
						if (LOG) clog("STOP...");
						break;
					}
					if (LOG) clog("A connection has been accepted");
					new SocketHandler(remoteClient, serverSocket).start();
				}
				serverSocket.close();
				if (LOG)  clog("Server stopped");
				System.exit(0);
			} catch (IOException e) {
				//e.printStackTrace();
				System.err.println("server error on port "+port+" "+e.toString());
				if (DParams.EXIT_ON_SERVER_ERROR)
				  System.exit(0);
			}
		}
	}

	private class SocketHandler extends Thread {

		private Socket remoteClient;
		private boolean stopSession;
		private ServerSocket serverSocket;

		public SocketHandler(Socket client, ServerSocket serverSocket) {
			super();
			this.remoteClient = client;
			this.serverSocket = serverSocket; // so the handler is able to stop
												// the server


		}

		private String parseCommand(String clientRequest) {
			if (clientRequest == null)
				return "request was null !!";
			String reponse = "(1) unknown command: " + clientRequest;
			if (clientRequest.toLowerCase().contains("circle")) {
				try {
					String[] c = clientRequest.split(" ");
					if (c.length == 2) {
						drawCircle(10, 10, Integer.parseInt(c[1]));
						reponse = clientRequest + " "+OK;
					}
				} catch (Exception e) {
					reponse = clientRequest + " Syntax Error";
				}
			}
			return reponse;
		}

		private String readBuffer(BufferedReader input) throws IOException,
				InterruptedException {
			String line = null;
			String clientRequest = "";
			do {
				try {
					line = input.readLine();
				} catch (java.net.SocketException e) {
					line=null;
					if (LOG)  clog("connection reset");
				}
				if (line != null && !line.isEmpty())
					clientRequest += line;
			} while (!(line == null || line.endsWith(END_SEPARATOR)));
			if (clientRequest.isEmpty())
				return null; //the client has closed the session
			if (clientRequest.endsWith(END_SEPARATOR))
				clientRequest = clientRequest.substring(0,
						clientRequest.length() - 1);
			return clientRequest;
		}

		public void run() {
			BufferedReader remoteClientInput;
			PrintWriter remoteClientOutput;
			try {
				remoteClientInput = new BufferedReader(new InputStreamReader(
						remoteClient.getInputStream()));
				remoteClientOutput = new PrintWriter(
						new BufferedWriter(new OutputStreamWriter(
								remoteClient.getOutputStream())), true);
				while (!stopSession) {
					String clientRequest = readBuffer(remoteClientInput);
					if (clientRequest == null) {
						stopSession = true;
						if (LOG)  clog("session ended");
						break;
					}
					if (LOG)  clog("client asks: " + clientRequest);
					String response = " no comprendo...  ";
					if (clientRequest.equals("¿Qué hora es?")) {
						Date d = new Date();
						response = "il est " + d.toString();
					} else {
						if (socketControler != null)
							response = socketControler
									.parseCommand(clientRequest);
						else
							response = parseCommand(clientRequest);
						if (response.contains("unknown command"))
							System.err.println("server error: "+response);
					}
					if (clientRequest.startsWith(STOP_SERVER)) { //TODO finalize random suffix
						if (LOG)  clog(STOP_SERVER+" requested");
						serverSocket.close();
						remoteClient.close();
						stopServer = true;
						if (socketControler != null)
							socketControler.stopServerJob();
						return;
					} else if (clientRequest.equals(END_CLIENT)) {
						stopSession = true;
						response = clientRequest + " STOPPING-CLIENT";
					}
					if (!stopServer && !clientRequest.isEmpty()) { //FP130514
						remoteClientOutput
								.println("here is the server - your request: "
										+ clientRequest + " ,my response: "
										+ "[" + response + "]" + END_SEPARATOR);
						// THERE MUST NO BE AN END_SEPARATOR in the middle of
						// the response - if necessary change the END_SEPARATOR
						remoteClientOutput.flush();
					}
				}
				remoteClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {
		AutomationServer server = new AutomationServer();
		server.test();
	}

	public void drawCircle(int x, int y, int radius) {
		if (LOG)  clog("circle " + x + "-" + y + "-" + radius);
	}

	@Override
	public boolean isStub() {
		return false;
	}

}
