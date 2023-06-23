package com.example.filikov_advanced_server.services.Interceptor;

import com.example.filikov_advanced_server.entity.LogEntity;
import com.example.filikov_advanced_server.repository.LogRepo;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class LogInterceptor implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger(LogInterceptor.class);
    private final LogRepo logRepo;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, @NotNull Object handler, Exception ex) throws Exception {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " " + response.getStatus());
        LogEntity logEntity = new LogEntity();
        logEntity.setRequestMethod(request.getMethod());
        logEntity.setRequestUri(request.getRequestURI());
        logEntity.setResponseStatus(response.getStatus());
        logRepo.save(logEntity);
    }
}