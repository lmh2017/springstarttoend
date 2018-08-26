package org.springstarttoend.beans.factory.support;

import org.springstarttoend.beans.BeanDefinition;
import org.springstarttoend.beans.PropertyValue;
import org.springstarttoend.beans.SimpleTypeConverter;
import org.springstarttoend.beans.factory.BeanCreationException;
import org.springstarttoend.beans.factory.config.ConfigurableBeanFactory;
import org.springstarttoend.utils.ClassUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author mh_liu
 * @create 2018/7/10 下午6:14
 */
public class DefaultBeanFactory extends DefaultSingletonBeanRegistry implements BeanDefinitionRegistry,ConfigurableBeanFactory {

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
        //设置属性
        Object bean = instantiateBean(beanDefinition);
        //创建实例
        populateBean(beanDefinition,bean);

        return bean;

    }

    private Object instantiateBean(BeanDefinition beanDefinition) {
        if(beanDefinition.hasConstructorArgumentValues()){
            ConStructorResolver resolver = new ConStructorResolver(this);
            return resolver.autowireConstructor(beanDefinition);
        }
        ClassLoader classLoader = this.getBeanClassLoader();
        String beanName = beanDefinition.getBeanClassName();
        try {
            Class<?> cls = classLoader.loadClass(beanName);
            return cls.newInstance();
        } catch (Exception e) {
            throw new BeanCreationException("create bean for "+ beanName +" failed",e);
        }
    }

    private void populateBean(BeanDefinition beanDefinition, Object bean) {
        List<PropertyValue> propertyValues = beanDefinition.getPropertValues();
        if(propertyValues ==null || propertyValues.isEmpty()){
            return;
        }
        BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(this);
        SimpleTypeConverter converter = new SimpleTypeConverter();

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
            for(PropertyValue propertyValue:propertyValues){
                String propertyName = propertyValue.getName();
                Object originalValue = propertyValue.getValue();
                Object resolverValue = resolver.resolveValueIfNecessary(originalValue);

                for(PropertyDescriptor pd:pds){
                    if(pd.getName().equals(propertyName)){
                        Object convertedValue = converter.convertIfNecessary(resolverValue, pd.getPropertyType());
                        pd.getWriteMethod().invoke(bean, convertedValue);
                        break;
                    }
                }
            }

        } catch (Exception e) {
            throw new BeanCreationException("Failed to obtain BeanInfo for class [" + beanDefinition.getBeanClassName() + "]", e);
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
