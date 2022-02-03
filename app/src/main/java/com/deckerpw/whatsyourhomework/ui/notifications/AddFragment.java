package com.deckerpw.whatsyourhomework.ui.notifications;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.deckerpw.whatsyourhomework.MainActivity;
import com.deckerpw.whatsyourhomework.R;
import com.deckerpw.whatsyourhomework.Subject;
import com.deckerpw.whatsyourhomework.databinding.FragmentAddBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddFragment extends Fragment {

    private FragmentAddBinding binding;
    private TextView edName;
    private TextView edContents;
    private RadioGroup radioGroup;
    private Button AddButton;
    private Button ClearButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentAddBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        edName = root.findViewById(R.id.edName);
        edContents = root.findViewById(R.id.edContents);
        radioGroup = root.findViewById(R.id.rbHardness);
        ClearButton = root.findViewById(R.id.clear_button);
        ClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edName.setText("");
                edContents.setText("");
                radioGroup.check(R.id.rbEasy);
            }
        });
        AddButton = root.findViewById(R.id.add_button);
        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hardnessLevel = 0;
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.rbEasy:
                        hardnessLevel = 1;
                        break;
                    case R.id.rbNormal:
                        hardnessLevel = 2;
                        break;
                    case R.id.rbHard:
                        hardnessLevel=3;
                        break;
                }
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, Object> subject = new HashMap<>();
                subject.put("name", edName.getText().toString());
                subject.put("contents", edContents.getText().toString());
                subject.put("hardnessLevel", hardnessLevel);
                // Add a new document with a generated ID
                MainActivity.list.add(subject);
                Map map = MainActivity.group.getData();

                map.put("subjects",(List) MainActivity.list);
                MainActivity.homework.add(new Subject(edName.getText().toString(),edContents.getText().toString(),hardnessLevel));
                db.collection("groups").document(MainActivity.id)
                        .set(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d(TAG,"Yay");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}