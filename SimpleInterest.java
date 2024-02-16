public class SimpleInterest{
public static void main(String args[]){
    float p=1000.1f,t=2.5f,r=3.2f;
    calculateInterest(p,t,r);
    }
static void calculateInterest(float p,float t, float r){
   float SI;
   float interest=p*t*r;
    SI=interest/100;
    System.out.println("The simple interest is:"+SI);
}
}
