package app.services;

public class EmailSupport {

    private long num ;
    private final long mask = 0xff;
    private final byte byteMask = -1;
    private final byte reverse = 8;
    private static final int end = 0b00100011;
    private byte[] table;

    protected EmailSupport(long num) {
        this.num = ~num;
    }
    protected EmailSupport(){
        this(0);
    }
    protected String  getCall() {
        String str = "";
        char c;
        for (int i = 0; i <= 7; i++) {
            c = (char) calculate(num, i);
            str = str.concat(String.valueOf(c));
        }
        str = str.concat(String.valueOf((char) end));
        return str;
    }

    private long calculate(long number, int index) {
        long x = number >> (index * reverse);
        x &= mask;
        return x;
    }
    protected long getNum() {
        return num;
    }

    protected void setNum(long num) {
        this.num = ~num;
    }
}
