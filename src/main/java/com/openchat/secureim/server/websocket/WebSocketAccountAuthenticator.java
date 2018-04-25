package com.openchat.secureim.server.websocket;

import com.google.common.base.Optional;
import org.eclipse.jetty.websocket.api.UpgradeRequest;
import com.openchat.secureim.server.auth.AccountAuthenticator;
import com.openchat.secureim.server.storage.Account;
import com.openchat.secureim.websocket.auth.AuthenticationException;
import com.openchat.secureim.websocket.auth.WebSocketAuthenticator;

import java.util.List;
import java.util.Map;

import io.dropwizard.auth.basic.BasicCredentials;


public class WebSocketAccountAuthenticator implements WebSocketAuthenticator<Account> {

  private final AccountAuthenticator accountAuthenticator;

  public WebSocketAccountAuthenticator(AccountAuthenticator accountAuthenticator) {
    this.accountAuthenticator = accountAuthenticator;
  }

  @Override
  public Optional<Account> authenticate(UpgradeRequest request) throws AuthenticationException {
    try {
      Map<String, List<String>> parameters = request.getParameterMap();
      List<String>              usernames  = parameters.get("login");
      List<String>              passwords  = parameters.get("password");

      if (usernames == null || usernames.size() == 0 ||
          passwords == null || passwords.size() == 0)
      {
        return Optional.absent();
      }

      BasicCredentials credentials = new BasicCredentials(usernames.get(0).replace(" ", "+"),
                                                          passwords.get(0).replace(" ", "+"));
      
      return accountAuthenticator.authenticate(credentials);
    } catch (io.dropwizard.auth.AuthenticationException e) {
      throw new AuthenticationException(e);
    }
  }

}
