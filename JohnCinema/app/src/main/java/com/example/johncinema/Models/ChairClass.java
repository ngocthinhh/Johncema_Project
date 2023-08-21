package com.example.johncinema.Models;

public class ChairClass {

    private String id_chair;
    private Integer row;
    private Integer column;
    private String id_schedule;
    private String state;

    public ChairClass (String id_chair , Integer row, Integer column, String id_schedule, String state)
    {
        this.id_chair = id_chair;
        this.setRow(row);
        this.setColumn(column);
        this.setId_schedule(id_schedule);
        this.state = state;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public String getId_schedule() {
        return id_schedule;
    }

    public void setId_schedule(String id_schedule) {
        this.id_schedule = id_schedule;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getId_chair() {
        return id_chair;
    }

    public void setId_chair(String id_chair) {
        this.id_chair = id_chair;
    }
}
