package com.jinjiang.computer.tianyi.output;

import com.jinjiang.computer.tianyi.entity.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ben on 2016/4/12 0012.
 */
public abstract class AbstractOutput {
    protected List<Student> alldata = new ArrayList<>();
    protected abstract void output(String filename,List<Student> ls);
}
