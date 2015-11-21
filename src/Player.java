public abstract class Player extends Fighter
{
   public Player()
   {}

   abstract int ability1();
   abstract int ability2();
   abstract int ability3();
   abstract int ability4();

   abstract String getName();
   abstract String getInfo();
   abstract String getDescription();
}
