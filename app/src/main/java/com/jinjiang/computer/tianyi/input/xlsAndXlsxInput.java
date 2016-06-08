package com.jinjiang.computer.tianyi.input;

import android.widget.Toast;

import com.jinjiang.computer.tianyi.entity.Student;
import com.jinjiang.computer.tianyi.utils.MyApplication;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Prometido on 2016/4/10 0010.
 */
public class xlsAndXlsxInput extends AbstractInput {

    File dataFile;
    Map<String,Integer> headerIndex;
    public xlsAndXlsxInput(File inputFile) {
        dataFile = inputFile;
        headerIndex = new HashMap<>();
        dataInput();
        InputToSqlite.writeToDB(this);
        Toast.makeText(MyApplication.getMyContext(),"导入成功",Toast.LENGTH_SHORT).show();
    }

    private boolean isXls(FileInputStream fis){
        try{
            new HSSFWorkbook(fis);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    protected void dataInput() {
        boolean hasSno = true;
        Workbook wb = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(dataFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if(isXls(new FileInputStream(dataFile))){
                wb = new HSSFWorkbook(fis);
            }else{
     //           wb = new XSSFWorkbook(new FileInputStream(dataFile));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Sheet sheet;
        try {
            sheet = wb.getSheetAt(0);
        }catch (Exception e)
        {
            try {
                sheet = wb.getSheetAt(1);
            }
            catch (Exception ee) {
                try {
                    sheet = wb.getSheet("Sheet0");
                }catch (Exception eee)
                {
                    sheet = wb.getSheet("Sheet1");
                }
            }
        }

        Iterator rowIterator = sheet.rowIterator();

        //获取列标题，及对应列标题的下标
        if(rowIterator.hasNext()){
            Row title = (Row) rowIterator.next();
            for(int i = 0;i < title.getPhysicalNumberOfCells();++i){
                title.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
                headerIndex.put(
                        title.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).getStringCellValue(),i);
            }
        }
        //遍历所有行，并且将数据写入到实体类
        while(rowIterator.hasNext()){
            Row everyRow = (Row) rowIterator.next();
            for(Cell cell : everyRow){
                cell.setCellType(Cell.CELL_TYPE_STRING);
            }
            String studentId = getSpecificCellValue("学号",everyRow);
            Student student = new Student();
            //如果学号列没有数据，则这条数据没有意义
            try {
                student.setStudentId(Integer.valueOf(studentId));
            }catch (Exception e){
                hasSno = false;
                continue;
            }
            student.setClassName(getSpecificCellValue("班级",everyRow));

            student.setStudentName(getSpecificCellValue("姓名",everyRow));

            student.setLate(0);
            String sex = getSpecificCellValue("性别",everyRow);
            if(sex.contains("男")) {
                student.setSex(1);
            }else if(sex.contains("女")) {
                student.setSex(0);
            } else {
                student.setSex(-1);
            }
            student.setIll(0);
            student.setTotal(0);
            student.setAbsent(0);
            allData.put(studentId,student);
            if(!hasSno)
            Toast.makeText(MyApplication.getMyContext(),"导入的学生对象含有无学号的个体，可能导入失败，请检查或修改Excel再试！",Toast.LENGTH_LONG).show();
        }
        try {
            wb.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getSpecificCellValue(String columnName,Row row){
        try {
            return row.getCell(
                    headerIndex.get(columnName), Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).
                    getStringCellValue();
        }catch (Exception e){
            return "";
        }
    }

}
