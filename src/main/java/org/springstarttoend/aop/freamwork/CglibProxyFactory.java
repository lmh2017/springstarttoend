package org.springstarttoend.aop.freamwork;

import org.apache.log4j.Logger;
import org.springframework.cglib.core.CodeGenerationException;
import org.springframework.cglib.core.SpringNamingPolicy;
import org.springframework.cglib.proxy.*;
import org.springstarttoend.aop.Advice;
import org.springstarttoend.utils.Assert;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mh_liu
 * @create 2018/10/12 下午3:13
 */
public class CglibProxyFactory implements AopProxyFactory {

    // Constants for CGLIB callback array indices
    private static final int AOP_PROXY = 0;
    private static final int INVOKE_TARGET = 1;
    private static final int NO_OVERRIDE = 2;
    private static final int DISPATCH_TARGET = 3;
    private static final int DISPATCH_ADVISED = 4;
    private static final int INVOKE_EQUALS = 5;
    private static final int INVOKE_HASHCODE = 6;

    /**
     * Logger available to subclasses; static to optimize serialization
     */
    protected static final Logger logger = Logger.getLogger(CglibProxyFactory.class);

    protected final AopConfig config;

    private Object[] constructorArgs;

    private Class<?>[] constructorArgTypes;

    public CglibProxyFactory(AopConfig config) throws AopConfigException {
        Assert.notNull(config, "AdvisedSupport must not be null");
        if (config.getAdvices().size() == 0 /*&& config.getTargetSource() == AdvisedSupport.EMPTY_TARGET_SOURCE*/) {
            throw new AopConfigException("No advisors and no TargetSource specified");
        }
        this.config = config;

    }

    /**
     * Set constructor arguments to use for creating the proxy.
     */
	/*public void setConstructorArguments(Object[] constructorArgs, Class<?>[] constructorArgTypes) {
		if (constructorArgs == null || constructorArgTypes == null) {
			throw new IllegalArgumentException("Both 'constructorArgs' and 'constructorArgTypes' need to be specified");
		}
		if (constructorArgs.length != constructorArgTypes.length) {
			throw new IllegalArgumentException("Number of 'constructorArgs' (" + constructorArgs.length +
					") must match number of 'constructorArgTypes' (" + constructorArgTypes.length + ")");
		}
		this.constructorArgs = constructorArgs;
		this.constructorArgTypes = constructorArgTypes;
	}*/
    @Override
    public Object getProxy() {
        return getProxy(null);
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        if (logger.isDebugEnabled()) {
            logger.debug("Creating CGLIB proxy: target source is " + this.config.getTargetClass());
        }

        try {
            Class<?> rootClass = this.config.getTargetClass();

            Enhancer enhancer = new Enhancer();  //创建cglib核心类
            if (classLoader != null) {
                enhancer.setClassLoader(classLoader);
            }
            enhancer.setSuperclass(rootClass);

            enhancer.setNamingPolicy(SpringNamingPolicy.INSTANCE); //"BySpringCGLIB"  创建的代理类的命名方式

            enhancer.setInterceptDuringConstruction(false);

            Callback[] callbacks = getCallbacks(rootClass);

            Class<?>[] types = new Class<?>[callbacks.length];
            for(int i=0;i<types.length;i++){
                types[i] = callbacks[i].getClass();
            }

            enhancer.setCallbackFilter(new ProxyCallbackFilter(this.config));
            enhancer.setCallbackTypes(types);
            enhancer.setCallbacks(callbacks);

            return enhancer.create();
        } catch (CodeGenerationException ex) {
            throw new AopConfigException("Could not generate CGLIB subclass of class [" +
                    this.config.getTargetClass() + "]: " +
                    "Common causes of this problem include using a final class or a non-visible class",
                    ex);
        } catch (IllegalArgumentException ex) {
            throw new AopConfigException("Could not generate CGLIB subclass of class [" +
                    this.config.getTargetClass() + "]: " +
                    "Common causes of this problem include using a final class or a non-visible class",
                    ex);
        } catch (Exception ex) {
            // TargetSource.getTarget() failed
            throw new AopConfigException("Unexpected AOP exception", ex);

        }
    }

    private Callback[] getCallbacks(Class<?> rootClass) {
        Callback aopInterceptor = new DynamicAdvisedInterceptor(this.config);

        Callback[] callbacks = new Callback[]{
                aopInterceptor,// AOP_PROXY for normal advice
                /*targetInterceptor,  // INVOKE_TARGET invoke target without considering advice, if optimized
                new SerializableNoOp(),  // NO_OVERRIDE  no override for methods mapped to this
                targetDispatcher,        //DISPATCH_TARGET
                this.advisedDispatcher,  //DISPATCH_ADVISED
                new EqualsInterceptor(this.advised),
                new HashCodeInterceptor(this.advised)*/
        };
        return callbacks;

    }

    private static class DynamicAdvisedInterceptor implements MethodInterceptor, Serializable {

        private final AopConfig aopConfig;

        public DynamicAdvisedInterceptor(AopConfig advised) {
            this.aopConfig = advised;
        }

        @Override
        public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            Object target = this.aopConfig.getTargetObject();

            List<Advice> chain  = aopConfig.getAdvices(method);
            Object retVal;
            if(chain.isEmpty() && Modifier.isPublic(method.getModifiers())){
                // We can skip creating a MethodInvocation: just invoke the target directly.
                // Note that the final invoker must be an InvokerInterceptor, so we know
                // it does nothing but a reflective operation on the target, and no hot
                // swapping or fancy proxying.
                retVal = methodProxy.invoke(target, args);
            }else {
                List<org.aopalliance.intercept.MethodInterceptor> interceptors =
                        new ArrayList<>();

                interceptors.addAll(chain);

                // We need to create a method invocation...
                retVal = new ReflectiveMethodInvocation(target,method,args,interceptors).proceed();
            }
            return retVal;
        }
    }

    /**
     * CallbackFilter to assign Callbacks to methods.
     */
    private static class ProxyCallbackFilter implements CallbackFilter {

        private final AopConfig config;



        public ProxyCallbackFilter(AopConfig advised) {
            this.config = advised;

        }


        public int accept(Method method) {
            // 注意，这里做了简化
            return AOP_PROXY;

        }

    }

}
