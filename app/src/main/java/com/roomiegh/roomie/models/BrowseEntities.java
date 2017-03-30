package com.roomiegh.roomie.models;

import java.io.Serializable;

/**
 * Created by Kwadwo Agyapon-Ntra on 27/07/2015.
 */
public class BrowseEntities implements Serializable{
    private int icon;
    private String Link;

    public BrowseEntities() {
    }

    public BrowseEntities(int icon, String link) {
        this.icon = icon;
        Link = link;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    @Override
    public boolean equals(Object o) {


        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BrowseEntities that = (BrowseEntities) o;

        if (icon != that.icon) return false;
        return !(Link != null ? !Link.equals(that.Link) : that.Link != null);

    }

    @Override
    public int hashCode() {
        int result = icon;
        result = 31 * result + (Link != null ? Link.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BrowseEntities{" +
                "icon=" + icon +
                ", Link='" + Link + '\'' +
                '}';
    }
}
