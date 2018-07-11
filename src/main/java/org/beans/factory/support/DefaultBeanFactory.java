package org.beans.factory.support;

import org.beans.BeanDefinition;
import org.beans.factory.BeanCreationException;
import org.beans.factory.config.ConfigurableBeanFactory;
import org.utils.ClassUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author mh_liu
 * @create 2018/7/10 下午6:14
 */
public class DefaultBeanFactory extends DefaultSingletonBeanRegistry
        implements ConfigurableBeanFactory,BeanDefinitionRegistry{

    private final Map<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>(64);
    private ClassLoader beanClassLoader;

    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }

    public ClassLoader getBeanClassLoader() {
        return (this.beanClassLoader!=null?beanClassLoader: ClassUtils.getDefaultClassLoader());
    }

    public Object getBean(String beanId) {
        BeanDefinition beanDefinition = this.getBeanDefinition(beanId);
        if(beanDefinition == null){
            return null;

        }
        if(beanDefinition.isSingleton()){
            Object bean = this.getSingleton(beanId);
            if(bean == null){
                try {
                    bean = createBean(beanDefinition);
                    this.registerBeanDefinition(beanId,beanDefinition);
                } catch (BeanCreationException e) {
                    e.printStackTrace();
                }
            }
            try {
                return createBean(beanDefinition);
            } catch (BeanCreationException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private Object createBean(BeanDefinition beanDefinition) throws BeanCreationException {
        ClassLoader classLoader = this.beanClassLoader;
        String beanName = beanDefinition.getBeanClassName();
        try {
            Class<?> cls = classLoader.loadClass(beanName);
            return cls.newInstance();
        } catch (Exception e) {
            throw new BeanCreationException("create bean for "+ beanName +" failed",e);
        }

    }

    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(beanName,beanDefinition);
    }

    public void removeBeanDefinition(String beanName) {
        this.beanDefinitionMap.remove(beanName);
    }

    public BeanDefinition getBeanDefinition(String beanName) {
        return this.beanDefinitionMap.get(beanName);
    }

    public boolean containsBeanDefinition(String beanName) {
        return this.beanDefinitionMap.containsKey(beanName);
    }

    public String[] getBeanDefinitionNames() {
        return new String[0];
    }

    public int getBeanDefinitionCount() {
        return this.beanDefinitionMap.size();
    }

    public boolean isBeanNameInUse(String beanName) {
        return false;
    }
}
