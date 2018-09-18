package org.springstarttoend.test.v4;

import org.junit.Assert;
import org.junit.Test;
import org.springstarttoend.context.ApplicationContext;
import org.springstarttoend.context.support.ClassPathXmlApplicationContext;
import org.springstarttoend.service.v4.PetService;

/**
 * @author mh_liu
 * @create 2018/9/17 下午3:39
 */
public class ApplicationContextTest4 {

    @Test
    public void testGetBeanProperty(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petStore-v4.xml");
        PetService petService = (PetService)ctx.getBean("petService");

        Assert.assertNotNull(petService.getAccountDao());
        Assert.assertNotNull(petService.getItemDao());
    }
}
