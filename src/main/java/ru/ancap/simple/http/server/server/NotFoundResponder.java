//- Copyright ©2009 Micah Martin.  All Rights Reserved
//- MMHTTP and all included source files are distributed under terms of the GNU LGPL.

package ru.ancap.simple.http.server.server;

import ru.ancap.simple.http.server.protocol.Response;
import ru.ancap.simple.http.server.protocol.Request;
import ru.ancap.simple.http.server.protocol.SimpleResponse;

/**
 * Creates a 404 Not Found response.  Used when the request resource doesn't map to any registered Responders. 
 */
public class NotFoundResponder implements Responder
{
	public Response makeResponse(Server server, Request request) throws Exception
	{
		SimpleResponse response = new SimpleResponse(404);
		response.setContent(makeHtml());
		return response;
	}

	private String makeHtml() throws Exception
	{
    return "<html><head><title>Not Found</title></head><body>Not Found</body></html>";
	}

}
