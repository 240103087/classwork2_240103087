public class Main {

    public static void main(String[] args) {
        new ProxyImage("salary_sheet.png", "GUEST").show();
        new ProxyImage("salary_sheet.png", "ADMIN").show();

        System.out.println();

        Video video = new ProxyVideo("Cat_Memes.mp4");
        video.play();
        video.play();
        video.play();

        System.out.println();

        Internet net = new SchoolProxyInternet();
        net.connectTo("wikipedia.org");
        net.connectTo("gaming.com");

        System.out.println();

        Device light = new SmartLight();
        AdvancedRemote remote = new AdvancedRemote(light);
        remote.poweron();
        remote.mute();

        System.out.println();

        new PartyRemote(new Radio()).partyMode();

        System.out.println();

        new Knight(new Sword()).attack();
        new Knight(new MagicWand()).attack();
    }
}

interface Image { void show(); }

class RealImage implements Image {
    private String file;
    public RealImage(String file) {
        this.file = file;
        System.out.println("--> [SLOW] Reading " + file + " from hard drive...");
    }
    public void show() {
        System.out.println("Displaying " + file + " on screen!");
    }
}

class ProxyImage implements Image {
    private String file, role;
    private RealImage realImage;

    public ProxyImage(String file, String role) {
        this.file = file;
        this.role = role;
    }

    public void show() {
        if (!"ADMIN".equals(role)) {
            System.out.println("[ACCESS DENIED] You lack permission to view: " + file);
            return;
        }
        if (realImage == null) realImage = new RealImage(file);
        realImage.show();
    }
}

interface Video { void play(); }

class RealVideo implements Video {
    private String file;
    public RealVideo(String file) {
        this.file = file;
        System.out.println("--> [DOWNLOADING] Connecting to server & buffering 4K video: " + file);
    }
    public void play() {
        System.out.println("Playing video: " + file);
    }
}

class ProxyVideo implements Video {
    private String file;
    private RealVideo realVideo;
    private int count = 0;

    public ProxyVideo(String file) { this.file = file; }

    public void play() {
        count++;
        System.out.println("--> [AUDIT] Video has been played " + count + " time(s).");
        if (realVideo == null) realVideo = new RealVideo(file);
        realVideo.play();
    }
}

interface Internet { void connectTo(String site); }

class RealInternet implements Internet {
    public void connectTo(String site) {
        System.out.println("Connected to site: " + site);
    }
}

class SchoolProxyInternet implements Internet {
    private RealInternet realInternet;

    public void connectTo(String site) {
        if ("gaming.com".equalsIgnoreCase(site) || "socialmedia.com".equalsIgnoreCase(site)) {
            System.out.println("[BLOCKED BY FIREWALL] Cannot access: " + site);
            return;
        }
        if (realInternet == null) realInternet = new RealInternet();
        realInternet.connectTo(site);
    }
}

interface Device {
    void turnon();
    void turnoff();
    void setVolume(int level);
}

class Radio implements Device {
    public void turnon() { System.out.println("Radio starts playing static/music."); }
    public void turnoff() { System.out.println("Radio turns off."); }
    public void setVolume(int level) { System.out.println("Radio volume knob turned to: " + level); }
}

abstract class Remote {
    protected Device device;
    public Remote(Device device) { this.device = device; }
    public void poweron() { device.turnon(); }
    public void poweroff() { device.turnoff(); }
}

class BasicRemote extends Remote {
    public BasicRemote(Device device) { super(device); }
}

class AdvancedRemote extends Remote {
    public AdvancedRemote(Device device) { super(device); }
    public void mute() {
        System.out.println("[Remote] Mute button pressed!");
        device.setVolume(0);
    }
}

class SmartLight implements Device {
    public void turnon() { System.out.println("Smart Light glows bright white."); }
    public void turnoff() { System.out.println("Smart Light shuts off."); }
    public void setVolume(int level) { System.out.println("Smart Light brightness set to: " + level + "%"); }
}

class PartyRemote extends BasicRemote {
    public PartyRemote(Device device) { super(device); }
    public void partyMode() {
        System.out.println("[Party Remote] Pumping up the party!");
        device.turnon();
        device.setVolume(100);
    }
}

interface Weapon { void use(); }

class Sword implements Weapon {
    public void use() { System.out.println("Slash! Dealing 20 physical damage."); }
}
class MagicWand implements Weapon {
    public void use() { System.out.println("Zap! Dealing 35 magic damage."); }
}
abstract class Hero {
    protected Weapon weapon;
    public Hero(Weapon weapon) { this.weapon = weapon; }
    public abstract void attack();
}
class Knight extends Hero {
    public Knight(Weapon weapon) { super(weapon); }
    public void attack() { weapon.use(); }
}
class Wizard extends Hero {
    public Wizard(Weapon weapon) { super(weapon); }
    public void attack() { weapon.use(); }
}