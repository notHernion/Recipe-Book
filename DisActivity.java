package com.example.mainactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class DisActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dis);
        ListView listView = findViewById(R.id.listView);



        ArrayList<String> dataList = getIntent().getStringArrayListExtra("dataList");
        ArrayList<String> datalist2 = getIntent().getStringArrayListExtra("dataList2");


        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datalist2);

        listView.setAdapter(adapter2);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =new Intent(DisActivity.this,listall.class);
                intent.putExtra("details",dataList.get(position));
                startActivity(intent);
            }
        });
    }
}