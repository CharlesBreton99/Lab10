package filters;

import dataaccess.UserDB;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Role;
import models.User;

/**
 *
 * @author Birdd
 */
public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String email = (String) session.getAttribute("email");
        
        UserDB userDB = new UserDB();
        User user;
        user = null;
        try {
            user = userDB.get(email);
        } catch (Exception e) {
            httpResponse.sendRedirect("login");
            return;
        }
        
        Role role = user.getRole();
        if (role.getRoleId() != 1) {
            httpResponse.sendRedirect("notes");
            return;
        } 
        
        chain.doFilter(request, response);

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

}
