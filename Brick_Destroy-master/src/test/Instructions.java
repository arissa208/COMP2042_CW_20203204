
package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;


public class Instructions extends JComponent implements MouseListener, MouseMotionListener {

    private static final String HEADER = "HOW TO PLAY?";
    private static final String INS_TEXT = "1. Click on START to go to game";
    private static final String INS_TEXT2 = "2. Press SPACEBAR to release ball and start game";
    private static final String INS_TEXT3 = "3. Use A and D keys to move left and right";
    private static final String INS_TEXT4 = "4. Press SPACEBAR again to pause game";
    private static final String INS_TEXT5 = "5. Click RESTART to restart the level";
    private static final String INS_TEXT6 = "6. Click EXIT to leave game";
    private static final String INS_TEXT7 = "5. Click RESTART to restart the level";

    private static final String RETURN_TEXT = "BACK";

    private static final Color BG_COLOR = Color.BLACK.darker();
    private static final Color TEXT_COLOR = new Color(225, 190, 174);//egyptian blue
    private static final Color CLICKED_BUTTON_COLOR = BG_COLOR.brighter();
    private static final Color CLICKED_TEXT = Color.WHITE;

    private static final Color BORDER_COLOR = new Color(0, 0, 0); //Venetian Red
    private static final Color DASH_BORDER_COLOR = new Color(26, 26, 25);//school bus yellow
    private static final int BORDER_SIZE = 5;
    private static final float[] DASHES = {12, 6};

    // border
    private final BasicStroke borderStoke;
    private final BasicStroke borderStoke_noDashes;

    // shapes
    private final Rectangle
            menuFace;
    private final Rectangle returnButton;

    // fonts
    private final Font titleFont;
    private final Font textFont;
    private final Font returnFont;

    private final GameFrame owner;

    private boolean returnClicked;

    /** Represents description of Instructions page
     * @param owner frame owner
     * @param area area of frame
     */
    public Instructions(GameFrame owner, Dimension area) {

        this.setFocusable(true);
        this.requestFocusInWindow();

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        this.owner = owner;

        menuFace = new Rectangle(new Point(0, 0), area);
        this.setPreferredSize(area);

        Dimension btnDim = new Dimension(area.width / 3, area.height / 12);
        returnButton = new Rectangle(btnDim);

        borderStoke = new BasicStroke(BORDER_SIZE, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, DASHES, 0);
        borderStoke_noDashes = new BasicStroke(BORDER_SIZE, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

        titleFont = new Font("IMPACT", Font.BOLD, 40);
        textFont = new Font("CONSOLAS", Font.BOLD, 16);
        returnFont = new Font("IMPACT", Font.BOLD, 20);

    }

    /** Buil graphics of Instructions page
     * @param g graphics of Instructions page
     */
    public void paint(Graphics g) {
        drawMenu((Graphics2D) g);
    }

    /** Build menu
     * @param g2d graphics of instructions page
     */
    public void drawMenu(Graphics2D g2d) {

        drawContainer(g2d);

        /*
        all the following method calls need a relative
        painting directly into the HomeMenu rectangle,
        so the translation is made here so the other methods do not do that.
         */
        Color prevColor = g2d.getColor();
        Font prevFont = g2d.getFont();

        double x = menuFace.getX();
        double y = menuFace.getY();

        g2d.translate(x, y);

        //methods calls
        drawText(g2d);
        drawButton(g2d);
        //end of methods calls

        g2d.translate(-x, -y);
        g2d.setFont(prevFont);
        g2d.setColor(prevColor);
    }

    /** Create frame dimension for Instructions page
     * @param g2d graphic of Instructions page
     */
    private void drawContainer(Graphics2D g2d) {
        Color prev = g2d.getColor();

        g2d.setColor(BG_COLOR);
        g2d.fill(menuFace);

        Stroke tmp = g2d.getStroke();

        g2d.setStroke(borderStoke_noDashes);
        g2d.setColor(DASH_BORDER_COLOR);
        g2d.draw(menuFace);

        g2d.setStroke(borderStoke);
        g2d.setColor(BORDER_COLOR);
        g2d.draw(menuFace);

        g2d.setStroke(tmp);

        g2d.setColor(prev);
        Image picture = Toolkit.getDefaultToolkit().getImage("brick.png");
        g2d.drawImage(picture, 0, 0, this);
    }

    /** Set texts in the  Instructions page
     * @param g2d graphics of Instructions page
     */
    private void drawText(Graphics2D g2d) {

        g2d.setColor(TEXT_COLOR);

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D titleRect = titleFont.getStringBounds(HEADER, frc);
        Rectangle2D insRect = textFont.getStringBounds(INS_TEXT, frc);

        int sX, sY;

        sX = (int) (menuFace.getWidth() - titleRect.getWidth()) / 2;
        sY = (int) (menuFace.getHeight() / 4);

        g2d.setFont(titleFont);
        g2d.drawString(HEADER, sX, sY);

        sX = (int) (menuFace.getWidth() - insRect.getWidth()) / 4;
        sY += (int) insRect.getHeight() * 3;

        g2d.setFont(textFont);
        g2d.drawString(INS_TEXT, sX, sY);

        sX = (int) (menuFace.getWidth() - insRect.getWidth()) / 4;
        sY += (int) insRect.getHeight() * 1.1;

        g2d.setFont(textFont);
        g2d.drawString(INS_TEXT2, sX, sY);

        sX = (int) (menuFace.getWidth() - insRect.getWidth()) / 4;
        sY += (int) insRect.getHeight() * 1.1;

        g2d.setFont(textFont);
        g2d.drawString(INS_TEXT3, sX, sY);

        sX = (int) (menuFace.getWidth() - insRect.getWidth()) / 4;
        sY += (int) insRect.getHeight() * 1.1;

        g2d.setFont(textFont);
        g2d.drawString(INS_TEXT4, sX, sY);

        sX = (int) (menuFace.getWidth() - insRect.getWidth()) / 4;
        sY += (int) insRect.getHeight() * 1.1;

        g2d.setFont(textFont);
        g2d.drawString(INS_TEXT5, sX, sY);

        sX = (int) (menuFace.getWidth() - insRect.getWidth()) / 4;
        sY += (int) insRect.getHeight() * 1.1;

        g2d.setFont(textFont);
        g2d.drawString(INS_TEXT6, sX, sY);

        sX = (int) (menuFace.getWidth() - insRect.getWidth()) / 4;
        sY += (int) insRect.getHeight() * 1.1;

        g2d.setFont(textFont);
        g2d.drawString(INS_TEXT7, sX, sY);

    }

    /**
     * @param g2d dgraphics of Instructions page
     */
    private void drawButton(Graphics2D g2d) {

        FontRenderContext frc = g2d.getFontRenderContext();
        Rectangle2D returnRect = returnFont.getStringBounds(RETURN_TEXT, frc);
        g2d.setFont(returnFont);
        int x = (menuFace.width - returnButton.width) / 2;
        int y = (int) ((menuFace.height - returnButton.height) * 0.8);
        returnButton.setLocation(x, y);

        x = (int) (returnButton.getWidth() - returnRect.getWidth()) / 2;
        y = (int) (returnButton.getHeight() - returnRect.getHeight()) / 2;
        x += returnButton.x;
        y += returnButton.y + (returnButton.height * 0.4);

        if (returnClicked) {
            Color tmp = g2d.getColor();
            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(returnButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(RETURN_TEXT, x, y);
            g2d.setColor(tmp);
        } else {
            g2d.draw(returnButton);
            g2d.drawString(RETURN_TEXT, x, y);
        }

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if (returnButton.contains(p)) {
            owner.enableHomeMenu();
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if (returnButton.contains(p)) {
            returnClicked = true;
            repaint(returnButton.x, returnButton.y, returnButton.width + 1, returnButton.height + 1);
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if (returnClicked) {
            returnClicked = false;
            repaint(returnButton.x, returnButton.y, returnButton.width + 1, returnButton.height + 1);
        }
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if (returnButton.contains(p))
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        else
            this.setCursor(Cursor.getDefaultCursor());

    }
}
