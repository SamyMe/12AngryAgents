
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

import javax.swing.plaf.synth.SynthOptionPaneUI;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.AMSService;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
public class JuryMember extends Agent {

	private float certainty;
	// Message sender's certainty
	private float certainty2;
	private float resistance ;
	private String myname; 
	// If a lot of messages in cue, don't send another one (not sure it works)
	private Boolean shutUp = false;
	 
	@Override
	protected void setup() {
		Object[] args = getArguments();
		
		certainty = Float.parseFloat((String)args[0]);
		resistance = Float.parseFloat((String)args[1]);
				
		// Agent's name
		myname = this.getAID().getLocalName();
		System.out.println("Hello I'm a Jury Member " + myname);
		
//		TickerBehavior, might be interesting
//		
//		addBehaviour(new TickerBehaviour(this, 100) {
//			private int counter = 0;
//			@Override
//			protected void onTick() {
//				++counter;				
//				System.out.println("Argument number " + counter);
//			}
//		});
		
		addBehaviour(new CyclicBehaviour() {

			//////////////////////
	        //  SEND A MESSAGE  //
			//////////////////////
			@Override
			public void action() {
			
//				Sleep for better readability
//
//				try {
//					TimeUnit.SECONDS.sleep(1);
//				} catch (InterruptedException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}

				
		        // GET LIST OF ALL AGENTS
		        AMSAgentDescription [] agents = null;	
		        try {
		            SearchConstraints c = new SearchConstraints();
		            c.setMaxResults ( new Long(-1) );
		            agents = AMSService.search(this.myAgent, new AMSAgentDescription (), c );
		        }
		        catch (Exception e) { 
		        }
			
		        
		        // SEND MESSAGE TO ALL AGENTS
		        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
				for (int i = 0; i < agents.length; i++) {
					
					// Don't send message to myself, and shutup if others are talking
					if(agents[i].getName().getLocalName() != myname && ! shutUp){
						msg.addReceiver(agents[i].getName());	
						msg.setLanguage("English");
						msg.setOntology(myname);
						msg.setContent( Float.toString(certainty));
						send(msg);			
					}				
				}
				
				if(agents.length == 4) takeDown();
			}
		});
	
		/////////////////////
        // RECEIVE MESSAGE //
		/////////////////////
		addBehaviour(new CyclicBehaviour() {
			
			@Override
			public void action() {

	        // RECEIVE MESSAGE
			MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
			
							// If you want to match complex stuff :
							//
							// = MessageTemplate.and(  ...or(  ...not(
							// 				MessageTemplate.MatchOntology(),
							//				MessageTemplate.Match...
							// );
			
			ACLMessage message = receive();
			
			if(message != null){
				shutUp = true;
				certainty2 = Float.parseFloat(message.getContent());
				certainty += resistance*(certainty2 - certainty);
			}
			else {
				// If no message found
				// Stop the execution of the cyclic behavior until the reception of a new message
				shutUp = false;
				block();
				
			}
			System.out.println(myname+" certainty is : "+Float.toString(certainty));
					

			// Write certainty value to file
			try {
			    Files.write(Paths.get("/home/sam/workspace/12AngryMen/results"), (myname+","+Float.toString(certainty)+"\n").getBytes(), StandardOpenOption.APPEND);
			}catch (IOException e) {
			    //exception handeling ...
			}
			
			System.out.print( Float.toString(certainty)+"\n");
			
			// If certainty above threshold, agent says he believes and stops 
			if(certainty>0.5 && !myname.contains("SEVEN") ){
				System.out.println(myname+" believe the kid is Innoncent !!\n");
				doDelete();
			}
			
		}
			
		});
	}
	
	@Override
	protected void takeDown() {
		doDelete();
	}
}