package com.jinjiang.computer.tianyi.ui;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.jinjiang.computer.tianyi.R;
import com.jinjiang.computer.tianyi.entity.Student;
import com.jinjiang.computer.tianyi.input.UIInput;
import com.jinjiang.computer.tianyi.output.statisticShow;
import com.jinjiang.computer.tianyi.utils.MyApplication;
import com.jinjiang.computer.tianyi.utils.lsChange;
import com.jinjiang.computer.tianyi.voice.Speaker;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SignFragment extends Fragment{

    public interface getclassname{
        public void returnstudent(Student student,List<Student> ls);
        public void returnclassname(String classname);
        public void returnprels(List<Student> pre);
    }



    private getclassname listener;
    private EditText sno;//学号的EdiText
    private EditText name;//姓名的EdiText
    private int presentIndex;//下标  指向当前Student对象
    private Boolean tag=false;//焦点是否开启的条件  初始化为不开启
    private List<Student> ls;//数据库获得的相应的list
    private List<Student> prels;
    private statisticShow ss;//展示层
    private String classname;//想要点的班名
    private Field field;//根据Student什么属性排序
    private Button last;//下一个按钮
    private Button next;
    private TextView textView;
    private UIInput uiInput;
    private final static int ItemIndex = Menu.FIRST+1;
    private List<Student> temp;
    private Button getclass;
    private EditText textclass;
    private TextView absent;
    private Button here;
    private Button sick;
    private Button late;
    private Button absentt;
    private lsChange lschange;
    private List<Student> pre;

    public SignFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sign, container, false);
        presentIndex=0;
        sno = (EditText) view.findViewById(R.id.snoinput);//获取对象
        name = (EditText) view.findViewById(R.id.studentname);
        last = ((Button)view.findViewById(R.id.lastone));
        next = (Button)view.findViewById(R.id.nextone);
        textView = (TextView)view.findViewById(R.id.absentimes);
        textclass = (EditText)view.findViewById(R.id.textclass);
        getclass = (Button)view.findViewById(R.id.getclassorsno);
        here =(Button)view.findViewById(R.id.here);
        sick= (Button)view.findViewById(R.id.leave);
        late =(Button)view.findViewById(R.id.late);
        absentt=(Button)view.findViewById(R.id.absent);

        textclass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    textclass.setHint("");

                }
                else
                    textclass.setHint("请根据班级名或学号查找");
            }
        });


        getclass.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getclass.requestFocus();
                presentIndex=0;
                    classname = textclass.getText().toString();
                 ss = new statisticShow();

                try {
                    field = Student.class.getDeclaredField("absent");
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }

                ls = ss.orderBy(classname, field, false);//降序 并获得list
                pre = new ArrayList<Student>(ls);
                temp = new ArrayList<>(ls);//模板  取消本次点到恢复使用
                prels = new ArrayList<>(ls);//目标ls  用于点到完成更新

                if(ls.size()!=0) {
                    Toast.makeText(MyApplication.getMyContext(),"人数:"+ls.size()+"",Toast.LENGTH_SHORT).show();
                    Student student = ls.get(presentIndex);
                    sno.setText(student.getStudentId()+"");//先把第一个给传递过来
                    Speaker.speak(student.getStudentName());
                    name.setText(student.getStudentName());
                    textView.setText("缺席："+student.getAbsent()+"次 迟到："+student.getLate()+"次 请假："+student.getIll()+"次");

                    if(classname!=null) {
                        listener.returnclassname(classname);
                        listener.returnstudent(ls.get(presentIndex),ls);
                    }

//                    sno.setText(ls.get(presentIndex).getStudentId()+"");//先把第一个给传递过来
//                    Speaker.speak(ls.get(presentIndex).getStudentName());
//                    name.setText(ls.get(presentIndex).getStudentName());
//                    textView.setText("缺席："+ls.get(presentIndex).getAbsent()+"次");
                }

                else {
                    Toast.makeText(MyApplication.getMyContext(),"未找到！",Toast.LENGTH_SHORT).show();
                }

            }
        });


            /**
             * 焦点的强制获取
             * 是为了处理EditText获取焦点后
             * 出现一直监听导致按钮失效的情况
             * 也就是说是否焦点成为开启TextWatcher的一个条件
             * 用一个tag来表示是否获得焦点
             */
            sno.clearFocus();
            last.setFocusable(true);//使焦点在lastone这个按钮上
            last.setFocusableInTouchMode(true);
            last.requestFocus();//获取焦点

            sno.setOnFocusChangeListener(new View.OnFocusChangeListener() {//设置焦点的监听事件
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    tag = hasFocus;
                }
            });

            sno.addTextChangedListener(new TextWatcher() {//EditText的文本监听
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {


                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (ls != null && (s.length() == 9)&&tag) {
                        int i;
                        for (i = 0; i < ls.size(); i++) {
                            if ((ls.get(i).getStudentId()+"").equals(s.toString())) {
                                name.setText(ls.get(i).getStudentName());
                                textView.setText("缺席："+ls.get(presentIndex).getAbsent()+"次 迟到："+ls.get(presentIndex).getLate()+"次 请假："+ls.get(presentIndex).getIll()+"次");
                                presentIndex = i;
                                listener.returnstudent(ls.get(i),ls);
                                break;
                            }
                        }
                        if (i == ls.size()) {
                            Toast.makeText(MyApplication.getMyContext(), "未找到！", Toast.LENGTH_SHORT).show();
                        }
                        tag = false;
                        sno.clearFocus();//搜索完成清除焦点
                        last.requestFocus();
                    }

                }

            });

        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ls!=null) {
                    if (ls.size() != 0) {
                        if (presentIndex > 0) {
                            sno.clearFocus();
                            presentIndex--;
                            sno.setText(ls.get(presentIndex).getStudentId() + "");
                            name.setText(ls.get(presentIndex).getStudentName());
                            textView.setText("缺席："+ls.get(presentIndex).getAbsent()+"次 迟到："+ls.get(presentIndex).getLate()+"次 请假："+ls.get(presentIndex).getIll()+"次");
                        }
                    }
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ls!=null) {
                    if (ls.size() != 0) {
                        if (presentIndex + 1 < ls.size()) {
                            sno.clearFocus();
                            presentIndex++;
                            sno.setText(ls.get(presentIndex).getStudentId() + "");
                            name.setText(ls.get(presentIndex).getStudentName());
                            textView.setText("缺席："+ls.get(presentIndex).getAbsent()+"次 迟到："+ls.get(presentIndex).getLate()+"次 请假："+ls.get(presentIndex).getIll()+"次");
                        }
                    }
                }

            }
        });

        here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ls!=null) {
                    if (presentIndex + 1 < ls.size())
                        Speaker.speak(prels.get(presentIndex + 1).getStudentName());//永远先读下一个
                    Student presentStudent = new Student(prels.get(presentIndex));
                    presentStudent.setTotal(presentStudent.getTotal() + 1);
                    ls.set(presentIndex, presentStudent);
                    uiInput = new UIInput(ls.get(presentIndex));
                    nextone();
                }
            }
        });

        sick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ls != null) {
                    if (presentIndex + 1 < ls.size())
                        Speaker.speak(prels.get(presentIndex + 1).getStudentName());//永远先读下一个
                    Student presentStudent = new Student(prels.get(presentIndex));
                    presentStudent.setIll(presentStudent.getIll() + 1);
                    presentStudent.setTotal(presentStudent.getTotal() + 1);
                    ls.set(presentIndex, presentStudent);
                    uiInput = new UIInput(ls.get(presentIndex));
                    nextone();
                }
            }
        });

        late.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ls != null) {
                    if (presentIndex + 1 < ls.size())
                        Speaker.speak(prels.get(presentIndex + 1).getStudentName());//永远先读下一个
                    Student presentStudent = new Student(prels.get(presentIndex));
                    presentStudent.setLate(presentStudent.getLate() + 1);
                    presentStudent.setTotal(presentStudent.getTotal() + 1);
                    ls.set(presentIndex, presentStudent);
                    uiInput = new UIInput(ls.get(presentIndex));
                    nextone();
                }
            }
        });

        absentt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ls != null) {
                    if (presentIndex + 1 < ls.size())
                        Speaker.speak(prels.get(presentIndex + 1).getStudentName());//永远先读下一个
                    Student presentStudent = new Student(prels.get(presentIndex));
                    presentStudent.setAbsent(presentStudent.getAbsent() + 1);
                    presentStudent.setTotal(presentStudent.getTotal() + 1);
                    ls.set(presentIndex, presentStudent);
                    uiInput = new UIInput(ls.get(presentIndex));
                    nextone();
                }
            }
        });


        return view;
    }

    public void nextone() {

        if(ls != null) {

            if (ls.size() != 0) {
                if(presentIndex >= ls.size()-1)
                {
                    new AlertDialog.Builder(this.getActivity())
                            .setMessage("已点到最后一名同学！")
                            .setPositiveButton("确定",null)
                            .show();
                }
                else if (presentIndex + 1 < ls.size()) {
                    sno.clearFocus();
                    presentIndex++;
                    Student student =ls.get(presentIndex);
                    if(listener!=null)
                        listener.returnstudent(student,ls);
                    Speaker.speak(student.getStudentName());
                    sno.setText(student.getStudentId() + "");
                    name.setText(student.getStudentName());
                    textView.setText("缺席："+ls.get(presentIndex).getAbsent()+"次 迟到："+ls.get(presentIndex).getLate()+"次 请假："+ls.get(presentIndex).getIll()+"次");
                }

                Toast.makeText(this.getActivity(),"还剩"+(ls.size()-presentIndex-1)+"个学生",Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.listener = (getclassname)activity;
    }
}
