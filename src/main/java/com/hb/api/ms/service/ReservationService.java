package com.hb.api.ms.service;

import com.hb.api.ms.dto.ReservationVO;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface ReservationService {

	ResponseEntity<ReservationVO> addBookings(ReservationVO reservationVO);
	
	ResponseEntity<List<ReservationVO>> searchBookingsByName(String guestName);

	Integer getAvailableRoomCount(String bookingDate);
}
