package com.achatenligne.web.mapping;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.achatenligne.model.exceptions.InexistantException;

@Provider
public class InexistantExceptionMapper implements ExceptionMapper<InexistantException>{

  @Override
  public Response toResponse(InexistantException exception) {
    return Response.status(Status.NOT_FOUND)
                   .type(MediaType.TEXT_PLAIN)
                   .entity(exception.getMessage())
                   .build();
  }

}
