package top.it138.facecheck;

import java.util.Properties;

public interface Driver {
	Connection connect(Properties info)
	        throws RecoginitionException;
	
	int getVersion();
	
	String getFullVersion();
}
