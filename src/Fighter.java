public class Fighter
{
    protected int baseHealth, basePower, currentHealth, currentPower;

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

    protected int getCurrentHealth(){ return currentHealth; }

    //changes fighter's base power based on amount parameter, used during level up
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

    protected int ability1() { return -1; }
    protected int ability2() { return -1; }
    protected int ability3() { return -1; }
    protected int ability4() { return -1; }

    protected String getName(){ return ""; }
    protected String getInfo(){ return ""; }
    protected String getDescription(){ return ""; }
}
