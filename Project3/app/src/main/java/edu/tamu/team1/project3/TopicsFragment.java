package edu.tamu.team1.project3;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashMap;

public class TopicsFragment extends Fragment {
    View view;
    ExpandableListView expListView;
    ExpandableListAdapter expListAdapter;
    String[] topics;
    HashMap<String,String[]> topicItems;

    private OnFragmentInteractionListener mListener;

    public static TopicsFragment newInstance() {
        TopicsFragment fragment = new TopicsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    // Required empty public constructor
    public TopicsFragment() {

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
        view = inflater.inflate(R.layout.fragment_topics, container, false);

        Resources res=getResources();//get access to resource
        topics =res.getStringArray(R.array.topics);
        topicItems = new HashMap<String, String[]>();
        String[] _itemList= res.getStringArray(R.array.fish);
        topicItems.put(topics[0],_itemList);
        _itemList= res.getStringArray(R.array.topic_2);
        topicItems.put(topics[1],_itemList);
        _itemList= res.getStringArray(R.array.topic_3);
        topicItems.put(topics[2],_itemList);

        expListView = (ExpandableListView) view.findViewById(R.id.topics_list_view);
        Context context=getActivity();
        expListAdapter = new ExpandableListAdapter(context, topics, topicItems);
        expListView.setAdapter(expListAdapter);

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
            Fragment fragment = GameFragment.newInstance(text);

            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    };
    //original non modified source here
    //http://www.androidhive.info/2013/07/android-expandable-list-view-tutorial/
    //modified to work with string and string array instead of list
    public class ExpandableListAdapter extends BaseExpandableListAdapter {

        private Context _context;
        private String[] _listDataHeader; // header titles
        // child data in format of header title, child title
        private HashMap<String, String[]> _listDataChild;

        public ExpandableListAdapter(Context context, String[] listDataHeader,
                                     HashMap<String, String[]> listChildData) {
            this._context = context;
            this._listDataHeader = listDataHeader;
            this._listDataChild = listChildData;
        }

        @Override
        public Object getChild(int groupPosition, int childPosititon) {
            return this._listDataChild.get(this._listDataHeader[groupPosition])[childPosititon];
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

            final String childText = (String) getChild(groupPosition, childPosition);

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.topic_item, null);
            }

            TextView txtListChild = (TextView) convertView
                    .findViewById(R.id.lblListItem);

            txtListChild.setText(childText);
            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return this._listDataChild.get(this._listDataHeader[groupPosition]).length;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return this._listDataHeader[groupPosition];
        }

        @Override
        public int getGroupCount() {
            return this._listDataHeader.length;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            String headerTitle = (String) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.topic, null);
            }

            TextView lblListHeader = (TextView) convertView
                    .findViewById(R.id.lblListHeader);
            lblListHeader.setTypeface(null, Typeface.BOLD);
            lblListHeader.setText(headerTitle);

            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}

