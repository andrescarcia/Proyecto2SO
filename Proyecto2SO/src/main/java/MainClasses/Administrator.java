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

    public Administrator(AI pro, Semaphore m) {
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
    public void run() {
        Random random = new Random();
        int outcome;

        try {
            while (true) {

                mutex.acquire(1);

                if (this.processor.getRoundCount() >= 2) {

                    outcome = random.nextInt(100);

                    if (outcome <= 80) {
                        addCharacter("SF");
                        addCharacter("ZE");

                        checkRQueue(random);
                    }


                    this.processor.setRoundCount(0);
                }

                switch (this.processor.getCurrentState()) {

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
                //countCharacter();// recorrer las colas y aumentar el contador de cada personaje.
                //this.processor.setStreetCharacter(GameCharacter.class.cast(this.street1.delFirst().getData()));
                // Incrementar la espera de los personajes en cada lista
                incrementQueueWaitForList(street1, null, streetR); // Para los de street nivel 1
                incrementQueueWaitForList(street2, street3, null);    // Para los de street nivel 2
                incrementQueueWaitForList(street3, streetR, null);    // Para los de street nivel 3

                incrementQueueWaitForList(zelda1, null, zeldaR);    // Para los de zelda nivel 1
                incrementQueueWaitForList(zelda2, zelda3, null);      // Para los de zelda nivel 2
                incrementQueueWaitForList(zelda3, zeldaR, null);      // Para los de zelda nivel 3
                
                incrementQueueWaitForList(streetR, street3, null); // Para los de street refuerzo
                incrementQueueWaitForList(zeldaR, zelda3, null);   // Para los de zelda refuerzo


                mutex.release();
                sleep(1000);

            }


        } catch (InterruptedException ex) {
            Logger.getLogger(Administrator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void incrementQueueWaitForList(LinkList currentList, LinkList nextList, LinkList reinforcementList) {
        Node currentNode = currentList.getlFirst();
        Node previousNode = null;
        while (currentNode != null) {
            GameCharacter character = (GameCharacter) currentNode.getData();
            character.incrementQueueWait();
            // Verificar si el contador ha alcanzado 8 y actuar según la cola actual
            if (character.getQueueWait() >= 8) {
                if (currentList == this.streetR || currentList == this.zeldaR) {
                    // Mover a la cola de nivel 3 si está en la cola de refuerzo y ha alcanzado la espera de 8
                    moveToLevelThree(character, currentList, previousNode, currentNode);
                } else if (currentList == this.street3 || currentList == this.zelda3) {
                    // Mover a la cola de refuerzo si es prioridad 3
                    moveToNextPriority(character, currentList, reinforcementList, previousNode, currentNode);
                } else if (nextList != null) {
                    // Mover a la siguiente cola de prioridad si no es la cola de refuerzo y hay una siguiente cola
                    moveToNextPriority(character, currentList, nextList, previousNode, currentNode);
                } else {
                    // Reiniciar el contador si está en la cola de prioridad 2 y no hay cola de refuerzo
                    character.resetQueueWait();
                }
                // Se actualiza previousNode solo si el personaje no fue movido
                if (currentList.contains(character)) {
                    previousNode = currentNode;
                }
                currentNode = currentNode.getpNext();
            } else {
                previousNode = currentNode;
                currentNode = currentNode.getpNext();
            }
        }
    }

    private void moveToLevelThree(GameCharacter character, LinkList currentList, Node previousNode, Node currentNode) {
        // Decide to which level 3 list the character should be moved
        LinkList levelThreeList = currentList == this.streetR ? this.street3 : this.zelda3;
        moveToNextPriority(character, currentList, levelThreeList, previousNode, currentNode);
    }
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
        // Tomar los personajes que empataron de la instancia processor
        GameCharacter streetFighter = this.processor.getStreetCharacter();
        GameCharacter zeldaChar = this.processor.getZeldaCharacter();

        // Crear nodos con estos personajes
        Node streetNode = new Node(streetFighter);
        Node zeldaNode = new Node(zeldaChar);

        // Volver a poner ambos personajes en la última posición de sus respectivas colas de nivel 1
        if (streetFighter != null) {
            this.street1.insertEnd(streetNode);
        }
        if (zeldaChar != null) {
            this.zelda1.insertEnd(zeldaNode);
        }

        // Reiniciar los personajes en la instancia processor
        this.processor.setStreetCharacter(null);
        this.processor.setZeldaCharacter(null);
    }

    public void cantFight() {
        // Tomar los personajes que no pueden pelear de la instancia processor
        GameCharacter streetFighter = this.processor.getStreetCharacter();
        GameCharacter zeldaChar = this.processor.getZeldaCharacter();

        // Crear nodos con estos personajes
        Node streetNode = new Node(streetFighter);
        Node zeldaNode = new Node(zeldaChar);

        // Poner ambos personajes en la cola de refuerzo
        if (streetFighter != null) {
            this.streetR.insertEnd(streetNode);
        }
        if (zeldaChar != null) {
            this.zeldaR.insertEnd(zeldaNode);
        }

        // Reiniciar los personajes en la instancia processor
        this.processor.setStreetCharacter(null);
        this.processor.setZeldaCharacter(null);
    }

    public void checkRQueue(Random random) {
        if (this.streetR.getlSize() != 0 && this.zeldaR.getlSize() != 0) {

            int outcome = random.nextInt(100);

            if (outcome <= 40) {
                this.street1.insertEnd(this.streetR.delFirst());
                this.zelda1.insertEnd(this.zeldaR.delFirst());
            }

        }

    }

    public void passStreet() {

        if (this.street1.isEmpty()) {

            if (this.street2.isEmpty()) {

                if (this.street3.isEmpty()) {

                    this.processor.setStreetCharacter(null);

                } else {
                    this.processor.setStreetCharacter(GameCharacter.class.cast(this.street2.delFirst().getData()));
                }

            } else {
                this.processor.setStreetCharacter(GameCharacter.class.cast(this.street2.delFirst().getData()));
            }

        } else {
            this.processor.setStreetCharacter(GameCharacter.class.cast(this.street1.delFirst().getData()));
        }

    }

    public void passZelda() {

        if (this.zelda1.isEmpty()) {

            if (this.zelda2.isEmpty()) {
                this.processor.setZeldaCharacter(GameCharacter.class.cast(this.zelda3.delFirst().getData()));

            } else {

                if (this.zelda3.isEmpty()) {

                    this.processor.setZeldaCharacter(null);

                } else {
                    this.processor.setZeldaCharacter(GameCharacter.class.cast(this.zelda2.delFirst().getData()));
                }
            }

        } else {
            this.processor.setZeldaCharacter(GameCharacter.class.cast(this.zelda1.delFirst().getData()));
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
