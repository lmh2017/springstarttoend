package org.springstarttoend.context.annotation;

import org.springstarttoend.beans.BeanDefinition;
import org.springstarttoend.beans.factory.BeanDefinitionStoreException;
import org.springstarttoend.beans.factory.support.BeanDefinitionRegistry;
import org.springstarttoend.beans.factory.support.BeanNameGenerator;
import org.springstarttoend.core.io.Resource;
import org.springstarttoend.core.io.support.PackageResourceLoader;
import org.springstarttoend.core.type.classreading.MetadataReader;
import org.springstarttoend.core.type.classreading.SimpleMetadataReader;
import org.springstarttoend.stereotype.Component;
import org.springstarttoend.utils.StringUtils;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author mh_liu
 * @create 2018/9/7 下午5:56
 */
public class ClassPathBeanDefinitionScanner {

    private final BeanDefinitionRegistry registry;

    private PackageResourceLoader resourceLoader = new PackageResourceLoader();

    private BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry factory) {
        this.registry = factory;
    }

    public Set<BeanDefinition> doScan(String packagesToScan) {
        String[] basePackages = StringUtils.tokenizeToStringArray(packagesToScan,",");

        Set<BeanDefinition> beanDefinitions = new LinkedHashSet<>();
        for(String basePackage : basePackages){
            Set<BeanDefinition> candidates = findCandidateCompents(basePackage);
            for(BeanDefinition candidate : candidates){
                beanDefinitions.add(candidate);
                registry.registerBeanDefinition(candidate.getID(),candidate);
            }
        }
        return beanDefinitions;
    }

    public Set<BeanDefinition> findCandidateCompents(String basePackage) {
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        try {
            Resource[] resources = this.resourceLoader.getResources(basePackage);

            for (Resource resource : resources){
                try {
                    MetadataReader metadataReader = new SimpleMetadataReader(resource);

                    if(metadataReader.getAnnotationMetadata().hasAnnotation(Component.class.getName())){
                        ScannedGenericBeanDefinition sbd = new ScannedGenericBeanDefinition(metadataReader.getAnnotationMetadata());
                        String beanName = this.beanNameGenerator.generateBeanName(sbd, this.registry);
                        sbd.setId(beanName);
                        candidates.add(sbd);
                    }
                }catch (Throwable ex){
                    throw new BeanDefinitionStoreException(
                            "Failed to read candidate component class: " + resource, ex);
                }
            }
        }catch (Exception e){
            throw new BeanDefinitionStoreException("I/O failure during classpath scanning", e);
        }
        return candidates;
    }
}
