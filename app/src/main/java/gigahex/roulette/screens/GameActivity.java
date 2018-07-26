package gigahex.roulette.screens;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brouding.simpledialog.SimpleDialog;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gigahex.roulette.R;
import gigahex.roulette.database.ResultPoints;
import co.ceryle.radiorealbutton.RadioRealButton;
import co.ceryle.radiorealbutton.RadioRealButtonGroup;


public class GameActivity extends AppCompatActivity {
    final static int BET_BLACK = 0;
    final static int BET_RED = 1;
    final static int BET_ODD = 2;
    final static int BET_EVEN = 3;
    final static int BET_STRAIT = 4;
    private Button spin;
    Button saveButton;
    private CardView roulette;
    private ImageView imageRoulette;
    private TextView textViewNumRol;
    private TextView textViewColorNum;
    private TextView textViewParityNum;
    private RadioRealButtonGroup radioRealButtonGroup;
    private EditText textEditBet, textEditBetNum;
    private TextView textViewDeposit;
    private Button buttonRules;
    private int profit = 0;
    private int rate = 100;
    private int deposit = 1000;
    int index_roulette = 0;
    int betNum;
    int betType;
    float oldPosition= 0;
    int number_roulette;
    float truePosition;
    float randPosition;


    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;

    @BindView(R.id.deposit)
    TextView depositTextView;

    @BindView(R.id.textViewRate)
    TextView rateTextView;

    @OnClick({R.id.buttonIncrease, R.id.buttonDecrease})
    void onClickChangeRate(View view) {
        switch (view.getId()) {
            case R.id.buttonIncrease:
                rate +=100;
                setTextRate(rate);
                break;
            case R.id.buttonDecrease:
                rate-=100;
                if(rate<=0){
                    rate =100;
                }
                setTextRate(rate);
                break;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);

        setTextRate(rate);
        depositTextView.setText("Ваш депозит:" + deposit);

        spin = (Button) findViewById(R.id.btnStart);
        roulette = (CardView) findViewById(R.id.roulette);

        imageRoulette = (ImageView) findViewById(R.id.imageRoulette);
        radioRealButtonGroup = (RadioRealButtonGroup) findViewById(R.id.radioRealButtonGroup);

        buttonRules = (Button) findViewById(R.id.back);
        buttonRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameActivity.this, TargetActivity.class);
                startActivity(intent);

            }
        });
        radioRealButtonGroup.setOnClickedButtonListener(new RadioRealButtonGroup.OnClickedButtonListener() {
            @Override
            public void onClickedButton(RadioRealButton button, int position) {
                switch (position){
                    case 0:
                        betType = BET_BLACK;
                        break;
                    case 1:
                        betType = BET_RED;
                        break;
                    case 2:
                        betType = BET_ODD;
                        break;
                    case 3:
                        betType = BET_EVEN;
                        break;
                    case 4:
                        betType = BET_STRAIT;
                        break;
                }
            }
        });
        saveButton = (Button) findViewById(R.id.save);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(getBaseContext());

                View view = layoutInflater.inflate(R.layout.dialog, null);

                final EditText nameBox = (EditText) view.findViewById(R.id.name);
                SimpleDialog mCustomDialog = new SimpleDialog.Builder(GameActivity.this)
                        .setCustomView(view)
                        .setBtnConfirmText("OK")
                        .setBtnCancelText("Cancel", false)
                        .setBtnCancelTextColor("#777777")

                        .setBtnConfirmTextColor("#de413e")
                        .setTitle("Сохранение результата")
                        .setBtnConfirmTextSizeDp(15)
                        .onConfirm(new SimpleDialog.BtnCallback() {
                            @Override
                            public void onClick(@NonNull SimpleDialog dialog, @NonNull SimpleDialog.BtnAction which) {
                                String name = nameBox.getText().toString();
                                ContentResolver contentResolver = GameActivity.this.getContentResolver();
                                ContentValues values = new ContentValues();
                                values.put(ResultPoints.Columns.COLUMN_NAME, name);
                                values.put(ResultPoints.Columns.COLUMN_POINTS, deposit);
                                Uri uri = contentResolver.insert(ResultPoints.CONTENT_URI, values);
                            }
                        })
                        .show();
            }
        });

        spin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(betType != BET_STRAIT){
                    if(rate <=deposit){
                        spinRoulette();
                    }
                    else{
                        Toast.makeText(GameActivity.this, "Ставка не может быть больше депозита!", Toast.LENGTH_SHORT).show();
                    }
                }

                else{
                    betNum = Integer.parseInt(String.valueOf(textEditBetNum.getText()));
                    if(betNum<36){
                        spinRoulette();
                    }
                    else {
                        Toast.makeText(GameActivity.this, "Нет такого числа на рулетке!", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
    }

    private void spinRoulette() {
        int SLOTS_COUNT = 38;
        float corner = (float) 360 / SLOTS_COUNT;
        int randNum = new Random().nextInt(SLOTS_COUNT);
        index_roulette = (index_roulette + randNum)%SLOTS_COUNT;
        randPosition = ((corner * randNum ) + oldPosition)%360;
        int MIN = 5;
        int MAX = 9;
        long TIME_IN_WHEEL = 1000;
        int randRotation = MIN + new Random().nextInt(MAX - MIN);
        truePosition = (float) (randRotation * 360 + randPosition);
        long totalTime = (long) (TIME_IN_WHEEL * randRotation +(TIME_IN_WHEEL / 360) * randPosition);
        ObjectAnimator animator = ObjectAnimator
                .ofFloat(roulette, "rotation", 0f, (float) truePosition);
        animator.setDuration(totalTime);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                spin.setEnabled(false);
                radioRealButtonGroup.setEnabled(false);

                linearLayout.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onAnimationEnd(Animator animator) {
                oldPosition = randPosition;
                checkWin(index_roulette);
                spin.setEnabled(true);
                radioRealButtonGroup.setEnabled(true);
                linearLayout.setVisibility(View.VISIBLE);
                }
            @Override
            public void onAnimationCancel(Animator animator) {}
            @Override
            public void onAnimationRepeat(Animator animator) {}
        });
        animator.start();
    }
    void checkWin(int index_roulette){
        int[] numbers_roulette = {00, 1, 13, 36, 24, 3, 15, 34, 22, 5, 17, 32, 20, 7, 11, 30, 26, 9, 28,
                0, 2, 14, 35, 23, 4, 16, 33, 21, 6, 18, 31, 19, 8, 12, 29, 25,
                10, 27};
        int[] colors_number = {3, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1,
                3, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0,
                1, 0};
        int[] parity_number = {3, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 1,
                3, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 1,
                0, 1};
        number_roulette = numbers_roulette[index_roulette];

        switch (betType) {
            case BET_BLACK:
                if (colors_number[index_roulette] == 1){
                    deposit+= rate;
                    Toast.makeText(GameActivity.this, "Выигрыш: "+ rate, Toast.LENGTH_SHORT).show();
                }
                else {
                    deposit-= rate;
                    Toast.makeText(GameActivity.this, "Проиграли: "+ rate, Toast.LENGTH_SHORT).show();
                }
                break;
            case BET_RED:
                if (colors_number[index_roulette] == 0){
                    deposit+= rate;
                    Toast.makeText(GameActivity.this, "Выигрыш: "+ rate, Toast.LENGTH_SHORT).show();
                }
                else {
                    deposit-= rate;
                    Toast.makeText(GameActivity.this, "Проиграли: "+ rate, Toast.LENGTH_SHORT).show();
                }
                break;
            case BET_EVEN:
                if (parity_number[index_roulette] == 0){
                    deposit+= rate;
                    Toast.makeText(GameActivity.this, "Выигрыш: "+ rate, Toast.LENGTH_SHORT).show();
                }
                else {
                    deposit-= rate;
                    Toast.makeText(GameActivity.this, "Проиграли: "+ rate, Toast.LENGTH_SHORT).show();
                }
                break;
            case BET_ODD:
                if (parity_number[index_roulette] == 1){
                    deposit+= rate;
                    Toast.makeText(GameActivity.this, "Выигрыш: "+ rate, Toast.LENGTH_SHORT).show();
                }
                else {
                    deposit-= rate;
                    Toast.makeText(GameActivity.this, "Проиграли: "+ rate, Toast.LENGTH_SHORT).show();
                }
                break;
            case BET_STRAIT:
                if(number_roulette == betNum){
                    deposit+=35* rate;
                    Toast.makeText(GameActivity.this, "Выигрыш: "+ 35* rate, Toast.LENGTH_SHORT).show();
                }
                else {
                    deposit-= rate;
                    Toast.makeText(GameActivity.this, "Проиграли: "+ rate, Toast.LENGTH_SHORT).show();
                }
                break;

        }
        depositTextView.setText("Ваш депозит:" + deposit);
    }
    void setTextRate(int text_rate){
        rateTextView.setText(String.valueOf(text_rate));
    }
}
