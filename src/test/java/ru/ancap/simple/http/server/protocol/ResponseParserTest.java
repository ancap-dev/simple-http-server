//- Copyright ©2009 Micah Martin.  All Rights Reserved
//- MMHTTP and all included source files are distributed under terms of the GNU LGPL.

package ru.ancap.simple.http.server.protocol;

import junit.framework.*;

import java.io.*;

public class ResponseParserTest extends TestCase
{
	private String response;
	private InputStream input;

	public void setUp() throws Exception
	{
	}

	public void tearDown() throws Exception
	{
	}

	public void testParsing() throws Exception
	{
		response = "HTTP/1.1 200 OK\r\n" +
		  "Content-Type: text/html\r\n" +
		  "Content-Length: 12\r\n" +
		  "Cache-Control: max-age=0\r\n" +
		  "\r\n" +
		  "some content";
		input = new ByteArrayInputStream(response.getBytes());

		ResponseParser parser = new ResponseParser(input);
		assertEquals(200, parser.getStatus());
		assertEquals("text/html", parser.getHeader("Content-Type"));
		assertEquals("some content", parser.getBody());
	}

	public void testChunkedResponse() throws Exception
	{
		response = "HTTP/1.1 200 OK\r\n" +
		  "Content-Type: text/html\r\n" +
		  "Transfer-Encoding: chunked\r\n" +
		  "\r\n" +
		  "3\r\n" +
		  "123\r\n" +
		  "7\r\n" +
		  "4567890\r\n" +
		  "0\r\n" +
		  "Tail-Header: TheEnd!\r\n";
		input = new ByteArrayInputStream(response.getBytes());

		ResponseParser parser = new ResponseParser(input);
		assertEquals(200, parser.getStatus());
		assertEquals("text/html", parser.getHeader("Content-Type"));
		assertEquals("1234567890", parser.getBody());
		assertEquals("TheEnd!", parser.getHeader("Tail-Header"));
	}
}
