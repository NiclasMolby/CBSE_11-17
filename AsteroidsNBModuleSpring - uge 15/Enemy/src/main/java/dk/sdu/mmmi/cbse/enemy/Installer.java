/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.enemy;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import org.openide.modules.ModuleInstall;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Installer extends ModuleInstall {
    
    private EnemyControlSystem ecs;

    @Override
    public void restored() {
        System.out.println("kald af restored1");
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        //ecs = context.getBean(EnemyControlSystem.class);
        System.out.println("kald af restored2");
    }

}
