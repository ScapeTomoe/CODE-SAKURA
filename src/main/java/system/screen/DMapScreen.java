package system.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import system.MyGame;
import system.object.Player;

public class DMapScreen implements Screen {

	private MyGame game;

	private TiledMap map;
	private SpriteBatch batch;
	private OrthogonalTiledMapRenderer mapRenderer;
	private OrthographicCamera camera;
	private Viewport viewport;
	Player player;
	// TMXマップの全体サイズ (30タイル * 32px = 960, 20タイル * 32px = 640)
	private static final float V_WIDTH = 960f;
	private static final float V_HEIGHT = 640f;

	private Array<RectangleMapObject> warpPoints;
	private Array<RectangleMapObject> collisionObjects;
	private float collisionOffsetX = 0f;
	private float collisionOffsetY = 0f;
	private RectangleMapObject lastOverlapped;
	private static final float PLAYER_SIZE = 50f;
	Texture scarlet;
	Texture[] list_scarlet;

	// ★デバッグ描画用
	private ShapeRenderer shapeRenderer;
	private boolean debugMode = false;

	public DMapScreen(MyGame game, String mapID) {
		this.game = game;
		this.player = new Player(100f, 100f);
		this.batch = new SpriteBatch();
		this.scarlet = new Texture(Gdx.files.internal("image/character/scarlet_field/face_closeup.png"));
		camera = new OrthographicCamera();
		viewport = new FitViewport(V_WIDTH, V_HEIGHT, camera);
		list_scarlet = new Texture[]{
				new Texture(Gdx.files.internal("image/character/scarlet_field/face_closeup.png")),
				new Texture(Gdx.files.internal("image/character/scarlet_field/back_view.png")),
				new Texture(Gdx.files.internal("image/character/scarlet_field/walk_front1.png")),
				new Texture(Gdx.files.internal("image/character/scarlet_field/walk_front2.png"))
		};

		shapeRenderer = new ShapeRenderer(); // ★追加

		loadMap("data/map/tmx/demo.tmx", 100f, 100f);
	}

	private void loadMap(String tmxPath, float spawnX, float spawnY) {
		if (map != null) {
			map.dispose();
		}
		map = new TmxMapLoader().load(tmxPath);

		if (mapRenderer == null) {
			mapRenderer = new OrthogonalTiledMapRenderer(map);
		} else {
			mapRenderer.setMap(map);
		}

		loadWarpPoints();
		loadCollisionObjects();

		player.setPlayerX(spawnX);
		player.setPlayerY(spawnY);
		lastOverlapped = null;
	}

	private void checkWarp() {
		Rectangle playerRect = new Rectangle(player.getPlayerX(), player.getPlayerY(), PLAYER_SIZE, 20f);

		RectangleMapObject currentOverlap = null;
		for (RectangleMapObject obj : warpPoints) {
			if (obj.getRectangle().overlaps(playerRect)) {
				currentOverlap = obj;
				break;
			}
		}

		if (currentOverlap != null && currentOverlap != lastOverlapped) {
			String targetMap = currentOverlap.getProperties().get("targetMap", String.class);

			Object xObj = currentOverlap.getProperties().get("targetX");
			Object yObj = currentOverlap.getProperties().get("targetY");
			float targetX = xObj instanceof Number ? ((Number) xObj).floatValue() : Float.parseFloat(xObj.toString());
			float targetY = yObj instanceof Number ? ((Number) yObj).floatValue() : Float.parseFloat(yObj.toString());

			loadMap(targetMap, targetX, targetY);
			return;
		}

		lastOverlapped = currentOverlap;
	}

	private void loadWarpPoints() {
		MapLayer warpLayer = map.getLayers().get("warp");
		if (warpLayer != null) {
			warpPoints = warpLayer.getObjects().getByType(RectangleMapObject.class);
		} else {
			warpPoints = new Array<>();
		}
	}

	private void loadCollisionObjects() {
		MapLayer collisionLayer = map.getLayers().get("collision");
		if (collisionLayer != null) {
			collisionObjects = collisionLayer.getObjects().getByType(RectangleMapObject.class);
			collisionOffsetX = collisionLayer.getOffsetX();
			collisionOffsetY = collisionLayer.getOffsetY();
		} else {
			collisionObjects = new Array<>();
			collisionOffsetX = 0f;
			collisionOffsetY = 0f;
		}
	}

	/**
	 * 指定した座標にプレイヤーを置いたとき、collisionレイヤーの矩形と重なるかを判定する
	 */
	private boolean isColliding(float x, float y) {
		Rectangle playerRect = new Rectangle(x, y, PLAYER_SIZE, 20f);
		for (RectangleMapObject obj : collisionObjects) {
			Rectangle r = obj.getRectangle();
			Rectangle adjusted = new Rectangle(
					r.x + collisionOffsetX,
					r.y + collisionOffsetY,
					r.width,
					r.height
			);
			if (adjusted.overlaps(playerRect)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void show() {
		// 画面が表示された瞬間に、現在のウィンドウサイズでViewportを強制更新する
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
	}

	@Override
	public void render(float delta) {
		// ★ESC+Mでデバッグモードのトグル
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && Gdx.input.isKeyJustPressed(Input.Keys.M)) {
			debugMode = !debugMode;
		}

		//キー入力
		//まずそのものダッシュありかなしか
		float speed = 5f;
		if (Gdx.input.isKeyPressed(Input.Keys.X)) {
			speed = 10f;
		}
		float deltaX = 0, deltaY = 0;
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			scarlet = list_scarlet[1];
			deltaY += speed;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			scarlet = list_scarlet[0];
			deltaY -= speed;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			scarlet = list_scarlet[3];
			deltaX -= speed;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			scarlet = list_scarlet[2];
			deltaX += speed;
		}

		// X軸・Y軸を別々にチェックして、壁沿いにスライドできるようにする
		float newX = player.getPlayerX() + deltaX;
		float newY = player.getPlayerY() + deltaY;

		if (isColliding(newX, player.getPlayerY())) {
			deltaX = 0;
		}
		if (isColliding(player.getPlayerX(), newY)) {
			deltaY = 0;
		}

		player.move(deltaX, deltaY);

		// 初回表示タイミングのズレ（起動直後の黒画面）を防ぐため、毎フレーム実サイズへ同期する
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		checkWarp();

		// 黒帯（左側の余白）のピクセル幅を取得し、ワールド座標系に変換
		float leftGutterPx = viewport.getLeftGutterWidth();
		float worldPerPixel = V_WIDTH / viewport.getScreenWidth();
		float offsetX = leftGutterPx * worldPerPixel;

		// カメラの位置を中心(V_WIDTH/2)から左の余白分だけ右に移動させることで、
		// マップの左端(x=0)が画面の左端にぴったり合います
		camera.position.set(V_WIDTH / 2f, V_HEIGHT / 2f, 0);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		mapRenderer.setView(camera);
		mapRenderer.render();
		//スカーレット
		batch.begin();
		batch.draw(scarlet, player.getPlayerX(), player.getPlayerY(), 50f, 70f);
		batch.end();

		// ★デバッグ描画:当たり判定を赤枠、warpポイントを緑枠、プレイヤーの当たり判定を黄枠で表示
		if (debugMode) {
			shapeRenderer.setProjectionMatrix(camera.combined);
			shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

			shapeRenderer.setColor(1, 0, 0, 1); // 赤:collision
			for (RectangleMapObject obj : collisionObjects) {
				Rectangle r = obj.getRectangle();
				shapeRenderer.rect(r.x + collisionOffsetX, r.y + collisionOffsetY, r.width, r.height);
			}

			shapeRenderer.setColor(0, 1, 0, 1); // 緑:warp
			for (RectangleMapObject obj : warpPoints) {
				Rectangle r = obj.getRectangle();
				shapeRenderer.rect(r.x, r.y, r.width, r.height);
			}

			shapeRenderer.setColor(1, 1, 0, 1); // 黄:プレイヤーの当たり判定
			shapeRenderer.rect(player.getPlayerX(), player.getPlayerY(), PLAYER_SIZE, 20f);

			shapeRenderer.end();
		}
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
		if (map != null)
			map.dispose();
		if (mapRenderer != null)
			mapRenderer.dispose();
		if (shapeRenderer != null)
			shapeRenderer.dispose(); // ★追加
	}
}