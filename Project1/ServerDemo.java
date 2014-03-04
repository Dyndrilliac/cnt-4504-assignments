/*
	Title: CNT 4504 Project 1 - Server Demo
	Author: Matthew Boyette
	Date: 1/12/2014
	
	This code implements the server application.
*/

import api.gui.*;
import api.util.*;
import CNT4504.Project1Code.CNT4504Project1Code.*;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

public class ServerDemo
{
	private final static Font	textFont	= new Font("Lucida Console", Font.PLAIN, 14);
	
	private static boolean							debugMode			= false;
	private static EventHandler						myActionPerformed	= null;
	private static EventHandler						myDrawGUI			= null;
	private static Networking.SimpleServerThread	myThread			= null;
	private static ApplicationWindow				myWindow			= null;
	private static int								portNumber			= 0;
	
	public static synchronized RichTextPane getOutput()
	{
		RichTextPane output = null;
		
		if (ServerDemo.myWindow != null)
		{
			for (int i = 0; i < ServerDemo.myWindow.getElements().size(); i++)
			{
				if (ServerDemo.myWindow.getElements().get(i) instanceof RichTextPane)
				{
					output = (RichTextPane)ServerDemo.myWindow.getElements().get(i);
				}
			}
		}
		
		return output;
	}
	
	// Server application entry-point.
	public static void main(final String[] args)
	{
		int choice = Support.promptDebugMode(ServerDemo.myWindow);
		ServerDemo.debugMode = (choice == JOptionPane.YES_OPTION);
		
		// Define a self-contained ActionListener event handler.
		ServerDemo.myActionPerformed = new EventHandler()
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
				RichTextPane output = ServerDemo.getOutput();
				
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
						
						default:
							
							break;
					}
				}
			}
		};
		
		// Define a self-contained interface construction event handler.
		ServerDemo.myDrawGUI = new EventHandler()
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
				RichTextPane outputBox = new RichTextPane(window, true, window.isDebugging(), ServerDemo.textFont);
				JScrollPane outputPanel = new JScrollPane(outputBox);
				
				fileMenu.setFont(ServerDemo.textFont);
				clearOption.setFont(ServerDemo.textFont);
				openOption.setFont(ServerDemo.textFont);
				saveOption.setFont(ServerDemo.textFont);
				
				fileMenu.setMnemonic('F');
				openOption.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.Event.CTRL_MASK));
				openOption.setMnemonic('O');
				saveOption.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.Event.CTRL_MASK));
				saveOption.setMnemonic('S');
				clearOption.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.Event.CTRL_MASK));
				clearOption.setMnemonic('C');
				
				clearOption.addActionListener(window);
				fileMenu.add(clearOption);
				openOption.addActionListener(window);
				fileMenu.add(openOption);
				saveOption.addActionListener(window);
				fileMenu.add(saveOption);
				menuBar.add(fileMenu);
				
				contentPane.setLayout(new BorderLayout());
				contentPane.add(outputPanel, BorderLayout.CENTER);
				window.setJMenuBar(menuBar);
				window.getElements().add(outputBox);
			}
		};
		
		ServerDemo.setListeningPort();
		ServerDemo.reset();
	}
	
	public static synchronized void reset()
	{
		if (ServerDemo.myWindow != null)
		{
			ServerDemo.myWindow.dispose();
			ServerDemo.myWindow = null;
		}
		
		if (ServerDemo.myThread != null)
		{
			ServerDemo.myThread.close();
			ServerDemo.myThread = null;
		}
		
		String serverTitle = "Server Demo - Listening Port: " + ServerDemo.portNumber;
		
		ServerDemo.myWindow = new ApplicationWindow(null, serverTitle, new Dimension(600, 400), ServerDemo.debugMode, false,
			ServerDemo.myActionPerformed, ServerDemo.myDrawGUI);
		ServerDemo.myThread = new CNT4504Project1ServerThread(ServerDemo.portNumber, ServerDemo.myWindow);
		ServerDemo.myThread.start();
		
		while (ServerDemo.myThread.isListening())
		{
			continue;
		}
		
		ServerDemo.myThread = null;
		ServerDemo.myWindow = null;
	}
	
	public static synchronized void setListeningPort()
	{
		String s = null;
		
		do
		{
			s = Support.getInputString(ServerDemo.myWindow, "Listening port?", "Set Listening Port");
		}
		while (Support.isStringParsedAsInteger(s) != true);
		
		ServerDemo.portNumber = Integer.parseInt(s);
	}
}