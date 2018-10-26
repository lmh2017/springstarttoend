package org.springstarttoend.test.v5;

import org.junit.Before;
import org.junit.Test;
import org.springstarttoend.aop.aspectj.AspectJAfterReturningAdvice;
import org.springstarttoend.aop.aspectj.AspectJBeforeAdvice;
import org.springstarttoend.aop.aspectj.AspectJExpressionPointcut;
import org.springstarttoend.aop.freamwork.AopConfig;
import org.springstarttoend.aop.freamwork.AopConfigSupport;
import org.springstarttoend.aop.freamwork.AopProxyFactory;
import org.springstarttoend.aop.freamwork.CglibProxyFactory;
import org.springstarttoend.service.v5.PetService;
import org.springstarttoend.tx.TransactionManager;

/**
 * @author mh_liu
 * @create 2018/10/10 下午3:52
 */
public class CglibAopProxyTest {

    private static AspectJBeforeAdvice beforeAdvice = null;
    private static AspectJAfterReturningAdvice afterAdvice = null;
    private static AspectJExpressionPointcut pc = null;

    private TransactionManager tx;

    @Before
    public void setUp() throws NoSuchMethodException {

        tx = new TransactionManager();

        String expression = "execution(* org.springstarttoend.service.v5.*.placeOrder(..))";
        pc = new AspectJExpressionPointcut();
        pc.setExpression(expression);

        beforeAdvice = new AspectJBeforeAdvice(TransactionManager.class.getMethod("start"),
                pc,
                tx);

        afterAdvice = new AspectJAfterReturningAdvice(TransactionManager.class.getMethod("commit"),
                pc,
                tx);

    }

    @Test
    public void testCglibAopProxy(){
        AopConfig aopConfig = new AopConfigSupport();
        aopConfig.setTargetObject(new PetService());
        aopConfig.addAdvice(beforeAdvice);
        aopConfig.addAdvice(afterAdvice);

        AopProxyFactory factory = new CglibProxyFactory(aopConfig);
        PetService petService = (PetService) factory.getProxy();
        petService.placeOrder();
    }

}
