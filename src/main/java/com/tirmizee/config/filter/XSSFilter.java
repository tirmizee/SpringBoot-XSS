package com.tirmizee.config.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tirmizee.component.ContentCachingHttpServletRequest;

@Component
public class XSSFilter extends OncePerRequestFilter {

	private static final Logger log = LoggerFactory.getLogger(XSSFilter.class);
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		ContentCachingHttpServletRequest wrappedRequest = new ContentCachingHttpServletRequest(request);
		String payload = this.getPayload(wrappedRequest);
//		String safe = Jsoup.clean(payload, Safelist.basic());
		log.info(payload);
		filterChain.doFilter(wrappedRequest, response);
	}

	private String getPayload(ContentCachingHttpServletRequest wrappedRequest) throws IOException {
		ServletInputStream inputStream = wrappedRequest.getInputStream();
		String encoding = wrappedRequest.getCharacterEncoding();
		return IOUtils.toString(inputStream, encoding);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return super.shouldNotFilter(request);
	}
	
}
