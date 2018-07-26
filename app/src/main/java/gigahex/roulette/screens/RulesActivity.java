package gigahex.roulette.screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import gigahex.roulette.R;


public class RulesActivity extends AppCompatActivity {
    @OnClick({R.id.backRules})
    void onBackClick(View view) {
        Intent i = new Intent(RulesActivity.this, TargetActivity.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        ButterKnife.bind(this);
    }
}
