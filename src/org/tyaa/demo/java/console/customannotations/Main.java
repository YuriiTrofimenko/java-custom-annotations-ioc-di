package org.tyaa.demo.java.console.customannotations;

import org.reflections.Reflections;
import org.tyaa.demo.java.console.customannotations.beans.Cookies;
import org.tyaa.demo.java.console.customannotations.beans.IsNotBean;
import org.tyaa.demo.java.console.customannotations.containers.Injector;
import org.tyaa.demo.java.console.customannotations.containers.Validator;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException, URISyntaxException, IOException, ClassNotFoundException {

        // Validator.validate(Cookies.class);
        // Validator.validate(IsNotBean.class);

        // Java 8
        /* Reflections reflections = new Reflections("org.tyaa.demo.java.console.customannotations.beans");

        Set<Class<? extends Object>> beans =
                reflections.getSubTypesOf(Object.class);

        for (Class<? extends Object> beanClass : beans) {
            Validator.validate(beanClass);
        } */

        // получение пути к каталогу суб-пакета beans в виде строки
        final String beansPackagePath = Main.class.getResource("beans").toURI().getPath();
        // получение суб-пакета beans в виде строки
        final String beansPackage = Main.class.getPackageName() + ".beans";
        // полный перебор объектов метаданных о всех файлах и суб-каталогах каталога суб-пакета beans
        for(var f : new File(beansPackagePath).listFiles()) {
            // проверка - получено описание суб-каталога или файла
            if(f.isFile()) {
                // если это - описание файла, загружаем одноименный класс в память,
                // получая ссылку на объект его отражения (описания типа)
                Class classForTest = Class.forName(beansPackage + "." + f.getName().replace(".class", ""));
                // валидируем класс предполагаемого бина для нашей системы
                Validator.validate(classForTest);
            }
        }

        /* Controller controller = new Controller();
        Injector.addControlledInstance(controller);
        controller.doWork(); */
    }
}
