package com.openchat.secureim.server.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.annotations.VisibleForTesting;

public class DeviceResponse {

  @JsonProperty
  private long deviceId;

  @VisibleForTesting
  public DeviceResponse() {}

  public DeviceResponse(long deviceId) {
    this.deviceId = deviceId;
  }

  public long getDeviceId() {
    return deviceId;
  }
}
