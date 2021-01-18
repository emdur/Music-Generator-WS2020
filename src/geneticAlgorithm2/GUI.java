package geneticAlgorithm2;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.sound.midi.MidiUnavailableException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import org.jfugue.realtime.RealtimePlayer;
import org.jfugue.temporal.TemporalPLP;
import org.jfugue.theory.Note;


public class GUI {


	private Solution sol;
	private RealtimePlayer player;
	private int generationNumber; //number of generations that can/will be generated
	private ArrayList<Fitness> fl = new ArrayList<Fitness>();
	
	private int genIndex = 1; //index of current generation in evolution
	private int bpm = 120; //bpm calculation only works with 60/120/240/480..
	private Boolean addBestFromLastRound = false;
	TemporalPLP plp;
	public GUI() {
		initializeGui();
	}

	private void initializeGui() {
		JFrame frame = new JFrame();
		JPanel pane = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		pane.setLayout(new GridBagLayout());
		frame.setTitle("Music Generator");
		frame.add(pane);
		frame.setSize(1000, 200);
		//frame.setResizable(false);
		
		// define components of GUI w/ action listeners

		TextField genNumber = new TextField(5);
		genNumber.setText("10");
		
		JToggleButton btnChildrensMelody = new JToggleButton("Children's Melody");
		JToggleButton btnReverseChildrensMelody = new JToggleButton("Reverse-Children's Melody");
		btnChildrensMelody.addActionListener(new ActionListener(){  
		    public void actionPerformed(ActionEvent e){  
		             if(((JToggleButton)e.getSource()).isSelected()) {
		            	 if(btnReverseChildrensMelody.isSelected()) {
		            		 btnReverseChildrensMelody.doClick();
		            	 }
		            	 if(!fl.stream().anyMatch(c -> c instanceof ChildrensMelody)) {
		            		 fl.add(new ChildrensMelody());
		            	 }
		             } else {
		            	 for(int i = 0; i < fl.size(); i++) {
		     				if(fl.get(i) instanceof ChildrensMelody) {
		     					fl.remove(i);
		     				}
		     			}
		             }
		        }  
		    });
		
		btnReverseChildrensMelody.addActionListener(new ActionListener(){  
		    public void actionPerformed(ActionEvent e){  
		             if(((JToggleButton)e.getSource()).isSelected()) {
		            	 if(btnChildrensMelody.isSelected()) {
		            		 btnChildrensMelody.doClick();
		            	 }
		            	 if(!fl.stream().anyMatch(c -> c instanceof ReverseChildrensMelody)) {
		            		 fl.add(new ReverseChildrensMelody());
		            	 }
		             } else {
		            	 for(int i = 0; i < fl.size(); i++) {
		     				if(fl.get(i) instanceof ReverseChildrensMelody) {
		     					fl.remove(i);
		     				}
		     			}
		             }
		        }  
		    });
		
		JToggleButton btnCON = new JToggleButton("ClosenessOfNotes");
		JToggleButton btnReverseCON = new JToggleButton("Reverse-ClosenessOfNotes");
		btnCON.addActionListener(new ActionListener(){  
		    public void actionPerformed(ActionEvent e){  
		             if(((JToggleButton)e.getSource()).isSelected()) {
		            	 if(btnReverseCON.isSelected()) {
			            	 btnReverseCON.doClick();
		            	 }
		            	 if(!fl.stream().anyMatch(c -> c instanceof ClosenessOfNotes)) {
		            		 fl.add(new ClosenessOfNotes());
		            		 //JOptionPane.showMessageDialog(frame, "Evaluate by Closeness of Notes.");
		            	 }
		             } else {
		            	 for(int i = 0; i < fl.size(); i++) {
		     				if(fl.get(i) instanceof ClosenessOfNotes) {
		     					fl.remove(i);
		     				}
		     			}
		             }
		        }  
		    });
		btnReverseCON.addActionListener(new ActionListener(){  
		    public void actionPerformed(ActionEvent e){  
		             if(((JToggleButton)e.getSource()).isSelected()) {
		            	 if(btnCON.isSelected()) {
		            		 btnCON.doClick(); 
		            	 }
		            	 if(!fl.stream().anyMatch(c -> c instanceof ReverseClosenessOfNotes)) {
		            		 fl.add(new ReverseClosenessOfNotes());
		            		 //JOptionPane.showMessageDialog(frame, "Evaluate by increased distance of Notes.");
		            	 }
		             } else {
		            	 for(int i = 0; i < fl.size(); i++) {
		     				if(fl.get(i) instanceof ReverseClosenessOfNotes) {
		     					fl.remove(i);
		     				}
		     			}
		             }
		        }  
		    });
		JToggleButton btnSub = new JToggleButton("Substitute by Step");
		btnSub.addActionListener(new ActionListener(){  
		    public void actionPerformed(ActionEvent e){  
		             if(((JToggleButton)e.getSource()).isSelected()) {
		            	 if(!fl.stream().anyMatch(c -> c instanceof SubstituteByStep)) {
		            		 fl.add(new SubstituteByStep());
		            		 JOptionPane.showMessageDialog(frame, "Substitute by step.\n Make sure this is the only enabled button.");
		            	 }
		             } else {
		            	 for(int i = 0; i < fl.size(); i++) {
		     				if(fl.get(i) instanceof SubstituteByStep) {
		     					fl.remove(i);
		     				}
		     			}
		             }
		        }  
		    });
		JToggleButton btnFindMelody = new JToggleButton("Find specific melody");
		btnFindMelody.addActionListener(new ActionListener(){  
		    @SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e){  
		             if(((JToggleButton)e.getSource()).isSelected()) {
		            	 if(!fl.stream().anyMatch(c -> c instanceof FindMelody)) {
		            		 Individual mel = new Individual();
		            		 ArrayList<Note> no = new ArrayList<Note>(Arrays.asList(new Note[] {new Note(48), new Note("R"), new Note("R"), new Note(55),new Note("R"), new Note("R"), new Note(53), new Note(52)}));
		            		 mel.notes = (ArrayList<Note>) no.clone();
		            		 fl.add(new FindMelody(mel));
		            		 JOptionPane.showMessageDialog(frame, "This evolves towards a specified melody by calculating the fitness values accordingly.\n Make sure this is the only enabled button.");
		            	 }
		             } else {
		            	 for(int i = 0; i < fl.size(); i++) {
		     				if(fl.get(i) instanceof FindMelody) {
		     					fl.remove(i);
		     				}
		     			}
		             }
		        }  
		    });
		JToggleButton btnCFriend = new JToggleButton("C-Friend");
		JToggleButton btnReverseCFriend = new JToggleButton("Reverse-C-Friend");
		btnCFriend.addActionListener(new ActionListener(){  
		    public void actionPerformed(ActionEvent e){  
		             if(((JToggleButton)e.getSource()).isSelected()) {
		            	 if(btnReverseCFriend.isSelected()) {
			            	 btnReverseCFriend.doClick();
		            	 }
		            	 if(!fl.stream().anyMatch(c -> c instanceof CFriend)) {
		            		 fl.add(new CFriend());
		            		 //JOptionPane.showMessageDialog(frame, "Fitness is higher if a note is a C.");
		            	 }
		             } else {
		            	 for(int i = 0; i < fl.size(); i++) {
		     				if(fl.get(i) instanceof CFriend) {
		     					fl.remove(i);
		     				}
		     			}
		             }
		        }  
		    });
		btnReverseCFriend.addActionListener(new ActionListener(){  
		    public void actionPerformed(ActionEvent e){  
		             if(((JToggleButton)e.getSource()).isSelected()) {
		            	 if(btnCFriend.isSelected()) {
		            		 btnCFriend.doClick();
		            	 }
		            	 if(!fl.stream().anyMatch(c -> c instanceof ReverseCFriend)) {
		            		 fl.add(new ReverseCFriend());
		            		 //JOptionPane.showMessageDialog(frame, "Fitness is higher if a note is *not* a C.");
		            	 }
		             } else {
		            	 for(int i = 0; i < fl.size(); i++) {
		     				if(fl.get(i) instanceof ReverseCFriend) {
		     					fl.remove(i);
		     				}
		     			}
		             }
		        }  
		    });
		JToggleButton btnDura = new JToggleButton("DurationEval");
		JToggleButton btnReverseDura = new JToggleButton("Alternate DurationEval");
		btnDura.addActionListener(new ActionListener(){  
		    public void actionPerformed(ActionEvent e){  
		             if(((JToggleButton)e.getSource()).isSelected()) {
		            	 if(btnReverseDura.isSelected()) {
		            		 btnReverseDura.doClick();
		            	 }
		            	 if(!fl.stream().anyMatch(c -> c instanceof DurationCritic)) {
		            		 fl.add(new DurationCritic());
		            	 }
		             } else {
		            	 for(int i = 0; i < fl.size(); i++) {
		     				if(fl.get(i) instanceof DurationCritic) {
		     					fl.remove(i);
		     				}
		     			}
		             }
		        }  
		    });
		btnReverseDura.addActionListener(new ActionListener(){  
		    public void actionPerformed(ActionEvent e){  
		             if(((JToggleButton)e.getSource()).isSelected()) {
		            	 if(btnDura.isSelected()) {
		            		 btnDura.doClick();
		            	 }
		            	 if(!fl.stream().anyMatch(c -> c instanceof ReverseDurationCritic)) {
		            		 fl.add(new ReverseDurationCritic());
		            	 }
		             } else {
		            	 for(int i = 0; i < fl.size(); i++) {
		     				if(fl.get(i) instanceof ReverseDurationCritic) {
		     					fl.remove(i);
		     				}
		     			}
		             }
		        }  
		    });
		JButton btnPlayDyn = new JButton("Play");
		JButton btnReset = new JButton("Reset (new Number)");
		JButton btnPause = new JButton("Pause");
		
		btnPlayDyn.addActionListener(new ActionListener(){  
		    public void actionPerformed(ActionEvent e){
		    	//fix?: nicht resetten, wenn generationNumberMax abgelaufen ist, sondern nur die generationNumber/Index ändern,
		    	//sodass es weiterspielt an der Stelle wo es aufgehört hat, wenn man z.B. nach abgelaufenen 10 generations
		    	// 100 eingibt und dann play drückt (nicht reset) --> sol.setGenLimit(int aus textbox in gui)
		    	if(fl.isEmpty()) {
		    		btnReset.doClick();
		    	} else {
		    		if(sol == null || genIndex-1 == generationNumber) {
			    		genIndex = 1;
			    		generationNumber = Integer.parseInt(genNumber.getText());
			    		restartPlayer();
			    		sol = new Solution(generationNumber);
			    		calculateWaitTime();
			    	}
	            	eval();
		     	}
		    }
		    	
		    });
		
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
	           	 scheduler.shutdownNow();
			 } 
		});
		
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				genIndex = 1;
	    		generationNumber = Integer.parseInt(genNumber.getText());
	    		restartPlayer();
	    		if(fl.isEmpty()) {
	    			sol = new Solution(generationNumber);
	    			calculateWaitTime();
		    	}
			}
		});
		JToggleButton btnLetBestMoveOn = new JToggleButton("Keep best pattern");
		btnLetBestMoveOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){  
	             addBestFromLastRound = !addBestFromLastRound;
	        }
		});
		// add components to GUI
		c.anchor = GridBagConstraints.WEST;
		JPanel smallPane = new JPanel();
		c.insets = new Insets(5,5,5,5);
		c.gridy = 0;
		c.gridx = 1;
		pane.add(new Label("Number of Generations: "),c);
		smallPane.add(genNumber,c);
		btnPlayDyn.setBackground(Color.white);
		smallPane.add(btnPlayDyn,c);
		btnPause.setBackground(Color.white);
		smallPane.add(btnPause,c);
		c.gridx = 3;
		c.gridy = 0;
		btnReset.setBackground(Color.white);
		pane.add(btnReset,c);
		c.gridx = 4;
		c.gridy = 0;
		pane.add(btnLetBestMoveOn,c);
		btnLetBestMoveOn.doClick();
		c.gridx = 2;
		c.gridy = 0;
		pane.add(smallPane,c);
		c.gridy = 1;
		c.gridx = 0;
		pane.add(btnCON,c);
		c.gridy = 2;
		c.gridx = 0;
		pane.add(btnReverseCON,c);
		c.gridy = 1;
		c.gridx = 1;
		pane.add(btnCFriend,c);
		c.gridy = 2;
		c.gridx = 1;
		pane.add(btnReverseCFriend,c);
		c.gridy = 1;
		c.gridx = 2;
		pane.add(btnDura,c);
		c.gridy = 2;
		c.gridx = 2;
		pane.add(btnReverseDura,c);
		c.gridy = 1;
		c.gridx = 3;
		pane.add(btnChildrensMelody,c);
		c.gridy = 2;
		c.gridx = 3;
		pane.add(btnReverseChildrensMelody,c);
		//pane.add(btnSub,c);//pane.add(btnFindMelody,c);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pane.setVisible(true);
		frame.setVisible(true);
	}
	private void eval() {
		scheduler = Executors.newScheduledThreadPool(1);
		     final ScheduledFuture<?> evolHandle =
		       scheduler.scheduleAtFixedRate(evolution, 1000, waitTime, TimeUnit.MILLISECONDS);
		     scheduler.schedule(new Runnable() {
		       public void run() { evolHandle.cancel(true); }
		     }, waitTime*(generationNumber-genIndex+1), TimeUnit.MILLISECONDS);    
	}
	private final Runnable evolution = new Runnable() {
	       public void run() {
	    	   if(genIndex > generationNumber) {
	    		   restartPlayer();
	    		   scheduler.shutdownNow();
	    	   } else {
		    	   sol.evaluateOnce(player, genIndex, bpm, fl, addBestFromLastRound);
		    	   
		    	   genIndex++;
	    	   }
	    	   
	       }
	     };
	private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private void calculateWaitTime() {
		//60sec/BPM*amount of quarter notes(duration=0.25) in 1 pattern (for 8 notes = 2 (bars))
		waitTime = (long)((float)60/bpm*4)*1000; 
		System.out.println("waittime: " + waitTime);
		
	}
	private long waitTime = 0;
	private void restartPlayer() {
		if(player != null) {
			player.close();
		}
		try {
			player = new RealtimePlayer();
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		}
	}


}