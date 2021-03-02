package com.cg.pmdb.dao;


import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.cg.pmdb.exception.EventManagementException;
import com.cg.pmdb.model.Event;
import com.cg.pmdb.uitl.ConnectionProvider;

public class EventImplementation implements IEventDAO{
	
	ConnectionProvider conProvider;
	
	public EventImplementation() throws EventManagementException {
		
		try {
			conProvider = ConnectionProvider.getInstance();
			
		} catch (ClassNotFoundException | IOException exp) {
			
			throw new EventManagementException("Database is not reachable");
		}
	}
	@Override
	public String add_event(Event event) throws EventManagementException {
		
		String serialNumber = null;
		if (event != null) {
			try (Connection con = conProvider.getConnection();
				PreparedStatement insert = con.prepareStatement(IQueryMapper.add_event)) {

				insert.setString(1, event.getSerialNumber());
				insert.setString(2, event.getTitle());
				insert.setString(3, event.getLocation());
				insert.setDate(4, Date.valueOf(event.getStartDate()));
				insert.setDouble(5, event.getCost());

				int rowCount = insert.executeUpdate();

				if (rowCount == 1) {
					
					serialNumber = event.getSerialNumber();
				}
			} catch (SQLException exp) {
				
				throw new EventManagementException("Event is not inserted");
			}
		}
		return serialNumber;
	}
	
	@Override
	public boolean remove_event(String serialNumber) throws EventManagementException {
		
		boolean isDone = false;

		try (Connection con = conProvider.getConnection();
			PreparedStatement delete = con.prepareStatement(IQueryMapper.remove_event)) {

			delete.setString(1, serialNumber);

			int rowCount = delete.executeUpdate();

			if (rowCount == 1) {
				
				isDone = true;
			}
			
		} catch (SQLException exp) {
	
			throw new EventManagementException("Event is not available");
		}

		return isDone;
		
	}
	
	@Override
	public List <Event> list_events_location() throws EventManagementException {
		
		List <Event> events = null;
		try (Connection con = conProvider.getConnection();
			PreparedStatement select = con.prepareStatement(IQueryMapper.list_all_location)) {

			ResultSet rs = select.executeQuery();
			
			events = new ArrayList <Event> ();
			
			while(rs.next()){
				Event event = new Event();
				event.setSerialNumber(rs.getString("serialNumber"));
				event.setTitle(rs.getString("title"));
				event.setLocation(rs.getString("location"));
			    event.setStartDate(rs.getDate("startDate").toLocalDate());
				event.setCost(rs.getDouble("cost"));
				events.add(event);
			}
			
		} catch (SQLException exp) {
			
			throw new EventManagementException("No Events are available.");
		}		
		return events;	
		
	}
	
	@Override
	public List <Event> list_events() throws EventManagementException {
		
		List <Event> events = null;
		try (Connection con = conProvider.getConnection();
			PreparedStatement select = con.prepareStatement(IQueryMapper.list_all_events)) {

			ResultSet rs = select.executeQuery();
			
			events = new ArrayList <Event> ();
			
			while(rs.next()){
				Event event = new Event();
				event.setSerialNumber(rs.getString("serialNumber"));
				event.setTitle(rs.getString("title"));
				event.setLocation(rs.getString("location"));
			    event.setStartDate(rs.getDate("startDate").toLocalDate());
				event.setCost(rs.getDouble("cost"));
				events.add(event);
			}
			
		} catch (SQLException exp) {
			
			throw new EventManagementException("No Events are available.");
		}		
		return events;	
		
	}
	
	@Override
	public List <Event> list_events_date(LocalDate startDate) throws EventManagementException {
		
		List <Event> events = null;
		try (Connection con = conProvider.getConnection();
			PreparedStatement select = con.prepareStatement(IQueryMapper.list_all_date)) {
			
			select.setDate(1, Date.valueOf(startDate));

			ResultSet rs = select.executeQuery();
			
			events = new ArrayList <Event> ();
			
			while(rs.next()){
				
				Event event = new Event();
				event.setSerialNumber(rs.getString("serialNumber"));
				event.setTitle(rs.getString("title"));
				event.setLocation(rs.getString("location"));
			    event.setStartDate(rs.getDate("startDate").toLocalDate());
				event.setCost(rs.getDouble("cost"));
				events.add(event);
			}
			
		} catch (SQLException exp) {
			
			throw new EventManagementException("No Events are available.");
		}		
		return events;
		
	}
	
	@Override
	public List <Event> list_location(String location) throws EventManagementException {
		
		List <Event> events = null;
		try (Connection con = conProvider.getConnection();
			PreparedStatement select = con.prepareStatement(IQueryMapper.list_location)) {
			
			select.setString(1, location);

			ResultSet rs = select.executeQuery();
			
			events = new ArrayList <Event> ();
			
			while(rs.next()){
				
				Event event = new Event();
				event.setSerialNumber(rs.getString("serialNumber"));
				event.setTitle(rs.getString("title"));
				event.setLocation(rs.getString("location"));
			    event.setStartDate(rs.getDate("startDate").toLocalDate());
				event.setCost(rs.getDouble("cost"));
				events.add(event);
			}
			
		} catch (SQLException exp) {
			
			throw new EventManagementException("No Events are available.");
		}		
		return events;
		
	}
	
	@Override
	public void persist() throws EventManagementException {

	}
}
