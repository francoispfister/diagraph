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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;
import org.isoe.extensionpoint.automation.IAutomationControler;
import org.isoe.fwk.core.DParams;

/**
 *
 * @author "François.Pfister francois.pfister@gmail.com"
 *
 */
public class SocketView extends ViewPart implements IAutomationControler {


	public static final String ID = "org.isoe.diagraph.socket.server.SocketView";
	private Socket clientSocket;
	private BufferedReader networkIn;
	private PrintWriter networkOut;
	private AutomationServer server;
	private Text logtext_;
	private Text queryText;
	private Text txtEappsphpwinvcx;
	private boolean runs;
	private Button btnNewButton;
	private Job job;


	public SocketView() {
	}

	@Override
	public int getPort() {
		throw new RuntimeException("NOT implemented SocketView getPort");
		//return DParams.LANGAGE_SERVER_PORT;
	}


	private void initClient() throws InterruptedException,
			UnknownHostException, IOException {
		clientSocket = new Socket(DParams.HOST_NAME, getPort());
		System.out.println("Connected to the server (a)");
		networkIn = new BufferedReader(new InputStreamReader(
				clientSocket.getInputStream()));
		networkOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
				clientSocket.getOutputStream())), true);
	}

	public Socket getServerSocket() {
		if (clientSocket == null)
			try {
				initClient();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		return clientSocket;
	}




	private void sendQuery() {
		runServer();
		try {
			Socket s = getServerSocket();
			if (s == null || s.isClosed()) {
				logResponse_("server is down");
				return;
			}
			networkOut.println(queryText.getText()
					+ AutomationServer.END_SEPARATOR);
			String serverResponse = "";
			String rl = null;
			boolean end = false;
			do {
				rl = networkIn.readLine();
				if (rl != null && !rl.isEmpty())
					serverResponse += rl;
				end = rl == null || rl.endsWith(AutomationServer.END_SEPARATOR);
			} while (!end);

			if (serverResponse.endsWith(";"))
				serverResponse = serverResponse.substring(0,
						serverResponse.length() - 1);
			if (serverResponse == null || serverResponse.isEmpty()) {
				logResponse_("server is down");
				return;
			}
			logResponse_(serverResponse);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public AutomationServer getServer(IProgressMonitor monitor) {
		if (server == null) {
			server = new AutomationServer();
			server.work(this,monitor);
		}
		return server;
	}

	private void endMessage() {
		//Display.getDefault().asyncExec(new Runnable() {
			//public void run() {
				logServer("server job stopped");
				//MessageDialog.openInformation(getSite().getShell(),"Diagraph Socket", "server stopped");
			//}
		//});

	}

	private void runServer() {
		if (runs)
			return;
	    job = new Job("Diagraph Automation Server") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				runs = true;
				getServer( monitor);
				endMessage();
				return Status.OK_STATUS;
			}
		};
		job.setUser(false);
		job.schedule(0);
	}

	@Override
	public void stopServerJob() {
		if (job!=null)
		   job.cancel();
	}


	public void createPartControl(Composite parent) {
		parent.setLayout(new RowLayout(SWT.HORIZONTAL));
		Composite composite = new Composite(parent, SWT.NONE);
		// composite.setBackground(SWTResourceManager.getColor(240, 255, 240));
		composite.setLayoutData(new RowData(591, 45));

		Button btnRequest = new Button(composite, SWT.NONE);
		btnRequest.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				sendQuery();
			}
		});
		btnRequest.setBounds(114, 10, 75, 25);
		btnRequest.setText("request");

		queryText = new Text(composite, SWT.BORDER);
		queryText.setText("circle 10");
		queryText.setBounds(195, 12, 297, 21);

		btnNewButton = new Button(composite, SWT.NONE);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				runServer();
			}

		});
		btnNewButton.setBounds(10, 10, 75, 25);
		btnNewButton.setText("Start Server");

		txtEappsphpwinvcx = new Text(parent, SWT.BORDER | SWT.WRAP);
		// txtEappsphpwinvcx.setText("E:\\Apps\\php-5.4.14-Win32-VC9-x86\\php.exe E:\\Apps\\php-5.4.14-Win32-VC9-x86\\exemple.php");
		txtEappsphpwinvcx.setLayoutData(new RowData(519, 22));

		logtext_ = new Text(parent, SWT.BORDER | SWT.MULTI);
		logtext_.setLayoutData(new RowData(585, 179));
	}

	public void setFocus() {
		logtext_.setFocus();
		runServer();
	}

	@Override
	public String parseCommand(String requeteClient) {
		String reponse = "(2) unknown command: " + requeteClient;
		if (requeteClient.toLowerCase().contains("circle")) {
			try {
				String[] c = requeteClient.split(" ");
				if (c.length == 2) {
					drawCircle(10, 10, Integer.parseInt(c[1]));
					reponse = requeteClient + " OK";
				}
			} catch (Exception e) {
				reponse = requeteClient + " Syntax Error";
			}
		}
		return reponse;
	}

	private void drawCircle(int x, int y, int radius) {
		logResponse_("circle " + x + "-" + y + "-" + radius);
	}
	@Override
	public void logResponse_(final String mesg) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				logtext_.setText(logtext_.getText() + "\r\n" + mesg);
			}
		});
	}

	@Override
	public void logServer(final String mesg) {

		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				logtext_.setText(logtext_.getText() + "\r\n" + "-srv- " + mesg);
			}
		});

	}

	@Override
	public void endServerJob() {
		logServer("end job");
	}

	@Override
	public boolean isModelConfiguration() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getLanguageUri() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void consolidate(String project, String folder, String language) {
		// TODO Auto-generated method stub

	}






}
