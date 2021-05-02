package com.example.sqlitedemo_cz;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText etName = (EditText)findViewById(R.id.editText_name);
        final EditText etAge = (EditText)findViewById(R.id.editText_age);
        final EditText etTall = (EditText)findViewById(R.id.editText_tall);
        Button btn_add = (Button)findViewById(R.id.button_add);
        Button btn_show = (Button)findViewById(R.id.button_show);
        Button btn_de = (Button)findViewById(R.id.button_de);
        Button btn_allde = (Button)findViewById(R.id.button_allde);
        final EditText et_id = (EditText)findViewById(R.id.et_id);
        Button id_delete = (Button)findViewById(R.id.btn_delete);
        Button id_search = (Button)findViewById(R.id.btn_search);
        Button id_update = (Button)findViewById(R.id.btn_update);
        final TextView textView_attention = (TextView)findViewById(R.id.textView_attention);
        final TextView textView_data = (TextView)findViewById(R.id.textView_data);

        final DBAdapter_cz dbAdapter_cz = new DBAdapter_cz(this);
        dbAdapter_cz.open();


        btn_add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                People_cz people_cz = new People_cz();
                people_cz.Name_cz = etName.getText().toString();
                people_cz.Age_cz = etAge.getText().toString();
                people_cz.Height_cz = etTall.getText().toString();
                long column_cz = dbAdapter_cz.insert_cz(people_cz);
                if(column_cz==-1){
                    textView_attention.setText("添加过程错误");
                }
                else{
                    textView_attention.setText("成功添加数据,ID"+String.valueOf(column_cz).toString());
                }
            }
        });
        btn_show.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                People_cz[] peoples_cz = dbAdapter_cz.queryAllData();
                if(peoples_cz == null){
                    textView_attention.setText("数据库中没有数据");
                    return;
                }
                textView_attention.setText("数据库");
                String msg = "";
                for(int i = 0;i<peoples_cz.length;i++){
                    msg += peoples_cz[i].toString_cz()+"\n";
                }
                textView_data.setText(msg);
            }
        });
        btn_de.setOnClickListener(new View.OnClickListener(){   //单个删除
            @Override
            public void onClick(View v) {
                textView_data.setText("");
            }
        });
        btn_allde.setOnClickListener(new View.OnClickListener(){    //删除所有
            @Override
            public void onClick(View v) {
                dbAdapter_cz.deleteAllData();
                String msg = "数据全部删除";
                textView_attention.setText(msg);
            }
        });
        id_delete.setOnClickListener(new View.OnClickListener() {   //ID删除
            @Override
            public void onClick(View v) {
                if(et_id.getText().toString().equals("")){
                    textView_attention.setText("请输入id");
                }
                else{
                    long id = Long.parseLong(et_id.getText().toString());
                    long result = dbAdapter_cz.deleteOneData(id);
                    String msg = "删除ID为"+et_id.getText().toString()+"的数据"+(result>0?"成功":"失败");
                    textView_attention.setText(msg);
                }
            }
        });
        id_search.setOnClickListener(new View.OnClickListener() {   //ID查询
            @Override
            public void onClick(View v) {
                if(et_id.getText().toString().equals("")){
                    textView_attention.setText("请输入id");
                }
                else{
                    int id = Integer.parseInt(et_id.getText().toString());
                    People_cz[] peoples_cz = dbAdapter_cz.queryOneData(id);
                    if (peoples_cz == null) {
                        textView_attention.setText("数据库中没有ID为" + String.valueOf(id) + "的数据");
                        return;
                    }
                    textView_attention.setText("数据库：");
                    textView_data.setText(peoples_cz[0].toString_cz());
                }
            }
        });
        id_update.setOnClickListener(new View.OnClickListener() {   //ID更新
            @Override
            public void onClick(View v) {
                People_cz people_cz = new People_cz();
                people_cz.Name_cz = etName.getText().toString();
                people_cz.Age_cz = etAge.getText().toString();
                people_cz.Height_cz = etTall.getText().toString();
                String id = et_id.getText().toString();
                long count = dbAdapter_cz.updateOneData(id,people_cz);
                if(count == -1){
                    textView_attention.setText("更新错误");
                }else{
                    textView_attention.setText("更新成功，更新数据"+String.valueOf(count)+"条");
                }
                return;
            }
        });
    }

}