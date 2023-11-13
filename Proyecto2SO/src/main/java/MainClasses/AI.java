/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MainClasses;
import com.mycompany.mavenproject1.Ganadores_UI;
import com.mycompany.mavenproject1.Main_UI;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;


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
    public LinkList winnerListSF;
    public LinkList winnerListZelda;
    private Semaphore mutex;
    private int roundCount;
    private  int durSeg;
    

    private Main_UI mainUI;
    private Ganadores_UI ganadoresUI;

    public AI(Semaphore m, LinkList winnersList,LinkList winnerListSF, LinkList winnerListZelda, Main_UI mainUI,Ganadores_UI ganadoresUI ) {
        this.streetCharacter = null;
        this.zeldaCharacter = null;
        this.fighter1 = null;
        this.fighter2 = null;
        this.winner = null;
        this.currentState = "Esperando";
        this.sfWins = 0;
        this.zeWins = 0;
        this.winnersList = winnersList;
        this.winnerListSF = winnerListSF;
        this.winnerListZelda = winnerListZelda; 
        this.mutex = m;
        this.roundCount = 0;
        this.mainUI = mainUI;
        this.ganadoresUI = ganadoresUI;
        this.durSeg = 100;
    }

    @Override
    public void run() {

        try {
            Random random = new Random();
            int outcome;

            sleep(20);

            while (true) {

                mutex.acquire(1);
                System.out.println("IA");

                if (this.zeldaCharacter != null && this.streetCharacter != null) {

                    this.currentState = "Procesando...";
                    setCurrentState(currentState);
                    //System.out.println(this.currentState);
                    
                    //Cambiar este sleep
                    sleep(durSeg * 10);

                    outcome = random.nextInt(100);
                    //System.out.println(outcome);

                    if (outcome < 40) {
                        this.currentState = "Decidiendo Ganador...";
                        setCurrentState(currentState);
                        //System.out.println(this.currentState);

                        fight();

                        this.currentState = "Combate finalizado";

                    } else if (outcome < 67) {
                        this.currentState = "Empate";
                        setCurrentState(currentState);
                        //System.out.println(this.currentState);

                    } else {
                        this.currentState = "Combate cancelado";
                        setCurrentState(currentState);
                        //System.out.println(this.currentState);

                    }


                emptyFighters();
                    
               }else{
                    this.currentState = "Esperando";
                    setCurrentState(currentState);
                    //System.out.println(this.currentState);
                    
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
        setZeldaCharacter(zeldaCharacter);
        setStreetCharacter(streetCharacter);
        this.mainUI.setVidaSF(this.streetCharacter.toString());
        this.mainUI.setVidaZelda(this.zeldaCharacter.toString());
        System.out.println(this.fighter1.getName());
        System.out.println(this.fighter2.getName());
        
        System.out.println("vida1:" + this.fighter1.getHealth());
        System.out.println("vida2: " + this.fighter2.getHealth());
        
        while(this.fighter1.getHealth() > 0 && this.fighter2.getHealth() > 0){
            fighter1Turn();
            
            if(this.fighter2.getHealth() <= 0){
                this.winner = this.fighter1;
                break;
            }
            
            //cambiar este sleep
            sleep(durSeg);
            fighter2Turn();
            
            if(this.fighter1.getHealth() <= 0){
                this.winner = this.fighter2;
                break;
            }
            //cambiar este sleep
            sleep(durSeg);
        }
        
        //System.out.println("Gana " + this.winner.getName() + "\n");
        this.winnersList.insertEnd(new Node(this.winner));
       
        setWinnersUI();
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
    public GameCharacter checkFirst() {
        if (this.zeldaCharacter.getAgility() > this.streetCharacter.getAgility()) {
            return this.zeldaCharacter;
        } else {
            return this.streetCharacter;
        }
    }

    public GameCharacter checkLast() {
        if (this.fighter1 == this.zeldaCharacter) {
            return this.streetCharacter;
        } else {
            return this.zeldaCharacter;
        }
    }
    
    public void setWinnersUI(){
        this.ganadoresUI.getListSF().setText(winnerListSF.printList());
        this.ganadoresUI.getListZelda().setText(winnerListZelda.printList());
}

    public void checkWinner() {

        if (this.winner == this.streetCharacter) {
            this.sfWins += 1;
            this.mainUI.getStreetWin().setText(Integer.toString(this.sfWins));
            
        // Concatenar el nombre y el ID del ganador y agregarlo a la lista correspondiente
        String winnerInfo = this.winner.getName() + " ID: " + this.winner.getId();
        winnerListSF.insertEnd(new Node(winnerInfo));
         setWinnersUI();

        } else {
            this.zeWins += 1;
            this.mainUI.getZeldaWin().setText(Integer.toString(this.zeWins));
            
        // Concatenar el nombre y el ID del ganador y agregarlo a la lista correspondiente
        String winnerInfo = this.winner.getName() + " ID: " + this.winner.getId();
        winnerListZelda.insertEnd(new Node(winnerInfo));
         setWinnersUI();
        }

    }

    public void fighter1Turn() {
        Random random = new Random();
        int damage;
        this.mainUI.setVidaSF(this.streetCharacter.toString());
        this.mainUI.setVidaZelda(this.zeldaCharacter.toString());
        int selectedMove = random.nextInt(3);
        switch (selectedMove) {

            case 0:
                damage = (this.fighter1.getStrenght());
                this.fighter2.setHealth(this.fighter2.getHealth() - damage);
                if (this.fighter1 == this.zeldaCharacter) {
                    this.mainUI.setVidaZelda("Vida" + ":" + this.fighter1.getHealth());
                    this.mainUI.setVidaSF("Vida" + ":" + this.fighter2.getHealth());
                    this.mainUI.setDamageZelda(" Inflige " + damage);
                    this.mainUI.setTurnZelda(" Usa " + this.fighter1.getMoveset()[selectedMove]);
                } else {
                    this.mainUI.setVidaZelda("Vida" + ":" + this.fighter2.getHealth());
                    this.mainUI.setVidaSF("Vida" + ":" + this.fighter1.getHealth());
                    this.mainUI.setDamageSF(" Inflige " + damage);
                    this.mainUI.setTurnSF(" Usa " + this.fighter1.getMoveset()[selectedMove]);
                }
                System.out.println(this.fighter1.getName() + " Usa " + this.fighter1.getMoveset()[selectedMove] + " Inflige " + damage + "\nVida restante de " + this.fighter2.getName() + ":" + this.fighter2.getHealth());
                break;

            case 1:
                damage = (this.fighter1.getSkill() * 75);
                this.fighter2.setHealth(this.fighter2.getHealth() - damage);
                if (this.fighter1 == this.zeldaCharacter) {
                    this.mainUI.setVidaZelda("Vida" + ":" + this.fighter1.getHealth());
                    this.mainUI.setVidaSF("Vida" + ":" + this.fighter2.getHealth());
                    this.mainUI.setDamageZelda(" Inflige " + damage);
                    this.mainUI.setTurnZelda(" Usa " + this.fighter1.getMoveset()[selectedMove]);
                } else {
                    this.mainUI.setVidaZelda("Vida" + ":" + this.fighter2.getHealth());
                    this.mainUI.setVidaSF("Vida" + ":" + this.fighter1.getHealth());
                    this.mainUI.setDamageSF(" Inflige " + damage);
                    this.mainUI.setTurnSF(" Usa " + this.fighter1.getMoveset()[selectedMove]);
                }

                System.out.println(this.fighter1.getName() + " Usa " + this.fighter1.getMoveset()[selectedMove] + " Inflige " + damage + "\nVida restante de " + this.fighter2.getName() + ":" + this.fighter2.getHealth());
                break;

            case 2:
                damage = (this.fighter1.getStrenght() * this.fighter1.getSkill());
                this.fighter2.setHealth(this.fighter2.getHealth() - damage);
                if (this.fighter1 == this.zeldaCharacter) {
                    this.mainUI.setVidaZelda("Vida" + ":" + this.fighter1.getHealth());
                    this.mainUI.setVidaSF("Vida" + ":" + this.fighter2.getHealth());
                    this.mainUI.setDamageZelda(" Inflige " + damage);
                    this.mainUI.setTurnZelda(" Usa " + this.fighter1.getMoveset()[selectedMove]);
                } else {
                    this.mainUI.setVidaZelda("Vida" + ":" + this.fighter2.getHealth());
                    this.mainUI.setVidaSF("Vida" + ":" + this.fighter1.getHealth());
                    this.mainUI.setDamageSF(" Inflige " + damage);
                    this.mainUI.setTurnSF(" Usa " + this.fighter1.getMoveset()[selectedMove]);
                }
                System.out.println(this.fighter1.getName() + " Usa " + this.fighter1.getMoveset()[selectedMove] + " Inflige " + damage + "\nVida restante de " + this.fighter2.getName() + ":" + this.fighter2.getHealth());
                break;

        }

    }

    public void fighter2Turn() {
        Random random = new Random();
        int damage;
        this.mainUI.setVidaSF(this.streetCharacter.toString());
        this.mainUI.setVidaZelda(this.zeldaCharacter.toString());
        int selectedMove = random.nextInt(3);
        switch (selectedMove) {
            

            case 0:
                damage = (this.fighter2.getStrenght());
                this.fighter1.setHealth(this.fighter1.getHealth() - damage);

                if (this.fighter2 == this.zeldaCharacter) {
                    this.mainUI.setVidaZelda("Vida" + ":" + this.fighter2.getHealth());
                    this.mainUI.setVidaSF("Vida" + ":" + this.fighter1.getHealth());
                    this.mainUI.setDamageZelda(" Inflige " + damage);
                    this.mainUI.setTurnZelda(" Usa " + this.fighter2.getMoveset()[selectedMove]);
                } else {
                    this.mainUI.setVidaZelda("Vida" + ":" + this.fighter1.getHealth());
                    this.mainUI.setVidaSF("Vida" + ":" + this.fighter2.getHealth());
                    this.mainUI.setDamageSF(" Inflige " + damage);
                    this.mainUI.setTurnSF(" Usa " + this.fighter2.getMoveset()[selectedMove]);
                }
                System.out.println(this.fighter2.getName() + " Usa " + this.fighter2.getMoveset()[selectedMove] + " Inflige " + damage + "\nVida restante de " + this.fighter1.getName() + ":" + this.fighter1.getHealth());
                break;

            case 1:
                damage = (this.fighter2.getSkill() * 75);
                this.fighter1.setHealth(this.fighter1.getHealth() - damage);
                this.fighter1.setHealth(this.fighter1.getHealth() - damage);

                if (this.fighter2 == this.zeldaCharacter) {
                    this.mainUI.setVidaZelda("Vida" + ":" + this.fighter2.getHealth());
                    this.mainUI.setVidaSF("Vida" + ":" + this.fighter1.getHealth());
                    this.mainUI.setDamageZelda(" Inflige " + damage);
                    this.mainUI.setTurnZelda(" Usa " + this.fighter2.getMoveset()[selectedMove]);
                } else {
                    this.mainUI.setVidaZelda("Vida" + ":" + this.fighter1.getHealth());
                    this.mainUI.setVidaSF("Vida" + ":" + this.fighter2.getHealth());
                    this.mainUI.setDamageSF(" Inflige " + damage);
                    this.mainUI.setTurnSF(" Usa " + this.fighter2.getMoveset()[selectedMove]);
                }
                System.out.println(this.fighter2.getName() + " Usa " + this.fighter2.getMoveset()[selectedMove] + " Inflige " + damage + "\nVida restante de " + this.fighter1.getName() + ":" + this.fighter1.getHealth());
                break;

            case 2:
                damage = (this.fighter2.getStrenght() * this.fighter2.getSkill());
                this.fighter1.setHealth(this.fighter1.getHealth() - damage);
                this.fighter1.setHealth(this.fighter1.getHealth() - damage);

                if (this.fighter2 == this.zeldaCharacter) {
                    this.mainUI.setVidaZelda("Vida" + ":" + this.fighter2.getHealth());
                    this.mainUI.setVidaSF("Vida" + ":" + this.fighter1.getHealth());
                    this.mainUI.setDamageZelda(" Inflige " + damage);
                    this.mainUI.setTurnZelda(" Usa " + this.fighter2.getMoveset()[selectedMove]);
                } else {
                    this.mainUI.setVidaZelda("Vida" + ":" + this.fighter1.getHealth());
                    this.mainUI.setVidaSF("Vida" + ":" + this.fighter2.getHealth());
                    this.mainUI.setDamageSF(" Inflige " + damage);
                    this.mainUI.setTurnSF(" Usa " + this.fighter2.getMoveset()[selectedMove]);
                }
                System.out.println(this.fighter2.getName() + " Usa " + this.fighter2.getMoveset()[selectedMove] + " Inflige " + damage + "\nVida restante de " + this.fighter1.getName() + ":" + this.fighter1.getHealth());
                break;

        }

    }

    public void emptyFighters() {
        this.fighter1 = null;
        this.fighter2 = null;
    }

    public GameCharacter getStreetCharacter() {
        return streetCharacter;

    }

    public void setStreetCharacter(GameCharacter streetCharacter) {
        this.streetCharacter = streetCharacter;
        if (streetCharacter != null) {
            this.mainUI.setDamageSF("Fuerza: "+Integer.toString(this.streetCharacter.getStrenght()));
            this.mainUI.setTurnSF("Agilidad: "+Integer.toString(this.streetCharacter.getAgility()));
            this.mainUI.setVidaSF("Habilidad: " + Integer.toString(this.streetCharacter.getSkill()));          this.mainUI.setPersonajeSFLabel(streetCharacter.getName());
        } else {
            this.mainUI.setPersonajeSFLabel("No asignado"); // O cualquier texto predeterminado
        }
    }


    public GameCharacter getZeldaCharacter() {
        return zeldaCharacter;
    }

    public void setZeldaCharacter(GameCharacter zeldaCharacter) {
        this.zeldaCharacter = zeldaCharacter;
        if (zeldaCharacter != null) {
            this.mainUI.setDamageZelda("Fuerza: "+Integer.toString(this.zeldaCharacter.getStrenght()));
            this.mainUI.setTurnZelda("Agilidad: "+Integer.toString(this.zeldaCharacter.getAgility()));
            this.mainUI.setVidaZelda("Habilidad: " + Integer.toString(this.zeldaCharacter.getSkill()));

            this.mainUI.setPersonajeZeldaLabel(zeldaCharacter.getName());
        } else {
            this.mainUI.setPersonajeZeldaLabel("No asignado"); // O cualquier texto predeterminado
        }
    }


    public void setDur(int durSegInterf) {
        this.durSeg = durSegInterf;
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
        this.mainUI.getEstadoCPU().setText(currentState);
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
