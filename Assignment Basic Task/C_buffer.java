//2715375

import java.util.*;

public class C_buffer {
    private Vector<Object> data;

    public C_buffer() {
        data = new Vector<Object>();
    }


    public int size() {
        return data.size();
    }


    public synchronized void saveRequest(String[] r) {
        data.add(r[0]);
        data.add(r[1]);
        show();
    }


    public void show() {
        System.out.print("Buffer contents:");
        for (int i = 0; i < data.size(); i++) {
            System.out.print(" " + data.get(i) + "|");
        }
        System.out.println(" ");
    }

    synchronized public Object get() {
        Object o = null;
        if (data.size() > 0) {
            o = data.get(0);
            data.remove(0);
        }
        return o;
    }


}
	
	
