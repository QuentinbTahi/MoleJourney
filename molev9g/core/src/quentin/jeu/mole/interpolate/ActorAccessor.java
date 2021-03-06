package quentin.jeu.mole.interpolate;


import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorAccessor implements TweenAccessor<Actor> {

	public static final int XY = 0, RGB = 1, ALPHA = 2, ROT=3, SIZE =4;

	@Override
	public int getValues(Actor target, int tweenType, float[] returnValues) {
		switch(tweenType) {
		case XY:
			returnValues[0] = target.getX();
			returnValues[1] = target.getY();
			return 2;
		case RGB:
			returnValues[0] = target.getColor().r;
			returnValues[1] = target.getColor().g;
			returnValues[2] = target.getColor().b;
			return 3;
		case ALPHA:
			returnValues[0] = target.getColor().a;
			return 1;
		case ROT:
			returnValues[0] = target.getRotation();
			return 1;
		case SIZE:
			returnValues[0] = target.getWidth();
			returnValues[1] = target.getHeight();
			return 2;
		default:
			assert false;
			return -1;
		}
	}

	@Override
	public void setValues(Actor target, int tweenType, float[] newValues) {
		switch(tweenType) {
		case XY:
			target.setX(newValues[0]);
			target.setY(newValues[1]);
			break;
		case RGB:
			target.setColor(newValues[0], newValues[1], newValues[2], target.getColor().a);
			break;
		case ALPHA:
			target.setColor(target.getColor().r, target.getColor().g, target.getColor().b, newValues[0]);
			break;
		case ROT:
			target.setRotation(newValues[0]);
			break;
		case SIZE:
			target.setWidth(newValues[0]);
			target.setHeight(newValues[1]);
		default:
			assert false;
		}
	}

}