/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swingaling.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import swingaling.entities.EntityFactory;
import swingaling.entities.EntityTypes;
import swingaling.server.SwingALingServerInterface;

/**
 *
 * @author jamesTemp
 */
public class SwingALingGUI extends JFrame implements KeyListener {
    // GUI Constants
    public static final int AREA_WIDTH = 400;
    public static final int AREA_HEIGHT = 400;
    public static final Color DARK_GREEN_COLOR = new Color(0, 100, 0);
    
    // The server to listen to.
    private final SwingALingServerInterface server;
    
    // The entity factory
    private final EntityFactory entityFactory = new EntityFactory();
    
    // GUI Components
    private final Thread drawThread;
    private final Runnable drawerRunnable;
    
    public SwingALingGUI(SwingALingServerInterface server) {
        super("SwingALing GUI Frame");
        
        this.drawerRunnable = () -> {
            while (server.isRunning()) {
                BufferStrategy bf = this.getBufferStrategy();
                final Graphics g = bf.getDrawGraphics();
                
                try {
                    // Paint the background
                    g.setColor(DARK_GREEN_COLOR);
                    g.fillRect(0, 0, AREA_WIDTH, AREA_HEIGHT);
                    
                    // Paint the entities
                    server.getEntities().stream().forEach((entity) -> {
                        entity.draw((Graphics2D)g);
                    });
                } finally {
                    g.dispose();
                }
                
                bf.show();
                
                Toolkit.getDefaultToolkit().sync();
            }
        };
        this.server = server;
        
        initGUI();
        
        drawThread = new Thread(drawerRunnable);
    }
    
    public void startDrawableThread() {
        this.createBufferStrategy(2);
        
        drawThread.start();
    }
    
    private void initGUI() {
        this.setSize(400, 400);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.setResizable(false);
        
        this.setVisible(true);
        
        addKeyListener(this);
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_1:
                server.addEntity(entityFactory.getEntity(EntityTypes.ORC, server));
                break;
            case KeyEvent.VK_2:
                server.addEntity(entityFactory.getEntity(EntityTypes.OGRE, server));
                break;
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }
}