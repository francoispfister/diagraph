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
public class UpdateSiteGenerator extends XmlGenerator {

	public UpdateSiteGenerator(FeatureBase featureBase) {
		super(featureBase);
	}


	protected void createContent(Document doc) {
		Element site = doc.createElement("site");
		doc.appendChild(site);

		Element feat1 = doc.createElement("feature");
		feat1.setAttribute("url", "features/org.isoe.diagraph.features_1.0.0.qualifier.jar");
		feat1.setAttribute("id", "org.isoe.diagraph.features");
		feat1.setAttribute("version", featureBase.getVersion());

		site.appendChild(feat1);
		Element feat1categ = doc.createElement("category");
		feat1categ.setAttribute("name", "metaeditors");
		feat1.appendChild(feat1categ);

		Element feat2 = doc.createElement("feature");

		feat2.setAttribute("url", "features/org.isoe.diagraph.thirdparty_1.0.0.qualifier.jar");
		feat2.setAttribute("id", "org.isoe.diagraph.thirdparty");
		feat2.setAttribute("version", featureBase.getThirdPartyVersion_());

		site.appendChild(feat2);
		Element feat2categ = doc.createElement("category");
		feat2categ.setAttribute("name", "metaeditors");
		feat2.appendChild(feat2categ);

		Element categ = doc.createElement("category-def");
		categ.setAttribute("name", "metaeditors");
		categ.setAttribute("label", "metaeditors");

		site.appendChild(categ);
		Element categ_descr = doc.createElement("description");
		categ.appendChild(categ_descr);
		categ_descr.setTextContent("metaeditors");
	}


}
