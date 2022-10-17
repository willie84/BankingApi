package com.example.BankingAPI.utils;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class CustomerCreationDetails {
    @NotBlank(message = "customer name is mandatory")
    private String name;

    @NotBlank(message = "customer idNumber is mandatory")
    private String idNumber;

    @NotBlank(message = "customer address is mandatory")
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerCreationDetails)) return false;
        CustomerCreationDetails that = (CustomerCreationDetails) o;
        return Objects.equals(getName(), that.getName()) && Objects.equals(getIdNumber(), that.getIdNumber()) && Objects.equals(getAddress(), that.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getIdNumber(), getAddress());
    }

    @Override
    public String toString() {
        return "CustomerCreationDetails{" + "name='" + name + '\'' + ", idNumber='" + idNumber + '\'' + ", address='" + address + '\'' + '}';
    }
}
