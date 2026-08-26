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

public class DMapScreen implements Screen {

    private MyGame game;

    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;
    private Viewport viewport;

    private float playerX = 100f;
    private float playerY = 100f;

    // TMXマップの全体サイズ (30タイル * 32px = 960, 20タイル * 32px = 640)
    private static final float V_WIDTH = 960f;
    private static final float V_HEIGHT = 640f;

    private Array<RectangleMapObject> warpPoints;
    private RectangleMapObject lastOverlapped;
    private static final float PLAYER_SIZE = 32f;

    public DMapScreen(MyGame game, String mapID) {
        this.game = game;

        camera = new OrthographicCamera();
        viewport = new FitViewport(V_WIDTH, V_HEIGHT, camera);

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

        playerX = spawnX;
        playerY = spawnY;
        lastOverlapped = null;
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

    @Override
    public void show() {
        // 画面が表示された瞬間に、現在のウィンドウサイズでViewportを強制更新する
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    }
    @Override
public void render(float delta) {
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

    mapRenderer.setView(camera);
    mapRenderer.render();
}

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        if (map != null) map.dispose();
        if (mapRenderer != null) mapRenderer.dispose();
    }
}