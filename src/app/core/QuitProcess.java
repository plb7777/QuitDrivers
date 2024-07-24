package app.core;

import java.util.ArrayList;
import java.util.List;

public class QuitProcess {
	
	private static QuitProcess _this;
	
	public static QuitProcess getInstance() {
		if (_this == null) {
			_this = new QuitProcess();
		}
		return _this;
	}

	private Process proc;
	private int exitCode;
	
	private String driverServer;

	public void quitAllDriverServers() {
		try {
			List<String> args = new ArrayList<>();
			args.add("taskkill");
			args.add("/F");
			args.add("/IM");
			args.add(driverServer);
			ProcessBuilder pb = new ProcessBuilder(args);
			proc = pb.start();
			exitCode = proc.waitFor();
		} catch (Exception e) {
			
		}
	}	
	
	public int getExitCode() {
		return exitCode;
	}

	public void setExitCode(int exitCode) {
		this.exitCode = exitCode;
	}	

	public void setDriverServer(String driverServer) {
		this.driverServer = driverServer;
	}

	public void stop() {
		try {
			proc.destroy();
		} catch (Exception e) {
		}
	}

}
