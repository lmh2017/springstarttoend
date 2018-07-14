package org.springstarttoend.beans.factory.support;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springstarttoend.beans.BeanDefinition;
import org.springstarttoend.beans.factory.BeanCreationException;
import org.springstarttoend.beans.factory.BeanDefinitionStoreException;
import org.springstarttoend.beans.factory.BeanFactory;
import org.springstarttoend.utils.ClassUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author mh_liu
 * @create 2018/7/10 下午6:14
 */
public class DefaultBeanFactory implements BeanFactory{

    public static final String ID_ATTRIBUTE = "id";  //xml id属性

    public static final String ClASS_ATTRIBUTE = "class";  //xml class属性

    public static final String SCOPE_ATTRIBUTE = "scope";   //xml   scope属性

    public final Map<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>(64);

    public String getBeanClassName() {
        return null;
    }

    public DefaultBeanFactory(String configFile) throws BeanCreationException {
        this.loadBeanDefinitions(configFile);
    }

    private void loadBeanDefinitions(String configFile) {
        InputStream inputStream = null;
        try {
            ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
            inputStream = classLoader.getResourceAsStream(configFile);
            SAXReader saxReader = new SAXReader();
            Document doc = saxReader.read(inputStream);

            Element root = doc.getRootElement();  //xml节点

            Iterator<Element> it = root.elementIterator();

            while (it.hasNext()) {
                Element e = it.next();
                String id = e.attributeValue(ID_ATTRIBUTE);
                String beanClassName = e.attributeValue(ClASS_ATTRIBUTE);
                BeanDefinition beanDefinition = new GenericBeanDefinition(id, beanClassName);
                if (null != e.attributeValue(SCOPE_ATTRIBUTE)) {
                    beanDefinition.setScope(e.attributeValue(SCOPE_ATTRIBUTE));
                }
                beanDefinitionMap.put(id,beanDefinition);
            }
        }catch (Exception e){
            throw new BeanDefinitionStoreException("");
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Object getBean(String beanId) {
        BeanDefinition beanDefinition = getBeanDefinition(beanId);
        if(beanDefinition==null){
            throw new BeanDefinitionStoreException("");
        }
        ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
        String beanClassName = beanDefinition.getBeanClassName();

        try {
            Class<?> c = classLoader.loadClass(beanClassName);
            return c.newInstance();
        } catch (Exception e) {
            throw new  BeanDefinitionStoreException("");
        }
    }

    public BeanDefinition getBeanDefinition(String beanId) {
        return this.beanDefinitionMap.get(beanId);
    }


//    private final Map<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>(64);
//    private ClassLoader beanClassLoader;
//
//    public void setBeanClassLoader(ClassLoader beanClassLoader) {
//        this.beanClassLoader = beanClassLoader;
//    }
//
//    public ClassLoader getBeanClassLoader() {
//        return (this.beanClassLoader!=null?beanClassLoader: ClassUtils.getDefaultClassLoader());
//    }
//
//    public Object getBean(String beanId) {
//        BeanDefinition beanDefinition = this.getBeanDefinition(beanId);
//        if(beanDefinition == null){
//            return null;
//
//        }
//        if(beanDefinition.isSingleton()){
//            Object bean = this.getSingleton(beanId);
//            if(bean == null){
//                try {
//                    bean = createBean(beanDefinition);
//                    this.registerBeanDefinition(beanId,beanDefinition);
//                } catch (BeanCreationException e) {
//                    e.printStackTrace();
//                }
//            }
//            try {
//                return createBean(beanDefinition);
//            } catch (BeanCreationException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//    private Object createBean(BeanDefinition beanDefinition) throws BeanCreationException {
//        ClassLoader classLoader = this.beanClassLoader;
//        String beanName = beanDefinition.getBeanClassName();
//        try {
//            Class<?> cls = classLoader.loadClass(beanName);
//            return cls.newInstance();
//        } catch (Exception e) {
//            throw new BeanCreationException("create bean for "+ beanName +" failed",e);
//        }
//
//    }
//
//    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
//        this.beanDefinitionMap.put(beanName,beanDefinition);
//    }
//
//    public void removeBeanDefinition(String beanName) {
//        this.beanDefinitionMap.remove(beanName);
//    }
//
//    public BeanDefinition getBeanDefinition(String beanName) {
//        return this.beanDefinitionMap.get(beanName);
//    }
//
//    public boolean containsBeanDefinition(String beanName) {
//        return this.beanDefinitionMap.containsKey(beanName);
//    }
//
//    public String[] getBeanDefinitionNames() {
//        return new String[0];
//    }
//
//    public int getBeanDefinitionCount() {
//        return this.beanDefinitionMap.size();
//    }
//
//    public boolean isBeanNameInUse(String beanName) {
//        return false;
//    }
}
