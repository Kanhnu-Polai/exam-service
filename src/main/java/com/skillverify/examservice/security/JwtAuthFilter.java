package com.skillverify.examservice.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.skillverify.examservice.enums.ErrorCodeEnum;
import com.skillverify.examservice.exceptions.MissingAuthorizationHeaderException;
import com.skillverify.examservice.http.HttpAuthServiceEngine;
import com.skillverify.examservice.http.ValidateResponse;
import com.skillverify.examservice.utils.ExceptionResponseUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j

public class JwtAuthFilter extends OncePerRequestFilter {
	
	
	private final HttpAuthServiceEngine httpAuthServiceEngine;
	
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		log.info("JwtAuthFilter || Intercepted request: {}", request.getRequestURI());

		String token = getTokenFromHeader(request);

		if (token == null) {
			log.warn("JwtAuthFilter || Missing or malformed Authorization header.");
			ExceptionResponseUtil.sendErrorResponse(
			        response,
			        "Missing or malformed Authorization header",
			        "5007",
			        "EXAM_SERVICE"
			    );
			    return;
		}

		log.info("JwtAuthFilter || doFilterInternal() called for token: {}", token);
		try {
			ValidateResponse validateResponse = httpAuthServiceEngine.makeAuthServiceValidationCall(token);
			log.info("JwtAuthFilter || Response from auth service: {}", validateResponse);

			if (!"valid".equalsIgnoreCase(validateResponse.getStatus())) {
				throw new RuntimeException("Invalid user status");
			}

			Authentication authentication = new UsernamePasswordAuthenticationToken(validateResponse.getEmail(), null, List.of());
			SecurityContextHolder.getContext().setAuthentication(authentication);
			log.info("JwtAuthFilter || Authenticated email set: {}", validateResponse.getEmail());

		} catch (Exception e) {
			log.error("JwtAuthFilter || Error validating token: {}", e.getMessage());
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType("application/json");

			String message = e.getMessage().toLowerCase().contains("expired") ?
					"Token has expired" : "Invalid token";

			response.getWriter().write("{\"error\": \"" + message + "\"}");
			return;
		}

		filterChain.doFilter(request, response);
	}
	
	
	
	
	
public String getTokenFromHeader(HttpServletRequest request) {
		
		String authorizationHeader = request.getHeader("Authorization");
		
		if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			return authorizationHeader.substring(7);
		}
		
		return null;
		
	}

}
