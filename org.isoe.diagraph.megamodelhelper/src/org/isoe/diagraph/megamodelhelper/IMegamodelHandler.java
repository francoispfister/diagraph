package org.isoe.diagraph.megamodelhelper;

import org.eclipse.emf.ecore.EPackage;
import org.isoe.diagraph.megamodel.Dsml;
import org.isoe.diagraph.megamodel.Megamodel;
import org.isoe.diagraph.megamodel.Model;

public interface IMegamodelHandler {
	Dsml findDsml(Megamodel megamodel, String name);
	Model findModel(Dsml Dsml, String name);
	Dsml addDsml(Megamodel megamodel, String universeNameSrc, EPackage model,
			String projectName, boolean fromBundle);
}
