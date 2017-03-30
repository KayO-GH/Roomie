package com.roomiegh.roomie.models;

import java.io.Serializable;

/**
 * Created by KayO on 30/03/2017.
 */

public class Hostel implements Serializable{
    int id, noOfRooms, locationId;
    String name, photopath;
    double rating;

    public Hostel() {
    }

    public Hostel(int id, int noOfRooms, int locationId, String name, String photopath, double rating) {
        this.id = id;
        this.noOfRooms = noOfRooms;
        this.locationId = locationId;
        this.name = name;
        this.photopath = photopath;
        this.rating = rating;
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
        return photopath != null ? photopath.equals(hostel.photopath) : hostel.photopath == null;

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
        temp = Double.doubleToLongBits(rating);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
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
                ", rating=" + rating +
                '}';
    }
}
