package model;

import java.util.ArrayList;
import util.Enums;
import util.Enums.RoomType;

public class Room {
    private int number;
    private Building building;
    private int capacity;
    private Enums.RoomType type;
    private ArrayList<Enums.Equipment> equip;
    private boolean status;
    private int[] dimensions = new int[3]; // Width,Height,Length

    public Room(int number, Building building, int capacity, RoomType type, ArrayList<Enums.Equipment> equip, boolean status) {
        this.number = number;
        this.building = building;
        this.type = type;
        this.capacity = capacity;
        this.equip = equip;
        this.status = status;
    }

    public int getNumber() { return number; }
    public Building getBuilding() { return building; }
    public int getCapacity() { return capacity; }
    public Enums.RoomType getType() { return type; }
    public ArrayList<Enums.Equipment> getEquip() { return equip; }
    public boolean getStatus() { return status; }
    public int[] getDimensions() { return dimensions; }

    public void setNumber(int number) { this.number = number; }
    public void setBuilding(Building building) { this.building = building; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public void setType(Enums.RoomType type) { this.type = type; }
    public void setEquip(ArrayList<Enums.Equipment> equip) { this.equip = equip; }
    public void setStatus(boolean status) { this.status = status; }
    public void setDimensions(int[] dimensions) { this.dimensions = dimensions; }

    private String getEquipmentString() {
        StringBuilder equips = new StringBuilder();
        for (Enums.Equipment equipment : this.equip) {
            equips.append(equipment.toString()).append(" "); 
        }
        return equips.toString().trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Room Number: ").append(Integer.toString(this.number))
          .append(" \n Building: ").append(this.building)
          .append(" \n Room Type: ").append(type.toString())
          .append(" \n Dimensions: ").append(this.dimensions)
          .append(" \n Equipment: ").append(getEquipmentString())
          .append(" \n Status: ").append(this.status);
        return sb.toString();
    }
}
