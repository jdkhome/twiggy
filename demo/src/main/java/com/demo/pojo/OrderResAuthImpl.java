package com.demo.pojo;

import com.jdkhome.twiggy.core.authentication.res.ResAuth;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

/**
 * create by linkji.
 * create at 11:54 2019-12-05
 */
@Service
public class OrderResAuthImpl implements ResAuth<Order> {

    @Override
    public String getExpression() {
        return "accountNo||userId";
    }

    @Override
    public Map<String, String> getValMap(Order resource) {
        Map<String, String> valMap = new HashMap<>();
        valMap.put("accountNo", resource.getAccountNo());
        valMap.put("userId", resource.getUserId().toString());
        return valMap;
    }
}
