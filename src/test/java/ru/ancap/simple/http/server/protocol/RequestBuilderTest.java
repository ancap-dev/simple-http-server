//- Copyright ©2009 Micah Martin.  All Rights Reserved
//- MMHTTP and all included source files are distributed under terms of the GNU LGPL.

package ru.ancap.simple.http.server.protocol;

import java.io.ByteArrayInputStream;

import junit.framework.TestCase;
import ru.ancap.simple.http.server.testutil.RegexTest;

public class RequestBuilderTest extends TestCase
{
	private RequestBuilder builder;

	public void setUp()
	{
		builder = new RequestBuilder("/");
	}

	public void testDeafultValues() throws Exception
	{
		builder = new RequestBuilder("/someResource");
		String text = builder.getText();
		RegexTest.assertHasRegexp("GET /someResource HTTP/1.1\r\n", text);
	}

	public void testHostHeader_RFC2616_section_14_23() throws Exception
	{
		builder = new RequestBuilder("/someResource");
		String text = builder.getText();
		RegexTest.assertSubString("Host: \r\n", text);
		
		builder.setHostAndPort("some.host.com", 123);
		text = builder.getText();
		RegexTest.assertSubString("Host: some.host.com:123\r\n", text);
	}

	public void testChangingMethod() throws Exception
	{
		builder.setMethod("POST");
		String text = builder.getText();
		RegexTest.assertHasRegexp("POST / HTTP/1.1\r\n", text);
	}

	public void testAddInput() throws Exception
	{
		builder.addInput("responder", "saveData");
		String content = "!fixture fit.ColumnFixture\n" +
				                                "\n" +
				                                "!path classes\n" +
				                                "\n" +
				                                "!2 ";
		builder.addInput("pageContent", content);

		String inputString = builder.queryString();
		RegexTest.assertSubString("responder=saveData", inputString);
		RegexTest.assertSubString("pageContent=%21fixture+fit.ColumnFixture%0A%0A%21path+classes%0A%0A%212+", inputString);
		RegexTest.assertSubString("&", inputString);
	}

	public void testGETMethodWithInputs() throws Exception
	{
		builder.addInput("key", "value");
		String text = builder.getText();
		RegexTest.assertSubString("GET /?key=value HTTP/1.1\r\n", text);
	}

	public void testPOSTMethodWithInputs() throws Exception
	{
		builder.setMethod("POST");
		builder.addInput("key", "value");
		String text = builder.getText();
		RegexTest.assertSubString("POST / HTTP/1.1\r\n", text);
		RegexTest.assertSubString("key=value", text);
	}

	public void testAddingCredentials() throws Exception
	{
		builder.addCredentials("Aladdin", "open sesame");
		RegexTest.assertSubString("Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==", builder.getText());
	}

	public void testGetBoundary() throws Exception
	{
		String boundary = builder.getBoundary();

		assertEquals(boundary, builder.getBoundary());
		Thread.sleep(10);
		assertFalse(boundary.equals(new RequestBuilder("blah").getBoundary()));
	}

	public void testMultipartOnePart() throws Exception
	{
    builder.addInputAsPart("myPart", "part data");
		String text = builder.getText();

		RegexTest.assertSubString("POST", text);
		RegexTest.assertSubString("Content-Type: multipart/form-data; boundary=", text);
		String boundary = builder.getBoundary();
		RegexTest.assertSubString("--" + boundary, text);
		RegexTest.assertSubString("\r\n\r\npart data\r\n", text);
    RegexTest.assertSubString("--" + boundary + "--", text);
	}

	public void testMultipartWithInputStream() throws Exception
	{
		ByteArrayInputStream input = new ByteArrayInputStream("data from input stream".getBytes());
		builder.addInputAsPart("input", input, 89, "text/html");
		String text = builder.getText();

		RegexTest.assertSubString("Content-Type: text/html", text);
		RegexTest.assertSubString("\r\n\r\ndata from input stream\r\n", text);
	}

	public void testMultipartWithRequestParser() throws Exception
	{
    builder.addInputAsPart("part1", "data 1");
		builder.addInput("input1", "input1 value");
		builder.addInputAsPart("part2", "data 2");
		String text = builder.getText();

		Request request = new Request(new ByteArrayInputStream(text.getBytes()));
		request.parse();
		assertEquals("data 1", request.getInput("part1"));
		assertEquals("data 2", request.getInput("part2"));
		assertEquals("input1 value", request.getInput("input1"));
	}
}