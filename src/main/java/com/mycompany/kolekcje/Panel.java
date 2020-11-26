/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.kolekcje;

/**
 *
 * @author Mirosław Karwowski
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Panel extends JPanel {
        
    private ArrayList<Ball> Ball_list;
    private int size = 20;
    private Timer timer;
    private final int DELAY = 20; //dla 30fps -> 1s/30 = 0,033s
    
    public Panel() {
        Ball_list = new ArrayList<>();
        setBackground(Color.BLACK);
        addMouseListener(new Event());
        addMouseWheelListener(new Event());
        timer = new Timer(DELAY, new Event());
        timer.start();
    }
    
    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Ball k : Ball_list) {
            g.setColor(k.color);
            g.drawOval(k.x-(k.size/2), k.y-(k.size/2), k.size, k.size);
        }
        g.setColor(Color.YELLOW);
        g.drawString(Integer.toString(Ball_list.size()),40,40);
    }
    
    private class Event implements MouseListener, ActionListener,MouseWheelListener{
        @Override public  void mouseClicked(MouseEvent e) {   
             
             
        }
        @Override public void mousePressed(MouseEvent e) {
            
            //mouse center click(scroll click) animation start
            if(e.getButton() == MouseEvent.BUTTON2)
            {
                timer.start();
            }
            
            //mosue right click stop animation
            if(e.getButton() == MouseEvent.BUTTON3)
            {
                timer.stop();
            }
            
            //mouse left click create new ball
            if(e.getButton() == MouseEvent.BUTTON1)
            {
                Ball_list.add(new Ball(e.getX(), e.getY(), size));
                repaint();
                
                   
                
            }
            
           
        }
        @Override public void mouseReleased(MouseEvent e) {
        }
        @Override public void mouseEntered(MouseEvent e) {
            timer.start();
        }
        @Override public void mouseExited(MouseEvent e) {
            timer.stop();
        }
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            int notches=e.getWheelRotation();
            
            if(notches != 0 ){
                size = size-notches;
                if(size <=0)
                {
                    size = 1;
                }
            }
       
        }
        @Override public void actionPerformed(ActionEvent e){
            
            int i=0;
            for (Ball k : Ball_list) {
                k.collision(i,k);
                k.update();
                i++;
                
            }
            
            repaint();
            
        }   

        
    }
    
    private class Ball {
        public int x, y, size, xspeed, yspeed;
        public Color color;
        private final int MAX_SPEED = 5;
        
        public Ball(int x, int y, int size) 
        {
            this.x = x;
            this.y = y;
            this.size = size;
            color = new Color((float) Math.random(), (float) Math.random(), (float) Math.random());
            do{
            xspeed = (int) (Math.random() * MAX_SPEED * 2 -MAX_SPEED);
            yspeed = (int) (Math.random() * MAX_SPEED * 2 -MAX_SPEED);
            }while(xspeed==0 || yspeed ==0);
        }
        public void update() 
        {
            
            x += xspeed;
            y += yspeed;
            if (x <= 0 || x >= getWidth()) {
                xspeed = -xspeed;
            }
            if (y <= 0 || y >= getHeight()) {
                yspeed = -yspeed;
            }
            
         
        }
        public void collision(int i,Ball k){
               
                
                
            for (int j = i + 1; j < Ball_list.size(); j++) {
                double xDistance = pow(k.x - Ball_list.get(j).x, 2);
                double yDistance = pow(k.y - Ball_list.get(j).y, 2);

                if (sqrt(xDistance + yDistance) < (k.size + Ball_list.get(j).size) / 2 && Ball_list.size() > 1) {
                    int a = k.xspeed;
                    int b = k.yspeed;
                    k.xspeed = Ball_list.get(j).xspeed;
                    k.yspeed = Ball_list.get(j).yspeed;
                    Ball_list.get(j).xspeed = a;
                    Ball_list.get(j).yspeed = b;
                    

                }

            }
        }
        
       
         
    }
    
}