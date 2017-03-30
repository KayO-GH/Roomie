package com.roomiegh.roomie.models;

import java.io.Serializable;

/**
 * Created by Kwadwo Agyapon-Ntra on 01/08/2015.
 */
public class Guardian implements Serializable {
    private String guardFName,guardLName,guardPhone,guardPhone2,guardOccupation,guardAddress;
    private int refNo;

    public Guardian() {
    }

    public Guardian(String guardFName, String guardLName, String guardPhone, String guardPhone2, String guardOccupation, String guardAddress, int refNo) {
        this.guardFName = guardFName;
        this.guardLName = guardLName;
        this.guardPhone = guardPhone;
        this.guardPhone2 = guardPhone2;
        this.guardOccupation = guardOccupation;
        this.guardAddress = guardAddress;
        this.refNo = refNo;
    }

    public String getGuardFName() {
        return guardFName;
    }

    public void setGuardFName(String guardFName) {
        this.guardFName = guardFName;
    }

    public String getGuardLName() {
        return guardLName;
    }

    public void setGuardLName(String guardLName) {
        this.guardLName = guardLName;
    }

    public String getGuardPhone() {
        return guardPhone;
    }

    public void setGuardPhone(String guardPhone) {
        this.guardPhone = guardPhone;
    }

    public String getGuardPhone2() {
        return guardPhone2;
    }

    public void setGuardPhone2(String guardPhone2) {
        this.guardPhone2 = guardPhone2;
    }

    public String getGuardOccupation() {
        return guardOccupation;
    }

    public void setGuardOccupation(String guardOccupation) {
        this.guardOccupation = guardOccupation;
    }

    public String getGuardAddress() {
        return guardAddress;
    }

    public void setGuardAddress(String guardAddress) {
        this.guardAddress = guardAddress;
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

        Guardian guardian = (Guardian) o;

        if (refNo != guardian.refNo) return false;
        if (guardFName != null ? !guardFName.equals(guardian.guardFName) : guardian.guardFName != null)
            return false;
        if (guardLName != null ? !guardLName.equals(guardian.guardLName) : guardian.guardLName != null)
            return false;
        if (guardPhone != null ? !guardPhone.equals(guardian.guardPhone) : guardian.guardPhone != null)
            return false;
        if (guardPhone2 != null ? !guardPhone2.equals(guardian.guardPhone2) : guardian.guardPhone2 != null)
            return false;
        if (guardOccupation != null ? !guardOccupation.equals(guardian.guardOccupation) : guardian.guardOccupation != null)
            return false;
        return !(guardAddress != null ? !guardAddress.equals(guardian.guardAddress) : guardian.guardAddress != null);

    }

    @Override
    public int hashCode() {
        int result = guardFName != null ? guardFName.hashCode() : 0;
        result = 31 * result + (guardLName != null ? guardLName.hashCode() : 0);
        result = 31 * result + (guardPhone != null ? guardPhone.hashCode() : 0);
        result = 31 * result + (guardPhone2 != null ? guardPhone2.hashCode() : 0);
        result = 31 * result + (guardOccupation != null ? guardOccupation.hashCode() : 0);
        result = 31 * result + (guardAddress != null ? guardAddress.hashCode() : 0);
        result = 31 * result + refNo;
        return result;
    }

    @Override
    public String toString() {
        return "Guardian{" +
                "guardFName='" + guardFName + '\'' +
                ", guardLName='" + guardLName + '\'' +
                ", guardPhone='" + guardPhone + '\'' +
                ", guardPhone2='" + guardPhone2 + '\'' +
                ", guardOccupation='" + guardOccupation + '\'' +
                ", guardAddress='" + guardAddress + '\'' +
                ", refNo=" + refNo +
                '}';
    }
}
