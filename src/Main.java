import javax.swing.*;
public class Main {
    public static void main(String[] args) {
        JFrame frame=new JFrame();// make instance of JFrame class
        GamePlay gp=new GamePlay();

        // basically set bound set location and height and width
        frame.setBounds(10,10,700,600);

        // we don't want any person can resize our frame so resizable set false
        frame.setResizable(false);

        //Here is the title
        frame.setTitle("Pramod Brick Breakers Game");

        //when we click on the crossbar our program should be close
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // we have to set visibility of our frame
        frame.setVisible(true);
        frame.add(gp);
    }
}
