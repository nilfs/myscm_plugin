package scm;

import hudson.model.AbstractBuild;
import hudson.scm.ChangeLogSet;

import java.util.Iterator;
import java.util.List;

/**
 * チェンジログデータ
 */
public class MyChangeLogSet extends ChangeLogSet<MyChangeLogEntry>
{		
	public MyChangeLogSet(AbstractBuild<?, ?> build, List<MyChangeLogEntry> logs) {
		super(build);
		this.logs = logs;
	}

	public Iterator<MyChangeLogEntry> iterator() {
		return logs.iterator();
	}

	@Override
	public boolean isEmptySet() {
		// TODO Auto-generated method stub
		return logs.isEmpty();
	}

	private List<MyChangeLogEntry> logs = null;
}
