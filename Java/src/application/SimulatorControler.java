package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class SimulatorControler {

	@FXML
	private ScrollPane scrlpane_MainDisplay;
	
	@FXML
	private AnchorPane pane_MainDisplay;
	
	@FXML
	private Button btn_Play_Pause;
	
	@FXML
	private TextField txt_PlaySpeed;
	
	
	/*
	 * 
	 * Graph Selection
	 * 
	 */
	
	@FXML
	private AnchorPane pane_Selection_Graph;
		
	@FXML
	private Label lbl_GraphSelection_Title;
	
	@FXML
	private Label lbl_GraphSelection_NumberOfNodes;
	
	@FXML
	private Label lbl_GraphSelection_InteractionModel;
	
	@FXML
	private Label lbl_GraphSelection_UpdateMechanism;
	
	@FXML
	private TextField txt_GraphSelection_TValue;
	
	@FXML
	private TextField txt_GraphSelection_RValue;
	
	@FXML
	private TextField txt_GraphSelection_PValue;
	
	@FXML
	private TextField txt_GraphSelection_SValue;
	
	
	@FXML
	private ChoiceBox<String> choiceBox_GraphSelection_SetZoom;
	
	
	/*
	 * 
	 * Node Selection
	 * 
	 */
	
	@FXML
	private AnchorPane pane_Selection_Node;
	
	@FXML
	private Label lbl_NodeSelection_Title;
	
	@FXML
	private CheckBox cbox_NodeSelection_Cooperation;
	
	@FXML
	private TextField txt_NodeSelection_XValue;
	
	@FXML
	private TextField txt_NodeSelection_YValue;	
	

	
	private int payoff_T,payoff_R,payoff_P,payoff_S;
	private int numberOfNodes;
	private Vertex currentNode;
	private ArrayList<Vertex> nodes = new ArrayList<Vertex> ();
	
	//used to reset the graph to its initial state
	private ArrayList<Integer> initial_nodes_strategy = new ArrayList<Integer> ();	
	
	private Timer timer;
	private int playSpeed;
	
	private int interaction_model, update_mechanism;
	
	private double zoomLevel, nodeRadius, lineWidth, glowRadius;
	
	/*
	 * 
	 * Image Variables
	 * 
	 */
	
	@FXML
	private CheckBox cbox_ImageView;
	
		
	@FXML
	private Button btn_SaveImage;
	
	@FXML
	private Canvas canvas;
	
	@FXML
    public void initialize() throws IOException {	
		
		// open file and initialize variables		
		int all_good = initObjects();
		
		// TO DO: Handle error all_good = 0 
		
		txt_PlaySpeed.setText("1000");
		playSpeed=1000;
		
		if(interaction_model == 1 && update_mechanism == 1)
		{
			int[] payoffs = {payoff_T, payoff_R, payoff_P, payoff_S};
			
			// Calculate current Payoff for each node				
			for(int i = 0; i<numberOfNodes; i++)
			{				
				nodes.get(i).calculateNextPayoff(payoffs);
				nodes.get(i).updateCurrentPayoff();
			}			
		}
		else if(interaction_model == 1 && update_mechanism == 2)
		{
			int[] payoffs = {payoff_T, payoff_R, payoff_P, payoff_S};
			
			// Calculate current Payoff for each node				
			for(int i = 0; i<numberOfNodes; i++)
			{				
				nodes.get(i).calculateNextPayoff(payoffs);
				nodes.get(i).updateCurrentPayoff();
			}
		}
		else
			System.out.println("Undefined behaviour for current interaction_model and update_mechanism");
		
		
		// Add text field out of focus listeners
		addTextFieldSubmitListeners();
		
		
		
		nodeRadius=20;
		lineWidth=3;
		glowRadius=35;
		
		//set zoom options
		choiceBox_GraphSelection_SetZoom.getItems().addAll("1", "2", "4", "8", "16");
		choiceBox_GraphSelection_SetZoom.setValue("1");
		zoomLevel=1;
		
		// add a listener 
		choiceBox_GraphSelection_SetZoom.getSelectionModel().selectedItemProperty().addListener( (v,oldValue,newValue) -> {	
			
			
			nodeRadius=20/Double.parseDouble(newValue);
			lineWidth=3/Double.parseDouble(newValue);
			glowRadius=35/Double.parseDouble(newValue);
			
			//if<1 we zoom out and if >1 we zoom in
			zoomLevel = Double.parseDouble(oldValue)/Double.parseDouble(newValue);
			
			//update visual values
			for(int i = 0; i<numberOfNodes; i++)
			{				
				//used the nodes
				nodes.get(i).setCenterX(nodes.get(i).getCenterX()*zoomLevel);
				nodes.get(i).setCenterY(nodes.get(i).getCenterY()*zoomLevel);
				
				//update edges
				for(int j = 0; j<nodes.get(i).getEdges().size(); j++)
				{
					if(nodes.get(i).getEdges().get(j).getNodeA() == nodes.get(i))
		    		{			    			
						nodes.get(i).getEdges().get(j).setStartX(nodes.get(i).getCenterX());
						nodes.get(i).getEdges().get(j).setStartY(nodes.get(i).getCenterY());							
		    		}
		    		else
		    		{
		    			nodes.get(i).getEdges().get(j).setEndX(nodes.get(i).getCenterX());
		    			nodes.get(i).getEdges().get(j).setEndY(nodes.get(i).getCenterY());
		    		}
				}
				
			}
			
			
			//set visual update
			for(int i = 0; i<numberOfNodes; i++)
			{				
				//used to update the visuals
				nodes.get(i).setMark(0);			
			}
			
			//update visuals
			updateMainDisplay();
		});		
		
		cbox_ImageView.selectedProperty().addListener( (v,oldValue,newValue) -> {
			
			int allgood = 1;
			//check if lattice graph
			for(int i = 0; i<numberOfNodes; i++)
			{				
				if(nodes.get(i).getDegree()!=4)
					allgood=0;
			}
			
			int length=0, height=0;
			
			for(int i = 2; i<numberOfNodes; i++)
			{
				if(nodes.get(i).getCenterY().compareTo(nodes.get(1).getCenterY())!= 0)
				{
					length = i;
					break;
				}
			}
			length = length+1;
			height=numberOfNodes/length;			
						
			if((double) height != (double)numberOfNodes/length)
				allgood=0;
			
			//if all good
			if(allgood == 1)
			{
				updateMainDisplay();
				if(newValue == true)
					{						
						btn_SaveImage.setDisable(false);
					}
				else
					{						
						btn_SaveImage.setDisable(true);
					}
			}
			else
				cbox_ImageView.setSelected(false);
		});
		
		
		
		changeSelectionPaneToGraph();
		
		//add listeners for change in the scrollpane (so we can only show the nodes and edges that are visible)		
		addMainDisplayScrollListeners();
		
		updateMainDisplay();
		
	}
	
	// open file and initialize variables
	public int initObjects()throws IOException
	{
		
		// check if we have a current graph
		File[] temp_files = new File("./CurrentGraph").listFiles(new FilenameFilter() { 
			 @Override public boolean accept(File dir, String name) { return name.endsWith(".csv"); } });

		// make directory if it dosen't exist
		 if (temp_files==null) {
			 new File("./CurrentGraph").mkdirs();	
			 
			 return 0; // file dosen't exist
			}
		 else
		 {
		 
			 //check if it's not empty 
			if(temp_files.length!=0){
				
				// open file
				try {
				FileInputStream fis = new FileInputStream(temp_files[0].getAbsolutePath());
			    BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			    String line;
			    
			    int temp_numberOfNodes;
			    
			    //get interaction_model & update_mechanism
			    if((line = br.readLine()) != null)
			    {
			    	
			    	String split[] = line.split(","); 
			    	
			    	if(split.length==2 )
			    	{
			    		interaction_model=Integer.parseInt(split[0]);
			    		update_mechanism=Integer.parseInt(split[1]);				    	
			    	}
			    	else
			    	{
			    		//throw bad file format error
			    		System.out.println("Bad file format");
			    		br.close();
			    		fis.close();
			    		return 0;
			    	}
			    }
			    else
			    {
			    	//throw bad file format error
			    	System.out.println("Bad file format");
			    	br.close();
			    	fis.close();
			    	return 0;
			    }
			    
			    if(interaction_model == 1 && update_mechanism == 1)
			    {
			    	
			    	// get payoffs
				    if((line = br.readLine()) != null)
				    {
				    	
				    	String split[] = line.split(","); 
				    	
				    	if(split.length==4 )
				    	{
					    	payoff_T=Integer.parseInt(split[0]);
					    	payoff_R=Integer.parseInt(split[1]);
					    	payoff_P=Integer.parseInt(split[2]);
					    	payoff_S=Integer.parseInt(split[3]);
				    	}
				    	else
				    	{
				    		//throw bad file format error
				    		System.out.println("Bad file format");
				    		br.close();
				    		fis.close();
				    		return 0;
				    	}
				    }
				    else
				    {
				    	// File empty, throw bad file format error
				    	System.out.println("Bad file format");
				    	br.close();
				    	fis.close();
				    	return 0;
				    }
				    
				    // get number of nodes
				    if((line = br.readLine()) != null)
				    {
				    	
				    	String split[] = line.split(","); 
				    	
				    	if(split.length==1 )
				    	{
				    		temp_numberOfNodes=Integer.parseInt(split[0]);
					    	
				    	}
				    	else
				    	{
				    		//throw bad file format error
				    		System.out.println("Bad file format");
				    		br.close();
					    	fis.close();
					    	return 0;
				    	}
				    }
				    else
				    {
				    	//throw bad file format error
				    	System.out.println("Bad file format");
				    	br.close();
				    	fis.close();
				    	return 0;
				    }
				    
				    
				  //initialize nodes
				    for(int i=0; i<temp_numberOfNodes; i++)
				    {
				    	placeNode(0, 0, 1);
				    }
				    
				    // set nodes details
				    for(int i=0; i<temp_numberOfNodes; i++)
				    {
				    	// get node i
					    if((line = br.readLine()) != null)
					    {
					    	
					    	String split[] = line.split(","); 
					    	
					    	if(split.length>3 )
					    	{
					    		
					    		// Set node position
					    		nodes.get(i).setCenterX(Double.parseDouble(split[1]));
					    		nodes.get(i).setCenterY(Double.parseDouble(split[2]));		    		
					    		
					    		
					    		// set defection
					    		if(Integer.parseInt(split[0])==0)
					    			{
					    				nodes.get(i).setCurrentStrategy(0);				    				
					    				
					    				initial_nodes_strategy.add(0);
					    			}
					    		else
					    			{
					    				nodes.get(i).setCurrentStrategy(1);				    				
					    				
					    				initial_nodes_strategy.add(1);
					    			}
					    		
					    		int degree = Integer.parseInt(split[3]);
					    		
					    		//set up adjacency
					    		if(split.length==4 + degree)
					    		{
					    			for(int t=0; t<degree; t++)
								    {
					    				//get neighbor
					    				int neighbor = Integer.parseInt(split[4 + t]);
					    								    				
					    				// see if neighbor has current node as a neighbor so we can get the edge
					    				if(neighbor>i)			    					
				    					{
				    						// set currentNode
					    					currentNode = nodes.get(i);
					    				
					    					placeEdge(nodes.get(i) , nodes.get(neighbor)); 
				    					}  
								    }
					    			
					    		}
					    		else
						    	{
						    		//throw bad file format error
						    		System.out.println("Bad file format");
						    		br.close();
							    	fis.close();
							    	return 0;
						    	}
					    		
					    	}
					    	else
					    	{
					    		//throw bad file format error
					    		System.out.println("Bad file format");
					    		br.close();
						    	fis.close();
						    	return 0;
					    	}
					    }
					    else
					    {
					    	//throw bad file format error
					    	System.out.println("Bad file format");
					    	br.close();
					    	fis.close();
					    	return 0;
					    }
				    }		
				    
				    // readjust edge positions
				    for(int i=0; i<temp_numberOfNodes; i++)
				    {
				    	ArrayList<Edge> edges = nodes.get(i).getEdges();
				    	for(int j=0; j<edges.size(); j++)
					    {
				    		if(edges.get(j).getNodeA() == nodes.get(i))
				    		{			    			
				    			edges.get(j).setStartX(nodes.get(i).getCenterX());
				    			edges.get(j).setStartY(nodes.get(i).getCenterY());							
				    		}
				    		else
				    		{
				    			edges.get(j).setEndX(nodes.get(i).getCenterX());
				    			edges.get(j).setEndY(nodes.get(i).getCenterY());
				    		}
					    }
				    	
				    }				    	
			    }
			    else if(interaction_model == 1 && update_mechanism == 2)
			    {
			    	
			    	// get payoffs
				    if((line = br.readLine()) != null)
				    {
				    	
				    	String split[] = line.split(","); 
				    	
				    	if(split.length==4 )
				    	{
					    	payoff_T=Integer.parseInt(split[0]);
					    	payoff_R=Integer.parseInt(split[1]);
					    	payoff_P=Integer.parseInt(split[2]);
					    	payoff_S=Integer.parseInt(split[3]);
				    	}
				    	else
				    	{
				    		//throw bad file format error
				    		System.out.println("Bad file format");
				    		br.close();
				    		fis.close();
				    		return 0;
				    	}
				    }
				    else
				    {
				    	// File empty, throw bad file format error
				    	System.out.println("Bad file format");
				    	br.close();
				    	fis.close();
				    	return 0;
				    }
				    
				    // get number of nodes
				    if((line = br.readLine()) != null)
				    {
				    	
				    	String split[] = line.split(","); 
				    	
				    	if(split.length==1 )
				    	{
				    		temp_numberOfNodes=Integer.parseInt(split[0]);
					    	
				    	}
				    	else
				    	{
				    		//throw bad file format error
				    		System.out.println("Bad file format");
				    		br.close();
					    	fis.close();
					    	return 0;
				    	}
				    }
				    else
				    {
				    	//throw bad file format error
				    	System.out.println("Bad file format");
				    	br.close();
				    	fis.close();
				    	return 0;
				    }
				    
				    
				  //initialize nodes
				    for(int i=0; i<temp_numberOfNodes; i++)
				    {
				    	placeNode(0, 0, 1);
				    }
				    
				    // set nodes details
				    for(int i=0; i<temp_numberOfNodes; i++)
				    {
				    	// get node i
					    if((line = br.readLine()) != null)
					    {
					    	
					    	String split[] = line.split(","); 
					    	
					    	if(split.length>3 )
					    	{
					    		
					    		// Set node position
					    		nodes.get(i).setCenterX(Double.parseDouble(split[1]));
					    		nodes.get(i).setCenterY(Double.parseDouble(split[2]));		    		
					    		
					    		
					    		// set defection
					    		if(Integer.parseInt(split[0])==0)
					    			{
					    				nodes.get(i).setCurrentStrategy(0);				    				
					    				
					    				initial_nodes_strategy.add(0);
					    			}
					    		else
					    			{
					    				nodes.get(i).setCurrentStrategy(1);				    				
					    				
					    				initial_nodes_strategy.add(1);
					    			}
					    		
					    		int degree = Integer.parseInt(split[3]);
					    		
					    		//set up adjacency
					    		if(split.length==4 + degree)
					    		{
					    			for(int t=0; t<degree; t++)
								    {
					    				//get neighbor
					    				int neighbor = Integer.parseInt(split[4 + t]);
					    								    				
					    				// see if neighbor has current node as a neighbor so we can get the edge
					    				if(neighbor>i)			    					
				    					{
				    						// set currentNode
					    					currentNode = nodes.get(i);
					    				
					    					placeEdge(nodes.get(i) , nodes.get(neighbor)); 
				    					}  
								    }
					    			
					    		}
					    		else
						    	{
						    		//throw bad file format error
						    		System.out.println("Bad file format");
						    		br.close();
							    	fis.close();
							    	return 0;
						    	}
					    		
					    	}
					    	else
					    	{
					    		//throw bad file format error
					    		System.out.println("Bad file format");
					    		br.close();
						    	fis.close();
						    	return 0;
					    	}
					    }
					    else
					    {
					    	//throw bad file format error
					    	System.out.println("Bad file format");
					    	br.close();
					    	fis.close();
					    	return 0;
					    }
				    }		
				    
				    // readjust edge positions
				    for(int i=0; i<temp_numberOfNodes; i++)
				    {
				    	ArrayList<Edge> edges = nodes.get(i).getEdges();
				    	for(int j=0; j<edges.size(); j++)
					    {
				    		if(edges.get(j).getNodeA() == nodes.get(i))
				    		{			    			
				    			edges.get(j).setStartX(nodes.get(i).getCenterX());
				    			edges.get(j).setStartY(nodes.get(i).getCenterY());							
				    		}
				    		else
				    		{
				    			edges.get(j).setEndX(nodes.get(i).getCenterX());
				    			edges.get(j).setEndY(nodes.get(i).getCenterY());
				    		}
					    }
				    	
				    }		    
			    }
			    else
					System.out.println("Undefined behaviour for current interaction_model and update_mechanism");
			    
			    	
			    
			    
			    br.close();
			    fis.close();
				
				
				}
				catch (FileNotFoundException ex) {
					System.out.println("File not found!");	
					return 0;
				}
				
				
			}
			else
			{				
				return 0; // file empty
			}
		 }
		 
		currentNode=null;
		return 1; // all good
	}
	

	// What to do when the user clicks on the screen
	public void mainDisplayClick(MouseEvent event)throws IOException
	{		
		// Deselect if possible
		currentNode=null;		
		
		//make the graph info visible
		changeSelectionPaneToGraph();
	}
		
	public void placeNode(double ev_x, double ev_y, int defect)
	{
		numberOfNodes++;
		
		if(interaction_model == 1 && update_mechanism == 1)
		{
			Vertex c = new Vertex_1_1(defect);
			
			c.setCenterX(ev_x);
			c.setCenterY(ev_y);		
			
			// add the node to the list
			nodes.add(c);
		}
		else if(interaction_model == 1 && update_mechanism == 2)
		{
			Vertex c = new Vertex_1_2(defect);
			
			c.setCenterX(ev_x);
			c.setCenterY(ev_y);		
			
			// add the node to the list
			nodes.add(c);
		}
		else
			System.out.println("Undefined behaviour for current interaction_model and update_mechanism");
		
	}
	

	public void showNode(Vertex v)
	{
		
		Circle c;
		if(v.getVisualObject()==null)
			c = new Circle();
		else
			c=v.getVisualObject();
		
		//Initialise or update the visual object
		c.setCenterX(v.getCenterX());
		c.setCenterY(v.getCenterY());
		c.setRadius(nodeRadius);
		c.setStroke(Color.BLACK);
		
		if(v.getVisualObject()==null)
		{
			// add an on mouse click event listener 
			c.setOnMouseClicked(new EventHandler<MouseEvent>(){
				 
		          @Override
		          public void handle(MouseEvent e) {// select node
		    		  currentNode = v;
		    		  
		    		  // Change display info 
		    		  changeSelectionPaneToNode(); 
		    		  
		    		  // make sure the events stop here
		        	  e.consume();}
		              
		          
		          });
			
			
		}
		
		if(interaction_model == 1 && update_mechanism == 1)
		{
			if(v.getCurrentStrategy()==0)
			{		
				c.setFill(Color.rgb(150, 0, 0)); //defector
			}
			else
			{
				c.setFill(Color.rgb(0, 100, 255)); // cooperator
			}	        			
		}
		else if(interaction_model == 1 && update_mechanism == 2)
		{
			if(v.getCurrentStrategy()==0)
			{		
				c.setFill(Color.rgb(150, 0, 0)); //defector
			}
			else
			{
				c.setFill(Color.rgb(0, 100, 255)); // cooperator
			}
		}
		else
			System.out.println("Undefined behaviour for current interaction_model and update_mechanism");
		
		
		
		if(v.getVisualObject()==null)
		{
			pane_MainDisplay.getChildren().addAll(c);
			v.setVisualObject(c);
		}
		
		//add glow
		if(v==currentNode)
		{
			
			if(v.getVisualObject() != null)
			{
				// add new visuals
				DropShadow borderGlow = new DropShadow();
				borderGlow.setColor(Color.rgb(255, 255, 100));
				borderGlow.setOffsetX(0f);
				borderGlow.setOffsetY(0f);
				borderGlow.setHeight(glowRadius);
				borderGlow.setWidth(glowRadius);
				borderGlow.setSpread(0.5);
				
				v.getVisualObject().setEffect(borderGlow); 
			}
		}
		else if(currentNode!= null && currentNode.getNeighbors().contains(v))
		{
			DropShadow borderGlow2 = new DropShadow();
			borderGlow2.setOffsetX(0f);
			borderGlow2.setOffsetY(0f);
			borderGlow2.setHeight(glowRadius);
			borderGlow2.setWidth(glowRadius);
			borderGlow2.setColor(Color.rgb(100, 100, 200));
			borderGlow2.setSpread(0.5);					          					  	
			v.getVisualObject().setEffect(borderGlow2);				
		}		
	}
	
	
	public int placeEdge(Vertex node , Vertex neighbor)
	{				  
		  // Check if start and end node are not the same node
		 if(node!=neighbor)
		  {
			//Check if the edge is not there already
			  if(node.isNeighbor(neighbor) == -1)
			  {
				  Edge temp_edge = new Edge(node, neighbor);
				  //update neighborhood for both nodes
				  node.addNeighbor(neighbor, temp_edge);
				  neighbor.addNeighbor(node, temp_edge);
				  			
				  temp_edge.setStartX(node.getCenterX());
				  temp_edge.setStartY(node.getCenterY());
				  temp_edge.setEndX(neighbor.getCenterX());
				  temp_edge.setEndY(neighbor.getCenterY());
				  
	  			  return 1;  			  
			  }
		  }
		  return 0;		
	}
	

	public void showEdge(Edge edge)
	{
		 Line temp_edge;
		 
		 if(edge.getVisualObject()==null)
			 temp_edge = new Line();
		 else
			 temp_edge=edge.getVisualObject();
		 
		  // initialise or update the line object	
		  temp_edge.setStartX(edge.getStartX());
		  temp_edge.setStartY(edge.getStartY());
		  temp_edge.setEndX(edge.getEndX());
		  temp_edge.setEndY(edge.getEndY());
		  
		 
		  temp_edge.setStrokeWidth(lineWidth);
		  
		  // add it to the pane
		  if(edge.getVisualObject()==null)
			  pane_MainDisplay.getChildren().addAll(temp_edge);
		  
		  // make it so the nodes are above the edges
		  temp_edge.toBack();
		  
		 
		  		 
		  if(edge.getVisualObject()==null)
		  {
			  edge.setVisualObject(temp_edge);
		  }
	}
	
	
	public void changeSelectionPaneToNode()
	{
		
		// remove old visuals
		  
		  for(int i=0;i<numberOfNodes;i++)
		  {	
			  if(nodes.get(i).getVisualObject() != null)
				  nodes.get(i).getVisualObject().setEffect(null);
		  }
	  
		  
		  
		  if(currentNode.getVisualObject() != null)
		  {
			// add new visuals
			  DropShadow borderGlow = new DropShadow();
			  borderGlow.setColor(Color.rgb(255, 255, 100));
			  borderGlow.setOffsetX(0f);
			  borderGlow.setOffsetY(0f);
			  borderGlow.setHeight(glowRadius);
			  borderGlow.setWidth(glowRadius);
			  borderGlow.setSpread(0.5);
			  
			  currentNode.getVisualObject().setEffect(borderGlow); 
		  }
		  
		  
		  
		  ArrayList<Vertex> neighbors = currentNode.getNeighbors();
		  
		  for(int i=0;i<currentNode.getDegree();i++)
		  {				  
			  if(neighbors.get(i).getVisualObject() != null)
			  {					  
				  DropShadow borderGlow2 = new DropShadow();
				  borderGlow2.setOffsetX(0f);
				  borderGlow2.setOffsetY(0f);
				  borderGlow2.setHeight(glowRadius);
				  borderGlow2.setWidth(glowRadius);
				  borderGlow2.setColor(Color.rgb(100, 100, 200));
				  borderGlow2.setSpread(0.5);					          					  	
				  neighbors.get(i).getVisualObject().setEffect(borderGlow2);					  
			  }
		  }
		
		// swap visible pane		
		pane_Selection_Node.setVisible(true);
		pane_Selection_Graph.setVisible(false);
		
		// Update values
		
		lbl_NodeSelection_Title.setText("Node "+ currentNode.getVertexNumber());
		
		if(currentNode.getCurrentStrategy()== 1)
				 cbox_NodeSelection_Cooperation.setSelected(true);
			 else
				 cbox_NodeSelection_Cooperation.setSelected(false);
		
		 
		txt_NodeSelection_XValue.setText(Double.toString(currentNode.getCenterX()));
		txt_NodeSelection_YValue.setText(Double.toString(currentNode.getCenterY()));			
	}
	
	public void changeSelectionPaneToGraph()
	{
		
		for(int i=0;i<numberOfNodes;i++)
		{
			if(nodes.get(i).getVisualObject()!=null)
				nodes.get(i).getVisualObject().setEffect(null);
		}
		
		pane_Selection_Node.setVisible(false);
		pane_Selection_Graph.setVisible(true);
		
		//show the number of nodes
		lbl_GraphSelection_NumberOfNodes.setText(numberOfNodes + " Nodes");
		
		//show the current payoffs
		txt_GraphSelection_TValue.setText(Integer.toString(payoff_T));
		txt_GraphSelection_RValue.setText(Integer.toString(payoff_R));
		txt_GraphSelection_PValue.setText(Integer.toString(payoff_P));
		txt_GraphSelection_SValue.setText(Integer.toString(payoff_S));
		
		lbl_GraphSelection_InteractionModel.setText("Int. Mod. : " + interaction_model);
		
		lbl_GraphSelection_UpdateMechanism.setText("Upd. Mec. : " + update_mechanism);
		
	}	
	
	public void addTextFieldSubmitListeners()
	{
		
		// timer speed		
		txt_PlaySpeed.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e){
				if(e.getCode() == KeyCode.ENTER)
				{
					pane_Selection_Graph.requestFocus();
				}
			}
			});
		
		txt_PlaySpeed.focusedProperty().addListener((observable, oldValue, newValue) -> {
		    if(newValue==false)
		    {
		    	// check input
		    	try{
		    		int tempImpVal = Integer.parseInt(txt_PlaySpeed.getText());
		    		
		    		if(tempImpVal>=0)
		    		{
		    			playSpeed = tempImpVal;		    			
		    		}   		
		    		
		    	}
		    	catch(Exception e)
		    	{
		    	}  
		    }		    
		    txt_PlaySpeed.setText(Integer.toString(playSpeed));
		});
		
	}
	
	
	public void goBack(ActionEvent event)throws IOException
	{
		if(interaction_model == 1 && update_mechanism == 1)
		{
			// reset vertex count
			Vertex c = new Vertex_1_1();
			c.setCount(0);			
		}
		else if(interaction_model == 1 && update_mechanism == 2)
		{
			// reset vertex count
			Vertex c = new Vertex_1_2();
			c.setCount(0);
		}
		else
			System.out.println("Undefined behaviour for current interaction_model and update_mechanism");
		
		
		// end play loop	
		if(timer!=null)
			timer.cancel();
		
		// change window
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Editor.fxml"));
		Parent temp_parent = (Parent)loader.load();
		Scene temp_scene = new Scene(temp_parent);
		Stage app_Stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		//keep the current window size
		double stage_height = app_Stage.getHeight();
		double stage_width = app_Stage.getWidth();
		
		// change scene
		app_Stage.setScene(temp_scene);
		
		// set window size to previous scene
		app_Stage.setHeight(stage_height);
		app_Stage.setWidth(stage_width);
		
		EditorControler controller = loader.getController();		
		controller.addMainDisplaySizeListeners(app_Stage.getScene().getHeight(),app_Stage.getScene().getWidth());
		
	}


	public void doRestore()
	{
		// reset node cooperations
		for(int i = 0; i<numberOfNodes; i++)
		{
			if(initial_nodes_strategy.get(i)==0)
			{
				nodes.get(i).setCurrentStrategy(0);				
			}
			else
			{
				nodes.get(i).setCurrentStrategy(1);				
			}			
		}
		
		
		if(interaction_model == 1 && update_mechanism == 1)
		{
			int[] payoffs = {payoff_T, payoff_R, payoff_P, payoff_S};
			
			// Calculate current Payoff for each node				
			for(int i = 0; i<numberOfNodes; i++)
			{				
				nodes.get(i).calculateNextPayoff(payoffs);
				nodes.get(i).updateCurrentPayoff();
				
				nodes.get(i).setMark(0);
			}			
		}
		else if(interaction_model == 1 && update_mechanism == 2)
		{
			int[] payoffs = {payoff_T, payoff_R, payoff_P, payoff_S};
			
			// Calculate current Payoff for each node				
			for(int i = 0; i<numberOfNodes; i++)
			{				
				nodes.get(i).calculateNextPayoff(payoffs);
				nodes.get(i).updateCurrentPayoff();
				
				nodes.get(i).setMark(0);
			}
		}
		else
			System.out.println("Undefined behaviour for current interaction_model and update_mechanism");
		
		
		updateMainDisplay();
		
		
	}
	
	public void doStep()
	{
		// Call the step function
		oneStep();		
	}
	
	public void doPlay_Pause()
	{
		// if pause
		if(btn_Play_Pause.getText().equals("Play"))
		{
		// change text
			btn_Play_Pause.setText("Pause");
		// start play loop
			loopStep();
		}
		else // else if play
		{
		
		// change text
			btn_Play_Pause.setText("Play");
		// end play loop				
			timer.cancel();
		}
		
		
	}

	public void oneStep()
	{	
		// Calculate next defect for each node
		for(int i = 0; i<numberOfNodes; i++)
		{
			nodes.get(i).calculateNextStrategy();			
		}
		// Update current defect
		
		for(int i = 0; i<numberOfNodes; i++)
		{
			nodes.get(i).updateCurrentStrategy();			
		}
		
		if(interaction_model == 1 && update_mechanism == 1)
		{
			int[] payoffs = {payoff_T, payoff_R, payoff_P, payoff_S};
			
			// Calculate current Payoff for each node				
			for(int i = 0; i<numberOfNodes; i++)
			{				
				nodes.get(i).calculateNextPayoff(payoffs);
				nodes.get(i).updateCurrentPayoff();
				
				nodes.get(i).setMark(0);
			}			
		}
		else if(interaction_model == 1 && update_mechanism == 2)
		{
			int[] payoffs = {payoff_T, payoff_R, payoff_P, payoff_S};
			
			// Calculate current Payoff for each node				
			for(int i = 0; i<numberOfNodes; i++)
			{				
				nodes.get(i).calculateNextPayoff(payoffs);
				nodes.get(i).updateCurrentPayoff();
				
				nodes.get(i).setMark(0);
			}
		}
		else
			System.out.println("Undefined behaviour for current interaction_model and update_mechanism");
		
		updateMainDisplay();
		
	}
	
	public void loopStep()
	{		
		timer = new Timer();
		
		timer.schedule( new TimerTask(){
            public void run(){
            	 Platform.runLater(new Runnable() {
            		 public void run(){
            			 oneStep(); //do a simulation step/turn
            		 }
            	 });
            }
        },playSpeed,  playSpeed ); // Every playSpeed ms
	}
	

	public void updateMainDisplay()
	{		
		if(cbox_ImageView.isSelected()==false)
		{
			
			if(canvas!=null)
			{
				pane_MainDisplay.getChildren().remove(canvas);
				canvas=null;
			}
			// the view start and end range
			double viewXStart = (pane_MainDisplay.getBoundsInLocal().getMaxX()-scrlpane_MainDisplay.getPrefWidth())*scrlpane_MainDisplay.getHvalue();
			viewXStart=viewXStart-50;
			double viewXEnd = viewXStart+scrlpane_MainDisplay.getPrefWidth();
			viewXEnd = viewXEnd +200;
			
			double viewYStart = (pane_MainDisplay.getBoundsInLocal().getMaxY()-scrlpane_MainDisplay.getPrefHeight())*scrlpane_MainDisplay.getVvalue();
			viewYStart=viewYStart-50;
			double viewYEnd = viewYStart+scrlpane_MainDisplay.getPrefHeight();
			viewYEnd = viewYEnd +200;
			
			
			//mark the nodes in the new range
			for(int i=0;i<nodes.size();i++)
			{
				double currentNodeXPossition = nodes.get(i).getCenterX();
				double currentNodeYPossition = nodes.get(i).getCenterY();
				
				//if in the view range
				if( (currentNodeXPossition> viewXStart && currentNodeXPossition<viewXEnd)&& 
						(currentNodeYPossition> viewYStart && currentNodeYPossition<viewYEnd))
						{
							if(nodes.get(i).getMark()== 0)
								nodes.get(i).setMark(1);
						}
				else
					nodes.get(i).setMark(0);
			}
			
			
			
			for(int i=0;i<nodes.size();i++)
			{
				if(nodes.get(i).getMark()== 1)
				{
					//create the nodes in the new range					
					showNode(nodes.get(i));
				}
				else if(nodes.get(i).getMark()== 0)
				{
					// and remove the ones not in the range	
						
					pane_MainDisplay.getChildren().remove(nodes.get(i).getVisualObject());
					nodes.get(i).setVisualObject(null);
					
				}
			}		
			
			
			for(int i=0;i<nodes.size();i++)
			{
				if(nodes.get(i).getMark()== 1) // create all edges to a new marked node
				{
					for(int j=0;j<nodes.get(i).getEdges().size();j++)
					{
						showEdge(nodes.get(i).getEdges().get(j));
					}
				}
				else if(nodes.get(i).getMark()== 0)//remove all edges from unmarked nodes to other unmarked nodes
				{
					for(int j=0;j<nodes.get(i).getEdges().size();j++)
					{
						if(nodes.get(i).getEdges().get(j).getNodeA().getMark() == 0 && nodes.get(i).getEdges().get(j).getNodeB().getMark()== 0)
							{	
								pane_MainDisplay.getChildren().remove(nodes.get(i).getEdges().get(j).getVisualObject());
								nodes.get(i).getEdges().get(j).setVisualObject(null);
							}
					}
				}
			}
			
			//update marks
			for(int i=0;i<nodes.size();i++)
			{
				if(nodes.get(i).getMark()== 1) 
				{
					nodes.get(i).setMark(2);
				}
				else if(nodes.get(i).getMark()== 0)
				{
					nodes.get(i).setMark(0);
				}
			}
					
		
		}
		else
		{
					
			if(pane_MainDisplay.getChildren().size()>1)
			{
				for(int i = 0; i<numberOfNodes; i++)
				{
					pane_MainDisplay.getChildren().remove(nodes.get(i).getVisualObject());
					nodes.get(i).setVisualObject(null);
					nodes.get(i).setMark(0);
					
					for(int j=0;j<nodes.get(i).getEdges().size();j++)
					{
						pane_MainDisplay.getChildren().remove(nodes.get(i).getEdges().get(j).getVisualObject());
						nodes.get(i).getEdges().get(j).setVisualObject(null);
					}
				}
			}
			
			
			int length=0, height=0;
			
			for(int i = 2; i<numberOfNodes; i++)
			{
				if(nodes.get(i).getCenterY().compareTo(nodes.get(1).getCenterY())!= 0)
				{
					length = i;
					break;
				}
			}
			length = length+1;
			height = numberOfNodes/length;
			
			pane_MainDisplay.getChildren().remove(canvas);
			canvas=null;
			int zoomFactor = Integer.parseInt(choiceBox_GraphSelection_SetZoom.getValue());
			
			canvas = new Canvas(10+length*zoomFactor, 10+height*zoomFactor);
			
			// Get the graphics context of the canvas
	        GraphicsContext gc = canvas.getGraphicsContext2D();	        
	        
	        
	        
	        //draw borders
			Color color = Color.rgb(0, 0, 0);
			
			gc.setFill(color);
			
			//left border
			gc.fillRect(0,0,5,10+height*zoomFactor);
	        
			//right border
			gc.fillRect(5+length*zoomFactor,0,10+length*zoomFactor,10+height*zoomFactor);	
			
			//top border
			gc.fillRect(0,0, 10+length*zoomFactor,5);
	        
			//bottom border
			gc.fillRect(0,5+height*zoomFactor,10+length*zoomFactor,10+height*zoomFactor);       
	        
	       
	        //draw nodes	        
	        for(int i=0;i<length;i++)
	        	for(int j=0;j<height;j++)
	        	{
	        		if(interaction_model == 1 && update_mechanism == 1)
	        		{
	        			if(nodes.get(j*length+i).getCurrentStrategy()==0)
		        			color = Color.rgb(150, 0, 0);
		        		else
		        			color = Color.rgb(0, 100, 255);	        			
	        		}
	        		else if(interaction_model == 1 && update_mechanism == 2)
	        		{
	        			if(nodes.get(j*length+i).getCurrentStrategy()==0)
		        			color = Color.rgb(150, 0, 0);
		        		else
		        			color = Color.rgb(0, 100, 255);
	        		}
	        		else
	    				System.out.println("Undefined behaviour for current interaction_model and update_mechanism");
	        		
	        		
	        		gc.setFill(color);	        		
	        		gc.fillRect(5+i*zoomFactor,5+j*zoomFactor, zoomFactor,zoomFactor);
	        		
	        	}	        
	         				
					        
	        pane_MainDisplay.getChildren().add(canvas);
	        canvas.setLayoutX(100);
	        canvas.setLayoutY(100);
		
			
		}
		
	}
	
	public void addMainDisplayScrollListeners()
	{
		//add listeners for change in the scrollpane (so we can only show the nodes and edges that are visible)
		
		scrlpane_MainDisplay.vvalueProperty().addListener((observable, oldValue, newValue) -> {	            	 
			//update Main Display
			updateMainDisplay();
	            	 
	        });
		
		
		scrlpane_MainDisplay.hvalueProperty().addListener((observable, oldValue, newValue) -> {
			//update Main Display
			updateMainDisplay();            
		});
	}
	
	
	public void addMainDisplaySizeListeners(double scene_height,double scene_widht)
	{		
		
		// set the initial height and width of the main display to be equal to the screen sizes
		pane_MainDisplay.setPrefHeight(scene_height);
		pane_MainDisplay.setPrefWidth(scene_widht);
		
		
		// look at all the nodes and see if one of them is outside the boundaries.		
		for(int i=0; i<numberOfNodes; i++)
	    {
			if(nodes.get(i).getCenterX()>pane_MainDisplay.getPrefWidth()-200)
				pane_MainDisplay.setPrefWidth(nodes.get(i).getCenterX() + 200);
			
			if(nodes.get(i).getCenterY()>pane_MainDisplay.getPrefHeight()-200)
				pane_MainDisplay.setPrefHeight(nodes.get(i).getCenterY() + 200);
	    }
		
		
		scrlpane_MainDisplay.heightProperty().addListener((observable, oldValue, newValue) -> {
		    if(newValue.doubleValue()>pane_MainDisplay.getPrefHeight())
		    {
		    	pane_MainDisplay.setPrefHeight(newValue.doubleValue());		    	  
		    }		
		    
		    scrlpane_MainDisplay.setPrefHeight(newValue.doubleValue());
		    
		    updateMainDisplay(); 
		    
		});
		
		scrlpane_MainDisplay.widthProperty().addListener((observable, oldValue, newValue) -> {
		    if(newValue.doubleValue()>pane_MainDisplay.getPrefWidth())
		    {
		    	pane_MainDisplay.setPrefWidth(newValue.doubleValue());		    	  
		    }
		    
		    scrlpane_MainDisplay.setPrefWidth(newValue.doubleValue());
		    
		    updateMainDisplay(); 
		    
		});
		
	}
	
	public void saveImage()
	{
				
		FileChooser fc = new FileChooser();
	    fc.setInitialDirectory(new File("./"));
	    fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG","*.png"));
	    fc.setTitle("Image");
	    File file = fc.showSaveDialog(pane_MainDisplay.getScene().getWindow());
	    if(file != null){
	        WritableImage wi = new WritableImage((int)canvas.getWidth(),(int)canvas.getHeight());
	        try {                    ImageIO.write(SwingFXUtils.fromFXImage(canvas.snapshot(null,wi),null),"png",file);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}
		
	
}
