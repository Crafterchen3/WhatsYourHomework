package com.deckerpw.whatsyourhomework;

import android.content.Intent;
import android.os.Bundle;
import android.util.JsonWriter;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.deckerpw.whatsyourhomework.databinding.ActivityDetailBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    private Subject subject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle obj = intent.getBundleExtra("obj");
        subject = new Subject(obj.getString("name"),obj.getString("contents"),obj.getLong("hardnessLevel"));
        System.out.println(subject.hardnessLevel);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageDrawable(getDrawable(R.drawable.homework));
        TextView tvtitle = findViewById(R.id.SubjectTitle);
        tvtitle.setText(subject.getName());
        TextView tvcontents = findViewById(R.id.SubjectContents);
        tvcontents.setText(subject.getContents());


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }


    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }


}

