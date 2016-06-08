package com.jinjiang.computer.tianyi.input;

import android.widget.Toast;

import com.jinjiang.computer.tianyi.entity.Student;
import com.jinjiang.computer.tianyi.utils.MyApplication;

import java.util.List;
import java.util.Map;

/**
 * Created by Ben on 2016/4/11 0011.
 */
public class UIInput extends AbstractInput {

    public UIInput(){}
    public UIInput(List<Student> ls)
    {
        for (int i=0;i<ls.size();i++){
            allData.put(ls.get(i).getStudentId()+"",ls.get(i));
        }
        dataInput();
    }
    public UIInput(Map<String,Student> map) {
        allData.putAll(map);
        dataInput();
    }
    public UIInput(Student student) {
        this.allData.put(student.getStudentId() + "", student);
        dataInput();
    }

    @Override
    protected void dataInput() {
        InputToSqlite.writeToDB(this);
    }

    public void deleteById(String StudentId){
        InputToSqlite.deleteStudent(StudentId);
        Toast.makeText(MyApplication.getMyContext(), "删除成功", Toast.LENGTH_SHORT).show();
    }
}
