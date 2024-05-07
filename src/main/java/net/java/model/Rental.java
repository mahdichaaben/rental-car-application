package net.java.model;

public class Rental {
	private String id;
    private String user_id;
    private String registrationNumber;
    private String startDate;
    private String endDate;
    private String note;
   // user_id, registrationNumber, startDate, endDate, note
    public Rental(String id,String user_id, String registrationNumber, String startDate, String endDate , String note) {
    	this.id = id;
    	this.user_id = user_id;
        this.registrationNumber = registrationNumber;
        this.startDate = startDate;
        this.endDate = endDate;
        this.note = note;
    }


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }



    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }



    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


}
