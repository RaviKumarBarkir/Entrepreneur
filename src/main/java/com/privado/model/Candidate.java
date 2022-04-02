package com.privado.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Candidate {
  @JsonProperty("Role")
  private Role Role;
  private String name;
  private int age;
  private String location;

  /*public String getRole() {
    return Role;
  }

  public void setRole(String role) {
    Role = role;
  }*/

  public com.privado.model.Role getRole() {
    return Role;
  }

  public void setRole(com.privado.model.Role role) {
    Role = role;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  @Override
  public String toString() {
    return "Candidate{" +
            "role=" + Role +
            ", name='" + name + '\'' +
            ", age=" + age +
            ", location='" + location + '\'' +
            '}';
  }
}
