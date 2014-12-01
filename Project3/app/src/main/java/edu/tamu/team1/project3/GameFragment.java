package edu.tamu.team1.project3;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class GameFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    Context context;
    View view;
    GridView cubeGrid;
    int sizeX, sizeY;
    CubeView cube;

    public static GameFragment newInstance(String dimen) {
        GameFragment fragment = new GameFragment();
        String[] args = dimen.split("x");
        Bundle bundle = new Bundle();
        bundle.putInt("SIZE_X", Integer.parseInt(args[0]));
        bundle.putInt("SIZE_Y", Integer.parseInt(args[1]));
        fragment.setArguments(bundle);
        return fragment;
    }
    public GameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle args = getArguments();
            sizeX = args.getInt("SIZE_X", 4);
            sizeY = args.getInt("SIZE_Y", 4);
        }
        else {
            sizeX = 4;
            sizeY = 4;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_game, container, false);

        //navigate back to main menu when back button is pressed
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //only listen for up actions
                if (event.getAction()!=KeyEvent.ACTION_UP)
                    return true;

                //prompt user to return if they hit back button
                if(keyCode == KeyEvent.KEYCODE_BACK) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Exit Game?")
                            .setMessage("Are you sure you want to return the the main menu? Your progress will be lost.")
                            .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    getActivity().getSupportFragmentManager()
                                            .popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .create().show();

                    return true;
                }
                else return false;
            }
        });

        cubeGrid = (GridView) view.findViewById(R.id.cube_grid);
        populateGrid();
        setupButtons();
//        if(Settings.get)
//        setupFling();

        return view;
    }

//ListAdapter for grid and multiselect callbacks
//------------------------------------------------------------------------------
    void populateGrid() {
        ArrayList<CubeView> cubes = new ArrayList<CubeView>();
        ArrayList<ArrayList<Integer>> faceElements = new ArrayList<ArrayList<Integer>>();

        //setup large pool of all available elements for the sides of the cube
        for(int j = 0; j < 4; j++) {
            faceElements.add(new ArrayList<Integer>());
            for (int i = 0; i < (sizeX * sizeY) / 2; i++) {
                faceElements.get(j).add(i);
                faceElements.get(j).add(i);
//                Log.i("ITEM NUMBER", "" + i);
            }
        }

        //create all the cubes for the game
        for (int i = 0; i < sizeX * sizeY; i++) {
            cubes.add(new CubeView(context));
        }

        //create cubes and pull a random element from the pool to set as each face
        Random random = new Random(Calendar.getInstance().getTimeInMillis());

        for(int j = 0; j < 4; j++) {
            for (int i = 0; i < sizeX * sizeY; i++) {
                //the cube to edit
                CubeView cube = cubes.get(i);

                //get random item from list and remove it from list
                int pos = random.nextInt(faceElements.get(j).size());
                int randomItem = faceElements.get(j).get(pos);
                Log.i("RANDOM ITEM", randomItem + "");
                faceElements.get(j).remove(pos);
                switch (j) {
                    case 0:
                        cube.setLeftFace(randomItem);
                        break;
                    case 1:
                        cube.setTopFace(randomItem);
                        break;
                    case 2:
                        cube.setRightFace(randomItem);
                        break;
                    case 3:
                        cube.setBottomFace(randomItem);
                        break;
                }
            }
        }

        cubeGrid.setNumColumns(sizeX);
        cubeGrid.setAdapter(new CubeView.Adapter(context, cubes));
        cubeGrid.setOnItemClickListener(itemClick);

        cube = (CubeView) cubeGrid.getAdapter().getItem(0);
    }

    // function for selecting two cubes
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

    void selectItem(int position) {

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
            ArrayList<CubeView> selectedCubes = ((CubeView.Adapter)cubeGrid.getAdapter()).getSelectedItems();
            // only flip if two cubes are selected
            if(((CubeView.Adapter)cubeGrid.getAdapter()).getSelectedCount() == 2) {
                for (CubeView cube : selectedCubes) {
                    cube.showLeft();
                }
            }
            else {
                Toast.makeText(context, "Must select two cubes to reveal", Toast.LENGTH_SHORT).show();
            }
        }
    };

    View.OnClickListener topClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ArrayList<CubeView> selectedCubes = ((CubeView.Adapter)cubeGrid.getAdapter()).getSelectedItems();
            // only flip if two cubes are selected
            if(((CubeView.Adapter)cubeGrid.getAdapter()).getSelectedCount() == 2) {
                for (CubeView cube : selectedCubes) {
                    cube.showTop();
                }
            }
            else {
                Toast.makeText(context, "Must select two cubes to reveal", Toast.LENGTH_SHORT).show();
            }
        }
    };

    View.OnClickListener bottomClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ArrayList<CubeView> selectedCubes = ((CubeView.Adapter)cubeGrid.getAdapter()).getSelectedItems();
            // only flip if two cubes are selected
            if(((CubeView.Adapter)cubeGrid.getAdapter()).getSelectedCount() == 2) {
                for (CubeView cube : selectedCubes) {
                    cube.showBottom();
                }
            }
            else {
                Toast.makeText(context, "Must select two cubes to reveal", Toast.LENGTH_SHORT).show();
            }
        }
    };

    View.OnClickListener rightClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ArrayList<CubeView> selectedCubes = ((CubeView.Adapter)cubeGrid.getAdapter()).getSelectedItems();
            // only flip if two cubes are selected
            if(((CubeView.Adapter)cubeGrid.getAdapter()).getSelectedCount() == 2) {
                for (CubeView cube : selectedCubes) {
                    cube.showRight();
                }
            }
            else {
                Toast.makeText(context, "Must select two cubes to reveal", Toast.LENGTH_SHORT).show();
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
