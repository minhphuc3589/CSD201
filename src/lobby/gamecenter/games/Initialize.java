/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lobby.gamecenter.games;

import javax.swing.JPanel;

/**
 * CSD201 - Game Play
 * 
 * @author PhucTDMCE190744
 */
public interface Initialize {
    public abstract JPanel getFrame();
    public abstract void start(int width, int height);
}
