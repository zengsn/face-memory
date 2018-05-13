package com.it138.impl;

import java.util.Properties;

import top.it138.facecheck.Connection;
import top.it138.facecheck.DriverManager;

public class Driver implements top.it138.facecheck.Driver {
	
	static {
		DriverManager.registerDriver(new Driver());
	}

	public Connection connect(Properties info) {
		return new com.it138.impl.Connection(info);
	}

	public int getVersion() {
		return 2;
	}

	public String getFullVersion() {
		return "myRecoition2.0";
	}

}
