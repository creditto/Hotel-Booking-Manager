package com.hb.api.ms.controller;

import com.hb.api.ms.dto.ReservationVO;
import com.hb.api.ms.service.ReservationService;
import com.hb.api.ms.serviceImp.ReservationServiceImp;
import com.hb.api.ms.validator.ReservationValidator;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

/**
 * Reservation Controller containing endpoints of Reservation related API calls
 */
@RestController
@RequestMapping("/api/v1")
@CrossOrigin()
public class ReservationController {

	Logger logger = Logger.getLogger(ReservationController.class.getName());

	private final ReservationService reservationService = ReservationServiceImp.getInstance();

	@ApiOperation("Creates a hotel reservation")
	@ApiResponses(@ApiResponse(code = 200, message = "Creates a hotel reservation"))
	@PostMapping(value = "/reservation", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ReservationVO> addBookings(@RequestBody ReservationVO reservationVO) {

		logger.info("Save a user specified reservation...");
		ReservationValidator.validateReservation(reservationVO);

		return reservationService.addBookings(reservationVO);
	}

	@ApiOperation("Get the hotel reservation details based on guest name")
	@ApiResponses(@ApiResponse(code = 200, message = "Get the hotel reservation details based on guest name"))
	@GetMapping(value = "/reservation-details/{guestName}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ReservationVO>> searchBookingsByName(@PathVariable String guestName) {

		logger.info("Search the reservation details by guests...");
		ReservationValidator.validateGuestName(guestName);

		return reservationService.searchBookingsByName(guestName);
	}

	@ApiOperation("Get the available room count based on date")
	@ApiResponses(@ApiResponse(code = 200, message = "Available room count"))
	@GetMapping(value = "/available-room/{date}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Integer getAvailableRoomCount(@PathVariable String date) {

		logger.info("Get the available room count for the date...");
		ReservationValidator.validateBookingDate(date);
		return reservationService.getAvailableRoomCount(date);
	}
}
