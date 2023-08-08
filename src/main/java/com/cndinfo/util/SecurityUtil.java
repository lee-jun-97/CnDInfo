package com.cndinfo.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

	/* Authentication의 Email 주소 가져옴. */
	public String getEmail() {
		return String.valueOf(getAuthentication().getPrincipal());
	}
	
	/* SecurityContextHolder 내부에 저장되어 있는 Authentication 가져옴 */
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
}
