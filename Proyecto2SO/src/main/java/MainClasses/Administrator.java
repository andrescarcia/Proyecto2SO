/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MainClasses;

import java.util.Random;
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
        Random random = new Random();
        int outcome;
        
        try{
            while(true){
                
                mutex.acquire(1);

                if(this.processor.getRoundCount() >= 2){
                    
                    outcome = random.nextInt(100);
                    
                    if(outcome <= 80){
                        addCharacter("SF");
                        addCharacter("ZE");

                        checkRQueue(random);
                    }
                    
                    this.processor.setRoundCount(0);
                }
                
                        
                switch(this.processor.getCurrentState()){
                
                    case "Empate":
                        tie();
                        break;
                    
                    case "Combace Cancelado":
                        cantFight();
                        break;
                }        
                
                
                passStreet();
                passZelda();
                checkRQueue(random);
                countCharacter();// recorrer las colas y aumentar el contador de cada personaje.
                //this.processor.setStreetCharacter(GameCharacter.class.cast(this.street1.delFirst().getData()));
                
                mutex.release();
                sleep(1000);

            }

        }catch (InterruptedException ex) {
            Logger.getLogger(Administrator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void checkRQueue(Random random){
        if(this.streetR.getlSize() != 0 && this.zeldaR.getlSize() != 0){
            
            int outcome = random.nextInt(100);
            
            if(outcome <= 40){
                this.street1.insertEnd(this.streetR.delFirst());
                this.zelda1.insertEnd(this.zeldaR.delFirst());
            }
            
        }
    
    }
    
    public void passStreet(){
        
        if(this.street1.isEmpty()){
            
            if(this.street2.isEmpty()){
                
                if(this.street3.isEmpty()){
                
                    this.processor.setStreetCharacter(null);
                    
                }else{
                    this.processor.setStreetCharacter(GameCharacter.class.cast(this.street2.delFirst().getData()));
                }
                
            }else{
                this.processor.setStreetCharacter(GameCharacter.class.cast(this.street2.delFirst().getData()));
            }
            
        }else{
            this.processor.setStreetCharacter(GameCharacter.class.cast(this.street1.delFirst().getData()));
        }
        
    }
    
    public void passZelda(){
    
        if(this.zelda1.isEmpty()){
            
            if(this.zelda2.isEmpty()){
                this.processor.setZeldaCharacter(GameCharacter.class.cast(this.zelda3.delFirst().getData()));
                
            }else{
                
                if(this.zelda3.isEmpty()){
                    
                    this.processor.setZeldaCharacter(null);
                
                }else{
                    this.processor.setZeldaCharacter(GameCharacter.class.cast(this.zelda2.delFirst().getData()));
                }
            }
            
        }else{
            this.processor.setZeldaCharacter(GameCharacter.class.cast(this.zelda1.delFirst().getData()));
        }
    }
    
    public void addCharacter(String series){
        
        GameCharacter tempChar = new GameCharacter(series, this.idCount);
        int statTotal = (tempChar.getSkill() * 100) + tempChar.getHealth() + tempChar.getStrenght() + tempChar.getAgility();
    
        if(series.equals("SF")){
            
            if(statTotal >= 3000){
                this.street1.insertEnd(new Node(tempChar));
                
            }else if(statTotal >= 2000){
                this.street2.insertEnd(new Node(tempChar));
                
            }else{
                this.street3.insertEnd(new Node(tempChar));
            }
                      
        }else{
            
            if(statTotal >= 3000){
                this.zelda1.insertEnd(new Node(tempChar));
                
            }else if(statTotal >= 2000){
                this.zelda2.insertEnd(new Node(tempChar));
                
            }else{
                this.zelda3.insertEnd(new Node(tempChar));
            }
        }
        
        this.idCount++;
        
    }
    
    
    
}
