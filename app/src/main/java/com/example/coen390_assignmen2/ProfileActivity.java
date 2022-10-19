package com.example.coen390_assignmen2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private TextView UserSurname;
    private TextView UserName;
    private TextView UserId;
    private TextView UserGPA;
    private TextView UserCreation;
    private ListView profileList;
    private ImageButton MoreProfile;
    private ImageButton Back;
    private boolean check = true;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        position =intent.getIntExtra("position",0);
        UserSurname = findViewById(R.id.UserSurname);
        UserName = findViewById(R.id.UserName);
        UserId = findViewById(R.id.UserId);
        UserGPA = findViewById(R.id.UserGPA);
        UserCreation = findViewById(R.id.UserCreation);
        profileList = findViewById(R.id.AccessList);
        MoreProfile = findViewById(R.id.MoreProfileButton);
        Back = findViewById(R.id.Backbutton);
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        List<Student> studentList = dbHelper.getAllStudents();
        int i=0;
        long id=1;
        for (Student s : studentList)
        {
            if(i==position) {
                id=s.getId();
                UserSurname.setText("Surname: " + s.getSurname() );
                UserName.setText("Name: " + s.getName().toString());
                UserId.setText("ID: " + Long.toString(s.getId()));
                UserGPA.setText("GPA: "+ Double.toString(s.getGPA()));
                UserCreation.setText("Profile created: " + s.getCreationDate().toString());
                dbHelper.insertAccess(new Access(-1, Long.toString(id),"Opened", new Date().toString()));

            }
            i+=1;

        }
        List<Access> accessList = dbHelper.getAllAccess();
        List<String> accessListString = new ArrayList<>();
        for (Access a: accessList)
        {
            if(Integer.parseInt(a.getId())==id)
            {
                accessListString.add(a.getTimeStamp()+" "+a.getaccessType());
            }

        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(ProfileActivity.this, android.R.layout.simple_list_item_1, accessListString);
        profileList.setAdapter(null);
        profileList.setAdapter(adapter);
        long finalId = id;
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ProfileActivity.this,MainActivity.class);
                dbHelper.insertAccess(new Access(-1, Long.toString(finalId),"Closed", new Date().toString()));
                startActivity(intent1);
            }
        });
        MoreProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.insertAccess(new Access(-1, Long.toString(finalId),"Deleted", new Date().toString()));
                dbHelper.deleteStudent(finalId);
                List<Access> accessList = dbHelper.getAllAccess();
                List<String> accessListString = new ArrayList<>();
                for (Access a: accessList)
                {
                    if(Integer.parseInt(a.getId())==finalId)
                    {
                        accessListString.add(a.getTimeStamp()+" "+a.getaccessType());
                    }

                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(ProfileActivity.this, android.R.layout.simple_list_item_1, accessListString);
                profileList.setAdapter(null);
                profileList.setAdapter(adapter);
            }
        });
    }
}