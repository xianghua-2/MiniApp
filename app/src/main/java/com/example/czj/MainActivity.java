package com.example.czj;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.czj.DataBase.StorageDataManager;
import com.example.czj.MyToolClass.MyFunction;
import com.example.czj.MyToolClass.MyStaticData;
import com.example.czj.MyToolClass.ScreenUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private RadioGroup radioGroup;
    private SparseArray<Fragment> sparseArray;
    Toolbar toolbar;
    TextView toolbarTitle;
//    Button mainButton;
    ImageView mainButton;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyStaticData myStaticData = (MyStaticData)this.getApplication();
        initView();
    }
    void initView(){
        mainButton = findViewById(R.id.MainButton);
        mainButton.bringToFront(); //最上层显示
        getSupportFragmentManager().beginTransaction().add(R.id.FragmentContain,CalendarFragment.newInstance("日历","Calendar")).commit();
        radioGroup = findViewById(R.id.RadioGroup);
        sparseArray = new SparseArray<>();
        sparseArray.append(R.id.radioButtonEarn,EarnFragment.newInstance("收礼","EARN"));
        sparseArray.append(R.id.MainButton,CalendarFragment.newInstance("日历","Calendar"));
        sparseArray.append(R.id.radioButtonGive,GiveFragment.newInstance("随礼","GIVE"));
        toolbar = findViewById(R.id.Toolbar);
        toolbarTitle = findViewById(R.id.ToolbarTitle);
        toolbarTitle.setText(getString(R.string.toolbar_mainpage));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); //隐藏标题 ,自定义居中

        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(MyStaticData.date[0],MyStaticData.date[1],MyStaticData.date[2]);
            }
        });
        //留个后门，悬浮按钮长按删除两个表
        floatingActionButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                StorageDataManager storageDataManager = new StorageDataManager();
                storageDataManager.deleteEarningTable();
                storageDataManager.deleteGivingTable();
                Toast.makeText(v.getContext(),"delete successfully",Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // 具体的fragment切换逻辑可以根据应用调整，例如使用show()/hide()
                getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContain,
                        sparseArray.get(checkedId)).commit();
                if(checkedId == R.id.radioButtonEarn)
                    ((TextView)findViewById(R.id.ToolbarTitle)).setText(getResources().getString(R.string.toolbar_earn_money));
                if(checkedId == R.id.radioButtonGive)
                    ((TextView)findViewById(R.id.ToolbarTitle)).setText(getResources().getString(R.string.toolbar_give_money));
            }
        });

        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContain,
                        sparseArray.get(R.id.MainButton)).commit();
                ((TextView)findViewById(R.id.ToolbarTitle)).setText(getResources().getString(R.string.toolbar_mainpage));
                //不加就会有bug，按了这个按钮之后radioButton的两个按钮就会有一个有问题
                RadioButton bntGive = findViewById(R.id.radioButtonGive);
                RadioButton bntEarn = findViewById(R.id.radioButtonEarn);
                bntGive.setChecked(false);
                bntEarn.setChecked(false);
            }
        });
    }




    //添加右上角的菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }



    private void showDialog(int year,int month,int day){
        View view = LayoutInflater.from(this).inflate(R.layout.date_layout,null,false);
        final AlertDialog dialog = new AlertDialog.Builder(this).setView(view).create();
        EditText inputYear,inputMonth,inputDay,inputReason,inputAmount,inputName;
        TextView inputReasonText = view.findViewById(R.id.DlgReasonText);
        inputDay = view.findViewById(R.id.DlgDayInput);
        inputMonth = view.findViewById(R.id.DlgMonthInput);
        inputYear = view.findViewById(R.id.DlgYearInput);
        inputReason = view.findViewById(R.id.DlgReasonInput);
        inputAmount = view.findViewById(R.id.DlgAmountInput);
        inputName = view.findViewById(R.id.DlgNameInput);
        inputYear.setText(String.valueOf(year));
        inputMonth.setText(String.valueOf(month));
        inputDay.setText(String.valueOf(day));

        int []witchChoose ={2}; //保存用户选择的是收礼还是随礼
        view.findViewById(R.id.DlgSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断年月日金额是否为整型
                if(!MyFunction.checkIsInt(inputYear.getText().toString())||
                    !MyFunction.checkIsInt(inputMonth.getText().toString())||
                        !MyFunction.checkIsInt(inputDay.getText().toString())||
                            !MyFunction.checkIsInt(inputAmount.getText().toString())){
                    Toast.makeText(view.getContext(),view.getResources().getString(R.string.dialog_not_int_warning),Toast.LENGTH_SHORT).show();
                    return;
                }
                int year = Integer.parseInt(inputYear.getText().toString());
                int month = Integer.parseInt(inputMonth.getText().toString());
                int day = Integer.parseInt(inputDay.getText().toString());
                int amount = Integer.parseInt(inputAmount.getText().toString());
                String name = inputName.getText().toString();
                //简单判断年月日是否合法
                if(year<0||month<0||day<0||month>12||day>31||amount<0){ //就简单一点判断吧Orz
                    Toast.makeText(view.getContext(),view.getResources().getString(R.string.dialog_input_innormal),Toast.LENGTH_SHORT).show();
                    return;
                }
                StorageDataManager storageDataManager = new StorageDataManager();
                if(witchChoose[0]==1){ //随礼
                    //插入对应的数据库
                    storageDataManager.insertGiving(name,amount,year,month,day);
                }
                else{
                    String reason = inputReason.getText().toString();
                    storageDataManager.insertEarning(name,amount,year,month,day,reason);
                }
                dialog.dismiss();
            }
        });
        RadioGroup radioGroup = view.findViewById(R.id.DlgRadioGroup);
        RadioButton bntGive = view.findViewById(R.id.DlgRadioButtonGiving);
        RadioButton bntEarn = view.findViewById(R.id.DlgRadioButtonEarning);
        bntEarn.setChecked(true);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == bntGive.getId()){
                    witchChoose[0]=1; //witchChoose[0]保存是收礼还是随礼
                    inputReason.setVisibility(View.INVISIBLE); //设置原因一栏是否可见
                    inputReasonText.setVisibility(View.INVISIBLE);
                }
                else if(checkedId == bntEarn.getId()){
                    witchChoose[0]=2;
                    inputReason.setVisibility(View.VISIBLE);
                    inputReasonText.setVisibility(View.VISIBLE);
                }
            }
        });

        dialog.show();
        //此处设置位置窗体大小，我这里设置为了手机屏幕宽度的3/4  注意一定要在show方法调用后再写设置窗口大小的代码，否则不起效果会
        dialog.getWindow().setLayout((ScreenUtils.getScreenWidth(this)), LinearLayout.LayoutParams.WRAP_CONTENT);
    }



}




