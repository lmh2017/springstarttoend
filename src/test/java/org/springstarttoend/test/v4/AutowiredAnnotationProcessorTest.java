package org.springstarttoend.test.v4;

import org.junit.Assert;
import org.junit.Test;
import org.springstarttoend.beans.factory.annotation.AutowiredAnnotationProcessor;
import org.springstarttoend.beans.factory.annotation.AutowiredFieldElement;
import org.springstarttoend.beans.factory.annotation.InjectionElement;
import org.springstarttoend.beans.factory.annotation.InjectionMetadata;
import org.springstarttoend.beans.factory.config.DependencyDescriptor;
import org.springstarttoend.beans.factory.support.DefaultBeanFactory;
import org.springstarttoend.dao.v4.AccountDao;
import org.springstarttoend.dao.v4.ItemDao;
import org.springstarttoend.service.v4.PetService;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author mh_liu
 * @create 2018/9/25 下午1:33
 */
public class AutowiredAnnotationProcessorTest {

    AccountDao accountDao = new AccountDao();
    ItemDao itemDao = new ItemDao();
    DefaultBeanFactory beanFactory = new DefaultBeanFactory(){
        public Object resolveDependency(DependencyDescriptor descriptor){
            if(descriptor.getDependencyType().equals(AccountDao.class)){
                return accountDao;
            }
            if(descriptor.getDependencyType().equals(ItemDao.class)){
                return itemDao;
            }
            throw new RuntimeException("can't support types except AccountDao and ItemDao");
        }
    };

    @Test
    public void testGetInjectionMetadata(){
        AutowiredAnnotationProcessor processor = new AutowiredAnnotationProcessor();
        processor.setBeanFactory(beanFactory);
        InjectionMetadata injectionMetadata = processor.buildAutowiringMetadata(PetService.class);
        List<InjectionElement> elements = injectionMetadata.getInjectionElements();
        Assert.assertEquals(2, elements.size());

//        assertFieldExists(elements,"accountDao");
//        assertFieldExists(elements,"itemDao");

        PetService petStore = new PetService();

        injectionMetadata.inject(petStore);

        Assert.assertTrue(petStore.getAccountDao() instanceof AccountDao);

        Assert.assertTrue(petStore.getItemDao() instanceof ItemDao);
    }

    private void assertFieldExists(List<InjectionElement> elements, String fieldName) {
        for(InjectionElement ele : elements){
            AutowiredFieldElement fieldEle = (AutowiredFieldElement)ele;
            Field f = fieldEle.getField();
            if(f.getName().equals(fieldName)){
                return;
            }
        }
        Assert.fail(fieldName + "does not exist!");
    }

}
