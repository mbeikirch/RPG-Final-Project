
import javax.swing.*;
public class Balanced extends Fighter{

        private final String CLASS_PATH = "/gamedata/bosses/boss[@id = '2']";
        private GameDataParser myParser;

        public Balanced()
        {
            myParser = new GameDataParser(CLASS_PATH);
            setBaseHealth(myParser.getBaseHealth());
        }

    public String getName() { return "Balanced"; }
    protected ImageIcon getIcon() { return (myParser.getIcon()); }
}
