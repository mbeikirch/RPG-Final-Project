import javax.swing.*;
import java.io.Serializable;

public class Fighter implements Serializable
{
   protected int baseHealth, currentHealth;
   protected String name;
   private static final long serialVersionUID = 1L;
   private boolean isAlive = true;

   public Fighter() { }

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

   //sets a fighters health to an exact number that is input
   protected void setCurrentHealth(int amount) { currentHealth = amount; }

   protected int getCurrentHealth()
   {
      return currentHealth;
   }

   protected boolean isFighterAlive()
   {
      return isAlive;
   }

   protected void setFighterAlive(boolean _isAlive)
   {
      isAlive = _isAlive;
   }

   protected int ability1() { return -1; }
   protected int ability2() { return -1; }
   protected int ability3() { return -1; }

   protected String getAbilityName(int num){ return ""; }
   protected String getAbilityDescription(int num){ return ""; }
   protected ImageIcon getIcon(){ return null; }

   protected String getClassName() { return "";  }
   protected void setName(String _name){ }
   protected String getName(){ return ""; }
}