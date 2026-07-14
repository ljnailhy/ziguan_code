package com.jk.asset.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wangTao
 * date2024/7/22 11:56
 **/
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelMerge {
    /**
     * 是否合并单元格
     *
     * @return true || false
     */
    boolean merge() default true;

    /**
     * 是否为主键（即该字段相同的行合并）
     *
     * @return true || false
     */
    boolean isPrimaryKey() default false;
}
