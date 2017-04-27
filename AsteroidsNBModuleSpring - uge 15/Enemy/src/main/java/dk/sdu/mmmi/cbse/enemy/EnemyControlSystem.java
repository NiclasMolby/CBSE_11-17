package dk.sdu.mmmi.cbse.enemy;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.commonenemy.IProc;
import dk.sdu.mmmi.cbse.enemy.enemyimpl.EnemyProcessingBean;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author jcs
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class)
})
public class EnemyControlSystem implements IEntityProcessingService {

    private EnemyProcessingBean processBean;
    
    public EnemyControlSystem() {
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

        //System.out.println("hej11111");
        processBean =  (EnemyProcessingBean) context.getBean("processBean");
    }
    
    @Override
    public void process(GameData gameData, World world) {
        //System.out.println("hej");
        processBean.process(gameData, world);
    }
    
    public void setProcessBean(EnemyProcessingBean processBean) {
        this.processBean = processBean;
    }
    
}
