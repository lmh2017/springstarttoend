package org.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author mh_liu
 * @create 2018/7/6 上午9:41
 */
public interface Resource {

    InputStream getInputStream() throws IOException;
    String getDescription();
}
