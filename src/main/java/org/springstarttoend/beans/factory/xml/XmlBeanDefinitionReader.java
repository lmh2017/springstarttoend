package org.springstarttoend.beans.factory.xml;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springstarttoend.beans.BeanDefinition;
import org.springstarttoend.beans.factory.BeanDefinitionStoreException;
import org.springstarttoend.beans.factory.support.BeanDefinitionRegistry;
import org.springstarttoend.beans.factory.support.GenericBeanDefinition;
import org.springstarttoend.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * @author mh_liu
 * @create 2018/7/5 下午5:38
 * 读取xml资源文件，为加载bean做准备
 */
public class XmlBeanDefinitionReader {

    public static final String ID_ATTRIBUTE = "id";  //xml id属性

    public static final String ClASS_ATTRIBUTE = "class";  //xml class属性

    public static final String SCOPE_ATTRIBUTE = "scope";   //xml   scope属性

    BeanDefinitionRegistry registry;

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void loadBeanDefinitions(Resource resource){
        InputStream inputStream = null;
        try{
            inputStream = resource.getInputStream();
            SAXReader saxReader = new SAXReader();
            Document doc = saxReader.read(inputStream);

            Element root = doc.getRootElement();  //xml节点

            Iterator<Element> it =  root.elementIterator();

            while (it.hasNext()){
                Element e = it.next();
                String id = e.attributeValue(ID_ATTRIBUTE);
                String beanClassName = e.attributeValue(ClASS_ATTRIBUTE);
                BeanDefinition beanDefinition = new GenericBeanDefinition(id,beanClassName);
                if(null != e.attributeValue(SCOPE_ATTRIBUTE)){
                    beanDefinition.setScope(e.attributeValue(SCOPE_ATTRIBUTE));
                }
                this.registry.registerBeanDefinition(id,beanDefinition);
            }
        }catch (Exception e){
            throw new BeanDefinitionStoreException("IOException parsing XML document from " + resource.getDescription(),e);
        }finally {
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
