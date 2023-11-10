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
public class AI extends Thread{
    public GameCharacter streetCharacter;
    public GameCharacter zeldaCharacter;
    private GameCharacter fighter1;
    private GameCharacter fighter2;
    private GameCharacter winner;
    private int sfWins;
    private int zeWins;
    private String currentState;
    private LinkList winnersList;
    private Semaphore mutex;
    private int roundCount;
    
    public AI(Semaphore m, LinkList winnersList){
        this.streetCharacter = null;
        this.zeldaCharacter = null;
        this.fighter1 = null;
        this.fighter2 = null;
        this.winner = null;
        this.currentState = "Esperando";
        this.sfWins = 0;
        this.zeWins = 0;
        this.winnersList = winnersList;
        this.mutex = m;
        this.roundCount = 0;
    }
    
    
    @Override
    public void run(){
        
        try{
            Random random = new Random();
            int outcome;

            sleep(100);
            
            while(true){            
                        
                mutex.acquire(1);
                System.out.println("AI semaforo");
                
                if(this.zeldaCharacter != null && this.streetCharacter != null){

                    this.currentState = "Procesando...";
                    
                    System.out.println(this.currentState);
                    
                    sleep(1000);
                    
                    outcome = random.nextInt(100);
                    System.out.println(outcome);
                    
                    
                    if(outcome < 40){
                        this.currentState = "Decidiendo Ganador...";
                        
                        System.out.println(this.currentState);
                        
                        fight();
                        
                        this.currentState = "Combate finalizado";

                    }else if(outcome < 67){
                        this.currentState = "Empate";
                        
                        System.out.println(this.currentState);
                        
                    }else{
                        this.currentState = "Combate cancelado";
                        System.out.println(this.currentState);
                        
                    }
                    

                emptyFighters();
                    
               }else{
                    this.currentState = "Esperando";
                    
                    System.out.println(this.currentState);
                    
                }
            
            this.roundCount++;
            mutex.release();
            sleep(1000);

            }
        } catch (InterruptedException ex) {
            Logger.getLogger(AI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void fight() throws InterruptedException{
        this.fighter1 = checkFirst();
        this.fighter2 = checkLast();
        System.out.println(this.fighter1.getName());
        System.out.println(this.fighter2.getName());
        
        while(this.fighter1.getHealth() > 0 && this.fighter2.getHealth() > 0){
            fighter1Turn();
            
            if(this.fighter2.getHealth() <= 0){
                this.winner = this.fighter1;
                break;
            }
             
            sleep(1000);
            fighter2Turn();
            
            if(this.fighter1.getHealth() <= 0){
                this.winner = this.fighter2;
                break;
            }
            sleep(1000);
        }
        
        System.out.println("Gana " + this.winner.getName() + "\n");
        this.winnersList.insertEnd(new Node(this.winner));
        checkWinner();
        this.winner = null;
    }


    
//   public void tie() throws InterruptedException{
//
//   } 
//    
//    public void cantFight() throws InterruptedException{
//        
//    }
//    
//    
//    

    public GameCharacter checkFirst(){
        if(this.zeldaCharacter.getAgility() > this.streetCharacter.getAgility()){
            return this.zeldaCharacter;
        }else{
            return this.streetCharacter;
        }
    }
    
    public GameCharacter checkLast(){
        if(this.fighter1 == this.zeldaCharacter){
            return this.streetCharacter;
        }else{
            return this.zeldaCharacter;
        }
    }
    
    public void checkWinner(){
    
        if(this.winner == this.streetCharacter){
            this.sfWins += 1;
          
        }else{
            this.zeWins += 1;
        }
    
    }
    
    public void fighter1Turn(){
        Random random = new Random();
        int damage;
        
        int selectedMove = random.nextInt(3);
        switch(selectedMove){
        
            case 0:
                damage = (this.fighter1.getStrenght());
                this.fighter2.setHealth(this.fighter2.getHealth() - damage);
            //    System.out.println(this.fighter1.getName() + " Usa " + this.fighter1.getMoveset()[selectedMove] + " Inflige " + damage + "\nVida restante de " + this.fighter2.getName() + ":" + this.fighter2.getHealth());
                break;
                
            case 1:
                damage = (this.fighter1.getSkill() * 75);
                this.fighter2.setHealth(this.fighter2.getHealth() - damage);
            //    System.out.println(this.fighter1.getName() + " Usa " + this.fighter1.getMoveset()[selectedMove] + " Inflige " + damage + "\nVida restante de " + this.fighter2.getName() + ":" + this.fighter2.getHealth());
                break;
                
            case 2:
                damage = (this.fighter1.getStrenght() * this.fighter1.getSkill());
                this.fighter2.setHealth(this.fighter2.getHealth() - damage);
            //    System.out.println(this.fighter1.getName() + " Usa " + this.fighter1.getMoveset()[selectedMove] + " Inflige " + damage + "\nVida restante de " + this.fighter2.getName() + ":" + this.fighter2.getHealth());
                break;
        
        }
    
    }
    
    public void fighter2Turn(){
        Random random = new Random();
        int damage;
        
        int selectedMove = random.nextInt(3);
        switch(selectedMove){
        
            case 0:
                damage = (this.fighter2.getStrenght());
                this.fighter1.setHealth(this.fighter1.getHealth() - damage);
            //    System.out.println(this.fighter2.getName() + " Usa " + this.fighter2.getMoveset()[selectedMove] + " Inflige " + damage + "\nVida restante de " + this.fighter1.getName() + ":" + this.fighter1.getHealth());
                break;
                
            case 1:
                damage = (this.fighter2.getSkill() * 75);
                this.fighter1.setHealth(this.fighter1.getHealth() - damage);
            //    System.out.println(this.fighter2.getName() + " Usa " + this.fighter2.getMoveset()[selectedMove] + " Inflige " + damage + "\nVida restante de " + this.fighter1.getName() + ":" + this.fighter1.getHealth());
                break;
                
            case 2:
                damage = (this.fighter2.getStrenght() * this.fighter2.getSkill());
                this.fighter1.setHealth(this.fighter1.getHealth() - damage);
            //    System.out.println(this.fighter2.getName() + " Usa " + this.fighter2.getMoveset()[selectedMove] + " Inflige " + damage + "\nVida restante de " + this.fighter1.getName() + ":" + this.fighter1.getHealth());
                break;
        
        }
    
    }

    public void emptyFighters(){
        this.fighter1 = null;
        this.fighter2 = null;
    }
    
    public GameCharacter getStreetCharacter() {
        return streetCharacter;
    }

    public void setStreetCharacter(GameCharacter streetCharacter) {
        this.streetCharacter = streetCharacter;
    }

    public GameCharacter getZeldaCharacter() {
        return zeldaCharacter;
    }

    public void setZeldaCharacter(GameCharacter zeldaCharacter) {
        this.zeldaCharacter = zeldaCharacter;
    }

    public GameCharacter getWinner() {
        return winner;
    }

    public void setWinner(GameCharacter winner) {
        this.winner = winner;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public GameCharacter getFighter1() {
        return fighter1;
    }

    public void setFighter1(GameCharacter fighter1) {
        this.fighter1 = fighter1;
    }

    public GameCharacter getFighter2() {
        return fighter2;
    }

    public void setFighter2(GameCharacter fighter2) {
        this.fighter2 = fighter2;
    }

    public LinkList getWinnersList() {
        return winnersList;
    }

    public void setWinnersList(LinkList winnersList) {
        this.winnersList = winnersList;
    }

    public int getRoundCount() {
        return roundCount;
    }

    public void setRoundCount(int roundCount) {
        this.roundCount = roundCount;
    }
    
    
    
}
