package org.springstarttoend.test.v4;

import org.junit.Assert;
import org.junit.Test;
import org.springstarttoend.beans.factory.annotation.AutowiredMethodElement;
import org.springstarttoend.beans.factory.annotation.InjectionElement;
import org.springstarttoend.beans.factory.annotation.InjectionMetadata;
import org.springstarttoend.beans.factory.support.DefaultBeanFactory;
import org.springstarttoend.beans.factory.xml.XmlBeanDefinitionReader;
import org.springstarttoend.core.io.ClassPathResource;
import org.springstarttoend.core.io.Resource;
import org.springstarttoend.dao.v4.AccountDao;
import org.springstarttoend.dao.v4.ItemDao;
import org.springstarttoend.service.v4.PetService;

import java.lang.reflect.Method;
import java.util.LinkedList;

/**
 * @author mh_liu
 * @create 2018/9/21 下午5:15
 */
public class InjectionMetadataTest {

    @Test
    public void testInjection() throws NoSuchFieldException, NoSuchMethodException {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("petStore-v4.xml");
        reader.loadBeanDefinitions(resource);

        Class<?> clz = PetService.class;
        LinkedList<InjectionElement> elements = new LinkedList<>();

//        {
//            Field f = PetService.class.getDeclaredField("accountDao");
//            InjectionElement injectionElem = new AutowiredFieldElement(f,true, factory);
//            elements.add(injectionElem);
//        }
//        {
//            Field f = PetService.class.getDeclaredField("itemDao");
//            InjectionElement injectionElem = new AutowiredFieldElement(f,true,factory);
//            elements.add(injectionElem);
//        }
        {
            Method method = PetService.class.getDeclaredMethod("setAccountDao", AccountDao.class);
            InjectionElement injectionElement = new AutowiredMethodElement(method,true,factory);
            elements.add(injectionElement);
        }
        {
            Method method = PetService.class.getDeclaredMethod("setItemDao", ItemDao.class);
            InjectionElement injectionElement = new AutowiredMethodElement(method,true,factory);
            elements.add(injectionElement);
        }

        InjectionMetadata metadata = new InjectionMetadata(clz,elements);

        PetService petService = new PetService();
        metadata.inject(petService);

        Assert.assertTrue(petService.getAccountDao() instanceof AccountDao);
        Assert.assertTrue(petService.getItemDao() instanceof ItemDao);
    }
}
