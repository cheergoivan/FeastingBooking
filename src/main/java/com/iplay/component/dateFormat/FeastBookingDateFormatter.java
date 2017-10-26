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
	
	@Value("${feast-booking.datetime.format}")
	private String defaultDateTimeFormat;
	
	@Value("${feast-booking.date.order.creationTime.format}")
	private String orderCreationTimeFormat;
	
	@Value("${feast-booking.date.order.lastUpdated.format}")
	private String orderLastUpdatedFormat;
	
	public String toDefaultFormattedDate(long date) {
		return format(defaultDateFormat, date);
	}
	
	public String toDefaultFormattedDateTime(long date) {
		return format(defaultDateTimeFormat, date);
	}

	public String toOrderCreationTime(long date) {
		return format(orderCreationTimeFormat, date);
	}
	
	public String toOrderLastUpdated(long date) {
		return format(orderLastUpdatedFormat, date);
	}
	
	public Date parseToDateWithDefaultDateFormat(String date) throws ParseException{
		return parse(defaultDateFormat, date);
	}
	
	public String getDefaultDateFormat(){
		return defaultDateFormat;
	}
	
	public String getDefaultDateTimeFormat(){
		return defaultDateTimeFormat;
	}
	
	private String format(String format, long time) {
		return new SimpleDateFormat(format).format(new Date(time));
	}
	
	private Date parse(String format, String date) throws ParseException {
		return new SimpleDateFormat(format).parse(date);
	}
	
}
