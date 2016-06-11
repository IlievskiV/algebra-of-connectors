package ch.epfl.risd.ac.datastructures;
public class Tuple<X, Y> { 
  public X x; 
  public Y y; 
  public Tuple(X x, Y y) { 
    this.x = x; 
    this.y = y; 
  }
  
  public X fst() {return x;}
  public Y snd() {return y;}
  
  public void setFst(X val) {this.x = val;}
  public void setSnd(Y val) {this.y = val;}
  
 @Override
  public String toString() {
   return "{" + x.toString() + ", " + y.toString() + "}";
  }
} 