package geneticAlgorithm2;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.sound.midi.MidiUnavailableException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import org.jfugue.realtime.RealtimePlayer;
import org.jfugue.temporal.TemporalPLP;


public class GUI {

	private Solution sol;
	public Solution getSol() {
		return sol;
	}

	public void setSol(Solution sol) {
		this.sol = sol;
	}
	private RealtimePlayer player;
	public RealtimePlayer getPlayer() {
		return player;
	}

	public void setPlayer(RealtimePlayer player) {
		this.player = player;
	}
	private int generationNumber; //number of generations that can/will be generated
	public int getGenerationNumber() {
		return generationNumber;
	}

	public void setGenerationNumber(int generationNumber) {
		this.generationNumber = generationNumber;
	}
	private ArrayList<Critic> cl = new ArrayList<Critic>();
	
	private Integer genIndex = 1; //index of current generation in evolution
	public Integer getGenIndex() {
		return genIndex;
	}

	public void setGenIndex(Integer genIndex) {
		this.genIndex = genIndex;
		Integer print = genIndex-1;
		this.genIndexLabel.setText(print.toString());
		if(genIndex.equals(0)) {
			fitnessLabel.setText("");
			patternLabel.setText("");
		}
	}
	private int bpm = 120;
	private Boolean addBestFromLastRound = true;
	public Boolean getAddBestFromLastRound() {
		return addBestFromLastRound;
	}

	public void setAddBestFromLastRound(Boolean addBestFromLastRound) {
		this.addBestFromLastRound = addBestFromLastRound;
		if(sol != null) {
			sol.getPopulation().setAddBestFromLastRound(addBestFromLastRound);
		}
	}
	TemporalPLP plp;
	JLabel fitnessLabel;
	JLabel patternLabel;
	JLabel genIndexLabel;
	static final Color background = new Color(37, 42, 54);
	public GUI() {
		initializeGui();
	}

	private void initializeGui() {
		JFrame frame = new JFrame();
		JPanel pane = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		pane.setLayout(new GridBagLayout());
		pane.setBackground(background);
		frame.setTitle("Muvolution");
		frame.add(pane);
		frame.setSize(1000, 260);
		frame.setResizable(false);
		
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
		            	 if(!cl.stream().anyMatch(c -> c instanceof ChildrensMelody)) {
		            		 cl.add(new ChildrensMelody());
		            	 }
		             } else {
		            	 for(int i = 0; i < cl.size(); i++) {
		     				if(cl.get(i) instanceof ChildrensMelody) {
		     					cl.remove(i);
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
		            	 if(!cl.stream().anyMatch(c -> c instanceof ReverseChildrensMelody)) {
		            		 cl.add(new ReverseChildrensMelody());
		            	 }
		             } else {
		            	 for(int i = 0; i < cl.size(); i++) {
		     				if(cl.get(i) instanceof ReverseChildrensMelody) {
		     					cl.remove(i);
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
		            	 if(!cl.stream().anyMatch(c -> c instanceof ClosenessOfNotes)) {
		            		 cl.add(new ClosenessOfNotes());
		            	 }
		             } else {
		            	 for(int i = 0; i < cl.size(); i++) {
		     				if(cl.get(i) instanceof ClosenessOfNotes) {
		     					cl.remove(i);
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
		            	 if(!cl.stream().anyMatch(c -> c instanceof ReverseClosenessOfNotes)) {
		            		 cl.add(new ReverseClosenessOfNotes());
		            	 }
		             } else {
		            	 for(int i = 0; i < cl.size(); i++) {
		     				if(cl.get(i) instanceof ReverseClosenessOfNotes) {
		     					cl.remove(i);
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
		            	 if(!cl.stream().anyMatch(c -> c instanceof CFriend)) {
		            		 cl.add(new CFriend());
		            	 }
		             } else {
		            	 for(int i = 0; i < cl.size(); i++) {
		     				if(cl.get(i) instanceof CFriend) {
		     					cl.remove(i);
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
		            	 if(!cl.stream().anyMatch(c -> c instanceof ReverseCFriend)) {
		            		 cl.add(new ReverseCFriend());
		            	 }
		             } else {
		            	 for(int i = 0; i < cl.size(); i++) {
		     				if(cl.get(i) instanceof ReverseCFriend) {
		     					cl.remove(i);
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
		            	 if(!cl.stream().anyMatch(c -> c instanceof DurationCritic)) {
		            		 cl.add(new DurationCritic());
		            	 }
		             } else {
		            	 for(int i = 0; i < cl.size(); i++) {
		     				if(cl.get(i) instanceof DurationCritic) {
		     					cl.remove(i);
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
		            	 if(!cl.stream().anyMatch(c -> c instanceof ReverseDurationCritic)) {
		            		 cl.add(new ReverseDurationCritic());
		            	 }
		             } else {
		            	 for(int i = 0; i < cl.size(); i++) {
		     				if(cl.get(i) instanceof ReverseDurationCritic) {
		     					cl.remove(i);
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
		    	if(cl.isEmpty()) {
		    		btnReset.doClick();
		    	} else {
		    		if(getSol() == null || getGenIndex()-1 == getGenerationNumber()) {
			    		genIndex = 1;
			    		setGenerationNumber(Integer.parseInt(genNumber.getText()));
			    		restartPlayer();
			    		setSol(new Solution(getGenerationNumber()));
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
				setGenIndex(1);
	    		setGenerationNumber(Integer.parseInt(genNumber.getText()));
	    		restartPlayer();
    			setSol(new Solution(getGenerationNumber()));
    			calculateWaitTime();
			}
		});
		JToggleButton btnLetBestMoveOn = new JToggleButton("Keep best pattern");
		btnLetBestMoveOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){  
				if(((JToggleButton)e.getSource()).isSelected()) {
	            	 setAddBestFromLastRound(true);
	             } else {
	            	 setAddBestFromLastRound(false);
	             }
	        }
		});
		JButton btnResetDurations = new JButton("Reset Durations");
		btnResetDurations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getSol().resetDuration();
			}
		});
		
		Font font = new Font("Lucida Sans", Font.PLAIN, 18);
		Color foreGround = new Color(105, 225, 255);
		// add components to GUI
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(5,5,5,5);
		c.gridx = 0;
		c.gridy = 0;
		JPanel smallPane2 = new JPanel();
		smallPane2.setBackground(background);
		JLabel genInL = new JLabel("Generation: ");
		genInL.setFont(font);
		genInL.setForeground(foreGround);
		smallPane2.add(genInL);
		genIndexLabel = new JLabel();
		genIndexLabel.setFont(font);
		genIndexLabel.setForeground(foreGround);
		smallPane2.add(genIndexLabel);
		pane.add(smallPane2, c);
		c.gridy = 0;
		c.gridx = 1;
		JLabel numGenLabel = new JLabel("Number of Generations: ");
		numGenLabel.setForeground(foreGround);
		pane.add(numGenLabel,c);
		JPanel smallPane = new JPanel();
		smallPane.setBackground(background);
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
		btnLetBestMoveOn.setSelected(true);
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
		c.gridy = 3;
		c.gridx = 2;
		pane.add(btnResetDurations,c);
		btnResetDurations.setBackground(Color.white);
		c.gridy = 1;
		c.gridx = 3;
		pane.add(btnChildrensMelody,c);
		c.gridy = 2;
		c.gridx = 3;
		pane.add(btnReverseChildrensMelody,c);
		c.gridx = 0;
		c.gridy = 4;
		fitnessLabel = new JLabel("Fitness:       ");
		fitnessLabel.setFont(font);
		fitnessLabel.setForeground(foreGround);
		pane.add(fitnessLabel,c);
		c.gridy = 4;
		c.gridx = 1;
		JLabel lab = new JLabel("Fittest Pattern: ");
		lab.setFont(font);
		lab.setForeground(foreGround);
		pane.add(lab,c);
		patternLabel = new JLabel();
		patternLabel.setFont(font);
		patternLabel.setForeground(foreGround);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 4;
		c.gridy = 4;
		c.gridx = 2;
		pane.add(patternLabel, c);
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
		     }, waitTime*(getGenerationNumber()-getGenIndex()+1), TimeUnit.MILLISECONDS);    
	}
	DecimalFormat df = new DecimalFormat("#.00"); 
	private final Runnable evolution = new Runnable() {
	       public void run() {
	    	   if(getGenIndex() > getGenerationNumber()) {
	    		   restartPlayer();
	    		   scheduler.shutdownNow();
	    	   } else {
		    	   patternLabel.setText(getSol().evaluateOnce(getPlayer(), getGenIndex(), bpm, cl).toString());
		    	   fitnessLabel.setText("Fitness: " + df.format(getSol().getPopulation().getMaxFitness()));
		    	   setGenIndex(getGenIndex()+1);
	    	   }
	    	   
	       }
	     };
	     
	//each generation is generated & the best ist played after the calculated waittime.
	//this is important so that the user can change Critics during the process of evolution.
	private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private void calculateWaitTime() {
		//60sec/BPM*amount of quarter notes(duration=0.25) in 1 pattern (8 8th notes = 4 quarter notes)
		waitTime = (long)((float)60/bpm*4)*1000;
		System.out.println("waittime: " + waitTime);
		
	}
	private long waitTime = 0;
	private void restartPlayer() {
		if(getPlayer() != null) {
			player.close();
		}
		try {
			setPlayer(new RealtimePlayer());
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		}
	}


}