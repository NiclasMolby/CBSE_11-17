/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 *
 * @author niclasmolby
 */
public interface IAsteroidCollisionService {
    void astroidBulletCollision(GameData gameData, World world);
    void astroidShipCollision(World world);
}
