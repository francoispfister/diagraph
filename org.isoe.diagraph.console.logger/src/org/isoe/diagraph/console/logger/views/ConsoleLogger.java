package org.isoe.diagraph.console.logger.views;


import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.SWTResourceManager;
//mport org.isoe.extensionpoint.diagraph.IDiagraphConsole;





public class ConsoleLogger extends ViewPart{// implements IDiagraphConsole{


	public static final String ID = "org.isoe.diagraph.console.logger.views.ConsoleLogger";
	private Text log;


	//private StringBuffer content = new StringBuffer();


	public ConsoleLogger() {
	}


	public void createPartControl(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));

		log = new Text(composite, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);

	}


	public void setFocus() {
		//viewer.getControl().setFocus();
	}

	//@Override
	public void clog(String mesg){
		//content.append(mesg).append("\n");
		log.setText(log.getText()+"\r\n"+"("+mesg+")");
	}



}