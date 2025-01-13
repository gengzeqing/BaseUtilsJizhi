package org.base.error.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Aspect
@Component
public class LoggableAspect {

    // 定义切点，拦截所有带有 @Loggable 注解的方法
    @Pointcut("@annotation(org.base.error.annotation.Loggable)")
    public void loggableMethods() {}

    // 方法执行前的拦截
    //@Before("loggableMethods()")
    @AfterThrowing("loggableMethods()")
    public void beforeLoggableMethod(JoinPoint joinPoint) throws NoSuchMethodException, IllegalAccessException, NoSuchFieldException {
        // 获取目标方法
        Method method = getMethod(joinPoint);

        // 获取 @Loggable 注解
        Loggable loggable = method.getAnnotation(Loggable.class);

        // 获取 bindParam 和 field 属性
        String bindParam = loggable.bindParam();
        String taskId = loggable.taskId();
        String methodName = loggable.methodName();

        // 获取方法参数
        Object[] args = joinPoint.getArgs();

        // 如果 bindParam 为空，打印所有参数
        if (bindParam.isEmpty()) {
            for (Object arg : args) {
                System.out.println("捕获到参数: " + arg);
            }
        } else {
            // 如果 bindParam 为参数名称，查找对应的参数
            for (int i = 0; i < args.length; i++) {
                String paramName = method.getParameters()[i].getName();
                if (paramName.equals(bindParam)) {
                    Object paramValue = args[i];
                    // 如果参数是对象且存在指定的字段，则通过反射获取字段值
                    if (paramValue != null && !taskId.isEmpty()) {
                        Field field = paramValue.getClass().getDeclaredField(taskId);
                        field.setAccessible(true);  // 设置可访问私有字段
                        Object fieldValue = field.get(paramValue);
                        System.out.println("捕获到绑定的参数 (" + taskId + "): " + fieldValue);
                    }
                    return;
                }
            }
            System.out.println("没有找到绑定的参数: " + bindParam);
        }
    }

    // 获取目标方法
    private Method getMethod(JoinPoint joinPoint) throws NoSuchMethodException {
        String methodName = joinPoint.getSignature().getName();
        Class<?> targetClass = joinPoint.getTarget().getClass();
        Class<?>[] parameterTypes = new Class<?>[joinPoint.getArgs().length];
        for (int i = 0; i < joinPoint.getArgs().length; i++) {
            parameterTypes[i] = joinPoint.getArgs()[i].getClass();
        }
        return targetClass.getMethod(methodName, parameterTypes);
    }
}
