package com.github.liuhuagui.mybatis.auxiliary.plugin;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;
import java.util.Properties;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

/**
 * When JavaModelGenerator's rootClass property and {@link org.mybatis.generator.plugins.FluentBuilderMethodsPlugin}
 * exist at the same time, the plugin is optionally used to support generic recursion, ensuring that the methods of rootClass
 * return the correct subtype without using unrecognized casts.<br/>
 * Your rootClass might look like following.
 * <pre class="code">
 * public abstract class Base&lt;T extends Base&lt;T>> {
 * protected abstract T this();
 * }
 * </pre>
 *
 * @author KaiKang 799600902@qq.com
 * @apiNote Because of JavaBeans property naming conventions, If the returnThisMethod starts with get,
 * then it may cause a direct self-reference leading to cycle error when converted to JSON or other format data.
 */
public class GenericRecursionSupportPlugin extends PluginAdapter {
    //if returnThisMethod property is not specified, "_this" is used.
    private String returnThisMethod = "_this";

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
        String returnThisMethod = properties.getProperty("returnThisMethod");
        //Because of JavaBeans property naming conventions, If the method name starts with get,
        //then it may cause a direct self-reference leading to cycle error when converted to JSON or other format data.
        if (stringHasValue(returnThisMethod)) {
            if (returnThisMethod.startsWith("get"))
                throw new IllegalArgumentException("The format of returnThisMethod property is illegal.");
            else
                this.returnThisMethod = returnThisMethod;
        }
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,
                                                 IntrospectedTable introspectedTable) {
        makeGeneric(topLevelClass);
        makeMethod(topLevelClass, introspectedTable);
        return true;
    }


    @Override
    public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass,
                                                 IntrospectedTable introspectedTable) {
        makeGeneric(topLevelClass);
        makeMethod(topLevelClass, introspectedTable);
        return true;
    }

    @Override
    public boolean modelRecordWithBLOBsClassGenerated(
            TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        makeGeneric(topLevelClass);
        makeMethod(topLevelClass, introspectedTable);
        return true;
    }

    private void makeGeneric(TopLevelClass topLevelClass) {
        topLevelClass.getSuperClass().ifPresent(superClassWithNoArguments -> {
            FullyQualifiedJavaType superClassWithArguments = new FullyQualifiedJavaType(superClassWithNoArguments.getFullyQualifiedName());
            superClassWithArguments.addTypeArgument(topLevelClass.getType());
            topLevelClass.setSuperClass(superClassWithArguments);
        });
    }


    private void makeMethod(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        Method fluentMethod = new Method(returnThisMethod);
        fluentMethod.addAnnotation("@Override");
        fluentMethod.setVisibility(JavaVisibility.PUBLIC);
        fluentMethod.setReturnType(topLevelClass.getType());

        if (introspectedTable.getTargetRuntime() == IntrospectedTable.TargetRuntime.MYBATIS3_DSQL) {
            context.getCommentGenerator().addGeneralMethodAnnotation(fluentMethod,
                    introspectedTable, topLevelClass.getImportedTypes());
        } else {
            context.getCommentGenerator().addGeneralMethodComment(fluentMethod,
                    introspectedTable);
        }
        fluentMethod.addBodyLine("return this;"); //$NON-NLS-1$

        topLevelClass.addMethod(fluentMethod);
    }
}
