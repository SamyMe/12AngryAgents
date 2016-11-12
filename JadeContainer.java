import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Iterator;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;

import org.json.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class JadeContainer {

	public static void main(String[] args) {
        JSONParser parser = new JSONParser();

        // READ JSON FILE
		try {
			org.json.simple.JSONArray obj = (org.json.simple.JSONArray) parser.parse(new FileReader("/home/sam/workspace/12AngryMen/src/debate.json"));
            for (Object o : obj)
            {
            	org.json.simple.JSONArray orray = (org.json.simple.JSONArray)  o;
            	// Speaker
            	System.out.println(orray.get(0));
            	// Speech
            	System.out.println(orray.get(1));
            	
            }
            
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
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
