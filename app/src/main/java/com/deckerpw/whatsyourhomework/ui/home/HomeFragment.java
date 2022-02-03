package com.deckerpw.whatsyourhomework.ui.home;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.JsonWriter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deckerpw.whatsyourhomework.DetailActivity;
import com.deckerpw.whatsyourhomework.MainActivity;
import com.deckerpw.whatsyourhomework.MyRecyclerViewAdapter;
import com.deckerpw.whatsyourhomework.R;
import com.deckerpw.whatsyourhomework.Subject;
import com.deckerpw.whatsyourhomework.databinding.FragmentHomeBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

public class HomeFragment extends Fragment implements MyRecyclerViewAdapter.ItemClickListener {

    private FragmentHomeBinding binding;
    public MyRecyclerViewAdapter adapter;
    private Spinner spinner;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();


        RecyclerView recyclerView = root.findViewById(R.id.rvHomework);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        adapter = new MyRecyclerViewAdapter(root.getContext(), MainActivity.homework);
        adapter.setClickListener(this);
        MainActivity.adapter = this;
        recyclerView.setAdapter(adapter);
        spinner = root.findViewById(R.id.spinner);


        return root;
    }


    public void updateSpinner(){
/*
        SpinnerAdapter adapter2 = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_spinner_item, MainActivity.groups .toArray(
                new String[MainActivity.groups.size()]));
        spinner.setAdapter(adapter2);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.index = position;
                MainActivity.updateHomework();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
*/
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);

        Subject subject = adapter.getItem(position);
        Bundle obj = new Bundle();
        obj.putString("name", subject.getName());
        obj.putString("contents", subject.getContents());
        obj.putLong("hardnessLevel", subject.getHardnessLevel());
        intent.putExtra("obj",obj);

        startActivity(intent);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}