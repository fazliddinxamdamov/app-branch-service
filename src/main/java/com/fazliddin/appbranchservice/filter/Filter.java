package com.fazliddin.appbranchservice.filter;

import ai.ecma.appbranchservice.common.MessageService;
import ai.ecma.lib.payload.ApiResult;
import ai.ecma.lib.payload.ErrorData;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * This class not documented :(
 *
 * @author Muhammad Mo'minov
 * @since 20.01.2022
 */
@Component
@RequiredArgsConstructor
public class Filter extends OncePerRequestFilter {
    private final Gson gson;

    private final Map<String, String> clients = Map.of(
            "gatewayServiceUsername", "gatewayServicePassword",
            "authServiceUsername", "authServicePassword",
            "orderServiceUsername", "orderServicePassword",
            "productServiceUsername", "productServicePassword"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("Welcome to filter!");

        String requestUsername = request.getHeader("serviceUsername");
        String requestPassword = request.getHeader("servicePassword");

        if (!checkUsernameAndPassword(requestUsername, requestPassword)) {
            ApiResult<Object> apiResult = new ApiResult<>(false, List.of(new ErrorData(MessageService.getMessage("FORBIDDEN"), 403)));

            response.getWriter().write(gson.toJson(apiResult));
            response.setStatus(403);
            response.setContentType("application/json");
            return;
        }
        filterChain.doFilter(request, response);
    }

    private boolean checkUsernameAndPassword(String requestUsername, String requestPassword) {
        try {
            String password = clients.get(requestUsername);
            return Objects.equals(requestPassword, password);
        } catch (Exception e) {
            return false;
        }
    }

}
