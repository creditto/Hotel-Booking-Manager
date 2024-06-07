package com.hb.api.ms.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.hb.api.ms.constants.AppConstants;
import com.hb.api.ms.constants.DBGeneralConstans;
import com.hb.api.ms.dto.ReservationResponeVO;
import com.hb.api.ms.dto.ReservationVO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:application.properties")
public class InMemReservationRepository implements ReservationRepository {

	@Value("${hb.api.ms.room.count}")
	private String actualRoomCount;

	private Integer tempActualRoomCount = 0;

	private Map<String, List<ReservationVO>> bookingVOMap;

	public List<ReservationVO> searchBookingsByName(String guestName) {

		return bookingVOMap.values().stream().flatMap(
				e -> e.stream().filter(a -> a.getGuestName().equals(guestName))).collect(Collectors.toList());
	}

	@Override
	public synchronized ReservationResponeVO addBookings(ReservationVO reservationVO) {

		List<ReservationVO> reservationVOList = bookingVOMap.get(reservationVO.getBookingDate());

		if (reservationVOList == null) {

			reservationVOList = new ArrayList<ReservationVO>();
		}

		ReservationResponeVO bookingResponeVO = new ReservationResponeVO();

		if (validBookingReservation(reservationVO, reservationVOList, bookingResponeVO)) {

			reservationVO.setCreatedDate(new Date());
			reservationVO.setReservationId(UUID.randomUUID().toString());
			reservationVO.setStatus(AppConstants.BOOKED);
			reservationVOList.add(reservationVO);
			bookingVOMap.put(reservationVO.getBookingDate(), reservationVOList);

			bookingResponeVO.setStatus(DBGeneralConstans.SUCCESSFUL.name());
			bookingResponeVO.setMessage(DBGeneralConstans.SUCCESSFUL.getName());
		}

		return bookingResponeVO;
	}

	private boolean validBookingReservation(ReservationVO bookingVO, List<ReservationVO> bookingVOList,
			ReservationResponeVO bookingResponeVO) {

		if (bookingVOList == null || bookingVOList.isEmpty()) {
			return true;
		}

		boolean isValid = true;
		if (tempActualRoomCount <= getBookedRoomsCount()) {
			bookingResponeVO.setStatus(DBGeneralConstans.NOT_EXITS.name());
			bookingResponeVO.setMessage(DBGeneralConstans.NOT_EXITS.getName());
			isValid = false;
		} else {
			ReservationVO vo = bookingVOList.stream().filter(x -> x.getRoomNo().equals(bookingVO.getRoomNo()))
					.findFirst().orElse(null);

			if (vo != null) {
				bookingResponeVO.setStatus(DBGeneralConstans.ENTRY_ALREADY_EXITS.name());
				bookingResponeVO.setMessage(DBGeneralConstans.ENTRY_ALREADY_EXITS.getName());
				isValid = false;
			}
		}

		return isValid;

	}

	@Override
	public Integer getAvailableRoomCount(String bookingDate) {

		List<ReservationVO> bookingVOList = bookingVOMap.get(bookingDate);

		return getAvailableRoomCount(bookingVOList);

	}

	private Integer getAvailableRoomCount(List<ReservationVO> bookingVOList) {

		return (bookingVOList != null && !bookingVOList.isEmpty()) ? (tempActualRoomCount - bookingVOList.size())
				: tempActualRoomCount;

	}

	private Integer getBookedRoomsCount() {
		return bookingVOMap.values().stream().filter(t -> t.size() > 0).mapToInt(x -> x.size()).sum();
	}

	private static volatile ReservationRepository reservationRepository = null;

	// private constructor
	private InMemReservationRepository() {
		bookingVOMap = new Hashtable<String, List<ReservationVO>>();

		try {
			tempActualRoomCount = Integer.parseInt(actualRoomCount);
		} catch (NumberFormatException formatException) {
			tempActualRoomCount = 20;
		}
	}

	public static ReservationRepository getInstance() {
		if (reservationRepository == null) {

			if (reservationRepository == null) {
				reservationRepository = new InMemReservationRepository();
			}
		}
		return reservationRepository;
	}
}
