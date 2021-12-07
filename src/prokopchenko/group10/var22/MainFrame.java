package prokopchenko.group10.var22;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class MainFrame extends JFrame {
    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;
    private JFileChooser fileChooser = null;
    private JMenuItem resetGraphicsMenuItem;
    private GraphicsDisplay display = new GraphicsDisplay();
    private boolean fileLoaded = false;


    public MainFrame() {
        super("Обработка событий от мыши");
        this.setSize(700, 500);
        Toolkit kit = Toolkit.getDefaultToolkit();
        this.setLocation((kit.getScreenSize().width - 700) / 2, (kit.getScreenSize().height - 500) / 2);
        this.setExtendedState(6);
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("Файл");
        menuBar.add(fileMenu);
        Action openGraphicsAction = new AbstractAction("Открыть файл с графиком") {
            public void actionPerformed(ActionEvent event) {
                if (MainFrame.this.fileChooser == null) {
                    MainFrame.this.fileChooser = new JFileChooser();
                    MainFrame.this.fileChooser.setCurrentDirectory(new File("."));
                }

                MainFrame.this.fileChooser.showOpenDialog(MainFrame.this);
                MainFrame.this.openGraphics(MainFrame.this.fileChooser.getSelectedFile());
            }
        };
        fileMenu.add(openGraphicsAction);
        Action resetGraphicsAction = new AbstractAction("Отменить все изменения") {
            public void actionPerformed(ActionEvent event) {
                MainFrame.this.display.reset();
            }
        };
        this.resetGraphicsMenuItem = fileMenu.add(resetGraphicsAction);
        this.resetGraphicsMenuItem.setEnabled(false);
        this.getContentPane().add(this.display, "Center");
        Action saveGraphicsAction = new AbstractAction("Сохранить файл с графиком") {
            public void actionPerformed(ActionEvent event ) {
                if (MainFrame.this.fileChooser == null) {
                    JFileChooser fileChooser = new JFileChooser();
                    int response = fileChooser.showSaveDialog(null);
                    if( response == JFileChooser.APPROVE_OPTION)
                    {
                        File file = new File(fileChooser.getSelectedFile().getAbsolutePath());

                    }
                }

                MainFrame.this.fileChooser.showSaveDialog(MainFrame.this);
                MainFrame.this.saveGraphics(MainFrame.this.fileChooser.getSelectedFile());
            }
        };



        fileMenu.add(saveGraphicsAction);
    }


    protected void saveGraphics(File saveFile) {

        try {
            FileWriter fileWriter=new FileWriter("graphic.txt");
            fileWriter.write("file");
            fileWriter.close();
        }
        catch (IOException e) {

            e.printStackTrace();
        }


    }

    protected void openGraphics(File selectedFile) {
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(selectedFile));
            ArrayList graphicsData = new ArrayList(50);

            while(in.available() > 0) {
                Double x = in.readDouble();
                Double y = in.readDouble();
                graphicsData.add(new Double[]{x, y});
            }

            if (graphicsData.size() > 0) {
                this.fileLoaded = true;
                this.resetGraphicsMenuItem.setEnabled(true);
                this.display.displayGraphics(graphicsData);
            }

        } catch (FileNotFoundException var6) {
            JOptionPane.showMessageDialog(this, "Указанный файл не найден", "Ошибка загрузки данных", 2);
        } catch (IOException var7) {
            JOptionPane.showMessageDialog(this, "Ошибка чтения координат точек из файла", "Ошибка загрузки данных", 2);
        }
    }



    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);
    }
}
