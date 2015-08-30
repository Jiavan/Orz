package com.jiavan.libgdx.orz;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;
import com.jiavan.libgdx.orz.bodydata.BoomData;
import com.jiavan.libgdx.orz.bodydata.PlayerData;

public class BodyCollisionListener implements ContactListener {
	public Array<Body> bulletDestroyList;
	
	public BodyCollisionListener() {
		bulletDestroyList = new Array<Body>();
	}

	@Override
	public void beginContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();
		
		Body bodyA = fixtureA.getBody();
		Body bodyB = fixtureB.getBody();
		
		if(bodyA.getUserData() instanceof PlayerData 
				&& bodyB.getUserData() instanceof BoomData) {
			PlayerData.health--;
		}else if(bodyB.getUserData() instanceof PlayerData 
				&& bodyA.getUserData() instanceof BoomData) {
			PlayerData.health--;
		}
 	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

}
