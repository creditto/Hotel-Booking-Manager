
package com.hb.api.ms.constants;

/**
 * Collection of Error Messages for Invalid Requests
 */
public final class ErrorMessages {
	public static final String INVALID_GUEST_NAME = "Guest Name cannot be null/empty.";
	public static final String INVALID_DATE = "Invalid date: Please input dates in 'yyyy-MM-dd' format.";
	public static final String RESERVATION_DATES = "Invalid Reservation Date: Reservation date should be greater than or equal to current date";
	public static final String INVALID_DATE_NULL_VALUES = "Booking date cannot be null/empty";
	public static final String INVALID_ROOM_NUMBER = "Room Number cannot be null/empty.";
	public static final String INVALID_BODY_TAG = "Invalid Body: Body cannot be null/empty.";
	public static final String LOCK_BOOKING = "The room booking is locked, Please try after some time, ";
	public static final String INVALID_GUEST_NAME_MORETHAN_20 = "Invalid Guest Name: Guest Name cannot be more than 20 chars";
	public static final String INVALID_ROOM_MORETHAN_5 = "Invalid Room Number: Room Number cannot be more than 5 chars";
	public static final String INVALID_GUEST = "Invalid Guest Name: Please input the valid guest name";
	public static final String INVALID_ROOM = "Invalid Room Number: Plaese input the valid room number";

}
