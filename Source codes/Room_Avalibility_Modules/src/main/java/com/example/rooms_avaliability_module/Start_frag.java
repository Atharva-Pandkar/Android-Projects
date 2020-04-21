package com.example.rooms_avaliability_module;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.zip.Inflater;

public class Start_frag extends Fragment {

    private StartFragViewModel mViewModel;

    public static Start_frag newInstance() {
        return new Start_frag();
    }
View root;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    int pos;
    EditText name;
    ListView lst,lst1;
    TextView txt;
    List <String> arrayList =new ArrayList<>();
    List <String> llist = new ArrayList<>();
    Button btn;
    ArrayAdapter<String> arrayAdapter,room_arrayAdapter,tempadapter;
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root=inflater.inflate(R.layout.activity_main, container, false);
        lst=root.findViewById(R.id.lst);
        arrayAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,arrayList);
        lst.setAdapter(arrayAdapter);
        btn = root.findViewById(R.id.btn1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str;
                txt = root.findViewById(R.id.txt2);
                Intent intent = new Intent(getContext(),Timer.class);
                intent.putExtra("Div",txt.getText().toString());
                txt=root.findViewById(R.id.txt1);
                intent.putExtra("Buildi",txt.getText().toString());
                intent.putExtra("num",tempadapter.getItem(pos));
                startActivity(intent);




            }
        });
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Buildings");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayAdapter.clear();
                for(DataSnapshot snap : dataSnapshot.getChildren())
                {
                    arrayAdapter.add(snap.getKey());
                    arrayAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        List plist = new ArrayList<>() ;

        room_arrayAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,llist);
        tempadapter = new ArrayAdapter<>(getContext(),android.R.layout.activity_list_item,plist);
        lst1=root.findViewById(R.id.lst1);
        lst1.setAdapter(room_arrayAdapter);
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView <?> parent, View view, final int position, long id) {
                Toast.makeText(getContext(), "Selected Building", Toast.LENGTH_SHORT).show();
                txt=root.findViewById(R.id.txt1);
                txt.setText(arrayAdapter.getItem(position));
                databaseReference.child(arrayAdapter.getItem(position)).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        room_arrayAdapter.clear();
                        for (DataSnapshot snap : dataSnapshot.getChildren())
                        {
                            for (DataSnapshot classroom : snap.getChildren())
                            {
                                if((Integer.parseInt(classroom.getValue().toString()))==0)
                                {
                                    room_arrayAdapter.add(arrayAdapter.getItem(position)+"-"+classroom.getKey());
                                    tempadapter.add(classroom.getKey());
                                    room_arrayAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



            }
        });
        lst1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Toast.makeText(getContext(), "Selected Room", Toast.LENGTH_SHORT).show();
                txt=root.findViewById(R.id.txt2);
                pos=position;
                txt.setText(room_arrayAdapter.getItem(position).toString());
            }
        });





        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(StartFragViewModel.class);




        // TODO: Use the ViewModel
    }


}
