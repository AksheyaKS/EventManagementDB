package com.cg.pmdb.dao;

public interface IQueryMapper {
	
	public static final String add_event = "INSERT INTO event_management (serialNumber, title, location, startDate, cost) VALUES (?,?,?,?,?)";
	
	public static final String remove_event = "DELETE FROM event_management WHERE serialNumber = ?";
	
	public static final String list_all_events = "SELECT * FROM event_management ORDER BY title ASC";
	
	public static final String list_all_location = "SELECT * FROM event_management ORDER BY location ASC";
	
	public static final String list_location = "SELECT * FROM event_management WHERE location = ?";
	
	public static final String list_all_date = "SELECT * FROM event_management WHERE startDate = ?";
	
	//public static final String update_event = "UPDATE event_management SET title = ?, location = ?, startDate = ?, cost = ? WHERE serialNumber = ?";
	
	//public static final String list_all_events = "SELECT * FROM event_management";

}
