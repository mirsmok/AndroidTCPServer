package com.example.android_tcp_server;


import java.net.Socket;

public interface weatherListener {
	public void weatherUpdate(String weatherXML);
}

