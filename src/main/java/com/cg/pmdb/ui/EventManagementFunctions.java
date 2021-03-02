package com.cg.pmdb.ui;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import com.cg.pmdb.exception.EventManagementException;
import com.cg.pmdb.model.Event;
import com.cg.pmdb.model.EventManagementMenu;
import com.cg.pmdb.service.EventServiceImplementation;
import com.cg.pmdb.service.IEventService;


public class EventManagementFunctions {
	
	private static IEventService eventService; 
	private static Scanner s;
	private static DateTimeFormatter formatter;

	public static void main(String[] args) {
		 
		try {
			
			eventService = new EventServiceImplementation();
			
		} catch (EventManagementException exc) {
			
			exc.printStackTrace();
		}
		
		s = new Scanner(System.in);
		formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		
		EventManagementMenu menu = null;
		
		while(menu != EventManagementMenu.QUIT) {
			
			System.out.println("Choice\tMenu Item");
			System.out.println("===========================");
			
			for (EventManagementMenu m : EventManagementMenu.values()) {          //To access menu
				
				System.out.println(m.ordinal() + "\t" + m.name());
				
			}
			
			System.out.print("Enter your choice: ");
			int choice = -1;
			
			if (s.hasNextInt()) {
			
				choice = s.nextInt();
			} else {
				
				s.next();
				System.out.println("Please choose one of the following: ");
				continue;
			}

			if (choice < 0 || choice >= EventManagementMenu.values().length) {
				
				System.out.println("Invalid Choice");
				
			} else {
				
				menu = EventManagementMenu.values()[choice];
				
				switch (menu) {
				
				case ADD_EVENT:
					addEvent();
					break;
					
				case REMOVE_EVENT:
					removeEvent();
					break;
					
				case LIST_ALL_EVENTS:
					list_all_events();
					break;
					
				case LIST_ALL_LOCATION:
					list_all_location();
					break;
					
				case LIST_LOCATION:
				     list_location();
				     break;
				     
				case LIST_ALL_DATE:
					list_all_date();
					break;
				     
				case QUIT:
					System.out.println("Successfully Exited!");
					break;
				}
			}

		}
		
		s.close();
		
		try {
			
			eventService.persist();
			
		} catch (EventManagementException exc) {
			
			exc.printStackTrace();
		}
			
	}
	
	private static void addEvent() {
		
		try {
			
			Event event = new Event(); 
			System.out.print("Enter Serial Number: ");
			event.setSerialNumber(s.next());
			
			System.out.print("Enter Event title: ");
			event.setTitle(s.next());
			
			System.out.println("Enter the location: ");
			event.setLocation(s.next());
			
			System.out.print("Enter the starting date: ");
			String startDate = s.next();
			
			try {
				
				event.setStartDate(LocalDate.parse(startDate, formatter));
				
			} catch (DateTimeException exp) {
				
				throw new EventManagementException("The start date must be in the format DD-MM-YYYY");
			}
			
			System.out.println("Cost: ");
			if (s.hasNextDouble()) {
				
				event.setCost(s.nextDouble());
				
			} else {
				s.next();
				throw new EventManagementException("Cost is a number");
			}

			String serialNumber = eventService.add_event(event);
			
			System.out.println("Event is Added with the serial number: " + serialNumber);
			
			} catch (EventManagementException exp) {
			
			System.out.println(exp.getMessage());
		}
	}
	
	private static void removeEvent() {
		
		System.out.print("Enter serial Number: ");
		String serialNumber = s.next();
		
		try {
			boolean flag = eventService.remove_event(serialNumber);
			if (flag) {
				
				System.out.println("Event is Deleted");
				
			} else {
				
				System.out.println("No Such Event exists");
			}
		} catch (EventManagementException exp) {
			
			System.out.println(exp.getMessage());
		}
	}		
	
	private static void list_location() {
		
		System.out.print("Enter Location: ");
		String location = s.next();

		try {
			
			List <Event> list = eventService.list_location(location);
			if (list != null) {
				
				System.out.println(list);
				
			} else {
				
				System.out.println("No Such Event exists");
			}
		} catch (EventManagementException exp) {
			
			System.out.println(exp.getMessage());
		}
	}
	
	private static void list_all_date() {
		
		System.out.print("Enter Date: ");
		String date = s.next();

		try {
			
			LocalDate startDate = LocalDate.parse(date, formatter);
			List <Event> list = eventService.list_events_date(startDate);
			if (list != null) {
				
				System.out.println(list);
				
			} else {
				
				System.out.println("No Such Event exists");
			}
		} catch (EventManagementException exp) {
			
			System.out.println(exp.getMessage());
		}
	}
	
	private static void list_all_events() {
		
		List <Event> events;
		try {
			events = eventService.list_events();
			if (events.isEmpty()) {
				
				System.out.println("No Events To display");
				
			} else {
				for (Event e : events)
					System.out.println(e);
			}
		} catch (EventManagementException exp) {
			
			System.out.println(exp.getMessage());
		}
	}
	
	private static void list_all_location() {
		
		List <Event> events;
		try {
			events = eventService.list_events_location();
			if (events.isEmpty()) {
				
				System.out.println("No Events To display");
				
			} else {
				for (Event e : events)
					System.out.println(e);
			}
		} catch (EventManagementException exp) {
			
			System.out.println(exp.getMessage());
		}
	}
	
}

