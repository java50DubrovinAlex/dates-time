package telran.time.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.time.NextFriday13;

class TemperalTests {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void test() {
		LocalDate birtDateAS = LocalDate.of(1799, 6, 6);
		LocalDate barMizvaAS = birtDateAS.plusYears(13);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM, YYYY/d EEEE");
		System.out.printf("Birtdate of AS is %s\n", birtDateAS.format(dtf), barMizvaAS.format(dtf));
		ChronoUnit unit = ChronoUnit .WEEKS;
		System.out.printf("Number of %s between %s and %s is %d\n", unit, 
				birtDateAS, barMizvaAS, unit.between(birtDateAS, barMizvaAS));
	}
	
	@Test
	void next13FridayTest()
	{
		TemporalAdjuster adjuster = new NextFriday13();
		LocalDate ld = LocalDate.of(2023, 8, 10);
		LocalDate expected1 = LocalDate.of(2023, 10, 13);
		LocalDate expected2 = LocalDate.of(2024, 9, 13);
		assertEquals(expected1, ld.with(adjuster));
		assertEquals(expected2, expected1.with(adjuster));
	}


}
            