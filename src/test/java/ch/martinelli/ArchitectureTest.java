package ch.martinelli;

import jdepend.framework.DependencyConstraint;
import jdepend.framework.JDepend;
import jdepend.framework.JavaPackage;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class ArchitectureTest {

    private static JDepend jdepend = new JDepend();

    @BeforeClass
    public static void beforeClass() throws IOException {
        jdepend.addDirectory("C:\\Users\\simon\\Workspace\\architecture-check-with-jdepend\\target\\classes");
    }

    @Test
    public void checkArchitecture() {
        DependencyConstraint constraint = new DependencyConstraint();

        JavaPackage root = constraint.addPackage("ch.martinelli");
        JavaPackage boundary = constraint.addPackage("ch.martinelli.boundary");
        JavaPackage service = constraint.addPackage("ch.martinelli.control.service");
        JavaPackage repository = constraint.addPackage("ch.martinelli.control.repository");
        JavaPackage entity = constraint.addPackage("ch.martinelli.entity");
        JavaPackage javaLang = constraint.addPackage("java.lang");
        JavaPackage javaUtil = constraint.addPackage("java.util");
        JavaPackage springBoot = constraint.addPackage("org.springframework.boot");
        JavaPackage springDataJpaRepository = constraint.addPackage("org.springframework.data.jpa.repository");

        boundary.dependsUpon(service);
        boundary.dependsUpon(entity);

        service.dependsUpon(repository);
        service.dependsUpon(entity);

        jdepend.analyze();

        assertEquals("Architecture violation", true, jdepend.dependencyMatch(constraint));
    }

    @Test
    public void checkCycles() {
        Collection packages = jdepend.analyze();

        assertEquals("Cycles exist", false, jdepend.containsCycles());
    }
}
