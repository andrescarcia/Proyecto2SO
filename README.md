# Proyecto 2: Game of the Year 2023

## Problemática

Tras las simulaciones realizadas en las últimas semanas, las empresas Square Enix y Bethesda decidieron retirar la idea de invertir en el país, dejando vía libre a **Zelda: Tears of the Kingdom** por parte de Nintendo y a **Street Fighter 6** por parte de Capcom como candidatos favoritos al GOTY 2023.

Directores de ambas empresas se reunieron para un proyecto que acabaría la rivalidad entre ambos títulos, y dejaría claro al ganador del GOTY 2023, un simulador de batallas entre los personajes de Zelda y Street Fighter. Dicho proyecto se le fue confiado a los desarrolladores de la simulación anterior, dándole más libertades creativas debido a la gran calidad de trabajo demostrado.

## Lineamientos generales

### 1. Inteligencia Artificial (Procesador):

- Su función es recibir dos personajes por ronda, uno de Zelda y otro de Street Fighter, procesar sus características y determinar el resultado de la batalla entre ambos.
- Dicho resultado le toma inicialmente a la IA 10 segundos en procesar y puede ser cualquiera de los siguientes:
  - **a.** Existe un ganador del combate
  - **b.** Ocurrió un empate en el combate
  - **c.** No puede llevarse a cabo el combate

### 2. Personajes (procesos):

- Cada personaje tiene un ID entero único asociado y alguno de los tres niveles de prioridad.
  - **a.** Nivel 1: Prioridad alta
  - **b.** Nivel 2: Prioridad media
  - **c.** Nivel 3: Prioridad baja

### 3. Administrador (Sistema Operativo):

- Se encarga de actualizar las colas del sistema y de dictarle a la Inteligencia Artificial cuál de los siguientes personajes se deben enfrentar.

### 4. Colas de prioridad:

- Cada empresa tendrá 4 colas de prioridad, las de nivel 1, 2 y 3, y la cola de refuerzo.

## Requerimientos funcionales

- La simulación opera de la siguiente manera: Comienza a ejecutarse y se deben crear 10 personajes para cada empresa.
- Se debe mostrar en pantalla en todo momento las colas que mantiene el Administrador, con los ID de los personajes, cada uno en su respectiva cola en el orden en el cual estén.
- Se debe mostrar a través de la interfaz lo que está haciendo en todo momento la inteligencia artificial: esperando, decidiendo o anunciado el resultado.
- Se solicita implementar un control de tiempos, para que el usuario pueda alterar la velocidad con la que trabaja la inteligencia artificial.
- Se pide mostrar a través de la interfaz un marcador que indique en tiempo real cuántos combates ha ganado Zelda y cuántos Street Fighter.
