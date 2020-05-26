package com.github.liuhuagui.mybatis.auxiliary.modle;

import java.math.BigInteger;
import java.util.function.Function;

/**
 *  Access to generated key.
 * @author KaiKang 799600902@qq.com
 */
public class GeneratedKey {
    public static final String KEY = "generatedKey";

    public static final String KEY_PROPERTY = "parameters.generatedKey";

    private static final ThreadLocal<Long> generatedKey = new ThreadLocal<Long>();

    public static void set(Function<String, Object> parametersFunction) {
        generatedKey.set(((BigInteger)parametersFunction.apply(KEY)).longValue());
    }

    public static Long get() {
        return generatedKey.get();
    }
}
