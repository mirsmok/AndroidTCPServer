package com.example.android_tcp_server;


import android.view.View;

import java.net.Socket;

public interface OnTCPMessageRecievedListener {
	public void onTCPMessageRecieved(String message);
	public String ModyfyView ( int socketId, String Str);
}

