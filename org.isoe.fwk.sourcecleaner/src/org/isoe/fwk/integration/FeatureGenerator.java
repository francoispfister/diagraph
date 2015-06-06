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
package org.isoe.fwk.integration;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author fpfister
 *
 */
public class FeatureGenerator extends XmlGenerator {

	private static final String COPYRIGHT = "      Copyright (c) 2014 Laboratoire de Genie Informatique et Ingenierie de Production - Ecole des Mines d&apos;Ales and others.\n"
			+ "All rights reserved. This program and the accompanying materials\n"
			+ "are made available under the terms of the Eclipse Public License v1.0\n"
			+ "which accompanies this distribution, and is available at\n"
			+ "http://www.eclipse.org/legal/epl-v10.html\n";

	private static final String AUTHORS = "     Francois Pfister (ISOE-LGI2P) - Diagraph initial API and implementation\n"
			+ "     Blazo Nastov (ISOE-LGI2P) - Gramgen initial API and implementation\n";

	private static final String LICENCE = "   Distributed under the Eclipse Public License.\n";

	private boolean thirdparty;

	public FeatureGenerator(FeatureBase featureBase, boolean thirdparty) {
		super(featureBase);
		this.thirdparty = thirdparty;
	}

	private void createThirdparty(Document doc, Element feature) {
		for (String plugin : featureBase.getThirdpartyToDeploy()) {
			Element plug = doc.createElement("plugin");
			plug.setAttribute("id", plugin);
			plug.setAttribute("download-size", "0");
			plug.setAttribute("install-size", "0");
			plug.setAttribute("version", "0.0.0");
			plug.setAttribute("unpack", "false");
			feature.appendChild(plug);
		}
	}

	private void createPlugins(Document doc, Element feature) {
		for (String plugin : featureBase.getPluginsToDeploy()) {
			Element plug = doc.createElement("plugin");
			plug.setAttribute("id", plugin);
			plug.setAttribute("download-size", "0");
			plug.setAttribute("install-size", "0");
			plug.setAttribute("version", "0.0.0");
			plug.setAttribute("unpack", "false");
			feature.appendChild(plug);

			if (featureBase.deploySource(plugin)) {
				Element plugsrc = doc.createElement("plugin");
				plugsrc.setAttribute("id", plugin + ".source");
				plugsrc.setAttribute("download-size", "0");
				plugsrc.setAttribute("install-size", "0");
				plugsrc.setAttribute("version", "0.0.0");
				plugsrc.setAttribute("unpack", "false");
				feature.appendChild(plugsrc);
			}
		}
	}

	protected void createContent(Document doc) {

		Element feat = doc.createElement("feature");

		if (thirdparty) {
			feat.setAttribute("id", "org.isoe.diagraph.thirdparty");
			feat.setAttribute("label", "Diagraph ThirdParty Feature");
		} else {
			feat.setAttribute("id", "org.isoe.diagraph.features");
			feat.setAttribute("label", "Diagraph Feature");
		}

		if (thirdparty)
			feat.setAttribute("version", featureBase.getThirdPartyVersion_());
		else
			feat.setAttribute("version", featureBase.getVersion());
		if (thirdparty)
			feat.setAttribute("provider-name", "Eclipse");
		else
			feat.setAttribute("provider-name", "LGI2P-Isoe");
		doc.appendChild(feat);

		Element desc = doc.createElement("description");
		desc.setTextContent(" Diagraph is an environment for designing graphical concrete syntaxes");
		feat.appendChild(desc);

		Element copyr = doc.createElement("copyright");
		if (thirdparty)
			copyr.setTextContent(COPYRIGHT);
		else
			copyr.setTextContent(COPYRIGHT + AUTHORS);

		feat.appendChild(copyr);

		Element lic = doc.createElement("license");
		lic.setAttribute("url", "http://www.eclipse.org/legal/epl-v10.html");
		if (!thirdparty)
			lic.setTextContent(LICENCE);
		else
			lic.setTextContent(LICENCE
					+ "   Third Party dependencies: GMF, ATL, QVTO\n");

		feat.appendChild(lic);

		if (thirdparty)
			createThirdparty(doc, feat);
		else
			createPlugins(doc, feat);

	}

}
