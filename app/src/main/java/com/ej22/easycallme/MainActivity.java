package com.ej22.easycallme;

import android.animation.ValueAnimator;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.dd.CircularProgressButton;

public class MainActivity extends ActionBarActivity {

	Button btn;
	EditText num;
	Spinner op;
	int pos, selection;
	String operator, number;
	TextView test;
    CircularProgressButton cpBtn;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        num = (EditText)findViewById(R.id.phoneNumEditTxt);
        op = (Spinner)findViewById(R.id.operatorSpinner);
        final String opNums[] = getResources().getStringArray(R.array.operatorNumbersIre);
        test = (TextView)findViewById(R.id.testOP);
        cpBtn = (CircularProgressButton)findViewById(R.id.btnWithText);
        
        //TEST LABEL FOR GETTING NETWORK NAMES - TO BE REMOVED
        TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        test.setText(tm.getNetworkOperatorName());
        //END REMOVE HERE
        
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.operatorChociesIre, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        op.setAdapter(adapter);
        
        selection = adapter.getPosition(tm.getNetworkOperatorName());
        
        op.setSelection(selection);
        op.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> adapter, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				pos = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
        	
        });

        cpBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				number = num.getText().toString();
				operator = opNums[pos];
				String opChoice = op.getSelectedItem().toString();

				if(opChoice.equals("Meteor") || opChoice.equals("vodafone IE"))
				{
					try {
				         SmsManager smsManager = SmsManager.getDefault();
				         smsManager.sendTextMessage(operator, null, number, null, null);

                        if (cpBtn.getProgress() == 0) {
                            successAnimation(cpBtn);
                        } else {
                            cpBtn.setProgress(0);
                        }
				      } catch (Exception e) {
                        cpBtn.setProgress(-1);
				         e.printStackTrace();
				      }
				}//end if statement
				else if(opChoice.equals("3")){
					try {
				         SmsManager smsManager = SmsManager.getDefault();
				         smsManager.sendTextMessage(operator, null, "Call me "+ number, null, null);
                        cpBtn.setProgress(100);
				      } catch (Exception e) {
                        cpBtn.setProgress(-1);
				         e.printStackTrace();
				      }
				}//end else if
				else if(opChoice.equals("O2")){
					callO2();
				}
			}
        	
        });
    }
    
    public void callO2(){
    	try{
			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse("tel:"+operator+number));
			startActivity(callIntent);
		}catch (ActivityNotFoundException e) {
	        Log.e("Call Me", "Call failed", e);
	    }
    }

    //Code gotten from sample of CircularProgressButton project on Github.
    private void successAnimation(final CircularProgressButton button) {
        ValueAnimator widthAnimation = ValueAnimator.ofInt(1, 100);
        widthAnimation.setDuration(1500);
        widthAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                button.setProgress(value);
            }
        });
        widthAnimation.start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
