package com.github.liuhuagui.mybatis.auxiliary;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class MBGDemo {

    @Test
    public void test() {
        //key为表名，value为别名
        Map<String, String> tablesMap = new HashMap<>();
        tablesMap.put("question_answer", "qa");

        MBG.builder().connectionURL("你的数据库URL")
                .driverClass("com.mysql.cj.jdbc.Driver")
                .userId("你的数据库用户名")
                .password("你的数据库用户名密码")
                .rootClass("model类的自定义根类，最好是BaseDO的子类")
                .javaModelTargetPackage("生成的DO类所在的包")
                .javaModelTargetProject("src/main/java")
                .javaClientTargetPackage("生成的Mapper接口所在的包")
                .javaClientTargetProject("src/main/java")
                .tablesMap(tablesMap).build().generate();
    }
}
