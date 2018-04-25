package com.openchat.secureim.dispatch.io;

import com.openchat.secureim.dispatch.redis.PubSubConnection;
import com.openchat.secureim.dispatch.redis.PubSubConnection;

public interface RedisPubSubConnectionFactory {

  public PubSubConnection connect();

}
