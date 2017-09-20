package com.example.android_tcp_server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.orderingapp.R;

public class TCPCommunicator {
	private static TCPCommunicator uniqInstance;
	private static int serverPort;
	private static List<OnTCPMessageRecievedListener> allListeners;
	private static ServerSocket ss;
	private static Socket s;
	private static BufferedReader in;
	private static BufferedWriter out;
	private static OutputStream outputStream;
	private static Handler handler = new Handler();
	private TCPCommunicator()
	{
		allListeners = new ArrayList<OnTCPMessageRecievedListener>();
	}
	public static TCPCommunicator getInstance()
	{
		if(uniqInstance==null)
		{
			uniqInstance = new TCPCommunicator();
		}
		return uniqInstance;
	}
	public  TCPWriterErrors init(int port)
	{
		setServerPort(port);
		InitTCPServerTask task = new InitTCPServerTask();
		task.execute(new Void[0]);
		return TCPWriterErrors.OK;
//		}
	}
	public static  TCPWriterErrors writeToSocket(JSONObject obj)
	{
		try
		{
		out.write(obj.toString() + System.getProperty("line.separator"));
		out.flush();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return TCPWriterErrors.OK;
		
	}
	
	public static void addListener(OnTCPMessageRecievedListener listener)
	{
		allListeners.add(listener);
	}
	


	public static int getServerPort() {
		return serverPort;
	}
	public static void setServerPort(int serverPort) {
		TCPCommunicator.serverPort = serverPort;
	}


	public class InitTCPServerTask extends AsyncTask<Void, Void, Void> {
        public InitTCPServerTask() {

        }

        @Override
        protected Void doInBackground(Void... params) {


/*
			try {
				ss = new ServerSocket(TCPCommunicator.getServerPort());
				s = ss.accept();
				s.setKeepAlive(true);
	             in = new BufferedReader(new InputStreamReader(s.getInputStream()));
	             outputStream = s.getOutputStream();
	             out = new BufferedWriter(new OutputStreamWriter(outputStream));
	            //receive a message
	             String incomingMsg;
	            while((incomingMsg=in.readLine())!=null)
	            {
	            	final String finalMessage=incomingMsg;
	            	handler.post(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							 for(OnTCPMessageRecievedListener listener:allListeners)


					       		            			            	listener.onTCPMessageRecieved(finalMessage);
					            Log.e("TCP", finalMessage);
						}
					});     
	            }
			} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
			return null;
			
			
			
		}*/
            try{
                ss = new ServerSocket(TCPCommunicator.getServerPort());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Socket socket = null;
            while (true) {
                try {
                    socket = ss.accept();
                } catch (IOException e) {
                    System.out.println("I/O error: " + e);
                }
                // new thread for a client
                new EchoThread(socket).start();
            }

        }
    }
	public enum TCPWriterErrors{UnknownHostException,IOException,otherProblem,OK}

	public class EchoThread extends Thread {
		protected Socket socket;

		public EchoThread(Socket clientSocket) {
			this.socket = clientSocket;
		}

		public void run() {
			InputStream inp = null;
			BufferedReader brinp = null;
			DataOutputStream out = null;
			try {
				inp = socket.getInputStream();
				brinp = new BufferedReader(new InputStreamReader(inp));
				out = new DataOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				return;
			}
			String line;
			while (true) {
				try {
					line = brinp.readLine();
					if ((line == null) || line.equalsIgnoreCase("QUIT")) {
						socket.close();
						return;
					} else {
                        final String finalMessage=line;
                        handler.post(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                for(OnTCPMessageRecievedListener listener:allListeners)


                                    listener.onTCPMessageRecieved(finalMessage);
                                Log.e("TCP", finalMessage);
                            }
                        });
                        out.writeBytes(line + "\n\r");
						out.flush();
					}
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}
			}
		}
	}

	public static void closeStreams() {
		// TODO Auto-generated method stub
		try
		{
			s.close();
			ss.close();
			out.close();
			in.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
