package com.example.coen390_assignmen2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements  StudentDialog.StudentDialogListener{
    private FloatingActionButton addStudent;
    private TextView Studentcount;
    private ListView Studentlist;
    private ImageButton MoreMain;
    private boolean check = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addStudent = findViewById(R.id.addStudent);
        Studentcount = findViewById(R.id.StudentCount);
        Studentlist = findViewById(R.id.StudentList);
        MoreMain = findViewById(R.id.MoreMainButton);

        addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();


                updateListView();
            }
        });
        MoreMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu=new PopupMenu(MainActivity.this, MoreMain);
                popupMenu.getMenuInflater().inflate(R.menu.mainpopup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.Toggle:
                                if (check == true) {
                                    check = false;
                                    List<String> stringStudentList = new ArrayList<>();
                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, stringStudentList);
                                    DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
                                    List<Student> studentList = dbHelper.getAllStudents();

                                    int i = 1;
                                    for (Student s : studentList) {

                                        stringStudentList.add(i + ", " + s.getId());
                                        i += 1;

                                    }
                                    Studentcount.setText(i - 1 + " Profiles, by Surname");

                                    Studentlist.setAdapter(null);
                                    Studentlist.setAdapter(adapter);
                                } else {
                                    check = true;
                                    updateListView();
                                }


                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        updateListView();
        Studentlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent profileIntent = new Intent(MainActivity.this, ProfileActivity.class);
                profileIntent.putExtra("position", i);

                startActivity(profileIntent);


            }
        });
    }


    public void updateListView(){
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        List<Student> studentList = dbHelper.getAllStudents();
        List<String> stringStudentList = new ArrayList<>();
        int i=1;
        for (Student s : studentList)
        {

            stringStudentList.add(i+ ", "+ s.getSurname() +" "+ s.getName());
            i+=1;

        }
        Studentcount.setText(i-1+" Profiles, by Surname");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, stringStudentList);
        Studentlist.setAdapter(null);
        Studentlist.setAdapter(adapter);
        check =true;

    }
    public void openDialog(){
        StudentDialog studentDialog = new StudentDialog();
        studentDialog.show(getSupportFragmentManager(),"student dialog");
        updateListView();
    }

    @Override
    public void refresh() {
        updateListView();
    }
}