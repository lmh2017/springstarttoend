package org.springstarttoend.test.v2;

import org.junit.Assert;
import org.junit.Test;
import org.springstarttoend.beans.BeanDefinition;
import org.springstarttoend.beans.PropertyValue;
import org.springstarttoend.beans.factory.config.RuntimeBeanReference;
import org.springstarttoend.beans.factory.support.DefaultBeanFactory;
import org.springstarttoend.beans.factory.xml.XmlBeanDefinitionReader;
import org.springstarttoend.io.ClassPathResource;

import java.util.List;

/**
 * @author mh_liu
 * @create 2018/8/10 下午5:27
 */
public class BeanDefinitionTestV2 {

    @Test
    public void testGetBeanDefinition(){
        DefaultBeanFactory defaultBeanFactory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(defaultBeanFactory);
        reader.loadBeanDefinitions(new ClassPathResource("petStore-v2.xml"));

        BeanDefinition beanDefinition = defaultBeanFactory.getBeanDefinition("petStore");

        List<PropertyValue> pvs = beanDefinition.getPropertValues();

        Assert.assertTrue(pvs.size() == 2);

        {
            PropertyValue pv = this.getPropertyValue("accountDao", pvs);

            Assert.assertNotNull(pv);

            Assert.assertTrue(pv.getValue() instanceof RuntimeBeanReference);
        }

        {
            PropertyValue pv = this.getPropertyValue("itemDao", pvs);

            Assert.assertNotNull(pv);

            Assert.assertTrue(pv.getValue() instanceof RuntimeBeanReference);
        }


    }

    private PropertyValue getPropertyValue(String name, List<PropertyValue> pvs) {
        for (PropertyValue propertyValue:
            pvs ) {
            if(propertyValue.getName().equals(name)){
                return propertyValue;
            }
        }
        return null;
    }
}
