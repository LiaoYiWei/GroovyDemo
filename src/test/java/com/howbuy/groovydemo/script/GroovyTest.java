package com.howbuy.groovydemo.script;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.util.GroovyScriptEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
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
    @Test
    public void loadGroovyScript() throws Exception {
        String fileStr = "/Users/wesleyliao/IdeaProjects/microservice-archetype/archetype-inrpc/src/main/java/com/lyw/archetype/inrpc/script/HelloWord.groovy";
        String fileStr1 = "/Users/wesleyliao/IdeaProjects/microservice-archetype/archetype-inrpc/src/main/java/com/lyw/archetype/inrpc/script/Verify.groovy";
        GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
        Class aClass = groovyClassLoader.parseClass(new File(fileStr));
        Object o = aClass.newInstance();
        Method method = aClass.getMethod("invokeSpringBean", String.class);
        Object hello = method.invoke(o, "hello");
        System.out.println(hello);
    }

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

    @Test
    public void groovyScriptEngine() throws Exception {
        Binding binding = new Binding();
        String s = "/Users/wesleyliao/IdeaProjects/microservice-archetype/archetype-inrpc/src/main/java/com/lyw/archetype/inrpc/script/";
        File file = new File(s);
        System.out.println("\n\n\n\n\n" + file.toURI() + "\n\n\n\n");
        System.out.println("\n\n\n\n\n" + file.toURI().toURL() + "\n\n\n\n");
        GroovyScriptEngine engine = new GroovyScriptEngine(new URL[]{file.toURI().toURL()});
        while (true) {
            Object run = engine.run("ReloadingTest.groovy", binding);
            System.out.println(run.getClass().getMethod("sayHello").invoke(run));
            Thread.sleep(1000);
        }
    }
}