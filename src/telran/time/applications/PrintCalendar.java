package telran.time.applications;

import java.time.*;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Arrays;
import java.util.Locale;

public class PrintCalendar {
	private static final int TITLE_OFFSET = 10;
	private static final int WEEK_DAYS_OFFSET = 2;
	private static final int COLUMN_WIDTH = 4;
    private static DayOfWeek[] weekDays;
    private static Locale LOCALE = Locale.getDefault();
	public static void main(String[] args) {
		try {
			RecordArguments recordArguments = getRecordArguments(args);
			weekDays = getWeekDaysOrder(recordArguments);
			printCalendar(recordArguments);

		} catch (RuntimeException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

	private static DayOfWeek[] getWeekDaysOrder(RecordArguments recordArguments) {
		DayOfWeek[] res = DayOfWeek.values();
		if(recordArguments.firstDay() != null) {
			DayOfWeek [] newWeekDayOrder = new DayOfWeek [res.length];
			int indexOfFirstDay = Arrays.binarySearch(res, recordArguments.firstDay());
			int firstPartAndIndex = newWeekDayOrder.length - indexOfFirstDay;
			int index = 0;
			for(int i = 0; i < firstPartAndIndex; i++ ) {
				newWeekDayOrder[i] = res[indexOfFirstDay++];
			}
			for(int i = firstPartAndIndex; i < newWeekDayOrder.length; i++) {
				newWeekDayOrder[i] = res[index++];
			}
			res = newWeekDayOrder;
			}
			
		
		return res;
	}

	private static void printCalendar(RecordArguments recordArguments) {
		printTitle(recordArguments.month(), recordArguments.year());
		printWeekDays(getWeekDaysOrder(recordArguments));
		printDays(recordArguments.month(), recordArguments.year());
		
	}

	private static void printDays(int month, int year) {

		int nDays = getMonthDays(month, year);
		int currentWeekDay = getFirstMonthWeekDay(month, year);
		System.out.printf("%s", " ".repeat(getFirstColumnOffset(currentWeekDay)));
		for(int day = 1; day <= nDays; day++) {
			System.out.printf("%4d", day);
			
			if (currentWeekDay == 7) {
				currentWeekDay = 0;
				System.out.println();
			}
			currentWeekDay++;
		}
		
	}

	private static int getFirstColumnOffset(int currentWeekDay) {
		
		return COLUMN_WIDTH * (currentWeekDay - 1);
	}

	private static int getFirstMonthWeekDay(int month, int year) {
		int newFirstDayWeek = getFirstDayWeeekNumber(weekDays);
		LocalDate ld = LocalDate.of(year, month, 1);
		return ld.get(ChronoField.DAY_OF_WEEK) ;
	}

	private static int getFirstDayWeeekNumber(DayOfWeek[] weekDays ) {
//		LocalDate ld = LocalDate.now();
//        DayOfWeek firstDayOfWeek = weekDays[0]; // Assumes that the array starts with SUNDAY
//
//        TemporalField field = WeekFields.of(firstDayOfWeek, 1).dayOfWeek();
//        int firstDayOfWeekNumber = ld.with(TemporalAdjusters.previousOrSame(firstDayOfWeek)).get(field);
//
//        return firstDayOfWeekNumber;
		return weekDays[0].ordinal();
	}

	private static int getMonthDays(int month, int year) {
		YearMonth ym = YearMonth.of(year, month);
		return ym.lengthOfMonth();
	}

	private static void printWeekDays(DayOfWeek[] weekDays) {
		System.out.printf("%s", " ".repeat(WEEK_DAYS_OFFSET));
		for(DayOfWeek dayWeek: weekDays) {
			System.out.printf("%s ",dayWeek.getDisplayName(TextStyle.SHORT, LOCALE));
		}
		System.out.println();
		
	}

	private static void printTitle(int month, int year) {
		Month monthEn = Month.of(month);
		System.out.printf("%s%s %d\n", " ".repeat(TITLE_OFFSET),
				monthEn.getDisplayName(TextStyle.FULL, LOCALE), year);
		
	}

	private static RecordArguments getRecordArguments(String[] args) throws Exception{
		
		int month = getMonthArg(args);
		int year = getYearArg(args);
		DayOfWeek dayOfWeek = getFirstDayOfWeek(args);
		return new RecordArguments(month, year, dayOfWeek); 
	}

	private static DayOfWeek getFirstDayOfWeek(String[] args) {
		// TODO Auto-generated method stub
		DayOfWeek firstDayRes = null;
		if(args.length == 3) {
			String dayName = args[2].toUpperCase();
			firstDayRes = DayOfWeek.valueOf(dayName);
		}
		return firstDayRes;
	}

	private static int getYearArg(String[] args) throws Exception {
		int yearRes = LocalDate.now().getYear();
		if(args.length > 1) {
			try {
				yearRes = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				throw new Exception("year must be a number");
			}
		}
		return yearRes;
	}

	private static int getMonthArg(String[] args) throws Exception{
		int monthRes = LocalDate.now().getMonthValue();
		if (args.length > 0)  {
			try {
				monthRes = Integer.parseInt(args[0]);
				if(monthRes < 1) {
					throw new Exception("Month value must not be less than 1");
				}
				if (monthRes > 12) {
					throw new Exception("Month value must not be greater than 12");
				}
			} catch (NumberFormatException e) {
				throw new Exception("Month value must be a number");
			}
		}
		return monthRes;
	}

}