package org.springstarttoend.core.type.classreading;


import org.springframework.asm.AnnotationVisitor;
import org.springframework.asm.Type;
import org.springstarttoend.core.annotation.AnnotationAttributes;
import org.springstarttoend.core.type.AnnotationMetadata;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author mh_liu
 * @create 2018/9/15 下午3:02
 */
public class AnnotationMetadataReadingVisitor extends ClassMetadataReadingVisitor implements AnnotationMetadata {

    private final Set<String> annotationSet = new LinkedHashSet<>(4);
    private final Map<String, AnnotationAttributes> attributesMap = new LinkedHashMap<>(4);

    public AnnotationMetadataReadingVisitor(){

    }

    /**
     * 这里返回的AnnotationVisitor为什么自定义重写这个类呢?
     * 因为我们引用的第三方解析工具ASM 在解析字节码的时候会引入AnnotationVisitor类，若重写此类，则需要重写ClassReader方法,ASM源码我们直接引用的，这里就没必要在这里下功夫了。
     * @param desc
     * @param visible
     * @return
     */
    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        String className = Type.getType(desc).getClassName();
        this.annotationSet.add(className);
        return new AnnotationAttributesReadingVisitor(className, this.attributesMap);
    }

    @Override
    public Set<String> getAnnotationTypes() {
        return this.annotationSet;
    }

    @Override
    public boolean hasAnnotation(String annotationType) {
        return this.annotationSet.contains(annotationType);
    }

    @Override
    public AnnotationAttributes getAnnotationAttributes(String annotationType) {
        return this.attributesMap.get(annotationType);
    }
}
