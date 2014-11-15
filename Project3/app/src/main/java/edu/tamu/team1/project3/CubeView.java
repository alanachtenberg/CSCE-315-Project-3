package edu.tamu.team1.project3;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class CubeView extends FrameLayout {
    private Context context;
    private ImageView face, left, top, right, bottom;
    final Animation left_to_right_shrink;
    final Animation left_to_right_grow;

//    final Animation right_to_left_shrink;
//    final Animation right_to_left_grow;

    public CubeView(Context context) {
        super(context);

        this.context = context;

        left_to_right_shrink = AnimationUtils.loadAnimation(context, R.anim.left_to_right_shrink);
        left_to_right_grow = AnimationUtils.loadAnimation(context, R.anim.left_to_right_grow);

//        right_to_left_shrink = AnimationUtils.loadAnimation(context, R.anim.right_to_left_shrink);
//        right_to_left_grow = AnimationUtils.loadAnimation(context, R.anim.right_to_left_grow);

        LayoutInflater.from(context).inflate(R.layout.cube_view, this);
        initialize();
    }

    private void initialize() {
        face = (ImageView) findViewById(R.id.face);
        left = (ImageView) findViewById(R.id.left);
        top = (ImageView) findViewById(R.id.top);
        right = (ImageView) findViewById(R.id.right);
        bottom = (ImageView) findViewById(R.id.bottom);
    }

    public void showLeft() {
        left.setImageResource(R.drawable.abc_ic_clear_mtrl_alpha);
        face.setImageResource(R.drawable.abc_ic_clear_mtrl_alpha);
        left.setVisibility(View.VISIBLE);
        left.startAnimation(left_to_right_grow);
        face.startAnimation(left_to_right_shrink);
        face.setVisibility(View.GONE);
    }


}
