import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Test {

	public static void main(String[] args) {
		LocalDate nowDate = LocalDate.now();
		
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		String today = nowDate.format(dateTimeFormatter);
		String nextString = nowDate.plusDays(0).format(dateTimeFormatter);
		System.out.print(today);
		System.out.println(nextString);

	}

}
