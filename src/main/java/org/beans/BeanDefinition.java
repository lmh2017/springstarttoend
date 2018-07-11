package org.beans;

/**
 * @author mh_liu
 * @create 2018/7/5 下午5:57
 * spring内部结构接口
 * 将Bean的定义信息存储到这个BeanDefinition相应的属性中，
 * 后面对Bean的操作就直接对BeanDefinition进行，
 * 例如拿到这个BeanDefinition后，可以根据里面的类名、构造函数、构造函数参数，使用反射进行对象创建。
 * BeanDefinition是一个接口，是一个抽象的定义，实际使用的是其实现类，
 * 如ChildBeanDefinition、RootBeanDefinition、GenericBeanDefinition等。
 */
public interface BeanDefinition {

    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";
    String SCOPE_DEFAULT = "";

    boolean isSingleton();

    boolean isPrototype();

    String getScope();

    void setScope(String scope);

    String getBeanClassName();

}
