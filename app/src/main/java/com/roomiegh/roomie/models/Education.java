package com.roomiegh.roomie.models;

import java.io.Serializable;

/**
 * Created by Kwadwo Agyapon-Ntra on 31/07/2015.
 */
public class Education implements Serializable{
    private int year, refNo,insID;
    private String programme;

    public Education() {
    }

    public Education(int year, int refNo, int insID, String programme) {
        this.year = year;
        this.refNo = refNo;
        this.insID = insID;
        this.programme = programme;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getRefNo() {
        return refNo;
    }

    public void setRefNo(int refNo) {
        this.refNo = refNo;
    }

    public int getInsID() {
        return insID;
    }

    public void setInsID(int insID) {
        this.insID = insID;
    }

    public String getProgramme() {
        return programme;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    @Override
    public boolean equals(Object o) {


        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Education education = (Education) o;

        if (year != education.year) return false;
        if (refNo != education.refNo) return false;
        if (insID != education.insID) return false;
        return !(programme != null ? !programme.equals(education.programme) : education.programme != null);

    }

    @Override
    public int hashCode() {
        int result = year;
        result = 31 * result + refNo;
        result = 31 * result + insID;
        result = 31 * result + (programme != null ? programme.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Education{" +
                "year=" + year +
                ", refNo=" + refNo +
                ", insID=" + insID +
                ", programme='" + programme + '\'' +
                '}';
    }
}
