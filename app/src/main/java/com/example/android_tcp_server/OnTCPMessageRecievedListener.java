package com.example.android_tcp_server;


import android.view.View;

public interface OnTCPMessageRecievedListener {
	public void onTCPMessageRecieved(String message);
	public void ModyfyView (int Action, String Str);
}

