package com.openchat.secureim.server.websocket;

public class InvalidWebsocketAddressException extends Exception {
  public InvalidWebsocketAddressException(String serialized) {
    super(serialized);
  }

  public InvalidWebsocketAddressException(Exception e) {
    super(e);
  }
}
