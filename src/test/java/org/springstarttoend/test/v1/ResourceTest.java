package org.springstarttoend.test.v1;

import org.junit.Assert;
import org.junit.Test;
import org.springstarttoend.io.ClassPathResource;
import org.springstarttoend.io.Resource;

import java.io.IOException;
import java.io.InputStream;

public class ResourceTest {

    @Test
    public void testResource(){
        Resource cl = new ClassPathResource("petStore-v1.xml");
        InputStream in = null;

        try {
            in = cl.getInputStream();
            Assert.assertNotNull(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
