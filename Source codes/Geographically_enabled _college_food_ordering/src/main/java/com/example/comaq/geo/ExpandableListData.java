package com.example.comaq.geo;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class ExpandableListData {
  static FirebaseDatabase firebaseDatabase;
  static DatabaseReference databaseReference;

static List<String> chin , paratha ,drinks;
static Vector cn,cn1,par1,drin1,par,drin;
    public static HashMap <String , List<String>> getData()
    {
        HashMap <String , List<String>> expandableListDetail = new HashMap <String , List<String>>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("menu");
        chin = new ArrayList<String>();
        drinks = new ArrayList<String>();
        paratha = new ArrayList<String>();
        drin = new Vector<String>();
        drin1 = new Vector<String>();
        cn = new Vector<String>();
        cn1 = new Vector<String>();
        par1 = new Vector<String>();
        par = new Vector<String>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    for (DataSnapshot snap : snapshot.getChildren())
                    {
                        if (snapshot.getKey().compareTo("Drinks") ==0)
                        {
                            drin.add(snap.getKey());
                            drin1.add(snap.getValue().toString());
                            drinks.add(snap.getKey() +"\t Price =" + snap.getValue().toString());
                        }

                        if (snapshot.getKey().compareTo("Paratha") == 0)
                        {
                            par.add(snap.getKey());
                            par1.add(snap.getValue().toString());
                            paratha.add(snap.getKey() +"\tPrice =" + snap.getValue().toString());
                        }
                        if (snapshot.getKey().compareTo("Chinese") == 0)
                        {

                            cn.add(snap.getKey());
                            cn1.add(snap.getValue().toString());
                            chin.add(snap.getKey() +"\t Price =" + snap.getValue().toString());
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        expandableListDetail.put("Chinese",chin);
        expandableListDetail.put("Drinks",drinks);
        expandableListDetail.put("Paratha",paratha);
        return expandableListDetail;
    }
     public Vector listener (int id)
     {
         if (id==0)
         {
             return drin;
         }
         else
         if (id==1)
         {
             return cn;
         }
         else
             return par;
     }

    public Vector getDrin1(int id) {
        if (id==0)
        {
            return drin1;
        }
        else
        if (id==1)
        {
            return cn1;
        }
        else
            return par1;
    }
}
