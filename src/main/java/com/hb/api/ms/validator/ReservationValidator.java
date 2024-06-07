package com.hb.api.ms.validator;

import com.hb.api.ms.exception.InvalidRequestException;
import com.hb.api.ms.utils.DateUtils;
import com.hb.api.ms.constants.ErrorMessages;

import java.text.ParseException;
import java.util.Date;
import java.util.logging.Logger;

import com.hb.api.ms.constants.AppConstants;
import com.hb.api.ms.dto.ReservationVO;

/**
 * Reservation Validator that ensures user inputs are correctly formatted
 */
public class ReservationValidator {

	private static Logger log = Logger.getLogger(ReservationValidator.class.getName());

	/**
	 * Validator for the Reservation POST call
	 *
	 * @param reservation
	 */
	public static void validateReservation(ReservationVO reservationVO) {

		if (reservationVO == null) {
			throw new InvalidRequestException(ErrorMessages.INVALID_BODY_TAG);
		}
		validateBookingDate(reservationVO.getBookingDate(), true);
		validateGuestName(reservationVO.getGuestName());
		validateRoomNumber(reservationVO.getRoomNo());
	}

	/**
	 * Validator for the Hotel id
	 *
	 * @param id
	 */
	public static void validateRoomNumber(String roomNumber) {
		if (roomNumber == null || roomNumber.length() == 0) {
			throw new InvalidRequestException(ErrorMessages.INVALID_ROOM_NUMBER);
		} else if(roomNumber.length() > 5) {
			log.info("Invalid Room Number: '{}', Invalid Room Number: Room Number cannot be more than 5 chars." + roomNumber);
			throw new InvalidRequestException(ErrorMessages.INVALID_ROOM_MORETHAN_5);
		} else if (!isValid(roomNumber, AppConstants.ROO_NO_REGEX)) {
			log.info("Invalid Room Number: '{}', Invalid Room Number:" + roomNumber);
			throw new InvalidRequestException(ErrorMessages.INVALID_ROOM);
		}
	}

	/**
	 * Validator for the guest name
	 *
	 * @param guestName
	 */
	public static void validateGuestName(String guestName) {

		if (guestName == null || guestName.length() == 0) {
			log.info("Invalid Guests Name: '{}', Guests name can not be null." + guestName);
			throw new InvalidRequestException(ErrorMessages.INVALID_GUEST_NAME);
		} else if(guestName.length() > 20) {
			log.info("Invalid Guests Name: '{}', Invalid Guest Name: Guest Name cannot be more than 20 chars." + guestName);
			throw new InvalidRequestException(ErrorMessages.INVALID_GUEST_NAME_MORETHAN_20);
		} else if (!isValid(guestName, AppConstants.GUEST_NAME_REGEX)) {
			log.info("Invalid Guests Name: '{}', Invalid Guest Name:" + guestName);
			throw new InvalidRequestException(ErrorMessages.INVALID_GUEST);
		}
	}

	/**
	 * Validator for 'bookingDate'
	 *
	 * @param bookingDate
	 */

	public static void validateBookingDate(String bookingDate, boolean isvalidateCurrentDate) {

		if (bookingDate == null || bookingDate.length() == 0) {
			throw new InvalidRequestException(ErrorMessages.INVALID_DATE_NULL_VALUES);
		} else if (!bookingDate.matches(AppConstants.DATE_FORMAT_REGEX)) {
			throw new InvalidRequestException(ErrorMessages.INVALID_DATE);
		} else {

			try {

				Date date = DateUtils.formatteDate(bookingDate);

				if (isvalidateCurrentDate) {

					Date currentDate = DateUtils.getDate();

					if (date.before(currentDate)) {
						throw new InvalidRequestException(ErrorMessages.RESERVATION_DATES);
					}
				}

			} catch (ParseException ex) {
				throw new InvalidRequestException(ErrorMessages.INVALID_DATE);
			}
		}
	}

	public static void validateBookingDate(String bookingDate) {

		validateBookingDate(bookingDate, false);
	}
	
	public static boolean isValid(String value, String expression) {
	    return value.matches(expression);        
	}
}
