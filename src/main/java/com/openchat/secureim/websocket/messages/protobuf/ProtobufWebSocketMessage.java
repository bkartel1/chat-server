package com.openchat.secureim.websocket.messages.protobuf;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.openchat.secureim.websocket.messages.InvalidMessageException;
import com.openchat.secureim.websocket.messages.WebSocketMessage;
import com.openchat.secureim.websocket.messages.WebSocketRequestMessage;
import com.openchat.secureim.websocket.messages.WebSocketResponseMessage;

public class ProtobufWebSocketMessage implements WebSocketMessage {

  private final SubProtocol.WebSocketMessage message;

  ProtobufWebSocketMessage(byte[] buffer, int offset, int length) throws InvalidMessageException {
    try {
      this.message = SubProtocol.WebSocketMessage.parseFrom(ByteString.copyFrom(buffer, offset, length));

      if (getType() == Type.REQUEST_MESSAGE) {
        if (!message.getRequest().hasVerb() || !message.getRequest().hasPath()) {
          throw new InvalidMessageException("Missing required request attributes!");
        }
      } else if (getType() == Type.RESPONSE_MESSAGE) {
        if (!message.getResponse().hasId() || !message.getResponse().hasStatus() || !message.getResponse().hasMessage()) {
          throw new InvalidMessageException("Missing required response attributes!");
        }
      }
    } catch (InvalidProtocolBufferException e) {
      throw new InvalidMessageException(e);
    }
  }

  ProtobufWebSocketMessage(SubProtocol.WebSocketMessage message) {
    this.message = message;
  }

  @Override
  public Type getType() {
    if (message.getType().getNumber() == SubProtocol.WebSocketMessage.Type.REQUEST_VALUE &&
        message.hasRequest())
    {
      return Type.REQUEST_MESSAGE;
    } else if (message.getType().getNumber() == SubProtocol.WebSocketMessage.Type.RESPONSE_VALUE &&
               message.hasResponse())
    {
      return Type.RESPONSE_MESSAGE;
    } else {
      return Type.UNKNOWN_MESSAGE;
    }
  }

  @Override
  public WebSocketRequestMessage getRequestMessage() {
    return new ProtobufWebSocketRequestMessage(message.getRequest());
  }

  @Override
  public WebSocketResponseMessage getResponseMessage() {
    return new ProtobufWebSocketResponseMessage(message.getResponse());
  }

  @Override
  public byte[] toByteArray() {
    return message.toByteArray();
  }
}
