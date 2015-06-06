package my.contrib;

import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDocumentEditor;
import org.isoe.extensionpoint.diagraph.action.IGenDiagraphServ;

public class ActionStub implements IGenDiagraphServ {

	public ActionStub() {
		// TODO Auto-generated constructor stub
	}



	@Override
	public void run() {
		System.err.println("ActionStub, does nothing !!! ");
	}

	@Override
	public boolean isStub() {
		return true; //FP141217
	}

	@Override
	public boolean isQualified() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setSilent(boolean value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCurrentView() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRcp(boolean rcp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAllViews() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDiagraphGenerator(Object diagraphGenerator) {
		// TODO Auto-generated method stub

	}

	@Override
	public void genCurrentView(IDocumentEditor diagramEditor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void genAllViews(IDocumentEditor diagramEditor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRefactor(boolean refactor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setHeadless(boolean headless) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRefreshOnly(boolean refreshOnly) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setControler(Object controler) {
		// TODO Auto-generated method stub
		
	}



}
