package com.lin.jiang.app.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by JiangLin.<br>
 * Date: 2019/09/17 10:58<br>
 * Description: TimeTrace 方法耗时计算注解<br>
 * <a href="https://www.google.com">文档地址</a>
 *
 * @author JiangLin
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TimeTrace {
    String value() default "[默认]";
}
