package fr.labri.gumtree.client.ui.json;

import fr.labri.gumtree.actions.ActionGenerator;
import fr.labri.gumtree.client.DiffClient;
import fr.labri.gumtree.client.DiffOptions;
import fr.labri.gumtree.io.ActionsIoUtils;
import fr.labri.gumtree.matchers.Matcher;

public class JsonDiff extends DiffClient {

	public JsonDiff(DiffOptions diffOptions) {
		super(diffOptions);
	}
	
	@Override
	public void start() {
		Matcher m = getMatcher();
		ActionGenerator g = new ActionGenerator(m.getSrc(), m.getDst(), m.getMappings());
		g.generate();
		String json = ActionsIoUtils.toJson(g.getActions(), m.getMappings());
		System.out.println(json);
	}
}
