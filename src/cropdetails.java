import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Frame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import java.awt.SystemColor;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import net.proteanit.sql.DbUtils;

import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

public class cropdetails {

	private JFrame frame;
	private JTextField textID;
	private JTextField textPPK;
	private JTextField textKG;
	private JTextField textDATE;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					cropdetails window = new cropdetails();
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
	private JTextField texttid;
	private JTextField textdescription;
	public cropdetails() {
		initialize();
		db.Connect();
		DisplayTable();
	}
	public void DisplayTable()
	{
		try {
			String q="SELECT id as ID,tid as Txn_ID,priceperkg as Price_per_quintal,quantityinkg as Quantity_in_Quintal,profit as Profit,dates as Date FROM crops WHERE ID=?;";
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
		frame.getContentPane().setLayout(null);
		
		JLabel lblCropDetails = new JLabel("Crop Details");
		lblCropDetails.setBounds(0, 21, 1540, 46);
		lblCropDetails.setHorizontalAlignment(SwingConstants.CENTER);
		lblCropDetails.setForeground(Color.BLACK);
		lblCropDetails.setFont(new Font("Segoe UI", Font.BOLD, 34));
		lblCropDetails.setFocusable(false);
		lblCropDetails.setFocusTraversalKeysEnabled(false);
		frame.getContentPane().add(lblCropDetails);
		
		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panel_3.setBackground(SystemColor.inactiveCaption);
		panel_3.setBounds(120, 94, 630, 586);
		frame.getContentPane().add(panel_3);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id=textID.getText();
				String tid=texttid.getText();
				String ppk=textPPK.getText();
				String kg=textKG.getText();
				String date=textDATE.getText();
				String desc=textdescription.getText();
				if(id.isEmpty() || tid.isEmpty() || ppk.isEmpty() || kg.isEmpty() || date.isEmpty())
				{
					JOptionPane.showMessageDialog(null, "Please select a row from the Lender table!");
				}
				else if(desc.isEmpty())
					JOptionPane.showMessageDialog(null, "Please write to description which will be updated in lenderledger!");
				else
				{
					boolean b=db.editCrops(id,ppk, kg, date,tid,desc);
					if(b==true)
					{
						JOptionPane.showMessageDialog(null, "Lender details updated successfully!");
						textID.setText("");
						textPPK.setText("");
						textKG.setText("");
						textDATE.setText("");
						texttid.setText("");
						textdescription.setText("");
						DisplayTable();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Lender details can't be updated");
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
		btnUpdate.setBounds(50, 430, 186, 61);
		panel_3.add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id=textID.getText();
				String tid=texttid.getText();
				String ppk=textPPK.getText();
				String kg=textKG.getText();
				String date=textDATE.getText();
				if(id.isEmpty() || tid.isEmpty())
				{
					JOptionPane.showMessageDialog(null, "Please select a row from the Crops Details table!");
				}
				else
				{
					boolean b=db.deleteCrops(id, ppk, kg, date,tid);
					if(b==true)
					{
						JOptionPane.showMessageDialog(null, "Selected Details are deleted successfully!");
						textID.setText("");
						textPPK.setText("");
						textKG.setText("");
						textDATE.setText("");
						texttid.setText("");
						DisplayTable();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Selected Details can't be deleted!");
						DisplayTable();
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
		btnDelete.setBounds(335, 430, 186, 61);
		panel_3.add(btnDelete);
		
		JLabel lblNewLabel_1 = new JLabel("ID");
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.BOLD, 28));
		lblNewLabel_1.setBounds(10, 20, 30, 46);
		panel_3.add(lblNewLabel_1);
		
		textID = new JTextField();
		textID.setEditable(false);
		textID.setFont(new Font("Segoe UI", Font.PLAIN, 26));
		textID.setColumns(10);
		textID.setBorder(new LineBorder(SystemColor.textText, 2, true));
		textID.setBounds(50, 20, 72, 46);
		panel_3.add(textID);
		
		JLabel lblNewLabel_1_1 = new JLabel("Price per quintal");
		lblNewLabel_1_1.setFont(new Font("Segoe UI", Font.BOLD, 28));
		lblNewLabel_1_1.setBounds(10, 86, 226, 46);
		panel_3.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Quantity of Crop in quintal");
		lblNewLabel_1_2.setFont(new Font("Segoe UI", Font.BOLD, 28));
		lblNewLabel_1_2.setBounds(10, 154, 376, 46);
		panel_3.add(lblNewLabel_1_2);
		
		textPPK = new JTextField();
		textPPK.setFont(new Font("Segoe UI", Font.PLAIN, 24));
		textPPK.setColumns(10);
		textPPK.setBorder(new LineBorder(SystemColor.textText, 2, true));
		textPPK.setBounds(256, 88, 125, 46);
		panel_3.add(textPPK);
		
		textKG = new JTextField();
		textKG.setFont(new Font("Segoe UI", Font.PLAIN, 26));
		textKG.setColumns(10);
		textKG.setBorder(new LineBorder(SystemColor.textText, 2, true));
		textKG.setBounds(396, 155, 224, 46);
		panel_3.add(textKG);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Date");
		lblNewLabel_1_1_1.setFont(new Font("Segoe UI", Font.BOLD, 28));
		lblNewLabel_1_1_1.setBounds(10, 225, 72, 46);
		panel_3.add(lblNewLabel_1_1_1);
		
		textDATE = new JTextField();
		textDATE.setFont(new Font("Segoe UI", Font.PLAIN, 24));
		textDATE.setColumns(10);
		textDATE.setBorder(new LineBorder(SystemColor.textText, 2, true));
		textDATE.setBounds(92, 227, 157, 46);
		panel_3.add(textDATE);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textID.setText("");
				textPPK.setText("");
				textKG.setText("");
				textDATE.setText("");
				texttid.setText("");
			}
		});
		btnClear.setFont(new Font("Segoe UI", Font.BOLD, 28));
		btnClear.setFocusable(false);
		btnClear.setFocusTraversalKeysEnabled(false);
		btnClear.setFocusPainted(false);
		btnClear.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		btnClear.setBackground(new Color(255, 204, 204));
		btnClear.setBounds(195, 502, 186, 61);
		panel_3.add(btnClear);
		
		JLabel lblNewLabel_1_3 = new JLabel("Transaction ID");
		lblNewLabel_1_3.setFont(new Font("Segoe UI", Font.BOLD, 28));
		lblNewLabel_1_3.setBounds(195, 20, 200, 46);
		panel_3.add(lblNewLabel_1_3);
		
		texttid = new JTextField();
		texttid.setFont(new Font("Segoe UI", Font.PLAIN, 26));
		texttid.setEditable(false);
		texttid.setColumns(10);
		texttid.setBorder(new LineBorder(SystemColor.textText, 2, true));
		texttid.setBounds(405, 20, 135, 46);
		panel_3.add(texttid);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 3, true), "For updating the same data in LenderLedger write description", TitledBorder.LEADING, TitledBorder.TOP, null, Color.RED));
		panel.setBounds(10, 300, 610, 95);
		panel_3.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Description");
		lblNewLabel_1_1_1_1.setBounds(10, 32, 151, 38);
		panel.add(lblNewLabel_1_1_1_1);
		lblNewLabel_1_1_1_1.setFont(new Font("Segoe UI", Font.BOLD, 28));
		
		textdescription = new JTextField();
		textdescription.setFont(new Font("Segoe UI", Font.PLAIN, 26));
		textdescription.setColumns(10);
		textdescription.setBorder(new LineBorder(SystemColor.textText, 2, true));
		textdescription.setBounds(171, 29, 429, 46);
		panel.add(textdescription);
		
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
				lenderlistadd.main(null);
			}
		});
		btnBack.setFont(new Font("Segoe UI", Font.BOLD, 28));
		btnBack.setFocusable(false);
		btnBack.setFocusTraversalKeysEnabled(false);
		btnBack.setFocusPainted(false);
		btnBack.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		btnBack.setBackground(new Color(255, 204, 204));
		btnBack.setBounds(666, 703, 186, 61);
		frame.getContentPane().add(btnBack);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new LineBorder(Color.BLACK, 3, true));
		scrollPane.setBounds(760, 94, 706, 586);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setBounds(100, 100, 1755, 1150);
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
				column=5;
				String indfive = table.getModel().getValueAt(row, column).toString();
				
				textID.setText(indone);
				texttid.setText(indtwo);
				textPPK.setText(indthree);
				textKG.setText(indfour);
				textDATE.setText(indfive);
			}
		});
		table.setFont(new Font("Segoe UI",Font.PLAIN, 18));
		table.setRowHeight(40);
		JTableHeader th = table.getTableHeader();
		th.setFont(new Font("Segoe UI", Font.BOLD, 20));
		((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer())
	    .setHorizontalAlignment(JLabel.LEFT);
	}
}
