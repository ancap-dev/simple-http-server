//- Copyright ©2009 Micah Martin.  All Rights Reserved
//- MMHTTP and all included source files are distributed under terms of the GNU LGPL.

package ru.ancap.simple.http.server.server;

import ru.ancap.simple.http.server.protocol.MockRequest;
import ru.ancap.simple.http.server.protocol.Response;
import ru.ancap.simple.http.server.protocol.SimpleResponse;
import org.junit.Test;
import org.junit.Assert;

public class NotFoundResponderTest extends Assert
{
  @Test
  public void shouldHaveNotFoundResponse() throws Exception
  {
    Response response = new NotFoundResponder().makeResponse(null, new MockRequest("/some/resource"));

    assertEquals(SimpleResponse.class, response.getClass());
    assertEquals(404, response.getStatus());
  }
}
