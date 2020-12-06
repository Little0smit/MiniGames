package com.games.snake;

import java.awt.*;
import java.util.ArrayList;

public class Snake {
    public static int CELLSIZE = 8;
    public static final int STARTX = 160, STARTY = 160 , STARTLENGTH = 10;

    private ArrayList<com.games.snake.Point> points;

    private int verticalDir, horizontalDir;
    private int score, length;
    private Boolean moving, grow, alive;

    
    
    public Snake(int CELLSIZE) {
        this.CELLSIZE = CELLSIZE;
        points = new ArrayList<>();
        for (int i = 0; i < STARTLENGTH; i++) {
            points.add(new com.games.snake.Point(STARTX - (i* Snake.CELLSIZE),STARTY));
        }
        verticalDir = 0;
        horizontalDir = 1;
        moving = false;
        length = STARTLENGTH;
        score = 0;
        grow = false;
        alive = true;
    }
    public void move() {
        if(!moving)
            return;

        com.games.snake.Point tail = points.get(points.size()-1);
        com.games.snake.Point oldHead = points.get(0);
        com.games.snake.Point newHead = new com.games.snake.Point(oldHead.getX() + horizontalDir* CELLSIZE, oldHead.getY() + verticalDir* CELLSIZE);
        for (int i = points.size()-1; i >0 ; i--) {
            points.set(i,points.get(i-1));
        }
        points.set(0,newHead);
        if(grow){
            points.add(tail);
            grow = false;
        }
    }

    public boolean checkCollision() {
        com.games.snake.Point head = points.get(0);
        for (int i = 1; i < points.size(); i++) {
            if (points.get(i).getX()==head.getX() && points.get(i).getY()==head.getY()){
                alive = false;
                moving = false;
                return true;
            }
        }
        return false;
    }

    public void render(Graphics g) {
        for (com.games.snake.Point p :points) {
            g.setColor(Color.WHITE);
            g.fillRect(p.getX(),p.getY(), CELLSIZE, CELLSIZE);
        }
    }

    /*
    GETTERS AND SETTERS
     */

    public int getHorizontalDir() {
        return horizontalDir;
    }

    public void setHorizontalDir(int i) {
        horizontalDir = i;
    }

    public int getVerticalDir() {
        return verticalDir;
    }

    public void setVerticalDir(int i) {
        verticalDir = i;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean b) {
        moving = b;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean b) {
        alive = false;
    }

    public int getScore() {
        return score;
    }

    public void incrementScore(int value){
        score+= value;
    }

    public com.games.snake.Point getHead() {
        return points.get(0);
    }

    public void setGrow(boolean b) {
        grow = b;
    }
}
