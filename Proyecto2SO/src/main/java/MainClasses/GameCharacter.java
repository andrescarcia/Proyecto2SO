/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MainClasses;
import java.util.Random;
/**
 *
 * @author Sebastián
 */
public class GameCharacter {
    private String name;
    private int health;
    private int strenght;
    private int agility;
    private int skill;
    private String[] moveset;
    private int id;
    public int queueWait;
    
    
    public GameCharacter(String series, int id){
        this.id = id;
        this.queueWait = 0;
        selectName(series);
        this.moveset = selectMoves();
        generateStats();
        
    }

    public void incrementQueueWait() {
        this.queueWait++;
    }

    public void resetQueueWait() {
        this.queueWait = 0;
    }

    public void selectName(String series){
        String[] sfNames = {"Ryu", "Ken", "Akuma", "Chun-Li", "Zangief", "Dee Jay", "Luke", "JP", "Marisa", "Jamie", "E. Honda", "Juri", "Kimberly", "Blanka", "Guile", "Dhalsim", "Rashid", "Cammy", "Sagat", "M. Bison"};
        String[] sfRare = {"Kazuya", "Dr Doom", "Zero", "Vergil", "Jonesy", "Dos Akumas", "Goku"};
        
        String[] zeldaNames = {"Link", "Zelda", "Ganondorf", "Riju", "Yunobo", "Sidon", "Tulin", "Daruk", "Mipha", "Urbosa", "Revali", "Maestro Kog", "Impa", "Phantom Ganon", "Rey Rhoam", "Hestu", "Calamity Ganon", "Guardian", "Sooga", "Teba"};
        String[] zeldaRare = {"Link Moto", "Pikachu", "Mario verde", "Sonic", "Ganondorf Suavemente", "Kamen Rider Geats", "Moto Voladora"};
                
        Random random = new Random();
        int nameType = random.nextInt(101);        
        
        if(nameType > 70){
            
            int nameSelect = random.nextInt(7);
            
            if (series.equals("SF")){
                this.name = sfRare[nameSelect];
                
            }else{
                this.name = zeldaRare[nameSelect];
            }
            
        }else{
            
            int nameSelect = random.nextInt(20);
            
            if(series.equals("SF")){
                this.name = sfNames[nameSelect];
                
            }else{
                this.name = zeldaNames[nameSelect];
            }
            
            
        }
    }
    
    public void generateStats(){
        Random random = new Random();
        int qualityHp = random.nextInt(101);
        int qualityStr = random.nextInt(101);
        int qualitySkl = random.nextInt(101);
        int qualitySpd = random.nextInt(101);
        
        if(qualitySkl <= 60){
            this.skill = random.nextInt(10 - 5) + 5;
        }else{
            this.skill = random.nextInt(6 - 1) + 1;
        }
        
        
        if(qualityHp <= 70){
            this.health = random.nextInt(3000 - 1500) + 1500;
        }else{
            this.health = random.nextInt(2000 - 1000) + 1000;
        }
        
        
        if(qualityStr <= 50){
            this.strenght = random.nextInt(300 - 120) + 120;
        }else{
            this.strenght = random.nextInt(210 - 50) + 50;
        }
        
        if(qualitySpd <= 40){
            this.agility = random.nextInt(300 - 120) + 120;
        }else{
            this.agility = random.nextInt(180 - 20) + 20;
        }
        
        /**
         * Casos especiales de estadisticas
         */
        
        switch(this.name){
            
            
            /**
             * Ryu siempre tendra fuerza y habilidad de calidad
             */
            case "Ryu":
                this.strenght = random.nextInt(300 - 120) + 120;
                this.skill = random.nextInt(10 - 7) + 7;  
                break;
                
            /**
             * Akuma tendra fuerza de calidad, habilidad alta unica pero igualmente variante
             * Y vida pobre, que puede entrar al rango regular o menor a este
             */    
            case "Akuma":
                this.strenght = random.nextInt(300 - 120) + 120;
                this.skill = random.nextInt(10 - 8) + 8;  
                this.health = random.nextInt(1500 - 800) + 800;
                break;
                
            /**
             * Zangief tendra fuerza de calidad y un rango de calidad de vida mayor a lo normal
             */    
                
            case "Zangief":
               this.strenght = random.nextInt(300 - 200) + 200;
               this.health = random.nextInt(3000 - 2000) + 2000; 
               break;
              
               
            /**
             * Chun-Li siempre tendra habilidad y agilidad de calidad
             */
            case "Chun-Li":
                this.skill = random.nextInt(10 - 5) + 5; 
                this.agility = random.nextInt(300 - 120) + 120;
                break;
            
           /**
            * Cammy siempre tendra agilidad y fuerza de calidad
            */     
                
            case "Cammy":
                this.agility = random.nextInt(300 - 120) + 120;
                this.strenght = random.nextInt(300 - 120) + 120;
                break;
            
            /**
             * Link siempre tendra agilidad de calidad y habilidades de calidad en un rango mayor
             * de lo normal
             */    
            case "Link":
                this.skill = random.nextInt(10 - 7) + 7;
                this.agility = random.nextInt(320 - 120) + 120;
                break;
                
            /**
             * Zelda siempre tendra fuerza de calidad (la magia), y habilidad de calidad en un rango
             * mayor de lo normal
             */
            case "Zelda":
                this.skill = random.nextInt(10 - 7) + 7;
                this.strenght = random.nextInt(300 - 120) + 120;
                break;
                
            /**
             * Ganondorf tendra vida y habilidades de calidad, y fuerza de calidad mayor de lo normal
             */
            case "Ganondorf":
                this.health = random.nextInt(3000 - 1500) + 1500;
                this.skill = random.nextInt(10 - 5) + 5;
                this.strenght = random.nextInt(300 - 200) + 200;
                break;
            
            /**
             * Calamity Ganon siempre tendra una fuerza extremadamente alta
             */
            case "Calamity Ganon":
                this.strenght = random.nextInt(300 - 250) + 250;
                break;
           
            /**
             * El maestro kog siempre tendra una agilidad extremadamente alta, pero como esta gordo
             * su agilidad es extremadamente pobre
             */
            case "Maestro Kog":
                this.skill = random.nextInt(10 - 9) + 9;
                this.agility = random.nextInt(80 - 20) + 20;
                break;
                
           /**
            * 
            * Zero tendra menores limites inferiores de vida, habilidad alta y un mayor rango
            * de velocidad, aun intentan nerfearlo
            */
            case "Zero":
                this.health = random.nextInt(1800 - 800) + 800; 
                this.skill = random.nextInt(10 - 7) + 7;
                this.agility = random.nextInt(300 - 200) + 200;
                break;
                
                
            /**
             * Vergil esta medio roto en la meta debido a sus mayores limites inferiores de 
             * fuerza de calidad, su habilidad de calidad garantizada y su mayor limite de velocidad
             * aunque tiene menos vida de lo normal
             */
            case "Vergil":
                this.strenght = random.nextInt(300 - 170) + 170;
                this.skill = random.nextInt(10 - 5) + 5;
                this.agility = random.nextInt(300 - 120) + 120;
                this.health = random.nextInt(1600 - 900) + 900; 
                break;
                
                
            /**
             * Es Goku
             */    
            case "Goku":
                this.strenght = random.nextInt(300 - 250) + 250;
                this.skill = random.nextInt(10 - 9) + 9;
                this.agility = random.nextInt(300 - 180) + 180;
                this.health = random.nextInt(3000 - 1500) + 1500; 
                break;
                
            /**
             * Link esta en una moto y debido a esa va muy rapido con una agilidad siempre muy alta
             * y la moto aguanta golpes por lo que tiene vida de calidad
             */ 
            case "Link Moto":
                this.agility = random.nextInt(300 - 250) + 250;
                this.health = this.health = random.nextInt(3000 - 1500) + 1500;
                break;
             
            /**
             * Sonic es extremadamente rapido, siempre tendra una agilidad mayor al maximo del
             * resto de los personajes, y tiene habilidad de calidad
             */
            case "Sonic":
                this.agility = 350;
                this.skill = this.skill = random.nextInt(10 - 5) + 5;
            
            /**
             * Pikachu tiene una vida mas concistente que los demas personajes debido a
             * las estadisticas base que tiene un pokemon y una velocidad de calidad
             */
            case "Pikachu":
                this.health = random.nextInt(1200 - 900) + 900;
                this.agility = random.nextInt(300 - 250);
                break;
        }
       

    }
    
    public String[] selectMoves(){
        
        String[] tempMove = {"Ataque", "Proyectil", "Especial"};
        
        switch(this.name) {
            
            case "Ryu":
                tempMove = new String[] {"Hadoken", "Shoryuken", "Shin Shoryuken"};
                break;
                
            case "Ken":
                tempMove = new String[] {"Tatsumaki Senpu-kyaku", "Gorai Axe Kick", "Shinryu Reppa"};
                break;
                
            case "Akuma":
                tempMove = new String[] {"Gohadoken", "Tatsumaki Zankukyaku", "Raging Demon"};
                break;
        
            case "Chun-Li":
                tempMove = new String[] {"Kikoken", "Hundred Lightning Kicks", "Kikosho"};
                break;
                
            case "Zangief":
                tempMove = new String[] {"Double Lariat", "Screw Piledriver", "Bolshoi Storm Buster"};
                break;
                
            case "Luke":
                tempMove = new String[] {"Sand Blast", "Rising Uppercut", "Pale Rider"};
                break;
                
            case "Juri":
                tempMove = new String[] {"Fuhajin", "Shiku-sen", "Kaisen Dankai Raku"};
                break;
                
            case "Cammy":
                tempMove = new String[] {"Spiral Arrow", "Razor's Edge Slicer", "Delta Red Assault"};
                break;
                
            case "Marisa":
                tempMove = new String[] {"Gladius", "Phalanx", "Goddess of the Hunt"};
                break;
                
            case "M. Bison":
                tempMove = new String[] {"Psycho Blast", "Psycho Inferno", "Ultimate Psycho Crusher"};
                break;
                
            case "Link":
                tempMove = new String[] {"Espada Maestra", "Arco", "Flecha ancestral"};
                break;
                
            case "Zelda":
                tempMove = new String[] {"Farore's Wind", "Din's Fire", "Arco de la luz"};
                break;
                
            case "Ganondorf":
                tempMove = new String[] {"Dark Dive", "Flame Choke", "Warlock Punch"};
                break;
                
            case "Daruk":
                tempMove = new String[] {"Machacarrocas", "Piedra", "Pilares de Magma"};
                break;
                
            case "Mipha":
                tempMove = new String[] {"Lanza zora", "Tridente de escamas", "Fuente"};
                break;
                
            case "Urbosa":
                tempMove = new String[] {"cimitarra de la Ira", "Ataque giratorio", "Ira de urbosa"};
                break;
                
            case "Revali":
                tempMove = new String[] {"Arco del águila", "Flecha bomba", "Furia de Revali"};
                break;
                
            case "Maestro Kog":
                tempMove = new String[] {"Espada demoníaca", "Bola de puas", "Carro Zoan"};
                break;
                
            case "Riju":
                tempMove = new String[] {"Cimitarra de la luna", "Casco del trueno", "Rayo"};
                break;
                
            case "Sidon":
                tempMove = new String[] {"Tridente ceremonial", "Doble lanza", "Tiburon Gigante"};
                break;
                
            case "Kazuya":
                tempMove = new String[] {"Flash Punch Combo", "Electric Wind God Fist", "Rage Art"};
                break;
                
            case "Dr Doom":
                tempMove = new String[] {"FOOT DIVE", "HIDDEN MISSILES", "Doom's Time"};
                break;
                
            case "Zero":
                tempMove = new String[] {"Raikousen", "Ryuenjin", "Genmu Zero"};
                break;
                
            case "Vergil":
                tempMove = new String[] {"Judgment Cut", "Rapid Slash", "Devil Trigger"};
                break;
                
            case "Jonesy":
                tempMove = new String[] {"Pico", "Escopeta", "Bailar"};
                break;
                
            case "Dos Akumas":
                tempMove = new String[] {"GohadokenGohadoken", "Tatsumaki ZankukyakuTatsumaki Zankukyaku", "Raging DemonRaging Demon"};
                break;
                
            case "Goku":
                tempMove = new String[] {"Kamehameha", "Kaioken", "Henkidama"};
                break;
                
            case "Link Moto":
                tempMove = new String[] {"Platano", "Caparazon Rojo", "Estrella"};
                break;
                
            case "Pikachu":
                tempMove = new String[] {"Impactrueno", "Tacleada de Volteos", "Catastropika"};
                break;
                
            case "Mario Verde":
                tempMove = new String[] {"Saltar", "Bola de fuego", "Estrella"};
                break;
                
            case "Sonic":
                tempMove = new String[] {"Cyclone Kick", "Spin Slash", "Phantom Rush"};
                break;
                
            case "Ganondorf Suavemente":
                tempMove = new String[] {"Suavemente", "Bailar", "Bailar Suavemente"};
                break;
                
            case "Kamen Rider Geats":
                tempMove = new String[] {"[Magnum]", "Boost", "Boost MK. IX"};
                break;
                
            case "Moto Voladora":
                tempMove = new String[] {"Esta rota", "Lo mas usado", "Resuelve todo"};
                break;
        }
        
        
        return tempMove;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getStrenght() {
        return strenght;
    }

    public void setStrenght(int strenght) {
        this.strenght = strenght;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public int getSkill() {
        return skill;
    }

    public void setSkill(int skill) {
        this.skill = skill;
    }

    public String[] getMoveset() {
        return moveset;
    }

    public void setMoveset(String[] moveset) {
        this.moveset = moveset;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQueueWait() {
        return queueWait;
    }

    public void setQueueWait(int queueWait) {
        this.queueWait = queueWait;
    }
//    @Override
    public String toString() {
        return "" + this.name + ",";
    }

    
    
}
