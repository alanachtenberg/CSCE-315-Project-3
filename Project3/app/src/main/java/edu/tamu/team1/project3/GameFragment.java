package edu.tamu.team1.project3;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class GameFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    Context context;
    View view;
    GridView cubeGrid;
    Button bLeft, bRight, bBottom, bTop;
    TextView movesTakenTV, timeTakenTV;

    GestureDetectorCompat detector; //for detecting touch gestures
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
        setupButtons();
        populateGrid();
        detector= new GestureDetectorCompat(view.getContext(), new MyGestureListener());
        cubeGrid.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                detector.onTouchEvent(event);
                return false;//return false to allow
            }
        });
//        if(Settings.get)
//        setupFling();

        return view;
    }

//Guesture listener for cube view
//------------------------------------------------------------------------------
    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures";

        @Override
        public boolean onDown(MotionEvent event) {
            Log.d(DEBUG_TAG, "onDown: " + event.toString());
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            Log.d(DEBUG_TAG, "onFling: " + event1.toString()+event2.toString());
            return true;
        }
    }


//ListAdapter for grid and multiselect callbacks
//------------------------------------------------------------------------------
    void populateGrid() {
        matchesRemaining = sizeX*sizeY*2;
        movesTaken = 0;

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

    public static final int LEFT_FACE = 0;
    public static final int TOP_FACE = 1;
    public static final int RIGHT_FACE = 2;
    public static final int BOTTOM_FACE = 3;

    private int matchesRemaining;
    private int movesTaken;
    private int timeTaken;

    void checkForMatch(CubeView c1, CubeView c2, int side) {
        int item1 = -1;
        int item2 = -1;
        movesTaken++;
        movesTakenTV.setText(Integer.toString(movesTaken));

        switch(side) {
            case LEFT_FACE:
                item1 = c1.showLeft();
                item2 = c2.showLeft();
                break;
            case TOP_FACE:
                item1 = c1.showTop();
                item2 = c2.showTop();
                break;
            case RIGHT_FACE:
                item1 = c1.showRight();
                item2 = c2.showRight();
                break;
            case BOTTOM_FACE:
                item1 = c1.showBottom();
                item2 = c2.showBottom();
                break;
        }

        if(item1 != -1 && item2 != -1 && item1 == item2) {
            switch(side) {
                case LEFT_FACE:
                    c1.setLeftMatched();
                    c2.setLeftMatched();
                    break;
                case TOP_FACE:
                    c1.setTopMatched();
                    c2.setTopMatched();
                    break;
                case RIGHT_FACE:
                    c1.setRightMatched();
                    c2.setRightMatched();
                    break;
                case BOTTOM_FACE:
                    c1.setBottomMatched();
                    c2.setBottomMatched();
                    break;
            }

            //showPopup();

            matchesRemaining--;

            if(matchesRemaining == 0) {
                Toast.makeText(context, "Congratulations, you have won the game!", Toast.LENGTH_SHORT).show();
            }
        }
    }

//click listeners for click to reveal
//------------------------------------------------------------------------------
    void setupButtons() {
        bLeft = (Button) view.findViewById(R.id.bLeft);
        bLeft.setOnClickListener(leftClick);
        bTop = (Button) view.findViewById(R.id.bTop);
        bTop.setOnClickListener(topClick);
        bBottom = (Button) view.findViewById(R.id.bBottom);
        bBottom.setOnClickListener(bottomClick);
        bRight = (Button) view.findViewById(R.id.bRight);
        bRight.setOnClickListener(rightClick);

        movesTakenTV = (TextView) view.findViewById(R.id.movesTaken);
        timeTakenTV = (TextView) view.findViewById(R.id.timeTaken);

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                timeTaken++; //keep track of time in seconds
                                int sec = timeTaken % 60;
                                int min = (int)(timeTaken / 60);

                                timeTakenTV.setText(String.format("%d:%02d", min, sec));
                            }
                        });
                    }
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        t.start();
    }

    View.OnClickListener leftClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ArrayList<CubeView> selectedCubes = ((CubeView.Adapter)cubeGrid.getAdapter()).getSelectedItems();
            // only flip if two cubes are selected
            if(((CubeView.Adapter)cubeGrid.getAdapter()).getSelectedCount() == 2) {
                checkForMatch(selectedCubes.get(0), selectedCubes.get(1), LEFT_FACE);
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
                checkForMatch(selectedCubes.get(0), selectedCubes.get(1), TOP_FACE);

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
                checkForMatch(selectedCubes.get(0), selectedCubes.get(1), BOTTOM_FACE);

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
                checkForMatch(selectedCubes.get(0), selectedCubes.get(1), RIGHT_FACE);

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
