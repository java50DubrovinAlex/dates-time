package telran.time;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.UnsupportedTemporalTypeException;

public class NextFriday13 implements TemporalAdjuster {

	@Override
	public Temporal adjustInto(Temporal temporal) {
		if(!temporal.isSupported(ChronoUnit.YEARS)&&temporal.isSupported(ChronoUnit.MONTHS)
				&&temporal.isSupported(ChronoUnit.DAYS))
		{
			throw new UnsupportedTemporalTypeException("must support yyyy.mm.dd");
		}
//		LocalDate  ld = LocalDate.from(temporal);
//		int dayOfMonth = ld.get(ChronoField.DAY_OF_MONTH);
//		if(dayOfMonth < 13 ) {
//			ld = ld.withDayOfMonth(13);
//		}else if(dayOfMonth >= 13) {
//			ld = ld.plus(1, ChronoUnit.MONTHS).withDayOfMonth(13);
//		}
//		while(ld.get(ChronoField.DAY_OF_WEEK) != 5) {
//			ld = ld.plus(1, ChronoUnit.MONTHS);
//		}
//		return ld;
		Temporal resTempolar = temporal;
		int localDate = temporal.get(ChronoField.DAY_OF_MONTH);
		int plusToGetFriday13 = localDate - 13;
//		if(plusToGetFriday < 0) {
//			resTempolar = resTempolar.plus(Math.abs(plusToGetFriday), ChronoUnit.DAYS);
//			
//		}else if(plusToGetFriday >= 0) {
//			resTempolar = resTempolar.plus(1, ChronoUnit.MONTHS)
//					.minus(Math.abs(plusToGetFriday), ChronoUnit.DAYS);
//		}
		resTempolar = plusToGetFriday13 < 0 ?  resTempolar.plus(Math.abs(plusToGetFriday13), ChronoUnit.DAYS):
			resTempolar.plus(1, ChronoUnit.MONTHS).minus(Math.abs(plusToGetFriday13), ChronoUnit.DAYS);	
		while(resTempolar.get(ChronoField.DAY_OF_WEEK) != 5) {
			resTempolar = resTempolar.plus(1, ChronoUnit.MONTHS);
		}
		return resTempolar;
	}

}
