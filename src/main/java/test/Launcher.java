package test;

import constants.Extension;
import constants.Status;
import download.SCPDownloadClass;
import model.Config;

public class Launcher {
	public static void runDownload() {
		Config config = Config.loadConfig(Status.IN_PROGRESS);
		Extension[] extensions = new Extension[] { Extension.CSV, Extension.TXT, Extension.XLS, Extension.XLSX };
		SCPDownloadClass.putExtension(extensions);
		SCPDownloadClass.putPattern("sinhvien_");
		SCPDownloadClass.download(config);
	}

	public static void runLoadToStaging() {
//		Log log = Logger.readLog(1, Action.DOWNLOAD, Status.SUCCESS);
//		ExtractStaging.loadStaging(log);
	}

	public static void main(String[] args) {
		// Test download
//		Launcher.runDownload();
		// load staging test

//		List<Config> loadAllConfigs = Config.loadAllConfigs(Status.IN_PROGRESS);
//		for (Config config : loadAllConfigs) {
//			System.out.println(config);
//		}

		Config config = Config.loadConfig(Status.DONE);
		System.out.println(config);
	}
}
