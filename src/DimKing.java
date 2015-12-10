import javax.swing.*;

public class DimKing extends Fighter

{
    private final String CLASS_PATH = "/gamedata/bosses/boss[@id = '1']";
    private GameDataParser myParser;

    public DimKing()
    {
        myParser = new GameDataParser(CLASS_PATH);
        setBaseHealth(myParser.getBaseHealth());
    }

    public String getName() { return "DimKing"; }
    protected ImageIcon getIcon() { return (myParser.getIcon()); }
}