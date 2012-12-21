package uk.ac.edu4all.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.RedirectStrategy;

public class StayOnPageRedirectStrategy implements RedirectStrategy {

	@Override
	public void sendRedirect(HttpServletRequest request, HttpServletResponse response,
			String arg2) throws IOException {
		System.out.println(request.getHeader("Referer"));
		response.sendRedirect(request.getHeader("Referer"));
	}

}
