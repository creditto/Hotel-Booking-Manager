package com.hb.api.ms.serviceImp;

import com.hb.api.ms.repository.InMemReservationRepository;
import com.hb.api.ms.repository.ReservationRepository;
import com.hb.api.ms.service.ReservationService;
import com.hb.api.ms.constants.ErrorMessages;
import com.hb.api.ms.dto.ReservationResponeVO;
import com.hb.api.ms.dto.ReservationVO;
import com.hb.api.ms.exception.InvalidRequestException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

/**
 * Reservation Service tha performs operations regarding Reservation API Calls
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service
public class ReservationServiceImp implements ReservationService {

	private static Logger log = Logger.getLogger(ReservationServiceImp.class.getName());

	private final ReservationRepository reservationRepository = InMemReservationRepository.getInstance();

	@Override
	public ResponseEntity<ReservationVO> addBookings(ReservationVO reservationVO) {

		String lockKey = reservationVO.getBookingDate() + "-" + reservationVO.getRoomNo();

		if (isCheckAvailability(lockKey)) {

			ReservationResponeVO reservationResponeVO = null;
			try {
				lock(lockKey);

				reservationResponeVO = reservationRepository.addBookings(reservationVO);

			} catch (Exception e) {
				log.info("Exception occur whick lock the transaction");
			} finally {
				unlock(lockKey);
			}

			return new ResponseEntity(reservationResponeVO, HttpStatus.OK);
		} else {
			throw new InvalidRequestException(ErrorMessages.LOCK_BOOKING);
		}
	}

	@Override
	public ResponseEntity<List<ReservationVO>> searchBookingsByName(String guestName) {

		return new ResponseEntity(reservationRepository.searchBookingsByName(guestName), HttpStatus.OK);
	}

	@Override
	public Integer getAvailableRoomCount(String bookingDate) {
		return reservationRepository.getAvailableRoomCount(bookingDate);
	}

	private static volatile ReservationService reservationService = null;
	private static Map<String, ReentrantLock> lockMap;

	// private constructor
	private ReservationServiceImp() {
	}

	public static ReservationService getInstance() {

		if (reservationService == null) {
			reservationService = new ReservationServiceImp();
			lockMap = new HashMap<>();
		}
		return reservationService;
	}

	public boolean isCheckAvailability(String lockKey) {

		if (lockMap != null && !lockMap.isEmpty()) {

			if (lockMap.containsKey(lockKey)) {

				ReentrantLock lock = lockMap.get(lockKey);

				return (lock != null && lock.isLocked()) ? false : true;
			}
		}

		return true;
	}

	public void lock(String name) {
		ReentrantLock newLock = new ReentrantLock(true);
		ReentrantLock lock;
		synchronized (lockMap) {
			lock = Optional.ofNullable(lockMap.putIfAbsent(name, newLock)).orElse(newLock);
		}
		lock.lock();
	}

	public void unlock(String name) {
		ReentrantLock lock = lockMap.get(name);
		synchronized (lockMap) {
			if (!lock.hasQueuedThreads()) {
				lockMap.remove(name);
			}
		}
		lock.unlock();
	}

}
