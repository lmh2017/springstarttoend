package org.springstarttoend.context.support;

import org.springstarttoend.beans.factory.support.DefaultBeanFactory;
import org.springstarttoend.beans.factory.xml.XmlBeanDefinitionReader;
import org.springstarttoend.context.ApplicationContext;
import org.springstarttoend.io.Resource;
import org.springstarttoend.utils.ClassUtils;

public abstract class AbstractApplicationContext implements ApplicationContext{

    public abstract Resource getResourceByPath(String path);

    private DefaultBeanFactory factory = null;
    private ClassLoader beanClassLoader;

    public AbstractApplicationContext(String configFile) {
        factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(factory);
        Resource resource =getResourceByPath(configFile);
        xmlBeanDefinitionReader.loadBeanDefinitions(resource);
        factory.setBeanClassLoader(beanClassLoader);
    }

    public Object getBean(String beanId){
        return factory.getBean(beanId);
    }

    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }

    public ClassLoader getBeanClassLoader() {
        return (this.beanClassLoader!=null?beanClassLoader: ClassUtils.getDefaultClassLoader());
    }
}
