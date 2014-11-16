package edu.tamu.team1.project3;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

public class GameSetupFragment extends Fragment {
    Button button;
    View view;

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
        view = inflater.inflate(R.layout.fragment_game_setup, container, false);
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
            Spinner spinner = (Spinner) view.findViewById(R.id.game_size_spinner);
            String text = spinner.getSelectedItem().toString();

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            Fragment fragment = GameFragment.newInstance();

            String[] args = text.split("x");
            Bundle bundle = new Bundle();
            bundle.putInt("SIZE_X", Integer.parseInt(args[0]));
            bundle.putInt("SIZE_Y", Integer.parseInt(args[1]));
            fragment.setArguments(bundle);

            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    };

}
