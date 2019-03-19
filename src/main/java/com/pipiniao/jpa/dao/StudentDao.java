package com.pipiniao.jpa.dao;

import com.pipiniao.jpa.bean.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentDao extends CrudRepository<Student,Long> {
    Student findStudentByName(String name);
}
