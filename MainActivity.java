package com.example.mainactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText name, ingredients, description;
    Button save, delete, view,searchButton,update,clear;

    SQLiteDatabase DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.etName);
        ingredients = findViewById(R.id.etIng);
        description = findViewById(R.id.etDesc);
        save = findViewById(R.id.buttonSave);
        delete = findViewById(R.id.buttonDelete);
        view = findViewById(R.id.buttonView);
        searchButton=findViewById(R.id.buttonSearch);
        update=findViewById(R.id.buttonUpdate);
        clear=findViewById(R.id.buttonClear);

        DB=openOrCreateDatabase("RecipeDatabase", Context.MODE_PRIVATE,null);
        DB.execSQL("CREATE TABLE IF NOT EXISTS recipe(Name VARCHAR, Ingredients TEXT, Description TEXT)");

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor c=DB.rawQuery("SELECT * FROM recipe", null);
                if (c.getCount() > 0) {
                    Intent intent = new Intent(MainActivity.this, DisActivity.class);
                    ArrayList<String> dataList = new ArrayList<>();
                    ArrayList<String> datalist2 =new ArrayList<>();
                    while (c.moveToNext()) {
                        datalist2.add("Recipe Name: "+c.getString(0));
                        dataList.add("Name: " + c.getString(0) +
                                "\nIngredients: " + c.getString(1) +
                                "\nDescription: " + c.getString(2) +
                                "\n-------------------\n");
                    }


                    // Pass the data to the new activity
                    intent.putStringArrayListExtra("dataList", dataList);
                    intent.putStringArrayListExtra("dataList2", datalist2);
                    startActivity(intent);
                }
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().trim().length() == 0 || ingredients.getText().toString().trim().length() == 0 ||
                        description.getText().toString().trim().length() == 0) {
                    Toast.makeText(MainActivity.this, "All fields are required", Toast.LENGTH_LONG).show();
                    return;
                }
                Cursor c = DB.rawQuery("SELECT * FROM recipe WHERE name='" + name.getText() + "'", null);
                if (c.moveToFirst()) {
                    Toast.makeText(MainActivity.this, "Enter another recipe!", Toast.LENGTH_LONG).show();
                    return;
                }
                DB.execSQL("INSERT INTO recipe VALUES('" + name.getText() + "','" + ingredients.getText() + "','" + description.getText() + "');");
                Toast.makeText(MainActivity.this, "Recipe Inserted.", Toast.LENGTH_LONG).show();
                name.setText("");
                ingredients.setText("");
                description.setText("");
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().trim().length()==0){
                    Toast.makeText(MainActivity.this,"Name cannot be empty.",Toast.LENGTH_LONG).show();
                    return;
                }
                Cursor c=DB.rawQuery("SELECT * FROM recipe WHERE name='" + name.getText()+"'",null);
                if(c.moveToFirst()){
                    DB.execSQL("DELETE FROM recipe WHERE name='"+name.getText()+"'");
                    Toast.makeText(MainActivity.this, "Recipe is Deleted.", Toast.LENGTH_SHORT).show();
                    name.setText("");
                    ingredients.setText("");
                    description.setText("");
                    return;
                }
                Toast.makeText(MainActivity.this, "Recipe not found", Toast.LENGTH_SHORT).show();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().trim().length()==0|| ingredients.getText().toString().trim().length()==0 ||
                        description.getText().toString().trim().length()==0 ){
                    Toast.makeText(MainActivity.this,"All fileds are required",Toast.LENGTH_LONG).show();
                    return; }
                Cursor c=DB.rawQuery("SELECT * FROM recipe WHERE name='"+name.getText()+"'",null);
                if(c.moveToFirst()){
                    DB.execSQL("UPDATE recipe set ingredients='"+ingredients.getText()+"',description='"+description.getText()+
                            "' WHERE name='"+name.getText()+"'");
                    Toast.makeText(MainActivity.this, "Recipe Updated", Toast.LENGTH_SHORT).show();
                    ingredients.setText(c.getString(1));
                    description.setText(c.getString(2));
                    return;
                }
                Toast.makeText(MainActivity.this, "This recipe is not available", Toast.LENGTH_SHORT).show();
                return;
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setText("");
                ingredients.setText("");
                description.setText("");
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performGoogleSearch();
            }
        });
    }
    private void performGoogleSearch() {
        EditText editTextSearch = findViewById(R.id.editTextSearch);
        String query = editTextSearch.getText().toString().trim();
        if (!query.isEmpty()) {
            // Create a Google search intent
            Intent searchIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=" + query));
            startActivity(searchIntent);
        }
    }
}

