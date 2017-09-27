package com.example.android_tcp_server;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Formatter;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android_tcp_server.EnumsAndStatics.MessageTypes;
import com.example.orderingapp.R;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MainActivity extends Activity implements OnTCPMessageRecievedListener {

	private Handler handler = new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity_layout);
    	TCPCommunicator writer =TCPCommunicator.getInstance();
    	TCPCommunicator.addListener(this);
    	writer.init(1500);
		//ustawienie adresu ip servera
        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
		@SuppressWarnings("deprecation")
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        TextView TmpTextView= (TextView) findViewById(R.id.workarea7Item1Value);
        TmpTextView.setText(ip);
		//ReadXMLString xmlReader= new ReadXMLString();

	}

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
            if (view.getId() == R.id.btnSendToClient) {
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
            }
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
				EditText editTxt = (EditText)findViewById(R.id.txtInputFromClient);
				editTxt.setText(theMessage);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		});

		
	}
	public void ModyfyView(int clientId, String Str) {

		final String Text = Str;
		ReadXMLString xmlReader= new ReadXMLString();
		HashMap<String, String> dataFromClient = xmlReader.readXMLString(Str);
		if(dataFromClient == null ) return;
		switch (clientId) {
			case 1: //
				LinearLayout tmpLayoutMenu4=(LinearLayout) findViewById(R.id.LayoutMenu4);
				///LayoutParams lparams = new LayoutParams(
				//		LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				TextView tv=new TextView(this);
				tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT));
				tmpLayoutMenu4.addView(tv);
				//dane z hashmap
				String viewText= new String();
				Set set = dataFromClient.entrySet();
				Iterator iterator = set.iterator();
				while(iterator.hasNext()) {
					Map.Entry mentry = (Map.Entry)iterator.next();
					viewText+="key: "+ mentry.getKey() + " & Value: "+ mentry.getValue()+"\n";
				}
				tv.setText(viewText);
				handler.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							TextView TmpTextView = (TextView) findViewById(R.id.workarea7Item1Value);
							TmpTextView.setText("Client 1:"+Text);
							//dynamiczne tworzenie nowych elemetnow

						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				});
				break;
			case 2: //
				handler.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							TextView TmpTextView = (TextView) findViewById(R.id.workarea7Item1Value);
							TmpTextView.setText("Client 2:"+Text);
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				});
				break;
		}
	}
}
