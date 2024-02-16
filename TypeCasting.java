public class TypeCasting {
    public static void main(String[] args) {
        byte b;
        int i=355;
        double d=423.150;
       b= (byte) i;
System.out.println("i=" +i+ "b="+b);
b=(byte)b;
System.out.println("Conversion of double to byte:d=" + d + "b=" + b);
    }
}
