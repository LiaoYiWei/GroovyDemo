package com.howbuy.groovydemo.bean;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>注释</p>
 *
 * @author liaoyiwei
 */
@Service
public class DataBean {

    public Map<String, Object> generateData() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("name", "张三");
        data.put("age", 25);
        return data;
    }

    public void save(Object object) {
        System.out.println("-----------DataBean save : \n" + object);
    }

}
