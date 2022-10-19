package com.example.coen390_assignmen2;

import java.text.DateFormat;

public class Student {
    private long id;
    private String Surname;
    private String Name;
    private double GPA;
    private String CreationDate;

    public Student(long id,String Surname,String Name,double GPA,String CreationDate)
    {
        this.id=id;
        this.Surname=Surname;
        this.Name=Name;
        this.GPA=GPA;
        this.CreationDate=CreationDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getGPA() {
        return GPA;
    }

    public void setGPA(double GPA) {
        this.GPA = GPA;
    }

    public String getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(String creationDate) {
        CreationDate = creationDate;
    }
}
