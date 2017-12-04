package com.lyw.groovydemo.script

import com.lyw.groovydemo.bean.DataBean
import com.lyw.groovydemo.bean.SpringContextUtil

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
