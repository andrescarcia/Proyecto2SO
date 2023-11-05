/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MainClasses;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sebastián
 */
public class Administrator extends Thread{
    Semaphore mutex;
    AI processor;
    int idCount;
    
    LinkList street1;
    LinkList street2;
    LinkList street3;
    LinkList streetR;
    
    LinkList zelda1;
    LinkList zelda2;
    LinkList zelda3;
    LinkList zeldaR;
    
    public Administrator(AI pro, Semaphore m){
        this.processor = pro;
        this.idCount = 0;
        this.mutex = m;
        
        this.street1 = new LinkList();
        this.street2 = new LinkList();
        this.street3 = new LinkList();
        this.streetR = new LinkList();
        
        this.zelda1 = new LinkList();
        this.zelda2 = new LinkList();
        this.zelda3 = new LinkList();
        this.zeldaR = new LinkList();
    }
    
    @Override
    public void run(){
  
        try{
            while(true){
                
                mutex.acquire(1);

                if(this.processor.getRoundCount() >= 2){
                    GameCharacter tempStreet = new GameCharacter("SF", this.idCount);
                    this.idCount++;
                    GameCharacter tempZelda = new GameCharacter("ZE", this.idCount);
                    this.idCount++;
                    
                    this.processor.setRoundCount(0);
                }
                
                if(this.processor.getState().equals("Empate")){
                    tie();
                    
                }else if(this.processor.getState().equals("Combate Cancelado")){
                    cantFight();
                    
                }else{
                    
                    passStreet();
                    passZelda();
                    
                    
                }
                
                
                
                mutex.release();
                sleep(1000);

            }

        }catch (InterruptedException ex) {
            Logger.getLogger(Administrator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
