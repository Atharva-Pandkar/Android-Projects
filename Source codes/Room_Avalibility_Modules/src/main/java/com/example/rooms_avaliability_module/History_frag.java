package com.example.rooms_avaliability_module;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class History_frag extends Fragment {

    private HistoryFragViewModel mViewModel;

    public static History_frag newInstance() {
        return new History_frag();
    }
View root;
    ListView lst;
    ArrayAdapter<String> arrayAdapter;
    public FirebaseDatabase firebaseDatabase;
   public DatabaseReference databaseReference,userss;
    List <String> arrayList =new ArrayList<>();
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
   public String fin,gin,mail;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.history_frag_fragment, container, false);
        lst=root.findViewById(R.id.ListView);
        arrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,arrayList);
        firebaseDatabase = FirebaseDatabase.getInstance();
        userss = firebaseDatabase.getReference();
        fin="";

        databaseReference = firebaseDatabase.getReference().child("History").child("Usres");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        //mail=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        mail="atharvapandkar18@gmail.com";
        lst.setAdapter(arrayAdapter);
      /*  userss.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    if (snapshot.getValue().toString().compareTo(mail) == 0) {
                        fin  = snapshot.getKey();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
      fin="1032191723";


        databaseReference.child(fin).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snap : dataSnapshot.getChildren())
                        {
                          for(  DataSnapshot s :snap.getChildren()) {
                              gin = s.getKey();
                              for (DataSnapshot snapshot1 : s.getChildren()) {
                                  arrayAdapter.add(gin+ " " + snapshot1.getKey());
                                  arrayAdapter.notifyDataSetChanged();

                              }
                          }
                        }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HistoryFragViewModel.class);
        // TODO: Use the ViewModel
    }

}
