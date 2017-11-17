package com.example.android_tcp_server;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Formatter;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_tcp_server.EnumsAndStatics.MessageTypes;
import com.example.orderingapp.R;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity implements OnTCPMessageRecievedListener,weatherListener{

	private Handler handler = new Handler();
	private DatabaseHandler db = new DatabaseHandler(this);
    private IoTInterface IoTThinkspeak= new IoTInterface();
    private Timer myTimer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity_layout);
		TCPCommunicator writer =TCPCommunicator.getInstance();
		TCPCommunicator.addListener(this);
		writer.init(1500);
		weatherInterface actualWeather = weatherInterface.getInstance();
		weatherInterface.addListener(this);
		actualWeather.init();
		//ustawienie adresu ip servera
        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
		@SuppressWarnings("deprecation")
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        TextView TmpTextView= (TextView) findViewById(R.id.workarea7Item1Value);
        TmpTextView.setText(ip);
		// database part
		db.initHeating();
		TmpTextView = (TextView)  findViewById(R.id.workarea1Item2Value);
		TmpTextView.setText(db.getHeatingData("setpoint"));
		//przypisanie metody update wartosci w bazie
		TmpTextView.setOnEditorActionListener(
				new EditText.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
						db.setHeatingData("setpoint",v.getText().toString());
						return false;
					}
				});
        //timer dla IoT
        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                TimerMethod();
            }

        }, 0, 300000);
	}

    private void TimerMethod()
    {
        //This method is called directly by the timer
        //and runs in the same thread as the timer.

        //We call the method that will work with the UI
        //through the runOnUiThread method.
        this.runOnUiThread(Timer_Tick);
    }


    private Runnable Timer_Tick = new Runnable() {
        public void run() {

            //This method runs in the same thread as the UI.

            //Do something to the UI thread here
            // IoT Stuff
            String fied1= db.getHeatingData("processTemperature");
            String fied2= db.getHeatingData("processSensorVoltage");
            String fied3= db.getHeatingData("processSensorRSSI");
            String fied4= db.getHeatingData("externalTemperature");
            String fied5= db.getHeatingData("setpoint");
            String fied6= db.getHeatingData("state").equals("ON") ? "1": "0";
            String fied7= db.getHeatingData("waterLoop");
            String fied8= "2";
            String [] IoTData = {fied1,fied2,fied3,fied4,fied5,fied6,fied7,fied8};
            IoTThinkspeak.SendData(getApplicationContext(),handler,0,IoTData);
        }
    };

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_activity2, menu);
		return true;
	}
	
    @Override
    protected void onResume()
    {
    	super.onResume();
        //setContentView(R.layout.main_activity_layout);
    }
    
    public void someButtonClicked(View view)
    {
    	JSONObject obj = new JSONObject();
    	try {
            /*if (view.getId() == R.id.btnSendToClient) {
                obj.put(EnumsAndStatics.MESSAGE_TYPE_FOR_JSON, MessageTypes.MessageFromServer);
                EditText txtContent = (EditText) findViewById(R.id.txtContentToSend);
                obj.put(EnumsAndStatics.MESSAGE_CONTENT_FOR_JSON, txtContent.getText().toString());
                //}

                final JSONObject jsonReadyForSend = obj;
                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        TCPCommunicator.writeToSocket(0,jsonReadyForSend);
                    }
                });

                thread.start();
            }*/
            if(view.getId()==R.id.leftButton1)
            {
                LinearLayout TmpLayoutMenu1 = (LinearLayout) findViewById(R.id.LayoutMenu1);
                TmpLayoutMenu1.setVisibility(View.VISIBLE);
                LinearLayout TmpLayoutMenu2 = (LinearLayout) findViewById(R.id.LayoutMenu2);
                TmpLayoutMenu2.setVisibility(View.GONE);
				LinearLayout TmpLayoutMenu3 = (LinearLayout) findViewById(R.id.LayoutMenu3);
				TmpLayoutMenu3.setVisibility(View.GONE);
				LinearLayout TmpLayoutMenu4 = (LinearLayout) findViewById(R.id.LayoutMenu4);
				TmpLayoutMenu4.setVisibility(View.GONE);
				LinearLayout TmpLayoutMenu5 = (LinearLayout) findViewById(R.id.LayoutMenu5);
				TmpLayoutMenu5.setVisibility(View.GONE);
				LinearLayout TmpLayoutMenu6 = (LinearLayout) findViewById(R.id.LayoutMenu6);
				TmpLayoutMenu6.setVisibility(View.GONE);
				LinearLayout TmpLayoutMenu7 = (LinearLayout) findViewById(R.id.LayoutMenu7);
				TmpLayoutMenu7.setVisibility(View.GONE);
				LinearLayout TmpLayoutMenu = (LinearLayout) findViewById(R.id.LayoutMenu);
				TmpLayoutMenu.setVisibility(View.GONE);

            }
            if(view.getId()==R.id.leftButton2)
            {
				LinearLayout TmpLayoutMenu1 = (LinearLayout) findViewById(R.id.LayoutMenu1);
				TmpLayoutMenu1.setVisibility(View.GONE);
				LinearLayout TmpLayoutMenu2 = (LinearLayout) findViewById(R.id.LayoutMenu2);
				TmpLayoutMenu2.setVisibility(View.VISIBLE);
				LinearLayout TmpLayoutMenu3 = (LinearLayout) findViewById(R.id.LayoutMenu3);
				TmpLayoutMenu3.setVisibility(View.GONE);
				LinearLayout TmpLayoutMenu4 = (LinearLayout) findViewById(R.id.LayoutMenu4);
				TmpLayoutMenu4.setVisibility(View.GONE);
				LinearLayout TmpLayoutMenu5 = (LinearLayout) findViewById(R.id.LayoutMenu5);
				TmpLayoutMenu5.setVisibility(View.GONE);
				LinearLayout TmpLayoutMenu6 = (LinearLayout) findViewById(R.id.LayoutMenu6);
				TmpLayoutMenu6.setVisibility(View.GONE);
				LinearLayout TmpLayoutMenu7 = (LinearLayout) findViewById(R.id.LayoutMenu7);
				TmpLayoutMenu7.setVisibility(View.GONE);
				LinearLayout TmpLayoutMenu = (LinearLayout) findViewById(R.id.LayoutMenu);
				TmpLayoutMenu.setVisibility(View.GONE);
            }
			if(view.getId()==R.id.leftButton3)
			{
				LinearLayout TmpLayoutMenu1 = (LinearLayout) findViewById(R.id.LayoutMenu1);
				TmpLayoutMenu1.setVisibility(View.GONE);
				LinearLayout TmpLayoutMenu2 = (LinearLayout) findViewById(R.id.LayoutMenu2);
				TmpLayoutMenu2.setVisibility(View.GONE);
				LinearLayout TmpLayoutMenu3 = (LinearLayout) findViewById(R.id.LayoutMenu3);
				TmpLayoutMenu3.setVisibility(View.VISIBLE);
				LinearLayout TmpLayoutMenu4 = (LinearLayout) findViewById(R.id.LayoutMenu4);
				TmpLayoutMenu4.setVisibility(View.GONE);
				LinearLayout TmpLayoutMenu5 = (LinearLayout) findViewById(R.id.LayoutMenu5);
				TmpLayoutMenu5.setVisibility(View.GONE);
				LinearLayout TmpLayoutMenu6 = (LinearLayout) findViewById(R.id.LayoutMenu6);
				TmpLayoutMenu6.setVisibility(View.GONE);
				LinearLayout TmpLayoutMenu7 = (LinearLayout) findViewById(R.id.LayoutMenu7);
				TmpLayoutMenu7.setVisibility(View.GONE);
				LinearLayout TmpLayoutMenu = (LinearLayout) findViewById(R.id.LayoutMenu);
				TmpLayoutMenu.setVisibility(View.GONE);
			}
			if(view.getId()==R.id.leftButton4)
			{
				LinearLayout TmpLayoutMenu1 = (LinearLayout) findViewById(R.id.LayoutMenu1);
				TmpLayoutMenu1.setVisibility(View.GONE);
				LinearLayout TmpLayoutMenu2 = (LinearLayout) findViewById(R.id.LayoutMenu2);
				TmpLayoutMenu2.setVisibility(View.GONE);
				LinearLayout TmpLayoutMenu3 = (LinearLayout) findViewById(R.id.LayoutMenu3);
				TmpLayoutMenu3.setVisibility(View.GONE);
				LinearLayout TmpLayoutMenu4 = (LinearLayout) findViewById(R.id.LayoutMenu4);
				TmpLayoutMenu4.setVisibility(View.VISIBLE);
				LinearLayout TmpLayoutMenu5 = (LinearLayout) findViewById(R.id.LayoutMenu5);
				TmpLayoutMenu5.setVisibility(View.GONE);
				LinearLayout TmpLayoutMenu6 = (LinearLayout) findViewById(R.id.LayoutMenu6);
				TmpLayoutMenu6.setVisibility(View.GONE);
				LinearLayout TmpLayoutMenu7 = (LinearLayout) findViewById(R.id.LayoutMenu7);
				TmpLayoutMenu7.setVisibility(View.GONE);
				LinearLayout TmpLayoutMenu = (LinearLayout) findViewById(R.id.LayoutMenu);
				TmpLayoutMenu.setVisibility(View.GONE);
			}
			if(view.getId()==R.id.leftButton5)
			{
				LinearLayout TmpLayoutMenu1 = (LinearLayout) findViewById(R.id.LayoutMenu1);
				TmpLayoutMenu1.setVisibility(View.GONE);
				LinearLayout TmpLayoutMenu2 = (LinearLayout) findViewById(R.id.LayoutMenu2);
				TmpLayoutMenu2.setVisibility(View.GONE);
				LinearLayout TmpLayoutMenu3 = (LinearLayout) findViewById(R.id.LayoutMenu3);
				TmpLayoutMenu3.setVisibility(View.GONE);
				LinearLayout TmpLayoutMenu4 = (LinearLayout) findViewById(R.id.LayoutMenu4);
				TmpLayoutMenu4.setVisibility(View.GONE);
				LinearLayout TmpLayoutMenu5 = (LinearLayout) findViewById(R.id.LayoutMenu5);
				TmpLayoutMenu5.setVisibility(View.VISIBLE);
				LinearLayout TmpLayoutMenu6 = (LinearLayout) findViewById(R.id.LayoutMenu6);
				TmpLayoutMenu6.setVisibility(View.GONE);
				LinearLayout TmpLayoutMenu7 = (LinearLayout) findViewById(R.id.LayoutMenu7);
				TmpLayoutMenu7.setVisibility(View.GONE);
				LinearLayout TmpLayoutMenu = (LinearLayout) findViewById(R.id.LayoutMenu);
				TmpLayoutMenu.setVisibility(View.GONE);
			}
			if(view.getId()==R.id.leftButton6)
			{
				LinearLayout TmpLayoutMenu1 = (LinearLayout) findViewById(R.id.LayoutMenu1);
				TmpLayoutMenu1.setVisibility(View.GONE);
				LinearLayout TmpLayoutMenu2 = (LinearLayout) findViewById(R.id.LayoutMenu2);
				TmpLayoutMenu2.setVisibility(View.GONE);
				LinearLayout TmpLayoutMenu3 = (LinearLayout) findViewById(R.id.LayoutMenu3);
				TmpLayoutMenu3.setVisibility(View.GONE);
				LinearLayout TmpLayoutMenu4 = (LinearLayout) findViewById(R.id.LayoutMenu4);
				TmpLayoutMenu4.setVisibility(View.GONE);
				LinearLayout TmpLayoutMenu5 = (LinearLayout) findViewById(R.id.LayoutMenu5);
				TmpLayoutMenu5.setVisibility(View.GONE);
				LinearLayout TmpLayoutMenu6 = (LinearLayout) findViewById(R.id.LayoutMenu6);
				TmpLayoutMenu6.setVisibility(View.VISIBLE);
				LinearLayout TmpLayoutMenu7 = (LinearLayout) findViewById(R.id.LayoutMenu7);
				TmpLayoutMenu7.setVisibility(View.GONE);
				LinearLayout TmpLayoutMenu = (LinearLayout) findViewById(R.id.LayoutMenu);
				TmpLayoutMenu.setVisibility(View.GONE);
			}
			if(view.getId()==R.id.leftButton7)
			{
				LinearLayout TmpLayoutMenu1 = (LinearLayout) findViewById(R.id.LayoutMenu1);
				TmpLayoutMenu1.setVisibility(View.GONE);
				LinearLayout TmpLayoutMenu2 = (LinearLayout) findViewById(R.id.LayoutMenu2);
				TmpLayoutMenu2.setVisibility(View.GONE);
				LinearLayout TmpLayoutMenu3 = (LinearLayout) findViewById(R.id.LayoutMenu3);
				TmpLayoutMenu3.setVisibility(View.GONE);
				LinearLayout TmpLayoutMenu4 = (LinearLayout) findViewById(R.id.LayoutMenu4);
				TmpLayoutMenu4.setVisibility(View.GONE);
				LinearLayout TmpLayoutMenu5 = (LinearLayout) findViewById(R.id.LayoutMenu5);
				TmpLayoutMenu5.setVisibility(View.GONE);
				LinearLayout TmpLayoutMenu6 = (LinearLayout) findViewById(R.id.LayoutMenu6);
				TmpLayoutMenu6.setVisibility(View.GONE);
				LinearLayout TmpLayoutMenu7 = (LinearLayout) findViewById(R.id.LayoutMenu7);
				TmpLayoutMenu7.setVisibility(View.VISIBLE);
				LinearLayout TmpLayoutMenu = (LinearLayout) findViewById(R.id.LayoutMenu);
				TmpLayoutMenu.setVisibility(View.GONE);
			}
			if(view.getId()==R.id.btnDisplayMenu)
			{
				LinearLayout TmpLayoutMenu = (LinearLayout) findViewById(R.id.LayoutMenu);
				if(TmpLayoutMenu.getVisibility()==View.VISIBLE)
					TmpLayoutMenu.setVisibility(View.GONE);
				else
					TmpLayoutMenu.setVisibility(View.VISIBLE);
			}
	    	
    	}
    	catch(Exception e)
    	{
    		
    	}
    	
    }

	@Override
	public void onTCPMessageRecieved(String message) {
		// TODO Auto-generated method stub
		final String theMessage=message;
		handler.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try
				{
				//EditText editTxt = (EditText)findViewById(R.id.txtInputFromClient);
				//editTxt.setText(theMessage);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		});

		
	}
	public String ModyfyView( int clientId, String Str) {

		final String Text = Str;
		String response="";
		String actualTemperature= new String("");
        String waterLoopTemperature=new String("");
		ReadXMLString xmlReader= new ReadXMLString();
		HashMap<String, String> dataFromClient = xmlReader.readXMLString(Str);
		if((dataFromClient != null)&&(dataFromClient.get("id")!=null) ) {
			LinearLayout tmpLinearLayout = new LinearLayout(new ContextThemeWrapper(this, R.style.left_menu_button), null, 0); //new LinearLayout(this);
			if (findViewById(Integer.parseInt(dataFromClient.get("id"))) == null) {
				LinearLayout tmpLayoutMenu4 = (LinearLayout) findViewById(R.id.LayoutMenu4);
				tmpLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.WRAP_CONTENT));
				tmpLinearLayout.setId(Integer.parseInt(dataFromClient.get("id")));
				tmpLinearLayout.setOrientation(LinearLayout.VERTICAL);
				tmpLinearLayout.setPadding(1, 1, 1, 1);
				tmpLinearLayout.setBackgroundResource(R.drawable.device_layout_backgroun_blue);
				tmpLayoutMenu4.addView(tmpLinearLayout);
			} else {
				tmpLinearLayout = (LinearLayout) findViewById(Integer.parseInt(dataFromClient.get("id")));
				tmpLinearLayout.removeAllViews();
			}
			TextView tmpTitle = new TextView(this);
			tmpTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT));
			tmpTitle.setTextSize(30);
			tmpTitle.setTextColor(Color.BLUE);
			tmpTitle.setText("Device id: " + dataFromClient.get("id"));
			tmpLinearLayout.addView(tmpTitle);
			//adding onClick event
			tmpLinearLayout.setClickable(true);
			if (dataFromClient.get("dev_type")!=null
					&& dataFromClient.get("dev_type").toString().equals("outputModule")) {
				tmpLinearLayout.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if (TCPCommunicator.getOutputModuleState()) {
							v.setBackgroundResource(R.drawable.device_layout_backgroun_blue);
							TCPCommunicator.setOutputModuleState(false);
						} else {
							v.setBackgroundResource(R.drawable.device_layout_backgroun_red);
							TCPCommunicator.setOutputModuleState(true);
						}
						Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();
					}
				});
			}
			TextView tv = new TextView(this);
			tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT));
			tmpLinearLayout.addView(tv);
			//dane z hashmap
			String viewText = new String();
			Set set = dataFromClient.entrySet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				Map.Entry mentry = (Map.Entry) iterator.next();
				if (!mentry.getKey().toString().contentEquals("id"))
					viewText += mentry.getKey() + " : " + mentry.getValue() + "\n";
			}
			tv.setText(viewText);
			//central heatingx
			if (dataFromClient.get("id").toString().equals(db.getHeatingData("processTemperatureId"))
					&& dataFromClient.get("sensorTemperature")!=null
					&& dataFromClient.get("RSSI")!=null
					&& dataFromClient.get("supplayVoltage")!=null) {
				actualTemperature = dataFromClient.get("sensorTemperature").toString();
                String rssi = dataFromClient.get("RSSI").toString();
                String voltage = dataFromClient.get("supplayVoltage").toString();
				//String Setpoint = db.getHeatingData("setpoint");
                if (actualTemperature.length() >= 1)
                    db.setHeatingData("processTemperature", actualTemperature);
                if (rssi.length() >= 1)
                    db.setHeatingData("processSensorRSSI", rssi);
                if (voltage.length() >= 1)
                    db.setHeatingData("processSensorVoltage", voltage);
				/*if(actualTemperature.length()>=1 && Setpoint.length()>=1){
					if(Float.parseFloat(actualTemperature) > Float.parseFloat(Setpoint)) {
                        TCPCommunicator.setOutputModuleState(false);
                        db.setHeatingData("state","OFF");
					}
					else {
                        TCPCommunicator.setOutputModuleState(true);
                        db.setHeatingData("state", "ON");
                    }
				}*/
			}

			if (dataFromClient.get("id").toString().equals(db.getHeatingData("waterLoopId"))
					&& dataFromClient.get("sensorTemperature")!=null) {
				waterLoopTemperature = dataFromClient.get("sensorTemperature").toString();
				if (waterLoopTemperature.length() >= 1)
					db.setHeatingData("waterLoop", waterLoopTemperature);
				String setpoint = db.getHeatingData("setpoint");
				String processTemperature = db.getHeatingData("processTemperature");
				if (processTemperature.length() >= 1 && setpoint.length() >= 1) {
					if (Float.parseFloat(processTemperature) > Float.parseFloat(setpoint)) {
						TCPCommunicator.setOutputModuleState(false);
						db.setHeatingData("state", "OFF");
					} else {
						TCPCommunicator.setOutputModuleState(true);
						db.setHeatingData("state", "ON");
					}
				}
			}

            //sending resposne to outputModule
            if (dataFromClient.get("id").toString().equals(db.getHeatingData("waterLoopId"))) {
//                try {
					//BufferedWriter out = null;
					//out =new BufferedWriter( new OutputStreamWriter(clientSocket.getOutputStream()));
					//DataOutputStream out = null;
					//DataOutputStream out =new DataOutputStream(clientSocket.getOutputStream());
					response="<content><outputState>"
							+ (db.getHeatingData("state").equals("ON") ? "ON" : "OFF")+"</outputState>"
							+"<roomTemperature>"+ db.getHeatingData("processTemperature")+"</roomTemperature>"
							+"<setpointTemperature>"+ db.getHeatingData("setpoint")+"</setpointTemperature>"
							+"</content>";
                  //  TCPCommunicator.writeToSocket(clientId,response);
  //              } catch (IOException e) {
    //                return;
      //          }
            }
		}
		else{
			String toastText=new String();
			if(dataFromClient==null)
				toastText="Nieprawidłowa struktura XML";
			else
				toastText="Brak id w strukturze XML";
			Toast toast =Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_LONG);
			toast.show();
		}
		final String actualTemperatureF=actualTemperature;
		final String waterLoopTemperatureF=waterLoopTemperature;

				handler.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							TextView TmpTextView = (TextView) findViewById(R.id.workarea7Item1Value);
							TmpTextView.setText("Client 1:"+Text);
							if(actualTemperatureF.length()>=1) {
								TmpTextView = (TextView) findViewById(R.id.workarea1Item1Value);
								TmpTextView.setText(actualTemperatureF);
							}
							if(waterLoopTemperatureF.length()>=1) {
								TmpTextView = (TextView) findViewById(R.id.workarea1Item3Value);
								TmpTextView.setText(waterLoopTemperatureF);
							}
							ImageView TmpImageView = (ImageView) findViewById(R.id.workarea1Item4Image);
							if(TCPCommunicator.getOutputModuleState())
							    TmpImageView.setImageResource(R.drawable.plomien_on);
                            else
                                TmpImageView.setImageResource(R.drawable.plomien_off);
							//dynamiczne tworzenie nowych elemetnow

						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				});
		return response;
	}
	public void weatherUpdate(String weatherXML){
        if(findTag(weatherXML,"temp_c").length()>=1){
            final String externalTemperature=findTag(weatherXML,"temp_c");
			db.setHeatingData("externalTemperature",externalTemperature);
            handler.post(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    try
                    {
                        EditText editTxt = (EditText)findViewById(R.id.workarea1Item4Value);
                        editTxt.setText(externalTemperature);
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });
        }
	}
    public String findTag(String source, String tag){
        String missing = "";
        if (source==null)
            return missing;
        if (source.length()<10)
            return missing;
        String startTag = "<"+tag+">";
        String endTag = "</"+tag+">";
        if(source.contains(startTag) && source.contains(endTag)){
            int startIndex= source.indexOf(startTag)+startTag.length();
            int stopIndex = source.indexOf(endTag);
            return source.substring(startIndex,stopIndex);
        }
        else{
            return missing;
        }

    }
}
