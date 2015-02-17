package com.example.javier.materialdesignrevealtransition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;


public class MainActivity extends ActionBarActivity {

    RelativeLayout layoutToShow;
    Button buttonReveal, buttonImage;
    int[] viewCoords = new int[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutToShow = (RelativeLayout) findViewById(R.id.layoutToShow);
        buttonReveal = (Button) findViewById(R.id.buttonReveal);
        buttonImage = (Button) findViewById(R.id.buttonImage);

        buttonReveal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutToShow.getVisibility() == View.VISIBLE) {
                    if (Build.VERSION.SDK_INT >= 21) {
                        hideView(layoutToShow);
                    } else {
                        hideViewPreLollipop(layoutToShow);
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= 21) {
                        showView(layoutToShow);
                    } else {
                        showViewPreLollipop(layoutToShow);
                    }
                }
            }
        });

        buttonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Clicked", "Clicked");
            }
        });
        buttonImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        viewCoords[0] = (int) event.getX();
                        viewCoords[1] = (int) event.getY();
                        if (layoutToShow.getVisibility() == View.VISIBLE) {
                            hideViewOnTouch(layoutToShow, viewCoords[0], viewCoords[1]);
                        } else {
                            showViewOnTouch(layoutToShow, viewCoords[0], viewCoords[1]);
                        }
                        break;
                }
                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showView(View view) {

        // get the center for the clipping circle
        int cx = (view.getLeft() + view.getRight()) / 2;
        int cy = (view.getTop() + view.getBottom()) / 2;

        // get the final radius for the clipping circle
        int finalRadius = Math.max(view.getWidth(), view.getHeight());

        if (Build.VERSION.SDK_INT >= 21) {

            // create the animator for this view (the start radius is zero)
            Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);

            // make the view visible and start the animation
            view.setVisibility(View.VISIBLE);
            anim.start();
        }
    }

    public void showViewOnTouch(View view, int cx, int cy) {


        // get the final radius for the clipping circle
        int finalRadius = Math.max(view.getWidth(), view.getHeight());

        if (Build.VERSION.SDK_INT >= 21) {

            // create the animator for this view (the start radius is zero)
            Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
            anim.setInterpolator(new AccelerateInterpolator(1));
            anim.setDuration(500);

            // make the view visible and start the animation
            view.setVisibility(View.VISIBLE);
            anim.start();
        }
    }

    public void hideViewOnTouch(final View view, int cx, int cy) {

        // get the initial radius for the clipping circle
        int initialRadius = view.getWidth();

        if (Build.VERSION.SDK_INT >= 21) {

            // create the animation (the final radius is zero)
            Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, 0);

            // make the view invisible when the animation is done
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    view.setVisibility(View.INVISIBLE);
                }
            });

            anim.setInterpolator(new AccelerateInterpolator(1));
            anim.setDuration(500);
            anim.setStartDelay(100);

            // start the animation
            anim.start();
        }
    }


    public void hideView(final View view) {

        // get the center for the clipping circle
        int cx = (view.getLeft() + view.getRight()) / 2;
        int cy = (view.getTop() + view.getBottom()) / 2;

        // get the initial radius for the clipping circle
        int initialRadius = view.getWidth();

        if (Build.VERSION.SDK_INT >= 21) {

            // create the animation (the final radius is zero)
            Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, 0);

            // make the view invisible when the animation is done
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    view.setVisibility(View.INVISIBLE);
                }
            });

            anim.setInterpolator(new AccelerateInterpolator(1));
            anim.setDuration(500);

            // start the animation
            anim.start();
        }
    }

    public void hideViewPreLollipop(final View view) {

        // Animation for pre-Lollipop Devices
        Animation slide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        view.startAnimation(slide);
        view.setVisibility(View.INVISIBLE);
    }

    public void showViewPreLollipop(final View view) {

        // Animation for pre-Lollipop Devices
        Animation slide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        view.startAnimation(slide);
        view.setVisibility(View.VISIBLE);
    }
}

