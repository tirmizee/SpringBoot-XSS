package com.tirmizee.component;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.IOUtils;

public class ContentCachingHttpServletRequest extends HttpServletRequestWrapper {

	private ByteArrayOutputStream cachedBytes;
	
	public ContentCachingHttpServletRequest(HttpServletRequest request) throws IOException {
		super(request);
		this.cachedBytes = new ByteArrayOutputStream(request.getContentLength());
	}
	
	@Override
	public ServletInputStream getInputStream() throws IOException {
		if(cachedBytes.size() == 0) {
			IOUtils.copy(super.getInputStream(), cachedBytes);
		}
		return new CachedServletInputStream();
	}

	@Override
	public BufferedReader getReader() throws IOException{
		return new BufferedReader(new InputStreamReader(this.getInputStream()));
	}
	
	public class CachedServletInputStream extends ServletInputStream {

		private ByteArrayInputStream inputStream;
		
		public CachedServletInputStream() {
			this.inputStream = new ByteArrayInputStream(cachedBytes.toByteArray());
	    }
		
		@Override
		public boolean isFinished() {
			return this.inputStream.available() == 0;
		}

		@Override
		public boolean isReady() {
			return true;
		}

		@Override
		public void setReadListener(ReadListener listener) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int read() throws IOException {
			return this.inputStream.read();
		}
		
	}

}
