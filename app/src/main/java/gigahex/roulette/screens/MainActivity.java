package gigahex.roulette.screens;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import gigahex.roulette.R;



public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        EasySplashScreen config = new EasySplashScreen(MainActivity.this)
                .withFullScreen()
                .withTargetActivity(TargetActivity.class)
                .withSplashTimeOut(4000)
                .withFooterText("Copyright 2018")
                .withLogo(R.drawable.roulette_main)
                .withBeforeLogoText("Рулетка");


        //set your own animations
        myCustomTextViewAnimation(config.getFooterTextView());

        //customize all TextViews
        Typeface pacificoFont = Typeface.createFromAsset(getAssets(), "Pacifico.ttf");
        Typeface proItalicFont = Typeface.createFromAsset(getAssets(), "Guardian Pro Italic.ttf");
        config.getBeforeLogoTextView().setTypeface(pacificoFont);
        config.getFooterTextView().setTypeface(proItalicFont);

        config.getBeforeLogoTextView().setTextColor(Color.WHITE);
        config.getBeforeLogoTextView().setTextSize(40f);
        config.getFooterTextView().setTextColor(Color.WHITE);
        //create the view
        View easySplashScreenView = config.create();

        setContentView(easySplashScreenView);
    }

    private void myCustomTextViewAnimation(TextView tv){
        Animation animation=new TranslateAnimation(0,0,480,0);
        animation.setDuration(1200);
        tv.startAnimation(animation);
    }
}
