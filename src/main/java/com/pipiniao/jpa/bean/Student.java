package com.pipiniao.jpa.bean;


import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;

@Entity(name = "student")
public class Student {

       private static final long serialVersionUID = 159714803901985366L;
       @Id
       @GeneratedValue(strategy = GenerationType.AUTO)
       private int id;
       private String name;
       private String sex;
       private String eye;

    public String getEye() {
        return eye;
    }

    public void setEye(String eye) {
        this.eye = eye;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", eye='" + eye + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

}
