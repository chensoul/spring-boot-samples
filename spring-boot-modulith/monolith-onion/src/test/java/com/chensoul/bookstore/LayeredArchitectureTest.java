package com.chensoul.bookstore;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(packages = "com.chensoul.bookstore")
public class LayeredArchitectureTest {
    @ArchTest
    static final ArchRule layer_dependencies_are_respected = layeredArchitecture().consideringAllDependencies()

            .layer("adapter").definedBy("..adapter..")
            .layer("application").definedBy("..application..")
            .layer("domain").definedBy("..domain..")

            .whereLayer("adapter").mayNotBeAccessedByAnyLayer()
            .whereLayer("application").mayOnlyBeAccessedByLayers("adapter")
            .whereLayer("domain").mayOnlyBeAccessedByLayers("application", "adapter");

    @ArchTest
    static final ArchRule domain_should_not_access_services =
            noClasses().that().resideInAPackage("..domain..")
                    .should().accessClassesThat().resideInAPackage("..application..");
}