package com.games.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

public class SnakeGame extends Canvas implements Runnable, KeyListener {

    public static final int WIDTH = 160;
    public static final int HEIGHT = (int)(WIDTH / 12D * 9);
    public static final int SCALE = 3;
    public static final int CELLSIZE = 8;
    public static final String NAME = "Snake V1.0";
    private JFrame frame;

    private Boolean running = false, restart = false;
    private int tickCount;

    //Create the image and pixel map usable as background in Render()
//    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
//    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

    Snake snake;
    Token token;

    public SnakeGame(){
        //Setting up the Frame for the game
        setMinimumSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
        setMaximumSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
        setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
        frame = new JFrame(NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(this,BorderLayout.CENTER);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        addKeyListener(this);
    }

    private void start(){
        running = true;
        new Thread(this).start();
    }

    private void stop(){
        running=false;
    }

    @Override
    public void run() {
        do{
            snake = new Snake(CELLSIZE);
            token = new Token(WIDTH*SCALE,HEIGHT*SCALE,CELLSIZE);
            restart = false;

            long lastTime = System.nanoTime();
            double nsPerTick = 1000000000D/60D;

            int ticks = 0;
            int frames = 0;

            long lastTimer = System.currentTimeMillis();
            double delta=0;
            while(running){
                long now = System.nanoTime();
                delta += (now-lastTime)/nsPerTick;
                lastTime = now;

                boolean shouldRender = true;
                while (delta >= 1){
                    ticks++;
                    tick(ticks);
                    delta-=1;
                    shouldRender = true;
                }



                //Render
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(shouldRender){
                    frames++;
                    render();
                }

                if(System.currentTimeMillis() - lastTimer >= 1000){
                    lastTimer += 1000;
                    //DEBUG
//                System.out.println("Ticks: " + ticks + ", FPS:" + frames);
                    frames = 0;
                    ticks = 0;
                }
            }

            //If the user wishes to restart, reset the running variable to true to that game loop resumes on restart.
            if(restart)
                running=true;


        } while (restart = true);
    }

    public void tick(int ticks){
        if(ticks%10==0 && snake.isAlive()){
            int j = token.checkEaten(snake.getHead());
            if(j>0){
                snake.incrementScore(j);
                snake.setGrow(true);
            }

            snake.move();

            if(checkCollision())
                snake.setAlive(false);
        }
        tickCount++;

    }

    public boolean checkCollision(){
        //Check that snake is within bounds of the game.
        Point head = snake.getHead();
        if(head.getX()<0 || head.getY()<0 || head.getX() > WIDTH*SCALE || head.getY() > HEIGHT*SCALE){
            snake.setAlive(false);
            snake.setMoving(false);
            return true;
        }

        //Check that the snake hasn't hit itself.
        if (snake.checkCollision())
            return true;

        //No collisions found so return game can continue.
        return false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (!snake.isAlive()){
            if(key == KeyEvent.VK_ENTER){
                running = false;
                restart = true;
            }
        } else {
            if(!snake.isMoving()){
                if (key== KeyEvent.VK_SPACE)
                    snake.setMoving(true);
            }
            if(key == KeyEvent.VK_UP){
                if(snake.getVerticalDir()==0){
                    snake.setVerticalDir(-1);
                    snake.setHorizontalDir(0);
                }
            } else
            if(key == KeyEvent.VK_DOWN){
                if(snake.getVerticalDir()==0){
                    snake.setVerticalDir(1);
                    snake.setHorizontalDir(0);
                }
            } else
            if(key == KeyEvent.VK_LEFT){
                if(snake.getHorizontalDir()==0){
                    snake.setHorizontalDir(-1);
                    snake.setVerticalDir(0);
                }
            } else
            if(key == KeyEvent.VK_RIGHT){
                if(snake.getHorizontalDir()==0){
                    snake.setHorizontalDir(1);
                    snake.setVerticalDir(0);
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if(bs==null){
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        //Draw base background
        g.setColor(Color.BLACK);
        g.fillRect(0,0,getWidth(),getHeight());

        //Option to draw image loaded in earlier instead of background
//        g.drawImage(image,0,0,getWidth(),getHeight(),null);
        if(snake.isAlive()){
            snake.render(g);
            token.render(g);
        } else {
            g.setColor(Color.RED);
            g.drawString("Game Over", WIDTH*SCALE/2,HEIGHT*SCALE/3);
            g.drawString("Score: " + snake.getScore(), WIDTH*SCALE/2, HEIGHT*SCALE/3 + 10);
            g.drawString("Press Enter to play again", WIDTH*SCALE/2, HEIGHT*SCALE/3 + 20);
        }

        g.dispose();
        bs.show();
    }



    public static void main(String[] args) {
        new SnakeGame().start();
    }
}
