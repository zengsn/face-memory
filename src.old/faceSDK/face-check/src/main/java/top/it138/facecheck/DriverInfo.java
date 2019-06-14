package top.it138.facecheck;

public class DriverInfo {
	public final Driver driver;

	public DriverInfo(Driver driver) {
		super();
		this.driver = driver;
	}

	public Driver getDriver() {
		return driver;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DriverInfo other = (DriverInfo) obj;
		if (driver == null) {
			if (other.driver != null)
				return false;
		}
		return true;
	}
	
	
}
