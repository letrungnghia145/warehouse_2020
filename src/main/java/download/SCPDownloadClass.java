package download;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.chilkatsoft.CkScp;
import com.chilkatsoft.CkSsh;

import constants.Action;
import constants.Extension;
import constants.Status;
import log.Logger;
import model.Config;
import model.Log;
import utils.ServerUtils;

public class SCPDownloadClass {
	private static List<String> fileExtensions;
	private static String pattern;
	static {
		fileExtensions = new ArrayList<String>();
		fileExtensions.add("txt");
		fileExtensions.add("xlsx");
		fileExtensions.add("xls");
		fileExtensions.add("csv");
	}

	public static void putExtension(Extension... extensions) {
		fileExtensions.removeAll(fileExtensions);
		for (Extension extension : extensions) {
			fileExtensions.add(extension.name().toLowerCase());
		}
	}

	public static List<String> getFileExtensions() {
		return fileExtensions;
	}

	public static String getPattern() {
		return pattern;
	}

	public static void putPattern(String pattern) {
		SCPDownloadClass.pattern = pattern;
	}

	public static boolean download(Config config) {
		CkSsh ssh = null;
		try {
			ssh = ServerUtils.connectSshServer(config.getHostname(), config.getPort(), config.getUsername(),
					config.getPassword());
			if (ssh != null) {
				CkScp scp = new CkScp();
				scp.UseSsh(ssh);
				for (String ext : fileExtensions) {
					scp.put_SyncMustMatch(pattern + "*." + ext);
					scp.SyncTreeDownload(config.getRemote_dir(), config.getLocal_dir(), 2, false);
					String localDirectory = config.getLocal_dir();
					List<File> notLogFiles = Logger.checkNotLogFiles(localDirectory);
					for (File file : notLogFiles) {
						Log log = new Log();
						log.setCurrent_action(Action.DOWNLOAD.name());
						log.setId_config(config.getId_config());
						log.setSource_dir(file.getParent());
						log.setSource_name(file.getName());
						log.setTime_insert(new Timestamp(System.currentTimeMillis()));
						log.setStatus(Status.SUCCESS.name());
						Logger.writeLog(log);
						config.updateStatus(Status.IN_PROGRESS);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			ServerUtils.disconnectServerQuietly(ssh);
		}
		return true;
	}
}
