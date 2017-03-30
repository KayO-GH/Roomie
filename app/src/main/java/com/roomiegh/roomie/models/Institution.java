package com.roomiegh.roomie.models;

import java.io.Serializable;

/**
 * Created by Kwadwo Agyapon-Ntra on 31/07/2015.
 */
public class Institution implements Serializable {
    private String insName;

    public Institution() {
    }

    public Institution(String insName) {
        this.insName = insName;
    }

    public String getInsName() {
        return insName;
    }

    public void setInsName(String insName) {
        this.insName = insName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Institution that = (Institution) o;

        return !(insName != null ? !insName.equals(that.insName) : that.insName != null);

    }

    @Override
    public int hashCode() {
        return insName != null ? insName.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Institution{" +
                "insName='" + insName + '\'' +
                '}';
    }
}
