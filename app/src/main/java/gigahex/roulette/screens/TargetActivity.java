package gigahex.roulette.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import gigahex.roulette.R;


public class TargetActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);

        Button startGame = (Button) findViewById(R.id.buttonGame);
        startGame.setOnClickListener(buttonListener);
        Button btnRules = (Button) findViewById(R.id.buttonRules);
        btnRules.setOnClickListener(buttonListener);
        Button exitGame = (Button) findViewById(R.id.buttonExit);
        exitGame.setOnClickListener(buttonListener);
        Button resultGame = (Button) findViewById(R.id.buttonResult);
        resultGame.setOnClickListener(buttonListener);
    }

    View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.buttonGame:
                    Intent intent = new Intent(view.getContext(), GameActivity.class);
                    startActivity(intent);
                    break;
                case R.id.buttonRules:
                    Intent i = new Intent(TargetActivity.this, RulesActivity.class);
                    startActivity(i);
                    break;
                case R.id.buttonResult:
                    Intent intent1 = new Intent(TargetActivity.this, ResultActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.buttonExit:
                    finishAffinity();
                    break;
            }


        }
    };
}
