package com.openchat.secureim.server.controllers;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.openchat.secureim.server.push.NotPushRegisteredException;
import com.openchat.secureim.server.push.ReceiptSender;
import com.openchat.secureim.server.push.TransientPushFailureException;
import com.openchat.secureim.server.storage.Account;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.io.IOException;

import io.dropwizard.auth.Auth;

@Path("/v1/receipt")
public class ReceiptController {

  private final ReceiptSender receiptSender;

  public ReceiptController(ReceiptSender receiptSender) {
    this.receiptSender = receiptSender;
  }

  @Timed
  @PUT
  @Path("/{destination}/{messageId}")
  public void sendDeliveryReceipt(@Auth                     Account source,
                                  @PathParam("destination") String destination,
                                  @PathParam("messageId")   long messageId,
                                  @QueryParam("relay")      Optional<String> relay)
      throws IOException
  {
    try {
      receiptSender.sendReceipt(source, destination, messageId, relay);
    } catch (NoSuchUserException | NotPushRegisteredException e) {
      throw new WebApplicationException(Response.Status.NOT_FOUND);
    } catch (TransientPushFailureException e) {
      throw new IOException(e);
    }
  }

}
