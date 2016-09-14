package pt.pxinxas.graviball.game.entity;

import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.JumpModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.IModifier;

import pt.pxinxas.graviball.game.conf.Constants;
import pt.pxinxas.graviball.game.entity.enumerator.PlayerStatus;
import pt.pxinxas.graviball.game.entity.enumerator.RoundStatus;
import pt.pxinxas.graviball.game.holder.EntityHolder;
import pt.pxinxas.graviball.game.holder.ScoreHolder;
import pt.pxinxas.graviball.game.holder.TexturesHolder;
import pt.pxinxas.graviball.game.scene.BaseRoundScene;
import pt.pxinxas.graviball.game.scores.ScoreText;

public class FallingEntity extends Sprite {
	private BaseRoundScene currentRoundScene;
	private Integer currentRoundPosition = null;
	protected PhysicsHandler entityPhysicsHandler;
	protected PlayerStatus status;
	private double health;
	private Sprite chute;
	private String name;
	private Sprite bubble;
	private Text position;
	private ParallelEntityModifier parallelEntityModifier = new ParallelEntityModifier(new ScaleModifier(0.3f, 0.1f, 3f), new MoveYModifier(0.3f, 20,
			-90));

	public FallingEntity(float pX, float pY, ITextureRegion playerTexture) {
		super(pX, pY, playerTexture, EntityHolder.getEngine().getVertexBufferObjectManager());
		this.entityPhysicsHandler = new PhysicsHandler(this);
		this.registerUpdateHandler(this.entityPhysicsHandler);
		parallelEntityModifier.setAutoUnregisterWhenFinished(true);
		this.chute = new Sprite(this.getWidth() / 4, -20, TexturesHolder.getChute(), EntityHolder.getEngine().getVertexBufferObjectManager());
		this.chute.setScale(2f);
		this.chute.setVisible(false);
		this.chute.setZIndex(-1);
		
		this.bubble = new Sprite(-24, -25, TexturesHolder.getBubble(), EntityHolder.getEngine().getVertexBufferObjectManager());
		this.bubble.setScale(0.9f);
		this.bubble.setVisible(false);
		
		this.attachChild(this.bubble);
		this.attachChild(this.chute);
		this.sortChildren();
	}

	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		if (this.status == PlayerStatus.FALSE_START && this.getEntityPhysicsHandler().getVelocityY() == Constants.NO_VELOTICY) {
			this.getEntityPhysicsHandler().setAccelerationY(Constants.FALL_ACCELERATION);
			super.onManagedUpdate(pSecondsElapsed);
			return;
		}
		if (this.status == PlayerStatus.FALSE_START && (this.getEntityPhysicsHandler().getVelocityY() > Constants.FALL_MAX_VELOCITY || this.getEntityPhysicsHandler().getVelocityY() < 0)) {
			this.bubble.setVisible(true);
			this.getEntityPhysicsHandler().setVelocityY(-Constants.SHUTE_MAX_VELOCITY / 4);
			this.getEntityPhysicsHandler().setAccelerationY(Constants.NO_ACCELERATION);
			super.onManagedUpdate(pSecondsElapsed);
			return;
		}
		if (this.status == PlayerStatus.STOPPED && this.getCurrentRoundScene().getStatus() != RoundStatus.FINISHED) {
			actionOnStart();
			parallelEntityModifier.reset();
			this.bubble.setVisible(false);
			this.getEntityPhysicsHandler().setAccelerationY(Constants.NO_ACCELERATION);
			this.getEntityPhysicsHandler().setVelocityY(Constants.NO_VELOTICY);
			this.setCurrentRoundPosition(null);
			if (this.position != null) {
				this.detachChild(this.position);
			}
			super.onManagedUpdate(pSecondsElapsed);
			return;
		}

		if (this.status == PlayerStatus.ACCEL && this.getEntityPhysicsHandler().getVelocityY() > Constants.FALL_MAX_VELOCITY) {
			this.status = PlayerStatus.MOVING;
			this.getEntityPhysicsHandler().setAccelerationY(Constants.NO_ACCELERATION);
			this.getEntityPhysicsHandler().setVelocityY(Constants.FALL_MAX_VELOCITY);
			super.onManagedUpdate(pSecondsElapsed);
			return;
		} else if (this.status == PlayerStatus.ACCEL && this.getEntityPhysicsHandler().getVelocityY() < Constants.FALL_MAX_VELOCITY) {
			this.getEntityPhysicsHandler().setAccelerationY(Constants.FALL_ACCELERATION);
			super.onManagedUpdate(pSecondsElapsed);
			return;
		}

		if (this.status == PlayerStatus.SLOWING && this.getEntityPhysicsHandler().getVelocityY() > Constants.SHUTE_MAX_VELOCITY
				&& this.getEntityPhysicsHandler().getAccelerationY() == Constants.NO_ACCELERATION) {
			this.getEntityPhysicsHandler().setAccelerationY(Constants.FALL_DECCELERATION);
			this.chute.setVisible(true);

			this.chute.registerEntityModifier(parallelEntityModifier);
			super.onManagedUpdate(pSecondsElapsed);
			return;
		} else if (this.status == PlayerStatus.SLOWING && this.getEntityPhysicsHandler().getVelocityY() < Constants.SHUTE_MAX_VELOCITY) {
			this.getEntityPhysicsHandler().setAccelerationY(Constants.NO_ACCELERATION);
			this.getEntityPhysicsHandler().setVelocityY(Constants.SHUTE_MAX_VELOCITY);
			super.onManagedUpdate(pSecondsElapsed);
			return;
		}

		if (this.getY() > Constants.LEAGUE_DEPTH - 10 - this.getHeight() && this.status != PlayerStatus.FINISHED && this.status != PlayerStatus.DEAD
				&& this.status != PlayerStatus.BOUNCING) {
			updateEntityHealthOnFall();
			this.getEntityPhysicsHandler().setVelocityY(Constants.NO_VELOTICY);
			this.getEntityPhysicsHandler().setAccelerationY(Constants.NO_ACCELERATION);
			this.setPosition(this.getX(), Constants.LEAGUE_DEPTH - 10 - this.getHeight());
			this.chute.setVisible(false);
			if (this.getHealth() <= 0 && this == EntityHolder.getPlayer()) {
				this.setStatus(PlayerStatus.DEAD);
				this.setCurrentRoundPosition(4);
				this.setHealth(0);
			} else {
				this.setStatus(PlayerStatus.BOUNCING);

				actionOnFall();

				ParallelEntityModifier loopEntityModifier = new ParallelEntityModifier(new IEntityModifier.IEntityModifierListener() {
					@Override
					public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
					}

					@Override
					public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
						((FallingEntity) pItem).setStatus(PlayerStatus.FINISHED);
					}
				}, new JumpModifier(0.6f, this.getX(), this.getX(), this.getY(), this.getY(), 30), new LoopEntityModifier(new SequenceEntityModifier(
						new ScaleModifier(0.20f, this.getScaleX(), this.getScaleX()* 1.15f, this.getScaleY(), this.getScaleY() * 0.85f), new ScaleModifier(
								0.20f, this.getScaleX()*1.15f, this.getScaleX(), this.getScaleY() * 0.85f, this.getScaleY())), 2));
				loopEntityModifier.setAutoUnregisterWhenFinished(true);
				this.registerEntityModifier(loopEntityModifier);

				this.setCurrentRoundPosition(ScoreHolder.arrived());
				showCurrentPlace();
				if (this.getCurrentRoundPosition() == 1) {
					this.position.setColor(Color.BLUE);
				} else if (this.getCurrentRoundPosition() == 4) {
					this.position.setColor(Color.RED);
				}

			}

			super.onManagedUpdate(pSecondsElapsed);
			return;
		}

		super.onManagedUpdate(pSecondsElapsed);
	}

	protected void actionOnStart() {

	}

	protected void actionOnFall() {
	}

	private void updateEntityHealthOnFall() {
		this.setHealth(this.getHealth() - ((-300 + this.getEntityPhysicsHandler().getVelocityY()) / 7));
	}

	private void showCurrentPlace() {
		position = ScoreText.getInstance().getTextFromNumber(this.getCurrentRoundPosition());
		position.setPosition(this.getWidth() / 2 - position.getWidth() / 2, 0 - position.getHeight() - 40);
		this.attachChild(position);
	}

	public PhysicsHandler getEntityPhysicsHandler() {
		return entityPhysicsHandler;
	}

	public void setEntityPhysicsHandler(PhysicsHandler entityPhysicsHandler) {
		this.entityPhysicsHandler = entityPhysicsHandler;
	}

	public PlayerStatus getStatus() {
		return status;
	}

	public void setStatus(PlayerStatus status) {
		this.status = status;
	}

	public Integer getCurrentRoundPosition() {
		return currentRoundPosition;
	}

	public void setCurrentRoundPosition(Integer currentLeaguePosition) {
		this.currentRoundPosition = currentLeaguePosition;
	}

	public BaseRoundScene getCurrentRoundScene() {
		return currentRoundScene;
	}

	public void setCurrentRoundScene(BaseRoundScene currentRoundScene) {
		this.currentRoundScene = currentRoundScene;
	}

	public double getHealth() {
		return health;
	}

	public void setHealth(double health) {
		this.health = health;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
