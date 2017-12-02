package com.example.android_tcp_server;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by mirsmok on 04.11.17.
 */

public class weatherInterface {
    private static List<weatherListener> allListeners;
    private static weatherInterface uniqInstance;
    private static Socket weatherSocket;
    private static BufferedReader in;
    private static BufferedWriter out;
    private static String weatherXML;
    private static String line;
    private static Handler handler = new Handler();
    private static Timer weatherPollInterval;
    private static boolean timerTick;
    private weatherInterface(){
        Log.i("weatherTask","tworzenie obiektu");
        timerTick=false;
        allListeners = new ArrayList<weatherListener>();
    }
    public static weatherInterface getInstance(){
        if(uniqInstance==null)
            uniqInstance = new weatherInterface();
        return uniqInstance;
    }
    public static void addListener(weatherListener listener)
    {
        allListeners.add(listener);
    }
    public  void init()
    {
        initWeatherInterface task = new initWeatherInterface();
        task.execute(new Void[0]);
        weatherPollInterval = new Timer();
        weatherPollInterval.schedule(new TimerTask() {
            @Override
            public void run() {
                readWeather();
            }

        }, 0, 300000);
    }
    private void TimerMethod() {
        //This method is called directly by the timer
        //and runs in the same thread as the timer.
        timerTick=true;
        Log.i("weatherTask","sendin Timer Tick");
    }
    public class initWeatherInterface extends AsyncTask<Void, Void, Void> {
        public initWeatherInterface() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.i("watherTask","start background task");
            String query = "GET /api/33b6815fdd298d17/conditions/q/50.89,15.81.xml HTTP/1.1\n";
            query += "Host: api.wunderground.com\n";
            query += "User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:51.0) Gecko/20100101 Firefox/51.0";
            query += "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\n";
            query += "Accept-Language: en-US,en;q=0.5\n";
            query += "Accept-Encoding: no \n";
            query += "Connection: close\n\n";
            //query += "Upgrade-Insecure-Requests: 1";
            //query += "If-Modified-Since: Sun, 05 Nov 2017 14:47:31 GMT\n";
            //query += "Cache-Control: max-age=0\n\n";
            while (true) {
                if(timerTick) {
                    try{
                        weatherSocket = new Socket("api.wunderground.com", 80);
                        in = new BufferedReader(new InputStreamReader(weatherSocket.getInputStream()));
                        out = new BufferedWriter(new OutputStreamWriter(weatherSocket.getOutputStream()));

                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        Log.i("weatherTask","error while creating socket");
                    }
                    Log.i("weatherTask","sendin request");
                    try {
                        out.write(query);
                        out.flush();
                        weatherXML = "";
                        //            Log.i("TcpClient", "sent: " + outMsg);
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    //odebranie danych z serwisu pogodowego
                    String data = new String();
                    try {
                        do {
                            line = in.readLine();
                            if(line!=null) {
                                data += line;
                            }
                            Log.i("weatherTask","Linia: "+line);
                        } while (line!=null && !line.equals("</response>") );
                        weatherXML = data;
                        final String finalMessage=weatherXML;
                        Log.i("weatherTask","Recieved: "+finalMessage);
                        handler.post(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                for(weatherListener listener:allListeners) {
                                    listener.weatherUpdate(finalMessage);
                                }
                                Log.e("TCP", finalMessage);
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    timerTick=false;
                    try {
                        in.close();
                        out.close();
                        weatherSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

        }
    }

    public boolean readWeather(){

        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    weatherSocket = new Socket("api.wunderground.com", 80);
                    in = new BufferedReader(new InputStreamReader(weatherSocket.getInputStream()));
                    out = new BufferedWriter(new OutputStreamWriter(weatherSocket.getOutputStream()));

                }
                catch (IOException e){
                    Log.e("weatherRead","Error while creating socket");
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
                    Log.e("weatherRead","Error while write to socket");
                }
                //odebranie danych z serwisu pogodowego
                String data = new String();
                try {
                    do {
                        line = in.readLine();
                        if(line!=null) {
                            data += line;
                        }
                        Log.i("weatherTask","Linia: "+line);
                    } while (line!=null && !line.equals("</response>") );
                    //zamykanie polaczenia
                    try {
                        in.close();
                        out.close();
                        weatherSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("weatherRead","Error while closing socket");
                    }
                    //wyslanie danych do UI poprzez interfejs
                    weatherXML=data;
                    final String finalMessage=weatherXML;
                    handler.post(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            for(weatherListener listener:allListeners) {
                                listener.weatherUpdate(finalMessage);
                            }
                            Log.e("TCP", finalMessage);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("weatherRead","Erro while reading from socket");
                }
            }

        };
        Thread thread = new Thread(runnable);
        thread.start();
        return true;
    }

}
