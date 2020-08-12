package main;

import java.util.Date;

import constants.Action;
import constants.DatabaseName;
import constants.Status;
import etl.extract.ExtractStaging;
import log.Logger;
import model.Config;
import model.Log;
import model.Timer;

public class LoadStagingClass {

	public static void main(String[] args) {
		long timeInMilis = Timer.getExecuteTime(() -> {
			runScript(args);
		});
		System.out.println("Complete in: " + timeInMilis + "ms");
	}

	private static void runScript(String[] args) {
		System.out.println("Staging start loading at: " + new Date(System.currentTimeMillis())+"...");
		Config config = Config.loadConfig(Integer.parseInt(args[0]));
		if (!(ExtractStaging.isStagingHaveTable(DatabaseName.staging, config.getSource_type()))) {
			Log log = Logger.readLog(config.getId_config(), Action.DOWNLOAD, Status.SUCCESS);
			boolean isSuccess = ExtractStaging.extractToStaging(log);
			System.out.println("Complete at: " + new Date(System.currentTimeMillis()));
		} else {
			System.out.println("WARNING: Staging is waiting for warehouse's processing");
		}
	}
}
