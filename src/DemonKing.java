import javax.swing.*;

public class DemonKing extends Fighter

{
    private final String CLASS_PATH = "/gamedata/bosses/boss[@id = '1']";
    private GameDataParser myParser;

    public DemonKing()
    {
        myParser = new GameDataParser(CLASS_PATH);
        setBaseHealth(myParser.getBaseHealth());
    }

    public String getName() { return "DimKing"; }
    protected ImageIcon getIcon() { return (myParser.getIcon()); }
}