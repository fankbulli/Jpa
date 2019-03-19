package com.pipiniao.jpa.service;

import com.pipiniao.jpa.bean.Student;

public interface StudentService {
    Student findByName(String name);
}
