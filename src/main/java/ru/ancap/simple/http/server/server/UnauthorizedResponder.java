//- Copyright ©2009 Micah Martin.  All Rights Reserved
//- MMHTTP and all included source files are distributed under terms of the GNU LGPL.

package ru.ancap.simple.http.server.server;

import ru.ancap.simple.http.server.protocol.Request;
import ru.ancap.simple.http.server.protocol.Response;
import ru.ancap.simple.http.server.protocol.SimpleResponse;

/**
 * Generates a 401 response which will usually cause the browser to promt the user for credentials.
 */
public class UnauthorizedResponder implements Responder
{
	public Response makeResponse(Server server, Request request) throws Exception
	{
		SimpleResponse response = new SimpleResponse(401);
		response.addHeader("WWW-Authenticate", "Basic realm=\"" + server.realm + "\"");

		response.setContent("<html><head><title>Unauthorized</title></head><body>Unauthorized</body></html>");

		return response;
	}
}
