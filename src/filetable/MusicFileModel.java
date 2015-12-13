package filetable;

import java.io.File;

public class MusicFileModel {
    private File file;
    public File getFile() {
        return file;
    }
    public void setFile(File f) {
        file = f;
    }

    private float networkSpeed;
    public float getNetworkSpeed() {
        return networkSpeed;
    }
    public void setNetworkSpeed(float speed) {
        networkSpeed = speed;
    }

    private String hostIP;
    public String getHostIP() {
        return hostIP;
    }
    public void setHostIP(String ip) {
        hostIP = ip;
    }

    private int port;
    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
    }

    private boolean selected;
    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean value) {
        selected = value;
    }

    public MusicFileModel() {
        setSelected(false);
    }

    public MusicFileModel(File f) {
        setFile(f);
        setSelected(false);
    }

    public MusicFileModel(File f, String hostIP, int port) {
        setFile(f);
        setHostIP(hostIP);
        setPort(port);
        setSelected(false);
    }
}
