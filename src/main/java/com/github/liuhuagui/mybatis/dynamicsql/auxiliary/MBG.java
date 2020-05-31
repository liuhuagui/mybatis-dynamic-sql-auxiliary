package com.github.liuhuagui.mybatis.dynamicsql.auxiliary;

import lombok.Builder;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Mybatis Generator Configuration based Java.
 *
 * @author KaiKang 799600902@qq.com
 */
@Builder
public class MBG {
    /**
     * Default root class, whose subclass any custom root class should be.
     */
    public static final String DEFAULT_ROOT_CLASS = "com.github.liuhuagui.mybatis.auxiliary.model.BaseDO";

    private String driverClass;

    private String connectionURL;

    private String userId;

    private String password;

    private String rootClass;

    private String javaModelTargetPackage;

    private String javaModelTargetProject;

    private String javaClientTargetPackage;

    private String javaClientTargetProject;

    /**
     * key 完整的表名，value 表的别名
     */
    private Map<String, String> tablesMap;


    public void generate() {
        List<String> warnings = new ArrayList<String>();
        Configuration config = new Configuration();
        Context context = new Context(null);
        context.setId("0");
        context.setTargetRuntime("MyBatis3DynamicSql");
        config.addContext(context);

        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setConnectionURL(this.connectionURL);
        jdbcConnectionConfiguration.setDriverClass(this.driverClass);
        jdbcConnectionConfiguration.setUserId(this.userId);
        jdbcConnectionConfiguration.setPassword(this.password);
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);

        JavaTypeResolverConfiguration javaTypeResolverConfiguration = new JavaTypeResolverConfiguration();
        javaTypeResolverConfiguration.addProperty("useJSR310Types", "true");
        context.setJavaTypeResolverConfiguration(javaTypeResolverConfiguration);

        CommentGeneratorConfiguration commentGeneratorConfiguration = new CommentGeneratorConfiguration();
        commentGeneratorConfiguration.setConfigurationType("com.github.liuhuagui.mybatis.auxiliary.plugin.OnlyRemarkCommentGenerator");
        commentGeneratorConfiguration.addProperty("suppressAllComments", "true");
        context.setCommentGeneratorConfiguration(commentGeneratorConfiguration);

        rootClass = rootClass == null ? DEFAULT_ROOT_CLASS : rootClass;
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        javaModelGeneratorConfiguration.setTargetPackage(this.javaModelTargetPackage);
        javaModelGeneratorConfiguration.setTargetProject(this.javaModelTargetProject);
        javaModelGeneratorConfiguration.addProperty("rootClass", rootClass);
        context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);

        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
        javaClientGeneratorConfiguration.setConfigurationType("ANNOTATEDMAPPER");
        javaClientGeneratorConfiguration.setTargetPackage(this.javaClientTargetPackage);
        javaClientGeneratorConfiguration.setTargetProject(this.javaClientTargetProject);
        context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);

        PluginConfiguration pluginConfiguration = new PluginConfiguration();
        pluginConfiguration.setConfigurationType("org.mybatis.generator.plugins.SerializablePlugin");
        context.addPluginConfiguration(pluginConfiguration);

        PluginConfiguration pluginConfiguration1 = new PluginConfiguration();
        pluginConfiguration1.setConfigurationType("org.mybatis.generator.plugins.UnmergeableXmlMappersPlugin");
        context.addPluginConfiguration(pluginConfiguration1);

        PluginConfiguration pluginConfiguration3 = new PluginConfiguration();
        pluginConfiguration3.setConfigurationType("com.github.liuhuagui.mybatis.auxiliary.plugin.BaseMapperPlugin");
        pluginConfiguration3.addProperty("baseMapper", "com.github.liuhuagui.mybatis.auxiliary.mapper.DynamicSqlMapper");
        context.addPluginConfiguration(pluginConfiguration3);

        PluginConfiguration pluginConfiguration5 = new PluginConfiguration();
        pluginConfiguration5.setConfigurationType("com.github.liuhuagui.mybatis.auxiliary.plugin.LombokPlugin");
        context.addPluginConfiguration(pluginConfiguration5);

        //改变Boolean值映射，去掉is_前缀
        ColumnRenamingRule columnRenamingRule = new ColumnRenamingRule();
        columnRenamingRule.setSearchString("is_");
        columnRenamingRule.setReplaceString("");
        //DO名命规范
        DomainObjectRenamingRule objectRenamingRule = new DomainObjectRenamingRule();
        objectRenamingRule.setSearchString(".+");
        objectRenamingRule.setReplaceString("$0DO");
        for (Map.Entry<String, String> entry : tablesMap.entrySet()) {
            TableConfiguration tableConfiguration = new TableConfiguration(context);
            tableConfiguration.setTableName(entry.getKey());
            tableConfiguration.setAlias(entry.getValue());
            tableConfiguration.setDomainObjectRenamingRule(objectRenamingRule);
            tableConfiguration.setColumnRenamingRule(columnRenamingRule);
            context.addTableConfiguration(tableConfiguration);
        }

        DefaultShellCallback callback = new DefaultShellCallback(true);
        try {
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
        } catch (InvalidConfigurationException | SQLException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
