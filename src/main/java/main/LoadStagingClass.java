package main;

import constants.Action;
import constants.Status;
import etl.extract.ExtractStaging;
import log.Logger;
import model.Config;
import model.Log;

public class LoadStagingClass {
	public static void main(String[] args) {
		Config config = Config.loadConfig(Status.IN_PROGRESS);
		Log log = Logger.readLog(config.getId_config(), Action.DOWNLOAD, Status.SUCCESS);
		boolean isSuccess = ExtractStaging.extractToStaging(log);
		System.out.println(isSuccess);
	}
}
