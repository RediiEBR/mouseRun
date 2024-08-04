package mouserun.mouse;

import mouserun.game.Cheese;
import mouserun.game.Grid;
import mouserun.game.Par;

import java.util.*;
//@SuppressWarnings("unused")
public class BFSSearch extends mouserun.game.Mouse
{
    public HashMap<Par<Integer,Integer>,Grid> visitadosR ;
    public HashMap<Par<Integer,Integer>,Grid> visitados ;
    public Stack<Integer> vueltaAtras;
    public Stack<Integer> camino;

    public BFSSearch()
    {
        super("");
        visitadosR = new HashMap<>();
        visitados = new HashMap<>();
        vueltaAtras = new Stack<>();
    }

    @Override
    public int move(Grid currentGrid, Cheese cheese)
    {
        if(visitados.containsKey(new Par<>(cheese.getX(),cheese.getY())))
        {
            return BFS(currentGrid,cheese);
        }
        return classic(currentGrid,cheese);
    }

    private int BFS(Grid currentGrid, Cheese cheese)
    {

        if(camino.empty())
        {
            //generate path
            Queue<Grid> q = new LinkedList<>();
            

        }
        else
        {
            return camino.pop();
        }
        return UP;

    }

    private int classic(Grid currentGrid,Cheese cheese)
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

        if(opciones.isEmpty())
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
        int solucion=Math.abs(random.nextInt()%opciones.size());
        if(opciones.get(solucion)==UP) vueltaAtras.push(DOWN);
        else if(opciones.get(solucion)==DOWN) vueltaAtras.push(UP);
        else if(opciones.get(solucion)==LEFT) vueltaAtras.push(RIGHT);
        else if((opciones.get(solucion)==RIGHT)) vueltaAtras.push(LEFT);
        else vueltaAtras.push(BOMB);
        return opciones.get(solucion);
    }
    @Override
    public void newCheese() {

    }

    @Override
    public void respawned()
    {
        vueltaAtras.clear();
        visitados.clear();
    }
    class Nodo
    {
        Nodo padre;
        Grid content;
        Nodo(Nodo padre, Grid content)
        {
            this.padre = padre;
            this.content = content;
        }

    }
}
