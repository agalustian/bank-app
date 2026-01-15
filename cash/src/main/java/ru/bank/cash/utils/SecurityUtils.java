package ru.bank.cash.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class SecurityUtils {

  private SecurityUtils() {
  }

  public static String getLogin() {
    var securityContext = SecurityContextHolder.getContext();
    if (securityContext == null) {
      return "";
    }

    Authentication authentication = securityContext.getAuthentication();

    if (authentication == null) {
      return "";
    }

    if (authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) {
      return jwtAuthenticationToken.getToken().getClaimAsString("preferred_username");
    }

    if (authentication.getPrincipal() instanceof UserDetails userDetails) {
      return userDetails.getUsername();
    }

    return authentication.getName();
  }

}
