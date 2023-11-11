/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MainClasses;

import com.mycompany.mavenproject1.Main_UI;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Sebastián
 */

public class Administrator extends Thread {

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
    private Main_UI mainUI;
    
    public Administrator(AI pro, Semaphore m, Main_UI mainUI) {
        this.processor = pro;
        this.idCount = 0;
        this.mutex = m;
        this.mainUI = mainUI;
        
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
    public void run() {
        Random random = new Random();
        
        for(int i = 0; i < 10; i++){
            addCharacter("SF");
            addCharacter("ZE");
        }
        

        try {
            while (true) {
                
                sleep(10);
                mutex.acquire(1);
                System.out.println("Administrador");

                if (this.processor.getRoundCount() >= 2) {
                    if (random.nextInt(100) <= 80) {
                        addCharacter("SF");
                        addCharacter("ZE");
                        checkRQueue(random);
                    }

                    this.processor.setRoundCount(0);
                }

                // ... otros casos y métodos ...
                
                switch(this.processor.getCurrentState()){
                
                    case "Empate":
                        tie();
                        break;
                        
                    case "Combate cancelado":
                        cantFight();
                        break;
                        
                }
                
                
                
                checkRQueue(random);
                // Incrementar la espera de los personajes en cada lista
                incrementQueueWaitForList(street2); // Para los de street nivel 2
                incrementQueueWaitForList(street3); // Para los de street nivel 3

                incrementQueueWaitForList(zelda2);  // Para los de zelda nivel 2
                incrementQueueWaitForList(zelda3);  // Para los de zelda nivel 3


                passStreet();
                passZelda();
                updateTextPane(street1, mainUI.getCola1_Capcom());
                updateTextPane(street2, mainUI.getCola2_Capcom());
                updateTextPane(street3, mainUI.getCola3_Capcom());
                updateTextPane(streetR, mainUI.getColaR_Capcom());

                updateTextPane(zelda1, mainUI.getCola1_Nintendo());
                updateTextPane(zelda2, mainUI.getCola2_Nintendo());
                updateTextPane(zelda3, mainUI.getCola3_Nintendo());
                updateTextPane(zeldaR, mainUI.getColaR_Nintendo());

                //System.out.println("Admin suelta semaforo");
                mutex.release();
                sleep(1000);
                
            }

        } catch (InterruptedException ex) {
            Logger.getLogger(Administrator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void updateTextPane(LinkList list, JTextPane textPane) {
        String listString = list.printList1Line();
        SwingUtilities.invokeLater(() -> textPane.setText(listString));
    }



// Y el método  actualizado 
    private void incrementQueueWaitForList(LinkList currentList) {
        Node currentNode = currentList.getlFirst();
        while (currentNode != null) {
            GameCharacter character = (GameCharacter) currentNode.getData();
            if (character.getQueueWait() == 8) { // Comprueba antes de incrementar
                changePriority(character, currentList); // Maneja el cambio de prioridad internamente
            } else {
                character.incrementQueueWait(); // Incrementa después de verificar
            }
            currentNode = currentNode.getpNext();
        }
    }


// Nuevo método changePriority
    private void changePriority(GameCharacter character, LinkList currentList) {
        // Decide la siguiente acción basándose en currentList y los detalles del personaje
        if (currentList == street2 || currentList == street3) {
            // Mueve los personajes de SF a la siguiente cola o reinicia su tiempo de espera
            handleStreetCharacterPriority(character, currentList);
        } else if (currentList == zelda2 || currentList == zelda3) {
            // Mueve los personajes de ZE a la siguiente cola o reinicia su tiempo de espera
            handleZeldaCharacterPriority(character, currentList);
        }
    }

// Método para manejar el cambio de prioridad de los personajes de Street Fighter
    private void handleStreetCharacterPriority(GameCharacter character, LinkList currentList) {
        if (currentList == street2) {
            moveToNextList(character, currentList, street1);
        }else{
            moveToNextList(character, currentList, street2);
        }
    }

// Método para manejar el cambio de prioridad de los personajes de Zelda
    private void handleZeldaCharacterPriority(GameCharacter character, LinkList currentList) {
        if (currentList == this.zelda2) {
            moveToNextList(character, currentList, this.zelda1);
        } else{
            moveToNextList(character, currentList, this.zelda2);
        }
    }

// Método para mover un personaje a la siguiente lista (utilizado por los métodos de manejo de prioridad)
    private void moveToNextList(GameCharacter character, LinkList currentList, LinkList nextList) {
        // Remueve el personaje de la lista actual
        currentList.remove(character); // Necesitarás implementar el método remove en LinkList
        // Reinicia el tiempo de espera en la cola del personaje
        character.resetQueueWait();
        // Añade el personaje al final de la siguiente lista
        nextList.insertEnd(new Node(character));
    }
    
    // Ya no se usa
//    private void moveToLevelThree(GameCharacter character, LinkList currentList, Node previousNode, Node currentNode) {
//        // Decide to which level 3 list the character should be moved
//        LinkList levelThreeList = currentList == this.streetR ? this.street3 : this.zelda3;
//        moveToNextPriority(character, currentList, levelThreeList, previousNode, currentNode);
//    }
    private void moveToNextPriority(GameCharacter character, LinkList currentList, LinkList nextList, Node previousNode, Node currentNode) {
        // Remover el personaje de la cola actual
        if (previousNode == null) { // Si es el primer nodo
            currentList.setlFirst(currentNode.getpNext());
        } else {
            previousNode.setpNext(currentNode.getpNext());
        }
        // Disminuir el tamaño de la cola actual
        currentList.setlSize(currentList.getlSize() - 1);

        // Si llegó al final de la cola actual, actualizar el último nodo
        if (currentNode == currentList.getlLast()) {
            currentList.setlLast(previousNode);
        }

        // Reiniciar el contador de espera del personaje
        character.resetQueueWait();

        // Crear un nuevo nodo para la siguiente cola
        Node newNode = new Node(character);

        // Insertar el personaje al final de la siguiente cola
        if (nextList.isEmpty()) {
            nextList.setlFirst(newNode);
            nextList.setlLast(newNode);
        } else {
            nextList.getlLast().setpNext(newNode);
            nextList.setlLast(newNode);
        }
        // Aumentar el tamaño de la siguiente cola
        nextList.setlSize(nextList.getlSize() + 1);
    }

    private void tie() {

        // Volver a poner ambos personajes en la última posición de sus respectivas colas de nivel 1
        this.street1.insertEnd(new Node(this.processor.getStreetCharacter()));
        this.zelda1.insertEnd(new Node(this.processor.getZeldaCharacter()));

        // Reiniciar los personajes en la instancia processor
        this.processor.setStreetCharacter(null);
        this.processor.setZeldaCharacter(null);
    }

    public void cantFight() {
        
        this.streetR.insertEnd(new Node(this.processor.getStreetCharacter()));
        this.zeldaR.insertEnd(new Node(this.processor.getZeldaCharacter()));
        
        // Reiniciar los personajes en la instancia processor
        this.processor.setStreetCharacter(null);
        this.processor.setZeldaCharacter(null);
    }

    public void checkRQueue(Random random) {
        if (this.streetR.getlSize() != 0 && this.zeldaR.getlSize() != 0) {

            int outcome = random.nextInt(100);

            if (outcome <= 40) {
                GameCharacter tempStreet = GameCharacter.class.cast(this.streetR.delFirst().getData());
                GameCharacter tempZelda = GameCharacter.class.cast(this.zeldaR.delFirst().getData());
                
                this.street1.insertEnd(new Node(tempStreet));
                this.zelda1.insertEnd(new Node(tempZelda));
            }

        }

    }

    public void passStreet() {

        if (this.street1.isEmpty()) {

            if (this.street2.isEmpty()) {

                if (this.street3.isEmpty()) {

                    this.processor.setStreetCharacter(null);

                } else {
                    this.processor.setStreetCharacter(GameCharacter.class.cast(this.street3.delFirst().getData()));
                }

            } else {
                this.processor.setStreetCharacter(GameCharacter.class.cast(this.street2.delFirst().getData()));
            }

        } else {
            this.processor.setStreetCharacter(GameCharacter.class.cast(this.street1.delFirst().getData()));
        }
        
        if(this.processor.getStreetCharacter() != null){
            //mainUI.getstreetIcon().setIcon(new ImageIcon("C:\\Users\\Sebastián\\Documents\\Unimet\\Sistemas operativos\\Proyecto 2\\Proyecto2SO\\src\\main\\java\\" + this.processor.getStreetCharacter().getName() + ".png"));
            mainUI.getstreetIcon().setIcon(new ImageIcon(getClass().getResource("/imagenes/" + this.processor.getStreetCharacter().getName() + ".png")));
        }
        

    }

    public void passZelda() {

        if (this.zelda1.isEmpty()) {

            if (this.zelda2.isEmpty()) {
                
                if (this.zelda3.isEmpty()){
                    
                    this.processor.setZeldaCharacter(null);
                }else{
                    this.processor.setZeldaCharacter(GameCharacter.class.cast(this.zelda3.delFirst().getData()));
                }
                

            } else {

                this.processor.setZeldaCharacter(GameCharacter.class.cast(this.zelda2.delFirst().getData()));
            }

        } else {
            this.processor.setZeldaCharacter(GameCharacter.class.cast(this.zelda1.delFirst().getData()));
        }
        
        if(this.processor.getZeldaCharacter()!= null){
            //mainUI.getstreetIcon().setIcon(new ImageIcon("C:\\Users\\Sebastián\\Documents\\Unimet\\Sistemas operativos\\Proyecto 2\\Proyecto2SO\\src\\main\\java\\" + this.processor.getStreetCharacter().getName() + ".png"));
            mainUI.getzeldaIcon().setIcon(new ImageIcon(getClass().getResource("/imagenes/" + this.processor.getZeldaCharacter().getName() + ".png")));
        }
        
    }

    public void addCharacter(String series) {

        GameCharacter tempChar = new GameCharacter(series, this.idCount);
        int statTotal = (tempChar.getSkill() * 100) + tempChar.getHealth() + tempChar.getStrenght() + tempChar.getAgility();

        if (series.equals("SF")) {

            if (statTotal >= 3000) {
                this.street1.insertEnd(new Node(tempChar));

            } else if (statTotal >= 2000) {
                this.street2.insertEnd(new Node(tempChar));

            } else {
                this.street3.insertEnd(new Node(tempChar));
            }

        } else {

            if (statTotal >= 3000) {
                this.zelda1.insertEnd(new Node(tempChar));

            } else if (statTotal >= 2000) {
                this.zelda2.insertEnd(new Node(tempChar));

            } else {
                this.zelda3.insertEnd(new Node(tempChar));
            }
        }

        this.idCount++;

    }


}
