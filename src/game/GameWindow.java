package game;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

import monster.Trainer;

/**
 * A top-level Swing window representing the game board. It includes the game world,
 * along with some Swing components used to display details of the battles as they
 * progress.
 * 
 * @author Mary Ellen Foster <MaryEllen.Foster@glasgow.ac.uk>
 */
@SuppressWarnings("serial")
public class GameWindow extends JFrame implements ActionListener {
	/** The game world */
	private GameWorld gameWorld;
	/** GUI components to show trainers on the screen */
    private JTextField trainer1Field, trainer2Field;
    private DefaultListModel<Trainer> faintedModel;

    /** 
     * Creates and displays a new top-level window.
     */
    public GameWindow() {
    	// Basic window stuff
    	super("Battle game");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);

		// Create the game world -- change the number here to have more or fewer trainers
		gameWorld = new GameWorld(this, 25);
		
		// Buttons to start and stop the sprites
		JButton startButton = new JButton("Start");
		JButton stopButton = new JButton("Stop");
		startButton.addActionListener(this);
		stopButton.addActionListener(this);

		// Fields and buttons at the top
		trainer1Field = new JTextField();
		trainer1Field.setEditable(false);
		trainer1Field.setBorder(new TitledBorder("Trainer 1"));
		trainer2Field = new JTextField(10);
		trainer2Field.setEditable(false);
		trainer2Field.setBorder(new TitledBorder("Trainer 2"));
		Box trainerBox = Box.createHorizontalBox();
		trainerBox.add(trainer1Field);
		trainerBox.add(trainer2Field);
		trainerBox.add(startButton);
		trainerBox.add(stopButton);
		
		// List of eliminated trainers at the side
		faintedModel = new DefaultListModel<>();
		JList<Trainer> faintedList = new JList<>(faintedModel);
		faintedList.setBorder(new TitledBorder("Eliminated trainers"));
		
		// Lay everything out
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(trainerBox, BorderLayout.NORTH);
		getContentPane().add(gameWorld);
		getContentPane().add(new JScrollPane(faintedList), BorderLayout.EAST);
		pack();
    }
    
    /**
     * Responds to a click on either of the buttons in the UI.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
    	// This is another way to work out which button was clicked
    	switch (e.getActionCommand()) {
    	case "Start":
    		gameWorld.startSprites();
    		break;
    		
    	case "Stop":
    		gameWorld.stopSprites();
    		break;
    	}
    }
    
    /**
     * Sets the trainer name to be displayed in the "Trainer 1" field.
     * 
     * @param trainer The trainer name to display -- if it is null, clears the field contents
     */
    public void setTrainer1(Trainer trainer) {
    	if (trainer == null) {
    		trainer1Field.setText("");
    	} else {
			trainer1Field.setText(trainer.getName());
    	}
    }

    /**
     * Sets the trainer name to be displayed in the "Trainer 2" field.
     * 
     * @param trainer The trainer name to display -- if it is null, clears the field contents
     */
    public void setTrainer2(Trainer trainer) {
    	if (trainer == null) {
    		trainer2Field.setText("");
    	} else {
			trainer2Field.setText(trainer.getName());
    	}
    }

    /**
     * Adds the given trainer to the list of eliminated trainers.
     * 
     * @param trainer The trainer to add
     */
    public void addEliminatedTrainer(Trainer trainer) {
    	faintedModel.addElement(trainer);
    }
    
    /**
     * Main method -- just creates and displays the window.
     */
    public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new GameWindow().setVisible(true);
			}
		});
	}
}
