import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.ControllerException;
import jade.util.leap.Properties; 

public class MainContainer {

	public static void main(String[] args) {
		try {
			Runtime rt = Runtime.instance();
			ProfileImpl pc = new ProfileImpl(false);

			AgentContainer container = rt.createMainContainer(pc);
			container.start();

		} catch (ControllerException e) {
			e.printStackTrace();
		}
		
		
	}

}
