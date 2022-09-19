//- Copyright ©2009 Micah Martin.  All Rights Reserved
//- MMHTTP and all included source files are distributed under terms of the GNU LGPL.

package ru.ancap.simple.http.server.server;

import ru.ancap.simple.http.server.protocol.Request;
import ru.ancap.simple.http.server.protocol.Response;
import ru.ancap.simple.http.server.protocol.SimpleResponse;

public class MockResponder implements Responder
{
  public Response makeResponse(Server server, Request request) throws Exception
  {
    return new SimpleResponse(200);
  }
}
