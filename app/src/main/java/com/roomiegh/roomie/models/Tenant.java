package com.roomiegh.roomie.models;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Kwadwo Agyapon-Ntra on 27/07/2015.
 */
public class Tenant implements Serializable {
    //personal info
    private String fName,lName,nationality, gender, phone, phone2,email,dob;
    private byte[] photo;
    private int refNo;

    public Tenant() {
    }

    public Tenant(String fName, String lName, String nationality, String gender, String phone, String phone2, String email, String dob, byte[] photo, int refNo) {
        this.fName = fName;
        this.lName = lName;
        this.nationality = nationality;
        this.gender = gender;
        this.phone = phone;
        this.phone2 = phone2;
        this.email = email;
        this.dob = dob;
        this.photo = photo;
        this.refNo = refNo;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public int getRefNo() {
        return refNo;
    }

    public void setRefNo(int refNo) {
        this.refNo = refNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tenant tenant = (Tenant) o;

        if (refNo != tenant.refNo) return false;
        if (fName != null ? !fName.equals(tenant.fName) : tenant.fName != null) return false;
        if (lName != null ? !lName.equals(tenant.lName) : tenant.lName != null) return false;
        if (nationality != null ? !nationality.equals(tenant.nationality) : tenant.nationality != null)
            return false;
        if (gender != null ? !gender.equals(tenant.gender) : tenant.gender != null) return false;
        if (phone != null ? !phone.equals(tenant.phone) : tenant.phone != null) return false;
        if (phone2 != null ? !phone2.equals(tenant.phone2) : tenant.phone2 != null) return false;
        if (email != null ? !email.equals(tenant.email) : tenant.email != null) return false;
        if (dob != null ? !dob.equals(tenant.dob) : tenant.dob != null) return false;
        return Arrays.equals(photo, tenant.photo);

    }

    @Override
    public int hashCode() {
        int result = fName != null ? fName.hashCode() : 0;
        result = 31 * result + (lName != null ? lName.hashCode() : 0);
        result = 31 * result + (nationality != null ? nationality.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (phone2 != null ? phone2.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (dob != null ? dob.hashCode() : 0);
        result = 31 * result + (photo != null ? Arrays.hashCode(photo) : 0);
        result = 31 * result + refNo;
        return result;
    }

    @Override
    public String toString() {
        return "Tenant{" +
                "fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", nationality='" + nationality + '\'' +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                ", phone2='" + phone2 + '\'' +
                ", email='" + email + '\'' +
                ", dob='" + dob + '\'' +
                ", photo=" + Arrays.toString(photo) +
                ", refNo=" + refNo +
                '}';
    }
}
