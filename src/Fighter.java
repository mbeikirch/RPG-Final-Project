public class Fighter
{
    protected int baseHealth, currentHealth;
    protected String name;

    public Fighter(String _name)
    {
        name = _name;
    }

    //changes fighter's base health
    protected void setBaseHealth(int amount)
    {
        baseHealth = amount;
        currentHealth = baseHealth;
    }
    protected int getBaseHealth()
    {
        return baseHealth;
    }

    //changes fighter's current health, i.e. from damage, heals, etc.
    protected void changeCurrentHealth(int amount)
    {
        currentHealth += amount;
    }

    protected int getCurrentHealth()
    {
        return currentHealth;
    }

    protected int ability1() { return -1; }
    protected int ability2() { return -1; }
    protected int ability3() { return -1; }
    protected int ability4() { return -1; }

    protected String getAbilityName(int num){ return ""; }

    protected String getAbilityDescription(int num){ return ""; }

    protected String getName(){ return ""; }
}