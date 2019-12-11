package com.demo.configuration;

import com.jdkhome.twiggy.common.exception.TwiggyPermissionDeniedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by jdk on 2019/1/7.
 * 异常处理器
 * 处理从Controller抛出的异常
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理无权限异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(TwiggyPermissionDeniedException.class)
    @ResponseBody
    String handleException(TwiggyPermissionDeniedException e, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //e.printStackTrace();
        return "权限不足";
    }


}