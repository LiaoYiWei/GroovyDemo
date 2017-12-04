package com.howbuy.groovydemo.script

import com.howbuy.groovydemo.bean.DataBean
import com.howbuy.groovydemo.bean.SpringContextUtil

/**
 * <p>注释</p>
 * @author liaoyiwei
 *
 */
class DataProvider {

    Map<String, String> provideDummyData() {
        def bean = SpringContextUtil.getBean(DataBean.class)
        return bean.generateData()
    }
}
