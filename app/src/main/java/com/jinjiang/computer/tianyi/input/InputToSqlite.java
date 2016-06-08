package com.jinjiang.computer.tianyi.input;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jinjiang.computer.tianyi.entity.Student;
import com.jinjiang.computer.tianyi.utils.GetDBHelper;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Ben on 2016/4/9 0009.
 */
public class InputToSqlite {

    public static void writeToDB(AbstractInput myInput) {
        SQLiteDatabase sqLiteDatabase = GetDBHelper.GetTheDBHelper().getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("StudentInfo", null, null, null, null, null, null, null);//所有人的id
        if (cursor.getCount() != 0) {
            Set<String> studentIds = new HashSet<>();
            Map<String, Student> existStudent = new HashMap<>();
            while (cursor.moveToNext()) {
                Student student = new Student();
                int id = cursor.getInt(cursor.getColumnIndex("studentId"));
                student.setStudentId(id);
                student.setStudentName(cursor.getString(cursor.getColumnIndex("studentName")));
                student.setSex(cursor.getInt(cursor.getColumnIndex("sex")));
                student.setClassName(cursor.getString(cursor.getColumnIndex("className")));
                student.setAbsent(cursor.getInt(cursor.getColumnIndex("absent")));
                student.setLate(cursor.getInt(cursor.getColumnIndex("late")));
                student.setIll(cursor.getInt(cursor.getColumnIndex("ill")));
                student.setTotal(cursor.getInt(cursor.getColumnIndex("total")));
                String t_studentId = "" + id;
                studentIds.add(t_studentId);
                existStudent.put(t_studentId, student);
            }

            cursor.close();

            Field[] fields = Student.class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
            }
            //将所有数据添加或者更新到数据库
            Set<String> allStudents = myInput.getAllStudent().keySet();
            for (String studentId : allStudents) {
                Student toWrite = myInput.getStudent(studentId);
                if (studentIds.contains(studentId)) {
                    //更新
                    Student tmp = existStudent.get(studentId);//已经存在的id
                    for (Field field : fields) {
                        try {
                            field.set(tmp, field.get(toWrite));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
        //            contentValues.put("studentId", tmp.getStudentId());
        //            contentValues.put("studentName", tmp.getStudentName());
        //            contentValues.put("sex", tmp.getSex());
       //             contentValues.put("className", tmp.getClassName());
       //             contentValues.put("absent", tmp.getAbsent());
      //              contentValues.put("late", tmp.getLate());
    //                contentValues.put("total", tmp.getTotal());
     //               sqLiteDatabase.execSQL("delete from StudentInfo where studentId=?", new String[]{tmp.getStudentId() + ""});
 //                   sqLiteDatabase.insert("StudentInfo", null, contentValues);//更新已经存在的id的状态

            //        sqLiteDatabase.execSQL("insert into StudentInfo(studentId,studentName,sex,className,absent,late,total) values(?,?,?,?,?,?)",
            //                           new String[]{tmp.getStudentId()+"",tmp.getStudentName(),tmp.getSex()+"",tmp.getClassName(),tmp.getAbsent()+"",tmp.getLate()+"",tmp.getTotal()+""});


                    sqLiteDatabase.execSQL("update StudentInfo set studentName =?,sex=?,className=?,absent=?,late=?,total=?,ill=? where studentId=?",
                            new String[]{tmp.getStudentName()+"",tmp.getSex()+"",tmp.getClassName(),tmp.getAbsent()+"",tmp.getLate()+"",tmp.getTotal()+"",tmp.getIll()+"",tmp.getStudentId()+""});

     //              contentValues.clear();
       //             Toast.makeText(MyApplication.getMyContext(),"更新成功",Toast.LENGTH_SHORT).show();
                } else {
                    //添加
                 //   contentValues.put("studentId",toWrite.getStudentId());
                 //   contentValues.put("studentName", toWrite.getStudentName());
                 //   contentValues.put("sex", toWrite.getSex());
                 //   contentValues.put("className", toWrite.getClassName());
                 //   contentValues.put("absent", toWrite.getAbsent());
                 //   contentValues.put("late", toWrite.getLate());
                  //  contentValues.put("total", toWrite.getTotal());
                  //  sqLiteDatabase.insert("studentInfo", null, contentValues);

                    sqLiteDatabase.execSQL("insert into StudentInfo(studentId,studentName,sex,className,absent,late,total,ill) values(?,?,?,?,?,?,?,?)",
                                                       new String[]{toWrite.getStudentId()+"",toWrite.getStudentName(),toWrite.getSex()+"",toWrite.getClassName(),toWrite.getAbsent()+"",toWrite.getLate()+"",toWrite.getTotal()+"",toWrite.getIll()+""});
                }

            }
        } else {
            for (Object write : myInput.allData.values()) {
                Student toWrite = (Student) write;
                sqLiteDatabase.execSQL("insert into StudentInfo(studentId,studentName,sex,className,absent,late,total,ill) values(?,?,?,?,?,?,?,?)",
                        new String[]{toWrite.getStudentId() + "", toWrite.getStudentName(), toWrite.getSex() + "", toWrite.getClassName(), toWrite.getAbsent() + "", toWrite.getLate() + "", toWrite.getTotal() + "",toWrite.getIll()+""});

            }

        }

        sqLiteDatabase.close();

    }

    public static void deleteStudent(String StudentId)
    {
        SQLiteDatabase sqLiteDatabase = GetDBHelper.GetTheDBHelper().getWritableDatabase();
        sqLiteDatabase.execSQL("delete from StudentInfo where studentId = ?",
                new String[]{StudentId});
        sqLiteDatabase.close();
    }

}
