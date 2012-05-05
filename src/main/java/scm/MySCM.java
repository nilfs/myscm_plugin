package scm;
import hudson.FilePath;
import hudson.FilePath.FileCallable;
import hudson.Launcher;
import hudson.Extension;
import hudson.util.FormValidation;
import hudson.util.XStream2;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.TaskListener;
import hudson.model.AbstractProject;
import hudson.model.User;
import hudson.remoting.VirtualChannel;
import hudson.scm.ChangeLogParser;
import hudson.scm.ChangeLogSet;
import hudson.scm.ChangeLogSet.Entry;
import hudson.scm.PollingResult;
import hudson.scm.SCMDescriptor;
import hudson.scm.SCMRevisionState;
import hudson.scm.SCM;
import net.sf.json.JSONObject;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.QueryParameter;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Sample {@link Builder}.
 *
 * @author nilfs
 */
public class MySCM extends SCM {

    // Fields in config.jelly must match the parameter names in the "DataBoundConstructor"
    @DataBoundConstructor
    public MySCM(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

	@Override
	public SCMRevisionState calcRevisionsFromBuild(AbstractBuild<?, ?> build,
			Launcher launcher, TaskListener listener) throws IOException,
			InterruptedException {
		listener.getLogger().println("calcRevisions...");
		return SCMRevisionState.NONE;
	}

	@Override
	public boolean checkout(AbstractBuild build, Launcher launcher, FilePath workspace, final BuildListener listener, File changelogFile) throws IOException,
			InterruptedException {
		
		listener.getLogger().println("begin checkout");
		
		return calcChangeLog(build, changelogFile);
	}

	/**
	 * changelogの作成
	 * @return checkoutできたかどうか
	 */
	private boolean calcChangeLog( AbstractBuild<?, ?> build, File changelogFile) {
		// @TODO changelogを作成する.
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss");
		
		List<MyChangeLogEntry> logs = new ArrayList<MyChangeLogEntry>();
		MyChangeLogEntry log = new MyChangeLogEntry("commit time"+format.format(now), User.get("username"));
		log.addPath(new MyAffectedFile("foo.java"));
		log.addPath(new MyAffectedFile("bar.java"));
		logs.add(log);

		XStream2 xstream = new XStream2();
		try {
			String xml = xstream.toXML(logs);
			FileWriter writer = new FileWriter(changelogFile);
			writer.write(xml);
			writer.close();			
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	@Override
	protected PollingResult compareRemoteRevisionWith(AbstractProject<?,?> project, Launcher launcher, FilePath workspace, final TaskListener listener, SCMRevisionState _baseline) throws IOException, InterruptedException {
		// @TODO ここでビルドが必要かチェックして結果を返すように実装する
        if (project.getLastBuild() == null) {
            listener.getLogger().println("first build");
            return PollingResult.BUILD_NOW;
        }
        
        listener.getLogger().println("build Start!!!");

		return PollingResult.BUILD_NOW;
	}

	@Override
	public ChangeLogParser createChangeLogParser() {
		return new MyChangeLogParser();
	}
	
	/**
	 * チャンジログパーサー
	 * @author nilfs
	 */
	private class MyChangeLogParser extends ChangeLogParser
	{
		@Override
		public ChangeLogSet<? extends Entry> parse(AbstractBuild build,
				File changelogFile) throws IOException, SAXException {

			List<MyChangeLogEntry> logs = null;
			XStream2 xstream = new XStream2();			
			logs = (List<MyChangeLogEntry>)xstream.fromXML( new FileReader(changelogFile) );			
			if( logs == null ){
				return null;
			}
			
			MyChangeLogSet changeLogSet = new MyChangeLogSet(build, logs);
			return changeLogSet;
		}	
	}
	
    /**
     * Descriptor for {@link MySCM}. Used as a singleton.
     * The class is marked as public so that it can be accessed from views.
     *
     * <p>
     * See <tt>src/main/resources/hudson/plugins/hello_world/HelloWorldBuilder/*.jelly</tt>
     * for the actual HTML fragment for the configuration screen.
     */
    @Extension // This indicates to Jenkins that this is an implementation of an extension point.
    public static final class DescriptorImpl extends SCMDescriptor<MySCM> {
        public DescriptorImpl() {
			super(MySCM.class, null);
			load();
		}

        /**
         * Performs on-the-fly validation of the form field 'name'.
         *
         * @param value
         *      This parameter receives the value that the user has typed.
         * @return
         *      Indicates the outcome of the validation. This is sent to the browser.
         */
        public FormValidation doCheckName(@QueryParameter String value)
                throws IOException, ServletException {
            if (value.length() == 0)
                return FormValidation.error("Please set a name");
            if (value.length() < 4)
                return FormValidation.warning("Isn't the name too short?");
            return FormValidation.ok();
        }

        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            // Indicates that this builder can be used with all kinds of project types 
            return true;
        }

        /**
         * This human readable name is used in the configuration screen.
         */
        public String getDisplayName() {
            return "MySCM";
        }

        @Override
        public boolean configure(StaplerRequest req, JSONObject formData) throws FormException {
            save();
            return super.configure(req,formData);
        }
    }
    
    private final String name;
}

