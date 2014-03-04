/*
	Title: CNT 4504 Project 1 - Client Demo
	Author: Matthew Boyette
	Date: 1/12/2014
	
	This code implements the client application.
*/

import api.gui.*;
import api.util.*;
import CNT4504.Project1Code.CNT4504Project1Code.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

public class ClientDemo
{
	private final static Font					textFont	= new Font("Lucida Console", Font.PLAIN, 14);
	
	private static boolean						debugMode			= false;
	private static String						hostName			= null;
	private static EventHandler					myActionPerformed	= null;
	private static EventHandler					myDrawGUI			= null;
	private static CNT4504Project1ClientThread	myThread			= null;
	private static ApplicationWindow			myWindow			= null;
	private static int							portNumber			= 0;
	
	// Client application entry-point.
	public static void main(final String[] args)
	{
		int choice = Support.promptDebugMode(ClientDemo.myWindow);
		ClientDemo.debugMode = (choice == JOptionPane.YES_OPTION);
		
		// Define a self-contained ActionListener event handler.
		ClientDemo.myActionPerformed = new EventHandler()
		{
			@Override
			public final void run(final Object... arguments) throws IllegalArgumentException
			{
				if ((arguments.length <= 1) || (arguments.length > 2))
				{
					throw new IllegalArgumentException("myActionPerformed Error : incorrect number of arguments.");
				}
				else if (!(arguments[0] instanceof ActionEvent))
				{
					throw new IllegalArgumentException("myActionPerformed Error : argument[0] is of incorrect type.");
				}
				else if (!(arguments[1] instanceof ApplicationWindow))
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
						JDK 7 allows string objects as the expression in a switch statement.
						This generally produces more efficient byte code compared to a chain of if statements.
						http://docs.oracle.com/javase/7/docs/technotes/guides/language/strings-switch.html
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
							
						case "Send":
							
							// TODO
							break;
							
						default:
							
							break;
					}
				}
			}
		};
		
		// Define a self-contained interface construction event handler.
		ClientDemo.myDrawGUI = new EventHandler()
		{
			@Override
			public final void run(final Object... arguments) throws IllegalArgumentException
			{
				if (arguments.length <= 0)
				{
					throw new IllegalArgumentException("myDrawGUI Error : incorrect number of arguments.");
				}
				else if (!(arguments[0] instanceof ApplicationWindow))
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
				RichTextPane outputBox = new RichTextPane(window, true, window.isDebugging(), ClientDemo.textFont);
				JComboBox<String> inputBox = new JComboBox<String>();
				JButton btnSend = new JButton("Send");
				
				fileMenu.setFont(ClientDemo.textFont);
				clearOption.setFont(ClientDemo.textFont);
				openOption.setFont(ClientDemo.textFont);
				saveOption.setFont(ClientDemo.textFont);
				inputBox.setEditable(false);
				inputBox.setFont(ClientDemo.textFont);
				
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
				btnSend.addActionListener(window);
				
				JScrollPane outputPanel = new JScrollPane(outputBox);
				JPanel inputPanel = new JPanel();
				
				inputPanel.setLayout(new FlowLayout());
				inputPanel.add(inputBox);
				inputPanel.add(btnSend);
				contentPane.add(outputPanel, BorderLayout.CENTER);
				contentPane.add(inputPanel, BorderLayout.SOUTH);
				window.getElements().add(outputBox);
				window.getElements().add(inputBox);
				
				window.getRootPane().setDefaultButton(btnSend);
			}
		};
		
		try
		{
			if (args.length > 0)
			{
				ClientDemo.hostName = args[0];
				ClientDemo.setRemotePort();
				ClientDemo.reset();
			}
			else
			{
				throw new Exception("User did not supply a remote hostname via the command-line!");
			}
		}
		catch (Exception e)
		{
			Support.displayException(ClientDemo.myWindow, e, true);
			e.printStackTrace();
		}
	}
	
	public static void printString(final String s)
	{
		RichTextPane output = null;
		
		if (ClientDemo.myWindow != null)
		{
			for (int i = 0; i < ClientDemo.myWindow.getElements().size(); i++)
			{
				if (ClientDemo.myWindow.getElements().get(i) instanceof RichTextPane)
				{
					output = (RichTextPane)ClientDemo.myWindow.getElements().get(i);
				}
			}
		}
		
		if (output != null)
		{
			output.append(Color.BLACK, Color.WHITE, "[" + Support.getDateTimeStamp() + "]: " + s);
		}
	}
	
	public static synchronized void reset()
	{
		if (ClientDemo.myWindow != null)
		{
			ClientDemo.myWindow.dispose();
			ClientDemo.myWindow = null;
		}
		
		if (ClientDemo.myThread != null)
		{
			ClientDemo.myThread.close();
			ClientDemo.myThread = null;
		}
		
		String clientTitle = "Client Demo - Remote Host: " + ClientDemo.hostName + " - Remote Port: " + ClientDemo.portNumber;
		
		ClientDemo.myWindow = new ApplicationWindow(null, clientTitle, new Dimension(600, 400), ClientDemo.debugMode, false,
			ClientDemo.myActionPerformed, ClientDemo.myDrawGUI);
		ClientDemo.myThread = new CNT4504Project1ClientThread(ClientDemo.hostName, ClientDemo.portNumber, ClientDemo.myWindow);
		ClientDemo.myThread.start();
		
		while (ClientDemo.myThread.isConnected())
		{
			continue;
		}
		
		ClientDemo.myThread = null;
		ClientDemo.myWindow = null;
	}
	
	public static synchronized void setRemoteHost()
	{
		String s = null;
		
		s = Support.getInputString(ClientDemo.myWindow, "Remote host?", "Set Remote Host");

		ClientDemo.hostName = s;
	}
	
	public static synchronized void setRemotePort()
	{
		String s = null;
		
		do
		{
			s = Support.getInputString(ClientDemo.myWindow, "Remote port?", "Set Remote Port");
		}
		while (Support.isStringParsedAsInteger(s) != true);
		
		ClientDemo.portNumber = Integer.parseInt(s);
	}
}