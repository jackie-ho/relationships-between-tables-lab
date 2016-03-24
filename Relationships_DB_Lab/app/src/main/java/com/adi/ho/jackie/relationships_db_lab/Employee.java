package com.adi.ho.jackie.relationships_db_lab;

public class Employee {
  private String firstName;
  private String lastName;
  private String address;
  private int yearOfBirth;
  private String ssn;

  public Employee(String name, String lastname,  int age, String address, String ssn) {
    this.firstName = name;
    this.lastName = lastname;
    this.yearOfBirth = age;
    this.address = address;
    this.ssn = ssn;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public int getYearOfBirth() {
    return yearOfBirth;
  }

  public void setYearOfBirth(int yearOfBirth) {
    this.yearOfBirth = yearOfBirth;
  }

  public String getSsn() {
    return ssn;
  }

  public void setSsn(String ssn) {
    this.ssn = ssn;
  }
}
