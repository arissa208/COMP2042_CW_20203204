/*
 *  Brick Destroy - A simple Arcade video game
 *   Copyright (C) 2017  Filippo Ranza
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;


public class HomeMenu extends JComponent implements MouseListener, MouseMotionListener {

    private static final String GREETINGS = "Welcome to:";
    private static final String GAME_TITLE = "Brick Destroy";
    private static final String CREDITS = "Version 0.1";
    private static final String START_TEXT = "Start";
    private static final String MENU_TEXT = "Exit";
    private static final String INSTRUCTION_TEXT = "Instructions";

    private static final Color BG_COLOR = Color.WHITE.darker();
    private static final Color BORDER_COLOR = new Color(200, 8, 21); //Venetian Red
    private static final Color DASH_BORDER_COLOR = new Color(255, 216, 0);//school bus yellow
    private static final Color TEXT_COLOR = new Color(203, 174, 164);//egyptian blue
    private static final Color CLICKED_BUTTON_COLOR = BG_COLOR.brighter();
    private static final Color CLICKED_TEXT = Color.WHITE;
    private static final int BORDER_SIZE = 5;
    private static final float[] DASHES = {12, 6};

    // shapes
    private final Rectangle menuFace;
    private final Rectangle startButton;
    private final Rectangle menuButton;
    private final Rectangle insButton;

    // border
    private final BasicStroke borderStoke;
    private final BasicStroke borderStoke_noDashes;

    // fonts
    private final Font greetingsFont;
    private final Font gameTitleFont;
    private final Font creditsFont;
    private final Font buttonFont;
    private final Font insFont;

    private final GameFrame owner;

    private boolean startClicked;
    private boolean menuClicked;
    private boolean insClicked;

    /** Representing the HomeMenu
     * @param owner gameboard frame
     * @param area area of gameboard frame
     */
    public HomeMenu(GameFrame owner, Dimension area) {

        this.setFocusable(true);
        this.requestFocusInWindow();

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        this.owner = owner;

        menuFace = new Rectangle(new Point(0, 0), area);
        this.setPreferredSize(area);

        Dimension btnDim = new Dimension(area.width / 3, area.height / 12);
        startButton = new Rectangle(btnDim);
        menuButton = new Rectangle(btnDim);
        insButton = new Rectangle(btnDim);

        borderStoke = new BasicStroke(BORDER_SIZE, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, DASHES, 0);
        borderStoke_noDashes = new BasicStroke(BORDER_SIZE, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

        greetingsFont = new Font("Noto Mono", Font.PLAIN, 25);
        gameTitleFont = new Font("Noto Mono", Font.BOLD, 40);
        creditsFont = new Font("Monospaced", Font.PLAIN, 10);
        buttonFont = new Font("Sans serif", Font.BOLD, 25);
        insFont = new Font("Sans serif", Font.BOLD, 25);

    }

    /** Build HomeMenu graphics
     * @param g homeMenu graphics
     */
    public void paint(Graphics g) {
        drawMenu((Graphics2D) g);
    }

    /** Build Menu
     * @param g2d menu graphics
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

    /** Create frame dimension for HomeMenu
     * @param g2d homeMenu graphics
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

    /** Set texts in HomeMenu
     * @param g2d graphic of HomeMenu
     */
    private void drawText(Graphics2D g2d) {

        g2d.setColor(TEXT_COLOR);

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D greetingsRect = greetingsFont.getStringBounds(GREETINGS, frc);
        Rectangle2D gameTitleRect = gameTitleFont.getStringBounds(GAME_TITLE, frc);
        Rectangle2D creditsRect = creditsFont.getStringBounds(CREDITS, frc);

        int sX, sY;

        sX = (int) (menuFace.getWidth() - greetingsRect.getWidth()) / 2;
        sY = (int) (menuFace.getHeight() / 4);

        g2d.setFont(greetingsFont);
        g2d.drawString(GREETINGS, sX, sY);

        sX = (int) (menuFace.getWidth() - gameTitleRect.getWidth()) / 2;
        sY += (int) gameTitleRect.getHeight() * 1.1;//add 10% of String height between the two strings

        g2d.setFont(gameTitleFont);
        g2d.drawString(GAME_TITLE, sX, sY);

        sX = (int) (menuFace.getWidth() - creditsRect.getWidth()) / 2;
        sY += (int) creditsRect.getHeight() * 1.1;

        g2d.setFont(creditsFont);
        g2d.drawString(CREDITS, sX, sY);


    }

    /** Sets buttons for HomeMenu
     * @param g2d graphic of HomeMenu
     */
    private void drawButton(Graphics2D g2d) {

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D txtRect = buttonFont.getStringBounds(START_TEXT, frc);
        Rectangle2D mTxtRect = buttonFont.getStringBounds(MENU_TEXT, frc);
        Rectangle2D iTxtRect = insFont.getStringBounds(INSTRUCTION_TEXT, frc);

        g2d.setFont(buttonFont);

        int x = (menuFace.width - startButton.width) / 2;
        int y = (int) ((menuFace.height - startButton.height) * 0.5);

        startButton.setLocation(x, y);

        x = (int) (startButton.getWidth() - txtRect.getWidth()) / 2;
        y = (int) (startButton.getHeight() - txtRect.getHeight()) / 2;

        x += startButton.x;
        y += startButton.y + (startButton.height * 0.6);

        if (startClicked) {
            Color tmp = g2d.getColor();
            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(startButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(START_TEXT, x, y);
            g2d.setColor(tmp);
        } else {
            g2d.draw(startButton);
            g2d.drawString(START_TEXT, x, y);
        }

        x = startButton.x;
        y = startButton.y;

        y *= 1.2;

        g2d.setFont(insFont);
        insButton.setLocation(x, y);

        x = (int) (insButton.getWidth() - iTxtRect.getWidth()) / 2;
        y = (int) (insButton.getHeight() - iTxtRect.getHeight()) / 2 ;

        x += insButton.x;
        y += insButton.y + (insButton.height * 0.6);

        if (insClicked) {
            Color tmp = g2d.getColor();
            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(insButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(INSTRUCTION_TEXT, x, y);
            g2d.setColor(tmp);
        } else {
            g2d.draw(insButton);
            g2d.drawString(INSTRUCTION_TEXT, x, y);
        }

        x = insButton.x;
        y = insButton.y;

        y *= 1.17;

        g2d.setFont(buttonFont);
        menuButton.setLocation(x, y);

        x = (int) (menuButton.getWidth() - mTxtRect.getWidth()) / 2;
        y = (int) (menuButton.getHeight() - mTxtRect.getHeight()) / 2;

        x += menuButton.x;
        y += menuButton.y + (startButton.height * 0.6);

        if (menuClicked) {
            Color tmp = g2d.getColor();

            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(menuButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(MENU_TEXT, x, y);
            g2d.setColor(tmp);
        } else {
            g2d.draw(menuButton);
            g2d.drawString(MENU_TEXT, x, y);
        }

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if (startButton.contains(p)) {
            owner.enableGameBoard();

        } else if (menuButton.contains(p)) {
            System.out.println("Goodbye " + System.getProperty("user.name"));
            System.exit(0);
        } else if (insButton.contains(p)) {
            owner.enableInstruction();
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if (startButton.contains(p)) {
            startClicked = true;
            repaint(startButton.x, startButton.y, startButton.width + 1, startButton.height + 1);

        } else if (menuButton.contains(p)) {
            menuClicked = true;
            repaint(menuButton.x, menuButton.y, menuButton.width + 1, menuButton.height + 1);
        } else if (insButton.contains(p)) {
            insClicked = true;
            repaint(insButton.x, insButton.y, insButton.width + 1, insButton.height + 1);
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if (startClicked) {
            startClicked = false;
            repaint(startButton.x, startButton.y, startButton.width + 1, startButton.height + 1);
        } else if (menuClicked) {
            menuClicked = false;
            repaint(menuButton.x, menuButton.y, menuButton.width + 1, menuButton.height + 1);
        } else if (insClicked) {
            insClicked = false;
            repaint(insButton.x, insButton.y, insButton.width + 1, insButton.height + 1);
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
        if (startButton.contains(p) || menuButton.contains(p) || insButton.contains(p))
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        else
            this.setCursor(Cursor.getDefaultCursor());

    }
}
