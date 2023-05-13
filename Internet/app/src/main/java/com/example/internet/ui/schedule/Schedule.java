package com.example.internet.ui.schedule;

public class Schedule {
    private int id;
    private String name;
    private String dateBegin;
    private String dateEnd;
    private String times;
    private String description;

    public Schedule(int id, String name, String dateBegin, String dateEnd, String times, String description) {
        this.id = id;
        this.name = name;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.times = times;
        this.description = description;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDateBegin() { return dateBegin; }
    public void setDateBegin(String dateBegin) { this.dateBegin = dateBegin; }

    public String getDateEnd() { return dateEnd; }
    public void setDateEnd(String dateEnd) { this.dateEnd = dateEnd; }

    public String getTimes() { return times; }
    public void setTimes(String times) { this.times = times; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
