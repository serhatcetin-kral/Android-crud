package com.example.firebasecrud;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//       Toolbar toolbar=findViewById(R.id.menu_toolbar);
//        toolbar.setTitle("FireBaseCRUD");
//        setSupportActionBar(toolbar); bunlar olmuyo burada ve xml de toolbar a dikkat et hangisini sectigine
        Toolbar toolbar=findViewById(R.id.menu_toolbar);
        //toolbar.setTitle("FireBaseCRUDneresi");//burasi ana ekranda gosukuyo yanlis olabilir
        setSupportActionBar(toolbar);
    }

    private void setSupportActionBar(Toolbar toolbar) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }
}