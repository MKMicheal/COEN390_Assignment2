package com.example.coen390_assignmen2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import java.util.Date;

public class StudentDialog extends AppCompatDialogFragment {
    private EditText editSurname;
    private EditText editName;
    private EditText editId;
    private EditText editGPA;
    private StudentDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);
        builder.setView(view)
                .setTitle("Student Login")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
                        if(editSurname.getText().toString().equals("")||editName.getText().toString().equals("")||editId.getText().toString().equals("")||editGPA.getText().toString().equals(""))
                        {

                            Toast.makeText(getActivity().getApplicationContext(), "Field Cannot Be Empty", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            if(Integer.parseInt(editId.getText().toString())<10000000||Double.parseDouble(editGPA.getText().toString())>4.3)
                            {
                                Toast.makeText(getActivity().getApplicationContext(), "GPA must be between 0 and 4.3 or ID must be between 10000000 and 99999999", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                dbHelper.insertStudent(new Student(Integer.parseInt(editId.getText().toString()),editSurname.getText().toString(),editName.getText().toString(),Double.parseDouble(editGPA.getText().toString()),new Date().toString()));

                            }
                        }
                        listener.refresh();
                    }
                });
        editSurname = view.findViewById(R.id.editSurname);
        editName = view.findViewById(R.id.editName);
        editId = view.findViewById(R.id.editId);
        editGPA = view.findViewById(R.id.editGPA);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        listener=(StudentDialogListener) context;
    }

    public interface StudentDialogListener{
        void refresh();
    }
}
