import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    private JPanel contentPane;
    private JPanel Pane1;
    private JPanel Pane2;
    private JPanel Pane3;
    private JPanel Pane4;
    private JPanel Pane5;

    private FileDialog fileDialog;

    static public InformationBoard InfBoard;

    static public TimeThread MyTimerThread;

    // MenuMode
    // 0：Two-person Duel
    // 1：Human-computer Duel
    // 2：Chessboard Demo
    // 3：Exit the Game
    static public int MenuMode = 0;
    static public char DoPlayer = '红';
    static public String RedPlayer;
    static public String BlackPlayer;
    static public ChessBoarder MyBoarder;
    static public ChessBoarder TempBoarder;
    static public ChessBoarderCanvas MyCanvas;
    static public boolean AIPlay = false;
    static public ArrayList<int[]> record = new ArrayList<>();

    public MainFrame() {
        // Default
        DataInit();

        // Draw the label
        this.setTitle("中国象棋 ChineseChess");
        this.setBounds(0, 0, 1366, 768);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        // Set contentPane
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        // Do not use layout
        contentPane.setLayout(null);
        // Set contentPane transparent
        contentPane.setOpaque(false);
        this.setContentPane(contentPane);

        // Set information on contentPane
        // Add background picture
        JLabel BackGround = new JLabel("");
        BackGround.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/background.png"))));
        BackGround.setBounds(0, 0, 1366, 768);
        this.getLayeredPane().add(BackGround, new Integer(Integer.MIN_VALUE));

        // Default 4 Pane
        Pane1 = new JPanel();
        Pane2 = new JPanel();
        Pane3 = new JPanel();
        Pane4 = new JPanel();
        Pane5 = new JPanel();

        // Set the position and property of the 4 Pane
        Pane1.setBounds(0, 0, 1366, 768);
        Pane1.setOpaque(false);
        Pane1.setVisible(true);
        Pane1.setLayout(null);
        Pane2.setBounds(0, 0, 1366, 768);
        Pane2.setOpaque(false);
        Pane2.setVisible(false);
        Pane2.setLayout(null);
        Pane3.setBounds(0, 0, 1366, 768);
        Pane3.setOpaque(false);
        Pane3.setVisible(false);
        Pane3.setLayout(null);
        Pane4.setBounds(0, 0, 1366, 768);
        Pane4.setOpaque(false);
        Pane4.setVisible(false);
        Pane4.setLayout(null);
        Pane5.setBounds(0, 0, 1366, 768);
        Pane5.setOpaque(false);
        Pane5.setVisible(false);
        Pane5.setLayout(null);

        // Add 4 Pane into contentPane
        contentPane.add(Pane1);
        contentPane.add(Pane2);
        contentPane.add(Pane3);
        contentPane.add(Pane4);
        contentPane.add(Pane5);

        // Add authors' information
        JLabel AuthorInfo = new JLabel("");
        AuthorInfo.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/information.png"))));
        AuthorInfo.setBounds(30, 600, new ImageIcon(MainFrame.class.getResource("/imageLibrary/information.png")).getIconWidth(), new ImageIcon(MainFrame.class.getResource("/imageLibrary/information.png")).getIconHeight());
        Pane1.add(AuthorInfo);

        // Add title
        JLabel Title = new JLabel("");
        Title.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/title.png"))));
        Title.setBounds(30, 50, new ImageIcon(MainFrame.class.getResource("/imageLibrary/title.png")).getIconWidth(), new ImageIcon(MainFrame.class.getResource("/imageLibrary/title.png")).getIconHeight());
        Pane1.add(Title);

        // Add ranking list title
        JLabel RankingListTitle = new JLabel("");
        RankingListTitle.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/RankingListTitle.png"))));
        RankingListTitle.setBounds(50, 50, new ImageIcon(MainFrame.class.getResource("/imageLibrary/RankingListTitle.png")).getIconWidth(), new ImageIcon(MainFrame.class.getResource("/imageLibrary/RankingListTitle.png")).getIconHeight());
        Pane5.add(RankingListTitle);

        // Add 4 buttons
        JLabel Menu1 = new JLabel("");
        Menu1.setOpaque(false);
        JLabel Menu2 = new JLabel("");
        Menu2.setOpaque(false);
        JLabel Menu3 = new JLabel("");
        Menu3.setOpaque(false);
        JLabel Menu4 = new JLabel("");
        Menu4.setOpaque(false);
        JLabel Menu5 = new JLabel("");
        Menu4.setOpaque(false);

        // Add time label
        JLabel TimerLabel = new JLabel();
        TimerLabel.setBounds(133, 650, 100, 50);
        TimerLabel.setFont(new Font("楷体",Font.BOLD,28));

        // Add ranking list
        Object[] columnNames = {"排名", "玩家", "积分"};
        DefaultTableModel tableModel = new DefaultTableModel(RankingList.getRankingList(), columnNames);
        JTable table = new JTable() {
            public Component prepareRenderer(TableCellRenderer renderer,
                                             int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (c instanceof JComponent) {
                    ((JComponent) c).setOpaque(false);
                }
                return c;
            }
        };
        table.setModel(tableModel);
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(renderer.CENTER);
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class,r);
        table.setFont(new Font("楷体",Font.BOLD,40));
        table.getTableHeader().setFont(new Font("楷体",Font.BOLD,40));
        table.setShowGrid(false);
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setReorderingAllowed(false);
        table.setOpaque(false);
        table.getTableHeader().setOpaque(false);
        table.setRowHeight(60);
        TableColumn tableColumn0 = table.getColumnModel().getColumn(0);
        tableColumn0.setWidth(100);
        TableColumn tableColumn1 = table.getColumnModel().getColumn(1);
        tableColumn1.setWidth(400);
        TableColumn tableColumn2 = table.getColumnModel().getColumn(2);
        tableColumn2.setWidth(50);
        table.setBounds(400, 100, 660, 700);
        Pane5.add(table);

        // Set buttons for two-person duel
        // Set default picture
        Menu1.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu1_0.png"))));
        // Add mouse listener
        Menu1.addMouseListener (new MouseAdapter() {
            @Override
            public void mouseClicked (MouseEvent arg0) {
                MenuMode = 1;
                Pane1.setVisible(false);
                Pane2.setVisible(true);
                Pane3.setVisible(false);
                Pane4.setVisible(false);
                Pane5.setVisible(false);
                Pane2.add(InfBoard);
                Pane2.add(MyCanvas);
                Pane2.add(TimerLabel);
                record = new ArrayList<>();
                MyTimerThread = new TimeThread(TimerLabel);
                AIPlay = false;
                PlayerName();
                MyTimerThread.start();

            }
            public void mouseEntered (MouseEvent arg0) {
                Menu1.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu1_1.png"))));
                Menu2.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu2_0.png"))));
                Menu3.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu3_0.png"))));
                Menu4.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu4_0.png"))));
                Menu5.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu5_0.png"))));
            }
            public void mouseExited (MouseEvent arg0) {
                // Default all pictures of buttons
                Menu1.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu1_0.png"))));
                Menu2.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu2_0.png"))));
                Menu3.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu3_0.png"))));
                Menu4.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu4_0.png"))));
                Menu5.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu5_0.png"))));
                // Set picture for MenuMode
                if (MenuMode == 1) {
                    Menu1.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu1_1.png"))));
                } else if (MenuMode == 2) {
                    Menu2.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu2_1.png"))));
                } else if (MenuMode == 3) {
                    Menu3.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu3_1.png"))));
                } else if (MenuMode == 4) {
                    Menu4.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu4_1.png"))));
                } else if (MenuMode == 5) {
                    Menu5.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu5_1.png"))));
                }
            }
        });
        // Set the position and size
        Menu1.setBounds(536, 140, new ImageIcon(MainFrame.class.getResource("/imageLibrary/Menu1_0.png")).getIconWidth(), new ImageIcon(MainFrame.class.getResource("/imageLibrary/Menu1_0.png")).getIconHeight());
        // Add Menu1 to ContentPane
        Pane1.add(Menu1);


        // Set buttons for human-machine duel
        // Set default picture
        Menu2.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu2_0.png"))));
        // Add mouse listener
        Menu2.addMouseListener (new MouseAdapter() {
            @Override
            public void mouseClicked (MouseEvent arg0) {
                MenuMode = 2;
                Pane1.setVisible(false);
                Pane2.setVisible(false);
                Pane3.setVisible(true);
                Pane4.setVisible(false);
                Pane5.setVisible(false);
                Pane3.add(InfBoard);
                Pane3.add(MyCanvas);
                Pane3.add(TimerLabel);
                record = new ArrayList<>();
                MyTimerThread = new TimeThread(TimerLabel);
                AIPlay = true;
                PlayerName();
                MyTimerThread.start();

            }
            public void mouseEntered (MouseEvent arg0) {
                Menu1.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu1_0.png"))));
                Menu2.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu2_1.png"))));
                Menu3.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu3_0.png"))));
                Menu4.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu4_0.png"))));
                Menu5.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu5_0.png"))));
            }
            public void mouseExited (MouseEvent arg0) {
                // Default all pictures of buttons
                Menu1.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu1_0.png"))));
                Menu2.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu2_0.png"))));
                Menu3.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu3_0.png"))));
                Menu4.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu4_0.png"))));
                Menu5.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu5_0.png"))));
                // Set picture for MenuMode
                if (MenuMode == 1) {
                    Menu1.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu1_1.png"))));
                } else if (MenuMode == 2) {
                    Menu2.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu2_1.png"))));
                } else if (MenuMode == 3) {
                    Menu3.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu3_1.png"))));
                } else if (MenuMode == 4) {
                    Menu4.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu4_1.png"))));
                } else if (MenuMode == 5) {
                    Menu5.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu5_1.png"))));
                }
            }
        });
        // Set the position and size
        Menu2.setBounds(536, 220, new ImageIcon(MainFrame.class.getResource("/imageLibrary/Menu2_0.png")).getIconWidth(), new ImageIcon(MainFrame.class.getResource("/imageLibrary/Menu2_0.png")).getIconHeight());
        // Add Menu2 to ContentPane
        Pane1.add(Menu2);


        // Set buttons for chessboard demo
        // Set default picture
        Menu3.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu3_0.png"))));
        // Add mouse listener
        Menu3.addMouseListener (new MouseAdapter() {
            @Override
            public void mouseClicked (MouseEvent arg0) {
                MenuMode = 3;
                Pane1.setVisible(false);
                Pane2.setVisible(false);
                Pane3.setVisible(false);
                Pane4.setVisible(true);
                Pane5.setVisible(false);
                Pane4.add(InfBoard);
                Pane4.add(MyCanvas);
            }
            public void mouseEntered (MouseEvent arg0) {
                Menu1.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu1_0.png"))));
                Menu2.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu2_0.png"))));
                Menu3.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu3_1.png"))));
                Menu4.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu4_0.png"))));
                Menu5.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu5_0.png"))));
            }
            public void mouseExited (MouseEvent arg0) {
                // Default all pictures of buttons
                Menu1.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu1_0.png"))));
                Menu2.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu2_0.png"))));
                Menu3.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu3_0.png"))));
                Menu4.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu4_0.png"))));
                Menu5.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu5_0.png"))));
                // Set picture for MenuMode
                if (MenuMode == 1) {
                    Menu1.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu1_1.png"))));
                } else if (MenuMode == 2) {
                    Menu2.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu2_1.png"))));
                } else if (MenuMode == 3) {
                    Menu3.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu3_1.png"))));
                } else if (MenuMode == 4) {
                    Menu4.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu4_1.png"))));
                } else if (MenuMode == 5) {
                    Menu5.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu5_1.png"))));
                }
            }
        });
        // Set the position and size
        Menu3.setBounds(536, 300, new ImageIcon(MainFrame.class.getResource("/imageLibrary/Menu3_0.png")).getIconWidth(), new ImageIcon(MainFrame.class.getResource("/imageLibrary/Menu3_0.png")).getIconHeight());
        // Add Menu3 to ContentPane
        Pane1.add(Menu3);

        // Set buttons for exiting the game
        // Set default picture
        Menu4.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu4_0.png"))));
        // Add mouse listener
        Menu4.addMouseListener (new MouseAdapter() {
            @Override
            public void mouseClicked (MouseEvent arg0) {
                MenuMode = 4;
                Pane1.setVisible(true);
                Pane2.setVisible(false);
                Pane3.setVisible(false);
                Pane4.setVisible(false);
                Pane5.setVisible(false);
                // Exit the game
                dispose();
                System.exit(0);
            }
            public void mouseEntered (MouseEvent arg0) {
                Menu1.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu1_0.png"))));
                Menu2.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu2_0.png"))));
                Menu3.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu3_0.png"))));
                Menu4.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu4_1.png"))));
                Menu5.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu5_0.png"))));
            }
            public void mouseExited (MouseEvent arg0) {
                // Default all pictures of buttons
                Menu1.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu1_0.png"))));
                Menu2.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu2_0.png"))));
                Menu3.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu3_0.png"))));
                Menu4.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu4_0.png"))));
                Menu5.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu5_0.png"))));
                // Set picture for MenuMode
                if (MenuMode == 1) {
                    Menu1.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu1_1.png"))));
                } else if (MenuMode == 2) {
                    Menu2.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu2_1.png"))));
                } else if (MenuMode == 3) {
                    Menu3.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu3_1.png"))));
                } else if (MenuMode == 4) {
                    Menu4.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu4_1.png"))));
                } else if (MenuMode == 5) {
                    Menu5.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu5_1.png"))));
                }
            }
        });
        // Set the position and size
        Menu4.setBounds(536, 380,new ImageIcon(MainFrame.class.getResource("/imageLibrary/Menu4_0.png")).getIconWidth(), new ImageIcon(MainFrame.class.getResource("/imageLibrary/Menu4_0.png")).getIconHeight());
        // Add Menu4 to contentPane
        Pane1.add(Menu4);

        // Set buttons for chessboard demo
        // Set default picture
        Menu5.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu5_0.png"))));
        // Add mouse listener
        Menu5.addMouseListener (new MouseAdapter() {
            @Override
            public void mouseClicked (MouseEvent arg0) {
                MenuMode = 5;
                Pane1.setVisible(false);
                Pane2.setVisible(false);
                Pane3.setVisible(false);
                Pane4.setVisible(false);
                table.setModel(new DefaultTableModel(RankingList.getRankingList(), columnNames));
                Pane5.revalidate();
                Pane5.repaint();
                Pane5.setVisible(true);
            }
            public void mouseEntered (MouseEvent arg0) {
                Menu1.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu1_0.png"))));
                Menu2.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu2_0.png"))));
                Menu3.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu3_0.png"))));
                Menu4.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu4_0.png"))));
                Menu5.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu5_1.png"))));
            }
            public void mouseExited (MouseEvent arg0) {
                // Default all pictures of buttons
                Menu1.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu1_0.png"))));
                Menu2.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu2_0.png"))));
                Menu3.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu3_0.png"))));
                Menu4.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu4_0.png"))));
                Menu5.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu5_0.png"))));
                // Set picture for MenuMode
                if (MenuMode == 1) {
                    Menu1.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu1_1.png"))));
                } else if (MenuMode == 2) {
                    Menu2.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu2_1.png"))));
                } else if (MenuMode == 3) {
                    Menu3.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu3_1.png"))));
                } else if (MenuMode == 4) {
                    Menu4.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu4_1.png"))));
                } else if (MenuMode == 5) {
                    Menu5.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/Menu5_1.png"))));
                }
            }
        });
        // Set the position and size
        Menu5.setBounds(1100, 300, new ImageIcon(MainFrame.class.getResource("/imageLibrary/Menu5_0.png")).getIconWidth(), new ImageIcon(MainFrame.class.getResource("/imageLibrary/Menu5_0.png")).getIconHeight());
        // Add Menu3 to ContentPane
        Pane1.add(Menu5);

        // Add Canvas into Panel to draw the chessboard
        MyCanvas = new ChessBoarderCanvas();
        MyCanvas.setBounds(DefaultSet.CanvasPosX, DefaultSet.CanvasPosY, 661, 728);
        // Send data to canvas
        MyCanvas.SendData(MyBoarder, Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/background.png")), DefaultSet.CanvasPosX, DefaultSet.CanvasPosY, DefaultSet.CanvasPosX + 661, DefaultSet.CanvasPosY + 728);
        MyCanvas.repaint();
        MyCanvas.addMouseListener(new ChessPieceClick());


        // Add information board to pane
        InfBoard = new InformationBoard();
        InfBoard.setBounds(1011, 50, 394, 481);

        InfBoard.AddLog("红方执子");

        // Add button for resetting
        DiyButton AllReset2 = new DiyButton("/imageLibrary/ButtonAllReset_0.png","/imageLibrary/ButtonAllReset_1.png");
        AllReset2.setBounds(20, 50, 326, 115);
        AllReset2.addMouseListener (new MouseAdapter() {
            @Override
            public void mouseClicked (MouseEvent arg0) {
                DataInit();
                InfBoard.Clear();
                InfBoard.AddLog("红方执子");
                MyCanvas.SendWinner('无');
                MyCanvas.paintImmediately(0, 0, MyCanvas.getWidth(), MyCanvas.getHeight());
            }
        });
        Pane2.add(AllReset2);

        DiyButton AllReset3 = new DiyButton("/imageLibrary/ButtonAllReset_0.png","/imageLibrary/ButtonAllReset_1.png");
        AllReset3.setBounds(20, 50, 326, 115);
        AllReset3.addMouseListener (new MouseAdapter() {
            @Override
            public void mouseClicked (MouseEvent arg0) {
                DataInit();
                InfBoard.Clear();
                InfBoard.AddLog("红方执子");
                MyCanvas.SendWinner('无');
                MyCanvas.paintImmediately(0, 0, MyCanvas.getWidth(), MyCanvas.getHeight());
            }
        });
        Pane3.add(AllReset3);

        DiyButton AllReset4 = new DiyButton("/imageLibrary/ButtonAllReset_0.png","/imageLibrary/ButtonAllReset_1.png");
        AllReset4.setBounds(20, 50, 326, 115);
        AllReset4.addMouseListener (new MouseAdapter() {
            @Override
            public void mouseClicked (MouseEvent arg0) {
                DataInit();
                InfBoard.Clear();
                InfBoard.AddLog("红方执子");
                MyCanvas.SendWinner('无');
                MyCanvas.paintImmediately(0, 0, MyCanvas.getWidth(), MyCanvas.getHeight());
            }
        });
        Pane4.add(AllReset4);

        // Add button for reading game
        DiyButton ReadGame2 = new DiyButton("/imageLibrary/ButtonReadGame_0.png", "/imageLibrary/ButtonReadGame_1.png");
        ReadGame2.setBounds(20, 190, 326, 115);
        ReadGame2.addMouseListener (new MouseAdapter() {
            @Override
            public void mouseClicked (MouseEvent arg0) {
                fileDialog = new FileDialog(new Frame(),"请选择*.chessboard文件",FileDialog.LOAD);
                FilenameFilter filenameFilter= (dir, name) -> name.endsWith("chessboard");
                fileDialog.setFilenameFilter(filenameFilter);
                fileDialog.setVisible(true);
                String address = fileDialog.getDirectory()+fileDialog.getFile();
                System.out.println(address);
                ReadFile readFile = new ReadFile(address);
                MyBoarder = new ChessBoarder(readFile.getSetPieces());
                DoPlayer = (readFile.getLastMover() == '红' ? '黑' : '红');
                if(readFile.isFailedLoading()){
                    JOptionPane.showMessageDialog(
                            contentPane,
                            "存档错误，将加载先前棋盘",
                            "提示",
                            JOptionPane.WARNING_MESSAGE,
                            null
                    );
                }else {
                    InfBoard.Clear();
                    InfBoard.AddLog(DoPlayer == '红' ? "红方执子" : "黑方执子");
                }
                MyCanvas.SendData(MyBoarder, Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/background.png")), DefaultSet.CanvasPosX, DefaultSet.CanvasPosY, DefaultSet.CanvasPosX + 661, DefaultSet.CanvasPosY + 728);
                MyCanvas.repaint();
            }
        });
        Pane2.add(ReadGame2);

        DiyButton ReadGame3 = new DiyButton("/imageLibrary/ButtonReadGame_0.png", "/imageLibrary/ButtonReadGame_1.png");
        ReadGame3.setBounds(20, 190, 326, 115);
        ReadGame3.addMouseListener (new MouseAdapter() {
            @Override
            public void mouseClicked (MouseEvent arg0) {
                fileDialog = new FileDialog(new Frame(),"请选择*.chessboard文件",FileDialog.LOAD);
                FilenameFilter filenameFilter= (dir, name) -> name.endsWith("chessboard");
                fileDialog.setFilenameFilter(filenameFilter);
                fileDialog.setVisible(true);
                String address = fileDialog.getDirectory()+fileDialog.getFile();
                ReadFile readFile = new ReadFile(address);
                MyBoarder = new ChessBoarder(readFile.getSetPieces());
                DoPlayer = (readFile.getLastMover() == '红' ? '黑' : '红');
                if(readFile.isFailedLoading()){
                    JOptionPane.showMessageDialog(
                            contentPane,
                            "存档错误，将加载先前棋盘",
                            "提示",
                            JOptionPane.WARNING_MESSAGE,
                            null
                    );
                }else {
                    InfBoard.Clear();
                    InfBoard.AddLog(DoPlayer == '红' ? "红方执子" : "黑方执子");
                }
                MyCanvas.SendData(MyBoarder, Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/background.png")), DefaultSet.CanvasPosX, DefaultSet.CanvasPosY, DefaultSet.CanvasPosX + 661, DefaultSet.CanvasPosY + 728);
                MyCanvas.repaint();
            }
        });
        Pane3.add(ReadGame3);

        DiyButton ReadGame4 = new DiyButton("/imageLibrary/ButtonReadGame_0.png", "/imageLibrary/ButtonReadGame_1.png");
        ReadGame4.setBounds(20, 190, 326, 115);
        ReadGame4.addMouseListener (new MouseAdapter() {
            @Override
            public void mouseClicked (MouseEvent arg0) {
                fileDialog = new FileDialog(new Frame(),"请选择*.chessmoveseq文件",FileDialog.LOAD);
                FilenameFilter filenameFilter= (dir, name) -> name.endsWith("chessmoveseq");
                fileDialog.setFilenameFilter(filenameFilter);
                fileDialog.setVisible(true);
                String address = fileDialog.getDirectory()+fileDialog.getFile();
                ReadFile readFile = new ReadFile(address);
                InfBoard.Clear();
                InfBoard.AddLog(DoPlayer == '红' ? "红方执子" : "黑方执子");
                ChessBoardDemo chessBoardDemo = new ChessBoardDemo();
                for(int i = 0 ; i < readFile.getMove().length ; i ++){
                    if (DoPlayer == '红') {
                        chessBoardDemo.movePieces(9 - readFile.getMove()[i][0], 10 - readFile.getMove()[i][1]);
                        System.out.print(readFile.getMove()[i][0] + " " + readFile.getMove()[i][1] + " ");
                        try {
                            Thread.sleep(500);
                        }catch (Exception e) {break;}
                        chessBoardDemo.movePieces(9 - readFile.getMove()[i][2], 10 - readFile.getMove()[i][3]);
                        System.out.print(readFile.getMove()[i][2] + " " + readFile.getMove()[i][3] + " ");
                        MyBoarder.p1 = null;
                        MyBoarder.p2 = null;
                        try {
                            Thread.sleep(500);
                        }catch (Exception e) {break;}
                        System.out.println();
                    } else {
                        chessBoardDemo.movePieces(readFile.getMove()[i][0] - 1, readFile.getMove()[i][1] - 1);
                        System.out.print(readFile.getMove()[i][0] + " " + readFile.getMove()[i][1] + " ");
                        try {
                            Thread.sleep(500);
                        }catch (Exception e) {break;}
                        chessBoardDemo.movePieces(readFile.getMove()[i][2] - 1, readFile.getMove()[i][3] - 1);
                        System.out.print(readFile.getMove()[i][2] + " " + readFile.getMove()[i][3] + " ");
                        MyBoarder.p1 = null;
                        MyBoarder.p2 = null;
                        try {
                            Thread.sleep(500);
                        }catch (Exception e) {break;}
                        System.out.println();
                    }
                }
            }
        });
        Pane4.add(ReadGame4);

        // Add button for saving game
        DiyButton SaveGame2 = new DiyButton("/imageLibrary/ButtonSaveGame_0.png", "/imageLibrary/ButtonSaveGame_1.png");
        SaveGame2.setBounds(20, 330, 326, 115);
        SaveGame2.addMouseListener (new MouseAdapter() {
            @Override
            public void mouseClicked (MouseEvent arg0) {
                fileDialog = new FileDialog(new Frame(),"请选择目录",FileDialog.SAVE);
                fileDialog.setVisible(true);
                String address = fileDialog.getDirectory()+fileDialog.getFile();
                System.out.println(address);
                WriteFile writeFile = new WriteFile(address, MyBoarder.MyPieces, (DoPlayer == '红'? '黑' : '红'));
                writeFile.writeChessboard();
            }
        });
        Pane2.add(SaveGame2);

        DiyButton SaveGame3 = new DiyButton("/imageLibrary/ButtonSaveGame_0.png", "/imageLibrary/ButtonSaveGame_1.png");
        SaveGame3.setBounds(20, 330, 326, 115);
        SaveGame3.addMouseListener (new MouseAdapter() {
            @Override
            public void mouseClicked (MouseEvent arg0) {
                fileDialog = new FileDialog(new Frame(),"请选择目录",FileDialog.SAVE);
                fileDialog.setVisible(true);
                String address = fileDialog.getDirectory()+fileDialog.getFile();
                System.out.println(address);
                WriteFile writeFile = new WriteFile(address, MyBoarder.MyPieces, (DoPlayer == '红'? '黑' : '红'));
                writeFile.writeChessboard();
            }
        });
        Pane3.add(SaveGame3);

        // Add button for backing to the menu
        DiyButton BackToMenu2 = new DiyButton("/imageLibrary/ButtonBackToMenu_0.png", "/imageLibrary/ButtonBackToMenu_1.png");
        BackToMenu2.setBounds(20, 470, 326, 115);
        BackToMenu2.addMouseListener (new MouseAdapter() {
            @Override
            public void mouseClicked (MouseEvent arg0) {
                Pane1.setVisible(true);
                Pane2.setVisible(false);
                Pane3.setVisible(false);
                Pane4.setVisible(false);
                Pane5.setVisible(false);
                DataInit();
                InfBoard.Clear();
                InfBoard.AddLog("红方执子");
                MyCanvas.SendWinner('无');
                MyCanvas.paintImmediately(0, 0, MyCanvas.getWidth(), MyCanvas.getHeight());
                MyTimerThread.interrupt();
            }
        });
        Pane2.add(BackToMenu2);

        DiyButton BackToMenu3 = new DiyButton("/imageLibrary/ButtonBackToMenu_0.png", "/imageLibrary/ButtonBackToMenu_1.png");
        BackToMenu3.setBounds(20, 470, 326, 115);
        BackToMenu3.addMouseListener (new MouseAdapter() {
            @Override
            public void mouseClicked (MouseEvent arg0) {
                Pane1.setVisible(true);
                Pane2.setVisible(false);
                Pane3.setVisible(false);
                Pane4.setVisible(false);
                Pane5.setVisible(false);
                DataInit();
                InfBoard.Clear();
                InfBoard.AddLog("红方执子");
                MyCanvas.SendWinner('无');
                MyCanvas.paintImmediately(0, 0, MyCanvas.getWidth(), MyCanvas.getHeight());
                MyTimerThread.interrupt();
            }
        });
        Pane3.add(BackToMenu3);

        DiyButton BackToMenu4 = new DiyButton("/imageLibrary/ButtonBackToMenu_0.png", "/imageLibrary/ButtonBackToMenu_1.png");
        BackToMenu4.setBounds(20, 330, 326, 115);
        BackToMenu4.addMouseListener (new MouseAdapter() {
            @Override
            public void mouseClicked (MouseEvent arg0) {
                Pane1.setVisible(true);
                Pane2.setVisible(false);
                Pane3.setVisible(false);
                Pane4.setVisible(false);
                Pane5.setVisible(false);
                DataInit();
                InfBoard.Clear();
                InfBoard.AddLog("红方执子");
                MyCanvas.SendWinner('无');
                MyCanvas.paintImmediately(0, 0, MyCanvas.getWidth(), MyCanvas.getHeight());
            }
        });
        Pane4.add(BackToMenu4);

        DiyButton BackToMenu5 = new DiyButton("/imageLibrary/ButtonBackToMenu_0.png", "/imageLibrary/ButtonBackToMenu_1.png");
        BackToMenu5.setBounds(20, 600, 326, 115);
        BackToMenu5.addMouseListener (new MouseAdapter() {
            @Override
            public void mouseClicked (MouseEvent arg0) {
                Pane1.setVisible(true);
                Pane2.setVisible(false);
                Pane3.setVisible(false);
                Pane4.setVisible(false);
                Pane5.setVisible(false);
                DataInit();
            }
        });
        Pane5.add(BackToMenu5);

        // Add button for undo
        DiyButton Undo2 = new DiyButton("/imageLibrary/ButtonUndo_0.png", "/imageLibrary/ButtonUndo_1.png");
        Undo2.setBounds(1010, 550, 120, 120);
        Undo2.addMouseListener (new MouseAdapter() {
            @Override
            public void mouseClicked (MouseEvent arg0) {
                int n = JOptionPane.showConfirmDialog(null, "是否悔棋？", "提示",JOptionPane.YES_NO_OPTION);
                if (n == 0) {
                    for(int i = 0 ; i < MyBoarder.MyPieces.length ; i ++){
                        for(int j = 0 ; j < MyBoarder.MyPieces[0].length ; j ++){
                            MyBoarder.MyPieces[i][j] = TempBoarder.MyPieces[i][j];
                        }
                    }
                    MyCanvas.repaint();
                    if (DoPlayer == '红') {
                        DoPlayer = '黑';
                        InfBoard.AddLog("黑方执子");
                    } else {
                        DoPlayer = '红';
                        InfBoard.AddLog("红方执子");
                    }
                }
            }
        });
        Pane2.add(Undo2);

        DiyButton Undo3 = new DiyButton("/imageLibrary/ButtonUndo_0.png", "/imageLibrary/ButtonUndo_1.png");
        Undo3.setBounds(1010, 550, 120, 120);
        Undo3.addMouseListener (new MouseAdapter() {
            @Override
            public void mouseClicked (MouseEvent arg0) {
                int n = JOptionPane.showConfirmDialog(null, "是否悔棋？", "提示",JOptionPane.YES_NO_OPTION);
                if (n == 0) {
                    for(int i = 0 ; i < MyBoarder.MyPieces.length ; i ++){
                        for(int j = 0 ; j < MyBoarder.MyPieces[0].length ; j ++){
                            MyBoarder.MyPieces[i][j] = TempBoarder.MyPieces[i][j];
                        }
                    }
                    MyCanvas.repaint();
                    if (DoPlayer == '红') {
                        DoPlayer = '黑';
                        InfBoard.AddLog("黑方执子");
                    } else {
                        DoPlayer = '红';
                        InfBoard.AddLog("红方执子");
                    }
                }
            }
        });
        Pane3.add(Undo3);

        // Add button for save chess moves sequences
        DiyButton SaveSeq = new DiyButton("/imageLibrary/ButtonSaveSeq_0.png", "/imageLibrary/ButtonSaveSeq_1.png");
        SaveSeq.setBounds(1210, 550, 120, 120);
        SaveSeq.addMouseListener (new MouseAdapter() {
            @Override
            public void mouseClicked (MouseEvent arg0) {
                try {
                    fileDialog = new FileDialog(new Frame(),"请选择目录",FileDialog.SAVE);
                    fileDialog.setVisible(true);
                    String address = fileDialog.getDirectory()+fileDialog.getFile();
                    FileOutputStream fos = new FileOutputStream(new File(address));
                    OutputStreamWriter osr = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
                    BufferedWriter bw = new BufferedWriter(osr);
                    int counter = 0;
                    bw.write("@TOTAL_STEP=" + (record.size() / 2) + "\n");
                    bw.write("@@\n");
                    bw.write("\n");
                    for(int[] r : record){
                        bw.write(r[0] + " " + r[1]);
                        if (counter % 2 == 0) {
                            bw.write(" ");
                        } else {
                            bw.write("\n");
                        }
                        counter++;
                    }
                    bw.flush();
                    bw.close();
                    JOptionPane.showMessageDialog(
                            contentPane,
                            "保存成功！",
                            "提示",
                            JOptionPane.WARNING_MESSAGE,
                            null
                    );
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        Pane2.add(SaveSeq);
    }

    // Default
    public void DataInit() {
        MenuMode = 0;
        DoPlayer = '红';
        MyBoarder = new ChessBoarder();
        TempBoarder = new ChessBoarder();
    }

    // Get both player name
    public void PlayerName(){
        RedPlayer = JOptionPane.showInputDialog("请输入红方玩家名称");
        if(Pane2.isVisible()){
            BlackPlayer = JOptionPane.showInputDialog("请输入黑方玩家名称");
        }else{
            BlackPlayer = "AI";
        }
    }

    public static void Human_AI() {
        if (MyBoarder.Winner() == '无') {
            // AI turn
            SearchModel searchModel = new SearchModel();
            AlphaBetaNode result = searchModel.search();
            ChessBoardDemo chessBoardDemo = new ChessBoardDemo();
            chessBoardDemo.movePieces(result.from[1], result.from[0]);
            chessBoardDemo.movePieces(result.to[1], result.to[0]);
            MyCanvas.repaint();
            MyBoarder.p1 = null;
            MyBoarder.p2 = null;
        } else if (MyBoarder.Winner() == '红') {
            new Audio(MainFrame.class.getResource("/music/win.wav").getPath().substring(1),false).play();
            MyCanvas.SendWinner('红');
            MainFrame.InfBoard.AddLog("红方获得胜利!");
            System.out.println(MainFrame.RedPlayer);
            RankingList.WriteList(MainFrame.RedPlayer);
        } else if (MyBoarder.Winner() == '黑') {
            new Audio(MainFrame.class.getResource("/music/win.wav").getPath().substring(1),false).play();
            MyCanvas.SendWinner('黑');
            MainFrame.InfBoard.AddLog("黑方获得胜利!");
            System.out.println(MainFrame.BlackPlayer);
            RankingList.WriteList(MainFrame.BlackPlayer);
        }
    }
}
