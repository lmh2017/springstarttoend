package org.springstarttoend.test.v5;

import org.junit.Assert;
import org.junit.Test;
import org.springstarttoend.aop.MethodMatcher;
import org.springstarttoend.aop.aspectj.AspectJExpressionPointcut;
import org.springstarttoend.service.v5.PetService;

import java.lang.reflect.Method;

/**
 * @author mh_liu
 * @create 2018/10/4 下午3:50
 */
public class PointcutTest {

    @Test
    public void testPointcut() throws Exception{
        String expression = "execution(* org.springstarttoend.service.v5.*.placeOrder(..))";

        AspectJExpressionPointcut pc = new AspectJExpressionPointcut();
        pc.setExpression(expression);

        MethodMatcher mm = pc.getMethodMatcher();

        {
            Class<?> targetClass = PetService.class;

            Method method1 = targetClass.getMethod("placeOrder");
            Assert.assertTrue(mm.matches(method1));

            Method method2 = targetClass.getMethod("getAccountDao");
            Assert.assertFalse(mm.matches(method2));
        }

        {
            Class<?> targetClass = org.springstarttoend.service.v4.PetService.class;

            Method method = targetClass.getMethod("getAccountDao");
            Assert.assertFalse(mm.matches(method));
        }

    }



}
