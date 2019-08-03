package com.example.hp.pallysteve;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AdminTab1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AdminTab1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminTab1 extends Fragment {

    EditText adminCom, adminRole, adminMinSal, adminMaxSal;
    Spinner adminLoc;
    Button uploadBtn;
    FirebaseDatabase database;
    DatabaseReference dataRef;
    UserInfo userdata;
    private String comp;
    private String role;
    private String loc;
    private Integer minSal;
    private Integer maxSal;

    private Dialog dialog;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AdminTab1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the
     * ''provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminTab1.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminTab1 newInstance(String param1, String param2) {
        AdminTab1 fragment = new AdminTab1();
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


        userdata = new UserInfo();
        database = FirebaseDatabase.getInstance();
        dataRef = database.getReference();//.child("UserInfo")
        View rootView = inflater.inflate(R.layout.fragment_admin_tab1, container, false);

        adminCom = rootView.findViewById(R.id.admin_company_name);
        adminRole = rootView.findViewById(R.id.admin_role_name);
        adminLoc = rootView.findViewById(R.id.admin_location_name);
        adminMinSal = rootView.findViewById(R.id.admin_salary_min_range);
        adminMaxSal = rootView.findViewById(R.id.admin_salary_max_range);
        uploadBtn = rootView.findViewById(R.id.upload_btn);


        ArrayAdapter spinnerAdapter = ArrayAdapter.
                createFromResource(getContext(), R.array.location_options, android.R.layout.simple_spinner_dropdown_item);
        adminLoc.setAdapter(spinnerAdapter);
        adminLoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch(adminLoc.getSelectedItem().toString().trim()){
                    case "Lagos":
                        loc = "Lagos";
                        adminLoc.setSelection(i);
                        return;
                    case "Ogun":
                        loc = "Ogun";
                        adminLoc.setSelection(i);
                        return;
                    case "Ibadan":
                        loc = "Ibadan";
                        adminLoc.setSelection(i);
                        return;
                    case "Abia":
                        loc = "Abia";
                        adminLoc.setSelection(i);
                        return;
                    default:
                        loc = "No location yet";
                        adminLoc.setSelection(i);
                        return;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    comp = adminCom.getText().toString().trim();
                    role = adminRole.getText().toString().trim();
//                    loc = adminLoc;

                    minSal = Integer.parseInt(adminMinSal.getText().toString().trim());
                    maxSal = Integer.parseInt(adminMaxSal.getText().toString().trim());

                    if(minSal > maxSal){
                        Toast.makeText(getContext(), "Minimum salary can't be greater than the Maximum value", Toast.LENGTH_SHORT).show();
                    }else{
                        userdata.setCompanyName(comp);
                        userdata.setJobRole(role);
                        userdata.setLocation(loc);
                        userdata.setMinSalary(minSal);
                        userdata.setMaxSalary(maxSal);
                    }

                }catch(Exception e) {
                    if (TextUtils.isEmpty(comp)) {
                        comp = "Unknown(check back later)";
                    }
                    if (TextUtils.isEmpty(role)) {
                        role = "Unknown(check back later)";
                    }
                    if (TextUtils.isEmpty(loc)) {
                        loc = "Unknown(check back later)";
                    }
                    if (minSal == null) {
                        minSal = 0;
                    }
                    if (maxSal == null){
                        maxSal = 0;
                    }
                }
                dataRef.push().setValue(userdata);

                adminCom.setText("");
                adminRole.setText("");
                adminMinSal.setText("");
                adminMaxSal.setText("");
                    Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
            }
        });

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_tab1, container, false);
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
