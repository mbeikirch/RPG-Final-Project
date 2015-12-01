import java.io.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import org.w3c.dom.*;
import org.xml.sax.*;

public class GameDataParser
{
   private DocumentBuilder builder;
   private XPath path;

   public GameDataParser()
   {
      try
      {
         DocumentBuilderFactory dbFactory =
            DocumentBuilderFactory.newInstance();
         
         builder = dbFactory.newDocumentBuilder();
         XPathFactory xpFactory = XPathFactory.newInstance();
         
         path = xpFactory.newXPath();   
      }
      catch(ParserConfigurationException pce){pce.printStackTrace();}
   }
   
   public void parse(String fileName)
   {
      try
      {
         Document doc = builder.parse(new File(fileName));
      
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
            
            
         }    
      
      }
      catch(Exception ex){ex.printStackTrace();}
   }
   
   public static void main(String[] args)
   {
      GameDataParser sp = new GameDataParser();
      sp.parse("GameData new.xml");
   }
}