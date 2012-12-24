package uk.ac.edu4all.data;

import java.util.Calendar;
import java.util.UUID;

import javax.validation.constraints.*;
import javax.validation.constraints.Pattern.Flag;

import org.hibernate.validator.constraints.CreditCardNumber;

import uk.ac.edu4all.domain.*;

public class CourseRegistration {
	
	public enum Status {BEGIN, ADDRESS, PAYMENT, CONFIRM, FINISH};
	public static final String[] MONTHS = new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
	public static final int CURRENT_YEAR = Calendar.getInstance().get(Calendar.YEAR);
 	
	private String uuid = UUID.randomUUID().toString();
	private Status registartionStatus = Status.BEGIN;
	private User user;
	private Course course;
	private String street, town, postcode, name;
	private CharSequence cardNumber, code;
	private int month = Calendar.getInstance().get(Calendar.MONTH), year = CURRENT_YEAR;
	
	public String getUuid() {
		return uuid;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	@Size(min=5, max=50, message="The House Number/Street field must be between 5 and 50 characters long")
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	@Size(min=2, max=30, message="A valid Town name required")
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	@Pattern(regexp = "^[A-Z]{1,2}[0-9R][0-9A-Z]?\\s[0-9][ABD-HJLNP-UW-Z]{2}$", flags=Flag.CASE_INSENSITIVE, message="The Postcode field must be a valid UK postcode" )
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode.toUpperCase();
	}
	@Size(min=2, max=30, message="Card Holder Name required (as printed on your card)")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Pattern(regexp = "^[0-9]{3,4}$", message="The Card Verification code must 3 or 4 digits long" )
	public CharSequence getCode() {
		return code;
	}
	public void setCode(CharSequence code) {
		this.code = code;
	}
	@Pattern(regexp = "^[0-9]{8,16}$", message="The Credit Card Number does not seem to be valid" )
	@CreditCardNumber( message="The Credit Card Number does not seem to be valid")
	public CharSequence getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(CharSequence cardNumber) {
		this.cardNumber = cardNumber;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public Status getRegistrationStatus() {
		return registartionStatus;
	}
	public void setRegistartionStatus(Status registartionStatus) {
		this.registartionStatus = registartionStatus;
	}
	public String[] getMonths() {
		return MONTHS;
	}
	public int getCurrentYear() {
		return CURRENT_YEAR;
	}
	public String getSecureCardNo() {
		String ret = "";
		for(int i=0; i<cardNumber.length()-4; i++) ret += "x";
		return ret + cardNumber.subSequence(cardNumber.length()-4, cardNumber.length());
	}
}
