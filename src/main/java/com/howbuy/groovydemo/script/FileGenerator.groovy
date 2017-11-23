package com.howbuy.groovydemo.script

/**
 * <p>注释</p>
 * @author liaoyiwei
 *
 */
class FileGenerator {

    void generateFile(Map<String, Object> data) {
        def file = new File('lctTestFile.txt')
        file.withWriter('utf-8') { writer ->
            writer.writeLine  data.toString()
        }
        println "generated File at location :" + file.getAbsolutePath()
    }
}
