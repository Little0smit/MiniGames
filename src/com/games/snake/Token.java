package com.games.snake;

import java.awt.*;

public class Token {
    private int maxWidth, maxHeight, cellWidth;
    private int xPos, yPos, value;

    public Token(int width, int height, int cellWidth){
        this.maxWidth = width;
        this.maxHeight = height;
        this.cellWidth = cellWidth;

        moveToken();
    }

    public void moveToken(){
        xPos = ((int)(Math.random()* (maxWidth+1))/cellWidth)*cellWidth;
        yPos = ((int)(Math.random()* (maxHeight+1))/cellWidth)*cellWidth;
        value = 1;
    }

    public int checkEaten(com.games.snake.Point head){
        if(head.getX()==xPos && head.getY()==yPos){
            moveToken();
            return value;
        }
        return 0;
    }

    public void render(Graphics g){
        g.setColor(Color.GREEN);
        g.fillRect(xPos,yPos,cellWidth,cellWidth);
    }
}
