package com.roomiegh.roomie.models;

import java.io.Serializable;

/**
 * Created by Kwadwo Agyapon-Ntra on 04/08/2015.
 */
public class SignIn implements Serializable {
    private String email,password,signInTme, signOutTime;
    //signInTime and signOutTie will be cnverted to string before storing in the database

    public SignIn() {
    }

    public SignIn(String email, String password, String signInTme, String signOutTime) {
        this.email = email;
        this.password = password;
        this.signInTme = signInTme;
        this.signOutTime = signOutTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSignInTme() {
        return signInTme;
    }

    public void setSignInTme(String signInTme) {
        this.signInTme = signInTme;
    }

    public String getSignOutTime() {
        return signOutTime;
    }

    public void setSignOutTime(String signOutTime) {
        this.signOutTime = signOutTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SignIn signIn = (SignIn) o;

        if (email != null ? !email.equals(signIn.email) : signIn.email != null) return false;
        if (password != null ? !password.equals(signIn.password) : signIn.password != null)
            return false;
        if (signInTme != null ? !signInTme.equals(signIn.signInTme) : signIn.signInTme != null)
            return false;
        return !(signOutTime != null ? !signOutTime.equals(signIn.signOutTime) : signIn.signOutTime != null);

    }

    @Override
    public int hashCode() {
        int result = email != null ? email.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (signInTme != null ? signInTme.hashCode() : 0);
        result = 31 * result + (signOutTime != null ? signOutTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SignIn{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", signInTme='" + signInTme + '\'' +
                ", signOutTime='" + signOutTime + '\'' +
                '}';
    }
}
