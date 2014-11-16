package edu.tamu.team1.project3;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;

public class GameFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    Context context;
    View view;
    GridView cubeGrid;
    int sizeX, sizeY;
    CubeView cube;

    public static GameFragment newInstance() {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    public GameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = getActivity();
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_game, container, false);

        cubeGrid = (GridView) view.findViewById(R.id.cube_grid);
        populateGrid();
        setupButtons();

        return view;
    }

//ListAdapter for grid and multiselect callbacks
//------------------------------------------------------------------------------
    void populateGrid() {
        sizeX = 4;
        sizeY = 4;

        ArrayList<CubeView> cubes = new ArrayList<CubeView>();

        for(int i = 0; i < sizeX*sizeY; i++) {
            cubes.add(new CubeView(context));
        }

        cubeGrid.setNumColumns(sizeX);
        cubeGrid.setAdapter(new CubeView.Adapter(context, cubes));
        cubeGrid.setOnItemClickListener(itemClick);

        cube = (CubeView) cubeGrid.getAdapter().getItem(0);
    }

    AdapterView.OnItemClickListener itemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            CubeView.Adapter adapter = (CubeView.Adapter) cubeGrid.getAdapter();
            if(adapter.getSelectedCount() < 2) {
                adapter.select(position);
            }
            else if(adapter.getSelectedCount() == 2) {
                if(((CubeView)adapter.getItem(position)).isChecked()) {
                    adapter.select(position);
                }
            }
        }
    };


//click listeners for click to reveal
//------------------------------------------------------------------------------
    void setupButtons() {
        Button bLeft = (Button) view.findViewById(R.id.bLeft);
        bLeft.setOnClickListener(leftClick);
        Button bTop = (Button) view.findViewById(R.id.bTop);
        bTop.setOnClickListener(topClick);
        Button bBottom = (Button) view.findViewById(R.id.bBottom);
        bBottom.setOnClickListener(bottomClick);
        Button bRight = (Button) view.findViewById(R.id.bRight);
        bRight.setOnClickListener(rightClick);
    }

    View.OnClickListener leftClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ArrayList<CubeView> selectedCubes = ((CubeView.Adapter)cubeGrid.getAdapter()).getSelectedItems();

            for(CubeView cube : selectedCubes) {
                cube.showLeft();
            }
        }
    };

    View.OnClickListener topClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ArrayList<CubeView> selectedCubes = ((CubeView.Adapter)cubeGrid.getAdapter()).getSelectedItems();

            for(CubeView cube : selectedCubes) {
                cube.showTop();
            }
        }
    };

    View.OnClickListener bottomClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ArrayList<CubeView> selectedCubes = ((CubeView.Adapter)cubeGrid.getAdapter()).getSelectedItems();

            for(CubeView cube : selectedCubes) {
                cube.showBottom();
            }
        }
    };

    View.OnClickListener rightClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ArrayList<CubeView> selectedCubes = ((CubeView.Adapter)cubeGrid.getAdapter()).getSelectedItems();

            for(CubeView cube : selectedCubes) {
                cube.showRight();
            }
        }
    };


//Gesture listeners for swipe to reveal
//------------------------------------------------------------------------------


//Accelerometer listeners for fling to reveal
//------------------------------------------------------------------------------


//misc fragment lifecycle callbacks
//------------------------------------------------------------------------------
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
}
