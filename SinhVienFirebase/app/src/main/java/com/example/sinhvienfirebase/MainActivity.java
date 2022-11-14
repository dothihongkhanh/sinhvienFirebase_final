package com.example.sinhvienfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageButton ibAdd;
    TextView txtChao;

    SinhVienAdapter mainAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        txtChao=(TextView) findViewById(R.id.ten);



        FirebaseRecyclerOptions<SinhVienModel> options=
                new FirebaseRecyclerOptions.Builder<SinhVienModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("svien"),SinhVienModel.class)
                        .build();

        mainAdapter=new SinhVienAdapter(options);
        recyclerView.setAdapter(mainAdapter);

        ibAdd=(ImageButton) findViewById(R.id.button_add);
        ibAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AddSinhVien.class));
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainAdapter.startListening();
    }

}