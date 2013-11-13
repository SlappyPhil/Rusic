package com.rusic_game.projectiles;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import java.util.Random;

import javax.xml.bind.Marshaller.Listener;


public class Circles   {

  
	private Body body;
    private Fixture fixture;
    public float circleRadius; 
    private float  randPosY;
    private float  yMax= 13,  yMin = -13;
    private float randSizeMax= 2, randSizeMin = .5f, randSize;
    public int circleData = 3;
    
    

    public Circles( World world, float radius) {
    	   circleRadius = radius;
    	   
    	
    	    randSize = randSizeMin + (float)(Math.random() * ((randSizeMax - randSizeMin) + 1.0));
    	    randPosY = yMin + (float)(Math.random() * ((yMax - yMin) + 1.0));
    	    
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyType.DynamicBody;
            bodyDef.position.set(25f, randPosY);
            bodyDef.fixedRotation = true;
            

            CircleShape circleShape = new CircleShape();
            
			circleShape.setRadius(circleRadius);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = circleShape;
            fixtureDef.restitution = 0;
            fixtureDef.friction = .8f;
            fixtureDef.density = 1;
            fixtureDef.isSensor = false;
            
            
			
            body = world.createBody(bodyDef);
            body.setUserData("projectile");
            fixture = body.createFixture(fixtureDef);
            
           
            
           
            
            circleShape.dispose();
            
            
    }
     public void update() {
         body.setBullet(true);
         body.setLinearVelocity(-30, 0);
        // body.applyForceToCenter(0, 100);
         body.setGravityScale(0);
         
         
    	 // body.applyLinearImpulse(-75, 5, body.getWorldCenter().x, body.getWorldCenter().y);
  }
    

    public Body getBody() {
        return body;
}

public Fixture getFixture() {
        return fixture;
}

    
   }