package com.example.comaq.geo;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;


public class Menu_frag extends Fragment {

View rootView,vt;
ExpandableListView expandableListView ;
HashMap <String , List<String>> ListChild;
List<String> listHeader;
CustomAdapter customAdapter;
Vector vc,vc1,all;
FirebaseDatabase firebaseDatabase;
FirebaseUser firebaseUser;
FirebaseAuth firebaseAuth;
String User,name,push,s,ex;

int s1=0,sum,quant;
    String one;
    int count = 1;
DatabaseReference databaseReference,databaseReference2, databaseReference1;
    Calculator c = new Calculator();
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_menu_frag,container,false);
        vt = inflater.inflate(R.layout.alertdbox,container,false);
        //final LinearLayout ll_alert_layout = new LinearLayout();
        //ll_alert_layout.addView(ed_input);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        User = firebaseUser.getEmail();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Account Details");
        expandableListView = (ExpandableListView) rootView.findViewById(R.id.expListView);
        ListChild = ExpandableListData.getData();
        databaseReference1 = firebaseDatabase.getReference().child("menu");
        databaseReference2 = firebaseDatabase.getReference().child("Orders");
        listHeader = new ArrayList<String>(ListChild.keySet());
        customAdapter = new CustomAdapter( getContext() , ListChild , listHeader);
        expandableListView.setAdapter(customAdapter);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
             vc = new ExpandableListData().listener(groupPosition);
             vc1 = new ExpandableListData().getDrin1(groupPosition);
             all = new Vector();
             s1 = Integer.parseInt(vc1.elementAt(childPosition).toString());
             s = vc.elementAt(childPosition).toString();
                all.add("Name\t\tQuantity\tprice\n");
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(false);
                builder.setView(R.layout.alertdbox);
                builder.setTitle("Confirm Item");
                builder.setMessage("You have selected " + s +"  Price = "+s1+". \nDo you wish to add more Items ");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        store();
                        final EditText ed_input = vt.findViewById(R.id.edttxt);
                       // sum = c.add(s1,Integer.parseInt( ed_input.getText().toString()));
                        all.add(s+"\t    ");
                      //  all.add(Integer.parseInt(ed_input.getText().toString())+"\t");
                        all.add(s1+"\n");
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                         dialog.dismiss();
                        store();
                        final EditText ed_input = vt.findViewById(R.id.edttxt);
                       // sum = c.add(s1,Integer.parseInt(ed_input.getText().toString()));
                         AlertDialog.Builder dia = new AlertDialog.Builder(getContext());
                         for (int i=0;i<all.size(); i++)
                         {
                             one = one + all.elementAt(i);

                         }
                         dia.setTitle("Confirm Order");
                                    dia.setMessage(one);

                                    dia.setCancelable(false);
                                    dia.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(getContext(),Home.class);
                                            startActivity(intent);
                                        }
                                    });
                                    dia.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            databaseReference.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                        if ((snapshot.child("Email").getValue().toString()).compareTo(User) == 0) {
                                                            name = snapshot.getKey();
                                                            databaseReference2.child(name).setValue(null);
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });


                                        }
                                    });
                                    dia.create().show();
                        one = null;
                    }
                });
                builder.create().show();
                return true ;
            }
        });
       return rootView;
    }

    public void store() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if ((snapshot.child("Email").getValue().toString()).compareTo(User) == 0) {
                        name = snapshot.getKey();
                        push = databaseReference2.child(name).push().getKey();
                        databaseReference2.child(name).child(push).child("product ").setValue(s);
                        databaseReference2.child(name).child(push).child("Price").setValue(s1);
                        databaseReference2.child(name).child(push).child("Sum").setValue(sum);
                        databaseReference2.child(name).child(push).child("Quantity").setValue(quant);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

public void Enter_Quantity()
{

}
}
