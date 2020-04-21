package com.example.comaq.geo;


import android.os.Bundle;
import androidx.annotation.NonNull;
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


/**
 * A simple {@link Fragment} subclass.
 */

public class Past_Orders extends Fragment {
    View rootView;
    ListView lsd;
    List<String> listHeader;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    ArrayAdapter<String> arrayAdapter;
    String User,one="";
    DatabaseReference databaseReference,databaseReference1;
    public Past_Orders() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_past__orders, container, false);
        lsd =(ListView) rootView.findViewById(R.id.lst);
        listHeader = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, listHeader);
        lsd.setAdapter(arrayAdapter);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        User = firebaseUser.getEmail();
        databaseReference = firebaseDatabase.getReference().child("Account Details");
        databaseReference1 = firebaseDatabase.getReference().child("Past Orders");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                    {
                    if ((snapshot.child("Email").getValue().toString()).compareTo(User) == 0) {
                        {
                            databaseReference1.child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    int count = 1;
                                    for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                                        one = one + "\nItem No :-" + count;
                                        count++;
                                        for (DataSnapshot snapshot2 : snapshot1.getChildren())
                                        for (DataSnapshot snapshot3 : snapshot2.getChildren())
                                            one = one + "\n \t \t" + snapshot3.getKey() + "= " + snapshot3.getValue();
                                    }
                                    arrayAdapter.add(one);
                                    arrayAdapter.notifyDataSetChanged();
                                    one = "";
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return rootView;
    }
}
