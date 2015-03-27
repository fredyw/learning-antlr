package profiler;

import java.util.*;
import java.io.*;

public final class HelloWorld implements Serializable {
    public final void sayHello(String message) {
        saySomething(message);
    }
    
    private void saySomething(String message) {
        return message;
    }
}
