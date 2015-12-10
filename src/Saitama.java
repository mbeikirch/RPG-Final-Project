import javax.swing.*;
public class Saitama extends Fighter{

        private final String CLASS_PATH = "/gamedata/bosses/boss[@id = '2']";
        private GameDataParser myParser;

        public Saitama()
        {
            myParser = new GameDataParser(CLASS_PATH);
            setBaseHealth(myParser.getBaseHealth());
        }

    public String getName() { return "Saitama"; }
    protected ImageIcon getIcon() { return (myParser.getIcon()); }
}
