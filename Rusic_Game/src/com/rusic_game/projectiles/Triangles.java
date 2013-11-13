package com.rusic_game.projectiles;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import java.util.Random;


public class Triangles  {

    
   
	private Body body;
    private Fixture fixture;
  
    private float  randPosY;
    private float  yMax= 13,  yMin = 1;
    private float randSizeMax= 2, randSizeMin = .5f;
    private Vector2[] vertices;

   



    
   
   
   
  
    
   
    
    

    public Triangles( World world) {
    		vertices = new Vector2[3];
    		for(int i = 0; i < vertices.length; i++) {
    			vertices[i] = new Vector2();
    		}
    	    vertices[0].set(0, 0);

    	    vertices[1].set(1, 0);

    	    vertices[2].set(0, 1);
    	    PolygonShape shape = new PolygonShape();
    	    
           
    	    shape.set(vertices);
    		//randSize = randSizeMin + (float)(Math.random() * ((randSizeMax - randSizeMin) + 1.0));
    	    randPosY = yMin + (float)(Math.random() * ((yMax - yMin) + 1.0));
    	    
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyType.DynamicBody;
            bodyDef.position.set(20, randPosY);
            bodyDef.fixedRotation = true;
            

			

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.restitution = 0;
            fixtureDef.friction = .8f;
            fixtureDef.density = 1;
            fixtureDef.isSensor = false;
            
            body = world.createBody(bodyDef);
            body.setUserData("projectile");
            fixture = body.createFixture(fixtureDef);
            
           
            
           
            
            shape.dispose();
            
            
    }
     public void update() {
         body.setBullet(true);
         body.setLinearVelocity(-30, 0);
        // body.applyForceToCenter(0, 100);
         body.setGravityScale(0);
         body.applyTorque(.5f, true);
         
         
    	 // body.applyLinearImpulse(-75, 5, body.getWorldCenter().x, body.getWorldCenter().y);
  }
    

    public Body getBody() {
        return body;
}

public Fixture getFixture() {
        return fixture;
}
    
   }