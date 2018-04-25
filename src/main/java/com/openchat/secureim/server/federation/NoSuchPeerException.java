package com.openchat.secureim.server.federation;


public class NoSuchPeerException extends Exception {
  public NoSuchPeerException(String name) {
    super(name);
  }
}
