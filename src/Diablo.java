import javax.swing.*;

public class Diablo extends Fighter
{
   private final String CLASS_PATH = "/gamedata/bosses/boss[@id = '0']";
   private GameDataParser myParser;

   public Diablo()
   {
      myParser = new GameDataParser(CLASS_PATH);
      setBaseHealth(myParser.getBaseHealth());
   }

   public String getName() { return "Diablo"; }
   protected ImageIcon getIcon() { return (myParser.getIcon()); }
}
