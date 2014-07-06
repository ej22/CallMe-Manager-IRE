package ej22.callmeapp;



import android.animation.ValueAnimator;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.dd.CircularProgressButton;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class CallMe extends Fragment {

    Button btn;
    EditText num;
    Spinner op;
    int pos, selection;
    String operator, number;
    TextView test;
    CircularProgressButton cpBtn;

    public CallMe() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_call_me, container, false);

        num = (EditText)rootView.findViewById(R.id.phoneNumEditTxt);
        op = (Spinner)rootView.findViewById(R.id.operatorSpinner);
        final String opNumbers[] = getResources().getStringArray(R.array.operatorNumbersIre);
        test = (TextView)rootView.findViewById(R.id.testOP);
        cpBtn = (CircularProgressButton)rootView.findViewById(R.id.btnWithText);

        //TEST LABEL FOR GETTING NETWORK NAMES - TO BE REMOVED
        TelephonyManager tm = (TelephonyManager)getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        test.setText(tm.getNetworkOperatorName());
        //END REMOVE HERE

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.operatorChociesIre, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        op.setAdapter(adapter);

        selection = adapter.getPosition(tm.getNetworkOperatorName());

        op.setSelection(selection);
        op.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

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

        cpBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                number = num.getText().toString();
                operator = opNumbers[pos];
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

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                cpBtn.setProgress(0);
                            }
                        }, 3500);
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
        return rootView;
    }

    public void callO2(){
        try{
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + operator + number));
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

}
