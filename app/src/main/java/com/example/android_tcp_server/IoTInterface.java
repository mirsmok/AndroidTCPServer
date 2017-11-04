package com.example.android_tcp_server;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Created by mirsmok on 04.11.17.
 */

public class IoTInterface {
    private static Socket IoTsocket;
    private static BufferedReader in;
    private static BufferedWriter out;
    private static String [] ApiKeys={"HPGNR50XGF9V9KAD"};
    private static Context appContext;
    private static Handler UIHandler;
    public IoTInterface(){
    //    try {
     //       IoTsocket = new Socket("data.sparkfun.com", 80);
     //       in = new BufferedReader(new InputStreamReader(IoTsocket.getInputStream()));
    //        out = new BufferedWriter(new OutputStreamWriter(IoTsocket.getOutputStream()));
//
  ///      }
   //     catch (IOException e){}
    }
    public static IoTWriterErrors SendData(Context context, Handler handler, int apiKeyIndex, String [] fields){
        appContext=context;
        UIHandler=handler;
        final String WriteApiKey=ApiKeys[apiKeyIndex];
        final String [] IoTFields=fields;

        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    IoTsocket = new Socket("api.thingspeak.com", 80);
                    in = new BufferedReader(new InputStreamReader(IoTsocket.getInputStream()));
                    out = new BufferedWriter(new OutputStreamWriter(IoTsocket.getOutputStream()));

                }
                catch (IOException e){
                    UIHandler.post(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            Toast.makeText(appContext ,"cant connect", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                try
                {
                    String body = "field1=";
                    body += IoTFields[0];
                    body += "&field2=";
                    body += IoTFields[1];
                    body += "&field3=";
                    body += IoTFields[2];
                    body += "&field4=";
                    body += IoTFields[3];
                    body += "&field5=";
                    body += IoTFields[4];
                    body += "&field6=";
                    body += IoTFields[5];
                    body += "&field7=";
                    body += IoTFields[6];
                    body += "&field8=";
                    body += IoTFields[7];
                    String outMsg = "POST /update HTTP/1.1\n";
                    outMsg += "Host: api.thingspeak.com\n";
                    outMsg += "Connection: close\n";
                    outMsg += "X-THINGSPEAKAPIKEY: " + WriteApiKey + "\n";
                    outMsg +="Content-Type: application/x-www-form-urlencoded\n";
                    outMsg +="Content-Length: ";
                    outMsg += Integer.toString(body.length());
                    outMsg +="\n\n";
                    outMsg +=body;
                    outMsg +="\n\n";
                    out.write(outMsg);
                    out.flush();
        //            Log.i("TcpClient", "sent: " + outMsg);
                }
                catch(Exception e)
                {
                    UIHandler.post(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            Toast.makeText(appContext ,"cant write to socket", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                try {
                    in.close();
                    out.close();
                    IoTsocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    UIHandler.post(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            Toast.makeText(appContext ,"error while closing socket", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

        };
        Thread thread = new Thread(runnable);
        thread.start();
        return IoTWriterErrors.OK;

    }
    public enum IoTWriterErrors{UnknownHostException,IOException,otherProblem,OK}

}
