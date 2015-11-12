public class Fighter
{
    protected int baseHealth;
    protected int currentHealth = baseHealth;
    protected int basePower; //health and power are the only two stats for now, will add more later
    protected int currentPower = basePower;

    public Fighter()
    {}

    //changes fighter's base health based on amount parameter, used during level up
    protected void changeBaseHealth(int amount)
    {
       baseHealth += amount;
       currentHealth = baseHealth;
    }
    //changes fighter's current health, i.e. from damage, heals, etc.
    protected void changeCurrentHealth(int amount)
    {
        currentHealth += amount;
    }

    //changes fighter's base power based on amount parameter, used during level down
    protected void changeBasePower(int amount)
    {
       basePower += amount;
       currentPower = basePower;
    }
    //changes fighter's current power, i.e from buffs, debuffs, etc.
    protected void changeCurrentPower(int amount)
    {
        currentPower += amount;
    }
}
