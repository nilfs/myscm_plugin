package scm;

import hudson.scm.EditType;
import hudson.scm.ChangeLogSet.AffectedFile;

public class MyAffectedFile implements AffectedFile
{
	public MyAffectedFile( String path )
	{
		this.path = path;
	}
	
	public String getPath() {
		return path;
	}

	public EditType getEditType() {
		return editType;
	}
	
	private EditType editType = EditType.ADD;		
	private String path;
}
