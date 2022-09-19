package ru.ancap.simple.http.server.server;

import ru.ancap.simple.http.server.protocol.Request;
import ru.ancap.simple.http.server.protocol.Response;

public interface ErrorResponder extends Responder
{
  Response makeResponse(Server server, Request request, Exception e) throws Exception;
}
