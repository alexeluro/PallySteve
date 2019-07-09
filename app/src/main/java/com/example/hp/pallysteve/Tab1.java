package com.example.hp.pallysteve;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tab1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tab1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab1 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerView recyclerView;
    //  Sample list of data required for this app
    ArrayList<String> companyList = new ArrayList<>();
    ArrayList<String> locationList = new ArrayList<>();
    ArrayList<String> salaryList = new ArrayList<>();
    ArrayList<String> roleList = new ArrayList<>();

    FirebaseDatabase database;
    DatabaseReference databaseRef;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Tab1() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Tab1 newInstance(String param1, String param2) {
        Tab1 fragment = new Tab1();
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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tab1, container, false);

        database = FirebaseDatabase.getInstance();
        databaseRef = database.getReference();
        recyclerView = rootView.findViewById(R.id.recycler_view);
        final CustomAdapter customAdapter = new CustomAdapter(getContext());


        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return rootView;
    }

    private void showData(DataSnapshot dataSnapshot) {
        CustomAdapter customAdapter;
        for(DataSnapshot data : dataSnapshot.getChildren()){
            UserInfo info = new UserInfo();
            info.setCompanyName(data.getValue(UserInfo.class).getCompanyName());
            info.setJobRole(data.getValue(UserInfo.class).getJobRole());
            info.setLocation(data.getValue(UserInfo.class).getLocation());
            info.setMinSalary(data.getValue(UserInfo.class).getMinSalary());
            info.setMaxSalary(data.getValue(UserInfo.class).getMaxSalary());

            companyList.add(data.getValue(UserInfo.class).getCompanyName());
            roleList.add(data.getValue(UserInfo.class).getJobRole());
            locationList.add(data.getValue(UserInfo.class).getLocation());
            salaryList.add((data.getValue(UserInfo.class).getMinSalary() +"-"+ data.getValue(UserInfo.class).getMaxSalary()));

        }
        CustomAdapter adapter = new CustomAdapter(getContext(), companyList, locationList, salaryList, roleList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
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

//    private void addToList() {
//        UserInfo info = new UserInfo();
//        companyList.add(info.getCompanyName());
//
//        locationList.add(info.getLocation());
//
//        salaryList.add(info.getSalary());
//
//        roleList.add(info.getJobRole());
//
//
//    }

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
