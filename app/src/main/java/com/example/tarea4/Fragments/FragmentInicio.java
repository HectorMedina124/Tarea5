package com.example.tarea4.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.tarea4.MainActivity;
import com.example.tarea4.PantallaInicio;
import com.example.tarea4.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentInicio.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentInicio#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentInicio extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button em;
    private Button pass;
    private Button delete;
    private Button singOut;
    private View view;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private OnFragmentInteractionListener mListener;

    public FragmentInicio() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentInicio.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentInicio newInstance(String param1, String param2) {
        FragmentInicio fragment = new FragmentInicio();

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_fragment_inicio, container, false);
        em=view.findViewById(R.id.btnEmail);
        pass=view.findViewById(R.id.btnContra);
        delete=view.findViewById(R.id.btnEliminar);
        singOut=view.findViewById(R.id.btnCerrarSesion);
        mAuth=FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();
        em.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment nuevo = new CambiarEmail();
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.Pantallla, nuevo,"CCEMAIL").commit();
                getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentByTag("Inicio")).commit();
            }
        });
        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment nuevo = new CambiarContra();
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.Pantallla, nuevo,"CCCONTRASEÑA").commit();
                getFragmentManager(). beginTransaction().remove(getFragmentManager().findFragmentByTag("Inicio")).commit();

            }
        });
         delete.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                 builder.setTitle("Confirma");
                 builder.setMessage("¿Estas seguro de eliminar la cuenta?");
                 builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {

                     public void onClick(DialogInterface dialog, int which) {
                         currentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                             @Override
                             public void onComplete(@NonNull Task<Void> task) {
                                 if(task.isSuccessful()){
                                     Intent intent = new Intent(getActivity(), MainActivity.class);
                                     startActivity(intent);
                                     getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentByTag("Inicio")).commit();
                                     Toast.makeText(getActivity(), "Cuenta eliminada :(",
                                             Toast.LENGTH_SHORT).show();
                                     getActivity().finish();



                                 }
                                 else{
                                     Toast.makeText(getActivity(), "Fallo en la eliminacion de la cuenta",
                                             Toast.LENGTH_SHORT).show();
                                 }
                             }
                         });

                         dialog.dismiss();
                     }
                 });

                 builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                     @Override
                     public void onClick(DialogInterface dialog, int which) {

                         // Do nothing
                         dialog.dismiss();
                     }
                 });

                 AlertDialog alert = builder.create();
                 alert.show();

             }
         });
         singOut.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 mAuth.signOut();
                 Intent intent = new Intent(getActivity(), MainActivity.class);
                 startActivity(intent);
                 getActivity().finish();
             }
         });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
