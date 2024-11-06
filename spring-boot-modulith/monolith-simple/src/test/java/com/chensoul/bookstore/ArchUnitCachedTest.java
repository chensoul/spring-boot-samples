package com.chensoul.bookstore;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import java.math.BigDecimal;

@AnalyzeClasses(packages = "com.chensoul.bookstore")
public class ArchUnitCachedTest {
    @ArchTest
    public void doNotCallDeprecatedMethodsFromTheProject(JavaClasses classes) {
        ArchRule rule = noClasses().should().dependOnClassesThat().areAnnotatedWith(Deprecated.class);
        rule.check(classes);
    }

    @ArchTest
    public void doNotCallConstructorCached(JavaClasses classes) {
        ArchRule rule = noClasses().should().callConstructor(BigDecimal.class, double.class);
        rule.check(classes);
    }

    public void thisMethodCallsTheWrongBigDecimalConstructor() {
//        BigDecimal value = new BigDecimal(123.0);
        BigDecimal value = new BigDecimal("123.0"); // works!
    }

    @Deprecated
    public class Dep {

    }
}