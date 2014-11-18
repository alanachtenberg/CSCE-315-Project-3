package edu.tamu.team1.project3;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class SettingsFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    View view;
    Button showSourcesButton;
    TextView sourcesText;

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_settings, container, false);
        showSourcesButton = (Button) view.findViewById(R.id.show_sources_button);//initialize button
        sourcesText = (TextView) view.findViewById(R.id.sources_text_view);//initialize source text view
        showSourcesButton.setOnClickListener(showButtonClick);//set click listener on button
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    //toggles visibility of sources text
    public View.OnClickListener showButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           if(sourcesText.getVisibility()==View.VISIBLE)//if text is visible
               sourcesText.setVisibility(View.GONE);//collapse visibility
           else
               sourcesText.setVisibility(View.VISIBLE);//otherwise set to visible
        }
    };

}