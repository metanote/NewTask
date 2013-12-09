package com.example.newtask;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import android.util.Log;
import com.example.newtask.SimpleHttpGetTask;

/**
 * This class contains utilities to perform HTTP GET requests and to extract the
 * {@link String} representation of a corresponding {@link HttpResponse}.
 * 
 * @author Gauthier Picard (EMSE) : picard@emse.fr
 * 
 */
public final class HttpUtils {

    public static final int BUFFER_SIZE = 1024;

    /**
     * Returns the {@link String} representation of the content a given
     * {@link HttpEntity}
     * 
     * @param entity
     *            the HttpEntity to analyse
     * @return a String representation of the content
     * @throws IOException
     * @throws ParseException
     */
    public static String getResponseBody(final HttpEntity entity)
	    throws IOException, ParseException {
	if (entity == null)
	    throw new IllegalArgumentException("HTTP entity may not be null");

	InputStream instream = entity.getContent();
	if (instream == null)
	    return "";
	if (entity.getContentLength() > Integer.MAX_VALUE)
	    throw new IllegalArgumentException(
		    "HTTP entity too large to be buffered in memory");

	String charset = HttpUtils.getContentCharSet(entity);
	if (charset == null)
	    charset = HTTP.DEFAULT_CONTENT_CHARSET;

	Reader reader = new InputStreamReader(instream, charset);
	StringBuilder buffer = new StringBuilder();
	try {
	    char[] tmp = new char[BUFFER_SIZE];
	    int l;
	    while ((l = reader.read(tmp)) != -1)
		buffer.append(tmp, 0, l);
	} finally {
	    reader.close();
	}
	return buffer.toString();
    }

    /**
     * Returns the HTTP charset value of a given {@link HttpEntity}
     * 
     * @param entity
     *            the entity to analyse
     * @return a the HTTP charset value of the entity
     * @throws ParseException
     */
    public static String getContentCharSet(final HttpEntity entity)
	    throws ParseException {
	if (entity == null)
	    throw new IllegalArgumentException("HTTP entity may not be null");

	String charset = null;
	if (entity.getContentType() != null) {
	    HeaderElement values[] = entity.getContentType().getElements();
	    if (values.length > 0) {
		NameValuePair param = values[0].getParameterByName("charset");
		if (param != null)
		    charset = param.getValue();
	    }
	}
	return charset;
    }

    /**
     * A helper method that performs the HTTP GET request using the
     * {@link HttpClient} class and returns a {@link String}.
     * 
     * @param path
     *            the path to request
     * @return the resulting {@link String}
     * @throws IOException 
     * @throws ParseException 
     */
    public static String httpGet(String path) {
	DefaultHttpClient httpClient = new DefaultHttpClient();
	HttpGet httpGet = new HttpGet(path);
	HttpResponse response = null;
	String serverResponse = null;
	try {
	    response = httpClient.execute(httpGet);
	} catch (Exception e) {
	    Log.e(SimpleHttpGetTask.class.getCanonicalName(), e.getMessage());
	    e.printStackTrace();
	}
	if (response != null) {
	    HttpEntity entity = response.getEntity();
	    try {
		serverResponse = HttpUtils.getResponseBody(entity);
	    } catch (ParseException e) {
		e.printStackTrace();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
	return serverResponse;
    }

}