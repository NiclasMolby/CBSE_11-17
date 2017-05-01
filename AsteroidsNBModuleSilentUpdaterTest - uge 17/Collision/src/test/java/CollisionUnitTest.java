/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dk.sdu.mmmi.cbse.collision.CollisionControlSystem;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityType;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author niclasmolby
 */
public class CollisionUnitTest {
    
    private CollisionControlSystem collision;
    private World world;
    private GameData gameData;
    private Entity bullet;
    private Entity asteroid;
    private List<Entity> asteroids;
    private List<Entity> bullets;
    
    public CollisionUnitTest() {
    }
    
    @Before
    public void setUp() {
        collision = new CollisionControlSystem();
        
        setUpBullet();
        setUpAsteroid();
        
        world = mock(World.class);
        gameData = mock(GameData.class);
        
        asteroids = new ArrayList<>();
        bullets = new ArrayList<>();
        asteroids.add(asteroid);
        bullets.add(bullet);
        
        when(world.getEntities(EntityType.ASTEROIDS)).thenReturn(asteroids);
        when(world.getEntities(EntityType.BULLET)).thenReturn(bullets);
        
        
    }
    
    private void setUpBullet() {
        bullet = new Entity();
        bullet.setType(EntityType.BULLET);
        bullet.setPosition(0, 0);
        bullet.setRadius(8);
    }
    
    private void setUpAsteroid() {
        asteroid = new Entity();
        asteroid.setType(EntityType.ASTEROIDS);
        asteroid.setPosition(50, 50);
        asteroid.setRadius(16);
        asteroid.setLife(2);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testCollision() {
        assertFalse("Collsion where it shouldn't be", collision.checkCollision(bullet, asteroid));
        asteroid.setPosition(0, 0);
        assertTrue("No collision where it should be", collision.checkCollision(bullet, asteroid));
    }
    
    @Test
    public void testAsteroidSplit() {
        assertEquals("Asteroid not hit", 2, asteroid.getLife());
        asteroid.setPosition(0, 0);
        collision.process(gameData, world);
        assertEquals("Asteroid hit", 1, asteroid.getLife());
    }
}
