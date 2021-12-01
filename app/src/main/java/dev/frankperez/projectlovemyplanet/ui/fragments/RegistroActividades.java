package dev.frankperez.projectlovemyplanet.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nonnull;

import dev.frankperez.projectlovemyplanet.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistroActividades#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistroActividades extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegistroActividades() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistroActividades.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistroActividades newInstance(String param1, String param2) {
        RegistroActividades fragment = new RegistroActividades();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registro_actividades, container, false);
        Spinner spinner =(Spinner)view.findViewById(R.id.spinnerActividades);

        String colors[] = {"Limpieza Playa","Limpieza Parque"};
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, colors);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner.setAdapter(spinnerArrayAdapter);


        ((Button)view.findViewById(R.id.btnguardar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        try {
            mAuth = FirebaseAuth.getInstance();
            db = FirebaseDatabase.getInstance().getReference();
            cargarActividades();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return view;
        //inflater.inflate(R.layout.fragment_registro_actividades, container, false)
    }
    FirebaseAuth mAuth;
    DatabaseReference db;
    public void cargarActividades() {
        final List<Actividad> actividades = new ArrayList<>();
        db.child("actividades").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@Nonnull DataSnapshot dataSnapshot) {
                String x = "OK: ";
                if (dataSnapshot.exists()) {
                    x+= " LLEGO : ";
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String id = ds.getKey();
                        String nombre = ds.child("nombre").getValue().toString();
                        x+= "  nombre: " + nombre;
                        actividades.add(new Actividad(id, nombre));
                    }
                    //ArrayAdapter<Actividad> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, actividades);
                    //mSpinnerActividades.setAdapter(arrayAdapter);
                }
                Log.d("xxxxxx", x);
            }
            @Override
            public void onCancelled(@Nonnull DatabaseError error) {
            }
        });

    }
}