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
- Random events like finding potions and coins, discovering treasures that contain potions and coins, or encountering the villager's shop where you can buy potions and items.
- Turn-based battles with enemies.
- Final boss fight.
- Console-based text interface.
- Save system in the `SaveFile/` folder.

---
# Game Mechanics
This section explains the basic mechanics of the game.

1. **Character Types**
   - You can select from 3 types of characters:
      - **Warrior**: The tanky character, with high HP (200), lower damage (75), and normal speed (4.5). Designed to be a solid tank.
      - **Mage**: The versatile character, with moderate HP (75), good damage (100), and good speed (6.5). Designed for flexible play: fast enough to survive and deal consistent damage.
      - **Rogue**: The fastest character, with low HP (50), high damage (150), and very high speed (8). Designed to quickly defeat enemies, but can be defeated in one hit if attacked.

2. **Battle** 
   - Battles are turn-based and feature different types of enemies. This will be explained in more detail in the next paragraph.

3. **Potions**:
      - **Healing Potion:** Restores 100 HP.  
      - **Damage Potion:** Adds 50 attack points. 
      - There is no attack speed potion, keeping turn order fair and predictable.
      - You can randomly find potions as you advance in the adventure.
      - Potions can also be purchased in the Villager Shop.
      - Potions can also appear in different chests.

4. **Coins**:
      - There are 4 types of coins: the **Coin** (gives 5 coins), the **Bronze Coin** (10 coins), the **Silver Coin** (25 coins), and the **Golden Coin** (50 coins).
      - **Coins** are useful for buying items and potions in the Villager Shop.
      - You also earn 5 coins when you defeat an enemy.
      - Coins can also appear in different chests.

5. **Chests**:
      - There are 4 types of **Chests**:
         - **Normal Chest**: 1 healing potion and 5 coins.
         - **Silver Chest**: 2 healing potions, 1 damage potion, and 15 coins.
         - **Golden Chest**: 2 healing potions, 2 damage potions, and 30 coins.
         - **Platinum Chest**: 4 healing potions, 4 damage potions, and 75 coins.

   **Note**
      - Each findable coin or chest has different drop chances. High-value items, like golden coins or platinum chests, are harder to find than normal chests or regular coins, which are the easiest to obtain.


6. **Villager Store / Items:**
   - In the **Villager Store**, you can choose to buy potions or **Items**.
   - You can always buy a potion if you have the required coins (15).
   - The **Shop’s Items** are unique and special; you can only buy the same item once, and it will upgrade your stats.
   - There are 3 types of shops:
      - **Bronze Shop**: appears when you have between 0 and 60 coins.
      - **Silver Shop**: appears when you have between 60 and 200 coins.
      - **Golden Shop**: appears when you have more than 200 coins.
   - Each shop always has healing potions and damage increase potions. The difference is that the other items in higher-tier shops are better and more expensive.
   - If you don’t have enough coins, you will need to leave the shop.

---

# Battle Mechanics
First of all, you should know that the game is designed to let players discover different events and is not very difficult, as it is just a technical demo leading up to the final boss.
The final boss is a bit more challenging, because it is the final fight.

About the battle, the game uses a turn-based combat system where the attack speed determines which character acts first. Each battle follows these rules:

1. **Turn Order:**  
   - The character with the higher attack speed hits first.
   - After the first attack, turns alternate between the player and the enemy. 
   - If both have the same attack speed, the first attacker is chosen randomly in each turn.

2. **Player Actions:**  
   - On your turn, you can choose to **attack**, **look your potions**, **use a potion**, **run away**, or **look at your stats** if you don’t remember them.
   - When you choose to **run away** during a battle, you have a **65% chance to escape successfully**, or a **35% chance to fail and lose your turn**.
   - Look your potions don't consumes a turn.
   - Using a potion consumes your turn, so you cannot attack in the same round, and also if you don't have a potion, it consumes a turn, because you can previously look if you have one.  
   - Looking at your stats doesn't consume a turn.
   - Strategic use of potions is key to surviving tougher enemies and bosses.

3. **Enemy Progression:**  
   - Initially, battles are against normal enemies.  
   - After defeating **10 normal enemies**, subsequent enemies become **Minibosses**.  
   - After defeating **20 total enemies**, the **Final Boss** appears.
   - To beat the different **Minibosses** and the **Final Boss**, you will need to buy items and collect or buy potions.

4. **Winning and Losing:**  
   - Winning a battle allows you to advance to the next enemy and earn 5 coins.
   - Losing a battle ends the game, so managing your health and potion usage is crucial.  
   - The **Final Boss** is significantly stronger, making the use of potions and strategy essential for victory.

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