package com.hb.api.ms.serviceImp;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hb.api.ms.controller.ReservationController;
import com.hb.api.ms.dto.ReservationVO;
import com.hb.api.ms.service.ReservationService;

@WebMvcTest(ReservationController.class)
public class ReservationServiceImpMockitoTest {
	@MockBean
	private ReservationService reservationService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void shouldCreateReservation() throws Exception {
		ReservationVO tutorial = new ReservationVO("Thadeuse", "100", "2024-10-12", null);

		mockMvc.perform(post("/api/v1/reservation").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(tutorial))).andExpect(status().isCreated()).andDo(print());
	}

	@Test
	void shouldReturnReservation() throws Exception {

		mockMvc.perform(
				get("/api/v1/reservation-details/{guestName}", "Thadeuse").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print());
	}

	@Test
	void shouldReturnAvailableRoomCount() throws Exception {

		mockMvc.perform(get("/api/v1/available-room/{date}", "2024-10-12").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print());
	}
}
