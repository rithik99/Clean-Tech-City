package com.example.subratha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class review extends AppCompatActivity {

    EditText ed1,txt9;
    String j ="";
    TextView txt1,txt2,txt3,txt4,txt5,txt6,txt7,txt8,txt10;
    DatabaseReference ref1,dref;
    String em,bna;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        initf();
        Intent i = getIntent();
        bna = i.getStringExtra("bname");
        ref1 = FirebaseDatabase.getInstance().getReference();
        dref = ref1.child("Reviews");
        txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emexists()){
                    em = ed1.getText().toString();
                    Map<String, Details> details = new HashMap<>();
                    details.put(em,new Details(em,bna,txt1.getText().toString()));
                    j+=em+bna+txt1.getText().toString();
                    dref.push().setValue(details);
                    Toast.makeText(getApplicationContext(),"Submitted: "+j,Toast.LENGTH_LONG).show();
                }
            }
        });
        txt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emexists()){
                    em = ed1.getText().toString();
                    Map<String, Details> details = new HashMap<>();
                    details.put(em,new Details(em,bna,txt3.getText().toString()));
                    j+=em+bna+txt3.getText().toString();
                    dref.push().setValue(details);
                    Toast.makeText(getApplicationContext(),"Submitted",Toast.LENGTH_LONG).show();
                }
            }
        });
        txt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emexists()){
                    em = ed1.getText().toString();
                    Map<String, Details> details = new HashMap<>();
                    details.put(em,new Details(em,bna,txt5.getText().toString()));
                    j+=em+bna+txt5.getText().toString();
                    dref.push().setValue(details);
                    Toast.makeText(getApplicationContext(),"Submitted",Toast.LENGTH_LONG).show();
                }
            }
        });
        txt8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emexists()){
                    em = ed1.getText().toString();
                    Map<String, Details> details = new HashMap<>();
                    details.put(em,new Details(em,bna,txt7.getText().toString()));
                    j+=em+bna+txt7.getText().toString();
                    dref.push().setValue(details);
                    Toast.makeText(getApplicationContext(),"Submitted",Toast.LENGTH_LONG).show();
                }
            }
        });
        txt10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emexists()){
                    em = ed1.getText().toString();
                    Map<String, Details> details = new HashMap<>();
                    details.put(em,new Details(em,bna,txt9.getText().toString()));
                    j+=em+bna+txt9.getText().toString();
                    dref.push().setValue(details);
                    Toast.makeText(getApplicationContext(),"Submitted",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void initf() {
        ed1 = findViewById(R.id.ed1);
        txt1 = findViewById(R.id.r1);
        txt2 = findViewById(R.id.a1);
        txt3 = findViewById(R.id.r2);
        txt4 = findViewById(R.id.a2);
        txt5 = findViewById(R.id.r3);
        txt6 = findViewById(R.id.a3);
        txt7 = findViewById(R.id.r4);
        txt8 = findViewById(R.id.a4);
        txt9 = findViewById(R.id.r5);
        txt10 = findViewById(R.id.a5);
    }

    private boolean emexists(){
        String h = ed1.getText().toString();
        return !TextUtils.isEmpty(h);
    }
}
