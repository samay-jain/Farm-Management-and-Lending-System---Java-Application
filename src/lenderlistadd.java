import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Frame;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.JPanel;
import java.awt.SystemColor;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import net.proteanit.sql.DbUtils;

import javax.swing.JButton;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.ComponentOrientation;

public class lenderlistadd {

	private JFrame frame;
	private JTable table;
	private JTextField textname;
	private JTextField textid;
	private JTextField textName;
	private JTextField textaddress;
	private JTextField textmob;

	/**
	 * Launch the application.
	 */
	public static String details[] = new String[4];
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					lenderlistadd window = new lenderlistadd();
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
	DB db = new DB();
	private JTextField textacres;
	public lenderlistadd() {
		initialize();
		db.Connect();
		DisplayTable();
	}
	public void DisplayTable()
	{
		try {
			String q="SELECT id as ID,name as NAME,address as ADDRESS,mobileno as CONTACT_NO,acres as ACRES FROM lenderlist;";
			db.pstmt = db.con.prepareStatement(q);
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
		frame.getContentPane().setMinimumSize(new Dimension(2147483647, 2147483647));
		frame.getContentPane().setLayout(null);
		
		JLabel lblLendersList = new JLabel("  Lender's List");
		lblLendersList.setFocusTraversalKeysEnabled(false);
		lblLendersList.setFocusable(false);
		lblLendersList.setIcon(new ImageIcon("image\\bor5.png"));
		lblLendersList.setBounds(0, 20, 1540, 43);
		lblLendersList.setHorizontalAlignment(SwingConstants.CENTER);
		lblLendersList.setForeground(new Color(0, 0, 0));
		lblLendersList.setFont(new Font("Segoe UI", Font.BOLD, 38));
		frame.getContentPane().add(lblLendersList);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new EtchedBorder(EtchedBorder.RAISED, SystemColor.controlDkShadow, SystemColor.textText));
		scrollPane.setBackground(SystemColor.scrollbar);
		scrollPane.setBounds(510, 88, 977, 551);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setBackground(SystemColor.control);
		table.setGridColor(SystemColor.activeCaption);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.RAISED, SystemColor.windowText, new Color(0, 0, 0)));
		panel.setBounds(57, 649, 802, 125);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String name,address,mob,acres;
				name=textName.getText();
				address=textaddress.getText();
				mob=textmob.getText();
				acres=textacres.getText();
				if(name.isEmpty() || address.isEmpty() || mob.isEmpty() || acres.isEmpty())
				{
					JOptionPane.showMessageDialog(null, "Please enter all the required details!");
				}
				else
				{
					try {
						long num = Long.parseLong(mob);
					}
					catch(NumberFormatException e1)
					{
						JOptionPane.showMessageDialog(null, "Please enter proper value as Mobile Number, string can't be considered as number!");
						textmob.setText("");
						return;
					}
					boolean b=db.addlenderlist(name,address,mob,acres);
					if(b==true)
					{
						JOptionPane.showMessageDialog(null, "Lender added successfully!");
						textName.setText("");
						textaddress.setText("");
						textmob.setText("");
						textacres.setText("");
						DisplayTable();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Unable to add Lender due to some Technical issue!");
						DisplayTable();
					}
				}
				
			}
		});
		btnAdd.setFocusPainted(false);
		btnAdd.setFocusTraversalKeysEnabled(false);
		btnAdd.setFocusable(false);
		btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 28));
		btnAdd.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		btnAdd.setBackground(new Color(255, 204, 204));
		btnAdd.setBounds(52, 29, 129, 65);
		panel.add(btnAdd);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id=textid.getText();
				String name=textName.getText();
				String address=textaddress.getText();
				String mob=textmob.getText();
				String acres=textacres.getText();
				if(id.isEmpty() || name.isEmpty() | address.isEmpty() || mob.isEmpty() || acres.isEmpty())
				{
					JOptionPane.showMessageDialog(null, "Please select a row from the Lender table!");
				}
				else
				{
					try {
						long num = Long.parseLong(mob);
					}
					catch(NumberFormatException e1)
					{
						JOptionPane.showMessageDialog(null, "Please enter proper value as Mobile Number, string can't be considered as number!");
						textmob.setText("");
						return;
					}
					boolean b=db.UpdateLender(id,name,address,mob,acres);
					if(b==true)
					{
						JOptionPane.showMessageDialog(null, "Lender details updated successfully!");
						textid.setText("");
						textName.setText("");
						textaddress.setText("");
						textmob.setText("");
						textacres.setText("");
						DisplayTable();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Lender details can't be updated");
					}
				}
			}
		});
		btnUpdate.setFocusPainted(false);
		btnUpdate.setFocusTraversalKeysEnabled(false);
		btnUpdate.setFocusable(false);
		btnUpdate.setFont(new Font("Segoe UI", Font.BOLD, 28));
		btnUpdate.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		btnUpdate.setBackground(new Color(255, 204, 204));
		btnUpdate.setBounds(239, 29, 129, 65);
		panel.add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id=textid.getText();
				String name=textName.getText();
				String address=textaddress.getText();
				String mob=textmob.getText();
				String acres=textacres.getText();
				if(id.isEmpty() || name.isEmpty() | address.isEmpty() || mob.isEmpty() || acres.isEmpty())
				{
					JOptionPane.showMessageDialog(null, "Please select a row from the Lender table!");
				}
				else
				{
					boolean b=db.DeleteLenderList(id,name);
					if(b==true)
					{
						JOptionPane.showMessageDialog(null, "Selected Lender is deleted successfully!");
						textid.setText("");
						textName.setText("");
						textaddress.setText("");
						textmob.setText("");
						textacres.setText("");
						DisplayTable();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Selected Lender can't be deleted!");
					}
				}
			}
		});
		btnDelete.setFocusPainted(false);
		btnDelete.setFocusTraversalKeysEnabled(false);
		btnDelete.setFocusable(false);
		btnDelete.setFont(new Font("Segoe UI", Font.BOLD, 28));
		btnDelete.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		btnDelete.setBackground(new Color(255, 204, 204));
		btnDelete.setBounds(432, 29, 129, 65);
		panel.add(btnDelete);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textid.setText("");
				textname.setText("");
				textName.setText("");
				textaddress.setText("");
				textmob.setText("");
				textacres.setText("");
			}
		});
		btnClear.setFont(new Font("Segoe UI", Font.BOLD, 28));
		btnClear.setFocusable(false);
		btnClear.setFocusTraversalKeysEnabled(false);
		btnClear.setFocusPainted(false);
		btnClear.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		btnClear.setBackground(new Color(255, 204, 204));
		btnClear.setBounds(625, 29, 129, 65);
		panel.add(btnClear);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(0, 0, 0), new Color(0, 0, 0)), "Edit Lender Details", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 120, 215)));
		panel_1.setBounds(57, 179, 443, 460);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblCompositeId = new JLabel("ID");
		lblCompositeId.setFont(new Font("Segoe UI", Font.BOLD, 30));
		lblCompositeId.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		lblCompositeId.setBounds(10, 35, 51, 54);
		panel_1.add(lblCompositeId);
		
		textid = new JTextField();
		textid.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		textid.setEditable(false);
		textid.setColumns(10);
		textid.setBounds(71, 35, 98, 56);
		panel_1.add(textid);
		
		JLabel lblNewLabel = new JLabel("Name");
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
		lblNewLabel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		lblNewLabel.setBounds(10, 118, 123, 54);
		panel_1.add(lblNewLabel);
		
		textName = new JTextField();
		textName.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		textName.setFocusTraversalPolicyProvider(true);
		textName.setFocusCycleRoot(true);
		textName.setColumns(10);
		textName.setBounds(137, 118, 296, 56);
		panel_1.add(textName);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setFont(new Font("Segoe UI", Font.BOLD, 30));
		lblAddress.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		lblAddress.setBounds(10, 213, 123, 54);
		panel_1.add(lblAddress);
		
		JLabel lblMobileNo = new JLabel("Mobile No.");
		lblMobileNo.setFont(new Font("Segoe UI", Font.BOLD, 30));
		lblMobileNo.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		lblMobileNo.setBounds(10, 301, 159, 54);
		panel_1.add(lblMobileNo);
		
		textaddress = new JTextField();
		textaddress.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		textaddress.setFocusTraversalPolicyProvider(true);
		textaddress.setFocusCycleRoot(true);
		textaddress.setColumns(10);
		textaddress.setBounds(137, 213, 296, 56);
		panel_1.add(textaddress);
		
		textmob = new JTextField();
		textmob.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		textmob.setFocusTraversalPolicyProvider(true);
		textmob.setFocusCycleRoot(true);
		textmob.setColumns(10);
		textmob.setBounds(177, 301, 256, 56);
		panel_1.add(textmob);
		
		JLabel lblAcres = new JLabel("Acres");
		lblAcres.setFont(new Font("Segoe UI", Font.BOLD, 30));
		lblAcres.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		lblAcres.setBounds(10, 381, 89, 54);
		panel_1.add(lblAcres);
		
		textacres = new JTextField();
		textacres.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		textacres.setFocusTraversalPolicyProvider(true);
		textacres.setFocusCycleRoot(true);
		textacres.setColumns(10);
		textacres.setBounds(109, 381, 256, 56);
		panel_1.add(textacres);
		
		textname = new JTextField();
		textname.setFocusCycleRoot(true);
		textname.setFocusTraversalPolicyProvider(true);
		textname.setFont(new Font("SansSerif", Font.BOLD, 20));
		textname.setBorder(new TitledBorder(null, "Search Name", TitledBorder.LEADING, TitledBorder.TOP, null, SystemColor.textHighlight));
		textname.setBounds(57, 88, 353, 63);
		frame.getContentPane().add(textname);
		textname.setColumns(10);
		
		JButton btnViewLenderDetails_1 = new JButton("Search");
		btnViewLenderDetails_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name=textname.getText();
				if(name.isEmpty())
				{
					JOptionPane.showMessageDialog(null, "Please enter a name to be searched!");
					DisplayTable();
				}
				else
				{
					try {
						String q="SELECT id as ID,name as NAME,address as ADDRESS,mobileno as CONTACT_NO,acres as ACRES FROM lenderlist where name like '%"+name+"%';";
						db.pstmt = db.con.prepareStatement(q);
						db.rs = db.pstmt.executeQuery();
						table.setModel(DbUtils.resultSetToTableModel(db.rs));
					}
					catch(Exception e2)
					{
						e2.printStackTrace();
					}
				}
			}
		});
		btnViewLenderDetails_1.setFocusable(false);
		btnViewLenderDetails_1.setFocusTraversalKeysEnabled(false);
		btnViewLenderDetails_1.setFocusPainted(false);
		btnViewLenderDetails_1.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		btnViewLenderDetails_1.setAutoscrolls(true);
		btnViewLenderDetails_1.setIcon(null);
		btnViewLenderDetails_1.setFont(new Font("Segoe UI", Font.BOLD, 22));
		btnViewLenderDetails_1.setBackground(new Color(255, 204, 204));
		btnViewLenderDetails_1.setBounds(413, 88, 87, 63);
		frame.getContentPane().add(btnViewLenderDetails_1);
		
		JButton btnViewLenderDetails = new JButton("View Lender Details");
		btnViewLenderDetails.setFocusPainted(false);
		btnViewLenderDetails.setFocusTraversalKeysEnabled(false);
		btnViewLenderDetails.setFocusable(false);
		btnViewLenderDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				details[0]=textid.getText();
				details[1]=textName.getText();
				details[2]=textaddress.getText();
				details[3]=textmob.getText();
				
				if(details[0].isEmpty() || details[1].isEmpty() || details[2].isEmpty() || details[3].isEmpty())
					JOptionPane.showMessageDialog(null, "Please select a Lender from the table to view details!");
				else
				{
					frame.setVisible(false);
					try {
						db.con.close();
					} catch (SQLException e1) {
					// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					lendertransactions.main(null);
				}
			}
		});
		btnViewLenderDetails.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		btnViewLenderDetails.setBounds(1201, 649, 286, 65);
		frame.getContentPane().add(btnViewLenderDetails);
		btnViewLenderDetails.setFont(new Font("Segoe UI", Font.BOLD, 28));
		btnViewLenderDetails.setBackground(new Color(255, 204, 204));
		
		JButton btnBack = new JButton("Back");
		btnBack.setFocusable(false);
		btnBack.setFocusTraversalKeysEnabled(false);
		btnBack.setFocusPainted(false);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				try {
					db.con.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dashboard.main(null);
			}
		});
		btnBack.setFont(new Font("Segoe UI", Font.BOLD, 28));
		btnBack.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		btnBack.setBackground(new Color(255, 204, 204));
		btnBack.setBounds(964, 649, 129, 65);
		frame.getContentPane().add(btnBack);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setBounds(100, 100, 1725, 1108);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
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
				column=4;
				String indfive = table.getModel().getValueAt(row, column).toString();
				
				textid.setText(indone);
				textName.setText(indtwo);
				textaddress.setText(indthree);
				textmob.setText(indfour);
				textacres.setText(indfive);
			}
		});
		
		table.setFont(new Font("Segoe UI",Font.PLAIN, 18));
		table.setRowHeight(40);
		
		JButton btnViewCropDetails = new JButton("View Crop Details");
		btnViewCropDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				details[0]=textid.getText();
				details[1]=textName.getText();
				details[2]=textaddress.getText();
				details[3]=textmob.getText();
				
				if(details[0].isEmpty() || details[1].isEmpty() || details[2].isEmpty() || details[3].isEmpty())
					JOptionPane.showMessageDialog(null, "Please select a Lender from the table to view details!");
				else
				{
					frame.setVisible(false);
					try {
						db.con.close();
					} catch (SQLException e1) {
					// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					cropdetails.main(null);
				}
			}
		});
		btnViewCropDetails.setFont(new Font("Segoe UI", Font.BOLD, 28));
		btnViewCropDetails.setFocusable(false);
		btnViewCropDetails.setFocusTraversalKeysEnabled(false);
		btnViewCropDetails.setFocusPainted(false);
		btnViewCropDetails.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		btnViewCropDetails.setBackground(new Color(255, 204, 204));
		btnViewCropDetails.setBounds(1201, 724, 286, 65);
		frame.getContentPane().add(btnViewCropDetails);
		JTableHeader th = table.getTableHeader();
		th.setFont(new Font("Segoe UI", Font.BOLD, 20));
		((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer())
	    .setHorizontalAlignment(JLabel.LEFT);
	}
}

