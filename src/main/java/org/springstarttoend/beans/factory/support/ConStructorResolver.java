package org.springstarttoend.beans.factory.support;

import org.apache.log4j.Logger;
import org.springstarttoend.beans.BeanDefinition;
import org.springstarttoend.beans.ConstructorArgument;
import org.springstarttoend.beans.SimpleTypeConverter;
import org.springstarttoend.beans.factory.BeanCreationException;
import org.springstarttoend.beans.factory.config.ConfigurableBeanFactory;

import java.lang.reflect.Constructor;
import java.util.List;

public class ConStructorResolver {

    protected final Logger logger = Logger.getLogger(ConStructorResolver.class);

    private final ConfigurableBeanFactory beanFactory;

    public ConStructorResolver(ConfigurableBeanFactory beanFactory){
        this.beanFactory = beanFactory;
    }


    public Object autowireConstructor(final BeanDefinition bd) {
        Constructor<?> constructorToUse = null;
        Object[] argToUse = null;

        Class<?> beanClass = null;

        try {
            beanClass = this.beanFactory.getBeanClassLoader().loadClass(bd.getBeanClassName());
        } catch (ClassNotFoundException e) {
            throw new BeanCreationException(bd.getID(),"Instantiation of bean fialed,can't be created");
        }
        Constructor<?>[] condidates = beanClass.getConstructors();

        BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this.beanFactory);

        ConstructorArgument cargs = bd.getConstructorArgument();

        SimpleTypeConverter typeConverter = new SimpleTypeConverter();

        for(int i = 0;i<condidates.length;i++){
            Class<?>[] parameterTypes = condidates[i].getParameterTypes();
            if(parameterTypes.length != cargs.getArgumentCount()){
                continue;
            }
            argToUse = new Object[parameterTypes.length];

            boolean result = this.valueMatchTypes(parameterTypes,
                    cargs.getArgumentValues(),
                    argToUse,
                    valueResolver,
                    typeConverter);
            if(result){
                constructorToUse = condidates[i];
                break;
            }
        }

        //找不到一个合适的构造函数
        if(constructorToUse == null){
            throw new BeanCreationException(bd.getID(),"can't find a apporiate constructor");
        }

        try{
            return constructorToUse.newInstance(argToUse);
        }catch (Exception e){
            throw new BeanCreationException(bd.getID(),"can't find a create instance using");
        }
    }

    private boolean valueMatchTypes(Class<?>[] parameterTypes, List<ConstructorArgument.ValueHolder> argumentValues, Object[] argToUse, BeanDefinitionValueResolver valueResolver, SimpleTypeConverter typeConverter) {
        for(int i=0;i<parameterTypes.length;i++){
            ConstructorArgument.ValueHolder valueHolder = argumentValues.get(i);
            //获取xml解析出来的参数的值，参数的值有可能为RuntimeBeanReference类型，也可能是TypedStringValue类型
            Object originalValue = valueHolder.getValue();

            try{
                Object resolverValue = valueResolver.resolveValueIfNecessary(originalValue);
                //如果参数类型是int，但是值是字符串，例如"3"，则还需要转型
                //如果转型失败，则抛出异常，代表参数不匹配，返回flase
                Object value = typeConverter.convertIfNecessary(resolverValue,parameterTypes[i]);
                argToUse[i] = value;

            }catch (Exception e){
                logger.equals(e);
                return false;
            }
        }
        return true;
    }
}
