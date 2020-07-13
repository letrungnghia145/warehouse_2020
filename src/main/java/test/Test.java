package test;

import constants.Action;
import constants.Status;
import log.Logger;

public class Test {

	public static void main(String[] args) throws Exception {
		Logger.updateLog(1, Action.LOAD_STAGING, Status.SUCCESS);
	}
}