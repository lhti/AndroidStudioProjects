package com.cursoandroid.lhgyn.flepbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture[] passaros;
	private Texture fundo;
	private Texture canoTopo;
	private Texture canoBaixo;
	private Texture gameOver;
	private Random numeroRandomico;
	private BitmapFont fonte;
	private BitmapFont mensagem;
	private Circle passaroCirculo;
	private Rectangle canoTopoRetangulo;
	private Rectangle canoBaixoRetangulo;
	//private ShapeRenderer shape;

	//Atributos de configuração
	private float larguraDispositivo;
	private float alturaDispositivo;
	private int estadoJogo=0; // 0 -> Jogo não iniciado  1 -> Jogo iniciado 2 -> Tela game over
	private int pontuacao=0;
	private boolean marcouPonto=false;

	private float variacao=0;
	private float velocidadeQueda=0;
	private float posicaoInicialVertical=0;
	private float posicaoMovimentoCanoHorizontal;
	private float espacoEntreCanos;
	private float deltaTime;
	private float alturaEntreCanosRandomica;

	//Câmera
	private OrthographicCamera camera;
	private Viewport viewport;
	private final float VIRTUAL_WIDTH = 768;
	private final float VIRTUAL_HEIGHT = 1024;


	@Override
	public void create () {

		batch = new SpriteBatch();
		numeroRandomico = new Random();
		passaroCirculo = new Circle();
		/*canoTopoRetangulo = new Rectangle();
		canoTopoRetangulo = new Rectangle();
		shape = new ShapeRenderer();*/
		fonte = new BitmapFont();
		fonte.setColor(Color.WHITE);
		fonte.getData().setScale(6);

		mensagem = new BitmapFont();
		mensagem.setColor(Color.WHITE);
		mensagem.getData().setScale(4);

		passaros = new Texture[3];
		passaros[0] = new Texture("passaro1.png");
		passaros[1] = new Texture("passaro2.png");
		passaros[2] = new Texture("passaro3.png");

		fundo = new Texture("fundo.png");

		canoBaixo = new Texture("cano_baixo.png");
		canoTopo = new Texture("cano_topo.png");

		gameOver = new Texture("game_over.png");

		/****************************************************
		 *  Configuração da câmera
		 */
		camera = new OrthographicCamera();
		camera.position.set(VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2, 0);
		viewport = new StretchViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);

		larguraDispositivo = VIRTUAL_WIDTH;
		alturaDispositivo = VIRTUAL_HEIGHT;
		posicaoInicialVertical = alturaDispositivo / 2;
		posicaoMovimentoCanoHorizontal = larguraDispositivo;
		espacoEntreCanos = 300;

	}

	@Override
	public void render () {

		camera.update();

		//Limpar frames anteriores
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		//Movimento das asas
		deltaTime = Gdx.graphics.getDeltaTime();
		variacao += deltaTime * 10;
		if (variacao > 2)
			variacao = 0;

		if ( estadoJogo == 0 ) {

			if (Gdx.input.justTouched()) {
				estadoJogo = 1;
			}

		} else {

			velocidadeQueda++;
			if (posicaoInicialVertical > 0 || velocidadeQueda < 0)
				posicaoInicialVertical = posicaoInicialVertical - (velocidadeQueda / 2);

			if ( estadoJogo == 1 ) {

				if (Gdx.input.justTouched())
					velocidadeQueda = -15;

				posicaoMovimentoCanoHorizontal -= deltaTime * 200;

				// Verifica se o cano saiu inteiramente da tela
				if (posicaoMovimentoCanoHorizontal < -canoTopo.getWidth()) {
					posicaoMovimentoCanoHorizontal = larguraDispositivo;
					alturaEntreCanosRandomica = numeroRandomico.nextInt(400) - 200;
					marcouPonto = false;
				}

				// Verifica pontuação
				if (posicaoMovimentoCanoHorizontal < 120)
					if ( marcouPonto == false ) {
						pontuacao++;
						marcouPonto = true;
					}
			} else {//Game over

				if (Gdx.input.justTouched()) {
					estadoJogo = 0;
					pontuacao = 0;
					velocidadeQueda = 0;
					posicaoInicialVertical = alturaDispositivo / 2;
					posicaoMovimentoCanoHorizontal = larguraDispositivo;
				}

			}

		}

		// Configurar dados de projeção da câmera
		batch.setProjectionMatrix( camera.combined );

		batch.begin();

		batch.draw(fundo, 0 , 0, larguraDispositivo, alturaDispositivo);
		batch.draw(canoTopo, posicaoMovimentoCanoHorizontal, alturaDispositivo / 2 + espacoEntreCanos / 2 + alturaEntreCanosRandomica);
		batch.draw(canoBaixo, posicaoMovimentoCanoHorizontal, alturaDispositivo / 2 - canoBaixo.getHeight() - espacoEntreCanos / 2 + alturaEntreCanosRandomica);
		batch.draw(passaros[(int) variacao],120, posicaoInicialVertical);
		fonte.draw(batch, String.valueOf(pontuacao), larguraDispositivo / 2, alturaDispositivo - 50);

		if ( estadoJogo == 2 ) {
			batch.draw(gameOver, larguraDispositivo / 2 - gameOver.getWidth() / 2, alturaDispositivo / 2);
			mensagem.draw(batch,"Toque para reiniciar!", larguraDispositivo / 2 - 260, alturaDispositivo / 2 - gameOver.getHeight() / 2 );
		}

		batch.end();

		passaroCirculo.set(120 + passaros[0].getWidth() / 2,posicaoInicialVertical + passaros[0].getHeight() / 2, passaros[0].getWidth() / 2);
		canoBaixoRetangulo = new Rectangle(posicaoMovimentoCanoHorizontal, alturaDispositivo / 2 - canoBaixo.getHeight() - espacoEntreCanos / 2 + alturaEntreCanosRandomica,
				canoBaixo.getWidth(), canoBaixo.getHeight());
		canoTopoRetangulo = new Rectangle(posicaoMovimentoCanoHorizontal, alturaDispositivo / 2 + espacoEntreCanos / 2 + alturaEntreCanosRandomica,
				canoTopo.getWidth(), canoTopo.getHeight());

		//Desenhar formas
		/*shape.begin(ShapeRenderer.ShapeType.Filled);
		shape.circle(passaroCirculo.x, passaroCirculo.y, passaroCirculo.radius);
		shape.rect(canoBaixoRetangulo.x,canoBaixoRetangulo.y,canoBaixoRetangulo.width, canoBaixoRetangulo.height);
		shape.rect(canoTopoRetangulo.x, canoTopoRetangulo.y, canoTopoRetangulo.width, canoTopoRetangulo.height);
		shape.setColor(Color.RED);
		shape.end();*/

		//Verifica colisoes
		if (Intersector.overlaps(passaroCirculo, canoTopoRetangulo) || Intersector.overlaps(passaroCirculo, canoBaixoRetangulo)
				|| posicaoInicialVertical <= 0 || posicaoInicialVertical >= alturaDispositivo)
			estadoJogo = 2;

	}
	
	@Override
	public void dispose () {
		/*
		batch.dispose();
		img.dispose();
		*/
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

}
