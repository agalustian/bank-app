package ru.bank.front.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

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

    if (authentication.getPrincipal() instanceof OidcUser oidcUser) {
      return oidcUser.getPreferredUsername();
    }

    return authentication.getName();
  }

}
