package mouserun.mouse;

import java.util.*;

import mouserun.game.Cheese;
import mouserun.game.Grid;
import mouserun.game.Mouse;
import mouserun.game.Par;
public class explorer extends Mouse
{

	private final HashMap<Par<Integer,Integer>,Grid> visitados;
	private final HashMap<Par<Integer,Integer>,Grid> visitadosR;
	private final Stack<Integer> vueltaAtras;
	
	public explorer()
	{
		super("Miraor");
		vueltaAtras = new Stack<>();
		visitados = new HashMap<>();
		visitadosR = new HashMap<>();
	}

	@Override
	public int move(Grid currentGrid, Cheese cheese) 
	{
		
		Random random = new Random();
        ArrayList<Integer> opciones = new ArrayList<>();
        if(!visitadosR.containsKey(new Par<>(currentGrid.getX(),currentGrid.getY())))
        {
        	visitadosR.put(new Par<>(currentGrid.getX(),currentGrid.getY()),currentGrid);
        	incExploredGrids();
        }
        if(!visitados.containsKey(new Par<>(currentGrid.getX(),currentGrid.getY())))
        {
        	visitados.put(new Par<>(currentGrid.getX(),currentGrid.getY()),currentGrid);
        	
        }
        if (currentGrid.canGoUp() && !visitados.containsKey(new Par<>(currentGrid.getX(),currentGrid.getY()+1))) 
        {
            opciones.add(UP);
        }
        if (currentGrid.canGoDown() &&!visitados.containsKey(new Par<>(currentGrid.getX(),currentGrid.getY()-1))) 
        {
            opciones.add(DOWN);
        }
        if (currentGrid.canGoRight() && !visitados.containsKey(new Par<>(currentGrid.getX()+1,currentGrid.getY()))) 
        {
            opciones.add(RIGHT);
        }
        if (currentGrid.canGoLeft() &&!visitados.containsKey(new Par<>(currentGrid.getX()-1,currentGrid.getY()))) 
        {
            opciones.add(LEFT);
        }

        if(opciones.size()==0)
        {
        	try
        	{
        		return vueltaAtras.pop();
        	}
        	catch(EmptyStackException e)
        	{
        		this.visitados.clear();
        		return move(currentGrid, cheese);
        	}
        }
       
        
        Integer solucion=Math.abs(random.nextInt()%opciones.size());
       
        
        if(opciones.get(solucion)==UP) vueltaAtras.push(DOWN);
    	else if(opciones.get(solucion)==DOWN) vueltaAtras.push(UP);
    	else if(opciones.get(solucion)==LEFT) vueltaAtras.push(RIGHT);
    	else if((opciones.get(solucion)==RIGHT)) vueltaAtras.push(LEFT);
    	else vueltaAtras.push(BOMB);
        
        return opciones.get(solucion);
	}

	@Override
	public void newCheese() 
	{

	}

	@Override
	public void respawned() 
	{
		// TODO Auto-generated method stub
		visitados.clear();
		vueltaAtras.clear();

	}
	 public boolean visitada(Grid casilla, int direccion)
	 {
	        int x = casilla.getX();
	        int y = casilla.getY();
	        
	        switch (direccion)
	        {
	            case UP:
	                y += 1;
	                break;
	            
	            case DOWN:
	                y -= 1;
	                break;
	            
	            case LEFT:
	                x -= 1;
	                break;
	            
	            case RIGHT:
	                x += 1;
	                break;
	        }
	        Par<Integer,Integer> par = new Par<>(x, y);
	        return visitados.containsKey(par);
	    }
}
