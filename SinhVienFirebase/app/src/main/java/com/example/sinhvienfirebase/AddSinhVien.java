package com.example.sinhvienfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddSinhVien extends AppCompatActivity {

    EditText ed_tenTourAdd, ed_tenTinhAdd, ed_AnhAdd, ed_giaAdd;
    Button btnAdd,btnBack;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sinh_vien);

        ed_tenTourAdd = (EditText)findViewById(R.id.editT_name_add);
        ed_tenTinhAdd =(EditText) findViewById(R.id.editT_tenTinh_add);
        ed_giaAdd=(EditText) findViewById(R.id.editT_gia_add);
        ed_AnhAdd = (EditText) findViewById(R.id.editT_anh_add);

        btnAdd = (Button) findViewById(R.id.button_save);
        btnBack = (Button) findViewById(R.id.button_back);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
                clearAll();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void clearAll(){
        ed_tenTourAdd.setText("");
        ed_tenTinhAdd.setText("");
        ed_AnhAdd.setText("");
        ed_giaAdd.setText("");
    }
    private void insertData(){
        Map<String,Object> map= new HashMap<>();
        map.put("hoTen", ed_tenTourAdd.getText().toString());
        map.put("lop", ed_tenTinhAdd.getText().toString());
        map.put("maSV", ed_giaAdd.getText().toString());
        map.put("anh", ed_AnhAdd.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("svien").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddSinhVien.this, "Data Insert Successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddSinhVien.this, "Error While Insertion", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}