package ca.davidpellegrini.scorekeepersolution;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.view.inputmethod.EditorInfo;
import android.util.Log;
import android.view.KeyEvent;
import android.content.Context;

import android.view.View.OnClickListener;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView.OnEditorActionListener;
import android.view.View.OnKeyListener;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences;

//import android.support.v7.app.AlertDialog;
import android.app.AlertDialog;
import android.content.DialogInterface;

import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnEditorActionListener, OnClickListener, OnCheckedChangeListener, OnKeyListener {

    private TextView teamA_score_textview, teamB_score_textview;
    private Button increase_TeamA_Button, increase_TeamB_Button;
    private Button decrease_TeamA_Button, decrease_TeamB_Button;
    private RadioGroup scoreChngFactorGroup;
    private RadioButton chngFactor2RadioButton, chngFactor4RadioButton ,chngFactor6RadioButton;

    private int teamAScore = 0;
    private int teamBScore = 0;
    private int chngFactor = 2;

    private int teamScore = 0;
    private SharedPreferences savedValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //variables to reference UI component actions
        teamA_score_textview = (TextView) findViewById(R.id.teamA_score_textview);
        teamB_score_textview = (TextView) findViewById(R.id.teamB_score_textview);

        increase_TeamA_Button = (Button) findViewById(R.id.increase_TeamA_Button);
        increase_TeamB_Button = (Button) findViewById(R.id.increase_TeamB_Button);
        decrease_TeamA_Button = (Button) findViewById(R.id.decrease_TeamA_Button);
        decrease_TeamB_Button = (Button) findViewById(R.id.decrease_TeamB_Button);

        //make your variables (set above) actualy listen to you
        increase_TeamA_Button.setOnClickListener(this);
        increase_TeamB_Button.setOnClickListener(this);

        decrease_TeamA_Button.setOnClickListener(this);
        decrease_TeamB_Button.setOnClickListener(this);

        scoreChngFactorGroup = (RadioGroup) findViewById(R.id.scoreChngFactorGroup_RadioGroup);
        scoreChngFactorGroup.setOnCheckedChangeListener(this);

        chngFactor2RadioButton = (RadioButton) findViewById(R.id.chngFactor2RadioButton);
        chngFactor4RadioButton = (RadioButton) findViewById(R.id.chngFactor4RadioButton);
        chngFactor6RadioButton = (RadioButton) findViewById(R.id.chngFactor6RadioButton);

        chngFactor2RadioButton.setChecked(true);
        teamA_score_textview.setText("0");
        teamB_score_textview.setText("0");
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
        if( actionId == EditorInfo.IME_ACTION_DONE ||
                actionId == EditorInfo.IME_ACTION_UNSPECIFIED){
            //tipTextView.setText("$10.00");
            //totalTextView.setText("$110.00");
        }
        return false;
    }

    @Override
    public void onClick(View v){
        boolean refresh_txtview = true;
        switch (v.getId()){
            case R.id.increase_TeamA_Button:
                teamAScore = getCurrentScore('A');
                teamAScore = teamAScore + chngFactor;
                refresh_txtview = validateScore('A',teamAScore);
                if (refresh_txtview){
                    setTeamScore('A', teamAScore);
                }
                break;
            case R.id.increase_TeamB_Button:
                teamBScore = getCurrentScore('B');
                teamBScore = teamBScore + chngFactor;
                refresh_txtview = validateScore('B',teamBScore);
                if (refresh_txtview) {
                    setTeamScore('B', teamBScore);
                }
                break;
            case R.id.decrease_TeamA_Button:
                teamAScore = getCurrentScore('A');
                teamAScore = teamAScore - chngFactor;
                refresh_txtview = validateScore('A',teamAScore);
                if (refresh_txtview) {
                    setTeamScore('A', teamAScore);
                }
                break;
            case R.id.decrease_TeamB_Button:
                teamBScore = getCurrentScore('B');
                teamBScore = teamBScore - chngFactor;
                refresh_txtview = validateScore('B',teamBScore);
                if (refresh_txtview) {
                    setTeamScore('B', teamBScore);
                }
                break;
            /*default:
                Default is like the else in an if statement
                With onClick it can be dangerous because it
                    will run when something other than our
                    button is clicked
                break;
             */
        }
    }

    public boolean validateScore(char team , int score){
        int new_val = 0;
        boolean ret = true;
        if (score > 50 ){
            displayMessage("Score value can not exceed 50");
            new_val = 50;
            ret = false;
        }else if (score < 0){
            displayMessage("Score value can not be a negative value");
            new_val = 0;
            ret = false;
        }

        switch(team) {
            case 'A' :
                //Toast.makeText(getApplicationContext(),Integer.toString(new_val),
                //        Toast.LENGTH_SHORT).show();
                teamA_score_textview.setText(Integer.toString(new_val));
                break;
            case 'B' :
                //Toast.makeText(getApplicationContext(),Integer.toString(new_val),
                //        Toast.LENGTH_SHORT).show();
                teamB_score_textview.setText(Integer.toString(new_val));
                break;
        }

        return ret;
    }

    public int getCurrentScore(char team){
        switch(team) {
            case 'A' :
                String tempScoreA = teamA_score_textview.getText().toString();
                if(tempScoreA.equals("")){
                    teamScore = 0;
                }
                else{
                    teamScore = Integer.parseInt(tempScoreA);
                }
                break;
            case 'B' :
                String tempScoreB = teamB_score_textview.getText().toString();
                if(tempScoreB.equals("")){
                    teamScore = 0;
                }
                else{
                    teamScore = Integer.parseInt(tempScoreB);
                }
                break;
        }
        return teamScore;
    }

    public void setTeamScore(char team, int score){
        switch(team) {
            case 'A' :
                teamA_score_textview.setText(Integer.toString(score));
                break;
            case 'B' :
                teamB_score_textview.setText(Integer.toString(score));
                break;
        }
       // percentTextView.setText((int)(tipPercent * 100) + "%");
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId){
       // Log.e("checkedId: ", Integer.toString(checkedId));
        switch (checkedId){
            case R.id.chngFactor2RadioButton:
            default:
                chngFactor = 2;
                break;
            case R.id.chngFactor4RadioButton:
                chngFactor = 4;
                break;
            case R.id.chngFactor6RadioButton:
                chngFactor = 6;
                break;
        }
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event){
        switch (keyCode){
            case KeyEvent.KEYCODE_ENTER:
            case KeyEvent.KEYCODE_DPAD_CENTER:
                //calculateAndDisplay();

                //hide the keyboard
                //InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                //imm.hideSoftInputFromWindow(billAmountEditText.getWindowToken(), 0);
                return true;
        }
        return false;
    }


    @Override
    public void onPause(){
        Editor editor = savedValues.edit();
        //editor.putString("billAmountString", billAmountString);
        //editor.putFloat("tipPercent", tipPercent);
        editor.commit();

        super.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();

        //billAmountString = savedValues.getString("billAmountString", "" );
        //tipPercent = savedValues.getFloat("tipPercent", 0.15f);
    }

    public void displayMessage(String message){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
