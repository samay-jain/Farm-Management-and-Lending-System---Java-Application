import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Frame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import java.awt.SystemColor;
import javax.swing.border.EtchedBorder;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import net.proteanit.sql.DbUtils;

import javax.swing.JButton;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import java.awt.ComponentOrientation;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class compositeDB {

	private JFrame frame;
	private JTextField textname;
	private JTextField textprice;
	private JTextField textid;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					compositeDB window = new compositeDB();
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
	private JTable table;
	private JTextField textquantity;
	public compositeDB() {
		initialize();
		db.Connect();
		DisplayTable();
	}
	public void DisplayTable()
	{
		try {
			String q="SELECT * FROM composite;";
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
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setBounds(100, 100, 1755, 1096);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblCon = new JLabel("Composite List");
		lblCon.setHorizontalAlignment(SwingConstants.CENTER);
		lblCon.setForeground(Color.BLACK);
		lblCon.setFont(new Font("Segoe UI", Font.BOLD, 40));
		lblCon.setFocusable(false);
		lblCon.setFocusTraversalKeysEnabled(false);
		lblCon.setBounds(10, 10, 1540, 43);
		frame.getContentPane().add(lblCon);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(0, 0, 0), new Color(0, 0, 0)), "Oprations on Composites", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 120, 215)));
		panel_1.setBounds(54, 63, 623, 395);
		frame.getContentPane().add(panel_1);
		
		JLabel lblNewLabel = new JLabel("Name");
		lblNewLabel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
		lblNewLabel.setBounds(38, 138, 139, 54);
		panel_1.add(lblNewLabel);
		
		textname = new JTextField();
		textname.setFocusCycleRoot(true);
		textname.setFocusTraversalPolicyProvider(true);
		textname.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		textname.setBounds(187, 138, 397, 56);
		panel_1.add(textname);
		textname.setColumns(10);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setFont(new Font("Segoe UI", Font.BOLD, 30));
		lblPrice.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		lblPrice.setBounds(38, 226, 139, 54);
		panel_1.add(lblPrice);
		
		textprice = new JTextField();
		textprice.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		textprice.setColumns(10);
		textprice.setBounds(187, 226, 397, 56);
		panel_1.add(textprice);
		
		JLabel lblCompositeId = new JLabel("ID");
		lblCompositeId.setFont(new Font("Segoe UI", Font.BOLD, 30));
		lblCompositeId.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		lblCompositeId.setBounds(243, 32, 45, 54);
		panel_1.add(lblCompositeId);
		
		textid = new JTextField();
		textid.setEditable(false);
		textid.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		textid.setColumns(10);
		textid.setBounds(290, 32, 93, 56);
		panel_1.add(textid);
		
		JLabel lblQuantity = new JLabel("Quantity");
		lblQuantity.setFont(new Font("Segoe UI", Font.BOLD, 30));
		lblQuantity.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		lblQuantity.setBounds(38, 309, 139, 54);
		panel_1.add(lblQuantity);
		
		textquantity = new JTextField();
		textquantity.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		textquantity.setColumns(10);
		textquantity.setBounds(187, 309, 397, 56);
		panel_1.add(textquantity);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new EtchedBorder(EtchedBorder.RAISED, SystemColor.windowText, new Color(0, 0, 0)));
		panel.setBounds(54, 468, 620, 218);
		frame.getContentPane().add(panel);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String id,name,price,quantity;
				id=textid.getText();
				name=textname.getText();
				price=textprice.getText();
				quantity=textquantity.getText();
				if(name.isEmpty() || price.isEmpty() || quantity.isEmpty())
				{
					JOptionPane.showMessageDialog(null, "Please enter all the required details!");
				}
				else
				{
					try {
						long num = Long.parseLong(price);
						long num1 = Long.parseLong(quantity);
					}
					catch(NumberFormatException e1)
					{
						JOptionPane.showMessageDialog(null, "Please enter proper value in the field of Price or Quantity!");
						textprice.setText("");
						textquantity.setText("");
						return;
					}
					boolean b=db.addComposite(name,price,quantity);
					if(b==true)
					{
						JOptionPane.showMessageDialog(null, "Composite added successfully!");
						textname.setText("");
						textprice.setText("");
						textquantity.setText("");
						DisplayTable();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Unable to add Composite due to some Technical issue!");
						DisplayTable();
					}
				}
			}
		});
		btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 28));
		btnAdd.setFocusable(false);
		btnAdd.setFocusTraversalKeysEnabled(false);
		btnAdd.setFocusPainted(false);
		btnAdd.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		btnAdd.setBackground(new Color(255, 204, 204));
		btnAdd.setBounds(42, 45, 129, 65);
		panel.add(btnAdd);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id=textid.getText();
				String name=textname.getText();
				String price=textprice.getText();
				String quantity=textquantity.getText();
				if(id.isEmpty())
				{
					JOptionPane.showMessageDialog(null, "Please select a row from the composite table!");
				}
				else
				{
					try {
						long num = Long.parseLong(price);
						long num1 = Long.parseLong(quantity);
					}
					catch(NumberFormatException e1)
					{
						JOptionPane.showMessageDialog(null, "Please enter proper value in the field of Price or Quantity!");
						textprice.setText("");
						textquantity.setText("");
						return;
					}
					boolean b=db.UpdateComposites(id,name,price,quantity);
					if(b==true)
					{
						JOptionPane.showMessageDialog(null, "Composite updated successfully!");
						textid.setText("");
						textname.setText("");
						textprice.setText("");
						textquantity.setText("");
						DisplayTable();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Composite can't be updated");
					}
				}
			}
		});
		btnUpdate.setFont(new Font("Segoe UI", Font.BOLD, 28));
		btnUpdate.setFocusable(false);
		btnUpdate.setFocusTraversalKeysEnabled(false);
		btnUpdate.setFocusPainted(false);
		btnUpdate.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		btnUpdate.setBackground(new Color(255, 204, 204));
		btnUpdate.setBounds(248, 45, 129, 65);
		panel.add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id=textid.getText();
				String name=textname.getText();
				String price=textprice.getText();
				String quantity=textquantity.getText();
				if(id.isEmpty() || name.isEmpty() || price.isEmpty() || quantity.isEmpty())
				{
					JOptionPane.showMessageDialog(null, "Please select a row from the composite table!");
				}
				else
				{
					boolean b=db.DeleteComposites(id,name,price,quantity);
					if(b==true)
					{
						JOptionPane.showMessageDialog(null, "Composite deleted successfully!");
						textid.setText("");
						textname.setText("");
						textprice.setText("");
						textquantity.setText("");
						DisplayTable();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Composite can't be deleted!");
					}
				}
			}
		});
		btnDelete.setFont(new Font("Segoe UI", Font.BOLD, 28));
		btnDelete.setFocusable(false);
		btnDelete.setFocusTraversalKeysEnabled(false);
		btnDelete.setFocusPainted(false);
		btnDelete.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		btnDelete.setBackground(new Color(255, 204, 204));
		btnDelete.setBounds(453, 45, 129, 65);
		panel.add(btnDelete);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textid.setText("");
				textname.setText("");
				textprice.setText("");
				textquantity.setText("");
			}
		});
		btnClear.setFont(new Font("Segoe UI", Font.BOLD, 28));
		btnClear.setFocusable(false);
		btnClear.setFocusTraversalKeysEnabled(false);
		btnClear.setFocusPainted(false);
		btnClear.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		btnClear.setBackground(new Color(255, 204, 204));
		btnClear.setBounds(248, 132, 129, 65);
		panel.add(btnClear);
		
		JButton btnBack = new JButton("Back");
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
		btnBack.setFocusable(false);
		btnBack.setFocusTraversalKeysEnabled(false);
		btnBack.setFocusPainted(false);
		btnBack.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		btnBack.setBackground(new Color(255, 204, 204));
		btnBack.setBounds(687, 698, 129, 65);
		frame.getContentPane().add(btnBack);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new EtchedBorder(EtchedBorder.RAISED, Color.DARK_GRAY, new Color(0, 0, 0)));
		scrollPane.setBounds(687, 63, 802, 623);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
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
				textid.setText(indone);
				textname.setText(indtwo);
				textprice.setText(indthree);
				textquantity.setText(indfour);
			}
		});
		scrollPane.setViewportView(table);
		table.setFont(new Font("Segoe UI",Font.PLAIN, 18));
		table.setRowHeight(40);
		JTableHeader th = table.getTableHeader();
		th.setFont(new Font("Segoe UI", Font.BOLD, 20));
		((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer())
	    .setHorizontalAlignment(JLabel.LEFT);
	}
}
