package com.howbuy.groovydemo.script;

import groovy.lang.GroovyClassLoader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * <p>注释</p>
 *
 * @author liaoyiwei
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GroovyTest {

    String fileParser = "src/main/java/com/howbuy/groovydemo/script/FileParser.groovy";

    /**
     *
     * 测试不断的加载class是否会导致metaspace溢出
     * jstat -gc 6361 2s
     * MC metaspace capability
     * MU mataspace used
     *
     * FGC 数量增大
     *
     * @throws IOException
     */
    @Test
    public void testOutOfMemory() throws IOException {
        String s = new String(Files.readAllBytes(Paths.get(fileParser)));
        System.out.println(s);
        GroovyClassLoader gcl = new GroovyClassLoader();
        Class clazz1;
        Class clazz2;
        while (true) {
            clazz1 = gcl.parseClass(s);
            clazz2 = gcl.parseClass(s);
            assert clazz1 != clazz2;
        }
    }

}