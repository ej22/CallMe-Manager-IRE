package com.ej22.easycallme;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	Button btn;
	EditText num, op;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        num = (EditText)findViewById(R.id.editText1);
        op = (EditText)findViewById(R.id.editText2);
        btn = (Button)findViewById(R.id.button1);
        
        btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String number = num.getText().toString();
				String operator = op.getText().toString();
				
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
