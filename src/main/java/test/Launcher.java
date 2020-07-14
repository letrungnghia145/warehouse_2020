package test;

import com.chilkatsoft.CkSsh;

import Exception.IllegalStagingStateException;
import constants.Action;
import constants.Status;
import downloader.ScpDownloader;
import extractor.ExtractStaging;
import log.Logger;
import model.Config;
import model.Log;
import utils.ServerUtils;

public class Launcher {
	public static void runDownload() {
		Config config = Config.loadConfig(1);

		// Need these function to execute download
		CkSsh ssh = ServerUtils.connectSshServer(config.getHostname(), config.getPort(), config.getUsername(),
				config.getPassword());
		ScpDownloader.putPattern("sinhvien_");
		ScpDownloader.putExtension("xlsx");
		System.out.println(ScpDownloader.download(ssh, config));
	}

	public static void runLoadToStaging() throws IllegalStagingStateException {
//		Log log = Logger.readLog(1, Action.DOWNLOAD, Status.SUCCESS);
//		ExtractStaging.loadStaging(log);
	}

	public static void main(String[] args) {
		// Test download
		Launcher.runDownload();
		// load staging test
	}
}
