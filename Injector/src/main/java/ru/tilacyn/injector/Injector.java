package ru.tilacyn.injector;

import com.sun.istack.internal.Pool;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Injector {

    static class AmbigiousImplementationException extends Exception {
    }

    static class ImplementationNotFoundException extends Exception {
    }

    static class InjectionCycleException extends Exception {
    }


    static HashMap<Class, Integer> color = new HashMap<>();


    public static Object initialize(String rootClassName, ArrayList<Class<?>> classes) throws
            AmbigiousImplementationException,
            InjectionCycleException,
            ImplementationNotFoundException,
            ClassNotFoundException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException {

        Class rootClass = Class.forName(rootClassName);

        // System.out.println("Class: " + rootClassName);

        if (color.containsKey(rootClass) && color.get(rootClass) == 1) {
            throw new InjectionCycleException();
        }

        color.put(rootClass, 1);

        Object result = null;

        boolean foundAlready = false;

        for (Class currentClass : classes) {
            if (rootClass.isAssignableFrom(currentClass)) {
                if (foundAlready) {
                    throw new AmbigiousImplementationException();
                }
                foundAlready = true;
                if (currentClass.equals(rootClass)) {
                    Constructor constructor = rootClass.getConstructors()[0];

                    
                    Object[] args = new Object[constructor.getParameterCount()];

                    for (int i = 0; i < constructor.getParameterCount(); i++) {
                        args[i] = initialize(constructor.getParameterTypes()[i].getName(), classes);
                    }

                    result = constructor.newInstance(args);

                    color.put(rootClass, 2);
                } else {
                    result = initialize(currentClass.getName(), classes);
                    color.put(rootClass, 2);
                }
            }
        }

        if (!foundAlready) {
            throw new ImplementationNotFoundException();
        }

        return result;
    }
}
