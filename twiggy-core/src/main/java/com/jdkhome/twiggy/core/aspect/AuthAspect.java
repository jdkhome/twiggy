package com.jdkhome.twiggy.core.aspect;

import com.jdkhome.twiggy.common.constants.TwiggySystemCons;
import com.jdkhome.twiggy.core.authentication.res.ResExpression;
import com.jdkhome.twiggy.common.bean.AuthorizedEntity;
import com.jdkhome.twiggy.core.authorize.TwiggyAuthorizeService;
import com.jdkhome.twiggy.core.authorize.TwiggyCacheService;
import com.jdkhome.twiggy.common.bean.TwiggyAuthBean;
import com.jdkhome.twiggy.common.bean.TwiggyRes;
import com.jdkhome.twiggy.common.exception.TwiggyPermissionDeniedException;
import com.jdkhome.twiggy.core.authorize.TwiggyVirtualTokenService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * create by linkji.
 * create at 11:08 2019-12-06
 * 鉴权切面
 */
@Slf4j
@Aspect
@Component
public class AuthAspect {

    @Autowired
    TwiggyCacheService twiggyCacheService;

    @Autowired
    TwiggyVirtualTokenService twiggyVirtualTokenService;

    // 切入同时包含RequestMapping 和 Twiggy 注解的方法
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping) " +
            "&& @annotation(com.jdkhome.twiggy.common.bean.Twiggy)")
    private void authAspect() {
    }

    //请求method前打印内容
    @Before(value = "authAspect()")
    public void methodBefore(JoinPoint joinPoint) {

        // 获取Request信息
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();

        // 请求URI
        String uri = request.getServletPath();

        // 获取权限实体
        TwiggyAuthBean authBean = twiggyCacheService.getAuthBean(uri);

        if (authBean != null) {

            // 取出v-token
            String vToken = request.getHeader(TwiggySystemCons.TOKEN_NAME);

            AuthorizedEntity authorizedEntity = twiggyVirtualTokenService.getAuthorizedEntityByVirtualToken(vToken);

            if (authBean.getFun().length() > 0) {

                if (authorizedEntity == null) {
                    throw new TwiggyPermissionDeniedException();
                }

                // 获取当前token的权限实体
                if (!authorizedEntity.getFunAuthSet().contains(uri)) {
                    throw new TwiggyPermissionDeniedException();
                }

            }

            if (authBean.getRes().length() > 0) {

                if (authorizedEntity == null) {
                    throw new TwiggyPermissionDeniedException();
                }

                // 取出需要鉴权的请求参数
                Map<String, String> resMap = new HashMap<>();

                MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
                Method method = methodSignature.getMethod();
                Annotation[][] parameterAnnotations = method.getParameterAnnotations();
                Class[] parameterTypes = method.getParameterTypes();

                int i = 0;
                for (Annotation[] annotations : parameterAnnotations) {
                    Class parameterType = parameterTypes[i];
                    int finalI = i;
                    Arrays.stream(annotations)
                            .filter(annotation -> annotation instanceof Valid) // 标了校验注解的参数
                            .forEach(annotation -> Arrays.stream(parameterType.getDeclaredFields())
                                    .filter(field -> field.getAnnotation(TwiggyRes.class) != null)
                                    .forEach(field -> {
                                                TwiggyRes twiggyRes = field.getAnnotation(TwiggyRes.class);
                                                try {
                                                    // 资源Key
                                                    String resKey = twiggyRes.value();
                                                    if (resKey.length() == 0) {
                                                        resKey = field.getName();
                                                    }

                                                    // 资源值
                                                    String resVal = null;
                                                    field.setAccessible(true);
                                                    Object val = field.get(joinPoint.getArgs()[finalI]);
                                                    if (val != null) {
                                                        resVal = val.toString();
                                                        resMap.put(resKey, resVal);
                                                    }
                                                } catch (Exception e) {
                                                    // 跳过
                                                }
                                            }
                                    ));
                    i++;
                }

                // 数据权限校验
                if (!ResExpression.calcExp(authBean.getRes(), resMap, authorizedEntity.getResAuthMap())) {
                    throw new TwiggyPermissionDeniedException();
                }

            }

        }


    }

}
