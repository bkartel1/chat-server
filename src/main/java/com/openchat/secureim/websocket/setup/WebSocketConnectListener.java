package com.openchat.secureim.websocket.setup;

import com.openchat.secureim.websocket.session.WebSocketSessionContext;

public interface WebSocketConnectListener {
  public void onWebSocketConnect(WebSocketSessionContext context);
}
