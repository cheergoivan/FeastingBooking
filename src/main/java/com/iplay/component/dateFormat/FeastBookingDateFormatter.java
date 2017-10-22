package com.iplay.component.dateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FeastBookingDateFormatter {
	
	@Value("${feast-booking.date.format}")
	private String defaultDateFormat;
	
	public String toDefaultFormattedDate(long date) {
		return new SimpleDateFormat(defaultDateFormat).format(new Date(date));
	}
	
	public Date parse(String date) throws ParseException{
		return new SimpleDateFormat(defaultDateFormat).parse(date);
	}
	
	public String getDefaultDateFormat(){
		return defaultDateFormat;
	}
	
}
