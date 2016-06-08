package com.jinjiang.computer.tianyi.output;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.jinjiang.computer.tianyi.entity.Student;
import com.jinjiang.computer.tianyi.utils.MyApplication;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by Ben on 2016/4/19 0019.
 */
public class xlsOutput extends AbstractOutput {

    public xlsOutput(String filename,List<Student> ls)
    {
        output(filename,ls);
    }
    @Override
    protected void output(String filename,List<Student> ls) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell  = row.createCell((short)0);
        cell.setCellValue("学号");
        cell = row.createCell((short)1);
        cell.setCellValue("姓名");
        cell = row.createCell((short)2);
        cell.setCellValue("请假");
        cell = row.createCell((short)3);
        cell.setCellValue("迟到");
        cell = row.createCell((short)4);
        cell.setCellValue("缺勤");
        cell = row.createCell((short)5);
        cell.setCellValue("点到次数");

        for(int i=0;i<ls.size();i++)
        {
            Student student = ls.get(i);
            row = sheet.createRow(i+1);
            cell = row.createCell((short) 0);
            cell.setCellValue(student.getStudentId()+"");
            cell = row.createCell((short)1);
            cell.setCellValue(student.getStudentName());
            cell = row.createCell((short)2);
            cell.setCellValue(student.getIll()+"");
            cell = row.createCell((short)3);
            cell.setCellValue(student.getLate()+"");
            cell = row.createCell((short)4);
            cell.setCellValue(student.getAbsent()+"");
            cell = row.createCell((short)5);
            cell.setCellValue(student.getTotal()+"");
        }

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            wb.write(os);
        } catch (IOException e) {
            Log.e("IO", "output: Excel写入失败");
            e.printStackTrace();
        }

        byte[] content = os.toByteArray();

        File file = new File(Environment.getExternalStorageDirectory()+File.separator+filename+".xls");
        OutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(content);
            os.close();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("File", "output: 文件未找到！" );
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("IO", "output:写入或关闭流失败！ " );
            e.printStackTrace();
        }
        Toast.makeText(MyApplication.getMyContext(),"导出完成:"+Environment.getExternalStorageDirectory()+File.separator+filename+".xls",Toast.LENGTH_LONG).show();
    }
}
