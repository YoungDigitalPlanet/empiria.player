import org.gradle.api.*
import org.gradle.api.tasks.*
import org.gradle.api.plugins.*

class GWTCompile extends DefaultTask {
    def File buildDir;
    def String module;

    @TaskAction
    public void exec() {
        def javaConvention = project.getConvention().getPlugin(JavaPluginConvention);
        def mainSourceSet = javaConvention.getSourceSets().getByName(SourceSet.MAIN_SOURCE_SET_NAME);
        buildDir.mkdirs()

        project.javaexec {
            main = 'com.google.gwt.dev.Compiler'
            classpath {
                [
                    mainSourceSet.java.srcDirs,           // Java source
                    mainSourceSet.output.resourcesDir,    // Generated resources
                    mainSourceSet.output.classesDir,      // Generated classes
                    mainSourceSet.compileClasspath,       // Deps
                ]   
            }
         
            args =
                [
                    this.module, // Your GWT module
                    '-war', this.buildDir.absolutePath,
                    '-logLevel', 'INFO',
                    '-localWorkers', '2',
                    '-XdisableCastChecking'
                ]

            systemProperty 'gwt.args', '-logLevel WARN'
            systemProperty 'java.awt.headless', 'true'

            maxHeapSize = '512M'
            jvmArgs( '-XX:MaxPermSize=256M' )
        }
    }
}