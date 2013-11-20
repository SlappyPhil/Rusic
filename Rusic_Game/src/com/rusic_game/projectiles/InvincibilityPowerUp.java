package com.rusic_game.projectiles;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.rusic_game.models.helper.CustomUserData;

public class InvincibilityPowerUp {

	private Body body;
    private Fixture fixture;
    public float powerUpRadius; 
    private float  randPosY;
    private float  yMax= 13,  yMin = -13;

    public InvincibilityPowerUp( World world, float radius) {
    	   powerUpRadius = radius;
    	    
   	       randPosY = yMin + (float)(Math.random() * ((yMax - yMin) + 1.0));
   	    
           BodyDef bodyDef = new BodyDef();
           bodyDef.type = BodyType.DynamicBody;
           bodyDef.position.set(25f, randPosY);
           bodyDef.fixedRotation = true;
            
           CircleShape circleShape = new CircleShape();
            
			circleShape.setRadius(powerUpRadius);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = circleShape;
            fixtureDef.restitution = 0;
            fixtureDef.friction = .8f;
            fixtureDef.density = 1;
            fixtureDef.isSensor = false;
            
            
			
            body = world.createBody(bodyDef);
            fixture = body.createFixture(fixtureDef);
            
            body.setUserData(new CustomUserData("iPowerUp"));
            
            circleShape.dispose();
            
            
    }
     public void update() {
         body.setBullet(true);
         body.setLinearVelocity(-70, 0);
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
