import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Window.Type;
import java.awt.Frame;
import java.awt.SystemColor;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import net.proteanit.sql.DbUtils;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.HashMap;
import java.awt.event.ActionEvent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenuBar;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.JTable;
import java.util.*;
public class lendertransactions {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					lendertransactions window = new lendertransactions();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public static int x=1,y=1,z=1;
	public static HashMap<String,String>map;
	public static String[]arr;
	public static HashMap<String,String>comquan;
	
	private JTextField textquantity;
	private JTable table;
	private JTextField textamount;
	private JTextField texttkg;
	private JTextField textdescription;
	private JTextField textppk;
	DB db = new DB();
	JLabel lblDummyAddress_1;
	public static String balance="0";
	private JTextField textdate;
	private JTextField textID;
	private JTextField textAMOUNT;
	private JTextField textDESCRIPTION;
	private JTextField textDATE;
	
	public lendertransactions() {
		initialize();
		db.Connect();
		DisplayTable();
	}
	public void DisplayTable()
	{
		try {
			
			int year1 = Integer.parseInt(db.datearr[0]);
			int year2 = year1-1;;
			balance = db.balance(lenderlistadd.details[0], year2+"", year1+"");
			lblDummyAddress_1.setText(balance);
			System.out.println(balance+"   "+year1+"   "+year2);
			
			
			String q="SELECT tid as ID,amount as Amount,description as Description,dates as Date FROM lenderledger WHERE id=?;";
			db.pstmt = db.con.prepareStatement(q);
			db.pstmt.setString(1, lenderlistadd.details[0]);
			db.rs = db.pstmt.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(db.rs));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setBounds(100, 100, 1722, 1095);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblLenderTransactions = new JLabel("Lender Ledger");
		lblLenderTransactions.setHorizontalAlignment(SwingConstants.CENTER);
		lblLenderTransactions.setForeground(Color.BLACK);
		lblLenderTransactions.setFont(new Font("Segoe UI", Font.BOLD, 34));
		lblLenderTransactions.setFocusable(false);
		lblLenderTransactions.setFocusTraversalKeysEnabled(false);
		lblLenderTransactions.setBounds(10, 0, 1520, 48);
		frame.getContentPane().add(lblLenderTransactions);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new EtchedBorder(EtchedBorder.RAISED, SystemColor.windowText, new Color(0, 0, 0)));
		panel.setBounds(10, 58, 530, 736);
		frame.getContentPane().add(panel);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(SystemColor.info);
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		panel_2.setBounds(10, 175, 510, 177);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel quantity = new JLabel("Quantity -");
		quantity.setHorizontalAlignment(SwingConstants.CENTER);
		quantity.setFont(new Font("Segoe UI", Font.BOLD, 23));
		quantity.setBounds(262, 71, 126, 44);
		panel_2.add(quantity);
		
		textquantity = new JTextField();
		textquantity.setHorizontalAlignment(SwingConstants.CENTER);
		textquantity.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		textquantity.setFont(new Font("Segoe UI", Font.PLAIN, 26));
		textquantity.setBounds(395, 71, 105, 44);
		panel_2.add(textquantity);
		textquantity.setColumns(10);
		
		
		
		JComboBox<?> comboBox = new JComboBox();
		comboBox.setBounds(10, 71, 240, 44);
		panel_2.add(comboBox);
		comboBox.setBorder(UIManager.getBorder("DesktopIcon.border"));
		comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 24));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Select composite"}));
		comboBox.setVisible(false);
		quantity.setVisible(false);
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ind=comboBox.getSelectedIndex();
				if(ind==0 || textquantity.getText().isEmpty())
					JOptionPane.showMessageDialog(null, "Please select valid composite or enter a valid quantity!");
				else
				{
					System.out.println(ind);
					long quant = Long.parseLong(textquantity.getText());
					String val = (String)(map.get(arr[ind]));
					long minus = -1*(quant*(Long.parseLong(val)));
					System.out.println(minus);
					String desc = textdescription.getText();
					String date = textdate.getText();
					if(desc.isEmpty())
						desc=val+"x"+quant+" "+arr[ind]+" composite are given";
					
					map = db.getComposites();
					arr = new String[map.size()+1];
					arr[0]="Choose Composite";
					int l=1;
					for(Map.Entry kv:map.entrySet())
					{
						arr[l]=(String)(kv.getKey());
						l+=1;
					}
					comboBox.setModel(new DefaultComboBoxModel(arr));
					comquan = db.getQuantity();
					
					long quanincomposites = Long.parseLong(comquan.get(arr[ind]));
					if((quanincomposites-quant)>-1)
					{
						quanincomposites-=quant;
						boolean a=db.reduceCompositeQuantity(arr[ind],val,quanincomposites);
						boolean b=db.giveComposite(lenderlistadd.details[0],(minus/2)+"",desc,date);
						if(b==true && a==true)
						{
							JOptionPane.showMessageDialog(null, "Composite Transaction is added successfully!\nAvailable Quantity of "+arr[ind]+":"+quanincomposites);
							comboBox.setSelectedIndex(0);
							textquantity.setText("");
							DisplayTable();
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Composite Transaction can't be added!");
							DisplayTable();
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Quantity limit exceeded!\nAvailable Quantity: "+quanincomposites);
					}
				}
			}
		});
		btnSubmit.setBounds(199, 132, 105, 35);
		panel_2.add(btnSubmit);
		btnSubmit.setFont(new Font("Segoe UI", Font.BOLD, 22));
		btnSubmit.setFocusable(false);
		btnSubmit.setFocusTraversalKeysEnabled(false);
		btnSubmit.setFocusPainted(false);
		btnSubmit.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		btnSubmit.setBackground(new Color(102, 204, 255));
		textquantity.setVisible(false);
		btnSubmit.setVisible(false);
		JButton btnComposite = new JButton("Composite");
		btnComposite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				x+=1;
				if(x%2==0)
				{
					comboBox.setVisible(true);
					quantity.setVisible(true);
					textquantity.setVisible(true);
					btnSubmit.setVisible(true);
				}
				else
				{
					comboBox.setVisible(false);
					quantity.setVisible(false);
					textquantity.setVisible(false);
					btnSubmit.setVisible(false);
				}
				if(x%2==0)
				{
					map = db.getComposites();
					arr = new String[map.size()+1];
					arr[0]="Choose Composite";
					int l=1;
					for(Map.Entry kv:map.entrySet())
					{
						arr[l]=(String)(kv.getKey());
						l+=1;
					}
					comboBox.setModel(new DefaultComboBoxModel(arr));
					comquan = db.getQuantity();
				}
				
			}
		});
		btnComposite.setBounds(157, 10, 186, 51);
		panel_2.add(btnComposite);
		btnComposite.setFont(new Font("Segoe UI", Font.BOLD, 28));
		btnComposite.setFocusable(false);
		btnComposite.setFocusTraversalKeysEnabled(false);
		btnComposite.setFocusPainted(false);
		btnComposite.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		btnComposite.setBackground(new Color(255, 204, 204));
		
		JPanel panel_2_1 = new JPanel();
		panel_2_1.setBackground(SystemColor.inactiveCaption);
		panel_2_1.setLayout(null);
		panel_2_1.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		panel_2_1.setBounds(10, 362, 510, 177);
		panel.add(panel_2_1);
		
		JLabel amount = new JLabel("Amount -");
		amount.setHorizontalAlignment(SwingConstants.CENTER);
		amount.setFont(new Font("Segoe UI", Font.BOLD, 26));
		amount.setBounds(85, 70, 158, 44);
		panel_2_1.add(amount);
		
		textamount = new JTextField();
		textamount.setHorizontalAlignment(SwingConstants.CENTER);
		textamount.setFont(new Font("Segoe UI", Font.PLAIN, 26));
		textamount.setColumns(10);
		textamount.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		textamount.setBounds(253, 70, 158, 44);
		panel_2_1.add(textamount);
		
		JButton btnreceived = new JButton("Received");
		btnreceived.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String amount = textamount.getText();
				long val = Long.parseLong(amount);
				String date = textdate.getText();
				if(amount.isEmpty())
				{
					JOptionPane.showMessageDialog(null, "Please enter a valid amount!");
					textamount.setText("");
					textdescription.setText("");
				}
				else
				{
					String desc = textdescription.getText();
					if(desc.isEmpty())
						desc="Cash";
					boolean b=db.receivemoney(lenderlistadd.details[0],val,desc,date);
					if(b==true)
					{
						JOptionPane.showMessageDialog(null, "Amount is received successfully!");
						textamount.setText("");
						textdescription.setText("");
						DisplayTable();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Amount can't received!");
						DisplayTable();
					}
				}
				
			}
		});
		btnreceived.setFont(new Font("Segoe UI", Font.BOLD, 22));
		btnreceived.setFocusable(false);
		btnreceived.setFocusTraversalKeysEnabled(false);
		btnreceived.setFocusPainted(false);
		btnreceived.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		btnreceived.setBackground(new Color(102, 204, 204));
		btnreceived.setBounds(263, 124, 120, 42);
		panel_2_1.add(btnreceived);
		
		
		
		
		JButton btnlend = new JButton("Lend");
		btnlend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String amount = textamount.getText();
				long val = Long.parseLong(amount);
				String date = textdate.getText();
				if(amount.isEmpty())
				{
					JOptionPane.showMessageDialog(null, "Please enter a valid amount!");
					textamount.setText("");
					textdescription.setText("");
				}
				else
				{
					String desc = textdescription.getText();
					if(desc.isEmpty())
						desc="Cash";
					val=-1*val;
					boolean b=db.lendmoney(lenderlistadd.details[0],val,desc,date);
					if(b==true)
					{
						JOptionPane.showMessageDialog(null, "Amount is Lended successfully!");
						textamount.setText("");
						textdescription.setText("");
						DisplayTable();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Amount Lending failed!");
						DisplayTable();
					}
				}
			}
		});
		btnlend.setFont(new Font("Segoe UI", Font.BOLD, 24));
		btnlend.setFocusable(false);
		btnlend.setFocusTraversalKeysEnabled(false);
		btnlend.setFocusPainted(false);
		btnlend.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		btnlend.setBackground(new Color(255, 204, 102));
		btnlend.setBounds(113, 124, 120, 42);
		panel_2_1.add(btnlend);
		
		JPanel panel_2_1_1 = new JPanel();
		panel_2_1_1.setBackground(SystemColor.info);
		panel_2_1_1.setLayout(null);
		panel_2_1_1.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		panel_2_1_1.setBounds(10, 549, 510, 177);
		panel.add(panel_2_1_1);
		
		JLabel tkg = new JLabel("Quantity in Quintal");
		tkg.setBackground(new Color(255, 204, 204));
		tkg.setHorizontalAlignment(SwingConstants.CENTER);
		tkg.setFont(new Font("Segoe UI", Font.BOLD, 20));
		tkg.setBounds(10, 126, 198, 44);
		panel_2_1_1.add(tkg);
		
		texttkg = new JTextField();
		texttkg.setHorizontalAlignment(SwingConstants.CENTER);
		texttkg.setFont(new Font("Segoe UI", Font.PLAIN, 26));
		texttkg.setColumns(10);
		texttkg.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		texttkg.setBounds(211, 124, 126, 44);
		panel_2_1_1.add(texttkg);
		
		JButton btnsub = new JButton("Submit");
		btnsub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ppkg,tkg,desc;
				ppkg=textppk.getText();
				tkg=texttkg.getText();
				desc=textdescription.getText();
				
				if(ppkg.isEmpty() || tkg.isEmpty())
				{
					JOptionPane.showMessageDialog(null, "Please fill the required crop details!");
				}
				else
				{
					double ppp=Double.parseDouble(ppkg),tttkg=Double.parseDouble(tkg);

					if(desc.isEmpty())
						desc="Crop price/quintal: "+ppp+" Quantity of Crop: "+tttkg+"quintal";
					
					double profit=(ppp*tttkg)/2;
					String date=textdate.getText();
					
					Formatter formatter = new Formatter();
					formatter.format("%.2f", profit);
					String amount=formatter.toString();

					
					String tid=db.addProfit(lenderlistadd.details[0],amount,desc,date);
					boolean c=db.addToCrops(lenderlistadd.details[0],tid,ppkg,tkg,amount,date);
					
					if(c==true)
					{
						JOptionPane.showMessageDialog(null, "Crop Calculations are added successfully!");
						textppk.setText("");
						texttkg.setText("");
						textdescription.setText("");
						DisplayTable();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Crop Calculations can't be added");
						DisplayTable();
					}
				}
			}
		});
		btnsub.setFont(new Font("Segoe UI", Font.BOLD, 22));
		btnsub.setFocusable(false);
		btnsub.setFocusTraversalKeysEnabled(false);
		btnsub.setFocusPainted(false);
		btnsub.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		btnsub.setBackground(new Color(102, 204, 255));
		btnsub.setBounds(361, 132, 113, 35);
		panel_2_1_1.add(btnsub);
		
		
		
		JLabel ppk = new JLabel("price/quintal");
		ppk.setBackground(new Color(255, 204, 204));
		ppk.setHorizontalAlignment(SwingConstants.CENTER);
		ppk.setFont(new Font("Segoe UI", Font.BOLD, 23));
		ppk.setBorder(null);
		ppk.setBounds(10, 71, 198, 44);
		panel_2_1_1.add(ppk);
		
		textppk = new JTextField();
		textppk.setHorizontalAlignment(SwingConstants.CENTER);
		textppk.setFont(new Font("Segoe UI", Font.PLAIN, 26));
		textppk.setColumns(10);
		textppk.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		textppk.setBounds(211, 71, 126, 44);
		panel_2_1_1.add(textppk);
		
		JLabel lblNewLabel_1_1_2 = new JLabel("Description");
		lblNewLabel_1_1_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_2.setFont(new Font("Segoe UI", Font.BOLD, 22));
		lblNewLabel_1_1_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblNewLabel_1_1_2.setBounds(10, 10, 136, 59);
		panel.add(lblNewLabel_1_1_2);
		
		textdescription = new JTextField();
		textdescription.setFont(new Font("Segoe UI", Font.PLAIN, 26));
		textdescription.setColumns(10);
		textdescription.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		textdescription.setBounds(156, 10, 364, 59);
		panel.add(textdescription);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				x=1;
				y=1;
				z=1;
				frame.setVisible(false);
				try {
					db.con.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				lenderlistadd.main(null);
			}
		});
		btnBack.setFont(new Font("Segoe UI", Font.BOLD, 28));
		btnBack.setFocusable(false);
		btnBack.setFocusTraversalKeysEnabled(false);
		btnBack.setFocusPainted(false);
		btnBack.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		btnBack.setBackground(new Color(255, 204, 204));
		btnBack.setBounds(1293, 680, 129, 65);
		frame.getContentPane().add(btnBack);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(550, 58, 479, 52);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		panel_1.setBorder(new LineBorder(new Color(51, 153, 255), 3, true));
		
		JLabel lblNewLabel = new JLabel("Name -");
		lblNewLabel.setForeground(new Color(0, 51, 102));
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
		lblNewLabel.setBounds(10, 0, 105, 52);
		panel_1.add(lblNewLabel);
		
		JLabel lblDummyName = new JLabel(lenderlistadd.details[1]);
		lblDummyName.setBackground(new Color(102, 0, 0));
		lblDummyName.setForeground(new Color(102, 0, 0));
		lblDummyName.setFont(new Font("Segoe UI", Font.BOLD, 30));
		lblDummyName.setBounds(125, 0, 375, 52);
		panel_1.add(lblDummyName);
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setLayout(null);
		panel_1_1.setBorder(new LineBorder(new Color(102, 153, 255), 3, true));
		panel_1_1.setBounds(1039, 58, 443, 52);
		frame.getContentPane().add(panel_1_1);
		
		JLabel lblAddress = new JLabel("Address -");
		lblAddress.setForeground(new Color(0, 51, 102));
		lblAddress.setFont(new Font("Segoe UI", Font.BOLD, 30));
		lblAddress.setBounds(10, 0, 136, 52);
		panel_1_1.add(lblAddress);
		
		JLabel lblDummyAddress = new JLabel(lenderlistadd.details[2]);
		lblDummyAddress.setForeground(new Color(102, 0, 0));
		lblDummyAddress.setFont(new Font("Segoe UI", Font.BOLD, 30));
		lblDummyAddress.setBackground(new Color(102, 0, 0));
		lblDummyAddress.setBounds(152, 0, 296, 52);
		panel_1_1.add(lblDummyAddress);
		
		JPanel panel_1_2 = new JPanel();
		panel_1_2.setBounds(1190, 602, 340, 52);
		frame.getContentPane().add(panel_1_2);
		panel_1_2.setLayout(null);
		panel_1_2.setBorder(new LineBorder(new Color(255, 0, 0), 3, true));
		
		JLabel lblBalance = new JLabel("Net Balance:");
		lblBalance.setForeground(new Color(255, 0, 0));
		lblBalance.setFont(new Font("Segoe UI", Font.BOLD, 24));
		lblBalance.setBounds(10, 7, 147, 39);
		panel_1_2.add(lblBalance);
		
		//fromyear.setText((Integer.parseInt(db.datearr[0])-1)+"");
		//toyear.setText(db.datearr[0]);
		
		lblDummyAddress_1 = new JLabel();
		lblDummyAddress_1.setForeground(new Color(102, 0, 0));
		lblDummyAddress_1.setFont(new Font("Segoe UI", Font.BOLD, 24));
		lblDummyAddress_1.setBackground(new Color(102, 0, 0));
		lblDummyAddress_1.setBounds(160, 4, 180, 44);
		panel_1_2.add(lblDummyAddress_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new LineBorder(new Color(130, 135, 144), 3, true));
		scrollPane.setBounds(550, 120, 980, 472);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		
		
		amount.setVisible(false);
		textamount.setVisible(false);
		btnlend.setVisible(false);
		btnreceived.setVisible(false);
		JButton btnMoney = new JButton("Money");
		btnMoney.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				y+=1;
				if(y%2==0)
				{
					amount.setVisible(true);
					textamount.setVisible(true);
					btnlend.setVisible(true);
					btnreceived.setVisible(true);
				}
				else
				{
					amount.setVisible(false);
					textamount.setVisible(false);
					btnlend.setVisible(false);
					btnreceived.setVisible(false);
				}
			}
		});
		btnMoney.setBounds(158, 10, 186, 50);
		panel_2_1.add(btnMoney);
		btnMoney.setFont(new Font("Segoe UI", Font.BOLD, 28));
		btnMoney.setFocusable(false);
		btnMoney.setFocusTraversalKeysEnabled(false);
		btnMoney.setFocusPainted(false);
		btnMoney.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		btnMoney.setBackground(new Color(255, 204, 204));
		
		ppk.setVisible(false);
		tkg.setVisible(false);
		btnsub.setVisible(false);
		textppk.setVisible(false);
		texttkg.setVisible(false);
		JButton btnCrops = new JButton("Crops");
		btnCrops.setBounds(161, 10, 186, 51);
		panel_2_1_1.add(btnCrops);
		btnCrops.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				z+=1;
				if(z%2==0)
				{
					ppk.setVisible(true);
					tkg.setVisible(true);
					btnsub.setVisible(true);
					textppk.setVisible(true);
					texttkg.setVisible(true);
				}
				else
				{
					ppk.setVisible(false);
					tkg.setVisible(false);
					btnsub.setVisible(false);
					textppk.setVisible(false);
					texttkg.setVisible(false);
				}
			}
		});
		btnCrops.setFont(new Font("Segoe UI", Font.BOLD, 28));
		btnCrops.setFocusable(false);
		btnCrops.setFocusTraversalKeysEnabled(false);
		btnCrops.setFocusPainted(false);
		btnCrops.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		btnCrops.setBackground(new Color(255, 204, 204));
		
		JLabel lblNewLabel_1_1_2_1 = new JLabel("Date");
		lblNewLabel_1_1_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_2_1.setFont(new Font("Segoe UI", Font.BOLD, 22));
		lblNewLabel_1_1_2_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblNewLabel_1_1_2_1.setBounds(10, 93, 136, 59);
		panel.add(lblNewLabel_1_1_2_1);
		
		textdate = new JTextField();
		textdate.setHorizontalAlignment(SwingConstants.CENTER);
		textdate.setFont(new Font("Segoe UI", Font.PLAIN, 26));
		textdate.setColumns(10);
		textdate.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		textdate.setBounds(156, 93, 183, 59);
		panel.add(textdate);
		
		table.setFont(new Font("Segoe UI",Font.PLAIN, 18));
		table.setRowHeight(40);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panel_3.setBackground(SystemColor.inactiveCaption);
		panel_3.setBounds(550, 602, 630, 192);
		frame.getContentPane().add(panel_3);
		panel_3.setLayout(null);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tid = textID.getText();
				String amount = textAMOUNT.getText();
				String description = textDESCRIPTION.getText();
				String datee = textDATE.getText();
				if(tid.isEmpty())
				{
					JOptionPane.showMessageDialog(null, "Please select a transaction from the above table!");
				}
				else
				{
					boolean b=db.editTransaction(tid,amount,description,datee);
					if(b==true)
					{
						JOptionPane.showMessageDialog(null, "Transaction is updated successfully!");
						textID.setText("");
						textAMOUNT.setText("");
						textDESCRIPTION.setText("");
						textDATE.setText("");
						DisplayTable();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Transaction can't be updated due to technical issue!");
						textID.setText("");
						textAMOUNT.setText("");
						textDESCRIPTION.setText("");
						textDATE.setText("");
						DisplayTable();
					}
				}
			}
		});
		btnUpdate.setFont(new Font("Segoe UI", Font.BOLD, 24));
		btnUpdate.setFocusable(false);
		btnUpdate.setFocusTraversalKeysEnabled(false);
		btnUpdate.setFocusPainted(false);
		btnUpdate.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		btnUpdate.setBackground(new Color(255, 204, 204));
		btnUpdate.setBounds(20, 136, 186, 46);
		panel_3.add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tid = textID.getText();
				String amount = textAMOUNT.getText();
				String description = textDESCRIPTION.getText();
				String datee = textDATE.getText();
				if(tid.isEmpty())
				{
					JOptionPane.showMessageDialog(null, "Please select a transaction from the above table!");
				}
				else
				{
					boolean c=db.deleteCropsUsingLender(lenderlistadd.details[0], tid);
					boolean b=db.deleteTransaction(tid);
					if(b==true && c==true)
					{
						JOptionPane.showMessageDialog(null, "Transaction is deleted successfully!");
						textID.setText("");
						textAMOUNT.setText("");
						textDESCRIPTION.setText("");
						textDATE.setText("");
						DisplayTable();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Transaction can't be deleted due to technical issue!");
						textID.setText("");
						textAMOUNT.setText("");
						textDESCRIPTION.setText("");
						textDATE.setText("");
						DisplayTable();
					}
				}
			}
		});
		btnDelete.setFont(new Font("Segoe UI", Font.BOLD, 24));
		btnDelete.setFocusable(false);
		btnDelete.setFocusTraversalKeysEnabled(false);
		btnDelete.setFocusPainted(false);
		btnDelete.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		btnDelete.setBackground(new Color(255, 204, 204));
		btnDelete.setBounds(227, 136, 186, 46);
		panel_3.add(btnDelete);
		
		JLabel lblNewLabel_1 = new JLabel("ID");
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.BOLD, 28));
		lblNewLabel_1.setBounds(10, 20, 30, 46);
		panel_3.add(lblNewLabel_1);
		
		textID = new JTextField();
		textID.setEditable(false);
		textID.setBorder(new LineBorder(SystemColor.textText, 2, true));
		textID.setFont(new Font("Segoe UI", Font.PLAIN, 26));
		textID.setBounds(50, 20, 72, 46);
		panel_3.add(textID);
		textID.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Amount");
		lblNewLabel_1_1.setFont(new Font("Segoe UI", Font.BOLD, 28));
		lblNewLabel_1_1.setBounds(132, 20, 114, 46);
		panel_3.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Description");
		lblNewLabel_1_2.setFont(new Font("Segoe UI", Font.BOLD, 28));
		lblNewLabel_1_2.setBounds(10, 76, 157, 46);
		panel_3.add(lblNewLabel_1_2);
		
		textAMOUNT = new JTextField();
		textAMOUNT.setFont(new Font("Segoe UI", Font.PLAIN, 24));
		textAMOUNT.setColumns(10);
		textAMOUNT.setBorder(new LineBorder(SystemColor.textText, 2, true));
		textAMOUNT.setBounds(256, 20, 125, 46);
		panel_3.add(textAMOUNT);
		
		textDESCRIPTION = new JTextField();
		textDESCRIPTION.setFont(new Font("Segoe UI", Font.PLAIN, 26));
		textDESCRIPTION.setColumns(10);
		textDESCRIPTION.setBorder(new LineBorder(SystemColor.textText, 2, true));
		textDESCRIPTION.setBounds(177, 76, 443, 46);
		panel_3.add(textDESCRIPTION);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Date");
		lblNewLabel_1_1_1.setFont(new Font("Segoe UI", Font.BOLD, 28));
		lblNewLabel_1_1_1.setBounds(391, 20, 72, 46);
		panel_3.add(lblNewLabel_1_1_1);
		
		textDATE = new JTextField();
		textDATE.setFont(new Font("Segoe UI", Font.PLAIN, 24));
		textDATE.setColumns(10);
		textDATE.setBorder(new LineBorder(SystemColor.textText, 2, true));
		textDATE.setBounds(463, 20, 157, 46);
		panel_3.add(textDATE);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textID.setText(null);
				textAMOUNT.setText(null);
				textDESCRIPTION.setText(null);
				textDATE.setText(null);
			}
		});
		btnClear.setFont(new Font("Segoe UI", Font.BOLD, 24));
		btnClear.setFocusable(false);
		btnClear.setFocusTraversalKeysEnabled(false);
		btnClear.setFocusPainted(false);
		btnClear.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		btnClear.setBackground(new Color(255, 204, 204));
		btnClear.setBounds(434, 136, 186, 46);
		panel_3.add(btnClear);
		
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int column = 0;
				int row = table.getSelectedRow();
				String indone = table.getModel().getValueAt(row, column).toString();
				column=1;
				String indtwo = table.getModel().getValueAt(row, column).toString();
				column=2;
				String indthree = table.getModel().getValueAt(row, column).toString();
				column=3;
				String indfour = table.getModel().getValueAt(row, column).toString();
				
				textID.setText(indone);
				textAMOUNT.setText(indtwo);
				textDESCRIPTION.setText(indthree);
				textDATE.setText(indfour);
			}
		});
		textdate.setText(DB.dateee);
		JTableHeader th = table.getTableHeader();
		th.setFont(new Font("Segoe UI", Font.BOLD, 20));
		((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer())
	    .setHorizontalAlignment(JLabel.LEFT);
	}
}
