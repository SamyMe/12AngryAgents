import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
		
		
		// Empty the file
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("/home/sam/workspace/12AngryMen/results", "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
		writer.close();
		
		// Min and Max Certainty
		float minC = 0.2f;
		float maxC = 0.3f;

		// Min and Max Resistance
		float minR = 0.2f;
		float maxR = 0.3f;
		
		Random rand = new Random();

		// Random generators
		float certanty  = rand.nextFloat() * (maxC - minC) + minC;
		float resistance  = rand.nextFloat() * (maxR - minR) + minR;

		
        // About Container
		Runtime rt = Runtime.instance();
		ProfileImpl pc = new ProfileImpl(false);
		pc.setParameter(ProfileImpl.MAIN_HOST, "localhost");
		AgentContainer container = rt.createAgentContainer(pc);

		// Will contain agents names after extraction from script
		String[] agents = new String[20];
		
		// Unused (no need for the for now arguments)
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
            	
            	// Creating Agents list
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

		            // SEVEN's parameters 
		            if (j==2){            	
		            	certanty = 0.9f;
		            	resistance = 0.01f;
		            }
		            // Other agents
		            else{
		            	certanty = rand.nextFloat() * (maxC - minC) + minC;
		            	resistance = rand.nextFloat() * (maxR - minR) + minR;

		            }
		            
		            //  Launching the agent
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
