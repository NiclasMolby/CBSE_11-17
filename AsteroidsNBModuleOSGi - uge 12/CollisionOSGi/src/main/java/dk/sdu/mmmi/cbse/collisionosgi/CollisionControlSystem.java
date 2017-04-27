package dk.sdu.mmmi.cbse.collisionosgi;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityType;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.events.Event;
import dk.sdu.mmmi.cbse.common.events.EventType;
import dk.sdu.mmmi.cbse.common.services.IAsteroidCollisionService;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import org.osgi.framework.ServiceReference;

/**
 *
 * @author niclasmolby
 */

public class CollisionControlSystem implements IEntityProcessingService {

    private IAsteroidCollisionService asteroidCollisionService;
    
    @Override
    public void process(GameData gameData, World world) {
        
        asteroidCollisionService.astroidBulletCollision(gameData, world);
        asteroidCollisionService.astroidShipCollision(world);
                
        bulletShip(world);
        
        //asteroidAsteroid(gameData, world);
    }
    
    private void asteroidAsteroid(GameData gameData, World world){
        for(Entity asteroid : world.getEntities(EntityType.ASTEROIDS)){
            for(Entity asteroid2 : world.getEntities(EntityType.ASTEROIDS)){
                if(asteroid != asteroid2 && checkCollision(asteroid, asteroid2)){
                    asteroid.setDx((-asteroid.getDx() + ((float) Math.cos(asteroid.getRadians() + 3.1415f)) * gameData.getDelta()));
                    asteroid.setDy((-asteroid.getDy() + ((float) Math.sin(asteroid.getRadians() + 3.1415f)) * gameData.getDelta()));
                }
            }
        }
    }
    
    private void bulletShip(World world){
        for(Entity ship : world.getEntities(EntityType.ENEMY, EntityType.PLAYER)){
            for(Entity bullet : world.getEntities(EntityType.BULLET)){
                if(checkCollision(bullet, ship)){
                    ship.setLife(ship.getLife()-1);
                    System.out.println(ship.getType() + " hit! His life is now: "+ship.getLife());
                    if(ship.getLife() == 0) {
                        world.removeEntity(ship);
                    }
                    world.removeEntity(bullet);
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
    
    public void installProcessingService(IAsteroidCollisionService asteroidCollisionService) {
        System.out.println("Bind Service");
        this.asteroidCollisionService = asteroidCollisionService;
    }

    public void uninstallProcessingService(IAsteroidCollisionService asteroidCollisionService) {
        System.out.println("unbind Service");
        this.asteroidCollisionService = null;
    }
    
}
