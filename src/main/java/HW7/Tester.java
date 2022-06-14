package HW7;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Tester {
    public static void start() throws InvocationTargetException, IllegalAccessException {
        Class<Tests> tests = Tests.class;
        Tests test = new Tests();

        Method[] methods = test.getClass().getDeclaredMethods();
        ArrayList<Method> methodsBefore = new ArrayList<>();
        ArrayList<Method> methodsAfter = new ArrayList<>();

        for (Method m:methods
             ) {
            if (m.isAnnotationPresent(BeforeSuite.class)){
                if (methodsBefore.isEmpty()){
                    methodsBefore.add(m);
                }
                else throw new NotUniqueException("Before");
            }
            if (m.isAnnotationPresent(AfterSuite.class)){
                if (methodsAfter.isEmpty()){
                    methodsAfter.add(m);
                }
                else throw new NotUniqueException("After");
            }
        }
        methodsBefore.get(0).invoke(test);

        for (int i=1; i<=10 ; i++) {
            for (Method method:methods
                 ) {
                if (method.isAnnotationPresent(Test.class)){
                    if (method.getAnnotation(Test.class).priority()==i){
                    method.invoke(test);
                    }
                }
            }
        }
        methodsAfter.get(0).invoke(test);
    }
}
