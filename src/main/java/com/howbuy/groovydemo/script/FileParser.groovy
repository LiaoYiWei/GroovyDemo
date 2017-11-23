package com.howbuy.groovydemo.script

import com.howbuy.groovydemo.bean.DataBean
import com.howbuy.groovydemo.bean.SpringContextUtil

/**
 * <p>注释</p>
 * @author liaoyiwei
 *
 */
class FileParser {

    String readContent() {
        def list = new File('src/main/resources/downloadFile').collect { it }
        def reduce = list.stream().reduce({ s1, s2 -> s1.concat('\n' + s2) })
        return reduce.get()
    }

    void parseAndSaveContent(String content) {
        println content
        SpringContextUtil.getBean(DataBean.class).save(content)
    }


    static void main(String[] args) {
        def file = new File('src/main/resources/downloadFile')
        println file.toURI().toString()
        def parser = new FileParser()
        println parser.readContent()
    }

}
