package cn.jdcloud.medicine.mall.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER,ElementType.FIELD,})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelHead {
    /**
     * 表格头名字
     */
    String value();

    boolean isImport() default true;

    boolean isExport() default true;

    boolean notNull() default false;

    boolean isOption() default false;

    String datePattern() default "yyyy/MM/dd";

}
