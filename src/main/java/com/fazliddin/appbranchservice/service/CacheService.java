package com.fazliddin.appbranchservice.service;

import com.fazliddin.appbranchservice.exception.RestException;
import com.fazliddin.appbranchservice.feign.AuthFeign;
import com.fazliddin.library.payload.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CacheService {
    private final AuthFeign authFeign;

    @Cacheable(value = "users", key = "#token")
    public UserDto getUserByToken(String token) {
        return authFeign.checkAuth(token).orElseThrow(RestException::forbidden);
    }

}
