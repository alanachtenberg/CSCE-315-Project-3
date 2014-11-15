package edu.tamu.team1.project3;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;

public class GameFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    Context context;
    View view;
    GridView cubeGrid;
    int sizeX, sizeY;

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

    CubeView cube;
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
        cubeGrid.setNumColumns(sizeX);
        cubeGrid.setAdapter(new CubeAdapter(context));
    }

    public class CubeAdapter extends BaseAdapter {
        private Context context;

        ArrayList<CubeView> cubes;

        // Constructor
        public CubeAdapter(Context c){
            context = c;
            cubes = new ArrayList<CubeView>();

            for(int i = 0; i < sizeX*sizeY; i++) {
                cubes.add(new CubeView(context));
            }
        }

        @Override
        public int getCount() {
            return cubes.size();
        }

        @Override
        public Object getItem(int position) {
            return cubes.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View item = cubes.get(position);
//            GridView.LayoutParams params = new GridView.LayoutParams(
//                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//
//            params.
//
//
//
//            item.setLayoutParams(params);


            return item;
        }

    }


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
            cube.showLeft();
        }
    };

    View.OnClickListener topClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            cube.showTop();
        }
    };

    View.OnClickListener bottomClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            cube.showBottom();
        }
    };

    View.OnClickListener rightClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            cube.showRight();
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
