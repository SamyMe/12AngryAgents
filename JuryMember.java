
import java.util.concurrent.CyclicBarrier;

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

	private String speech;
	
	@Override
	protected void setup() {
		Object[] args = getArguments();
		//speech = (String)args[0];
		
		System.out.println("Hello I'm a Jury Member" + this.getAID().getName());
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
			
			@Override
			public void action() {

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
					msg.addReceiver(agents[i].getName());
					System.out.println(agents[i].getName());;
				}
				msg.setLanguage("English");
				msg.setOntology("argument");
				msg.setContent("Today it’s raining !!!!!!!!!!!!");
				send(msg);
				
				System.out.print("message sent");

		        // RECEIVE MESSAGE
				MessageTemplate messageTemplate = MessageTemplate.and(
							MessageTemplate.MatchPerformative(ACLMessage.INFORM),
							MessageTemplate.MatchOntology("argument"));
				
				ACLMessage message = receive();
				if(message != null){
					System.out.print("Reception du message " + message.getContent());
					
				}
				else {
					// If no message found
					// Stop the execution of the cyclic behavior untill the reception of a new message
					block();
					
				}
			}
		});
	}
	
	@Override
	protected void takeDown() {
		System.out.println("Agent " + this.getAID().getName()+" distroyed");
		super.takeDown();
	}
}