package com.example.android_tcp_server;

import android.content.Context;
import android.os.Handler;
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

public class weatherInterface {
    private static Socket weatherSocket;
    private static BufferedReader in;
    private static BufferedWriter out;
    private static String weatherXML;
    private static String line;
    private static Context appContext;
    private static Handler UIHandler;
    public weatherInterface(){
    //    try {
     //       IoTsocket = new Socket("data.sparkfun.com", 80);
     //       in = new BufferedReader(new InputStreamReader(IoTsocket.getInputStream()));
    //        out = new BufferedWriter(new OutputStreamWriter(IoTsocket.getOutputStream()));
//
  ///      }
   //     catch (IOException e){}
    }
    public static String  readWeather(Context context, Handler handler){
        appContext=context;
        UIHandler=handler;


        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    weatherSocket = new Socket("api.wuderground.com", 80);
                    in = new BufferedReader(new InputStreamReader(weatherSocket.getInputStream()));
                    out = new BufferedWriter(new OutputStreamWriter(weatherSocket.getOutputStream()));

                }
                catch (IOException e){
                    UIHandler.post(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            Toast.makeText(appContext ,"cant connect to weather service", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                //wyslanie zapytania do serwisu pogodowego
                try
                {
                    String query = "GET /api/33b6815fdd298d17/conditions/q/50.89,15.81.xml HTTP/1.1\n";
                    query += "Host: api.wunderground.com\n";
                    query += "User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:51.0) Gecko/20100101 Firefox/51.0";
                    query += "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\n";
                    query += "Accept-Language: en-US,en;q=0.5\n";
                    query += "Accept-Encoding: no \n";
                    query += "Connection: close\n";
                    query += "Upgrade-Insecure-Requests: 1";
                    query += "If-Modified-Since: Sun, 05 Nov 2017 14:47:31 GMT\n";
                    query += "Cache-Control: max-age=0\n\n";
                    out.write(query);
                    out.flush();
                    weatherXML="";
        //            Log.i("TcpClient", "sent: " + outMsg);
                }
                catch(Exception e)
                {
                    UIHandler.post(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            Toast.makeText(appContext ,"cant write to weather service", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                //odebranie danych z serwisu pogodowego
                try {
                    do {
                        line = in.readLine();
                        weatherXML+=line;
                    }while(!line.equals("</response>"));
                } catch (IOException e) {
                    UIHandler.post(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            Toast.makeText(appContext ,"cant write to weather service", Toast.LENGTH_SHORT).show();
                        }
                    });
                    e.printStackTrace();
                }
                //zamykanie polaczenia
                try {
                    in.close();
                    out.close();
                    weatherSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    UIHandler.post(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            Toast.makeText(appContext ,"error while closing weather socket", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

        };
        Thread thread = new Thread(runnable);
        thread.start();
        return weatherXML;

    }
    public String findTag(String input, String tag){
        if(input.length()<=1)
            input=weatherXML;
        String startTag = "<"+tag+">";
        String endTag = "</"+tag+">";
        String missing = "";
        if(input.contains(startTag) && input.contains(endTag)){
            int startIndex= input.indexOf(startTag)+startTag.length();
            int stopIndex = input.indexOf(endTag);
            return input.substring(startIndex,stopIndex);
        }
        else{
            return missing;
        }

    }
}
