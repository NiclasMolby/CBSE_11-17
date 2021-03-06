/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.enemyprocessor;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityType;
import static dk.sdu.mmmi.cbse.common.data.EntityType.ENEMY;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.events.Event;
import dk.sdu.mmmi.cbse.common.events.EventType;
import dk.sdu.mmmi.cbse.commonenemy.IProc;
import java.util.Random;

/**
 *
 * @author niclasmolby
 */
public class EnemyProcessingBean implements IProc {
    
    private int timer = 50;
    private int shotTimer = 50;
    private boolean right = false;
    private boolean left = false;
    private boolean up = false;
    private Entity enemy;
 
    
    public void start(GameData gameData, World world) {
        enemy = createEnemyShip(gameData);
        setShape();
        world.addEntity(enemy);
    }
    
    private Entity createEnemyShip(GameData gameData) {
        Entity enemyShip = new Entity();
        enemyShip.setType(ENEMY);

        enemyShip.setPosition(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);

        enemyShip.setMaxSpeed(300);
        enemyShip.setAcceleration(200);
        enemyShip.setDeacceleration(10);

        enemyShip.setRadians(3.1415f / 2);
        enemyShip.setRotationSpeed(3);
        
        enemyShip.setLife(3);
        enemyShip.setRadius(8);

        return enemyShip;
    }
    
    public void setShape(){
        float[] shapex = new float[4];
        float[] shapey = new float[4];
        float x = enemy.getX();
        float y = enemy.getY();
        float radians = enemy.getRadians();
        
        shapex[0] = (float) (x + Math.cos(radians) * 8);
	shapey[0] = (float) (y + Math.sin(radians) * 8);
		
	shapex[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5) * 8);
	shapey[1] = (float) (y + Math.sin(radians - 4 * 3.1145f / 5) * 8);
		
	shapex[2] = (float) (x + Math.cos(radians + 3.1415f) * 5);
	shapey[2] = (float) (y + Math.sin(radians + 3.1415f) * 5);
		
	shapex[3] = (float) (x + Math.cos(radians + 4 * 3.1415f / 5) * 8);
	shapey[3] = (float) (y + Math.sin(radians + 4 * 3.1415f / 5) * 8);
        
        enemy.setShapeX(shapex);
        enemy.setShapeY(shapey);
    }
    
    @Override
    public void process(GameData gameData, World world) {
        Random rand = new Random();
        
        // TODO: Implement entity processor
        for(Entity enemy : world.getEntities(EntityType.ENEMY)){
       
            timer--;
            if(timer < 0){
                right = rand.nextBoolean();
                left = rand.nextBoolean();
                up = rand.nextBoolean();
                timer = 50;
            }
            
            shotTimer--;
            if(shotTimer < 0) {
                gameData.addEvent(new Event(EventType.ENEMY_SHOOT, enemy.getID()));
                shotTimer = rand.nextInt(100)+10;
            }

            if(right) {
                enemy.setRadians(enemy.getRadians()-enemy.getRotationSpeed() * gameData.getDelta());
            }

            if(left) {
                enemy.setRadians(enemy.getRadians()+enemy.getRotationSpeed() * gameData.getDelta());
            }

            if(up) {
                enemy.setDx(enemy.getDx() + ((float) Math.cos(enemy.getRadians())) * enemy.getAcceleration() * gameData.getDelta());
                enemy.setDy(enemy.getDy() + ((float) Math.sin(enemy.getRadians())) * enemy.getAcceleration() * gameData.getDelta());
            }

            float vec = (float) Math.sqrt(enemy.getDx() * enemy.getDx() + enemy.getDy() * enemy.getDy());

            if(vec > 0){
                enemy.setDx(enemy.getDx() - (enemy.getDx() / vec) * enemy.getDeacceleration() * gameData.getDelta());
                enemy.setDy(enemy.getDy() - (enemy.getDy() / vec) * enemy.getDeacceleration() * gameData.getDelta());
            }

            if(vec > enemy.getMaxSpeed()) {
                enemy.setDx((enemy.getDx() / vec) * enemy.getMaxSpeed());
                enemy.setDy((enemy.getDy() / vec) * enemy.getMaxSpeed());
            }

            enemy.setX(enemy.getX() + enemy.getDx() * gameData.getDelta());
            enemy.setY(enemy.getY() + enemy.getDy() * gameData.getDelta());


            if(enemy.getX() < 0) enemy.setX(gameData.getDisplayWidth());
            if(enemy.getX() > gameData.getDisplayWidth()) enemy.setX(0);
            if(enemy.getY() < 0) enemy.setY(gameData.getDisplayHeight());
            if(enemy.getY() > gameData.getDisplayHeight()) enemy.setY(0);
        }
    
    }
}
