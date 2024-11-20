//package com.video.server.clients.interceptors;
//
//
//import org.springframework.http.HttpHeaders;
//import reactor.util.context.Context;
//import org.springframework.stereotype.Component;
//import org.springframework.util.ObjectUtils;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import feign.RequestInterceptor;
//import feign.RequestTemplate;
//import jakarta.servlet.http.HttpServletRequest;
//
//@Component
//public class OauthFeignInterceptor implements RequestInterceptor{
//
//	@Override
//	public void apply(RequestTemplate template) {
//		final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//		if(!ObjectUtils.isEmpty(requestAttributes)) {
//			final HttpServletRequest httpServletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
//			template.header(HttpHeaders.AUTHORIZATION, httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION));
//			template.header(HttpHeaders.COOKIE, httpServletRequest.getHeader(HttpHeaders.COOKIE));
//		}
//		
//	}
//
//}
