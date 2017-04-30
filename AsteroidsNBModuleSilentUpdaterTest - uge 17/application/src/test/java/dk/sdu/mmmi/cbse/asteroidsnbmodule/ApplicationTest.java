package dk.sdu.mmmi.cbse.asteroidsnbmodule;

import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.io.IOException;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import junit.framework.Test;
import org.netbeans.junit.NbModuleSuite;
import org.netbeans.junit.NbTestCase;
import org.openide.util.Lookup;

public class ApplicationTest extends NbTestCase {
    
        private static final String ADD_PLAYER = "/Users/niclasmolby/NetBeansProjects/CBSE_uge_11-17/AsteroidsUpdateCenterTest/netbeans_site/updatesLoadPlayer.xml";
    private static final String REMOVE_PLAYER = "/Users/niclasmolby/NetBeansProjects/CBSE_uge_11-17/AsteroidsUpdateCenterTest/netbeans_site/updatesUnloadPlayer.xml";
    private static final String UPDATE_CENTER = "/Users/niclasmolby/NetBeansProjects/CBSE_uge_11-17/AsteroidsUpdateCenterTest/netbeans_site/updates.xml";

    public static Test suite() {
        return NbModuleSuite.createConfiguration(ApplicationTest.class).
                gui(false).
                failOnMessage(Level.WARNING). // works at least in RELEASE71
                failOnException(Level.INFO).
                enableClasspathModules(false). 
                clusters(".*").
                suite(); // RELEASE71+, else use NbModuleSuite.create(NbModuleSuite.createConfiguration(...))
    }

    public ApplicationTest(String n) {
        super(n);
    }

    public void testApplication() throws InterruptedException, IOException {
        // pass if there are merely no warnings/exceptions
        /* Example of using Jelly Tools (additional test dependencies required) with gui(true):
        new ActionNoBlock("Help|About", null).performMenu();
        new NbDialogOperator("About").closeByButton();
         */
        
        List<IEntityProcessingService> processors = new CopyOnWriteArrayList<>();
        List<IGamePluginService> plugins = new CopyOnWriteArrayList<>();
        waitForUpdate(processors, plugins);
        
        assertEquals("No plugins", 0, plugins.size());
        assertEquals("No processor", 0, processors.size());
        
        copy(get(ADD_PLAYER), get(UPDATE_CENTER), REPLACE_EXISTING);
        waitForUpdate(processors, plugins);

        assertEquals("One plugin", 1, plugins.size());
        assertEquals("One processor", 1, plugins.size());
        
        copy(get(REMOVE_PLAYER), get(UPDATE_CENTER), REPLACE_EXISTING);
        waitForUpdate(processors, plugins);
        
        assertEquals("No plugins", 0, plugins.size());
        assertEquals("No processor", 0, processors.size());
    }
    
    private void waitForUpdate(List<IEntityProcessingService> processors, List<IGamePluginService> plugins) throws InterruptedException {
        Thread.sleep(10000);
        processors.clear();
        processors.addAll(Lookup.getDefault().lookupAll(IEntityProcessingService.class));
        
        plugins.clear();
        plugins.addAll(Lookup.getDefault().lookupAll(IGamePluginService.class));
    }

}
