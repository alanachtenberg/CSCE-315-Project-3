package edu.tamu.team1.project3;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class GameSetupFragment extends Fragment {
    Button button;

    private OnFragmentInteractionListener mListener;

    public static GameSetupFragment newInstance() {
        GameSetupFragment fragment = new GameSetupFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    // Required empty public constructor
    public GameSetupFragment() {

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
        View view = inflater.inflate(R.layout.fragment_game_setup, container, false);
        button = (Button) view.findViewById(R.id.start_game_button);
        button.setOnClickListener(gameButtonClick);
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

    public View.OnClickListener gameButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mListener.onFragmentInteraction("4");
        }
    };

}
