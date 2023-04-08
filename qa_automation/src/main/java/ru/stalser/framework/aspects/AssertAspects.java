package ru.stalser.framework.aspects;

import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.model.Parameter;
import io.qameta.allure.model.StepResult;
import io.qameta.allure.util.AspectUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.assertj.core.api.AbstractAssert;
import ru.stalser.framework.helpers.AllureHelper;
import ru.stalser.framework.helpers.JsonHelper;
import ru.stalser.framework.helpers.StepsHierarchy;

import java.lang.reflect.Field;
import java.util.*;

import static ru.stalser.framework.helpers.JsonHelper.serialize;

@Aspect
public class AssertAspects {

    List<String> argsToCapture = Arrays.asList("expected", "other", "otherToString", "regex", "pattern", "condition",
            "requirements", "types", "type", "values", "value", "boundary", "sequence", "prefix", "suffix", "keys", "key",
            "matcher");

    private static final InheritableThreadLocal<AllureLifecycle> LIFECYCLE = new InheritableThreadLocal<>() {

        @Override
        protected AllureLifecycle initialValue() {
            return Allure.getLifecycle();
        }
    };

    @Pointcut("execution(* org.assertj.core.api.*.*(..))")
    public static void anyAbstractAssert() {
//
    }

    @Pointcut("execution(* com.shazam.shazamcrest.*.*(..))")
    public static void shazamCrestAssert() {
//
    }

    @Before("anyAbstractAssert() && shazamCrestAssert()")
    public void apiAssertLog(final JoinPoint joinPoint) throws IllegalAccessException {

        if (Objects.isNull(joinPoint.getSourceLocation()) || joinPoint.getSourceLocation().getFileName().toLowerCase().contains("unknown")) {

            return;
        }

        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Object instance = joinPoint.getThis();
        String infoDescription;

        if (instance instanceof AbstractAssert) {
            AbstractAssert item = (AbstractAssert) instance;
            infoDescription = item.info.descriptionText();
        } else if (methodSignature.getDeclaringTypeName().equalsIgnoreCase("com.shazam.shazamcrest.MatcherAssert")) {

            String name = String.valueOf(joinPoint.getArgs()[0]).replaceAll("\r", "").replaceAll("\n", "");

            final StepResult result = new StepResult().setName("Проверка -- <<" + name + ">>");
            getLifeCycle().startStep(UUID.randomUUID().toString(), result);
            StepsHierarchy.get().onStepStart("Проверка sameBean -- <<" + name + ">>");
            AllureHelper.attachTxt("Фактический", JsonHelper.prettifyJsonString(serialize(joinPoint.getArgs()[1])));
            AllureHelper.attachTxt("Ожидаемый", JsonHelper.prettifyJsonString(serialize(joinPoint.getArgs()[2])));
            StepsHierarchy.get().onStepStop();
            getLifeCycle().stopStep();
            infoDescription = "";
        } else {
            infoDescription = "";
        }

        if (!infoDescription.isEmpty()) {

            Optional<Field> fieldActual = getFields(instance).stream()
                    .filter(f -> f.getName().equalsIgnoreCase("actual"))
                    .findFirst();

            final List<Parameter> parameters = AspectUtils.getParameters(methodSignature, joinPoint.getArgs());
            Optional<String> expected = parameters.stream()
                    .filter(n -> argsToCapture.contains(n.getName().toLowerCase()))
                    .findFirst()
                    .map(Parameter::getValue);

            StringBuilder sb = new StringBuilder();
            sb.append("<<");
            sb.append(infoDescription);
            sb.append(">>");
            sb.append(" ");
            sb.append(methodSignature.getName());
            if (expected.isPresent()) {
                sb.append(" ");
                sb.append("<<");
                sb.append(expected.get());
                sb.append(">>");
            }

            final String stepName = sb.toString();

            final StepResult result = new StepResult().setName(stepName);
            getLifeCycle().startStep(UUID.randomUUID().toString(), result);
            if (fieldActual.isPresent()) {
                fieldActual.get().setAccessible(true);
                result.setParameters(
                        Arrays.asList(
                                new Parameter().setName(fieldActual.get().getName()).setValue(String.valueOf(fieldActual.get().get(instance)))
                        )
                );
                StepsHierarchy.get().onStepStart(stepName + " (actual = " + fieldActual.get().get(instance) + ")");
            } else {
                Optional<String> actual = parameters.stream()
                        .filter(n -> n.getName().equalsIgnoreCase("actual"))
                        .findFirst()
                        .map(Parameter::getValue);
                if (actual.isPresent() && !actual.get().isEmpty()) {
                    StepsHierarchy.get().onStepStart(stepName);
                }
            }

            StepsHierarchy.get().onStepStop();
            getLifeCycle().stopStep();
        }
    }

    private AllureLifecycle getLifeCycle() {

        return LIFECYCLE.get();
    }

    private <T> List<Field> getFields(T t) {

        List<Field> fields = new ArrayList<>();
        Class clazz = t.getClass();
        while (clazz != Object.class) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }
}
