package fr.labri.gumtree.io;

import java.io.IOException;
import java.util.List;

import com.google.gson.stream.JsonWriter;

import fr.labri.gumtree.actions.model.Action;
import fr.labri.gumtree.actions.model.Delete;
import fr.labri.gumtree.actions.model.Insert;
import fr.labri.gumtree.actions.model.Move;
import fr.labri.gumtree.actions.model.Permute;
import fr.labri.gumtree.actions.model.Update;
import fr.labri.gumtree.matchers.MappingStore;
import fr.labri.gumtree.tree.Tree;

public class ActionsJsonIoUtils {

	static void writeJsonActions(List<Action> actions, MappingStore mappings, JsonWriter w) throws IOException {
		for (Action a : actions) {
			w.beginObject();
			w.name("action_type").value(a.getClass().getSimpleName());
			w.name("object");
			writeJsonNode(w, a.getNode());
			if (a instanceof Move || a instanceof Update || a instanceof Permute) {
				Tree src = a.getNode();
				Tree dst = mappings.getDst(src);
				w.name("parent");
				writeJsonNode(w, dst.getParent());
				writeJsonTreePos(w, true, src);
				writeJsonTreePos(w, false, dst);
			} else {
				writeJsonTreePos(w, true, a.getNode());
			}
			w.endObject();
		}
	}

	private static void writeJsonNode(JsonWriter w, Tree n) throws IOException {
		w.beginObject();
		w.name("type").value(n.getTypeLabel());
		if(!n.getLabel().equals(Tree.NO_LABEL)) w.name("label").value(n.getLabel());
		w.endObject();
	}

	static void writeJsonTreePos(JsonWriter w, boolean isBefore, Tree tree) throws IOException {
		if (isBefore) w.name("before"); else w.name("after");
		w.beginObject();
		if (Tree.NO_VALUE != tree.getPos()) {
			w.name("pos").value(tree.getPos());
			w.name("length").value(tree.getLength());
		}
		w.endObject();
	}

	static void writeJsonInsertPos(JsonWriter w, boolean isBefore, int[] pos) throws IOException {
		if (isBefore) w.name("before"); else w.name("after");
		w.beginObject();
		if (pos != null) {
			w.name("begin_line").value(Integer.toString(pos[0]));
			w.name("begin_col").value(Integer.toString(pos[1]));
			w.name("end_line").value(Integer.toString(pos[0]));
			w.name("end_col").value(Integer.toString(pos[1]));
		}
		w.endObject();
	}

}
