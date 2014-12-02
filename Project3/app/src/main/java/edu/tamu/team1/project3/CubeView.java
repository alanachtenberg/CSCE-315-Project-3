package edu.tamu.team1.project3;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CubeView extends FrameLayout implements Checkable {
    private Context context;
    private TextView face, left, top, right, bottom;
    private int leftId, topId, rightId, bottomId;
    private boolean selected;

    Animation left_to_right_shrink;
    Animation left_to_right_grow;
    Animation top_to_bottom_shrink;
    Animation top_to_bottom_grow;
    Animation bottom_to_top_shrink;
    Animation bottom_to_top_grow;
    Animation right_to_left_shrink;
    Animation right_to_left_grow;

    public CubeView(Context context) {
        super(context);

        this.context = context;

        LayoutInflater.from(context).inflate(R.layout.cube_view, this);
        initialize();
    }

    private void initialize() {
        face = (TextView) findViewById(R.id.face);
        left = (TextView) findViewById(R.id.left);
        top = (TextView) findViewById(R.id.top);
        right = (TextView) findViewById(R.id.right);
        bottom = (TextView) findViewById(R.id.bottom);

        left_to_right_shrink = AnimationUtils.loadAnimation(context, R.anim.left_to_right_shrink);
        left_to_right_grow = AnimationUtils.loadAnimation(context, R.anim.left_to_right_grow);
        top_to_bottom_shrink = AnimationUtils.loadAnimation(context, R.anim.top_to_bottom_shrink);
        top_to_bottom_grow = AnimationUtils.loadAnimation(context, R.anim.top_to_bottom_grow);
        bottom_to_top_shrink = AnimationUtils.loadAnimation(context, R.anim.bottom_to_top_shrink);
        bottom_to_top_grow = AnimationUtils.loadAnimation(context, R.anim.bottom_to_top_grow);
        right_to_left_shrink = AnimationUtils.loadAnimation(context, R.anim.right_to_left_shrink);
        right_to_left_grow = AnimationUtils.loadAnimation(context, R.anim.right_to_left_grow);
    }

    public void setLeftFace(int id) {
        leftId = id;
        left.setText(Integer.toString(id));
    }

    public void setTopFace(int id) {
        topId = id;
        top.setText(Integer.toString(id));
    }

    public void setRightFace(int id) {
        rightId = id;
        right.setText(Integer.toString(id));
    }

    public void setBottomFace(int id) {
        bottomId = id;
        bottom.setText(Integer.toString(id));
    }

    public void setLeftMatched() {
        left.setBackgroundColor(Color.parseColor("#CC000000"));
        left.setTextColor(Color.parseColor("#FFFFFF"));
        int numberFacesUnmatched = Integer.parseInt(face.getText().toString());
        face.setText(Integer.toString(numberFacesUnmatched - 1));
    }

    public void setTopMatched() {
        top.setBackgroundColor(Color.parseColor("#CC000000"));
        top.setTextColor(Color.parseColor("#FFFFFF"));
        int numberFacesUnmatched = Integer.parseInt(face.getText().toString());
        face.setText(Integer.toString(numberFacesUnmatched - 1));
    }

    public void setRightMatched() {
        right.setBackgroundColor(Color.parseColor("#CC000000"));
        right.setTextColor(Color.parseColor("#FFFFFF"));
        int numberFacesUnmatched = Integer.parseInt(face.getText().toString());
        face.setText(Integer.toString(numberFacesUnmatched - 1));
    }

    public void setBottomMatched() {
        bottom.setBackgroundColor(Color.parseColor("#CC000000"));
        bottom.setTextColor(Color.parseColor("#FFFFFF"));
        int numberFacesUnmatched = Integer.parseInt(face.getText().toString());
        face.setText(Integer.toString(numberFacesUnmatched - 1));
    }


    public int showLeft() {
        left.setVisibility(View.VISIBLE);
        left_to_right_grow.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                left.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        face.startAnimation(left_to_right_shrink);
        left.startAnimation(left_to_right_grow);

        return leftId;
    }

    public int showTop() {
        top.setVisibility(View.VISIBLE);
        top_to_bottom_grow.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                top.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        face.startAnimation(top_to_bottom_shrink);
        top.startAnimation(top_to_bottom_grow);

        return topId;
    }

    public int showBottom() {
        bottom.setVisibility(View.VISIBLE);
        bottom_to_top_grow.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                bottom.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        face.startAnimation(bottom_to_top_shrink);
        bottom.startAnimation(bottom_to_top_grow);

        return bottomId;
    }

    public int showRight() {
        right.setVisibility(View.VISIBLE);
        right_to_left_shrink.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                right.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        face.startAnimation(right_to_left_shrink);
        right.startAnimation(right_to_left_grow);

        return rightId;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    public void setChecked(boolean checked) {
        selected = checked;

        TypedValue lightValue = new TypedValue();
        TypedValue darkValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(R.attr.colorPrimaryLight, lightValue, true);
        int light = lightValue.data;
        theme.resolveAttribute(R.attr.colorPrimaryDark, darkValue, true);
        int dark = darkValue.data;


        if(selected) face.setBackgroundColor(dark);
        else face.setBackgroundColor(light);
    }

    @Override
    public boolean isChecked() {
        return selected;
    }

    @Override
    public void toggle() {
        setChecked(!selected);
    }


//Adapter for this object
//------------------------------------------------------------------------------
    public static class Adapter extends BaseAdapter {
        private Context context;

        ArrayList<CubeView> cubes;
        int selectedCount;

        // Constructor
        public Adapter(Context context, ArrayList<CubeView> cubes){
            this.context = context;
            this.cubes = cubes;
            selectedCount = 0;
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
            return cubes.get(position);
        }

        void select(int position) {
            CubeView selectedCube = (CubeView) getItem(position);
            selectedCube.toggle();
            if(selectedCube.isChecked()) selectedCount++;
            else selectedCount--;
        }

        int getSelectedCount() {
            return selectedCount;
        }

        ArrayList<CubeView> getSelectedItems() {
            ArrayList<CubeView> selectedItems = new ArrayList<CubeView>();

            for(CubeView cube : cubes) {
                if(cube.isChecked()) selectedItems.add(cube);
            }

            return selectedItems;
        }
    }
}
