package topworker.view.utils;

/**
 * Created by echomil on 06.03.16.
 */

import org.jdal.vaadin.VaadinUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;


public class AuthenticationService {


    public void handleAuthentication(String login, String password) {

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(login, password);
        HttpServletRequest h = VaadinUtils.getRequest();
        token.setDetails(new WebAuthenticationDetails(h));
        ServletContext servletContext = VaadinUtils.getSession().getServletContext();

        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);

        AuthenticationManager authManager = wac.getBean(AuthenticationManager.class);

        Authentication authentication = authManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public void handleLogout() {

        ServletContext servletContext = VaadinUtils.getSession().getServletContext();

        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);

        LogoutHandler logoutHandler = wac.getBean(LogoutHandler.class);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Response should not be used?
        HttpServletRequest h = VaadinUtils.getRequest();

        logoutHandler.logout(h, null, authentication);
    }

    public boolean isAuthenticated() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return false;
        } else {
            return auth.isAuthenticated();
        }
    }


}
