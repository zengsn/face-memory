package top.it138.facesdk;
import org.junit.Test;

public class MyTest {
	
	@Test
	public void test() {
		FaceApp faceApp = FaceAppFactory.getBykeyAndSecret("4344f36a-f3b2-4bdb-9cea-b250e6ca1a73", "fd844b7e-2974-4daf-be59-73fbb078d488");
		try {
			faceApp.createNewPersonId("1345", "fsdfsdf");
			Person p = faceApp.getPersonById(2L);
			System.out.println(p);
		} catch (FaceAppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
