package com.jinjiang.computer.tianyi.ui;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.DrawableRes;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;


import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.jinjiang.computer.tianyi.R;
import com.jinjiang.computer.tianyi.entity.Student;
import com.jinjiang.computer.tianyi.input.UIInput;
import com.jinjiang.computer.tianyi.input.xlsAndXlsxInput;
import com.jinjiang.computer.tianyi.output.AbstractOutput;
import com.jinjiang.computer.tianyi.utils.CallbackBundle;
import com.jinjiang.computer.tianyi.utils.FragmentAdapter;
import com.jinjiang.computer.tianyi.output.xlsOutput;
import com.jinjiang.computer.tianyi.utils.OpenFileDialog;
import com.jinjiang.computer.tianyi.utils.lsChange;
import com.jinjiang.computer.tianyi.voice.Speaker;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,SignFragment.getclassname{

    private lsChange lc;
    private Student prestudent=null;
    private SignFragment.getclassname st;
    private String classname="";
    private String filepath;
    private int openfileDialogId = 0;
//    viewpager的用法
//    private ViewPager viewPager;
//    private List<Fragment> fragments;
    private Fragment helpfragment;
    private Fragment addfragment;
    private Fragment signfragment;
    private Fragment aboutfragment;
    private Fragment statisticfragment;
    private Fragment mContent;
    private FragmentManager fm;
    private xlsAndXlsxInput input;
    private List<Student> prels = null;
    private List<Student> nowls = null;
    private Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        SpeechUtility.createUtility(this, SpeechConstant.APPID+"=5715fff3");
        Speaker.speak("开始点到！");
        fm = getFragmentManager();
        setDefaultFragment();
//viewpager实现fragment切换
        //      viewPager = (ViewPager)findViewById(R.id.main_viewpager);
   //     fragments = new ArrayList<Fragment>();
  //      fragments.add(new AddFragment());
   //     fragments.add(new SignFragment());

    //    FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(),fragments);

   //     viewPager.setAdapter(adapter);

//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//                    @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    private void setDefaultFragment()
    {
        FragmentTransaction transaction = fm.beginTransaction();
        signfragment = new SignFragment();
        mContent=signfragment;
        transaction.replace(R.id.fragment_container,signfragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(prestudent==null)
        Toast.makeText(this,"名单不能为空！",Toast.LENGTH_SHORT).show();
        //noinspection SimplifiableIfStatement
        else if(id == R.id.action_settings)
        {
            Intent intent = new Intent(this,SettingsActivity.class);
            startActivity(intent);
        }
        else if ((id == R.id.action_change)&&mContent.getClass()==signfragment.getClass()) {
            addfragment = new AddFragment(prestudent);
            switchContent(addfragment);
            return true;
        }
        else if (id == R.id.action_delete) {
                final UIInput ui = new UIInput();
                new AlertDialog.Builder(this)
                        .setTitle("删除学生信息")
                        .setMessage("确认删除？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ui.deleteById(prestudent.getStudentId() + "");
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                return true;

        }
        else if (id == R.id.action_export) {
            final EditText et = new EditText(this);
            et.setBackgroundResource(R.drawable.edit_sharp);
            et.setHint("请给文件命名");
            if(nowls!=null) {
                new AlertDialog.Builder(this)
                        .setView(et)
                        .setMessage("")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Collections.sort(nowls, new Comparator<Student>() {
                                    @Override
                                    public int compare(Student lhs, Student rhs) {

                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                            return Integer.compare(lhs.getStudentId(),rhs.getStudentId());
                                        }
                                        else {
                                            if(lhs.getStudentId()>rhs.getStudentId())
                                                return 1;
                                            else if(lhs.getStudentId()==rhs.getStudentId())
                                                return 0;
                                            else
                                                return -1;
                                        }
                                    }
                                });
                                xlsOutput abstractOutput = new xlsOutput(et.getText().toString(),nowls);
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
            else
                Toast.makeText(this,"导出失败！",Toast.LENGTH_SHORT).show();
            return true;
        }
        else if ((id == R.id.action_cancel) &&mContent.getClass()==signfragment.getClass()){
            new AlertDialog.Builder(this)
                    .setTitle("取消点到")
                    .setMessage("确认取消本次点到？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            UIInput ui = new UIInput(nowls);
                            Toast.makeText(MainActivity.this,"操作成功！",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.nav_import) {
            showDialog(openfileDialogId);
        } else if (id == R.id.nav_sign) {
            if (signfragment==null)
                signfragment = new SignFragment();
            switchContent(signfragment);
        } else if (id == R.id.nav_add) {
                addfragment = new AddFragment();
           switchContent(addfragment);
        } else if (id == R.id.nav_about) {
            if (aboutfragment==null)
                aboutfragment = new AboutFragment();
            switchContent(aboutfragment);
        } else if (id == R.id.nav_help) {
            if (helpfragment==null)
                helpfragment = new HelpFragment();
            switchContent(helpfragment);
        }else if(id==R.id.nav_statistics) {
            if(classname.length()==9)
                Toast.makeText(this,"当前学生:"+classname,Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this,"当前班级:"+classname,Toast.LENGTH_SHORT).show();
            statisticfragment = new StatisticFragment(classname);
            switchContent(statisticfragment);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == openfileDialogId) {
            Map<String, Integer> images = new HashMap<String, Integer>();
            // 下面几句设置各文件类型的图标， 需要你先把图标添加到资源文件夹
            images.put(OpenFileDialog.sRoot, R.drawable.filedialog_root);   // 根目录图标
            images.put(OpenFileDialog.sParent, R.drawable.filedialog_folder_up);    //返回上一层的图标
            images.put(OpenFileDialog.sFolder, R.drawable.filedialog_folder);   //文件夹图标
            images.put("xls", R.drawable.filedialog_xlsfile);   //xls文件图标
            images.put(OpenFileDialog.sEmpty, R.drawable.filedialog_root);
            Dialog dialog = OpenFileDialog.createDialog(id, this, "打开文件", new CallbackBundle() {
                        @Override
                        public void callback(Bundle bundle) {
                            filepath = bundle.getString("path");
                            input = new xlsAndXlsxInput(new File("mnt" + File.separator + filepath));
                        }
                    },
                    ".xls;",
                    images);
            return dialog;
        }
        return null;
    }

    public void switchContent(Fragment fragment) {
        if(mContent!=fragment) {
            mContent=fragment;
            FragmentTransaction transaction = fm.beginTransaction();
       //     transaction.remove(mContent);
            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
        }
    }

    @Override
    public void returnstudent(Student student,List<Student> ls) {
        this.prestudent = student;
        this.nowls= ls;
    }

    @Override
    public void returnclassname(String classname) {

        this.classname  = classname;
    }

    @Override
    public void returnprels(List<Student> pre) {
        this.prels = pre;
    }
}
