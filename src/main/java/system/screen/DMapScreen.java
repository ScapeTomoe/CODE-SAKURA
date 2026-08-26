package system.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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

public class DMapScreen implements Screen{
	
	private MyGame game;
	
	 private TiledMap map;
	 private OrthogonalTiledMapRenderer mapRenderer;
	 private OrthographicCamera camera;
	 private Viewport viewport;
	 
	 private float playerX = 100f;
	 private float playerY = 100f;
	 
	private Array<RectangleMapObject> warpPoints;
	
	private RectangleMapObject lastOverlapped;
	private static final float PLAYER_SIZE = 32f;
	public DMapScreen(MyGame game,String mapID) {
		camera = new OrthographicCamera();
        viewport = new FitViewport(960, 720, camera); // マップ表示領域を960x720と仮定
        loadMap("resources/data/map/demo.tmx",0f,0f);
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

        playerX = spawnX;
        playerY = spawnY;
        lastOverlapped = null; // マップ切替直後は重なり判定をリセット

        camera.position.set(playerX, playerY, 0);
        camera.update();
    }
	
	private void checkWarp() {
        Rectangle playerRect = new Rectangle(playerX, playerY, PLAYER_SIZE, PLAYER_SIZE);

        RectangleMapObject currentOverlap = null;
        for (RectangleMapObject obj : warpPoints) {
            if (obj.getRectangle().overlaps(playerRect)) {
                currentOverlap = obj;
                break;
            }
        }

        // 「前フレームは重なっていなかったが、今フレームで重なった」タイミングのみ発火
        if (currentOverlap != null && currentOverlap != lastOverlapped) {
            String targetMap = currentOverlap.getProperties().get("targetMap", String.class);
            float targetX = currentOverlap.getProperties().get("targetX", Float.class);
            float targetY = currentOverlap.getProperties().get("targetY", Float.class);
            loadMap(targetMap, targetX, targetY);
            return; // loadMap内でlastOverlappedはnullにリセット済み
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
	
	@Override
	public void show() {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // ここでプレイヤー移動入力処理を行い playerX, playerY を更新する想定
        // handleInput(delta);

        checkWarp();

        camera.position.set(playerX, playerY, 0);
        camera.update();
        mapRenderer.setView(camera);
        mapRenderer.render();

	}

	@Override
	public void resize(int width, int height) {
		// TODO 自動生成されたメソッド・スタブ
		viewport.update(width, height);
	}

	@Override
	public void pause() {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void resume() {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void hide() {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void dispose() {
		map.dispose();
        mapRenderer.dispose();
	}

}
