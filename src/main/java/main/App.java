package main;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;
import org.apache.commons.lang3.StringUtils;

public class App implements Daemon {

    private Thread appThread;
    private boolean stopped = false;

    @Override
    public void init(DaemonContext daemonContext) throws Exception {
        appThread = new Thread() {

            @Override
            public synchronized void start() {
                App.this.stopped = false;
                super.start();
            }

            @Override
            public void run() {
                while(!stopped){
                    System.out.println("Raspberry Pi Daemon Test!");
                    try {
                        Thread.sleep(60000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
    }

    @Override
    public void start() throws Exception {
        appThread.start();
    }

    @Override
    public void stop() throws Exception {
        try{
            appThread.join(1000);
        }catch(InterruptedException e){
            System.err.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void destroy() {
        appThread = null;
    }

    public static void main(String[] args) {
        System.out.println("Raspberry Pi Daemon Test!");
        System.out.println(StringUtils.repeat("-", 30));
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
