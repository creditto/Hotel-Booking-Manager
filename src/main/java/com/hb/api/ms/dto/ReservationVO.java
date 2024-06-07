package com.hb.api.ms.dto;

import java.util.Date;

public class ReservationVO {

	private String guestName;
	private String roomNo;
	private String bookingDate;
	private Date createdDate = new Date();
	private String reservationId;
	private String status;
	

	public ReservationVO(String guestName, String roomNo, String bookingDate, Date createdDate) {
		this.guestName = guestName;
		this.roomNo = roomNo;
		this.bookingDate = bookingDate;
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "BookingVO{" + "guestName='" + guestName + '\'' + ", roomNo='" + roomNo + '\'' + ", bookingDate=" + bookingDate
				+ ", createdDate=" + createdDate + '}';
	}

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public String getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getReservationId() {
		return reservationId;
	}

	public void setReservationId(String reservationId) {
		this.reservationId = reservationId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
