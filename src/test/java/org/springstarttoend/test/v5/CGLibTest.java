package org.springstarttoend.test.v5;

import org.junit.Test;
import org.springframework.cglib.proxy.*;
import org.springstarttoend.service.v5.PetService;
import org.springstarttoend.tx.TransactionManager;

import java.lang.reflect.Method;

/**
 * @author mh_liu
 * @create 2018/10/9 下午5:11
 */
public class CGLibTest {

    @Test
    public void testCallBack(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(PetService.class);

        enhancer.setCallback( new TransactionInterceptor() );

        PetService petService = (PetService) enhancer.create();
        petService.placeOrder();

    }

    public static class TransactionInterceptor implements MethodInterceptor {
        TransactionManager tx = new TransactionManager();

        @Override
        public Object intercept(Object obj, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            tx.start();
            Object result = methodProxy.invokeSuper(obj,objects);
            tx.commit();
            return result;
        }
    }

    public static class TransactionThrowsInterceptor implements MethodInterceptor {
        TransactionManager tx = new TransactionManager();

        @Override
        public Object intercept(Object obj, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            tx.start();
            Object result = null;
            try{
                result = methodProxy.invokeSuper(obj,objects);
            }catch (Exception e){
                tx.rollback();
                throw e;
            }
            tx.commit();
            return result;
        }
    }

    @Test
    public void testFilter(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(PetService.class);
        enhancer.setInterceptDuringConstruction(false);

        Callback[] callbacks = new Callback[]{new TransactionInterceptor(),new TransactionThrowsInterceptor(), NoOp.INSTANCE};

        Class<?>[] types = new Class<?>[callbacks.length];
        for (int x = 0; x < types.length; x++) {
            types[x] = callbacks[x].getClass();
        }

        enhancer.setCallbackFilter(new ProxyCallbackFilter());
        enhancer.setCallbacks(callbacks);
        enhancer.setCallbackTypes(types);
        PetService petService = (PetService) enhancer.create();

        petService.placeOrder();

        try {
            petService.thorwsException();
        }catch (Exception e){

        }

        System.out.println(petService.toString());



    }

    private static class ProxyCallbackFilter implements CallbackFilter {

        public ProxyCallbackFilter() {

        }

        public int accept(Method method) {
            if(method.getName().startsWith("place")){
                return 0;
            } else if(method.getName().startsWith("thorws")){
                return 1;
            }else {
                return 2;
            }

        }

    }

}
