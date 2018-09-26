package org.springstarttoend.beans.factory.annotation;

import org.springstarttoend.beans.BeansException;
import org.springstarttoend.beans.factory.BeanCreationException;
import org.springstarttoend.beans.factory.config.AutowireCapableBeanFactory;
import org.springstarttoend.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springstarttoend.beans.factory.support.DefaultBeanFactory;
import org.springstarttoend.core.annotation.AnnotationUtils;
import org.springstarttoend.utils.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * @author mh_liu
 * @create 2018/9/25 下午2:07
 */
public class AutowiredAnnotationProcessor implements InstantiationAwareBeanPostProcessor{

    private AutowireCapableBeanFactory beanFactory;

    private String requiredParamterName = "required";

    private boolean requiredParameterValue = true;

    private final Set<Class<? extends Annotation>> autowiredAnnotationTypes = new LinkedHashSet<>();

    public AutowiredAnnotationProcessor(){
        this.autowiredAnnotationTypes.add(Autowired.class);
    }

    public InjectionMetadata buildAutowiringMetadata(Class<?> clazz ) {
        LinkedList<InjectionElement> elements = new LinkedList<>();
        Class<?> targetClass = clazz;

        do{
            LinkedList<InjectionElement> currElements = new LinkedList<>();
            for(Field field : targetClass.getDeclaredFields()){
                Annotation ann = findAutowiredAnnotation(field);
                if(ann != null){
                    if(Modifier.isStatic(field.getModifiers())){
                        continue;
                    }
                    boolean required = determineRequiredStatus(ann);
                    currElements.add(new AutowiredFieldElement(field, required,beanFactory));

                }
            }

            for (Method method : targetClass.getDeclaredMethods()) {
                Annotation ann = findAutowiredAnnotation(method);
                if(ann != null){
                    if(Modifier.isStatic(method.getModifiers())){
                        continue;
                    }
                    if(method.getParameterTypes().length == 0 ){
                        continue;
                    }
                    boolean required = determineRequiredStatus(ann);
                    currElements.add(new AutowiredMethodElement(method,required,beanFactory));

                }
            }
            elements.addAll(0, currElements);
            targetClass = targetClass.getSuperclass();
        }while (targetClass != null && targetClass != Object.class);

        return new InjectionMetadata(clazz,elements);
    }

    private boolean determineRequiredStatus(Annotation ann) {
        try {
            Method method = ReflectionUtils.findMethod(ann.annotationType(), this.requiredParamterName);
            if (method == null) {
                // Annotations like @Inject and @Value don't have a method (attribute) named "required"
                // -> default to required status
                return true;
            }
            return (this.requiredParameterValue == (Boolean) ReflectionUtils.invokeMethod(method, ann));
        }
        catch (Exception ex) {
            // An exception was thrown during reflective invocation of the required attribute
            // -> default to required status
            return true;
        }
    }

    private Annotation findAutowiredAnnotation(AccessibleObject ao) {
        for (Class<? extends Annotation> type : this.autowiredAnnotationTypes) {
            Annotation ann = AnnotationUtils.getAnnotation(ao, type);
            if (ann != null) {
                return ann;
            }
        }
        return null;    }

    @Override
    public Object beforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    @Override
    public boolean afterInstantiation(Object bean, String beanName) throws BeansException {
        return false;
    }

    @Override
    public void postProcessPropertyValues(Object bean, String beanName) throws BeansException {
        InjectionMetadata metadata = buildAutowiringMetadata(bean.getClass());
        try {
            metadata.inject(bean);
        }
        catch (Throwable ex) {
            throw new BeanCreationException(beanName, "Injection of autowired dependencies failed", ex);
        }
    }

    public void setBeanFactory(DefaultBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }


    @Override
    public Object beforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object afterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }
}
