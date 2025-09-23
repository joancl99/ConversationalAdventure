# Conversational Adventure
This conversational adventure is a simple console-based game where the player faces random events like finding treasures or potions.  
The main feature of the game is encountering enemies to battle, and after defeating them, the player can advance to face the final boss.  
Developed in Java, this project was created to practice object-oriented programming, basic game logic, and control flow structures.

---

# My Learning Goals
- Practice object-oriented programming (classes, objects, inheritance).
- Work with game loops and control structures.
- Use Java collections and simple algorithms.
- Organize a small project from scratch.
- Manage version control with Git/GitHub.

---

# Features
- Random events like treasures, villagers, and items.
- Turn-based battles with enemies.
- Final boss fight.
- Console-based text interface.
- Save system in the `SaveFile/` folder.

---

# Battle Mechanics
First of all, you should know that the game is designed to be easy, since it is just a technical demo to reach the final boss.  
The final boss is a bit more challenging, because it is the final fight.

About the battle, the game uses a turn-based combat system where the attack speed determines which character acts first. Each battle follows these rules:

1. **Turn Order:**  
   - The character with higher speed attacks first.  
   - After the first attack, turns alternate between the player and the enemy.

2. **Player Actions:**  
   - On your turn, you can choose to **attack**, **use a potion**, **run away**, or **look at your stats** if you don’t remember them.
   - When you choose to **run away** during a battle, you have a **65% chance to escape successfully**, or a **35% chance to fail and lose your turn**.
   - Using a potion consumes your turn, so you cannot attack in the same round.  
   - Looking at your stats doesn't consume a turn.
   - Strategic use of potions is key to surviving tougher enemies and bosses.

3. **Potion Effects:**  
   - **Healing Potion:** Restores 100 HP.  
   - **Damage Potion:** Adds 50 attack points for the current turn.  
   - There is no attack speed potion, keeping turn order fair and predictable.

4. **Enemy Progression:**  
   - Initially, battles are against normal enemies.  
   - After defeating **5 normal enemies**, subsequent enemies become **minibosses**.  
   - After defeating **10 total enemies**, the **final boss** appears.  

5. **Winning and Losing:**  
   - Winning a battle allows you to advance and face the next enemy.  
   - Losing a battle ends the game, so managing your health and potion usage is crucial.  
   - The final boss is significantly stronger, making the use of potions and strategy essential for victory.

---

# Example of the Starting Gameplay
Welcome to this Conversational Adventure:

Main Menu:    

1. Start Game.
2. Reset Save.
3. Exit.

---

# How to Run the Game
## Option 1: Compile and run from source
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/ConversationalAdventure.git

2. Navigate into the project folder:
    cd ConversationalAdventure

3. Compile the project:
    javac src/*.java

4. Run the game:
    java src.Main

### Option 2: Run the executable JAR
1. Make sure the ConversationalAdventure.jar file exists in the project folder.

2. Run the game with:
    java -jar ConversationalAdventure.jar