package edu.tamu.team1.project3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    View view;
    Context context;

    Button showHighScoresButton;
    Button deleteHighScoresButton;
    Button showSourcesButton;
    Button aboutButton;

    Switch flingSwitch;
    Switch swipeSwitch;

    Spinner themeSpinner;
    Spinner topicSpinner;

    TextView sourcesText;

    boolean inhibitThemeSpinner;
    boolean inhibitTopicSpinner;

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
        context = getActivity();

        inhibitThemeSpinner = true;
        inhibitTopicSpinner = true;

        //switches
        flingSwitch = (Switch) view.findViewById(R.id.fling_switch);
        flingSwitch.setOnCheckedChangeListener(flingChange);
        swipeSwitch = (Switch) view.findViewById(R.id.swipe_switch);
        swipeSwitch.setOnCheckedChangeListener(swipeChange);

        //spinners
        themeSpinner = (Spinner) view.findViewById(R.id.theme_spinner);
        themeSpinner.setOnItemSelectedListener(themeSelected);
        topicSpinner = (Spinner) view.findViewById(R.id.topic_spinner);
        topicSpinner.setOnItemSelectedListener(topicSelected);

        try {
            String theme;
            String topic;
            Settings settings = Settings.deserialize();
            if(settings != null) {
                theme = settings.getTheme();
                if(theme == null || theme.length() == 0) {
                    theme = "Red";
                }

                topic = settings.getTopic();
                if(topic == null || topic.length() == 0) {
                    topic = "fish";
                }

                flingSwitch.setChecked(settings.isFling());
                swipeSwitch.setChecked(settings.isSwipe());
            }
            else {
                theme = "Red";
                topic = "fish";
            }

            if (theme.equals("Red")) {
                themeSpinner.setSelection(0);
            } else if (theme.equals("Green")) {
                themeSpinner.setSelection(1);
            } else if (theme.equals("Blue")) {
                themeSpinner.setSelection(2);
            }

            if (topic.equals("Fish")) {
                topicSpinner.setSelection(0);
            } else if (topic.equals("Mammals")) {
                topicSpinner.setSelection(1);
            } else if (topic.equals("Reptiles")) {
                topicSpinner.setSelection(2);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        //buttons
        showHighScoresButton = (Button) view.findViewById(R.id.show_high_scores_button);
        showHighScoresButton.setOnClickListener(showHighScoresClick);

        deleteHighScoresButton = (Button) view.findViewById(R.id.delete_high_scores_button);
        deleteHighScoresButton.setOnClickListener(deleteHighScoresClick);

        showSourcesButton = (Button) view.findViewById(R.id.show_sources_button);
        showSourcesButton.setOnClickListener(showSourcesClick);

        aboutButton = (Button) view.findViewById(R.id.about_button);
        aboutButton.setOnClickListener(aboutClick);

        sourcesText = (TextView) view.findViewById(R.id.sources_text_view);

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

//Click listeners for settings items
//------------------------------------------------------------------------------

    //switch listeners
    CompoundButton.OnCheckedChangeListener flingChange = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Settings.deserialize().setFling(isChecked);
        }
    };

    CompoundButton.OnCheckedChangeListener swipeChange = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Settings.deserialize().setSwipe(isChecked);
        }
    };


    //Spinner listeners
    AdapterView.OnItemSelectedListener themeSelected = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (inhibitThemeSpinner) {
                inhibitThemeSpinner = false;
            }
            else {

                String[] themes = context.getResources().getStringArray(R.array.themes);

                try {
                    Settings.deserialize().setTheme(themes[position]);
                }
                catch(Exception e) {
                    e.printStackTrace();
                }

                Intent intent = getActivity().getIntent();
                getActivity().overridePendingTransition(0, android.R.anim.fade_out);
                getActivity().finish();
                getActivity().overridePendingTransition(android.R.anim.fade_in, 0);
                startActivity(intent);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    AdapterView.OnItemSelectedListener topicSelected = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (inhibitTopicSpinner) {
                inhibitTopicSpinner = false;
            }
            else {
                String[] topics = context.getResources().getStringArray(R.array.topics);
                Settings.deserialize().setTopic(topics[position]);
                Toast.makeText(context, "Topic - " + topics[position], Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };



    //button listeners
    public View.OnClickListener showHighScoresClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(context, "Show High Scores", Toast.LENGTH_SHORT).show();
        }
    };

    //toggles visibility of sources text
    public View.OnClickListener deleteHighScoresClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(context, "Delete High Scores", Toast.LENGTH_SHORT).show();
        }
    };

    //toggles visibility of sources text
    public View.OnClickListener showSourcesClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(context, "Show Sources", Toast.LENGTH_SHORT).show();
//            if(sourcesText.getVisibility()==View.VISIBLE)//if text is visible
//                sourcesText.setVisibility(View.GONE);//collapse visibility
//            else
//                sourcesText.setVisibility(View.VISIBLE);//otherwise set to visible
        }
    };

    //toggles visibility of sources text
    public View.OnClickListener aboutClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(context, "About", Toast.LENGTH_SHORT).show();
        }
    };

}