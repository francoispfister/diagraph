package org.isoe.fwk.diagen.text;

import java.util.List;

public interface RoleManager {

	void parseNotationNodeOrGeneric(String view, String element, String argument);

	int parseNotationEdge(String view, String element, String argument,
			List<String> cst, int l);

	List<String> getRoles();

	void sortCommands();

}
