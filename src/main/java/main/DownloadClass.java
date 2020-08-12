package main;

import constants.Extension;
import constants.NamedResources;
import constants.Status;
import download.SCPDownloadClass;
import model.Config;

public class DownloadClass {
	public static void main(String[] args) {
		Config config = Config.loadConfig(Status.IN_PROGRESS);
		Extension[] extensions = new Extension[] { Extension.CSV, Extension.TXT, Extension.XLS, Extension.XLSX };
		SCPDownloadClass.putExtension(extensions);
		SCPDownloadClass.putPattern(NamedResources.getNamedResource(config.getSource_type()) + "_");
		boolean isDownloaded = SCPDownloadClass.download(config);
		System.out.println(isDownloaded);
	}
}
