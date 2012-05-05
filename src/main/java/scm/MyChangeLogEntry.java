package scm;

import hudson.model.User;
import hudson.scm.ChangeLogSet;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *	@TODO paths‚ÉƒpƒX‚ð’Ç‰Á
 */
public class MyChangeLogEntry extends ChangeLogSet.Entry
{
	public MyChangeLogEntry( String msg, User author )
	{
		this.commitMsg = msg;
		this.author = author;
	}
	
	@Override
	public String getMsg() {
		return "test message";
	}

	@Override
	public User getAuthor() {
		return User.get("nilfs");
	}

	@Override
	public Collection<String> getAffectedPaths() {
		return new AbstractList<String>() {

			@Override
			public String get(int index) {
				// TODO Auto-generated method stub
				return paths.get(index).getPath();
			}

			@Override
			public int size() {
				return paths.size();
			}
		};
	}

	public void addPath( MyAffectedFile file )
	{
		paths.add(file);
	}
	
	private String commitMsg;
	private User author;
	private List<MyAffectedFile> paths = new ArrayList<MyAffectedFile>();
}
