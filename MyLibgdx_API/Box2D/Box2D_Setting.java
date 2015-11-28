package Box2D;

import com.badlogic.gdx.physics.box2d.World;

public class Box2D_Setting {

    public static World p_world; //point to World
	public static float PPM=100.0F; 
	
	/*
	 public static void create_ContactListener(){
		 
		 
		 p_world.setContactListener(new ContactListener() {

	            @Override
	            public void beginContact(Contact contact) {
	            	
	                Fixture fixtureA = contact.getFixtureA();
	                Fixture fixtureB = contact.getFixtureB();
	                Gdx.app.log("beginContact", "between " + fixtureA.toString() + " and " + fixtureB.toString());
	            }

	            @Override
	            public void endContact(Contact contact) {
	                Fixture fixtureA = contact.getFixtureA();
	                Fixture fixtureB = contact.getFixtureB();
	                Gdx.app.log("endContact", "between " + fixtureA.toString() + " and " + fixtureB.toString());
	            }

	            @Override
	            public void preSolve(Contact contact, Manifold oldManifold) {
	            }

	            @Override
	            public void postSolve(Contact contact, ContactImpulse impulse) {
	            	
	            }

	        });
	 }
	
	*/
}
