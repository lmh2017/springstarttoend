package org.springstarttoend.test.v4;

import org.junit.Assert;
import org.junit.Test;
import org.springstarttoend.core.io.Resource;
import org.springstarttoend.core.io.support.PackageResourceLoader;

import java.io.IOException;

/**
 * @author mh_liu
 * @create 2018/9/7 下午6:07
 */
public class PackageResourceLoaderTest {

    @Test
    public void testGetResource() throws IOException{
        PackageResourceLoader loader = new PackageResourceLoader();
        Resource[] resources = loader.getResources("org.springstarttoend.dao.v4");
        Assert.assertEquals(2, resources.length);
    }

}
