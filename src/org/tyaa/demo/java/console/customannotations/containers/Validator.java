package org.tyaa.demo.java.console.customannotations.containers;

import org.tyaa.demo.java.console.customannotations.annotations.Getter;
import org.tyaa.demo.java.console.customannotations.annotations.ManagedBean;
import org.tyaa.demo.java.console.customannotations.annotations.Setter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Validator {
    // Метод, получающий в качестве параметра объект отражения некоторого класса
    // (рапечатывает информацию об ожидаемых аннотациях из исследуемого типа в консоль)
    public static void validate (Class cl) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        System.out.println("Class name: " + cl.getName());
        // Присутствует ли в описании класса аннотация ControlledObject?
        if(!cl.isAnnotationPresent(ManagedBean.class)){
            System.err.println("No ManagedBean annotation");
        } else {
            // Выводим информацию о найденной аннотации
            System.out.println("The class is annotated ; name  -  " + cl.getAnnotation(ManagedBean.class));
            // получаем объект отражения самой аннотации ManagedBean
            Class<?> managedBeanClass = ManagedBean.class;
            // из отражения аннотации получаем отражение метода (свойство) name
            Method m = managedBeanClass.getDeclaredMethod("name");
            // на объекте отражения метода name вызываем метод вызова (invoke) метода name
            // на объекте аннотации ManagedBean, не передавая ни одного аргумента,
            // и получаем в ответ значение свойства name
            String nameValue =
                    (String) m.invoke(cl.getAnnotation(managedBeanClass), null);
            System.out.println("Bean's name - " + nameValue);
        }
        // Проверка 2:
        // есть ли в описании исследуемого типа (класса) две аннотации,
        // записанные над методами: StartObject и StopObject.
        // Предполагаем, что нет
        boolean hasGetter=false;
        boolean hasSetter=false;
        // Получаем из отражения исследуемого типа отражение всех его открытых методов
        Method[] method = cl.getMethods();
        // Проходим по отражениям всех методов циклом
        for(Method md: method){
            // Присутствует ли над текущим методом аннотация StartObject
            if(md.isAnnotationPresent(Getter.class)) {hasGetter=true;}
            // Присутствует ли над текущим методом аннотация StopObject
            if(md.isAnnotationPresent(Setter.class)) {hasSetter=true;}
            // Как только оказалось, что обе аннотации уже найдены -
            // выходим из цикла
            if(hasGetter && hasSetter){break;}
        }
        // Выводим в консоль отчет о найденных или не найденных аннотациях методов
        System.out.println("Getter annotation  - " + hasGetter + ";  Setter annotation  - " + hasSetter );
        System.out.println("*** End ***");
    }
}
