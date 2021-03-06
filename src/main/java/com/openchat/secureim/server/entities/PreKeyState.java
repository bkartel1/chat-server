package com.openchat.secureim.server.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.annotations.VisibleForTesting;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public class PreKeyState {

  @JsonProperty
  @NotNull
  @Valid
  private List<PreKey> preKeys;

  @JsonProperty
  @NotNull
  @Valid
  private SignedPreKey signedPreKey;

  @JsonProperty
  @NotEmpty
  private String identityKey;

  public PreKeyState() {}

  @VisibleForTesting
  public PreKeyState(String identityKey, SignedPreKey signedPreKey, List<PreKey> keys) {
    this.identityKey   = identityKey;
    this.signedPreKey  = signedPreKey;
    this.preKeys       = keys;
  }

  public List<PreKey> getPreKeys() {
    return preKeys;
  }

  public SignedPreKey getSignedPreKey() {
    return signedPreKey;
  }

  public String getIdentityKey() {
    return identityKey;
  }

}
