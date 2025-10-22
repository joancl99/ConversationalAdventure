import java.util.Random;

public class GameLore 
{
    private Random random = new Random();

    public void showLore() 
    {
        int randomOption = random.nextInt(8);

        switch (randomOption) 
        {
            case 0:
                System.out.println("\nChatty Frog: Well, well… a human! Don't tell me you came here to steal my shiny rocks!\n" +
                                   "Chatty Frog: I must warn you… the forest beyond is full of sneaky creatures. Some are friendly, some… less so.\n" +
                                   "Chatty Frog: You look brave… or foolish. Sometimes I can't tell the difference.\n" +
                                   "Chatty Frog: Ah! That's a rare herb you're carrying. Be careful, it might taste like fire if misused.\n" +
                                   "Chatty Frog: I can see magic in your eyes. Don't let it go to waste!\n" +
                                   "Chatty Frog: The mountains to the east hide more than just caves… I've seen shadows moving there.\n" +
                                   "Chatty Frog: I like humans with courage. They are more interesting than sheep, anyway.\n" +
                                   "Chatty Frog: If you need advice, listen carefully… not everything I say is true, but all of it is entertaining.\n" +
                                   "Chatty Frog: I once tried to make a potion using moonlight and snail shells… disastrous. Don't ask.\n" +
                                   "Chatty Frog: The rivers here can be tricky. Swim only where the stones glow.\n" +
                                   "Chatty Frog: Sometimes I pretend to sleep just to see who dares walk past me.\n" +
                                   "Chatty Frog: Beware the old ruins. I hear whispers at night, and not all of them are friendly.\n" +
                                   "Chatty Frog: If you find a glowing feather, take it. But don't touch the claws… they bite!\n" +
                                   "Chatty Frog: I love riddles. Answer one correctly and I might show you a hidden path.\n" +
                                   "Chatty Frog: Humans talk too much. If you want to survive, sometimes silence is the best shield.");
                break;
            case 1:
                System.out.println("\nFarmer: It looks like there are so many monsters around here lately. Be careful on your travels!\n" +
                                   "Farmer: The harvest has been poor this year... I fear it's not just bad weather.\n" +
                                   "Farmer: If you see any strange footprints, mark them. I fear something is hunting our livestock.\n" +
                                   "Farmer: The crops have been failing... maybe it's the magic in the air, or maybe someone is cursed.\n" +
                                   "Farmer: I heard the forest to the east is haunted. Few who go there return.\n" +
                                   "Farmer: My goat went missing last night. I swear I heard growls near the barn.\n" +
                                   "Farmer: If you're strong, maybe you can help me protect the farm from the creatures.\n" +
                                   "Farmer: Beware of the river at night. Some say monsters lurk beneath the water.");
                break;
            case 2:
                System.out.println("\nMerchant: Greetings, traveler! Are you looking for potions or weapons today?\n" +
                                   "Merchant: I hear the northern woods are dangerous. Monsters roam freely there.\n" +
                                   "Merchant: My prices are fair, but I only trade with those who prove themselves.\n" +
                                   "Merchant: Rare ingredients are harder to find since the monsters appeared.\n" +
                                   "Merchant: A brave adventurer might fetch a handsome price for dragon scales.\n" +
                                   "Merchant: Keep an eye on your purse. Bandits follow travelers near the city gates.\n" +
                                   "Merchant: I've heard rumors of a hidden cave filled with treasure, but few dare enter.\n" +
                                   "Merchant: Come back often. New wares arrive every week… if they survive the journey.\n" +
                                   "Merchant: Magic scrolls are my specialty, but they are costly and fragile.\n" +
                                   "Merchant: If you hear someone calling your name at night, don't answer. It's probably a trick.");
                break;
            case 3:
                System.out.println("\nTraveler: I've been on the road for weeks. Monsters have made the path treacherous.\n" +
                                   "Traveler: If you want to survive, stick to the high roads during the day.\n" +
                                   "Traveler: Beware the abandoned village to the west. It's said to be cursed.\n" +
                                   "Traveler: I've seen creatures with eyes like burning coals. Avoid them at all costs.\n" +
                                   "Traveler: A companion makes survival easier. Don't travel alone if you can help it.\n" +
                                   "Traveler: Strange lights appear in the forest sometimes… and vanish without a trace.\n" +
                                   "Traveler: I found an old ruin yesterday. Something inside whispered my name.\n" +
                                   "Traveler: Watch out for bandits as well. They hide behind the trees, waiting for the careless.\n" +
                                   "Traveler: Some say the mountains are home to giants, but I've never seen one… not yet.\n" +
                                   "Traveler: Keep your weapons close. Even a small creature can be deadly if surprised.");
                break;
            case 4:
                System.out.println("\nCity Guard: Halt! What business do you have in the city at this hour?\n" +
                                   "Guard: Monsters have been spotted near the walls. Keep your sword ready.\n" +
                                   "Guard: The roads are dangerous. Travel in groups if you value your life.\n" +
                                   "Guard: Report any suspicious activity immediately. The safety of the city depends on it.\n" +
                                   "Guard: Bandits often use the forest paths to ambush travelers.\n" +
                                   "Guard: If you see a child wandering alone, bring them to the town hall.\n" +
                                   "Guard: Strange shadows have been spotted near the gates. Don't approach them.\n" +
                                   "Guard: Keep your armor in good condition. You never know when trouble will strike.\n" +
                                   "Guard: I've heard rumors of spies in the city. Trust no one completely.\n" +
                                   "Guard: Night watch is the hardest duty. Many never see the sunrise.");
                break;
            case 5:
                System.out.println("\nSage: The balance of magic is shifting. Do not meddle recklessly.\n" +
                                   "Sage: Seek the old library. Knowledge is your greatest weapon against the darkness.\n" +
                                   "Sage: Many heroes have fallen because of pride. Learn patience and observe your enemy.\n" +
                                   "Sage: The spirits of the forest speak to those who listen carefully.\n" +
                                   "Sage: Do not trust every charm or talisman. Some are cursed by dark magic.\n" +
                                   "Sage: Time flows differently in the enchanted valleys. Be cautious of your steps.\n" +
                                   "Sage: I have studied the stars. Their patterns reveal hidden dangers.\n" +
                                   "Sage: A hero's true strength lies not in their sword, but in their mind.\n" +
                                   "Sage: Beware of illusions. What seems real may be a trap from another plane.\n" +
                                   "Sage: If you find an ancient relic, handle it with care. It may carry both blessing and curse.");
                break;
            case 6:
                System.out.println("\nYou found a Note: Insert lore here...");
                break;
            case 7:
                System.out.println("\nYou found a Diary: Insert lore here...");
                break;
            default:
                System.out.println("\nNo lore available at this time.");
                break;
        }
    }
}