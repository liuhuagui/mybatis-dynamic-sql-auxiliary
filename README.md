MybatisDynamicSQL + 简单易用的物理分页+ Dynamic SQL Generator+《阿里巴巴泰山版开发手册》命名规范，在Java8的基础上，极大的简化Mybatis的使用，使你的项目开发飞起，妈妈再也不用担心你要加班了！！

### 快速使用
```xml
<!-- 必选 -->
<dependency>
    <groupId>com.github.liuhuagui</groupId>
    <artifactId>mybatis-dynamic-sql-auxiliary</artifactId>
    <version>1.2.0</version>
</dependency>
<!-- 可选 -->
<dependency>
    <groupId>org.mybatis.generator</groupId>
    <artifactId>mybatis-generator-core</artifactId>
    <version>1.4.0</version>
</dependency>
<!-- 项目自己提供 -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.12</version>
</dependency>
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.5.4</version>
</dependency>
```

#### Demo
```java
public class MBGDemo {

    @Test
    public  void test() {
        //key为表名，value为别名
        Map<String, String> tablesMap = new HashMap<>();
        tablesMap.put("firewall_policy", "fp");

        MBG.builder().connectionURL("你的数据库URL")
                .driverClass("com.mysql.cj.jdbc.Driver")
                .userId("你的数据库用户名")
                .password("你的数据库用户名密码")
                .javaModelTargetPackage("生成的DO类所在的包")
                .javaModelTargetProject("src/main/java")
                .javaClientTargetPackage("生成的Mapper接口所在的包")
                .javaClientTargetProject("src/main/java")
                .tablesMap(tablesMap).build().generate();
    }
}
```