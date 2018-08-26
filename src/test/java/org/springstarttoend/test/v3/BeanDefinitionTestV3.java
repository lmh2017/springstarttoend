package org.springstarttoend.test.v3;

import org.junit.Assert;
import org.junit.Test;
import org.springstarttoend.beans.BeanDefinition;
import org.springstarttoend.beans.ConstructorArgument;
import org.springstarttoend.beans.factory.config.RuntimeBeanReference;
import org.springstarttoend.beans.factory.config.TypedStringValue;
import org.springstarttoend.beans.factory.support.DefaultBeanFactory;
import org.springstarttoend.beans.factory.xml.XmlBeanDefinitionReader;
import org.springstarttoend.io.ClassPathResource;
import org.springstarttoend.io.Resource;

import java.util.List;

public class BeanDefinitionTestV3 {

    @Test
    public void beanDefinitionTestV3(){
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("petStore-v3.xml");
        reader.loadBeanDefinitions(resource);

        BeanDefinition bd = factory.getBeanDefinition("petStore");
        Assert.assertEquals("org.springstarttoend.service.v3.PetStoreService",bd.getBeanClassName());

        ConstructorArgument args = bd.getConstructorArgument();
        List<ConstructorArgument.ValueHolder> valueHolders = args.getArgumentValues();

        Assert.assertEquals(3,args.getArgumentCount());

        RuntimeBeanReference ref1 = (RuntimeBeanReference) valueHolders.get(0).getValue();
        Assert.assertEquals("accountDao",ref1.getBeanName());
        Assert.assertEquals("accountDao",ref1.getBeanName());
        RuntimeBeanReference ref2 = (RuntimeBeanReference) valueHolders.get(1).getValue();
        Assert.assertEquals("itemDao",ref2.getBeanName());

        TypedStringValue strValue = (TypedStringValue) valueHolders.get(2).getValue();
        Assert.assertEquals("1",strValue.getValue());

    }
}
