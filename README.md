# Groovy demo  

## groovy 介绍

* 基于JAVA虚拟机的灵活的动态语言
* 建立在Java的优势之上，但是还具有Python，Ruby和Smalltalk等语言的特性
* 以近乎零的学习曲线使Java开发人员可以使用现代编程功能
* 提供静态类型检查和静态编译代码的功能，以提高健壮性和性能
* 支持特定于域的语言和其他紧凑的语法，因此您的代码变得易于阅读和维护
* 通过其强大的处理原语，OO能力和Ant DSL，使得编写shell和构建脚本变得容易
* 在开发Web，GUI，数据库或控制台应用程序时，通过减少脚手架代码提高开发人员的生产力
* 通过支持单元测试和开箱即用模拟来简化测试
* 与所有现有的Java类和库无缝集成
* 直接编译为Java字节码，以便您可以在任何可以使用Java的地方使用它

## groovy 运行原理图
![groovy运行原理图](https://raw.githubusercontent.com/LiaoYiWei/groovydemo/master/doc/groovy.png)

## groovy官方文档地址
<http://groovy-lang.org/documentation.html#gettingstarted>

## demo说明
### 文件处理模拟demo  
com.howbuy.groovydemo.script.FileProcessTest 

### 不断加载groovy class不会导致OutOfMemoryException, JDK1.8下通过测试  
com.howbuy.groovydemo.script.GroovyTest

### java调用groovy脚本  
```java
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
```
### groovy脚本使用java对象  
DataProvider groovy类
```groovy
class DataProvider {

    Map<String, String> provideDummyData() {
        def bean = SpringContextUtil.getBean(DataBean.class)
        return bean.generateData()
    }
}
```
DataBean-Java类
```java
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
```
SpringContextUtil-java类
```java
@Component
public class SpringContextUtil implements ApplicationContextAware {
    // Spring应用上下文环境
    private static ApplicationContext applicationContext;

    /**
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境
     *
     * @param applicationContext
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextUtil.applicationContext = applicationContext;
    }

    /**
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 获取对象
     *
     * @param name
     * @return Object
     * @throws BeansException
     */
    public static Object getBean(String name) throws BeansException {
        return applicationContext.getBean(name);
    }

    /**
     * 获取对象
     *
     * @param name
     * @return Object
     * @throws BeansException
     */
    public static <T> T getBean(String name, Class<T> cls) throws BeansException {
        return (T) applicationContext.getBean(name);
    }


    /**
     * 获取对象
     *
     * @param aclass
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> aclass) {
        return applicationContext.getBean(aclass);
    }
}
```







