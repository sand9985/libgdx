package MyLib;
/*
  Sprite繪圖器 ver 1.0
  作者:NULL(w100386435)
  個人小屋:http://home.gamer.com.tw/homeindex.php?owner=w100386435
  轉載請保留此標籤
*/
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Mesh.VertexDataType;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;


public class SpriteDrawer {
	private Mesh mesh; // 貼圖用網格

	private final float[] vertices;
	private int idx = 0; // 貼圖用
	private Texture lastTexture = null; // 最新的貼圖
	float invTexWidth = 0, invTexHeight = 0;// 貼圖座標
	private boolean drawing = false; // 是否正在繪圖

	private final Matrix4 transformMatrix = new Matrix4(); // 轉換矩陣
	private final Matrix4 projectionMatrix = new Matrix4();// 投影矩陣
	private final Matrix4 combinedMatrix = new Matrix4(); // 合併的矩陣

	private boolean blendingDisabled = false; // 是否混色
	// 混色參數
	private int blendSrcFunc = GL20.GL_SRC_ALPHA;
	private int blendDstFunc = GL20.GL_ONE_MINUS_SRC_ALPHA;

	private ShaderProgram shader; // 著色器
	private boolean ownsShader;

	public int maxSpritesInBatch = 0;

	public SpriteDrawer() {
		this(1000, null);
	}

	public SpriteDrawer(int size) {
		this(size, null);
	}

	public SpriteDrawer(int size, ShaderProgram defaultShader) {
		if (size > 5460)
			throw new IllegalArgumentException(
					"Can't have more than 5460 sprites per batch: " + size);

		mesh = new Mesh(VertexDataType.VertexArray, false, size * 4, size * 6,
				new VertexAttribute(Usage.Position, 2,
						ShaderProgram.POSITION_ATTRIBUTE), new VertexAttribute(
						Usage.ColorPacked, 4, ShaderProgram.COLOR_ATTRIBUTE),
				new VertexAttribute(Usage.TextureCoordinates, 2,
						ShaderProgram.TEXCOORD_ATTRIBUTE + "0"),
				new VertexAttribute(Usage.Generic, 1, "a_alpha"));

		projectionMatrix.setToOrtho2D(0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());

		vertices = new float[size * Sprite.SPRITE_SIZE];

		int len = size * 6;
		short[] indices = new short[len];
		short j = 0;
		for (int i = 0; i < len; i += 6, j += 4) {
			indices[i] = j;
			indices[i + 1] = (short) (j + 1);
			indices[i + 2] = (short) (j + 2);
			indices[i + 3] = (short) (j + 2);
			indices[i + 4] = (short) (j + 3);
			indices[i + 5] = j;
		}
		mesh.setIndices(indices);

		if (defaultShader == null) {
			shader = createDefaultShader();
			ownsShader = true;
		} else
			shader = defaultShader;
	}

	// 預設著色器
	static public ShaderProgram createDefaultShader() {
		String vertexShader = "attribute vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
				+ "attribute vec4 " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
				+ "attribute vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
				+ "attribute float a_alpha;"
				+ "uniform mat4 u_projTrans;\n" //
				+ "varying vec4 v_color;\n" //
				+ "varying vec2 v_texCoords;\n"
				+ "varying float v_alpha; "//
				+ "\n" //
				+ "void main()\n" //
				+ "{\n" //
				+ "   v_alpha=a_alpha;"
				+ "   v_color = " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
				+ "   v_color.a = v_color.a * (256.0/255.0);\n" //
				+ "   v_texCoords = " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
				+ "   gl_Position =  u_projTrans * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
				+ "}\n";
			String fragmentShader = "#ifdef GL_ES\n" //
				+ "#define LOWP lowp\n" //
				+ "precision mediump float;\n" //
				+ "#else\n" //
				+ "#define LOWP \n" //
				+ "#endif\n" //
				+ "varying LOWP vec4 v_color;\n" //
				+ "varying vec2 v_texCoords;\n" //
				+ "varying float v_alpha;"
				+ "uniform sampler2D u_texture;\n" // 
				+ "vec4 alpha_blend(vec4 c1,vec4 c2){\n"
				+"  float a1=c1.a;\n"
				+"  float a2=c2.a;\n"
				+"  vec4 color=vec4(0.0, 0.0, 0.0, 0.0);\n"
				+"  color.r= a1*c1.r+a2*(1-a1)*c2.r;\n "
				+"  color.g= a1*c1.g+a2*(1-a1)*c2.g;\n "
				+"  color.b= a1*c1.b+a2*(1-a1)*c2.b;\n "
				+"  color.a= a1+a2*(1-a1);\n"
				+"   return color;\n"
				+"  }\n"
				+ "void main()\n"//
				+ "{\n" //
				+ "  vec4 t_color=texture2D(u_texture, v_texCoords);\n"
				+ "  if(t_color.a>0)\n"
				+ "  gl_FragColor = alpha_blend(v_color,t_color); \n" 
				+ "  else\n"
				+ "  gl_FragColor=t_color;\n"
				+ "  gl_FragColor.a=gl_FragColor.a*v_alpha;"
				+ "}";

		ShaderProgram shader = new ShaderProgram(vertexShader, fragmentShader);
		if (shader.isCompiled() == false)
			throw new IllegalArgumentException("Error compiling shader: "
					+ shader.getLog());
		return shader;
	}

	// 開始繪圖
	public void begin() {
		if (drawing)
			throw new IllegalStateException(
					"SpriteDrawer.end must be called before begin.");
		Gdx.gl.glDepthMask(false);
		shader.begin();
		setupMatrices(); // 設定矩陣

		drawing = true;
	}

	// 結束繪圖
	public void end() {
		if (!drawing)
			throw new IllegalStateException(
					"SpriteDrawer.begin must be called before end.");
		if (idx > 0)
			flush();
		lastTexture = null;
		drawing = false;
		GL20 gl = Gdx.gl;
		gl.glDepthMask(true);
		if (isBlendingEnabled())
			gl.glDisable(GL20.GL_BLEND);
		shader.end();
	}

	// 繪製Sprite
	public void draw(Sprite sprite) {

		if (!drawing)
			throw new IllegalStateException(
					"SpriteDrawer.begin must be called before draw.");

		float[] vertices = this.vertices;

		if (sprite.texture != lastTexture)
			switchTexture(sprite.texture);
		else if (idx == vertices.length) 
			flush();
		
		
		final float worldOriginX = sprite.x ;
		final float worldOriginY = sprite.y ;
		float fx = -sprite.ox;
		float fy = -sprite.oy; 
		float fx2 = sprite.rect.width - sprite.ox;
		float fy2 = sprite.rect.height - sprite.oy;

		// scale
		if (sprite.scale_x != 1 || sprite.scale_y != 1) {
			fx *= sprite.scale_x;
			fy *= sprite.scale_y;
			fx2 *= sprite.scale_x;
			fy2 *= sprite.scale_y;
		}
		//貼圖座標
		float u = sprite.rect.x*invTexWidth;
		float v = ( sprite.rect.y +  sprite.rect.height) * invTexHeight;;
		float u2 =(sprite.rect.x +sprite.rect.width) * invTexWidth;
		float v2 = sprite.rect.y*invTexHeight;
		 //反轉x
			if (sprite.flip_x) {
				float tmp = u;
				u = u2;
				u2 = tmp;
			}
	        //反轉y
			if (sprite.flip_y) {
				float tmp = v;
				v = v2;
				v2 = tmp;
			}
		final float p1x = fx;
		final float p1y = fy;
		final float p2x = fx;
		final float p2y = fy2;
		final float p3x = fx2;
		final float p3y = fy2;
		final float p4x = fx2;
		final float p4y = fy;

		float x1;
		float y1;
		float x2;
		float y2;
		float x3;
		float y3;
		float x4;
		float y4;

		// rotate
		if (sprite.angle != 0) {
			final float cos = MathUtils.cosDeg(sprite.angle);
			final float sin = MathUtils.sinDeg(sprite.angle);

			x1 = cos * p1x - sin * p1y;
			y1 = sin * p1x + cos * p1y;

			x2 = cos * p2x - sin * p2y;
			y2 = sin * p2x + cos * p2y;

			x3 = cos * p3x - sin * p3y;
			y3 = sin * p3x + cos * p3y;

			x4 = x1 + (x3 - x2);
			y4 = y3 - (y2 - y1);
		} else {
			x1 = p1x;
			y1 = p1y;

			x2 = p2x;
			y2 = p2y;

			x3 = p3x;
			y3 = p3y;

			x4 = p4x;
			y4 = p4y;
		}

		
		x1 += worldOriginX;
		y1 += worldOriginY;
		x2 += worldOriginX;
		y2 += worldOriginY;
		x3 += worldOriginX;
		y3 += worldOriginY;
		x4 += worldOriginX;
		y4 += worldOriginY;
		
	
		float color=sprite.color.toFloatBits();
		
		int idx = this.idx;
		vertices[idx++] = x1;
		vertices[idx++] = y1;
		vertices[idx++] = color;
		vertices[idx++] = u;
		vertices[idx++] = v;
		vertices[idx++] = sprite.alpha;
		
		vertices[idx++] = x2;
		vertices[idx++] = y2;
		vertices[idx++] = color;
		vertices[idx++] = u;
		vertices[idx++] = v2;
		vertices[idx++] = sprite.alpha;
		
		vertices[idx++] = x3;
		vertices[idx++] = y3;
		vertices[idx++] = color;
		vertices[idx++] = u2;
		vertices[idx++] = v2;
		vertices[idx++] = sprite.alpha;
		
		vertices[idx++] = x4;
		vertices[idx++] = y4;
		vertices[idx++] = color;
		vertices[idx++] = u2;
		vertices[idx++] = v;
		vertices[idx++] = sprite.alpha;
		
		this.idx = idx;
		
		
	}

	// 刷新
	public void flush() {
		if (idx == 0)
			return;

		int spritesInBatch = idx / 20;
		if (spritesInBatch > maxSpritesInBatch)
			maxSpritesInBatch = spritesInBatch;
		int count = spritesInBatch * 6;

		lastTexture.bind();
		Mesh mesh = this.mesh;
		mesh.setVertices(vertices, 0, idx);
		mesh.getIndicesBuffer().position(0);
		mesh.getIndicesBuffer().limit(count);
		if (blendingDisabled) {
			Gdx.gl.glDisable(GL20.GL_BLEND);
		} else {
			Gdx.gl.glEnable(GL20.GL_BLEND);
			if (blendSrcFunc != -1)
				Gdx.gl.glBlendFunc(blendSrcFunc, blendDstFunc);
		}
		mesh.render(shader, GL20.GL_TRIANGLES, 0, count);
		idx = 0;
	}

	// 設定混色
	public void setBlendFunction(int srcFunc, int dstFunc) {
		if (blendSrcFunc == srcFunc && blendDstFunc == dstFunc)
			return;
		flush();
		blendSrcFunc = srcFunc;
		blendDstFunc = dstFunc;
	}

	// 釋放
	public void dispose() {
		mesh.dispose();
		if (ownsShader && shader != null)
			shader.dispose();
	}

	// 安裝矩陣
	private void setupMatrices() {
		combinedMatrix.set(projectionMatrix).mul(transformMatrix);
		shader.setUniformMatrix("u_projTrans", combinedMatrix);
		shader.setUniformi("u_texture", 0);

	}

	// 切換貼圖
	protected void switchTexture(Texture texture) {
		flush();
		lastTexture = texture;
		invTexWidth = 1.0f / texture.getWidth();
		invTexHeight = 1.0f / texture.getHeight();
	}

	// 是否可以混色
	public boolean isBlendingEnabled() {
		return !blendingDisabled;
	}

	// 是否正在繪圖
	public boolean isDrawing() {
		return drawing;
	}
}