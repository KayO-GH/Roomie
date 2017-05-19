package com.roomiegh.roomie.models;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Kwadwo Agyapon-Ntra on 27/07/2015.
 */
public class User implements Serializable {
    //personal info
    private String fName,lName, gender, phone, email, programme, nok, nokPhone;
    private byte[] photo;


    public User() {
    }

    public User(String fName, String lName, String gender, String phone, String email, String programme, String nok, String nokPhone, byte[] photo) {
        this.fName = fName;
        this.lName = lName;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.programme = programme;
        this.nok = nok;
        this.nokPhone = nokPhone;
        this.photo = photo;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProgramme() {
        return programme;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    public String getNok() {
        return nok;
    }

    public void setNok(String nok) {
        this.nok = nok;
    }

    public String getNokPhone() {
        return nokPhone;
    }

    public void setNokPhone(String nokPhone) {
        this.nokPhone = nokPhone;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (fName != null ? !fName.equals(user.fName) : user.fName != null) return false;
        if (lName != null ? !lName.equals(user.lName) : user.lName != null) return false;
        if (gender != null ? !gender.equals(user.gender) : user.gender != null) return false;
        if (phone != null ? !phone.equals(user.phone) : user.phone != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (programme != null ? !programme.equals(user.programme) : user.programme != null)
            return false;
        if (nok != null ? !nok.equals(user.nok) : user.nok != null) return false;
        if (nokPhone != null ? !nokPhone.equals(user.nokPhone) : user.nokPhone != null)
            return false;
        return Arrays.equals(photo, user.photo);

    }

    @Override
    public int hashCode() {
        int result = fName != null ? fName.hashCode() : 0;
        result = 31 * result + (lName != null ? lName.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (programme != null ? programme.hashCode() : 0);
        result = 31 * result + (nok != null ? nok.hashCode() : 0);
        result = 31 * result + (nokPhone != null ? nokPhone.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", programme='" + programme + '\'' +
                ", nok='" + nok + '\'' +
                ", nokPhone='" + nokPhone + '\'' +
                ", photo=" + Arrays.toString(photo) +
                '}';
    }
}
