package com.jdkhome.twiggy.core.authentication.res;


import com.jdkhome.twiggy.common.bean.AuthorizedEntity;
import com.jdkhome.twiggy.common.constants.TwiggySystemCons;
import com.jdkhome.twiggy.core.authorize.TwiggyAuthorizeService;
import com.jdkhome.twiggy.core.authorize.TwiggyVirtualTokenService;
import com.jdkhome.twiggy.utils.TwiggyApplicationContextProvider;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

import java.util.stream.Stream;

/**
 * create by linkji.
 * create at 15:21 2019-11-29
 * 资源鉴权接口
 */
public interface ResAuth<T> {

    /**
     * 资源鉴权表达式
     *
     * @return
     */
    String getExpression();

    /**
     * 实体资源与鉴权项的映射
     *
     * @param resource
     * @return
     */
    Map<String, String> getValMap(T resource);

    /**
     * 从当前的request 中获取到授权实体
     *
     * @return
     */
    default Map<String, Set<String>> getThisPermissions() {
        // 获取Request信息
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();

        // 取出vToken
        String vToken = request.getHeader(TwiggySystemCons.TOKEN_NAME);
        AuthorizedEntity authorizedEntity = TwiggyApplicationContextProvider.getBean(TwiggyVirtualTokenService.class)
                .getAuthorizedEntityByVirtualToken(vToken);

        if (authorizedEntity != null) {
            return authorizedEntity.getResAuthMap();
        }
        return null;
    }

    /**
     * 对单个资源进行鉴权
     *
     * @param resource 被鉴权的资源
     * @return
     */
    default boolean authOne(T resource) {
        return ResExpression.calcExp(this.getExpression(), this.getValMap(resource), this.getThisPermissions());
    }

    /**
     * 对流进行鉴权
     *
     * @param resourceStream 被鉴权的资源流
     * @return
     */
    default Stream<T> authStream(Stream<T> resourceStream) {
        return resourceStream.filter(this::authOne);
    }


    default boolean checkExp(String exp, Map<String, String> valMap, Map<String, Set<String>> thisPermissions) {
        return ResExpression.calcExp(exp, valMap, thisPermissions);
    }

}
