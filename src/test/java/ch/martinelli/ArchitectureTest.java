package ch.martinelli;

import jdepend.framework.DependencyConstraint;
import jdepend.framework.JDepend;
import jdepend.framework.JavaPackage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collection;


public class ArchitectureTest {

    private static JDepend jdepend = new JDepend();

    @BeforeAll
    public static void beforeClass() throws IOException {
        jdepend.addDirectory("C:\\Users\\simon\\Workspace\\architecture-check-with-jdepend\\target\\classes");
    }

    @Test
    public void checkArchitecture() {
        DependencyConstraint constraint = new DependencyConstraint();

        JavaPackage root = constraint.addPackage("ch.martinelli");
        JavaPackage api = constraint.addPackage("ch.martinelli.api");
        JavaPackage service = constraint.addPackage("ch.martinelli.service");
        JavaPackage repository = constraint.addPackage("ch.martinelli.repository");
        JavaPackage entity = constraint.addPackage("ch.martinelli.entity");


        JavaPackage springBoot = constraint.addPackage("org.springframework.boot");
        JavaPackage springDataJpaRepository = constraint.addPackage("org.springframework.data.jpa.repository");
        JavaPackage javaLang = constraint.addPackage("java.lang");
        JavaPackage javaUtil = constraint.addPackage("java.util");

        api.dependsUpon(service);
        api.dependsUpon(entity);

        service.dependsUpon(repository);
        service.dependsUpon(entity);

        root.dependsUpon(springBoot);
        repository.dependsUpon(springDataJpaRepository);

        jdepend.analyze();

        Assertions.assertTrue(jdepend.dependencyMatch(constraint));
    }

    @Test
    public void checkCycles() {
        Collection packages = jdepend.analyze();

        Assertions.assertFalse(jdepend.containsCycles());
    }
}
