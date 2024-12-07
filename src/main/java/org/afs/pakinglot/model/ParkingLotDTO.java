package org.afs.pakinglot.model;

import org.afs.pakinglot.domain.Ticket;

import java.util.List;

public class ParkingLotDTO {
    private int id;
    private String name;
    private int capacity;
    private List<Ticket> tickets;

    public ParkingLotDTO(int id, String name, int capacity, List<Ticket> tickets) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.tickets = tickets;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
