package jeu.classes;



import java.awt.Dimension;
import java.util.*;
// class pour algo astar
public class AStarSearchEngine extends AbstractSearchEngine {
    
    ArrayList<Dimension> L2= new ArrayList<Dimension>(), T=new ArrayList<Dimension>();
    PriorityQueue<Dimension> L1 = new PriorityQueue<Dimension>();
    Dimension predecessor[][];  
       Dimension  loc = new Dimension(); 
        Dimension  G = new Dimension(); 
    public AStarSearchEngine(int width, int height,Dimension Loc,Dimension g) {
        super(width, height);
        this.loc = Loc;
         this.G = g;
        predecessor = new Dimension[width][height];  
        for (int i=0; i<width; i++) 
            for (int j=0; j<height; j++) 
                predecessor[i][j] = null;
        
        L1.add(new Case(loc,0,-1.0));
        // On suppose que T contient un seul But 
        T.add(g);
        
        doAStar();
        
        // now calculate the shortest path from the predecessor array:
        maxDepth = 0;
        if (!isSearching) {
            searchPath[maxDepth++] = G;
            for (int i=0; i<2000; i++) {
                searchPath[maxDepth] = predecessor[searchPath[maxDepth - 1].width][searchPath[maxDepth - 1].height];
                maxDepth++;
                if (equals(searchPath[maxDepth - 1], Loc))  break;  // back to starting node
            }
        }
    }
    
    // On suppose que T contient un seul But (gloalLoc)
    public double h(Dimension d) {
          return Math.sqrt((G.height-d.height)*(G.height-d.height)+(G.width-d.width)*(G.width-d.width));
    }
   
    private void doAStar() {
              
        while (!L1.isEmpty()) {
            
            currentLoc = L1.remove(); // éléments triés dans L1 par ordre croissant de f
            
            L2.add(currentLoc);           
            if(T.contains(currentLoc)){
                 System.out.println("But trouvé : (" + currentLoc.width +", " + currentLoc.height+")");
                 isSearching = false;
                 break;
             }
             else{
                
                Dimension [] connected = getPossibleMoves(currentLoc);
                for (int i=0; i<4; i++) {

                    if ( connected[i]!=null && !L1.contains(connected[i]) && !L2.contains(connected[i])) {
                        Case casei= new Case(connected[i],((Case)currentLoc).getG()+1,((Case)currentLoc).getG()+1+h(connected[i]));
                        predecessor[connected[i].width][connected[i].height] = currentLoc;
                        L1.add(casei);
                    }
                }
            }
        }
        
    }
   
}
