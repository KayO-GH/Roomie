package com.roomiegh.roomie.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by KayO on 30/03/2017.
 */

public class Hostel implements Serializable{
    int id, noOfRooms, locationId;
    //TODO change photopath to ArrayList of photopaths when we get multiple photos per hostel
    String name, photopath, location;
    double rating;
    String description;
    ArrayList<String> allFacilities;

    public Hostel() {
    }

    public Hostel(int id, int noOfRooms, int locationId, String name, String photopath, String location, double rating, String description, ArrayList<String> allFacilities) {
        this.id = id;
        this.noOfRooms = noOfRooms;
        this.locationId = locationId;
        this.name = name;
        this.photopath = photopath;
        this.location = location;
        this.rating = rating;
        this.description = description;
        this.allFacilities = allFacilities;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNoOfRooms() {
        return noOfRooms;
    }

    public void setNoOfRooms(int noOfRooms) {
        this.noOfRooms = noOfRooms;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotopath() {
        return photopath;
    }

    public void setPhotopath(String photopath) {
        this.photopath = photopath;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getAllFacilities() {
        return allFacilities;
    }

    public void setAllFacilities(ArrayList<String> allFacilities) {
        this.allFacilities = allFacilities;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hostel hostel = (Hostel) o;

        if (id != hostel.id) return false;
        if (noOfRooms != hostel.noOfRooms) return false;
        if (locationId != hostel.locationId) return false;
        if (Double.compare(hostel.rating, rating) != 0) return false;
        if (name != null ? !name.equals(hostel.name) : hostel.name != null) return false;
        if (photopath != null ? !photopath.equals(hostel.photopath) : hostel.photopath != null)
            return false;
        if (location != null ? !location.equals(hostel.location) : hostel.location != null)
            return false;
        if (description != null ? !description.equals(hostel.description) : hostel.description != null)
            return false;
        return allFacilities != null ? allFacilities.equals(hostel.allFacilities) : hostel.allFacilities == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + noOfRooms;
        result = 31 * result + locationId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (photopath != null ? photopath.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        temp = Double.doubleToLongBits(rating);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (allFacilities != null ? allFacilities.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Hostel{" +
                "id=" + id +
                ", noOfRooms=" + noOfRooms +
                ", locationId=" + locationId +
                ", name='" + name + '\'' +
                ", photopath='" + photopath + '\'' +
                ", location='" + location + '\'' +
                ", rating=" + rating +
                ", description='" + description + '\'' +
                ", allFacilities=" + allFacilities +
                '}';
    }
}
