package com.jinjiang.computer.tianyi.input;

import com.jinjiang.computer.tianyi.entity.Student;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Prometido on 2016/4/9 0009.
 */
public abstract class AbstractInput {
    protected Map<String, Student> allData = new HashMap<>();//id 和对应的studnt对象
    protected abstract void dataInput();
    //can be null
    protected Student getStudent(String studentId){
        return allData.get(studentId);
    }
    protected Map<String,Student> getAllStudent(){
        return allData;
    }
}
