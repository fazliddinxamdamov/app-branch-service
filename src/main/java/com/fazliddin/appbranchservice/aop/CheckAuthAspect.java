package com.fazliddin.appbranchservice.aop;

import com.fazliddin.appbranchservice.exception.RestException;
import com.fazliddin.appbranchservice.service.CacheService;
import com.fazliddin.appbranchservice.utils.CommonUtils;
import com.fazliddin.library.enums.PermissionEnum;
import com.fazliddin.library.enums.RoleTypeEnum;
import com.fazliddin.library.payload.UserDto;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Aspect
@Order(1)
@Component
@RequiredArgsConstructor
public class CheckAuthAspect {
    private final CacheService cacheService;
    @Before(value = "@annotation(checkAuth)")
    public void checkAuthExecutor(CheckAuth checkAuth) {
        check(checkAuth);
    }


    public void check(CheckAuth checkAuth) {
        PermissionEnum[] permissions = checkAuth.permissions();
        RoleTypeEnum[] roleTypeEnums = checkAuth.roles();
        Set<String> authoritiesForCheck = Arrays.stream(permissions).map(PermissionEnum::name).collect(Collectors.toSet());
        authoritiesForCheck.addAll(Arrays.stream(roleTypeEnums).map(RoleTypeEnum::name).collect(Collectors.toSet()));

        HttpServletRequest httpServletRequest = CommonUtils.getCurrentRequest();

        String token = httpServletRequest.getHeader("Authorization");
        if (token == null) throw RestException.forbidden();

        UserDto userDto = cacheService.getUserByToken(token);

        Set<String> userAuthorities = userDto.getAuthorities();

        boolean exists = false;
        for (String authority : authoritiesForCheck) {
            exists = userAuthorities.contains(authority);
        }

        if (!exists) throw RestException.forbidden();

        httpServletRequest.setAttribute("user", userDto);
    }


//    public void check(CheckAuth checkAuth) {
//        try {
//            PermissionEnum[] permissions = checkAuth.permissions();
//            RoleTypeEnum[] roleTypeEnums = checkAuth.roles();
//
//            HttpServletRequest httpServletRequest = CommonUtils.getCurrentRequest();
//
//            String token = httpServletRequest.getHeader("Authorization");
//            if (token == null) throw RestException.forbidden();
//
//            ApiResult<UserDto> apiResult = authFeign.checkAuth(token, new CheckAuthDto(permissions, roleTypeEnums));
//
//            if (!apiResult.isSuccess()) throw RestException.forbidden();
//
//            UserDto userDto = apiResult.getData();
//
//            httpServletRequest.setAttribute("user", userDto);
//
//        } catch (Exception e) {
//            throw RestException.forbidden();
//        }
//    }
}
