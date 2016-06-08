package com.jinjiang.computer.tianyi.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.test.suitebuilder.annotation.Suppress;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jinjiang.computer.tianyi.R;
import com.jinjiang.computer.tianyi.entity.Student;
import com.jinjiang.computer.tianyi.input.UIInput;
import com.jinjiang.computer.tianyi.utils.MyApplication;
@SuppressLint("ValidFragment")
public class AddFragment extends Fragment {

    private EditText id;
    private EditText name;
    private EditText sex;
    private EditText cc;
    private Button submit;
    private Student student = null;
    private boolean tag = false;

    public AddFragment()
    {

    }

    public AddFragment(Student student)
    {
        this.student = student;
        this.tag = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View  view=inflater.inflate(R.layout.fragment_add, container, false);
        id = (EditText) view.findViewById(R.id.getid);
        name = (EditText) view.findViewById(R.id.getname);
        sex = (EditText) view.findViewById(R.id.getsex);
        cc = (EditText) view.findViewById(R.id.getcc);
        submit = (Button) view.findViewById(R.id.submitbutton);

        if(student!=null) {
            id.setText(student.getStudentId()+"");
            name.setText(student.getStudentName());
            if(student.getSex()==1)
                sex.setText("男");
            else if(student.getSex()==0)
                sex.setText("女");
            else
                sex.setText("");
            cc.setText(student.getClassName());
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                student = new Student();
                if(!id.getText().toString().equals("")) {
                    student.setStudentId(Integer.parseInt(id.getText().toString()));
                    student.setStudentName(name.getText().toString());
                    if (sex.getText().toString().contains("男"))
                        student.setSex(1);
                    else if (sex.getText().toString().contains("女"))
                        student.setSex(0);
                    else
                        student.setSex(-1);
                    student.setClassName(cc.getText().toString());

                    if(!tag) {
                        student.setLate(0);
                        student.setIll(0);
                        student.setTotal(0);
                        student.setAbsent(0);
                    }

                    UIInput uiInput = new UIInput(student);
                    Toast.makeText(MyApplication.getMyContext(), "操作成功", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(MyApplication.getMyContext(), "操作失败！请检查格式！", Toast.LENGTH_SHORT).show();
                }
        });


        return view;
    }
}
