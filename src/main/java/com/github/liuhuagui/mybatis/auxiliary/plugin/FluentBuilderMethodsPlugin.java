package com.github.liuhuagui.mybatis.auxiliary.plugin;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;

/**
 * @author KK_SUN
 * @date 2020-05-25 20
 */
public class FluentBuilderMethodsPlugin extends PluginAdapter {
    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean modelSetterMethodGenerated(Method method,
                                              TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
                                              IntrospectedTable introspectedTable,
                                              ModelClassType modelClassType) {

        String name = method.getName().substring(3);
        char[] charArray = name.toCharArray();
        charArray[0] += 32;
        name = String.valueOf(charArray);

        Method fluentMethod = new Method(name); //$NON-NLS-1$
        fluentMethod.setVisibility(JavaVisibility.PUBLIC);
        fluentMethod.setReturnType(topLevelClass.getType());
        fluentMethod.getParameters().addAll(method.getParameters());

        if (introspectedTable.getTargetRuntime() == IntrospectedTable.TargetRuntime.MYBATIS3_DSQL) {
            context.getCommentGenerator().addGeneralMethodAnnotation(fluentMethod,
                    introspectedTable, topLevelClass.getImportedTypes());
        } else {
            context.getCommentGenerator().addGeneralMethodComment(fluentMethod,
                    introspectedTable);
        }

        StringBuilder sb = new StringBuilder()
                .append("this.") //$NON-NLS-1$
                .append(method.getName())
                .append('(')
                .append(introspectedColumn.getJavaProperty())
                .append(");"); //$NON-NLS-1$
        fluentMethod.addBodyLine(sb.toString()); //$NON-NLS-1$
        fluentMethod.addBodyLine("return this;"); //$NON-NLS-1$

        topLevelClass.addMethod(fluentMethod);

        return super.modelSetterMethodGenerated(method, topLevelClass, introspectedColumn,
                introspectedTable, modelClassType);
    }
}
