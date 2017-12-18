package ru.tilacyn.injector;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class InjectorTest {
    interface Interface1 {
    }

    abstract class Interface2 {
    }

    interface Interface3 {
    }


    public class A {
        public A(Interface1 x) {
        }
    }

    public class B implements Interface1 {
        public B() {
        }
    }

    public class C extends Interface2 {
        public C(Interface3 x) {

        }
    }

    public class D implements Interface3 {
        public D(Interface2 x) {

        }
    }


    @Test
    public void initialize() throws Exception {
        ArrayList<Class<?>> vector = new ArrayList<>();

        vector.add(B.class);
        vector.add(A.class);
        vector.add(InjectorTest.class);

        assertEquals(Injector.initialize(A.class.getName(), vector).getClass(), A.class);

        vector.clear();

        vector.add(Interface1.class);
        vector.add(InjectorTest.class);

        try {
            Injector.initialize(A.class.getName(), vector);
            assertFalse(true);
        } catch (Injector.ImplementationNotFoundException e) {

        }


        vector.clear();


        vector.add(C.class);
        vector.add(D.class);
        vector.add(InjectorTest.class);

        try {
            Injector.initialize(C.class.getName(), vector);
            assertFalse(true);
        } catch (Injector.InjectionCycleException e) {
            //e.printStackTrace();
        }
    }

}