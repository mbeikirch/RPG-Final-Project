import java.io.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import org.w3c.dom.*;

/**
 * I can push again.
 */
public class GameDataParser implements Serializable
{
   private DocumentBuilder builder;
   private XPath path;
   private Document doc;
   private String classPath;
   private String FILE = "GameData.xml";

   public GameDataParser(String _classPath)
   {
      classPath = _classPath;

      try
      {
         DocumentBuilderFactory dbFactory =
            DocumentBuilderFactory.newInstance();
         
         builder = dbFactory.newDocumentBuilder();
         XPathFactory xpFactory = XPathFactory.newInstance();
         
         path = xpFactory.newXPath();

         parse();
      }
      catch(ParserConfigurationException pce){ pce.printStackTrace(); }
   }
   
   public void parse()
   {
      try
      {
         doc = builder.parse(new File(FILE));

         /*
         int playersCount = 
            Integer.parseInt(path.evaluate("count(/gamedata/players/player)",doc));
            
         System.out.println("*** player Listing ***");
         for(int i=1; i <= playersCount; i++)
         {
            String playerName = path.evaluate("/gamedata/players/player[" + i + "]/name",doc);
            int playerPower = 
               Integer.parseInt(path.evaluate("/gamedata/players/player[" + i + "]/power",doc));            
            int playerHealth = 
               Integer.parseInt(path.evaluate("/gamedata/players/player[" + i + "]/health",doc)); 
            
            //String playerAbility = path.evaluate("/gamedata/players/player/abilities[" + i + "]/abilityname",doc);

            System.out.printf("Name: %-10s   Power: %d   health: %d  %n", playerName, playerPower, playerHealth);               
            
            
         }
         int bossesCount = 
            Integer.parseInt(path.evaluate("count(/gamedata/bosses/boss)",doc));
            
         System.out.println("*** Boss Listing ***");
         for(int i=1; i <= bossesCount; i++)
         {
            String bossName = path.evaluate("/gamedata/bosses/boss[" + i + "]/name",doc);
            int bossPower = 
               Integer.parseInt(path.evaluate("/gamedata/bosses/boss[" + i + "]/power",doc));            
            int bossHealth = 
               Integer.parseInt(path.evaluate("/gamedata/bosses/boss[" + i + "]/health",doc)); 

            System.out.printf("Name: %-10s     Power: %d    health: %d%n", bossName, bossPower, bossHealth);
         }*/
      }
      catch(Exception ex){ ex.printStackTrace(); }
   }

   //TODO: 12/4/2015 getBaseHealth(); getAbility(); getAbilityDescription();
   public int getAbilityDamage(int num)
   {
      try
      {
         return Integer.parseInt(path.evaluate(classPath + "/abilities/ability["+num+"]/damagevalue",doc));
      }
      catch(XPathExpressionException xpe){ xpe.printStackTrace(); }

      return -1;
   }

   public String getAbilityDescription(int num)
   {
      try
      {
         return path.evaluate(classPath + "/abilities/ability["+num+"]/description",doc);
      }
      catch(XPathExpressionException xpe){ xpe.printStackTrace(); }

      return "nope";
   }

   public String getAbilityName(int num)
   {
      try
      {
         return path.evaluate(classPath + "/abilities/ability["+num+"]/abilityname",doc);
      }
      catch(XPathExpressionException xpe){ xpe.printStackTrace(); }

      return "nope";
   }

   public int getBaseHealth()
   {
      try
      {
         return Integer.parseInt(path.evaluate(classPath + "/health", doc));
      }
      catch(XPathExpressionException xpe){ xpe.printStackTrace(); }

      return -1;
   }
}