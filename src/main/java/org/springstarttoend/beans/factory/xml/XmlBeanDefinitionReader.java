package org.springstarttoend.beans.factory.xml;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springstarttoend.beans.BeanDefinition;
import org.springstarttoend.beans.ConstructorArgument;
import org.springstarttoend.beans.PropertyValue;
import org.springstarttoend.beans.factory.BeanDefinitionStoreException;
import org.springstarttoend.beans.factory.config.RuntimeBeanReference;
import org.springstarttoend.beans.factory.config.TypedStringValue;
import org.springstarttoend.beans.factory.support.BeanDefinitionRegistry;
import org.springstarttoend.beans.factory.support.GenericBeanDefinition;
import org.springstarttoend.context.annotation.ClassPathBeanDefinitionScanner;
import org.springstarttoend.core.io.Resource;
import org.springstarttoend.utils.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * @author mh_liu
 * @create 2018/7/5 下午5:38
 * 读取xml资源文件，为加载bean做准备
 */
public class XmlBeanDefinitionReader {

    private static final Logger logger = Logger.getLogger(XmlBeanDefinitionReader.class);

    public static final String ID_ATTRIBUTE = "id";  //xml id属性

    public static final String ClASS_ATTRIBUTE = "class";  //xml class属性

    public static final String SCOPE_ATTRIBUTE = "scope";   //xml   scope属性

    public static final String PROPERTY_ELEMENT="property";

    public static final String NAME_ATTRIBUTE = "name";

    public static final String REF_ATTRIBUTE = "ref";

    public static final String VALUE_ATTRIBUTE = "value";

    public static final String CONSTRUCTOR_ARG_ELEMENT = "constructor-arg";

    public static final String TYPE_ATTRIBUTE = "type";

    public static final String BEANS_NAMESPACE_URI = "http://www.springframework.org/schema/beans";

    public static final String CONTEXT_NAMESPACE_URI = "http://www.springframework.org/schema/context";

    private static final String BASE_PACKAGE_ATTRIBUTE = "base-package";

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
                String namespaceUri = e.getNamespaceURI();
                if(this.isDefaultNamespace(namespaceUri)){
                    parseDefaultElement(e); //普通的bean
                } else if(this.isContextNamespace(namespaceUri)){
                    parseComponentElement(e); //例如<context:component-scan>
                }
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

    private void parseComponentElement(Element e) {
        String basePackages = e.attributeValue(BASE_PACKAGE_ATTRIBUTE);
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);
        scanner.doScan(basePackages);
    }

    private void parseDefaultElement(Element ele) {
        String id = ele.attributeValue(ID_ATTRIBUTE);
        String beanClassName = ele.attributeValue(ClASS_ATTRIBUTE);
        BeanDefinition bd = new GenericBeanDefinition(id,beanClassName);
        if (ele.attribute(SCOPE_ATTRIBUTE)!=null) {
            bd.setScope(ele.attributeValue(SCOPE_ATTRIBUTE));
        }
        parseConstructorArgElements(ele,bd);
        parsePropertyElement(ele,bd);
        this.registry.registerBeanDefinition(id, bd);

    }

    public boolean isDefaultNamespace(String namespaceUri) {
        return (!StringUtils.hasLength(namespaceUri) || BEANS_NAMESPACE_URI.equals(namespaceUri));
    }
    public boolean isContextNamespace(String namespaceUri){
        return (!StringUtils.hasLength(namespaceUri) || CONTEXT_NAMESPACE_URI.equals(namespaceUri));
    }

    private void parseConstructorArgElements(Element e, BeanDefinition beanDefinition) {
        Iterator<Element> iterator = e.elementIterator(CONSTRUCTOR_ARG_ELEMENT);
        while (iterator.hasNext()){
            Element element = iterator.next();
            parseConstructorArgElement(element,beanDefinition);
        }
    }

    private void parseConstructorArgElement(Element element, BeanDefinition beanDefinition) {

        String typeAttr = element.attributeValue(TYPE_ATTRIBUTE);
        String nameAttr = element.attributeValue(NAME_ATTRIBUTE);

        Object value = parsePropertyValue(element,beanDefinition,null);
        ConstructorArgument.ValueHolder valueHolder = new ConstructorArgument.ValueHolder(value);
        if(StringUtils.hasLength(typeAttr)){
            valueHolder.setType(typeAttr);
        }
        if(StringUtils.hasLength(nameAttr)){
            valueHolder.setName(nameAttr);
        }
        beanDefinition.getConstructorArgument().addArgumentValue(valueHolder);
    }

    private void parsePropertyElement(Element ele,BeanDefinition bd) {
        Iterator<Element> iterator = ele.elementIterator(PROPERTY_ELEMENT);
        while (iterator.hasNext()) {
            Element element = iterator.next();
            String propertyName = element.attributeValue(NAME_ATTRIBUTE);
            if(!StringUtils.hasLength(propertyName)){
                logger.fatal("Tag 'property' must have a 'name' attribute");
                return;
            }

            Object value = parsePropertyValue(element,bd,propertyName);

            PropertyValue pv = new PropertyValue(propertyName,value);
            bd.getPropertValues().add(pv);

        }
    }

    private Object parsePropertyValue(Element element, BeanDefinition bd, String propertyName) {
        String eleName = (propertyName!=null)?
                "<property> element for property '" + propertyName + "'" :
                "<constructor-arg> element";
        boolean hasRefAttribute = (element.attribute(REF_ATTRIBUTE)!=null);
        boolean hasValueAttribute = (element.attribute(VALUE_ATTRIBUTE)!=null);

        if(hasRefAttribute){
            String refName = element.attributeValue(REF_ATTRIBUTE);
            if(!StringUtils.hasText(refName)){
                logger.error(eleName + " contains empty 'ref' attribute");
            }
            RuntimeBeanReference ref = new RuntimeBeanReference(refName);
            return ref;
        }else if(hasValueAttribute){
            TypedStringValue valueHolder = new TypedStringValue(element.attributeValue(VALUE_ATTRIBUTE));
            return valueHolder;
        }else {
            throw new RuntimeException(eleName + " must specify a ref or value");
        }


    }
}
