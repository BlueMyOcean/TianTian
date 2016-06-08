package com.jinjiang.computer.tianyi.output;

import android.annotation.TargetApi;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import com.jinjiang.computer.tianyi.entity.Student;
import com.jinjiang.computer.tianyi.utils.GetDBHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 将现有数据库中的信息进行展示供前端调用
 * Created by Prometido on 2016/4/10 0010.
 * Edited by Ben
 */
public class statisticShow {
    private Map<String, Student> allStudent = new HashMap<>();
    private int tem ;
    public statisticShow() {
        SQLiteDatabase sqLiteDatabase = GetDBHelper.GetTheDBHelper().getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("StudentInfo", null, null, null, null, null, null, null);
        tem = cursor.getCount();
        while (cursor.moveToNext()) {
            Student student = new Student();
            int id = cursor.getInt(cursor.getColumnIndex("studentId"));
            student.setStudentId(id);
            student.setStudentName(cursor.getString(cursor.getColumnIndex("studentName")));
            student.setSex(cursor.getInt(cursor.getColumnIndex("sex")));
            student.setClassName(cursor.getString(cursor.getColumnIndex("className")));
            student.setAbsent(cursor.getInt(cursor.getColumnIndex("absent")));
            student.setLate(cursor.getInt(cursor.getColumnIndex("late")));
            student.setTotal(cursor.getInt(cursor.getColumnIndex("total")));
            student.setIll(cursor.getInt(cursor.getColumnIndex("ill")));
            String t_studentId = "" + id;
            allStudent.put(t_studentId,student);
        }
           sqLiteDatabase.close();

        }


    public boolean isClassNameExist(String classname)//判断班级是否存在
    {
        for (Object everyStudent:allStudent.values()) {
            if (((Student)everyStudent).getClassName().equals(classname))
                return true;
        }
        return false;
    }
    public int getStudentAbsent(String studentId) {//根据学号获取学生缺席次数
        return allStudent.get(studentId).getAbsent();
    }

    public Student getSpecificStudentByID(String id)//根据ID获取学生对象
    {
        return allStudent.get(id);
    }

    public List<Student> orderBy(String className, final Field attribute, final boolean AscOrDesc) {//根据对应的属性排序
//        Toast.makeText(MyApplication.getMyContext(),"yi:"+tem,Toast.LENGTH_SHORT).show();
        List<Student> aimStudent = new ArrayList<>();
        if(isClassNameExist(className)) {
            for (Object everyStudent : allStudent.values()) {//取得相同班级的人
                Student st = (Student) everyStudent;
                if (st.getClassName().equals(className))
                    aimStudent.add(st);
            }
        } else {
            for (Object everyStudent : allStudent.values()) {//未找到 插入相似学学号的人
                Student st = (Student) everyStudent;
                if ((st.getStudentId()+"").contains(className))
                    aimStudent.add(st);
            }
        }
        Comparator<Student> comparator = new Comparator<Student>() {
//            @TargetApi(Build.VERSION_CODES.KITKAT)
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public int compare(Student o1, Student o2) {
                try {
                    if(AscOrDesc){
                     //   return Integer.compare(attribute.getInt(o1), attribute.getInt(o2));
                        return Integer.compare(o1.getAbsent(),o2.getAbsent());
                    }else{
//                        return Integer.compare(attribute.getInt(o2), attribute.getInt(o1));
                        return Integer.compare(o2.getAbsent(),o1.getAbsent());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        };
        Collections.sort(aimStudent,comparator);
        return aimStudent;
    }

}
