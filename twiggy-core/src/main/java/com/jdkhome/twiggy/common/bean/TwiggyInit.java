package com.jdkhome.twiggy.common.bean;

import com.jdkhome.twiggy.core.authorize.TwiggyCacheService;
import com.jdkhome.twiggy.utils.ClassUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jdk on 17/12/22.
 * Authj初始化
 */
@Slf4j
@Component
public class TwiggyInit implements InitializingBean {


    @Value("${twiggy.controller}")
    String packageName;

    @Autowired
    TwiggyCacheService twiggyCacheService;

    private void getAllSupervisionUrl() {

        // 获取所有监管中的方法注解

        List<TwiggyAuthBean> allAuthBean = new ArrayList<>();

        ClassUtil.getClasses(packageName)
                .forEach(aClass -> Arrays.stream(aClass.getMethods())
                        .filter(method -> method.getAnnotation(Twiggy.class) != null)
                        .forEach(method -> allAuthBean.addAll(this.getAuthBeans(method))));


        twiggyCacheService.resetAllAuthBean(allAuthBean);

    }

    private List<TwiggyAuthBean> getAuthBeans(Method method) {

        List<TwiggyAuthBean> authBeans = new ArrayList<>();

        Class theClass = method.getDeclaringClass();

        String[] classUris = null;
        if (theClass.getAnnotation(RequestMapping.class) != null) {
            classUris = ((RequestMapping) theClass.getAnnotation(RequestMapping.class)).value();
        }
        String[] methodUris = method.getAnnotation(RequestMapping.class).value();

        if (classUris != null && classUris.length != 0) {
            Arrays.stream(classUris).forEach(classUri ->
                    Arrays.stream(methodUris).forEach(methodUri -> {
                        StringBuffer uriSb = new StringBuffer();
                        uriSb.append(classUri).append(methodUri);
                        Twiggy twiggy = method.getAnnotation(Twiggy.class);
                        authBeans.add(new TwiggyAuthBean(uriSb.toString(), twiggy));
                    }));
        } else {
            Arrays.stream(methodUris).forEach(methodUri -> {
                StringBuffer uriSb = new StringBuffer();
                uriSb.append(methodUri);
                Twiggy twiggy = method.getAnnotation(Twiggy.class);
                authBeans.add(new TwiggyAuthBean(uriSb.toString(), twiggy));
            });
        }

        return authBeans;
    }

    /**
     * Invoked by a BeanFactory after it has set all bean properties supplied
     * (and satisfied BeanFactoryAware and ApplicationContextAware).
     * <p>This method allows the bean instance to perform initialization only
     * possible when all bean properties have been set and to throw an
     * exception in the event of misconfiguration.
     *
     * @throws Exception in the event of misconfiguration (such
     *                   as failure to set an essential property) or if initialization fails.
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        getAllSupervisionUrl();
    }
}
