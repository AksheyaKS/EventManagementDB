package com.cg.pmdb.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cg.pmdb.exception.EventManagementException;
import com.cg.pmdb.model.Event;

class EventsTest {
	
	static EventServiceImplementation service;

	@BeforeAll
	static void setUpBeforeClass() throws EventManagementException {
		
		service = new EventServiceImplementation();
	}

	@AfterAll
	static void tearDownAfterClass() throws EventManagementException {
		
		service = null;
	}
	
	@Test
	@DisplayName("Should return serialNumber of event")
	void testAddEvent(Event event) throws EventManagementException {
		
		Event e = new Event("E105", "Quincanera", "Pune", LocalDate.parse("25-09-2021", DateTimeFormatter.ofPattern("dd-MM-yyyy")), 500.0);
		String expected = "E105";
        String actual = service.add_event(e);
        assertEquals(expected, actual);
	}
	
	@Test
	@DisplayName("Should return exception")
	void eventAddWithException(Event event) throws EventManagementException {
		
		assertThrows(EventManagementException.class, ()-> {
			
			Event e = new Event("105", "Blooper", "Chennai", LocalDate.parse("27-12-2021", DateTimeFormatter.ofPattern("dd-MM-yyyy")), 500.0);
			service.add_event(e);
			
		});
	}
	
	@Test
	@DisplayName("Should return true")
	void testRemoveEvent(String serialNumber) throws EventManagementException {
		
		serialNumber = "E105";
		boolean expected = true;
		boolean actual = service.remove_event(serialNumber);
		assertEquals(expected,actual);
		
	}

	@Test
	void test() {
		fail("Not yet implemented");
	}

}
