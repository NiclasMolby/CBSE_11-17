/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.player;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.player.playerimpl.EntityPlugin;
import dk.sdu.mmmi.cbse.player.playerimpl.PlayerControlSystem;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@ServiceProviders(value = {
    @ServiceProvider(service = IGamePluginService.class),
    @ServiceProvider(service = IEntityProcessingService.class)
})
public class PlayerController implements IEntityProcessingService, IGamePluginService {
    
    private EntityPlugin plugin;
    private PlayerControlSystem controlSystem;
    
    public PlayerController() {
        ApplicationContext context = new ClassPathXmlApplicationContext("PlayerBeans.xml");
        plugin = (EntityPlugin) context.getBean("pluginBean");
        controlSystem = (PlayerControlSystem) context.getBean("processBean");
    }

    @Override
    public void process(GameData gameData, World world) {
        controlSystem.process(gameData, world);
    }

    @Override
    public void start(GameData gameData, World world) {
        plugin.start(gameData, world);
    }

    @Override
    public void stop(GameData gameData, World world) {
        plugin.stop(gameData, world);
    }

    @Override
    public void setShape() {
        plugin.setShape();
    }  
    
}
