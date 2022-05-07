package com.fazliddin.appbranchservice.utils;

import com.fazliddin.appbranchservice.exception.RestException;
import com.fazliddin.library.payload.UserDto;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;

/**
 * This class not documented :(
 *
 * @author Muhammad Mo'minov
 * @since 17.12.2021
 */
@Component
public class CommonUtils {
    public static HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest httpServletRequest = Optional.of(requestAttributes).map(ServletRequestAttributes::getRequest).orElse(null);
        return httpServletRequest;
    }

    public static UserDto getCurrentUser() {
        try {
            HttpServletRequest currentRequest = getCurrentRequest();
            UserDto user = (UserDto) currentRequest.getAttribute("user");
            if (user == null || Objects.isNull(user.getId())) throw RestException.forbidden();
            return user;
        } catch (Exception e) {
            throw RestException.forbidden();
        }
    }

}
