package utils;

import java.util.Date;

import com.chilkatsoft.CkGlobal;
import com.chilkatsoft.CkSsh;

public class ServerUtils {

	private static void loadChilkat() {
		try {
			System.loadLibrary("chilkat");
			CkGlobal ckGlobal = new CkGlobal();
			ckGlobal.UnlockBundle("OKE");
		} catch (Exception e) {
			System.out.println("Fail to load library");
			System.exit(1);
		}
	}

	public static void disconnectServerQuietly(CkSsh ssh) {
		try {
			ssh.Disconnect();
		} catch (Exception e) {
			System.out.println("Error");
		}
	}

	public static CkSsh connectSshServer(String hostname, int port, String username, String password) {
		loadChilkat();
		CkSsh ssh = new CkSsh();
		ssh.put_IdleTimeoutMs(6000);
		ssh.Connect(hostname, port);
		boolean isAuthenticated = ssh.AuthenticatePw(username, password);
		if (!isAuthenticated) {
			System.out.println("Connect to ssh server fail: " + new Date());
			return null;
		}
		return ssh;
	}
}
