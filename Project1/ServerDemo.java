/*
	Title: CNT 4504 Project 1 - Server Demo
	Author: Matthew Boyette
	Date: 1/12/2014
	
	
*/

import api.gui.*;
import api.util.*;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

public class ServerDemo
{
	private final static Font						textFont	= new Font("Lucida Console", Font.PLAIN, 14);
	
	private static boolean							debugMode	= false;
	private static Networking.SimpleServerThread	myThread	= null;
	private static ApplicationWindow				myWindow	= null;
	
	// Client application entry-point.
	public static void main(final String[] args)
	{
		int choice = Support.promptDebugMode(ServerDemo.myWindow);
		ServerDemo.debugMode = (choice == JOptionPane.YES_OPTION);
		
		// Define a self-contained ActionListener event handler.
		EventHandler myActionPerformed = new EventHandler()
		{
			@Override
			public final void run(final Object... arguments) throws IllegalArgumentException
			{
				if ((arguments.length <= 1) || (arguments.length > 2))
				{
					throw new IllegalArgumentException("myActionPerformed Error : incorrect number of arguments.");
				}
				else
					if (!(arguments[0] instanceof ActionEvent))
					{
						throw new IllegalArgumentException("myActionPerformed Error : argument[0] is of incorrect type.");
					}
					else
						if (!(arguments[1] instanceof ApplicationWindow))
						{
							throw new IllegalArgumentException("myActionPerformed Error : argument[1] is of incorrect type.");
						}
				
				ActionEvent event = (ActionEvent)arguments[0];
				ApplicationWindow window = (ApplicationWindow)arguments[1];
				RichTextPane output = null;
				
				for (int i = 0; i < window.getElements().size(); i++)
				{
					if (window.getElements().get(i) instanceof RichTextPane)
					{
						output = (RichTextPane)window.getElements().get(i);
					}
				}
				
				if (output != null)
				{
					/*
					 * JDK 7 allows string objects as the expression in a switch statement.
					 * This generally produces more efficient byte code compared to a chain of if statements.
					 * http://docs.oracle.com/javase/7/docs/technotes/guides/language/strings-switch.html
					 */
					switch (event.getActionCommand())
					{
						case "Clear":
							
							output.clear();
							break;
						
						case "Open":
							
							output.openFile();
							break;
						
						case "Save":
							
							output.saveFile();
							break;
						
						default:
							
							break;
					}
				}
			}
		};
		
		// Define a self-contained interface construction event handler.
		EventHandler myDrawGUI = new EventHandler()
		{
			@Override
			public final void run(final Object... arguments) throws IllegalArgumentException
			{
				if (arguments.length <= 0)
				{
					throw new IllegalArgumentException("myDrawGUI Error : incorrect number of arguments.");
				}
				else
					if (!(arguments[0] instanceof ApplicationWindow))
					{
						throw new IllegalArgumentException("myDrawGUI Error : argument[0] is of incorrect type.");
					}
				
				ApplicationWindow window = (ApplicationWindow)arguments[0];
				Container contentPane = window.getContentPane();
				JMenuBar menuBar = new JMenuBar();
				JMenu fileMenu = new JMenu("File");
				JMenuItem clearOption = new JMenuItem("Clear");
				JMenuItem openOption = new JMenuItem("Open");
				JMenuItem saveOption = new JMenuItem("Save");
				RichTextPane outputBox = new RichTextPane(window, true, window.isDebugging(), ServerDemo.textFont);
				JComboBox<String> inputBox = new JComboBox<String>();
				
				fileMenu.setFont(ServerDemo.textFont);
				clearOption.setFont(ServerDemo.textFont);
				openOption.setFont(ServerDemo.textFont);
				saveOption.setFont(ServerDemo.textFont);
				inputBox.setFont(ServerDemo.textFont);
				
				fileMenu.setMnemonic('F');
				openOption.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.Event.CTRL_MASK));
				openOption.setMnemonic('O');
				saveOption.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.Event.CTRL_MASK));
				saveOption.setMnemonic('S');
				clearOption.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.Event.CTRL_MASK));
				clearOption.setMnemonic('C');
				
				contentPane.setLayout(new BorderLayout());
				clearOption.addActionListener(window);
				fileMenu.add(clearOption);
				openOption.addActionListener(window);
				fileMenu.add(openOption);
				saveOption.addActionListener(window);
				fileMenu.add(saveOption);
				menuBar.add(fileMenu);
				window.setJMenuBar(menuBar);
				
				JScrollPane outputPanel = new JScrollPane(outputBox);
				JPanel inputPanel = new JPanel();
				
				inputPanel.setLayout(new FlowLayout());
				inputPanel.add(inputBox);
				contentPane.add(outputPanel, BorderLayout.CENTER);
				contentPane.add(inputPanel, BorderLayout.SOUTH);
				window.getElements().add(outputBox);
				window.getElements().add(inputBox);
			}
		};
		
		ServerDemo.myWindow = new ApplicationWindow(null, "Server Demo", new Dimension(600, 400), ServerDemo.debugMode, false,
			myActionPerformed, myDrawGUI);
		ServerDemo.myThread = new CNT4504Project1Code.CNT4504Project1ServerThread(15000, ServerDemo.myWindow);
		ServerDemo.myThread.start();
		
		while (ServerDemo.myThread.isListening())
		{
			continue;
		}
		
		ServerDemo.myThread = null;
		ServerDemo.myWindow = null;
	}
}