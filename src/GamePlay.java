import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePlay extends JPanel implements ActionListener , KeyListener {
    // following line is tell us the game is on or off
    private boolean play=false;

    private int score=0;

    private int totalBrecks=21;

    private Timer timer;
    // delay after losing the game
    private int delay=8;

    //platform x pos
    private int batPosX =250;

    // platform ball X position
    private int ballPosX=290;

    //platform ball Y position
    private int ballPosY=529;

    private int ballXdir=-1;
    private int ballYdir =-1;
    private GenerateBreaks gB;

    public GamePlay() {
        gB =new GenerateBreaks(3,7);
        setFocusTraversalKeysEnabled(false);
        setFocusable(true);
        //focus traversal key like,TAB,SHIFT+TAB etc.,can't be  used to move the next components
        addKeyListener(this);
        timer=new Timer(8,this);
        timer.start();
    }
    public void paint(Graphics g){
        //Background
        g.setColor(Color.black);
        g.fillRect(1,1,692,592);
        gB.makeBricks((Graphics2D) g);
        //in makeBricks the parameter is type of Graphics2D but the paint method is
        //only have graphics so here we typecast graphics to graphics 2D

        //Border
        g.setColor(Color.yellow);
        g.fillRect(0,0,3 ,592);
        g.fillRect(0,0,692,3);
        g.fillRect(0,557,692,3);
        g.fillRect(680,0,3,592);

        //Score
        g.setColor(Color.white);
        g.setFont(new Font("sherif",Font.BOLD,25));
        g.drawString(""+score,590,30);

        //platform
        g.setColor(Color.yellow);
        g.fillRect(batPosX,550,100,8);

        //Ball
        g.setColor(Color.green);
        g.fillOval(ballPosX,ballPosY,20,20);

        //ball touches the floor
        if(ballPosY>570){
            play=false;//game is over
            ballXdir=-1;
            ballYdir =-2;
            g.setColor(Color.RED);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("GAME OVER,SCORE ="+score,190,300);
            g.drawString("Press to Restart",190,340);
        }

        //if all bricks are over
        if(totalBrecks==0){
            play=false;//game is over
            ballXdir=-1;
            ballYdir =-2;
            g.setColor(Color.RED);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("YOU WON, SCORE ="+score,190,300);
            g.drawString("Press Enter to Restart",190,340);
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (batPosX >= 600) {
                batPosX = 600;
            } else
                moveRight();//platform should be move right
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (batPosX <= 10)
                batPosX = 10;
            else
                moveLeft();//platform should move left
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {//Restart the game
            if (!play) {//not playing
                ballPosX = 120;
                ballPosY = 350;
                ballXdir = -1;
                ballYdir = -2;
                score = 0;
                batPosX = 310;
                totalBrecks = 21;
                gB = new GenerateBreaks(3, 7);
                repaint();
            }

        }
    }



    private void moveRight() {
        play=true;
        batPosX +=20;
    }
    private void moveLeft() {
        play=true;
        batPosX -=20;
    }


    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(play){
            if(new Rectangle(ballPosX,ballPosY,20,20).intersects(new Rectangle(batPosX,550,100,8))){
                ballYdir =-ballYdir;
            }
            loop:
            for(int i = 0; i< gB.map.length; i++){
                for(int j = 0; j< gB.map[0].length; j++){
                    if(gB.map[i][j]>0){
                        int brickX=j* gB.brickWidth+80;
                        int brickY=i* gB.brickHeight+50;
                        int brickWidth= gB.brickWidth;
                        int brickHeight= gB.brickHeight;
                        Rectangle brickRect=new Rectangle(brickX,brickY,brickWidth,brickHeight);
                        Rectangle ballRect = new Rectangle(ballPosX,ballPosY,20,20);
                        if(ballRect.intersects(brickRect)){
                            gB.setVal(i,j,0);
                            totalBrecks--;
                            score+=5;
                            //if ball hits brecks horizontally left to right
                            if(ballPosX + 19 <= brickRect.x || ballPosX+1>=brickRect.x+brickWidth){
                                ballXdir=-ballXdir;
                            }
                            else
                                ballYdir =-ballYdir;
                            break loop;
                        }

                    }
                }
            }
            ballPosX +=ballXdir;
            ballPosY += ballYdir;
            if(ballPosX<0){ // ball goes out of bound from left
                ballXdir=-ballXdir;
            }
            if(ballPosY<0){
                ballYdir =-ballYdir;// ball goes above the roof
            }
            if(ballPosX>670){ // ball goes out of bound from right
                ballXdir =- ballXdir;
            }
            repaint();
        }

    }

}
