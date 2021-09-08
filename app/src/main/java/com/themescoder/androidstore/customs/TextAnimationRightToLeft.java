package com.themescoder.androidstore.customs;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

public class TextAnimationRightToLeft {
    TextView textView;
    View view;
    Context context;
    int screenWidth, currentMsg;
    Animation.AnimationListener myAnimationListener;
    public TextAnimationRightToLeft(View view) {
        this.view=view;
    }

    public void animateTxt(int textViewId, final String msgArray[]){
        textView = (TextView) view.findViewById(textViewId);


        screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;


        // Set the first message
        textView.setText(msgArray[0]);
        // Measure the size of textView
        textView.measure(0, 0);
        // Get textView width
        int textWidth = textView.getMeasuredWidth();

        // Create the animation
        Animation animation = new TranslateAnimation(textWidth, screenWidth, 0, 0);
        animation.setDuration(5000);
        animation.setRepeatMode(Animation.RESTART);
        animation.setRepeatCount(Animation.INFINITE);

        myAnimationListener =new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

                if(++currentMsg >= msgArray.length){
                    currentMsg = 0;
                    // Set the next msg
                    textView.setText(msgArray[currentMsg]);
                    // Measure the size of textView // this is important
                    textView.measure(0, 0);
                    // Get textView width
                    int textWidth = textView.getMeasuredWidth();
                    // Create the animation
                    animation = new TranslateAnimation(-textWidth, screenWidth, 0, 0);

                    animation.setDuration(5000);
                    animation.setRepeatMode(Animation.RESTART);
                    animation.setRepeatCount(Animation.INFINITE);
                    animation.setAnimationListener(myAnimationListener);
                    textView.setAnimation(animation);
                }
            }
        };

        animation.setAnimationListener(myAnimationListener);

        textView.setAnimation(animation);
    }

}

