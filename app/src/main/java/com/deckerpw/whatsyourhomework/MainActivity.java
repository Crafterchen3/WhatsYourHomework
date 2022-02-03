package com.deckerpw.whatsyourhomework;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;

import com.deckerpw.whatsyourhomework.ui.home.HomeFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.deckerpw.whatsyourhomework.databinding.ActivityMainBinding;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {


    public static ArrayList<Subject> homework;
    private ActivityMainBinding binding;
    public static QueryDocumentSnapshot group;
    public static HomeFragment adapter;
    public static String id;
    public static ArrayList<String> groups;
    public static int index = 0;
    public static ArrayList<Map> list;

    public static void updateHomework(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        homework = new ArrayList<>();
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG,email);
                                if (document.getId().equals(email)) {
                                    groups = (ArrayList<String>) document.getData().get("groups");
                                    id = groups.get(index);
                                    Log.d(TAG,id+"  "+email);
                                    db.collection("groups")
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        for (QueryDocumentSnapshot document1 : task.getResult()) {
                                                            Log.d(TAG,document1.toString());
                                                            Log.d(TAG,document1.getId());
                                                            Log.d(TAG,id);
                                                            if (document1.getId().equals(id)) {
                                                                Log.d(TAG,"lol");
                                                                group = document1;
                                                                list = (ArrayList<Map>) document1.getData().get("subjects");
                                                                for (Map m: list) {
                                                                    Subject subject = convertMaptoSubject(m);
                                                                    homework.add(subject);
                                                                }
                                                            }
                                                        }
                                                        adapter.adapter.notifyDataSetChanged();
                                                        adapter.updateSpinner();
                                                    } else {
                                                        Log.w(TAG, "Error getting documents.", task.getException());
                                                    }
                                                }
                                            });
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

    }


    public static Subject convertMaptoSubject(Map map){
        return new Subject((String)map.get("name"),(String) map.get("contents"), (long)  map.get("hardnessLevel"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateHomework();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                 R.id.navigation_messages,R.id.navigation_home, R.id.navigation_add)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        NavigationUI.setupWithNavController(binding.navView, navController);
    }

}