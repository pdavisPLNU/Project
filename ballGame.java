import javafx.event.*;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.canvas.*;
import javafx.scene.layout.*;
import javafx.animation.*;
import javafx.application.*;
import javafx.geometry.*;
import javafx.stage.*;
import java.util.*;
import javafx.scene.paint.Color;


//create canvas for shape within center of grid
public class ballGame extends Application
{
   private GamePane[][] GamePanes = new GamePane[4][4];
   private GridPane gp = new GridPane();
   
   public void start(Stage stage)
   {
      //Root component as border pane
      BorderPane root = new BorderPane();
      
      //Add text top top border and center it
      Label l1 = new Label("The Text Field");
      root.setTop(l1);
      root.setAlignment(l1,Pos.CENTER);
      
      //GridPane gp = new GridPane();
        //gp.setHgap(5);
        //gp.setVgap(5);
      //GamePane GamePanes [][]= new GamePane[4][4]; 
        // Add GamePanes to grid pane 
        for (int i = 0; i < 4; i++) 
        {
            for (int j = 0; j < 4; j++) 
            {
                GamePane gamePane = new GamePane(i,j);
                
                GamePanes[i][j]=gamePane;
                
                //gp.add(gamePane, i, j);
                if(gamePane.getX()==0&&gamePane.getY()==2)
                {
                  gamePane.setC(false);
                }
                
                //gamePane.drawGamePane();
            }
        }
        
        for (int i = 0; i < 4; i++) 
        {
            for (int j = 0; j < 4; j++) 
            {
                playable(GamePanes);
                gp.add(GamePanes[i][j],i,j);
                GamePanes[i][j].drawGamePane();
            }
        }
          //System.out.println(GamePanes[1][2].getX());
        
               

        // Add grid pane to center of border pane
        root.setCenter(gp);
        root.setAlignment(gp,Pos.CENTER);      
            
      Scene scene = new Scene(root, 600, 600);
      stage.setScene(scene);
      stage.setTitle("Ball Game");
      stage.show();

   }

   //Playable
   public void playable(GamePane GamePanes[][])
   {
      for (int i=0;i<4;i++)
      {
         for (int j=0;j<4;j++)
         {
            GamePane curr=GamePanes[i][j];
            //top button
            if(curr.getY()<2 && GamePanes[i][j+2].getC()==false && GamePanes[i][j+1].getC()==true)
            {
               curr.setT(true);
            }
            else
            {
               curr.setT(false);
            }
            //bottom button
            if(curr.getY()>1 && GamePanes[i][j-2].getC()==false && GamePanes[i][j-1].getC()==true)
            {
               curr.setB(true);
            }
            else
            {
               curr.setB(false);
            }

            //left button
            if(curr.getX()<2 && GamePanes[i+2][j].getC()==false && GamePanes[i+1][j].getC()==true)
            {
               curr.setL(true);
            }
            else
            {
               curr.setL(false);
            }

            //right button
            if(curr.getX()>1 && GamePanes[i-2][j].getC()==false && GamePanes[i-1][j].getC()==true)
            {
               curr.setR(true);
            }
            else
            {
               curr.setR(false);
            }

         }
      }
   }
   
   public void click(String direction,int x,int y)
   {
      GamePane current = GamePanes[x][y];
      
      switch (direction)
      {
         case "top":
            if(y<2 && GamePanes[y+1][x].getC()==true && GamePanes[y+2][x].getC()==false)
            {
               current.setC(false);
               current.drawGamePane();
               GamePanes[y+1][x].setC(false);
               GamePanes[y+2][x].setC(true);
            }
            break;
         
         case "bottom":
            if(y>2 && GamePanes[x][y-1].getC()==true && GamePanes[x][y-2].getC()==false)
            {
               current.setC(false);
               GamePanes[x][y-1].setC(false);
               GamePanes[x][y-2].setC(true);
            }
            break;
         
         case "left":
            if(x<2 && GamePanes[x+1][y].getC()==true && GamePanes[x+2][y].getC()==false)
            {
               current.setC(false);
               GamePanes[x+1][y].setC(false);
               GamePanes[x+2][y].setC(true);
            }
            break;
         
         case "right":
            if(x>2 && GamePanes[x-1][y].getC()==true && GamePanes[x-2][y].getC()==false)
            {
               current.setC(false);
               current.drawGamePane();
               GamePanes[x-1][y].setC(false);
               GamePanes[x-2][y].setC(true);
            }
            break;

         
      }
      
   }
   
   public class GamePane extends GridPane 
   {
      //private static int
      
      private Button Top;
      private Button Bottom;
      private Button Left;
      private Button Right;
      
      private Canvas canvas;
      
      private int x;
      private int y;
      
      private boolean CT=true;
      
      private boolean TT=true;
      private boolean BT=true;
      private boolean LT=true;
      private boolean RT=true;
      
      public GamePane(int x,int y) 
      {
           super();
   
           this.x = x;
           this.y = y;
           
           // Set grid dimensions
           this.setHgap(5);
           this.setVgap(5);
      }
      
      public int getX()
      {
         return x;
      }
      public int getY()
      {
         return y;
      }
      
      public void setPaneVisible(boolean v)
      {
         if(v==false)
         {
            setC(false);
            canvas.setVisible(false);
         }
      }
      //-------------GET BUTTONS--------------
      public Button getTB()
      {
         return Top;
      }
      public Button getBB()
      {
         return Bottom;
      }
      public Button getLB()
      {
         return Left;
      }
      public Button getRB()
      {
         return Right;
      }


      public void drawGamePane()
      {     
         if(this.getC()==false)
         {
            this.TT = false;
            this.BT = false;
            this.LT = false;
            this.RT = false;
         } 
         else
         {    
           Button T = new Button();
           T.setPrefSize(80, 20);
           Top = T;
           Top.setOnAction(new ButtonHandler());
           this.add(Top, 1, 0);
           
           Top.setVisible(TT);
           
           Button B = new Button();
           B.setPrefSize(80, 20);
           Bottom = B;
           Bottom.setOnAction(new ButtonHandler());
           this.add(Bottom, 1, 2);
           Bottom.setVisible(BT);
           
           
           Button L = new Button();
           L.setPrefSize(20, 80);
           Left = L;
           Left.setOnAction(new ButtonHandler());
           this.add(Left, 0, 1);
           Left.setVisible(LT);
           
           
           Button R = new Button();
           R.setPrefSize(20, 80);
           Right = R;
           Right.setOnAction(new ButtonHandler());
           this.add(Right, 2, 1);
           Right.setVisible(RT);
           
           canvas = new Canvas(80, 80);
           GraphicsContext gc = canvas.getGraphicsContext2D();
           gc.setFill(Color.BLUE);
           gc.fillOval(0, 0, 80, 80);
           this.add(canvas, 1, 1);
           canvas.setVisible(CT);
         }
         

           
      }

      // -------------TOP BUTTON---------------
      public void setT(boolean Active)
      {
         if(Active == true)
         {
            TT = true;
         }
         else
         {
            TT = false;
         }
      }
      public boolean getT()
      {
         return TT;
      }
      
         
      //-------------BOTTOM BUTTON--------------
      public void setB(boolean Active)
      {
         if(Active == true)
         {
            BT = true;
         }
         else
         {
            BT = false;
         }
      }
      public boolean getB()
      {
         return BT;
      }

         
      // ------------LEFT BUTTON-----------------
      public void setL(boolean Active)
      {
         if(Active == true)
         {
            LT = true;
         }
         else
         {
            LT = false;
         }
      }
      public boolean getL()
      {
         return LT;
      }

         
      // --------------RIGHT BUTTON---------------
      public void setR(boolean Active)
      {
         if(Active == true)
         {
            RT = true;
         }
         else
         {
            RT = false;
         }
      }
      public boolean getR()
      {
         return RT;
      }

         
      // ----------------CENTER-------------------
      public void drawC()
      {
              Canvas canvas = new Canvas(80, 80);
              GraphicsContext gc = canvas.getGraphicsContext2D();
              gc.setFill(Color.BLUE);
              gc.fillOval(0, 0, 80, 80);
              this.add(canvas, 1, 1);
      }
      
      public void setC(boolean Active)
      {
         if(Active == true)
         {
            CT = true;
         }
         else
         {
            CT = false;
         }
      }
      public boolean getC()
      {
         return CT;
      }
     
      public class ButtonHandler implements EventHandler<ActionEvent>  
      {
         public void handle(ActionEvent e) 
         {
            Button b = (Button) e.getSource();
            
            int x = GridPane.getColumnIndex(b.getParent());
            int y = GridPane.getRowIndex(b.getParent());

            if(b==GamePanes[x][y].getTB())
            {
               click("top",x,y);
               System.out.println("hi");
            }
            if(b==GamePanes[x][y].getBB())
            {
               click("bottom",x,y);
            }
            if(b==GamePanes[x][y].getLB())
            {
               click("left",x,y);
            }
            if(b==GamePanes[x][y].getRB())
            {
               click("right",x,y);
            }

              
         }
      }


           
   }
   public static void main(String[] args)
   {
      launch(args);
   }

}
