import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Frame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import java.awt.SystemColor;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.util.*;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;
public class dashboard {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					dashboard window = new dashboard();
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
	private JLabel lblNewLabel_1_1_2_1,lblNewLabel_1_1_2_1_1,lby1,lby2,lby3,lblNewLabel_1_1_2_1_1_1;
	DB db = new DB();
	private JTextField textgetyear;
	private JPanel panel_2;
	public dashboard() {
		initialize();
		db.Connect();
		DisplayTable();
	}
	public void DisplayTable()
	{
		try {
			double rec=db.totalReceive();
			long lend=db.totallending();
			
			
			lblNewLabel_1_1_2_1.setText("Overall Balance:  "+rec);
			lblNewLabel_1_1_2_1_1.setText("Total Acres of Land:  "+db.getTotalAcres());
			lblNewLabel_1_1_2_1_1_1.setText("Crop production in quintal:  "+db.getProduction(db.datearr[0]));
			textgetyear.setText(db.datearr[0]);
			ArrayList<Long>list=new ArrayList<>();
			int y1=Integer.parseInt(db.datearr[0]);
			int y2=Integer.parseInt(db.datearr[0])-1;
			int y3=Integer.parseInt(db.datearr[0])-2;
			String s="";
			s=db.getYearlyProfit(y1);
			lby1.setText("Total Profit of "+y1+":  "+s);
			s=db.getYearlyProfit(y2);
			lby2.setText("Total Profit of "+y2+":  "+s);
			s=db.getYearlyProfit(y3);
			lby3.setText("Total Profit of "+y3+":  "+s);
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
		frame.setBounds(100, 100, 1589, 982);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblNewLabel = new JLabel("Dashboard");
		lblNewLabel.setForeground(SystemColor.menuText);
		lblNewLabel.setBounds(0, 10, 1540, 64);
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 38));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		panel.setBounds(196, 106, 568, 209);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		lby1 = new JLabel("Total Money receivables in the year ");
		lby1.setBackground(new Color(0, 153, 153));
		lby1.setForeground(new Color(0, 153, 102));
		lby1.setHorizontalAlignment(SwingConstants.CENTER);
		lby1.setFont(new Font("Segoe UI", Font.BOLD, 28));
		lby1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		lby1.setBounds(10, 10, 514, 51);
		panel.add(lby1);
		
		lby2 = new JLabel("Total Money receivables in the year ");
		lby2.setForeground(new Color(102, 51, 102));
		lby2.setHorizontalAlignment(SwingConstants.CENTER);
		lby2.setFont(new Font("Segoe UI", Font.BOLD, 28));
		lby2.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		lby2.setBounds(10, 71, 514, 51);
		panel.add(lby2);
		
		lby3 = new JLabel("Total Money receivables in the year ");
		lby3.setForeground(new Color(0, 51, 153));
		lby3.setHorizontalAlignment(SwingConstants.CENTER);
		lby3.setFont(new Font("Segoe UI", Font.BOLD, 28));
		lby3.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		lby3.setBounds(10, 132, 514, 51);
		panel.add(lby3);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(203, 231, 254));
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Operations", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 120, 215)));
		panel_1.setBounds(196, 492, 1143, 288);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JButton btnNewButton = new JButton(" Edit Composites");
		btnNewButton.setFocusable(false);
		btnNewButton.setFocusTraversalKeysEnabled(false);
		btnNewButton.setFocusPainted(false);
		btnNewButton.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				try {
					db.con.close();
				} catch (SQLException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
				compositeDB.main(null);
			}
		});
		btnNewButton.setIcon(null);
		btnNewButton.setBackground(new Color(255, 204, 204));
		btnNewButton.setFont(new Font("Segoe UI", Font.BOLD, 32));
		btnNewButton.setBounds(169, 104, 315, 78);
		panel_1.add(btnNewButton);
		
		JButton btnLenders = new JButton(" Lenders");
		btnLenders.setFocusPainted(false);
		btnLenders.setFocusTraversalKeysEnabled(false);
		btnLenders.setFocusable(false);
		btnLenders.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		btnLenders.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				try {
					db.con.close();
				} catch (SQLException e4) {
					e4.printStackTrace();
				}
				lenderlistadd.main(null);
			}
		});
		btnLenders.setIcon(null);
		btnLenders.setFont(new Font("Segoe UI", Font.BOLD, 32));
		btnLenders.setBackground(new Color(255, 204, 204));
		btnLenders.setBounds(634, 104, 315, 78);
		panel_1.add(btnLenders);
		
		panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		panel_2.setBounds(771, 106, 568, 209);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		textgetyear = new JTextField();
		textgetyear.setFont(new Font("Segoe UI", Font.BOLD, 26));
		textgetyear.setBorder(new TitledBorder(null, "Enter year", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		textgetyear.setBounds(179, 30, 108, 56);
		panel_2.add(textgetyear);
		textgetyear.setColumns(10);
		
		lblNewLabel_1_1_2_1_1_1 = new JLabel("Total Acres of Land: ");
		lblNewLabel_1_1_2_1_1_1.setBounds(10, 119, 549, 56);
		panel_2.add(lblNewLabel_1_1_2_1_1_1);
		lblNewLabel_1_1_2_1_1_1.setForeground(new Color(255, 0, 153));
		lblNewLabel_1_1_2_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_2_1_1_1.setFont(new Font("Segoe UI", Font.BOLD, 28));
		lblNewLabel_1_1_2_1_1_1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		
		JButton btnNewButton_1 = new JButton("Search");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String year=textgetyear.getText();
				if(year.isEmpty())
					JOptionPane.showMessageDialog(null, "Please enter year to see total crop production!");
				else
				{
					lblNewLabel_1_1_2_1_1_1.setText("Crop production in quintal:  "+db.getProduction(year));
				}
			}
		});
		btnNewButton_1.setBackground(new Color(255, 204, 153));
		btnNewButton_1.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		btnNewButton_1.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnNewButton_1.setBounds(297, 38, 80, 38);
		panel_2.add(btnNewButton_1);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(196, 325, 1143, 157);
		frame.getContentPane().add(panel_3);
		panel_3.setLayout(null);
		
		lblNewLabel_1_1_2_1_1 = new JLabel("Overall Lended Money till now: ");
		lblNewLabel_1_1_2_1_1.setBounds(337, 105, 469, 42);
		panel_3.add(lblNewLabel_1_1_2_1_1);
		lblNewLabel_1_1_2_1_1.setForeground(new Color(255, 0, 153));
		lblNewLabel_1_1_2_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_2_1_1.setFont(new Font("Segoe UI", Font.BOLD, 28));
		lblNewLabel_1_1_2_1_1.setBorder(new LineBorder(new Color(0, 51, 102), 1, true));
		
		lblNewLabel_1_1_2_1 = new JLabel("Overall Receivables till now: ");
		lblNewLabel_1_1_2_1.setBounds(277, 31, 590, 64);
		panel_3.add(lblNewLabel_1_1_2_1);
		lblNewLabel_1_1_2_1.setForeground(new Color(0, 102, 102));
		lblNewLabel_1_1_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_2_1.setFont(new Font("Segoe UI", Font.BOLD, 34));
		lblNewLabel_1_1_2_1.setBorder(new LineBorder(new Color(153, 0, 102), 4, true));
	}
}
