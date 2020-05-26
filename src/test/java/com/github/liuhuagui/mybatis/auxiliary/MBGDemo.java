package com.github.liuhuagui.mybatis.auxiliary;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class MBGDemo {

    @Test
    public  void test() {
        //key为表名，value为别名
        Map<String, String> tablesMap = new HashMap<>();
        tablesMap.put("firewall", "f");
        tablesMap.put("firewall_rule", "fr");
        tablesMap.put("firewall_policy", "fp");

        MBG.builder().connectionURL("jdbc:mysql://172.31.133.29:3306/xfyun_exquisite?serverTimezone=GMT%2B8")
                .driverClass("com.mysql.cj.jdbc.Driver")
                .userId("root")
                .password("xfyun_test")
                .javaModelTargetPackage("com.iflytek.xfyun.cloudlego.iaasweb.firewall.do0")
                .javaModelTargetProject("src/main/java")
                .javaClientTargetPackage("com.iflytek.xfyun.cloudlego.iaasweb.firewall.mapper")
                .javaClientTargetProject("src/main/java")
                .tablesMap(tablesMap).build().generate();
    }
}
