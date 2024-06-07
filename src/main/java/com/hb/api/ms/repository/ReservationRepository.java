package com.hb.api.ms.repository;

import java.util.List;

import com.hb.api.ms.dto.ReservationResponeVO;
import com.hb.api.ms.dto.ReservationVO;

public interface ReservationRepository {

	ReservationResponeVO addBookings(ReservationVO reservationVO);

	List<ReservationVO> searchBookingsByName(String guestName);

	Integer getAvailableRoomCount(String bookingDate);
}
