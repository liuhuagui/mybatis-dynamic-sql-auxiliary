/**
 * Copyright 2006-2018 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.liuhuagui.mybatis.dynamicsql.auxiliary.plugin;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.IntrospectedTable.TargetRuntime;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;

import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

/**
 * Java class can implement more than one interface, so baseMapper property can exist with JavaClientGenerator's rootInterface.
 * If the fully qualified name is different, it will not conflict.
 *
 * @author KaiKang 799600902@qq.com
 */
public class BaseMapperPlugin extends PluginAdapter {

    private String baseMapper;

    @Override
    public boolean validate(List<String> warnings) {
        //if baseMapper property is not specified, the plugin doesn't work.
        String baseMapper = properties.getProperty("baseMapper");
        if (!stringHasValue(baseMapper))
            return false;//$NON-NL
        this.baseMapper = baseMapper;
        return true;
    }

    @Override
    public boolean clientBasicCountMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientBasicDeleteMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientBasicInsertMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientBasicUpdateMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientBasicInsertMultipleMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientGeneralCountMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        method.addAnnotation("@Override");
        return super.clientGeneralCountMethodGenerated(method, interfaze, introspectedTable);
    }

    @Override
    public boolean clientGeneralSelectMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        method.addAnnotation("@Override");
        return super.clientGeneralSelectMethodGenerated(method, interfaze, introspectedTable);
    }

    @Override
    public boolean clientGenerated(Interface interfaze, IntrospectedTable introspectedTable) {
        //for importing
        FullyQualifiedJavaType baseMapperTypeWithNoArguments = new FullyQualifiedJavaType(baseMapper);
        interfaze.addImportedType(baseMapperTypeWithNoArguments);

        //for adding a type argument
        //Plugin works as a singleton, please note security.
        FullyQualifiedJavaType baseMapperTypeWithArguments = new FullyQualifiedJavaType(baseMapper);
        //record type as type argument.
        FullyQualifiedJavaType recordType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        baseMapperTypeWithArguments.addTypeArgument(recordType);
        interfaze.addSuperInterface(baseMapperTypeWithArguments);

        return true;
    }
}