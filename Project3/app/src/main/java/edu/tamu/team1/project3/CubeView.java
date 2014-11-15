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

    final Animation right_to_left_shrink;
    final Animation right_to_left_grow;

    final Animation bottom_to_top_shrink;
    final Animation bottom_to_top_grow;

    final Animation top_to_bottom_shrink;
    final Animation top_to_bottom_grow;

    public CubeView(Context context) {
        super(context);

        this.context = context;

        left_to_right_shrink = AnimationUtils.loadAnimation(context, R.anim.left_to_right_shrink);
        left_to_right_grow = AnimationUtils.loadAnimation(context, R.anim.left_to_right_grow);

        right_to_left_shrink = AnimationUtils.loadAnimation(context, R.anim.right_to_left_shrink);
        right_to_left_grow = AnimationUtils.loadAnimation(context, R.anim.right_to_left_grow);

        bottom_to_top_shrink = AnimationUtils.loadAnimation(context, R.anim.bottom_to_top_shrink);
        bottom_to_top_grow = AnimationUtils.loadAnimation(context, R.anim.bottom_to_top_grow);

        top_to_bottom_shrink = AnimationUtils.loadAnimation(context, R.anim.top_to_bottom_shrink);
        top_to_bottom_grow = AnimationUtils.loadAnimation(context, R.anim.top_to_bottom_grow);

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

    public ImageView showLeft() {
        left.setVisibility(View.VISIBLE);
        left.startAnimation(left_to_right_grow);

        left_to_right_shrink.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                left.setVisibility(View.GONE);
                face.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        face.startAnimation(left_to_right_shrink);



        return left;
    }

    public ImageView showRight() {
        right.setVisibility(View.VISIBLE);
        right.startAnimation(right_to_left_grow);
        face.startAnimation(right_to_left_shrink);

        right_to_left_shrink.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                right.setVisibility(View.GONE);
                face.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        face.setVisibility(View.GONE);

        return right;
    }

    public ImageView showTop() {
        top.setVisibility(View.VISIBLE);
        top.startAnimation(top_to_bottom_grow);
        face.startAnimation(top_to_bottom_shrink);

        top_to_bottom_shrink.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                top.setVisibility(View.GONE);
                face.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        face.setVisibility(View.GONE);

        return top;
    }

    public ImageView showBottom() {
        bottom.setVisibility(View.VISIBLE);
        bottom.startAnimation(bottom_to_top_grow);
        face.startAnimation(bottom_to_top_shrink);

        bottom_to_top_shrink.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                bottom.setVisibility(View.GONE);
                face.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        face.setVisibility(View.GONE);

        return bottom;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
