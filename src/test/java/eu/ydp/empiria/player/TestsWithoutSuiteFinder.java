package eu.ydp.empiria.player;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import java.util.Set;

public class TestsWithoutSuiteFinder extends Task {
    private String assigne;

    @Override
    public void execute() throws BuildException {
        getProject().setProperty(assigne, Joiner.on(",").join(unsupportedTests()));
    }

    public Iterable<String> unsupportedTests() {
        Set<Class<?>> testClasses = Sets.newHashSet();
        testClasses.addAll(GWTTestCaseSuite.getAllTestClasses());
        testClasses.addAll(JUnitTestSuite.getAllTestClasses());

        testClasses.removeAll(GWTTestCaseSuite.getTestClasses());
        testClasses.removeAll(JUnitTestSuite.getTestClasses());
        return Iterables.transform(testClasses, new Function<Class<?>, String>() {
            @Override
            public String apply(final Class<?> clazz) {
                return "**/*" + clazz.getSimpleName() + ".java";
            }
        });
    }

    // public static void main(final String[] args) {
    // System.out.println(new TestsWithoutSuiteFinder().unsupportedTests());
    // }

    public void setAssigne(final String assigne) {
        this.assigne = assigne;
    }
}
