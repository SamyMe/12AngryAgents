import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;


public class JadeContainer {

	public static void main(String[] args) {

		try {
			Runtime rt = Runtime.instance();
			
			ProfileImpl pc = new ProfileImpl(false);
			pc.setParameter(ProfileImpl.MAIN_HOST, "localhost");
			
			AgentContainer container = rt.createAgentContainer(pc);
			
			AgentController agentControler = container.createNewAgent(
											"Jury1", "JuryMember", new Object[]{}
											);
			
			
			container.start();
			} 
		catch (ControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
	}

}
