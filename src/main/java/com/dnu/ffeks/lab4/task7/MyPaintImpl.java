package com.dnu.ffeks.lab4.task7;

import com.google.common.io.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class MyPaintImpl extends JFrame implements MyPaint {

    public static final String NOT_A_NUMBER_ERR_MESSAGE = "Please, enter a number! It should be less than 100";
    private int previousX;
    private int previousY;
    private int brushFormWidth;
    private Color brushColor;
    private JPanel paintPanel;
    private JPanel buttonPanel;
    private JButton brushColorButton;
    private JButton brushWidthButton;
    private JTextField brushWidthValueField;

    MyPaintImpl(String title) {
        this.setTitle(title);
        prepareInstantiation();
        addActionListeners();
        addMouseListener(new MouseCoordinateReader());
        addMouseMotionListener(new PaintClass());
    }

    @Override
    public void setProperties(Image img, Dimension screenSize) {
        this.setMinimumSize(new Dimension(screenSize.width / 2, screenSize.height / 2));
        this.setLocationByPlatform(true);
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setIconImage(img);
    }

    private void prepareInstantiation() {
        brushFormWidth = 1;
        brushColor = Color.BLACK;
        createPanels();
        createButtons();
        createWidthValueFields();
        setDefaultParamsForWidthValueFields();
        setDefaultParamsForPanels();
        setIconImages();
        createContainer();
    }

    private void createWidthValueFields() {
        brushWidthValueField = new JTextField();
    }

    private void setDefaultParamsForWidthValueFields() {
        brushWidthValueField.setColumns(10);
        brushWidthValueField.setHorizontalAlignment(JTextField.CENTER);
    }

    private void createPanels() {
        paintPanel = new JPanel();
        buttonPanel = new JPanel();
    }

    private void setDefaultParamsForPanels() {
        paintPanel.setLayout(new BorderLayout());
        paintPanel.setBackground(Color.GRAY);

        buttonPanel.add(brushColorButton);
        buttonPanel.add(brushWidthButton);
        buttonPanel.add(brushWidthValueField);
    }

    private void createButtons() {
        brushColorButton = new JButton("Color");
        brushWidthButton = new JButton("Brush width");
    }

    private void setIconImages() {
        brushColorButton.setIcon(new ImageIcon(Resources.getResource("palette.png")));
        brushWidthButton.setIcon(new ImageIcon(Resources.getResource("paint-brush.png")));
    }

    private void createContainer() {
        Container container = getContentPane();
        container.add(paintPanel, BorderLayout.CENTER);
        container.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addActionListeners() {
        brushColorButton.addActionListener(new ColorButtonActionListener());
        brushWidthButton.addActionListener(new WidthButtonActionListener());
    }

    private class ColorButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            brushColor = JColorChooser.showDialog(((Component) event.getSource()).getParent(), "Select brush color panel", brushColor);
        }
    }

    private class WidthButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            String enteredWidth = brushWidthValueField.getText();
            boolean isNumber = enteredWidth.matches("\\d+");
            if (isNumber && enteredWidth.length() <= 2) {
                brushFormWidth = Integer.parseInt(brushWidthValueField.getText());
            } else {
                JOptionPane.showMessageDialog(null, NOT_A_NUMBER_ERR_MESSAGE, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class MouseCoordinateReader extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent event) {
            setPreviousCoordinates(event.getX(), event.getY());
        }
    }

    private class PaintClass extends MouseMotionAdapter {
        @Override
        public void mouseDragged(MouseEvent event) {
            Graphics2D graphics = (Graphics2D) getGraphics();
            BasicStroke brushForm = new BasicStroke(brushFormWidth, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL);

            graphics.setStroke(brushForm);
            graphics.setColor(brushColor);
            graphics.drawLine(previousX, previousY, event.getX(), event.getY());
            setPreviousCoordinates(event.getX(), event.getY());
        }
    }

    private void setPreviousCoordinates(int previousX, int previousY) {
        this.previousX = previousX;
        this.previousY = previousY;
    }
}
