import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Random;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;

import org.json.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.apache.commons.lang3.*;

public class JadeContainer {

	public static void main(String[] args) {
		float minC = 0.2f;
		float maxC = 0.3f;

		float minR = 0.3f;
		float maxR = 0.4f;
		
		Random rand = new Random();

		float certanty  = rand.nextFloat() * (maxC - minC) + minC;
		float resistance  = rand.nextFloat() * (maxR - minR) + minR;

		
        // About Container
		Runtime rt = Runtime.instance();
		ProfileImpl pc = new ProfileImpl(false);
		pc.setParameter(ProfileImpl.MAIN_HOST, "localhost");
		AgentContainer container = rt.createAgentContainer(pc);

		String[] agents = new String[20];
		String[] arguments = new String[1000];

		int nbAgents = 0;
		int i = 0;
		
		JSONParser parser = new JSONParser();
		
        // READ JSON FILE
		try {
			org.json.simple.JSONArray obj = (org.json.simple.JSONArray) parser.parse(new FileReader("/home/sam/workspace/12AngryMen/src/debate.json"));
            for (Object o : obj)
            	{
            	org.json.simple.JSONArray orray = (org.json.simple.JSONArray)  o;
            	// Speaker
            	// System.out.println(orray.get(0));
            	// Speech
            	// System.out.println(orray.get(1));
            	
            	if (! ArrayUtils.contains(agents, orray.get(0)))
            		{
            		agents[nbAgents] = (String) orray.get(0);
            		
            		nbAgents++;
            		}
            	else
            		{
            		
            		}
               	}
            
           
			// Creating all 12 AngryAgents
            AgentController agentControler ;

            for (int j = 0; j < nbAgents; j++) {
		            System.out.println(agents[j]);
		
		            if (j==2){            	
		            	certanty = 0.9f;
		            	resistance = 0.01f;
		            }
		            else{
		            	certanty = rand.nextFloat() * (maxC - minC) + minC;
		            	resistance = rand.nextFloat() * (maxR - minR) + minR;

		            }
		            
	            	agentControler = container.createNewAgent(
							agents[j], "JuryMember", new Object[]{
									Float.toString(certanty), Float.toString(resistance)
									}
							);		            
		            agentControler.start();
			}
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
