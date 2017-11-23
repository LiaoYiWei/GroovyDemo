package com.howbuy.groovydemo.script;

import groovy.lang.GroovyClassLoader;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>注释</p>
 *
 * @author liaoyiwei
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FileProcessTest {

    String dataProvider = "src/main/java/com/howbuy/groovydemo/script/DataProvider.groovy";
    String FileGenerator = "src/main/java/com/howbuy/groovydemo/script/FileGenerator.groovy";
    String fileParser = "src/main/java/com/howbuy/groovydemo/script/FileParser.groovy";

    @Test
    public void generateFile() throws Exception {
        GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
        Class dataProviderCls = groovyClassLoader.parseClass(new File(dataProvider));
        Object dataProviderInstance = dataProviderCls.newInstance();
        Map<String, Object> provideDummyData = (Map<String, Object>) invokeMethod(dataProviderCls, dataProviderInstance, "provideDummyData", new ArrayList<>());
        System.out.println("get Data from script...\n" + provideDummyData);
        Class FileGeneratorCls = groovyClassLoader.parseClass(new File(FileGenerator));
        Object fileGeneratorInstance = FileGeneratorCls.newInstance();
        System.out.println("generate file...\n" + provideDummyData);
        invokeMethod(FileGeneratorCls, fileGeneratorInstance, "generateFile", Lists.newArrayList(Map.class), provideDummyData);
    }

    @Test
    public void parseFile() throws Exception {
        GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
        Class parseFileCls = groovyClassLoader.parseClass(new File(fileParser));
        Object parseFileInstance = parseFileCls.newInstance();
        System.out.println("get file content from script...");
        String fileContent = (String) invokeMethod(parseFileCls, parseFileInstance, "readContent", new ArrayList<>());
        System.out.println("----------------content is ------------\n" + fileContent);
        System.out.println("----------parse and save content--------");
        invokeMethod(parseFileCls, parseFileInstance, "parseAndSaveContent", Lists.newArrayList(String.class), fileContent);
    }

    private Object invokeMethod(Class cls, Object instance, String methodName, List<Class> params, Object... args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method method = cls.getMethod(methodName, params.toArray(new Class[]{}));
        return method.invoke(instance, args);
    }
}