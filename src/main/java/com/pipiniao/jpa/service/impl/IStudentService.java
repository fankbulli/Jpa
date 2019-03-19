package com.pipiniao.jpa.service.impl;

import com.pipiniao.jpa.bean.Student;
import com.pipiniao.jpa.dao.StudentDao;
import com.pipiniao.jpa.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IStudentService implements StudentService {
    @Autowired
    StudentDao studentDao;
    @Override
    public Student findByName(String name) {
        return studentDao.findStudentByName(name);
    }
}
