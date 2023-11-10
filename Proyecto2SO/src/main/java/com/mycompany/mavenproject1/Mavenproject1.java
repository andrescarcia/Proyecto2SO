/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mavenproject1;

import javax.swing.SwingUtilities;

/**
 *
 * @author andre
 */
public class Mavenproject1 {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Main_UI ventanaPrincipal = new Main_UI();
                ventanaPrincipal.setVisible(true);
            }
        });
    }
}
