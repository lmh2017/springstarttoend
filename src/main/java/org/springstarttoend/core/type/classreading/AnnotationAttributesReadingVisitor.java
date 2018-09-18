package org.springstarttoend.core.type.classreading;

import org.springframework.asm.AnnotationVisitor;
import org.springframework.asm.SpringAsmInfo;
import org.springstarttoend.core.annotation.AnnotationAttributes;

import java.util.Map;

/**
 * @author mh_liu
 * @create 2018/9/15 下午3:27
 */
public class AnnotationAttributesReadingVisitor extends AnnotationVisitor {

    private final String annotationType;

    private final Map<String,AnnotationAttributes> attributesMap;

    AnnotationAttributes attributes = new AnnotationAttributes();

    public AnnotationAttributesReadingVisitor(String annotationType, Map<String, AnnotationAttributes> attributesMap) {
        super(SpringAsmInfo.ASM_VERSION);
        this.annotationType = annotationType;
        this.attributesMap = attributesMap;
    }

    @Override
    public final void visitEnd(){
        this.attributesMap.put(this.annotationType,this.attributes);
    }

    @Override
    public void visit(String attributeName, Object attributeValue){
        this.attributes.put(attributeName,attributeValue);
    }
}
