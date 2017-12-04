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
    String fileGenerator = "src/main/java/com/howbuy/groovydemo/script/FileGenerator.groovy";
    String fileParser = "src/main/java/com/howbuy/groovydemo/script/FileParser.groovy";

    @Test
    public void parseFile() throws Exception {
        //1.加载fileParser脚本并解析为Java class
        GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
        Class parseFileCls = groovyClassLoader.parseClass(new File(fileParser));
        //2.实例化DataProvider
        Object parseFileInstance = parseFileCls.newInstance();
        //3.调用readContent方法获取文件内容
        System.out.println("获取文件内容...");
        String fileContent = (String) invokeMethod(parseFileCls, parseFileInstance, "readContent", new ArrayList<>());
        System.out.println("----------------文件内容是 ------------\n" + fileContent);
        //4.调用parseAndSaveContent方法解析并保存内容
        System.out.println("----------解析并保存内容--------");
        invokeMethod(parseFileCls, parseFileInstance, "parseAndSaveContent", Lists.newArrayList(String.class), fileContent);
    }

    @Test
    public void generateFile() throws Exception {
        //1.加载dataProvider脚本并解析为Java class
        GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
        Class dataProviderCls = groovyClassLoader.parseClass(new File(dataProvider));
        //2.实例化DataProvider
        Object dataProviderInstance = dataProviderCls.newInstance();
        //3.调用provideDummyData方法获取Map数据
        Map<String, Object> provideDummyData = (Map<String, Object>) invokeMethod(dataProviderCls, dataProviderInstance, "provideDummyData", new ArrayList<>());
        System.out.println("从脚本中获取的数据...\n" + provideDummyData);
        //4.加载fileGenerator脚本并解析为Java class
        Class FileGeneratorCls = groovyClassLoader.parseClass(new File(fileGenerator));
        //5.实例化fileGenerator
        Object fileGeneratorInstance = FileGeneratorCls.newInstance();
        System.out.println("生成文件...\n" + provideDummyData);
        //5.调用generateFile方法生成文件
        invokeMethod(FileGeneratorCls, fileGeneratorInstance, "generateFile", Lists.newArrayList(Map.class), provideDummyData);
    }

    private Object invokeMethod(Class cls, Object instance, String methodName, List<Class> params, Object... args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method method = cls.getMethod(methodName, params.toArray(new Class[]{}));
        return method.invoke(instance, args);
    }
}