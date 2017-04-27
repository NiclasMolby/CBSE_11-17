/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.asteroidcollisionosgi;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityType;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.events.Event;
import dk.sdu.mmmi.cbse.common.events.EventType;
import dk.sdu.mmmi.cbse.common.services.IAsteroidCollisionService;

/**
 *
 * @author niclasmolby
 */
public class AsteroidCollision implements IAsteroidCollisionService{

    @Override
    public void astroidBulletCollision(GameData gameData, World world) {
        for(Entity bullet : world.getEntities(EntityType.BULLET)){
            for(Entity astroid : world.getEntities(EntityType.ASTEROIDS)){
                if(checkCollision(bullet, astroid)) {
                                        
                    if(astroid.getLife() == 2){
                        astroid.setLife(1);
                        gameData.addEvent(new Event(EventType.ASTEROID_SPLIT, astroid.getID()));
                    }
                    else {
                        world.removeEntity(astroid);
                    }                               
                    world.removeEntity(bullet);
                }
            }
        }
    }

    @Override
    public void astroidShipCollision(World world) {
        for(Entity ship : world.getEntities(EntityType.PLAYER, EntityType.ENEMY)){
            for(Entity asteroid : world.getEntities(EntityType.ASTEROIDS)){
                if(checkCollision(asteroid, ship)){
                    world.removeEntity(ship);
                }
            }
        }    
    }
    
    private boolean checkCollision(Entity a, Entity b){
        float dx = a.getX() - b.getX();
        float dy = a.getY() - b.getY();
        
        float dist = (float) Math.sqrt(dx * dx + dy * dy);
        
        return (dist < a.getRadius() + b.getRadius());
    }
    
}
