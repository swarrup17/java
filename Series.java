public class Series {
    static float fact(float a){
        if(a==0 || a==1)
            return 1;
    
        else{
            return(a*fact(a-1));
        }
    }
    public static void main(String[] args) {
        float i;
        float sum=0;
        for(i=1;i<=7;i++){
                sum=sum+(i/fact(i));

        }
        System.out.println("The sum of first seven terms in the series is:"+sum);
    }
    
}