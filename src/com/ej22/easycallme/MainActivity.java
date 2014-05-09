package com.ej22.easycallme;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	Button btn;
	EditText num;
	Spinner op;
	String opChoice;
	int pos;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        num = (EditText)findViewById(R.id.editText1);
        op = (Spinner)findViewById(R.id.operatorSpinner);
        btn = (Button)findViewById(R.id.button1);
        final String opNums[] = getResources().getStringArray(R.array.operatorNumbersIre);
        
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.operatorChociesIre, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        op.setAdapter(adapter);
        
        op.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> adapter, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				opChoice = adapter.getItemAtPosition(pos).toString();
				pos = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
        	
        });
        
        btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String number = num.getText().toString();
				String operator = opNums[pos];
				
				switch(opChoice){
				case "Meteor":
				case "Vodafone":
					try {
				         SmsManager smsManager = SmsManager.getDefault();
				         smsManager.sendTextMessage(operator, null, number, null, null);
				         Toast.makeText(getApplicationContext(), "SMS sent.",
				         Toast.LENGTH_LONG).show();
				      } catch (Exception e) {
				         Toast.makeText(getApplicationContext(),
				         "SMS failed, please try again.",
				         Toast.LENGTH_LONG).show();
				         e.printStackTrace();
				      }
					
				}
			}
        	
        });
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
