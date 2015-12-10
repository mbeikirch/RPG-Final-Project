import javax.swing.*;

public class Diablo extends Fighter
{
   private final String CLASS_PATH = "/gamedata/bosses/boss[@id ='0']";
   private GameDataParser myParser;
   private ImageIcon myIcon;

   public Diablo()
   {
      myParser = new GameDataParser(CLASS_PATH);
      setBaseHealth(myParser.getBaseHealth());

      myIcon = myParser.getIcon();
   }

   public String getName() { return "Diablo"; }

   protected ImageIcon getIcon() { return myIcon; }
}