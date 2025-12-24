package com.saf.messages;

public class TicketDTO {

    private String id;
    private String title;
    private String description;
    private TicketPriority priority;
    private TicketStatus status;
    private String createdByName;

    public TicketDTO() {}

    public TicketDTO(String i, String t, String d, TicketPriority p, TicketStatus s, String n) {
        this.id = i;
        this.title = t;
        this.description = d;
        this.priority = p;
        this.status = s;
        this.createdByName = n;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public  void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(TicketPriority priority) {
        this.priority = priority;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public TicketPriority getPriority() {
        return priority;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    @Override
    public String toString() {
        return "Ticket ->" +
                "\nId : " + id +
                "\nTitle : " + title +
                "\nDescription : " + description +
                "\nPriority : " + priority +
                "\nStatus : " + status +
                "\nCreatedByName : " + createdByName;
    }
}
